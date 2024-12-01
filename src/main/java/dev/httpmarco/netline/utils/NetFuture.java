package dev.httpmarco.netline.utils;

import io.netty5.util.concurrent.Future;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class NetFuture<T> extends CompletableFuture<T> {

    /**
     * Simple use if generic type is Void
     */
    public void complete() {
        this.complete(null);
    }

    /**
     * Interpret a Netty future and complete this future accordingly
     *
     * @param future Netty future
     * @param value  Value to complete this future with
     */
    public void interpretFuture(@NotNull Future<?> future, T value) {
        future.addListener(it -> {
            if (future.isSuccess()) {
                this.complete(value);
                return;
            }
            this.completeExceptionally(future.cause());
        });
    }

    @SneakyThrows
    public T sync() {
        return this.get(5, TimeUnit.SECONDS);
    }

    public @NotNull NetFuture<T> waitFor(Future<?> future) {
        var binding = new NetFuture<T>();

        this.whenComplete((t, throwable) -> {
            if (throwable != null) {
                binding.completeExceptionally(throwable);
                return;
            }

            future.addListener(it -> {
                if (it.isSuccess()) {
                    binding.complete(null);
                } else {
                    binding.completeExceptionally(it.cause());
                }
            });
        });
        return binding;
    }

    public void whenCompleteSuccessfully(@NotNull Consumer<T> consumer) {
        this.whenComplete((t, throwable) -> {
            if (throwable == null) {
                consumer.accept(t);
            }
        });
    }

    /**
     * Transform a Netty future into a NetFuture and use interpretFuture to complete it
     *
     * @param it Netty future
     * @return NetFuture
     */
    public static @NotNull NetFuture<Void> interpretFuture(@NotNull Future<?> it) {
        var future = new NetFuture<Void>();
        future.interpretFuture(it, null);
        return future;
    }
}

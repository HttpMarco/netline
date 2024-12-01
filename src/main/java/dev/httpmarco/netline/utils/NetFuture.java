package dev.httpmarco.netline.utils;

import io.netty5.util.concurrent.Future;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

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
        if (future.isSuccess()) {
            this.complete(value);
            return;
        }
        this.completeExceptionally(future.cause());
    }

    /**
     * Transform a Netty future into a NetFuture and use interpretFuture to complete it
     * @param it Netty future
     * @return NetFuture
     */
    public static @NotNull NetFuture<Void> interpretFuture(@NotNull Future<?> it) {
        var future = new NetFuture<Void>();
        future.interpretFuture(it, null);
        return future;
    }
}

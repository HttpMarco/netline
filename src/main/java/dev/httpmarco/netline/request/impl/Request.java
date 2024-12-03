package dev.httpmarco.netline.request.impl;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.NetRequestMapper;
import dev.httpmarco.netline.request.NetRequestPool;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.request.packets.RequestPacket;
import dev.httpmarco.netline.utils.NetFuture;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
public final class Request<R> implements NetRequest<R> {

    private final UUID id;
    private final RequestScheme<?, R> requestScheme;
    private final NetChannel channel;

    public Request(RequestScheme<?, R> requestScheme, @NotNull NetChannel channel) {
        this.channel = channel;
        this.requestScheme = requestScheme;
        this.id = UUID.randomUUID();
    }

    @Contract(pure = true)
    @Override
    public @Nullable NetRequestMapper<R> mapResponse() {
        return null;
    }

    @Override
    public @NotNull NetFuture<R> send() {
        var future = new NetFuture<R>();

        // register the request for identification the response
        NetRequestPool.put(this);

        this.channel.send(new RequestPacket(requestScheme().id(), id));
        return future;
    }
}

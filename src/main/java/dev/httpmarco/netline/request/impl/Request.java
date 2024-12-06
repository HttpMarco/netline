package dev.httpmarco.netline.request.impl;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.NetRequestPool;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.request.packets.RequestPacket;
import dev.httpmarco.netline.request.utils.RespondPacketTranslator;
import dev.httpmarco.netline.utils.NetFuture;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

@Log4j2
@Getter
@Accessors(fluent = true)
public final class Request<R, A> implements NetRequest<R, A> {

    private final UUID id;
    private final RequestScheme<R, A> requestScheme;
    private final NetChannel channel;
    private final NetFuture<A> completeFuture = new NetFuture<>();

    public Request(RequestScheme<R, A> requestScheme, @NotNull NetChannel channel) {
        this.channel = channel;
        this.requestScheme = requestScheme;
        this.id = UUID.randomUUID();
    }

    @Override
    @SneakyThrows
    public @NotNull NetFuture<A> send(R request) {
        // register the request for identification the response
        NetRequestPool.put(this);
        log.debug("Register request with id: {}", id);

        this.channel.send(new RequestPacket(requestScheme().id(), id, RespondPacketTranslator.translate(request)));
        return completeFuture;
    }

    @SuppressWarnings("unchecked")
    public void complete(Object response) {
        completeFuture.complete((A) response);
    }
}

package dev.httpmarco.netline.client;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.client.common.AbstractNetClient;
import dev.httpmarco.netline.common.AbstractNetCompHandler;
import dev.httpmarco.netline.request.RequestScheme;
import io.netty5.channel.Channel;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
public final class NetClientHandler extends AbstractNetCompHandler {

    private final AbstractNetClient client;

    public NetClientHandler(AbstractNetClient client) {
        super(client);
        this.client = client;
    }

    @Override
    public void netChannelClose(NetChannel channel) {
        client.close();
    }

    @Override
    public void netChannelOpen(@NotNull NetChannel channel) {
        client.channel(channel);

        if(client.bootFuture() == null) {
            log.warn("Client boot future is null. So we can't proceed with the channel open event.");
            channel.close();
            return;
        }

        channel.updateId(client.config().id);
        client.request(RequestScheme.CLIENT_AUTH).send(client.config().id).whenComplete((result, throwable) -> {
            if (result) {
                client.available(true);
                client.bootFuture().complete();
            }
        });
    }

    @Override
    public NetChannel findChannel(Channel channel) {
        return client.channel();
    }
}

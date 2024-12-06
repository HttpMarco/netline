package dev.httpmarco.netline.server;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.common.AbstractNetCompHandler;
import dev.httpmarco.netline.server.common.AbstractNetServer;
import io.netty5.channel.Channel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class NetServerHandler extends AbstractNetCompHandler {

    private final AbstractNetServer<?> server;

    public NetServerHandler(AbstractNetServer<?> server) {
        super(server);
        this.server = server;
    }

    @Override
    public void netChannelClose(NetChannel channel) {
        this.server.unregisterChannel(channel);
    }

    @Override
    public void netChannelOpen(NetChannel channel) {
        if (!server.available()) {
            log.warn("Server is not available to register channel.");
            return;
        }

        server.registerChannel(channel);
    }

    @Override
    public NetChannel findChannel(Channel channel) {
        return server.allClients().stream().filter(it -> it.channel().equals(channel)).findFirst().orElse(null);
    }
}

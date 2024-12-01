package dev.httpmarco.netline.server.common;

import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.channel.NetChannelInitializer;
import dev.httpmarco.netline.common.AbstractNetComp;
import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.server.NetServerConfig;
import dev.httpmarco.netline.server.NetServerHandler;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.EventLoopGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractNetServer extends AbstractNetComp<NetServerConfig> implements NetServer {

    private final static int NET_SERVER_GROUP_THREADS = 1;
    private static final Logger log = LogManager.getLogger(AbstractNetServer.class);

    private final EventLoopGroup workerGroup = NetworkNettyUtils.createEventLoopGroup(0);

    public AbstractNetServer() {
        super(new NetServerConfig(), NET_SERVER_GROUP_THREADS);
    }

    @Override
    public NetFuture<Void> boot() {
        var future = NetFuture.interpretFuture(new ServerBootstrap()
                .group(mainGroup(), this.workerGroup)
                .childHandler(new NetChannelInitializer(handler()))
                .channelFactory(NetworkNettyUtils.generateChannelFactory())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.IP_TOS, 24)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind(config().hostname(), config().port()));

        future.whenCompleteSuccessfully(it -> {
            // now we can allow connections
            this.available(true);
            log.debug("The server is now running on port 9090");
        });

        return future;
    }

    @Override
    public NetFuture<Void> close() {
        return super.close().waitFor(this.workerGroup.shutdownGracefully());
    }

    @Override
    public NetCompHandler handler() {
        return new NetServerHandler();
    }

    @Override
    public int amountOfClients() {
        return -1;
    }
}

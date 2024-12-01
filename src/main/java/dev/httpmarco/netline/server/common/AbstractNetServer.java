package dev.httpmarco.netline.server.common;

import dev.httpmarco.netline.common.AbstractNetComp;
import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.EventLoopGroup;

import java.io.IOException;

public abstract class AbstractNetServer extends AbstractNetComp implements NetServer {

    private final static int NET_SERVER_GROUP_THREADS = 1;

    private final EventLoopGroup workerGroup = NetworkNettyUtils.createEventLoopGroup(0);

    public AbstractNetServer() {
        super(NET_SERVER_GROUP_THREADS);
    }

    @Override
    public NetFuture<Void> boot() {
        return NetFuture.interpretFuture(new ServerBootstrap()
                .group(mainGroup(), this.workerGroup)
                //.childHandler(new NetChannelInitializer(handler()))
                .channelFactory(NetworkNettyUtils.generateChannelFactory())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.IP_TOS, 24)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind("0.0.0.0", 9090));
    }

    @Override
    public void close() throws IOException {
        this.workerGroup.shutdownGracefully();
        super.close();
    }
}

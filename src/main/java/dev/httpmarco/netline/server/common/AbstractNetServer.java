package dev.httpmarco.netline.server.common;

import dev.httpmarco.netline.Available;
import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.channel.NetChannelInitializer;
import dev.httpmarco.netline.common.AbstractNetComp;
import dev.httpmarco.netline.request.RequestScheme;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractNetServer extends AbstractNetComp<NetServerConfig> implements NetServer {

    private final static int NET_SERVER_GROUP_THREADS = 1;
    private static final Logger log = LogManager.getLogger(AbstractNetServer.class);

    private final Collection<NetChannel> clients = new LinkedList<>();
    private final EventLoopGroup workerGroup = NetworkNettyUtils.createEventLoopGroup(0);

    public AbstractNetServer() {
        super(new NetServerConfig(), NET_SERVER_GROUP_THREADS);

        this.waitFor(RequestScheme.CLIENT_AUTH, (id, channel) -> {
            // set channel name here
            // check security rules here
            channel.updateId(id);
            return true;
        });
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
            log.debug("The server is now running on port {}", config().port());
        });

        return future;
    }

    @Override
    public NetFuture<Void> close() {
        log.debug("Closing the server...");

        var future = new NetFuture<Void>();

        CompletableFuture.allOf(clients.stream().map(NetChannel::close).toArray(value -> new CompletableFuture[clients.size()]))
                .whenComplete((unused, throwable) -> super.close().waitFor(this.workerGroup.shutdownGracefully())
                        .whenComplete((it, th) -> future.complete()));

        return future;
    }

    @Override
    public NetCompHandler handler() {
        return new NetServerHandler(this);
    }

    @Override
    public int amountOfClients() {
        return this.clients.size();
    }

    @Override
    public Collection<NetChannel> allClients() {
        return Collections.unmodifiableCollection(clients);
    }

    @Override
    public Collection<NetChannel> availableClients() {
        return this.clients.stream().filter(Available::available).toList();
    }

    public void registerChannel(NetChannel channel) {
        this.clients.add(channel);
    }

    public void unregisterChannel(NetChannel channel) {
        this.clients.remove(channel);
    }
}

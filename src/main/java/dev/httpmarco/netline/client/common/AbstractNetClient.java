package dev.httpmarco.netline.client.common;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.broadcast.Broadcast;
import dev.httpmarco.netline.channel.NetChannelInitializer;
import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.client.NetClientBroadcast;
import dev.httpmarco.netline.client.NetClientConfig;
import dev.httpmarco.netline.client.NetClientHandler;
import dev.httpmarco.netline.common.AbstractNetComp;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.request.impl.Request;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelOption;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@Getter
@Accessors(fluent = true)
@Log4j2
public abstract class AbstractNetClient extends AbstractNetComp<NetClientConfig> implements NetClient {

    @Nullable
    @Setter
    private Channel channel;

    @Nullable
    private NetFuture<Void> bootFuture;

    public AbstractNetClient() {
        super(new NetClientConfig());
    }

    @Override
    public NetFuture<Void> boot() {
        this.bootFuture = new NetFuture<>();
        new Bootstrap()
                .group(mainGroup())
                .channelFactory(NetworkNettyUtils::createChannelFactory)
                .handler(new NetChannelInitializer(handler()))
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.IP_TOS, 24)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .connect(config().hostname(), config().port()).addListener(future -> {
                    // we wait for the identification -> success future
                    if(future.isFailed() && bootFuture != null) {
                        this.bootFuture.completeExceptionally(future.cause());
                    }
                });

        return this.bootFuture;
    }

    @Override
    public NetCompHandler handler() {
        return new NetClientHandler(this);
    }

    @Override
    public <R, A> NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        return new Request<>(id, this);
    }

    @Override
    public void send(Packet packet) {
        this.channel.writeAndFlush(packet);
    }

    @Override
    public NetAddress clientAddress() {
        return null;
    }

    @Override
    public void updateId(String id) {
        this.config().id = id;
    }

    @Override
    public String id() {
        return this.config().id;
    }

    @Override
    public Broadcast broadcast() {
        return new NetClientBroadcast(this);
    }
}

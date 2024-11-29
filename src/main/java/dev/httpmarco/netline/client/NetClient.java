package dev.httpmarco.netline.client;

import dev.httpmarco.netline.NetChannel;
import dev.httpmarco.netline.broadcast.Broadcast;
import dev.httpmarco.netline.channel.NetChannelInitializer;
import dev.httpmarco.netline.impl.AbstractNetCompImpl;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.common.ChannelIdentifyPacket;
import dev.httpmarco.netline.request.Request;
import dev.httpmarco.netline.request.ResponderRegisterPacket;
import dev.httpmarco.netline.utils.NetworkUtils;
import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.epoll.Epoll;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@Log4j2
@Accessors(fluent = true)
public class NetClient extends AbstractNetCompImpl<NetClientConfig> implements NetChannel {

    private Bootstrap bootstrap;
    @Setter(AccessLevel.PACKAGE)
    @Nullable
    @Getter
    private Channel channel;
    @Nullable
    @Getter(AccessLevel.PACKAGE)
    private CompletableFuture<Void> bindProcess;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private NetClientState state = NetClientState.INITIALIZE;

    public NetClient() {
        super(0, new NetClientConfig());
        this.initializeClient();
    }

    @Override
    public @NotNull CompletableFuture<Void> boot() {
        var bindProcess = new CompletableFuture<Void>();
        this.bindProcess = bindProcess;

        this.state = NetClientState.CONNECTING;

        if(bossGroup().isShuttingDown() || bossGroup().isShutdown()) {
            this.reinitializeEventLoopGroup();
            this.initializeClient();
        }

        this.bootstrap.connect(config().hostname(), config().port()).addListener(it -> {
            if(it.isSuccess()) {
                this.channel = it.getNow();

                // we must wait for the channel to be identified, so we can complete the boot future
                this.request("channel_identification", ChannelIdentifyPacket.class).withProp("id", config().id()).async().whenComplete((packet, throwable) -> {
                    this.state = NetClientState.CONNECTED;
                    bindProcess.complete(null);
                    this.bindProcess = null;
                });
                return;
            }
            bindProcess.completeExceptionally(it.cause());
            this.bindProcess = null;
        });
        return bindProcess;
    }

    @Override
    public @NotNull CompletableFuture<Void> close() {
        var future = new CompletableFuture<Void>();
        if(this.channel != null) {
            this.channel.close().addListener(it -> bossGroup().shutdownGracefully().addListener(future1 -> future.complete(null)));
        }else {
            bossGroup().shutdownGracefully().addListener(future1 -> future.complete(null));
        }
        future.whenComplete((unused, throwable) -> this.state = NetClientState.CLOSED);
        return future;
    }

    @Override
    public String id() {
        return this.config().id();
    }

    @Override
    public void send(Packet packet) {
        if(channel == null || !this.channel.isActive()) {
            log.warn("Channel is not active, packet {} will not be sent on client with id {}", packet, "TODO");
            return;
        }
        this.channel.writeAndFlush(packet);
    }

    public void send(String clientId, Packet packet) {
        // todo
    }

    @Override
    public void responderOf(String id, BiFunction<NetChannel, Map<String, String>, Packet> responder) {
        super.responderOf(id, responder);

        if (this.channel != null && this.channel.isActive()) {
            send(new ResponderRegisterPacket(id));
        }
    }

    @Override
    public <T extends Packet> @NotNull Request<T> request(String id, Class<T> packetClazz) {
        return new Request<>(this, this, id, packetClazz);
    }

    @Override
    public String hostname() {
        return channel.remoteAddress().toString().split(":", -1)[0].substring(1);
    }

    private void initializeClient() {
        this.bootstrap = new Bootstrap()
                .group(bossGroup())
                .channelFactory(NetworkUtils::createChannelFactory)
                .handler(new NetChannelInitializer(new NetClientHandler(this)))
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.IP_TOS, 24)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);

        if (config().tryTcpFastOpen() && Epoll.isTcpFastOpenClientSideAvailable()) {
            bootstrap.option(ChannelOption.TCP_FASTOPEN_CONNECT, true);
        }
    }

    @Override
    public NetChannel findChannel(Channel channel) {
        return this;
    }

    @Override
    public NetChannel generateChannel(Channel channel, @Nullable String id) {
        this.channel = channel;
        return this;
    }

    @Contract(" -> new")
    public @NotNull Broadcast generateBroadcast() {
        return new Broadcast(this, this, new NetChannel[]{this});
    }
}
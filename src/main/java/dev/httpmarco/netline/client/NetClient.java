package dev.httpmarco.netline.client;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.channel.NetChannelInitializer;
import dev.httpmarco.netline.channel.NetClientHandler;
import dev.httpmarco.netline.impl.AbstractNetworkComponent;
import dev.httpmarco.netline.packet.BroadcastPacket;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.utils.NetworkUtils;
import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.epoll.Epoll;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public final class NetClient extends AbstractNetworkComponent<NetClientConfig> {

    private final Bootstrap bootstrap;

    @Getter
    @Setter
    private NetChannel channel;

    public NetClient() {
        super(0, new NetClientConfig());

        this.bootstrap = new Bootstrap()
                .group(bossGroup())
                .channelFactory(NetworkUtils::createChannelFactory)
                .handler(new NetChannelInitializer(new NetClientHandler(this)))
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.IP_TOS, 24)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);


        if(!config().disableTcpFastOpen() && Epoll.isTcpFastOpenClientSideAvailable()) {
            bootstrap.option(ChannelOption.TCP_FASTOPEN_CONNECT, true);
        }
    }

    @Override
    public void boot() {
        this.bootstrap.connect(config().hostname(), config().port()).addListener(handleConnectionRelease());
    }

    public void broadcast(Packet packet) {
        this.channel.send(new BroadcastPacket(packet));
    }

    public void send(Packet packet) {
        this.channel.send(packet);
    }
}
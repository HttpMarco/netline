package dev.httpmarco.netline.client.common;

import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.channel.NetChannelInitializer;
import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.client.NetClientConfig;
import dev.httpmarco.netline.client.NetClientHandler;
import dev.httpmarco.netline.common.AbstractNetComp;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.bootstrap.Bootstrap;
import io.netty5.channel.ChannelOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;

@Getter
@Accessors(fluent = true)
@Log4j2
public abstract class AbstractNetClient extends AbstractNetComp<NetClientConfig> implements NetClient {

    @Nullable
    @Setter(AccessLevel.PACKAGE)
    private NetChannel channel;

    public AbstractNetClient() {
        super(new NetClientConfig());
    }

    @Override
    public NetFuture<Void> boot() {
        var future = NetFuture.interpretFuture(new Bootstrap()
                .group(mainGroup())
                .channelFactory(NetworkNettyUtils::createChannelFactory)
                .handler(new NetChannelInitializer(handler()))
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.IP_TOS, 24)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .connect(config().hostname(), config().port()));

        future.whenCompleteSuccessfully(it -> {
            // now we can allow connections
            this.available(true);
            log.debug("The client successfully connected to the server!");
        });

        return future;
    }

    @Override
    public NetCompHandler handler() {
        return new NetClientHandler(this);
    }
}

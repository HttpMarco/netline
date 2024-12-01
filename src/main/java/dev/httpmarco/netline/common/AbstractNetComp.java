package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.NetConfig;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.channel.EventLoopGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public abstract class AbstractNetComp<C extends NetConfig> implements NetComp<C> {

    private static final int DEFAULT_MAIN_GROUP_THREADS = 0;

    private final EventLoopGroup mainGroup;
    private final C config;

    @Setter
    private boolean available = false;

    public AbstractNetComp(C config, int mainGroupThreads) {
        this.mainGroup = NetworkNettyUtils.createEventLoopGroup(mainGroupThreads);
        this.config = config;
    }

    public AbstractNetComp(C config) {
        this(config, DEFAULT_MAIN_GROUP_THREADS);
    }

    @Override
    public NetFuture<Void> close()  {
        this.available = false;
        // we deny incoming and outgoing packets
        return NetFuture.interpretFuture(this.mainGroup.shutdownGracefully());
    }
}

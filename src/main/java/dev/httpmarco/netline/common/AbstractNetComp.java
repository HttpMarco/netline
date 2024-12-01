package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.channel.EventLoopGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public abstract class AbstractNetComp implements NetComp {

    private static final int DEFAULT_MAIN_GROUP_THREADS = 0;

    private final EventLoopGroup mainGroup;
    @Setter
    private boolean available = false;

    public AbstractNetComp(int mainGroupThreads) {
        this.mainGroup = NetworkNettyUtils.createEventLoopGroup(mainGroupThreads);
    }

    public AbstractNetComp() {
        this(DEFAULT_MAIN_GROUP_THREADS);
    }

    @Override
    public NetFuture<Void> close()  {
        return NetFuture.interpretFuture(this.mainGroup.shutdownGracefully());
    }
}

package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.channel.EventLoopGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.IOException;

@Accessors(fluent = true)
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractNetComp implements NetComp {

    private static final int DEFAULT_MAIN_GROUP_THREADS = 0;

    private final EventLoopGroup mainGroup;

    public AbstractNetComp(int mainGroupThreads) {
        this.mainGroup = NetworkNettyUtils.createEventLoopGroup(mainGroupThreads);
    }

    public AbstractNetComp() {
        this(DEFAULT_MAIN_GROUP_THREADS);
    }

    @Override
    public void close() throws IOException {
        this.mainGroup.shutdownGracefully();
    }
}

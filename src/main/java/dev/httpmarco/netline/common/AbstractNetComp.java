package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.NetConfig;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.RequestChannelResponder;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.tracking.ChannelTracker;
import dev.httpmarco.netline.tracking.Tracking;
import dev.httpmarco.netline.tracking.TrackingPool;
import dev.httpmarco.netline.tracking.impl.DefaultTrackingPoolImpl;
import dev.httpmarco.netline.utils.NetFuture;
import dev.httpmarco.netline.utils.NetworkNettyUtils;
import io.netty5.channel.EventLoopGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
@Accessors(fluent = true)
@Getter
public abstract class AbstractNetComp<C extends NetConfig> implements NetComp<C> {

    private static final int DEFAULT_MAIN_GROUP_THREADS = 0;

    private final TrackingPool trackingPool = new DefaultTrackingPoolImpl();
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

    @Override
    public <A extends Tracking> UUID track(Class<A> tracking, ChannelTracker<A> tracker) {
        return trackingPool.put(tracking, tracker);
    }

    @Override
    public void untrack(UUID trackingId) {
        this.trackingPool.remove(trackingId);
    }

    @Override
    public void untrackAll() {
        this.trackingPool.clear();
    }

    @Override
    public void call(NetChannel channel, Tracking tracking) {
        this.trackingPool.callTracking(channel, tracking);
    }

    @Override
    public <R, T> void waitFor(RequestScheme<R, T> id, RequestChannelResponder<R, T> result) {
        this.trackingPool.responderOf(id, result);
    }
}

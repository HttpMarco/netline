package dev.httpmarco.netline.tracking;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.*;
import java.util.UUID;

public interface TrackingProvider extends ResponderProvider {

    /**
     * Track a specific tracking type with a tracker
     * @param tracking the tracking type
     * @param tracker the tracker
     * @param <A> the tracking type
     * @return the tracking id
     */
    default <A extends Tracking> UUID track(Class<A> tracking, Tracker<A> tracker) {
        return track(tracking, ((channel, it) -> tracker.track(it)));
    }

    /**
     * Track a specific tracking type with a tracker
     * @param tracking the tracking type
     * @param tracker the tracker with the channel property
     * @param <A> the tracking type
     * @return the tracking id
     */
    <A extends Tracking> UUID track(Class<A> tracking, ChannelTracker<A> tracker);

    /**
     * Call a tracking with a tracking object
     * @param channel the channel
     * @param tracking the tracking object
     */
    void call(NetChannel channel, Tracking tracking);

    /**
     * Untracked a specific tracking id
     * @param trackingId the tracking id
     */
    void untrack(UUID trackingId);

    /**
     * Untracked all tracking ids
     */
    void untrackAll();

    /**
     * Get the tracking pool
     * @return the tracking pool
     */
    TrackingPool trackingPool();

}

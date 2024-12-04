package dev.httpmarco.netline.tracking;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.RequestChannelResponder;
import dev.httpmarco.netline.request.RequestResponder;
import dev.httpmarco.netline.request.RequestScheme;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface TrackingProvider {

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

    /**
     * Wait for a specific request id
     * @param id the request id
     * @param result the result function
     * @param <T> the request type
     */
    default <R, T> void waitFor(RequestScheme<R, T> id, RequestResponder<R, T> result) {
        waitFor(id, (it, channel) -> result.respond(it));
    }

    /**
     * Wait for a specific request id
     * @param id the request id
     * @param result the result function
     * @param <R> the result type
     * @param <T> the request type
     */
    <R, T> void waitFor(RequestScheme<R, T> id, RequestChannelResponder<R, T> result);

    /**
     * Request a specific request id
     * @param id the request id
     * @return the request
     * @param <T> the request type
     */
    <T> NetRequest<T> request(@NotNull RequestScheme<?, T> id);

    /**
     * Call a request with a response
     * @param channel the channel
     * @param requestId the request id
     * @param response the response
     */
    void callRequest(NetChannel channel, UUID requestId, Packet response);

}

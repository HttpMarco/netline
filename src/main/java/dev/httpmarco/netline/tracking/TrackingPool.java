package dev.httpmarco.netline.tracking;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.RequestChannelResponder;
import dev.httpmarco.netline.request.RequestResponder;
import dev.httpmarco.netline.request.RequestScheme;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface TrackingPool {

    /**
     * Track a specific tracking type with a tracker
     * @param tracking the tracking type
     * @param tracker the tracker
     * @return the tracking id
     * @param <A> the tracking type
     */
    <A extends Tracking> UUID put(Class<A> tracking, ChannelTracker<A> tracker);

    /**
     * Clear all tracking and responders
     */
    void clear();

    /**
     * Call a tracking by id
     * @param netChannel the net channel
     * @param tracking the tracking
     */
    void callTracking(NetChannel netChannel, Tracking tracking);

    /**
     * Remove a current racking by id
     *
     * @param trackingId the tracking id
     */
    void remove(UUID trackingId);

    /**
     * Register a responder for a specific request scheme
     *
     * @param scheme    the request scheme
     * @param responder the responder
     * @param <R>       the request type
     * @param <A>       the response type
     */
    <R, A> void responderOf(RequestScheme<R, A> scheme, RequestChannelResponder<R, A> responder);

    /**
     * Register a responder for a specific request scheme, without the channel
     *
     * @param scheme    the request scheme
     * @param responder the responder
     * @param <R>       the request type
     * @param <A>       the response type
     */
    default <R, A> void responderOf(RequestScheme<R, A> scheme, RequestResponder<R, A> responder) {
        responderOf(scheme, (request, channel) -> responder.respond(request));
    }

    /**
     * Check if a responder is present for a specific request scheme
     *
     * @param requestID the request id
     * @return true if a responder is present
     */
    boolean responderPresent(String requestID);

    /**
     * Check if a responder is present for a specific request scheme
     *
     * @param schema the request scheme
     * @return true if a responder is present
     */
    default boolean responderPresent(@NotNull RequestScheme<?, ?> schema) {
        return responderPresent(schema.id());
    }

    /**
     * Respond to a specific request id
     * @param requestID the request id
     * @return the responder
     */
    RequestChannelResponder<?, ?> responder(String requestID);
}

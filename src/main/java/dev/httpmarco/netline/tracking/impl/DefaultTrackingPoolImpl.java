package dev.httpmarco.netline.tracking.impl;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.RequestChannelResponder;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.tracking.ChannelTracker;
import dev.httpmarco.netline.tracking.Tracking;
import dev.httpmarco.netline.tracking.TrackingPool;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
public final class DefaultTrackingPoolImpl implements TrackingPool {

    private final Map<Class<? extends Tracking>, Map<UUID, ChannelTracker<?>>> trackers = new HashMap<>();
    private final Map<RequestScheme<?, ?>, RequestChannelResponder<?, ?>> responders = new HashMap<>();

    @Override
    public <A extends Tracking> @NotNull UUID put(Class<A> tracking, ChannelTracker<A> tracker) {
        log.debug("Registering tracker for tracking: {}", tracking);
        var id = UUID.randomUUID();

        var currentTrackers = this.trackers.getOrDefault(tracking, new HashMap<>());
        currentTrackers.put(id, tracker);
        this.trackers.put(tracking, currentTrackers);

        return id;
    }

    @Override
    public void clear() {
        this.trackers.clear();
    }

    @Override
    public void callTracking(NetChannel channel, @NotNull Tracking tracking) {
        if(!trackers.containsKey(tracking.getClass())) {
            return;
        }
        log.debug("Call tracking: {} from {}. Found {} trackers", tracking.getClass().getSimpleName(), channel.id(), trackers.get(tracking.getClass()).size());
        trackers.get(tracking.getClass()).values().forEach(it -> it.trackWith(channel, tracking));
    }

    @Override
    public void remove(UUID trackingId) {
        this.trackers.values().forEach(map -> map.remove(trackingId));
    }

    @Override
    public <R, A> void responderOf(RequestScheme<R, A> scheme, RequestChannelResponder<R, A> responder) {
        log.debug("Registering responder for scheme: {}", scheme);
        this.responders.put(scheme, responder);
    }

    @Override
    public boolean responderPresent(String requestID) {
        return responders.keySet().stream().anyMatch(it -> it.id().equals(requestID));
    }

    @Override
    public RequestChannelResponder<?, ?> responder(String requestID) {
        return this.responders.get(responders.keySet().stream().filter(it -> it.id().equals(requestID)).findFirst().orElse(null));
    }

    @Override
    public int amountOfTracking(Class<? extends Tracking> tracking) {
        return this.trackers.containsKey(tracking) ? this.trackers.get(tracking).size() : 0;
    }
}

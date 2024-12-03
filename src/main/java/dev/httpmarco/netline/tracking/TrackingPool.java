package dev.httpmarco.netline.tracking;

import java.util.UUID;

public interface TrackingPool {

    <A extends Tracking> UUID put(Class<A> tracking, ChannelTracker<A> tracker);

    void clear();

    void remove(UUID trackingId);

}

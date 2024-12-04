package dev.httpmarco.netline.tracking;

import dev.httpmarco.netline.channel.NetChannel;

public interface ChannelTracker <A extends Tracking> {

    void track(NetChannel channel, A tracking);

    @SuppressWarnings("unchecked")
    default void trackWith(NetChannel channel, Tracking tracking) {
        this.track(channel, (A) tracking);
    }
}

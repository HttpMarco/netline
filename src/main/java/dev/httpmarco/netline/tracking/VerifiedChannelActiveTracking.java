package dev.httpmarco.netline.tracking;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.tracking.defaults.NetChannelTracking;

public final class VerifiedChannelActiveTracking extends NetChannelTracking {

    public VerifiedChannelActiveTracking(NetChannel channel) {
        super(channel);
    }
}

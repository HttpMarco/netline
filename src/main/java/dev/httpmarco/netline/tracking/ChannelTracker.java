package dev.httpmarco.netline.tracking;

import dev.httpmarco.netline.channel.NetChannel;

public interface ChannelTracker <A extends Tracking> {

    void track(NetChannel channel, A tracking);

}

package dev.httpmarco.netline;

import dev.httpmarco.netline.channel.NetChannel;
import io.netty5.channel.ChannelHandler;

public interface NetCompHandler extends ChannelHandler {

    /**
     * If the channel is not more usable
     * @param channel can be a custom channel
     */
    void netChannelClose(NetChannel channel);


    /**
     * Call if the channel is now open and a new connection
     * @param channel a new channel
     */
    void netChannelOpen(NetChannel channel);
}

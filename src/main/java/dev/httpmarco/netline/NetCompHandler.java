package dev.httpmarco.netline;

import io.netty5.channel.ChannelHandler;

public interface NetCompHandler extends ChannelHandler {

    void netChannelClose();

}

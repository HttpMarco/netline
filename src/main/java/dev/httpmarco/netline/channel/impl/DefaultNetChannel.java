package dev.httpmarco.netline.channel.impl;

import dev.httpmarco.netline.channel.common.AbstractNetChannel;
import io.netty5.channel.Channel;

public final class DefaultNetChannel extends AbstractNetChannel {

    public DefaultNetChannel(Channel channel) {
        super(channel);
    }
}

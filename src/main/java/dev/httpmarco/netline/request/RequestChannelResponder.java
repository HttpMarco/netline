package dev.httpmarco.netline.request;

import dev.httpmarco.netline.channel.NetChannel;

public interface RequestChannelResponder<T, R> {

    R respond(T request, NetChannel channel);

    @SuppressWarnings("unchecked")
    default R respondWith(Object request, NetChannel channel) {
        return respond((T) request, channel);
    }
}

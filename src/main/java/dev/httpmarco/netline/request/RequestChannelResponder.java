package dev.httpmarco.netline.request;

import dev.httpmarco.netline.channel.NetChannel;

public interface RequestChannelResponder<T, R> {

    R respond(T request, NetChannel channel);

}

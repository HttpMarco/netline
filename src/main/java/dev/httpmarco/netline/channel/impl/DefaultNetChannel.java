package dev.httpmarco.netline.channel.impl;

import dev.httpmarco.netline.channel.common.AbstractNetChannel;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.request.impl.Request;
import io.netty5.channel.Channel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class DefaultNetChannel extends AbstractNetChannel {

    public DefaultNetChannel(Channel channel) {
        super(channel);
    }

    @Contract("_ -> new")
    @Override
    public <R, A> @NotNull NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        return new Request<>(id, this);
    }
}

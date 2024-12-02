package dev.httpmarco.netline.channel.common;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.utils.NetFuture;
import io.netty5.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public abstract class AbstractNetChannel implements NetChannel {

    private final Channel channel;
    private final NetAddress clientAddress, serverAddress;

    @Override
    public boolean available() {
        return channel != null && channel.isActive() && clientAddress != null && serverAddress != null;
    }

    @Override
    public NetFuture<Void> close() {
        return NetFuture.interpretFuture(channel.close());
    }
}

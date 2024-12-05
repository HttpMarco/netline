package dev.httpmarco.netline.channel.common;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.utils.NetFuture;
import io.netty5.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Getter
@Log4j2
@Accessors(fluent = true)
public abstract class AbstractNetChannel implements NetChannel {

    private String id;
    private final Channel channel;
    private final NetAddress clientAddress;

    public AbstractNetChannel(@NotNull Channel channel) {
        this.channel = channel;
        this.clientAddress = NetAddress.fromAddress(channel.localAddress());
    }

    @Override
    public boolean available() {
        return channel != null && channel.isActive() && clientAddress != null && id != null;
    }

    @Override
    public void send(Packet packet) {
        if(!available()) {
            log.warn("Channel is not available to send packet.");
            return;
        }
        log.debug("Sending packet to channel: {}", packet);
        channel().writeAndFlush(packet);
    }

    @Override
    public NetFuture<Void> close() {
        return NetFuture.interpretFuture(channel.close());
    }

    @Override
    public void updateId(String id) {
        this.id = id;
        // todo call maybe on a valid connection
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractNetChannel netChannel && netChannel.channel.equals(channel);
    }
}

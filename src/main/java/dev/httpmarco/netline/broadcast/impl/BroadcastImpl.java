package dev.httpmarco.netline.broadcast.impl;

import dev.httpmarco.netline.broadcast.Broadcast;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;
import lombok.AllArgsConstructor;
import java.util.Collection;

@AllArgsConstructor
public class BroadcastImpl implements Broadcast {

    private final Collection<NetChannel> netChannels;

    @Override
    public void send(Packet packet) {
        for (NetChannel channel : netChannels) {
            channel.send(packet);
        }
    }
}

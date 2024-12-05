package dev.httpmarco.netline.client;

import dev.httpmarco.netline.broadcast.impl.BroadcastImpl;
import dev.httpmarco.netline.broadcast.packets.BroadcastPacket;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;

import java.util.List;

public final class NetClientBroadcast extends BroadcastImpl {

    public NetClientBroadcast(NetChannel channel) {
        super(List.of(channel));
    }

    @Override
    public void send(Packet packet) {
        super.send(new BroadcastPacket(packet));
    }
}

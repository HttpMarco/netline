package dev.httpmarco.netline.broadcast.packets;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.basic.HoldingPacket;

public final class BroadcastPacket extends HoldingPacket {

    public BroadcastPacket(Packet packet) {
        super(packet);
    }
}

package dev.httpmarco.netline.packet;

import dev.httpmarco.netline.tracking.Tracking;

public abstract class Packet implements Tracking {

    public abstract void read(PacketBuffer buffer);

    public abstract void write(PacketBuffer buffer);

}

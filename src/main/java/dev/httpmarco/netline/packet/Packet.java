package dev.httpmarco.netline.packet;

public abstract class Packet {

    public abstract void read(PacketBuffer buffer);

    public abstract void write(PacketBuffer buffer);

}

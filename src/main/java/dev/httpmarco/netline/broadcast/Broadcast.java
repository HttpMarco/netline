package dev.httpmarco.netline.broadcast;

import dev.httpmarco.netline.packet.Packet;

public interface Broadcast {

    void send(Packet packet);

    Broadcast toAll();

    //todo maybe a better naming
    Broadcast toMe();

}

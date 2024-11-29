package dev.httpmarco.netline.packet.basic;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class IntBasePacket extends Packet {

    private int value;

    @Override
    public void read(PacketBuffer buffer) {
        this.value = buffer.readInt();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(this.value);
    }
}

package dev.httpmarco.netline.packet.basic;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class StringPacket extends Packet {

    private String value;

    @Override
    public void read(@NotNull PacketBuffer buffer) {
        value = buffer.readString();
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(value);
    }
}

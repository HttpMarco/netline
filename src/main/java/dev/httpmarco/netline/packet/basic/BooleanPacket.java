package dev.httpmarco.netline.packet.basic;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class BooleanPacket extends Packet {

    private Boolean value;

    @Override
    public void read(@NotNull PacketBuffer buffer) {
        value = buffer.readBoolean();
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeBoolean(value);
    }
}

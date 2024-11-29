package dev.httpmarco.netline.packet.basic;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
public class BooleanBasePacket extends Packet {

    private boolean value;

    @Override
    public void read(@NotNull PacketBuffer packetBuffer) {
        this.value = packetBuffer.readBoolean();
    }

    @Override
    public void write(@NotNull PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(value);
    }
}

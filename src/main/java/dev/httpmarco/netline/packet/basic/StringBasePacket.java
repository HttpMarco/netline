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
public class StringBasePacket extends Packet {

    private String value;

    @Override
    public void read(@NotNull PacketBuffer buffer) {
        this.value = buffer.readString();
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(this.value);
    }
}

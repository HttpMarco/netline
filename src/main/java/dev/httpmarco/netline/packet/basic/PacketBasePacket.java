package dev.httpmarco.netline.packet.basic;

import dev.httpmarco.netline.codec.PacketAllocator;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class PacketBasePacket extends Packet {

    private Packet packet;

    @Override
    @SneakyThrows
    public void read(@NotNull PacketBuffer buffer) {
        var clazzName = buffer.readString();
        this.packet = (Packet) PacketAllocator.allocate(Class.forName(clazzName));
        this.packet.read(buffer);
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(this.packet.getClass().getName());
        packet.write(buffer);
    }
}

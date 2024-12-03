package dev.httpmarco.netline.request.packets;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketAllocator;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public final class ResponsePacket extends Packet {

    private UUID id;
    private Packet packet;

    @Override
    @SneakyThrows
    public void read(@NotNull PacketBuffer buffer) {
        id = buffer.readUniqueId();

        var className = buffer.readString();
        this.packet = (Packet) PacketAllocator.allocate(Class.forName(className));

        packet.read(buffer);
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeUniqueId(id);

        buffer.writeString(packet.getClass().getName());
        packet.write(buffer);
    }
}

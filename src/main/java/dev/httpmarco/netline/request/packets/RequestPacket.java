package dev.httpmarco.netline.request.packets;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketAllocator;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public final class RequestPacket extends Packet {

    private String requestId;
    private UUID id;
    private Packet request;

    @Override
    @SneakyThrows
    public void read(@NotNull PacketBuffer buffer) {
        this.requestId = buffer.readString();
        this.id = buffer.readUniqueId();

        var className = buffer.readString();
        this.request = (Packet) PacketAllocator.allocate(Class.forName(className));
        request.read(buffer);
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(this.requestId);
        buffer.writeUniqueId(this.id);

        buffer.writeString(this.request.getClass().getName());
        this.request.write(buffer);
    }
}

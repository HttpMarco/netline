package dev.httpmarco.netline.packet.basic;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketAllocator;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class HoldingPacket extends Packet {

    private Packet packet;

    @SneakyThrows
    @Override
    public void read(@NotNull PacketBuffer buffer) {
        var className = buffer.readString();

        this.packet = (Packet) PacketAllocator.allocate(Class.forName(className));

        if(this.packet == null) {
            log.warn("The packet cannot be deserialize. {}", className);
            return;
        }
        this.packet.read(buffer);
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeString(this.packet.getClass().getName());
        this.packet.write(buffer);
    }
}

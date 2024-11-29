package dev.httpmarco.netline.packet.cluster;

import dev.httpmarco.netline.cluster.node.NetNodeData;
import dev.httpmarco.netline.cluster.node.NetNodeState;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public final class NodeStateInfoPacket extends Packet {

    private NetNodeState state;
    private NetNodeData data;

    @Override
    public void read(@NotNull PacketBuffer buffer) {
        this.state = buffer.readEnum(NetNodeState.class);
        this.data = new NetNodeData(buffer.readLong());
    }

    @Override
    public void write(@NotNull PacketBuffer buffer) {
        buffer.writeEnum(this.state);
        buffer.writeLong(this.data.creationMillis());
    }
}

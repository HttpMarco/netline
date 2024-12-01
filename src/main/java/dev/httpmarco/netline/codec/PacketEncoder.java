package dev.httpmarco.netline.codec;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketBuffer;
import io.netty5.buffer.Buffer;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.MessageToByteEncoder;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Log4j2
public final class PacketEncoder extends MessageToByteEncoder<Packet> {

    private static final HashMap<Packet, PacketBuffer> tempPacketEncoderList = new HashMap<>();

    @Override
    protected @Nullable Buffer allocateBuffer(ChannelHandlerContext channelHandlerContext, @NotNull Packet packet) {
        try {
            var buffer = PacketBuffer.allocate();
            packet.write(buffer);
            tempPacketEncoderList.put(packet, buffer);

            // amount of chars in class name
            var bytes = Integer.BYTES +
                    // class name
                    packet.getClass().getName().getBytes(StandardCharsets.UTF_8).length +
                    // amount of bytes in buffer
                    Integer.BYTES +
                    // buffer content
                    buffer.getOrigin().readableBytes();

            return channelHandlerContext.bufferAllocator().allocate(bytes);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, Buffer out) {
        try {
            var origin = tempPacketEncoderList.get(packet).getOrigin();
            var buffer = new PacketBuffer(out);
            var readableBytes = origin.readableBytes();

            buffer.writeString(packet.getClass().getName());
            buffer.writeInt(readableBytes);

            origin.copyInto(0, out, out.writerOffset(), readableBytes);
            out.skipWritableBytes(readableBytes);
        } catch (Exception e) {
            log.error("Error while encoding packet {}", packet.getClass().getName());
        }
        tempPacketEncoderList.remove(packet);
    }
}

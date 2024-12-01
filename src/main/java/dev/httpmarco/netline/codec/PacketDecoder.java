package dev.httpmarco.netline.codec;

import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.PacketAllocator;
import dev.httpmarco.netline.packet.PacketBuffer;
import io.netty5.buffer.Buffer;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.ByteToMessageDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Buffer in) {
        var buffer = new PacketBuffer(in);

        var className = buffer.readString();

        try {
            var readableBytes = buffer.readInt();
            var content = new PacketBuffer(in.copy(in.readerOffset(), readableBytes, true));
            in.skipReadableBytes(readableBytes);

            var packet = (Packet) PacketAllocator.allocate(Class.forName(className));

            packet.read(content);

            buffer.resetBuffer();
            channelHandlerContext.fireChannelRead(packet);
        } catch (Exception e) {
            log.error("Error while decoding packet! Could not find packet class: {}", className);
        }
    }
}
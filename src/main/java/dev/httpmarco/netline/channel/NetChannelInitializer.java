package dev.httpmarco.netline.channel;

import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.codec.PacketDecoder;
import dev.httpmarco.netline.codec.PacketEncoder;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelInitializer;
import io.netty5.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty5.handler.codec.LengthFieldPrepender;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class NetChannelInitializer extends ChannelInitializer<Channel> {

    private final NetCompHandler handler;

    @Override
    protected void initChannel(@NotNull Channel channel) {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, Integer.BYTES, 0, Integer.BYTES))
                .addLast(new PacketDecoder())
                .addLast(new LengthFieldPrepender(Integer.BYTES))
                .addLast(new PacketEncoder())
                .addLast(handler);
    }
}
package dev.httpmarco.netline.channel;

import dev.httpmarco.netline.NetworkComponentHandler;
import dev.httpmarco.netline.codec.PacketDecoder;
import dev.httpmarco.netline.codec.PacketEncoder;
import io.netty5.channel.Channel;
import io.netty5.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty5.handler.codec.LengthFieldPrepender;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class NetChannelInitializer extends io.netty5.channel.ChannelInitializer<Channel> {

    private NetworkComponentHandler componentHandler;

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, Integer.BYTES, 0, Integer.BYTES))
                .addLast(new PacketDecoder())
                .addLast(new LengthFieldPrepender(Integer.BYTES))
                .addLast(new PacketEncoder())
                .addLast(componentHandler);
    }
}

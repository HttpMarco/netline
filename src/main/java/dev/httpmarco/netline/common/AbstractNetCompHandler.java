package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.channel.SimpleChannelInboundHandler;

public abstract class AbstractNetCompHandler extends SimpleChannelInboundHandler<Packet> implements NetCompHandler {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Packet msg)  {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // todo
        this.netChannelClose(null);
    }
}

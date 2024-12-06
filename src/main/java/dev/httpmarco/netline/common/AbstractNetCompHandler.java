package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.channel.impl.DefaultNetChannel;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.request.NetRequestPool;
import dev.httpmarco.netline.request.packets.BadRequestPacket;
import dev.httpmarco.netline.request.packets.RequestPacket;
import dev.httpmarco.netline.request.packets.ResponsePacket;
import dev.httpmarco.netline.request.utils.RespondPacketTranslator;
import io.netty5.channel.Channel;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.channel.SimpleChannelInboundHandler;
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
public abstract class AbstractNetCompHandler extends SimpleChannelInboundHandler<Packet> implements NetCompHandler {

    private NetComp<?> netComp;

    @Override
    @SneakyThrows
    public void messageReceived(@NotNull ChannelHandlerContext ctx, Packet packet) {
        var channel = findChannel(ctx.channel());

        if (channel == null) {
            ctx.close();
            return;
        }

        if (packet instanceof RequestPacket request) {
            // block not registered request
            if (!netComp.trackingPool().responderPresent(request.requestId())) {
                log.debug("No responder found for request id: {}", request.requestId());
                channel.send(new ResponsePacket(request.id(), new BadRequestPacket()));
                return;
            }

            var responder = this.netComp.trackingPool().responder(request.requestId());

            log.debug("Call responder for request id: {}", request.requestId());
            var respond = responder.respondWith(RespondPacketTranslator.translatePacket(request.request()), channel);
            var responsePacket = RespondPacketTranslator.translate(respond);

            // we send the response back to the client
            channel.send(new ResponsePacket(request.id(), responsePacket));
            return;
        }

        if(packet instanceof ResponsePacket response) {

            if(response.packet() instanceof BadRequestPacket) {
                log.warn("The request found no responder. Remove request from pool.");
                NetRequestPool.removeExceptionallyRequest(response.id());
                return;
            }

            channel.callRequest(channel, response.id(), response.packet());
            return;
        }

        log.debug("Received new packet: {} on channel {}", packet, channel.id());
        this.netComp.call(channel, packet);
    }

    @Override
    public void channelActive(@NotNull ChannelHandlerContext ctx) {
        /// todo: generate by comp factory
        var newChannel = new DefaultNetChannel(ctx.channel());

        // we save the channel without an id
        this.netChannelOpen(newChannel);
    }

    @Override
    public void channelInactive(@NotNull ChannelHandlerContext ctx) {
        this.netChannelClose(findChannel(ctx.channel()));
    }

    public abstract NetChannel findChannel(Channel channel);

    @Override
    public void channelExceptionCaught(ChannelHandlerContext ctx, @NotNull Throwable cause) {
        log.error("Error while netty communication", cause);
    }
}

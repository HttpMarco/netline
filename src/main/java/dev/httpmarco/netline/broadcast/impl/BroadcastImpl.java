package dev.httpmarco.netline.broadcast.impl;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.broadcast.Broadcast;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
public class BroadcastImpl implements Broadcast {

    private final NetComp<?> owner;
    private boolean notifyHimself = false;

    private final Collection<NetChannel> possibleChannels;
    private final Set<NetChannel> broadcastChannels = new HashSet<>();

    @Override
    public void send(Packet packet) {

        if(broadcastChannels.isEmpty()) {
            log.info("The broadcast pool is empty! No packet send...");
            return;
        }

        log.info("Broadcasting packet {} to {} channels: {}", packet.getClass().getSimpleName(), broadcastChannels.size(), String.join(", ", this.broadcastChannels.stream().map(NetChannel::id).toList()));
        for (var channel : broadcastChannels) {
            channel.send(packet);
        }

        if(notifyHimself) {
            // the client can be also the channel, here we have the chance for a not null channel
            if(owner instanceof NetChannel channel) {
                this.owner.call(channel, packet);
                return;
            }
            // server has no own channel
            this.owner.call(null, packet);
        }
    }

    @Override
    public Broadcast toAll() {
        this.broadcastChannels.addAll(possibleChannels);
        return this;
    }

    @Override
    public Broadcast toMe() {
        this.notifyHimself = true;
        return this;
    }
}

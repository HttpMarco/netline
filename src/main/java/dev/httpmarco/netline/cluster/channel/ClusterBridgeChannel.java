package dev.httpmarco.netline.cluster.channel;

import dev.httpmarco.netline.channel.common.AbstractNetChannel;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.RequestScheme;
import io.netty5.channel.Channel;
import org.jetbrains.annotations.NotNull;

/**
 * Channel connect to the parent node and redirect all packets to the bridge
 * This channel is not a local node instance
 */
public final class ClusterBridgeChannel extends AbstractNetChannel {

    public ClusterBridgeChannel(@NotNull Channel channel) {
        super(channel);
    }

    @Override
    public <R, A> NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        // todo
        return null;
    }
}

package dev.httpmarco.netline.client;

import dev.httpmarco.netline.NetChannel;
import dev.httpmarco.netline.NetCompHandler;
import dev.httpmarco.netline.packet.common.BroadcastPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class NetClientHandler extends NetCompHandler {

    private static final Logger log = LogManager.getLogger(NetClientHandler.class);
    private final NetClient netClient;

    public NetClientHandler(NetClient netClient) {
        super(netClient);

        this.netClient = netClient;
    }

    @Override
    public void closeChannel(NetChannel netChannel) {
        this.netClient.channel(null);
        if(netClient.bindProcess() != null) {
            log.error("Deny the bind process, because the channel close before the bind process complete -> whitelist or security policy");
            // deny the bind process, because the channel close before the bind process complete -> whitelist
            this.netClient.state(NetClientState.FAILED);
            netClient.bindProcess().complete(null);
        } else {
            this.netClient.state(NetClientState.CLOSED);
        }
    }

    @Override
    public void handshakeChannel(NetChannel netChannel) {

    }

    @Override
    public void broadcastDefinition(NetChannel incoming, @NotNull BroadcastPacket packet) {
        netClient.callTracking(incoming, packet.packet());
    }
}

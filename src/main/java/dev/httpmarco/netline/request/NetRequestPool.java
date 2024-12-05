package dev.httpmarco.netline.request;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.basic.BooleanPacket;
import dev.httpmarco.netline.request.impl.Request;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
@UtilityClass
public final class NetRequestPool {

    private final Map<UUID, Request<?, ?>> requests = new HashMap<>();

    public void put(Request<?, ?> request) {
        requests.put(request.id(), request);
    }

    public void applyRequest(UUID id, NetChannel channel, Packet response) {
        if (!requests.containsKey(id)) {
            log.warn("No request found for id: {}", id);
            return;
        }

        var request = requests.get(id);
        requests.remove(id);

        if (response.getClass().equals(request.requestScheme().responseType())) {
            request.complete(response);
        } else if (response instanceof BooleanPacket booleanPacket) {
            request.complete(booleanPacket.value());
        }

        //todo other values
    }
}

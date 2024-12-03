package dev.httpmarco.netline.request.utils;

import dev.httpmarco.netline.excpetions.SimpleResponderTypeException;
import dev.httpmarco.netline.packet.Packet;
import dev.httpmarco.netline.packet.basic.BooleanPacket;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class RespondPacketTranslator {

    public Packet translate(Object value) throws SimpleResponderTypeException {

        if (value instanceof Packet packet) {
            return packet;
        }

        if (value instanceof Boolean booleanValue) {
            return new BooleanPacket(booleanValue);
        }

        throw new SimpleResponderTypeException(value);
    }
}

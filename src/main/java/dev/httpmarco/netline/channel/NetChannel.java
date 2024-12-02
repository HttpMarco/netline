package dev.httpmarco.netline.channel;

import dev.httpmarco.netline.Available;
import dev.httpmarco.netline.Closeable;
import dev.httpmarco.netline.NetAddress;
import io.netty5.channel.Channel;

public interface NetChannel extends Available, Closeable {

    Channel channel();

    /**
     * Get the client side host information
     * @return NetAddress with port and host
     */
    NetAddress clientAddress();

    /**
     * Get the server side host information
     * @return NetAddress with port and host
     */
    NetAddress serverAddress();

}

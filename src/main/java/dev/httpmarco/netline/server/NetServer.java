package dev.httpmarco.netline.server;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.NetConfig;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.tracking.TrackingProvider;

import java.util.Collection;

public interface NetServer<T extends NetConfig> extends NetComp<T>, TrackingProvider {

    /**
     * Get the amount of all connected clients. Ignore availability
     * @return the amount
     */
    int amountOfClients();

    /**
     * Return a list of all clients. Ignore availability
     * @return a collection of channels
     */
    Collection<NetChannel> allClients();

    /**
     * Return a list of only available clients.
     * @return a collection of channel
     */
    Collection<NetChannel> availableClients();

}
package dev.httpmarco.netline.server;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.channel.NetChannel;

public interface NetServer extends NetComp<NetServerConfig> {

    int amountOfClients();

}

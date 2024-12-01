package dev.httpmarco.netline.server;

import dev.httpmarco.netline.NetComp;

public interface NetServer extends NetComp<NetServerConfig> {

    int amountOfClients();

}

package dev.httpmarco.netline.server;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.tracking.TrackingProvider;

public interface NetServer extends NetComp<NetServerConfig>, TrackingProvider, NetServerClientHandler {

}
package dev.httpmarco.netline.server.impl;

import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.server.NetServerConfig;
import dev.httpmarco.netline.server.common.AbstractDynamicNetServer;

public final class NetServerImpl extends AbstractDynamicNetServer<NetServerConfig> implements NetServer {

    public NetServerImpl() {
        super(new NetServerConfig());
    }

}

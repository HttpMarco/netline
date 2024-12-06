package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.cluster.NetNodeConfig;
import dev.httpmarco.netline.server.common.AbstractDynamicNetServer;

public final class LocalNetNode extends AbstractDynamicNetServer<NetNodeConfig> {

    public LocalNetNode(NetNodeConfig config) {
        super(config);
    }
}

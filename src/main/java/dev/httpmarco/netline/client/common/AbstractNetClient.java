package dev.httpmarco.netline.client.common;

import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.client.NetClientConfig;
import dev.httpmarco.netline.common.AbstractNetComp;

public abstract class AbstractNetClient extends AbstractNetComp<NetClientConfig> implements NetClient {

    public AbstractNetClient() {
        super(new NetClientConfig());
    }
}

package dev.httpmarco.netline.server.impl;

import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.server.NetServerConfig;
import dev.httpmarco.netline.server.common.AbstractNetServer;
import org.jetbrains.annotations.NotNull;

public final class NetServerImpl extends AbstractNetServer<NetServerConfig> {

    public NetServerImpl() {
        super(new NetServerConfig());
    }

    @Override
    public <R, A> NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        return null;
    }
}

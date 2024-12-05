package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.NetConfig;
import dev.httpmarco.netline.cluster.NetNodeConfig;
import dev.httpmarco.netline.request.NetRequest;
import dev.httpmarco.netline.request.RequestScheme;
import dev.httpmarco.netline.server.common.AbstractNetServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LocalNetNode extends AbstractNetServer<NetNodeConfig> implements NetComp<NetNodeConfig> {

    public LocalNetNode(NetConfig config) {
        super(new NetNodeConfig());
    }

    @Contract(pure = true)
    @Override
    public <R, A> @Nullable NetRequest<R, A> request(@NotNull RequestScheme<R, A> id) {
        return null;
    }
}

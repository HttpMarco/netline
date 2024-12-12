package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.cluster.NetNode;
import dev.httpmarco.netline.cluster.NetNodeConfig;
import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.cluster.NetNodeState;
import dev.httpmarco.netline.server.common.AbstractDynamicNetServer;
import dev.httpmarco.netline.utils.NetFuture;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@Accessors(fluent = true)
public final class LocalNetNode<D extends NetNodeData> extends AbstractDynamicNetServer<NetNodeConfig> implements NetNode<D> {

    private NetNodeState state = NetNodeState.UNAVAILABLE;


    public LocalNetNode() {
        super(new NetNodeConfig());
    }

    @Override
    public D data() {
        return null;
    }

    @Override
    public void updateData() {

    }

    @Override
    public long lastDataUpdate() {
        return 0;
    }

    @Override
    public @NotNull NetFuture<Void> boot() {
        var future = super.boot();
        future.whenCompleteSuccessfully(unused -> state = NetNodeState.READY);
        return future;
    }
}

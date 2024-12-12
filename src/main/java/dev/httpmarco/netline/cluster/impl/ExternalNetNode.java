package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.cluster.NetNode;
import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.cluster.NetNodeState;

public final class ExternalNetNode<D extends NetNodeData> implements NetNode<D> {

    private NetNodeState state = NetNodeState.UNAVAILABLE;

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
    public NetNodeState state() {
        return null;
    }
}

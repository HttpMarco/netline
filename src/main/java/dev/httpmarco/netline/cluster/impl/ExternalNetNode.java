package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.cluster.NetNode;
import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.cluster.NetNodeState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class ExternalNetNode<D extends NetNodeData> implements NetNode<D> {

    private final NetAddress address;
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

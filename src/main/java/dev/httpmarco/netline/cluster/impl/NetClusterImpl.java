package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.NetNode;
import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.utils.NetFuture;

public final class NetClusterImpl<D extends NetNodeData> implements NetCluster<D> {

    private NetNode headNode;

    public NetClusterImpl() {
        // todo‚
    }

    @Override
    public NetNode headNode() {
        return null;
    }

    @Override
    public void searchHeadNode() {

    }

    @Override
    public NetNode localNode() {
        return null;
    }

    @Override
    public void registerNode(NetAddress address) {

    }

    @Override
    public void unregisterNode(NetNode node) {

    }

    @Override
    public boolean available() {
        return false;
    }

    @Override
    public NetFuture<Void> close() {
        return null;
    }
}

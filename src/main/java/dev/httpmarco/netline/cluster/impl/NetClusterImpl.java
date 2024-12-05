package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.NetNode;
import dev.httpmarco.netline.utils.NetFuture;

public final class NetClusterImpl implements NetCluster {

    @Override
    public NetNode headNode() {
        return null;
    }

    @Override
    public void searchHeadNode() {

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

package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.cluster.*;
import dev.httpmarco.netline.utils.NetFuture;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class NetClusterImpl<D extends NetNodeData> implements NetCluster<D> {

    private NetNode<D> headNode;
    private final LocalNetNode<D> localNode;

    public NetClusterImpl() {
        // todo‚
        this.localNode = new LocalNetNode<>();
    }

    @Override
    public void searchHeadNode() {

    }

    @Override
    public void registerNode(NetAddress address) {

    }

    @Override
    public void unregisterNode(NetNode<D> node) {

    }

    @Override
    public boolean available() {
        return this.localNode.available();
    }

    @Override
    public NetFuture<Void> close() {
        return null;
    }

    @Override
    public NetFuture<Void> boot() {
        var future = new NetFuture<Void>();

        //todo add other nodes
        //todo search the new head node
        localNode.boot().whenComplete((unused, throwable) -> {
            if(throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                future.complete();
            }
        });

        return future;
    }
}

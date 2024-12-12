package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.NetAddress;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.cluster.*;
import dev.httpmarco.netline.utils.NetFuture;
import lombok.Getter;
import lombok.experimental.Accessors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Getter
@Accessors(fluent = true)
public class NetClusterImpl<D extends NetNodeData> implements NetCluster<D> {

    private NetNode<D> headNode;
    private final LocalNetNode<D> localNode;

    private final Collection<NetChannel> clients = new ArrayList<>();

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

    @Override
    public int amountOfClients() {
        return this.clients.size();
    }

    @Override
    public Collection<NetChannel> allClients() {
        return Collections.unmodifiableCollection(this.clients);
    }
}

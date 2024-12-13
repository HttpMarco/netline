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
import java.util.Comparator;

@Getter
@Accessors(fluent = true)
public class NetClusterImpl<D extends NetNodeData> implements NetCluster<D> {

    private NetNode<D> headNode;
    private final LocalNetNode<D> localNode;

    private final Collection<NetNode<D>> nodes = new ArrayList<>();
    private final Collection<NetChannel> clients = new ArrayList<>();

    public NetClusterImpl() {
        // todo‚
        this.localNode = new LocalNetNode<>();
    }

    @Override
    public void searchHeadNode() {
        if(this.nodes.isEmpty()) {
            this.headNode = this.localNode;
            return;
        }
        // Find the head node by the starting time.
        // The oldest node are the head node
        this.headNode = this.nodes.stream()
                .min(Comparator.comparingLong(value -> value.data().initializationTime()))
                .orElseThrow();
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
        var future = new NetFuture<Void>();

        // todo find a better not duplicated logic
        this.localNode.close().whenComplete((unused, throwable) -> {
            if(throwable != null) {
                future.completeExceptionally(throwable);
            } else {
                future.complete();
            }
        });

        return future;
    }

    @Override
    public NetFuture<Void> boot() {
        var future = new NetFuture<Void>();

        this.searchHeadNode();

        //todo add other nodes
        //todo search the new head node
        // todo find a better not duplicated logic
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

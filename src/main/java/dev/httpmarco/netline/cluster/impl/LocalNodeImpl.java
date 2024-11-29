package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.node.AbstractNetNode;
import dev.httpmarco.netline.cluster.node.NetNodeData;
import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.server.NetServerState;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public final class LocalNodeImpl extends AbstractNetNode {

    private final NetServer server;

    public LocalNodeImpl(@NonNull NetCluster cluster, @NonNull NetNodeData data) {
        super(cluster, data);

        this.server = Net.line().server();
    }

    @Override
    public @NonNull CompletableFuture<Void> boot() {
        return server.boot();
    }

    @Override
    public boolean ready() {
       return this.server.state() == NetServerState.BOOTED;
    }

    @Override
    public void bootSync() {
        this.server.bootSync();
    }
}


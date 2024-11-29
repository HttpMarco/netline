package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.node.NetNode;
import dev.httpmarco.netline.cluster.node.NetNodeData;
import dev.httpmarco.netline.cluster.node.NetNodeState;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Getter
@Accessors(fluent = true)
public final class NetClusterImpl implements NetCluster {

    private static final Logger log = LogManager.getLogger(NetClusterImpl.class);
    private final LocalNodeImpl localNode;
    private final List<NetNode> nodes = new LinkedList<>();
    private NetNode headNode;

    public NetClusterImpl() {
        // todo find a config
        this.localNode = new LocalNodeImpl(this, new NetNodeData("nodeA"));
    }

    @Override
    public void searchHeadNode() {
        this.headNode = this.nodes.stream().filter(NetNode::ready).min(Comparator.comparingLong(it -> it.data().creationMillis())).orElseThrow();
    }

    @Override
    public @NonNull List<NetNode> availableNodes() {
        return this.nodes.stream().filter(NetNode::ready).toList();
    }

    @Override
    public @Nullable NetNode findNode(String nodeId) {
        return this.nodes.stream().filter(it -> it.data().id().equals(nodeId)).findFirst().orElse(null);
    }

    @Override
    public void registerNode(NetNodeData data) {
        this.nodes.add(new ExternalNodeImpl(this, data));
    }

    @Override
    public void unregisterNode(NetNodeData data) {

    }


    @Override
    public @NotNull CompletableFuture<Void> boot() {
        var future = new CompletableFuture<Void>();

        log.debug("Boot cluster. Start local node server...");
        // start local node
        this.localNode.boot().whenComplete((unused, throwable) -> {

            if(throwable != null) {
                log.error("Failed to boot local node server", throwable);
                future.completeExceptionally(throwable);
                return;
            }

            log.debug("Local node server successfully booted.");

            // read all nodes
            //this.nodes.forEach(NetNode::boot);

            // add self node in the node pool
            this.nodes.add(this.localNode);
            this.searchHeadNode();
            log.debug("Select head node: {}", this.headNode.data().id());


            this.localNode.state(NetNodeState.READY);
            future.complete(null);
        });
        return future;
    }

    @Contract(pure = true)
    @Override
    public @Nullable CompletableFuture<Void> close() {
        return null;
    }

    @Override
    @SneakyThrows
    public void closeSync() {
        this.close().get(5, TimeUnit.SECONDS);
    }
}

package dev.httpmarco.netline.cluster;

import dev.httpmarco.netline.Available;
import dev.httpmarco.netline.Closeable;
import dev.httpmarco.netline.NetAddress;

public interface NetCluster<D extends NetNodeData> extends Available, Closeable {

    /**
     * Get the head node of the cluster.
     * @return the head node of the cluster.
     */
    NetNode<D> headNode();

    /**
     * Search for the head node of the cluster.
     */
    void searchHeadNode();

    /**
     * Get the local runtime representing node
     * @return the node
     */
    NetNode<D> localNode();

    /**
     * Add a new node into the networking cluster
     * @param address the new node address
     */
    void registerNode(NetAddress address);

    /**
     * Unregister a connected node
     * @param node the current cluster node
     */
    void unregisterNode(NetNode<D> node);

}

package dev.httpmarco.netline.cluster;

import dev.httpmarco.netline.Available;
import dev.httpmarco.netline.Closeable;

public interface NetCluster extends Available, Closeable {

    /**
     * Get the head node of the cluster.
     * @return the head node of the cluster.
     */
    NetNode headNode();

    /**
     * Search for the head node of the cluster.
     */
    void searchHeadNode();

}

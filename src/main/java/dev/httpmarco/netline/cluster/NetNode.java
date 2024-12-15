package dev.httpmarco.netline.cluster;

import dev.httpmarco.netline.NetAddress;

public interface NetNode<D extends NetNodeData> {

    /**
     * Get the current data of the node
     * @return the data
     */
    D data();

    /**
     * Request data update process
     */
    void updateData();

    /**
     * Get the timestamp of the last update
     * @return the time millis
     */
    long lastDataUpdate();

    /**
     * Current state of the node
     * @return the state
     */
    NetNodeState state();

    /**
     * Get the address of the node
     * @return the address
     */
    NetAddress address();

}

package dev.httpmarco.netline.cluster.impl;

import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.cluster.common.AbstractNetNode;

public final class ExternalNetNode extends AbstractNetNode {
    @Override
    public NetNodeData data() {
        return null;
    }

    @Override
    public void updateData() {

    }

    @Override
    public long lastDataUpdate() {
        return 0;
    }
}

package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.cluster.NetNodeState;
import org.junit.jupiter.api.*;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("6 - Node test")
public final class NetClusterTest {

    private final NetCluster<NetNodeData> cluster = Net.line().cluster();

    @BeforeEach
    public void beforeEach() {
        this.cluster.boot().sync();
        assert this.cluster.available();
        assert this.cluster.localNode().state() == NetNodeState.READY;
    }

    @Test
    @Order(1)
    @DisplayName("6.1 Boot first node")
    public void handleClusterBoot() {

    }
}
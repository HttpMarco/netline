package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.node.NetNodeState;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("6 - Node cluster test")
public final class NodeTest {

    private static NetCluster netClusterPartA;

    @BeforeAll
    public static void beforeHandling() {
        netClusterPartA = Net.line().cluster();
    }

    @Test
    @Order(1)
    @DisplayName("6.1 Start first node")
    public void testState() {
        netClusterPartA.bootSync();
        assert netClusterPartA.localNode().state() == NetNodeState.READY;
    }

    @Test
    @Order(2)
    @DisplayName("6.2 Start second node")
    public void testSecondNode() {

    }

}

package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.utils.NetworkTestUtils;
import org.junit.jupiter.api.*;
import java.util.concurrent.ExecutionException;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("1 - Server-Client Binding Test")
public class NetServerTest {

    private NetServer server;

    @BeforeEach
    public void init() {
        this.server = Net.line().server();
        this.server.boot().sync();
    }

    @Test
    @Order(1)
    @DisplayName("1.1 Bind server on default port 9090")
    public void defaultBoot() {
        assert this.server.available();
    }

    @Test
    @Order(2)
    @DisplayName("1.2 Close running server")
    public void testCloseProcess() {
        this.server.close().sync();
        assert !this.server.available();
    }

    @Test
    @Order(3)
    @DisplayName("1.3 Bind server on an used port")
    public void testAlreadyPortUse() {
        Assertions.assertThrows(ExecutionException.class, () -> Net.line().server().boot().sync());
    }

    @Test
    @Order(4)
    @DisplayName("1.4 Ping server")
    public void pingServer() {
        assert NetworkTestUtils.isServerReachable(server.config().hostname(), server.config().port());
    }

    @Test
    @Order(5)
    @DisplayName("1.5 Use custom hostname and port configuration")
    public void testConfigurationUse() {
        var server = Net.line().server();

        server.config().hostname("0.0.0.0");
        server.config().port(9093);

        server.boot().sync();
        assert server.available();
        assert NetworkTestUtils.isServerReachable(server.config().hostname(), server.config().port());
    }

    @AfterEach
    public void closeServer() {
        this.server.close().sync();
    }
}

package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.server.NetServer;
import org.junit.jupiter.api.*;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("2 - NetClient test")
public final class NetClientTest {

    private NetServer server;
    private NetClient client;

    @BeforeEach
    public void init() {
        this.client = Net.line().client();
        this.server = Net.line().server();

        this.server.boot().sync();
    }

    @Test
    @Order(1)
    @DisplayName("2.1 Start a client to an invalid server")
    public void defaultBoot() {
        this.server.close().sync();

        // no server is started
        Assertions.assertThrows(Exception.class, () -> this.client.boot().sync());
        assert !this.client.available();
    }

    @Test
    @Order(2)
    @DisplayName("2.2 First Connection")
    public void firstConnectionTest() {
        assert server.available();

        this.client.boot().sync();
        assert this.client.available();
        assert server.amountOfClients() == 1;

        server.close().sync();
        assert !server.available();
        assert server.amountOfClients() == 0;
        assert !this.client.available();
    }

    @Test
    @Order(3)
    @DisplayName("2.3 Clean client disconnect")
    public void testClientDisconnect() {
        this.client.boot().sync();
        assert this.client.available();
        this.client.close().sync();
        assert !client.available();
    }

    @AfterEach
    public void closeBoth() {
        this.client.close().sync();
        this.server.close().sync();
    }
}

package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.packets.EmptyTestPacket;
import dev.httpmarco.netline.server.NetServer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("3 - Tracking test")
public class TrackingTest {

    private NetServer server;
    private NetClient client;

    @BeforeEach
    public void before() {
        this.client = Net.line().client();
        this.server = Net.line().server();

        this.server.boot().sync();
    }

    @Test
    @Order(1)
    @DisplayName("3.1 Register a new simple tracking")
    public void registerTracking() {
        this.client.track(EmptyTestPacket.class, (channel, tracking) -> {});
        // todo check amount of empty test packet
    }

    @Test
    @Order(2)
    @DisplayName("3.2 Register tracking and call this locally")
    public void waitForLocalTracking() {
        var requestResponse = new AtomicBoolean();
        this.client.track(EmptyTestPacket.class, (channel, tracking) -> requestResponse.set(true));
        this.client.call(null, new EmptyTestPacket());
        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilTrue(requestResponse);
        assert requestResponse.get();
    }

    @Test
    @Order(3)
    @DisplayName("3.3 Register tracking and call this from client to server")
    public void waitForRemoteTracking() {
        this.client.boot().sync();

        var requestResponse = new AtomicBoolean();
        this.server.track(EmptyTestPacket.class, (channel, tracking) -> requestResponse.set(true));
        client.send(new EmptyTestPacket());
        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilTrue(requestResponse);
        assert requestResponse.get();
    }

    @AfterEach
    public void after() {
        this.client.close().sync();
        this.server.close().sync();
    }
}

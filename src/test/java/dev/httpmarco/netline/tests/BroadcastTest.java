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
@DisplayName("4 - Broadcast test")
public class BroadcastTest {

    private NetServer server;
    private NetClient client;

    @BeforeEach
    public void init() {
        this.client = Net.line().client();
        this.server = Net.line().server();

        this.server.boot().sync();
        this.client.boot().sync();
    }

    @Test
    @Order(1)
    @DisplayName("4.1 Broadcast from server to all clients")
    public void serverBroadcast() {
        var result = new AtomicBoolean();
        client.track(EmptyTestPacket.class, (channel, tracking) -> result.set(true));
        server.broadcast().send(new EmptyTestPacket());

        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilTrue(result);
        assert result.get();
    }

    @Test
    @Order(2)
    @DisplayName("4.2 Broadcast from client to server")
    public void clientBroadcast() {
        var result = new AtomicBoolean();
        server.track(EmptyTestPacket.class, (channel, tracking) -> result.set(true));
        client.broadcast().send(new EmptyTestPacket());

        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilTrue(result);
        assert result.get();
    }

    @Test
    @Order(3)
    @DisplayName("4.3 Broadcast from client over the server to another client")
    public void clientAllBroadcast() {
        var result = new AtomicBoolean();

        var externalClient = Net.line().client();
        externalClient.boot().sync();

        externalClient.track(EmptyTestPacket.class, (channel, tracking) -> result.set(true));
        client.broadcast().send(new EmptyTestPacket());

        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilTrue(result);
        externalClient.close().sync();
        assert result.get();
    }

    @AfterEach
    public void afterAll() {
        this.client.close().sync();
        this.server.close().sync();
    }
}

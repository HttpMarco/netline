package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.packets.EmptyTestPacket;
import org.junit.jupiter.api.*;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("3 - Tracking test")
public class TrackingTest {

    private NetClient client;

    @BeforeEach
    public void before() {
        this.client = Net.line().client();
    }

    @Test
    public void registerTracking() {
        this.client.track(EmptyTestPacket.class, (channel, tracking) -> {});
        // todo check amount of empty test packet
    }

}

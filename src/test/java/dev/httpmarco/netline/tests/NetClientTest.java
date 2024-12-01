package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.client.NetClient;
import org.junit.jupiter.api.*;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("2 - NetClient test")
public class NetClientTest {

    private NetClient client;

    @BeforeEach
    public void init() {
        this.client = Net.line().client();
    }

    @Test
    @Order(1)
    @DisplayName("1.1 Start a client to an invalid server")
    public void defaultBoot() {
        assert this.client.available();
    }

}

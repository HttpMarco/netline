package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.server.NetServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NetServerTest {

    private NetServer server;

    @BeforeEach
    public void init() {
        this.server = Net.line().server();
        this.server.boot().sync();
    }

    @Test
    public void defaultBoot() {
        assert this.server.available();
    }
}

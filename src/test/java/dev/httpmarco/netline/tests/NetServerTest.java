package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import org.junit.jupiter.api.Test;

public class NetServerTest {

    @Test
    public void defaultBoot() {
        var server = Net.line().server();

        server.boot().sync();


    }

}

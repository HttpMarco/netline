package dev.httpmarco.netline;

import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.server.NetServer;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@Getter
@Accessors(fluent = true)
public abstract class BaseTest {

    private NetServer server;
    private NetClient client;

    @BeforeEach
    public void init() {
        this.client = Net.line().client();
        this.server = Net.line().server();

        this.server.boot().sync();
        this.client.boot().sync();
    }

    @AfterEach
    public void afterAll() {
        this.client().close().sync();
        this.server().close().sync();
    }
}

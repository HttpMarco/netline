package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.Net;
import dev.httpmarco.netline.client.NetClientState;
import dev.httpmarco.netline.content.TestSecurityProvider;
import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.tracking.BlacklistTracking;
import dev.httpmarco.netline.tracking.WhitelistTracking;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
// todo github pipeline fails on this test
@DisplayName("3 - White-/Blacklist test")
public final class SecurityListTest {

    private static NetServer server;

    @BeforeAll
    public static void createTestSetup() {
        server = Net.line().server();
        server.bootSync();
    }


    @Test
    @Order(1)
    @DisplayName("3.1 Test hostname whitelist")
    public void testHostNameWhitelist() throws InterruptedException {
        var result = new AtomicBoolean(false);
        var whitelistedIp = "127.0.0.2";

        server.config(it -> it.whitelist().add(whitelistedIp));
        server.track(WhitelistTracking.class, (channel, it) -> result.set(true));

        var testClient = Net.line().client();
        testClient.bootSync();

        if(testClient.state() == NetClientState.CONNECTED) {
            testClient.closeSync();
            assert false;
        }

        Awaitility.await().atMost(7, TimeUnit.SECONDS).untilTrue(result);

        // we reset the whitelist for the next test
        server.config(it -> it.whitelist().remove(whitelistedIp));
        assert result.get();
    }

    @Test
    @Order(2)
    @DisplayName("3.2 Test hostname blacklist")
    public void testBlacklist() throws InterruptedException {
        var result = new AtomicBoolean(false);
        var blockedIp = "127.0.0.1";

        server.config(it -> it.blacklist().add(blockedIp));

        server.track(BlacklistTracking.class, (channel, it)  -> result.set(true));

        var testClient = Net.line().client();
        testClient.bootSync();

        if(testClient.state() == NetClientState.CONNECTED) {
            testClient.closeSync();
            assert false;
        }

        Awaitility.await().atMost(5, TimeUnit.SECONDS).untilTrue(result);

        // we reset the whitelist for the next test
        server.config(it -> it.blacklist().remove(blockedIp));
        assert result.get();
    }

    @Test
    @Order(3)
    @DisplayName("3.3 Test custom security policy")
    public void testSecurityPolicy() {
        // register the security policy
        server.withSecurityPolicy(new TestSecurityProvider());

        var testClientA = Net.line().client();
        testClientA.config(netClientConfig -> netClientConfig.id("testA"));

        var testClientB = Net.line().client();
        testClientB.config(netClientConfig -> netClientConfig.id("testB"));

        testClientA.bootSync();
        assert testClientA.state() == NetClientState.CONNECTED;
        assert server.clients().size() == 1;
        testClientA.closeSync();

        testClientB.bootSync();
        assert testClientB.state() == NetClientState.FAILED;
        assert server.clients().isEmpty();
        testClientB.closeSync();
    }

    @AfterAll
    public static void closeAll() {
        server.closeSync();
    }
}

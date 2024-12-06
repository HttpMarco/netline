package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.BaseTest;
import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.request.NetRequestPool;
import dev.httpmarco.netline.request.RequestScheme;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("5 - Request/Response test")
public final class RequestTest extends BaseTest {

    private static final RequestScheme<String, Boolean> requestScheme = new RequestScheme<>("test", String.class, Boolean.class);

    @Test
    @Order(1)
    @DisplayName("5.1 Register a new async request")
    public void registerAsyncRequest() {
        this.client().request(requestScheme).send("abc");

        // we have an async request, the id must be registered
        assert NetRequestPool.amountOfRequests() == 1;
    }

    @Test
    @Order(2)
    @DisplayName("5.2 Register a new sync request, with no responder")
    public void registerSyncRequest() {
        Assertions.assertThrows(Exception.class, () -> this.client().request(requestScheme).send("abc").sync());

        // because we have a sync request, the id must be already removed
        assert NetRequestPool.amountOfRequests() == 0;
    }

    @Test
    @Order(3)
    @DisplayName("5.3 Register a new async request, with a responder")
    public void registerAsyncRequestWithResponder() {
        var requestResponse = new AtomicBoolean();
        this.server().waitFor(requestScheme, (request) -> true);
        this.client().request(requestScheme).send("abc").whenComplete((it, throwable) -> requestResponse.set(it));
        Awaitility.await().atMost(3, TimeUnit.SECONDS).untilTrue(requestResponse);
        assert requestResponse.get();
    }

    @Test
    @Order(4)
    @DisplayName("5.4 Register a new sync request, with a responder")
    public void registerSyncRequestWithResponder() {
        this.server().waitFor(requestScheme, (request) -> true);
        var result = this.client().request(requestScheme).send("abc").sync();
        assert result;
    }

    @Test
    @Order(4)
    @DisplayName("5.5 Register a new sync request from server to client")
    public void registerNewChannelServerSideRequest() {
        this.client().waitFor(requestScheme, (request) -> true);

        assert !this.server().allClients().isEmpty();

        for (var client : this.server().allClients()) {
            assert client.request(requestScheme).send("abc").sync();
            break;
        }
    }
}

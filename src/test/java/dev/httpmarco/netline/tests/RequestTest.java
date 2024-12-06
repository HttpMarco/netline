package dev.httpmarco.netline.tests;

import dev.httpmarco.netline.BaseTest;
import dev.httpmarco.netline.request.NetRequestPool;
import dev.httpmarco.netline.request.RequestScheme;
import org.junit.jupiter.api.*;

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
}

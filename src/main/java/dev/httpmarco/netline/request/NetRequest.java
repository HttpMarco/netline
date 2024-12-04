package dev.httpmarco.netline.request;

import dev.httpmarco.netline.utils.NetFuture;

import java.util.UUID;

/**
 * Build a new request to be sent to the server.
 * @param <R> the response type
 */
public interface NetRequest<R, A> {

    /**
     * The unique id of the request.
     * @return the id
     */
    UUID id();

    /**
     * Send the request to the server.
     * @return a future that will be completed when the response is received
     */
    NetFuture<A> send(R request);

}

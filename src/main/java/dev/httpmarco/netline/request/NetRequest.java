package dev.httpmarco.netline.request;

import dev.httpmarco.netline.utils.NetFuture;

import java.util.UUID;

/**
 * Build a new request to be sent to the server.
 * @param <R> the response type
 */
public interface NetRequest<R> {

    /**
     * The unique id of the request.
     * @return the id
     */
    UUID id();

    /**
     * Mapper for response.
     * @return the response mapper
     */
    NetRequestMapper<R> mapResponse();

    /**
     * Send the request to the server.
     * @return a future that will be completed when the response is received
     */
    NetFuture<R> send();

}

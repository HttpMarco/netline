package dev.httpmarco.netline.request;

public interface ResponderProvider {

    /**
     * Wait for a specific request id
     * @param id the request id
     * @param result the result function
     * @param <T> the request type
     */
    default <R, T> void waitFor(RequestScheme<R, T> id, RequestResponder<R, T> result) {
        waitFor(id, (it, channel) -> result.respond(it));
    }

    /**
     * Wait for a specific request id
     * @param id the request id
     * @param result the result function
     * @param <R> the result type
     * @param <T> the request type
     */
    <R, T> void waitFor(RequestScheme<R, T> id, RequestChannelResponder<R, T> result);

}
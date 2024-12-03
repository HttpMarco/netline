package dev.httpmarco.netline.request;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record RequestScheme<R, A>(String id, Class<R> requestType, Class<A> responseType) {

    public static <RT, AT> @NotNull RequestScheme<RT, AT> from(String id, Class<RT> requestType, Class<AT> responseType) {
        return new RequestScheme<>(id, requestType, responseType);
    }

    // I'd for the client authentication request
    public static RequestScheme<String, Boolean> CLIENT_AUTH = new RequestScheme<>("CLIENT_AUTH", String.class, Boolean.class);


    @Override
    public boolean equals(Object obj) {
        return obj instanceof RequestScheme<?, ?> requestScheme && requestScheme.id.equals(this.id);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "RequestScheme{" +
                "id='" + id + '\'' +
                ", requestType=" + requestType +
                ", responseType=" + responseType +
                '}';
    }
}
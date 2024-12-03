package dev.httpmarco.netline.request;

public record RequestID(String id) {

    public static void from(String id) {
        new RequestID(id);
    }

    // I'd for the client authentication request
    public static RequestID CLIENT_AUTH = new RequestID("CLIENT_AUTH");

}
package dev.httpmarco.netline.request;

public interface RequestResponder<T, R> {

    R respond(T request);

}

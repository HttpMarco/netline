package dev.httpmarco.netline.request;

import org.jetbrains.annotations.NotNull;

public interface NetRequestProvider {

    <T> NetRequest<T> request(@NotNull RequestScheme<?, T> id);

}

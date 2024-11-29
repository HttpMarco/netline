package dev.httpmarco.netline;

import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.server.NetServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Net {

    @Contract(value = " -> new", pure = true)
    public static @NotNull Net line() {
        return new Net();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetClient client() {
        return new NetClient();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetServer server() {
        return new NetServer();
    }

}
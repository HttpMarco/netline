package dev.httpmarco.netline;

import dev.httpmarco.netline.client.NetClient;
import dev.httpmarco.netline.client.impl.NetClientImpl;
import dev.httpmarco.netline.cluster.NetCluster;
import dev.httpmarco.netline.cluster.NetNodeData;
import dev.httpmarco.netline.cluster.impl.NetClusterImpl;
import dev.httpmarco.netline.server.NetServer;
import dev.httpmarco.netline.server.impl.NetServerImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Net {

    @Contract(" -> new")
    @SuppressWarnings("java:S2440")
    public static @NotNull Net line() {
        return new Net();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetServer server() {
        return new NetServerImpl();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetClient client() {
        return new NetClientImpl();
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull NetCluster<NetNodeData> cluster() {
        return new NetClusterImpl<>();
    }
}
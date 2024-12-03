package dev.httpmarco.netline;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public record NetAddress(String hostname, int port) {

    @Contract("_ -> new")
    public static @NotNull NetAddress fromAddress(@NotNull SocketAddress address) {
        if (address instanceof InetSocketAddress inetSocketAddress) {
            return new NetAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } else {
            throw new IllegalArgumentException("Unsupported address type: " + address.getClass().getName());
        }
    }
}

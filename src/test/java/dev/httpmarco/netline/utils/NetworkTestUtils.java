package dev.httpmarco.netline.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@UtilityClass
public final class NetworkTestUtils {

    public boolean isServerReachable(String host, int port) {
        try (var socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

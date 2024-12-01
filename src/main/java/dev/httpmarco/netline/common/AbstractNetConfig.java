package dev.httpmarco.netline.common;

import dev.httpmarco.netline.NetConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractNetConfig implements NetConfig {

    private String hostname = "localhost";
    private int port = 9091;

    @Override
    public void hostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public void port(int port) {
        this.port = port;
    }
}

package dev.httpmarco.netline;

public interface NetConfig {

    String hostname();

    int port();

    void hostname(String hostname);

    void port(int port);

}

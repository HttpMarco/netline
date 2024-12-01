package dev.httpmarco.netline.client;

import dev.httpmarco.netline.client.common.AbstractNetClient;
import dev.httpmarco.netline.common.AbstractNetCompHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class NetClientHandler extends AbstractNetCompHandler {

    private final AbstractNetClient netClient;

    @Override
    public void netChannelClose() {
        System.out.println("polo");
        netClient.available(false);
    }
}

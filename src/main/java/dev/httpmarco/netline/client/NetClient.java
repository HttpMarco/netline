package dev.httpmarco.netline.client;

import dev.httpmarco.netline.NetComp;
import dev.httpmarco.netline.channel.NetChannel;
import org.jetbrains.annotations.Nullable;

public interface NetClient extends NetComp<NetClientConfig> {

    @Nullable NetChannel channel();

}

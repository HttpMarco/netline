package dev.httpmarco.netline.channel;

import dev.httpmarco.netline.Available;
import dev.httpmarco.netline.Closeable;
import io.netty5.channel.Channel;

public interface NetChannel extends Available, Closeable {

    Channel channel();

}

package dev.httpmarco.netline;

import dev.httpmarco.netline.channel.NetChannel;
import dev.httpmarco.netline.utils.NetFuture;
import java.io.Closeable;

/**
 * A network component.
 */
public interface NetComp<C extends NetConfig> extends Available {

    /**
     * Boot the component.
     * @return a future that will be completed when the component is booted.
     */
    NetFuture<Void> boot();

    /**
     * Close the component.
     * @return a future that will be completed when the component is closed.
     */
    NetFuture<Void> close();

    /**
     * Get the configuration of the component.
     * @return the configuration of the component.
     */
    C config();

    /**
     * Get the handler of the component.
     * @return the handler of the component.
     */
    NetCompHandler handler();

}
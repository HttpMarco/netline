package dev.httpmarco.netline;

import dev.httpmarco.netline.broadcast.Broadcastable;
import dev.httpmarco.netline.tracking.TrackingProvider;
import dev.httpmarco.netline.utils.NetFuture;

/**
 * A network component.
 */
public interface NetComp<C extends NetConfig> extends Available, TrackingProvider, Closeable, Broadcastable {

    /**
     * Boot the component.
     * @return a future that will be completed when the component is booted.
     */
    NetFuture<Void> boot();

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
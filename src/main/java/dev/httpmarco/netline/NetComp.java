package dev.httpmarco.netline;

import dev.httpmarco.netline.utils.NetFuture;
import java.io.Closeable;

/**
 * A network component.
 */
public interface NetComp {

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
     * Check if the component is available. Packets can be sent and received .
     * @return true if the component is available, false otherwise.
     */
    boolean available();

}

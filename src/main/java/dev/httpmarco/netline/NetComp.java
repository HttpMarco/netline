package dev.httpmarco.netline;

import dev.httpmarco.netline.utils.NetFuture;
import java.io.Closeable;

/**
 * A network component.
 */
public interface NetComp extends Closeable {

    /**
     * Boot the component.
     * @return a future that will be completed when the component is booted.
     */
    NetFuture<Void> boot();

}

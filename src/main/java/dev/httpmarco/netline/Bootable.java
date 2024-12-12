package dev.httpmarco.netline;

import dev.httpmarco.netline.utils.NetFuture;

public interface Bootable {

    /**
     * Boot the component.
     * @return a future that will be completed when the component is booted.
     */
    NetFuture<Void> boot();

}

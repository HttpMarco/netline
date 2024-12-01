package dev.httpmarco.netline;

import dev.httpmarco.netline.utils.NetFuture;

public interface Closeable {

    NetFuture<Void> close();

}

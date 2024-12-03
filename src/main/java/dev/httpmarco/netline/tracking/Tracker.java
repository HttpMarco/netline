package dev.httpmarco.netline.tracking;

public interface Tracker<A extends Tracking> {

    void track(A tracking);

}

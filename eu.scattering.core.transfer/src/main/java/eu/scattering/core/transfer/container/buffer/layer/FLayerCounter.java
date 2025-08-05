package eu.scattering.core.transfer.container.buffer.layer;

import eu.scattering.core.transfer.container.buffer.Buffer;

public interface FLayerCounter extends Buffer<FLayerCounter>, Iterable<Integer> {

    int get(int layer);
    int inc(int layer);
    int set(int layer, int value);

    void add(FLayerCounter... fLayer);
    void avg(FLayerCounter... fLayer);
    void max(FLayerCounter... fLayer);

    double addSelf();
    double avgSelf();
    double maxSelf();

    int size();

    void reset();

    boolean isEmpty();
    boolean isZeroLayerOnly();

    // -------------------------------------------------------------------------------------------------

    default int get() {

        return get(0);
    }

    default int inc() {

        return inc(0);
    }

    default int set(int value) {

        return set(0, value);
    }
}

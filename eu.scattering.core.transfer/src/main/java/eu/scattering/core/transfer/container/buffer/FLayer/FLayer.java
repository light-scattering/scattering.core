package eu.scattering.core.transfer.container.buffer.FLayer;

import eu.scattering.core.transfer.container.buffer.Buffer;

public interface FLayer extends Buffer<FLayer>, Iterable<Integer> {

    int get(int layer);
    int inc(int layer);
    int set(int layer, int value);

    void add(FLayerDef... fLayer);
    void average(FLayerDef... fLayer);
    void max(FLayerDef... fLayer);

    double addSelf();
    double averageSelf();
    double maxSelf();

    int size();

    void reset();

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

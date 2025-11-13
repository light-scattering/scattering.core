package eu.scattering.core.design.storage.layer;

import eu.scattering.core.design.storage.Storage;

public interface FLayer extends Storage<FLayer>, Iterable<Integer> {

    int get(int layer);

    int inc(int layer);
    int set(int layer, int value);

    void incGroup(int minLayer, int maxLayer);
    void setGroup(int minLayer, int maxLayer, int value);

    void add(FLayer... fLayer);
    void avg(FLayer... fLayer);
    void max(FLayer... fLayer);

    double addSelf();
    double avgSelf();
    double maxSelf();

    void assertSize(int size);

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

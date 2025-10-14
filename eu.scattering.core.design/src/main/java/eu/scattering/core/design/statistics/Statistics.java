package eu.scattering.core.design.statistics;

import org.json.JSONObject;

public interface Statistics<T> {

    T copy();
    int size();
    void clear();

    boolean isEqual(T fStat);
    boolean isEqualData(T fStat);

    JSONObject toJSON();
}

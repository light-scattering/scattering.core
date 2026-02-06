package eu.scattering.core.design.storage.box.variants;

import eu.scattering.core.design.storage.Storage;

public interface FBoxDouble extends Storage<FBoxDouble> {

    double getValue();
    void setValue(double value);
}

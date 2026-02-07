package eu.scattering.core.design.storage.box.variant;

import eu.scattering.core.design.storage.Storage;

public interface FBoxString extends Storage {

    String getValue();
    void setValue(String value);
}

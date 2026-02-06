package eu.scattering.core.design.storage.box.variants;

import eu.scattering.core.design.storage.Storage;

public interface FBoxString extends Storage<FBoxString> {

    String getValue();
    void setValue(String value);
}

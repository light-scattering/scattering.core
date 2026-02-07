package eu.scattering.core.design.storage.transfer.pair.variants;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.single.variants.FPos4D;

public interface FPairPos4D extends Storage {

    FPos4D getPosA();
    FPos4D getPosB();
}

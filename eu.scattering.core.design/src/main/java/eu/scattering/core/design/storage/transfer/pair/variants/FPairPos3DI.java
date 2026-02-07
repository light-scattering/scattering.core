package eu.scattering.core.design.storage.transfer.pair.variants;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3DI;

public interface FPairPos3DI extends Storage {

    FPos3DI getPosA();
    FPos3DI getPosB();
}

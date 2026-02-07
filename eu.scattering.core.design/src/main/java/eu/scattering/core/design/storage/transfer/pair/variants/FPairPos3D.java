package eu.scattering.core.design.storage.transfer.pair.variants;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

public interface FPairPos3D extends Storage {

    FPos3D getPosA();
    FPos3D getPosB();
}

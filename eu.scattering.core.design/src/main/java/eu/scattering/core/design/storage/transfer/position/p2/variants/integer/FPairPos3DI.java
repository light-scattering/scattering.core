package eu.scattering.core.design.storage.transfer.position.p2.variants.integer;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.position.p1.variants.integer.FPos3DI;

public interface FPairPos3DI extends Storage {

    FPos3DI getPosA();
    FPos3DI getPosB();
}

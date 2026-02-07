package eu.scattering.core.design.storage.transfer.position.p2.variants;

import eu.scattering.core.design.storage.Storage;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos2D;

public interface FPairPos2D extends Storage {

    FPos2D getPosA();
    FPos2D getPosB();
}

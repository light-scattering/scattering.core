package eu.scattering.core.design;

import eu.scattering.core.design.annotations.Unsafe;
import eu.scattering.core.design.elements.CoreFactory;
import eu.scattering.core.design.elements.engine.random.FRandom;
import eu.scattering.core.design.helpers.HelperFactory;

public interface Factory extends CoreFactory, HelperFactory {

    double getJitter();
    Factory setJitter(double jitter);

    @Unsafe("Interferes with the internal structure of the object")
    FRandom getInternalFRandom();
    @Unsafe("Interferes with the internal structure of the object")
    void setInternalFRandom(FRandom fRandom);
}

package eu.scattering.core.design;

import eu.scattering.core.design.core.CoreFactory;
import eu.scattering.core.design.helper.HelperFactory;

public interface Factory extends CoreFactory, HelperFactory {

    double getJitter();
    Factory setJitter(double jitter);
}

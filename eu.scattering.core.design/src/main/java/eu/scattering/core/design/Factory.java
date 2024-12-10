package eu.scattering.core.design;

import eu.scattering.core.design.core.CoreFactory;
import eu.scattering.core.design.support.SupportFactory;

public interface Factory extends CoreFactory, SupportFactory {

    double getJitter();
    Factory setJitter(double jitter);
}

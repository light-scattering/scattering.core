package eu.scattering.core.design;

import eu.scattering.core.design.main.MainFactory;
import eu.scattering.core.design.support.SupportFactory;

public interface Factory extends MainFactory, SupportFactory {

    double getJitter();
    Factory setJitter(double jitter);
}

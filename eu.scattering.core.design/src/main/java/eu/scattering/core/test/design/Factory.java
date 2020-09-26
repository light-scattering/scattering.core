package eu.scattering.core.test.design;

import eu.scattering.core.test.design.main.MainFactory;
import eu.scattering.core.test.design.support.SupportFactory;

public interface Factory extends MainFactory, SupportFactory {

    double getJitter();
    Factory setJitter(double jitter);
}

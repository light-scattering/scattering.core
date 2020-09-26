package eu.scattering.core.test.design.support;

import eu.scattering.core.test.design.support.helper.AngleHelper;
import eu.scattering.core.test.design.support.helper.RandomHelper;
import eu.scattering.core.test.design.support.helper.SignalHelper;

public interface SupportFactory {

    AngleHelper getHelperAngle();

    SignalHelper getHelperSignal();

    RandomHelper getHelperRandom();
}

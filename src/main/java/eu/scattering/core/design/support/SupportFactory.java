package eu.scattering.core.design.support;

import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.design.support.helper.SignalHelper;

public interface SupportFactory {

    AngleHelper getHelperAngle();

    SignalHelper getHelperSignal();

    RandomHelper getRandomHelper();
}

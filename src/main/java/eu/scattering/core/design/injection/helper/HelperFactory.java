package eu.scattering.core.design.injection.helper;

import eu.scattering.core.design.support.AngleHelper;
import eu.scattering.core.design.support.SignalHelper;

public interface HelperFactory {

    AngleHelper forAngle();

    SignalHelper forSignal();
}

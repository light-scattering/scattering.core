package eu.scattering.core.implementation.support;

import eu.scattering.core.design.support.SupportFactory;
import eu.scattering.core.design.support.helper.AngleHelper;
import eu.scattering.core.design.support.helper.SignalHelper;
import eu.scattering.core.implementation.support.helper.AngleHelperDefault;
import eu.scattering.core.implementation.support.helper.SignalHelperDefault;

public final class SupportFactoryDefault implements SupportFactory {

    private SupportFactoryDefault() { }

    private static AngleHelper angleHelper;
    private static SignalHelper signalHelper;

    @Override
    public AngleHelper forAngle() {

        if (angleHelper == null) {
            angleHelper = getAngleHelper();
        }

        return angleHelper;
    }

    @Override
    public SignalHelper forSignal() {

        if (signalHelper == null) {
            signalHelper = getSignalHelper();
        }

        return signalHelper;
    }

    private static AngleHelper getAngleHelper() {

        return new AngleHelperDefault();
    }

    private static SignalHelper getSignalHelper() {

        return new SignalHelperDefault();
    }

}

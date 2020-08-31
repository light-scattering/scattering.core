package eu.scattering.core.implementation.injection.helper;

import eu.scattering.core.design.injection.helper.HelperFactory;
import eu.scattering.core.design.support.AngleHelper;
import eu.scattering.core.design.support.SignalHelper;
import eu.scattering.core.implementation.support.AngleHelperDefault;
import eu.scattering.core.implementation.support.SignalHelperDefault;

public final class HelperFactoryDefault implements HelperFactory {

    private HelperFactoryDefault() { }

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

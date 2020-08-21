package eu.scattering.core.injection;

import eu.scattering.core.design.support.AngleHelper;
import eu.scattering.core.design.support.SignalHelper;
import eu.scattering.core.implementation.support.AngleHelperDefault;
import eu.scattering.core.implementation.support.SignalHelperDefault;

public final class Helper {

    private Helper() { }

    private static AngleHelper angleHelper;
    private static SignalHelper signalHelper;

    public static AngleHelper forAngle() {

        if (angleHelper == null) {
            angleHelper = getAngleHelper();
        }

        return angleHelper;
    }

    public static SignalHelper forSignal() {

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

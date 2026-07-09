package eu.scattering.core.design.component.aggregate.config.df;

import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public interface FConfigDC {

    RadiusOfGyration getRadiusOfGyration();
    FConfigDC setRadiusOfGyration(RadiusOfGyration type);

    double getScalingFactor();
    FConfigDC setScalingFactor(double factor);

    boolean isRestricted();
    FConfigDC setRestricted(boolean restricted);

    double getWindowRatio();
    FConfigDC setWindowRatio(double ratio);

    // -------------------------------------------------------------------------------------------------

    enum Preset {
        FULL, RESTRICTED
    }
}

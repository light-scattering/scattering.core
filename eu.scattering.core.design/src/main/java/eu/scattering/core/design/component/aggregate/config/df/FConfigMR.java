package eu.scattering.core.design.component.aggregate.config.df;

import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public interface FConfigMR {

    RadiusOfGyration getRadiusOfGyration();
    FConfigMR setRadiusOfGyration(RadiusOfGyration type);

    double getScalingFactor();
    FConfigMR setScalingFactor(double factor);

    boolean isRestricted();
    FConfigMR setRestricted(boolean restricted);

    double getWindowRatio();
    FConfigMR setWindowRatio(double ratio);

    // -------------------------------------------------------------------------------------------------

    enum Preset {
        FULL, RESTRICTED
    }
}

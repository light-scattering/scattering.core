package eu.scattering.core.design.component.aggregate.monitor.common;

import eu.scattering.core.design.component.aggregate.monitor.common.module.FMonitorRadiusOfGyration;
import eu.scattering.core.design.type.RadiusOfGyration;

public interface FMonitorCommonFactory {

    FMonitorRadiusOfGyration getFMonitorRadiusOfGyration(int skip, RadiusOfGyration type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorRadiusOfGyration getFMonitorRadiusOfGyration(RadiusOfGyration type) {

        return getFMonitorRadiusOfGyration(-1, type);
    }
}

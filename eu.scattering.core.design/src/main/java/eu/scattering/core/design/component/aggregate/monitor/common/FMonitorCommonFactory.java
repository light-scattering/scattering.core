package eu.scattering.core.design.component.aggregate.monitor.common;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.common.module.FMonitorRadiusOfGyration;

public interface FMonitorCommonFactory {

    FMonitorRadiusOfGyration getFMonitorRadiusOfGyration(int skip, FAggregate.RadiusOfGyration type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorRadiusOfGyration getFMonitorRadiusOfGyration(FAggregate.RadiusOfGyration type) {

        return getFMonitorRadiusOfGyration(-1, type);
    }
}

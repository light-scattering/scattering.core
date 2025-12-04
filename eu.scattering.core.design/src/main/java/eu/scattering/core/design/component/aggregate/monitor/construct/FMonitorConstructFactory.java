package eu.scattering.core.design.component.aggregate.monitor.construct;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.construct.dedicate.FMonitorRadiusOfGyration;

public interface FMonitorConstructFactory {

    FMonitorRadiusOfGyration getFMonitorRadiusOfGyration(int skip, FAggregate.RadiusOfGyration type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorRadiusOfGyration getFMonitorRadiusOfGyration(FAggregate.RadiusOfGyration type) {

        return getFMonitorRadiusOfGyration(-1, type);
    }
}

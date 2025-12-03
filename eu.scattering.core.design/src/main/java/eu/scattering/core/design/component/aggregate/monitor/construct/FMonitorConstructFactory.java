package eu.scattering.core.design.component.aggregate.monitor.construct;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FMonitorConstructFactory {

    FMonitorConstruct getFMonitorRoG(int skip, FAggregate.RadiusOfGyration type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorConstruct getFMonitorRoG(FAggregate.RadiusOfGyration type) {

        return getFMonitorRoG(-1, type);
    }
}

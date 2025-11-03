package eu.scattering.core.design.component.aggregate.monitor.construct;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FMonitorConstructFactory {

    FMonitorConstruct getFMonitorRoG(int skip, FAggregate.RoG type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorConstruct getFMonitorRoG(FAggregate.RoG type) {

        return getFMonitorRoG(-1, type);
    }
}

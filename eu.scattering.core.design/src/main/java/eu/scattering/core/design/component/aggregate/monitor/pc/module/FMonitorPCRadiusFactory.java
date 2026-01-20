package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.type.Center;

public interface FMonitorPCRadiusFactory {

    FMonitorPCRadius radius(int skip, Center type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorPCRadius radius(Center type) {

        return radius(-1, type);
    }
}

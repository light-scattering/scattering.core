package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public interface FMonitorPCRadiusOfGyrationFactory {

    FMonitorPCRadiusOfGyration radiusOfGyration(RadiusOfGyration type, int skip);

    // -------------------------------------------------------------------------------------------------

    default FMonitorPCRadiusOfGyration radiusOfGyration(RadiusOfGyration type) {

        return radiusOfGyration(type, -1);
    }
}

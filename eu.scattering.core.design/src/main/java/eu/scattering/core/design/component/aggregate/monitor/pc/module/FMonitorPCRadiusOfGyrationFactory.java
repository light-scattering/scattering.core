package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.utility.type.RadiusOfGyration;

public interface FMonitorPCRadiusOfGyrationFactory {

    FMonitorPCRadiusOfGyration radiusOfGyration(int skip, RadiusOfGyration type);

    // -------------------------------------------------------------------------------------------------

    default FMonitorPCRadiusOfGyration radiusOfGyration(RadiusOfGyration type) {

        return radiusOfGyration(-1, type);
    }
}

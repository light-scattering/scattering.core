package eu.scattering.core.design.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCCFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPCFactoryContext;

public interface FMonitorFactoryContext {

    FMonitorPCFactoryContext pc();
    FMonitorCCFactoryContext cc();
}

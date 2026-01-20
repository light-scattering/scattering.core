package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPC;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMonitorPCRadius extends FMonitorPC {

    @Modificator
    FPlot getRefFPlot();
}

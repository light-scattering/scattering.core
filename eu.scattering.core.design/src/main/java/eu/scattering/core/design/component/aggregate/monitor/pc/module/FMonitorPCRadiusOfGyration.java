package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.FMonitor;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMonitorPCRadiusOfGyration extends FMonitor {

    double getPowerLawDimension();

    @Modificator
    FPlot getRefFPlot();
}

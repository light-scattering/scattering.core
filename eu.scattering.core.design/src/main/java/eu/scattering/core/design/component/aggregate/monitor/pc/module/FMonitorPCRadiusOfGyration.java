package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPC;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMonitorPCRadiusOfGyration extends FMonitorPC {

    double getPowerLawDimension();

    @Modificator
    FPlot getRefFPlot();
}

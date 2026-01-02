package eu.scattering.core.design.component.aggregate.monitor.common.module;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.FMonitor;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMonitorRadiusOfGyration extends FMonitor {

    double getPowerLawDimension();

    @Modificator
    FPlot getRefFPlot();
}

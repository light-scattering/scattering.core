package eu.scattering.core.design.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCC;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;

public interface FMonitorCCRadiusOfGyration extends FMonitorCC {

    double getPowerLawDimension();

    @Modificator
    FPlotBar getRefFPlotBar();
}

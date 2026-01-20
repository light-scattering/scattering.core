package eu.scattering.core.design.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCC;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;

public interface FMonitorCCRadius extends FMonitorCC {

    @Modificator
    FPlotBar getRefFPlotBar();
}

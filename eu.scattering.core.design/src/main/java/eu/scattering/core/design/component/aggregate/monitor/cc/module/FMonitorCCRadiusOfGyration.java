package eu.scattering.core.design.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCC;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;

import java.util.function.Function;

public interface FMonitorCCRadiusOfGyration extends FMonitorCC {

    double getR2();
    double getPowerLawDimension();

    String getFPlotVisual(Function<FStat, Double> function);
    String getFPlotBarVisual();

    @Modificator
    FPlotBar getRefFPlotBar();
}

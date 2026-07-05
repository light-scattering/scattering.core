package eu.scattering.core.design.component.aggregate.monitor.cc;

import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;

import java.util.function.Function;

public interface FMonitorCCAspectSave {

    String toChart(FMonitorCCRadiusOfGyration monitor);
    String toChart(FMonitorCCRadiusOfGyration monitor, Function<FStat, Double> function);
}

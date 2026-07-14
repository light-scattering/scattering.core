package eu.scattering.core.design.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.cc.FMetaCCPL;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCC;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;

public interface FMonitorCCRadiusOfGyration extends FMonitorCC {

    double getPowerLawDimension(FConfigCCPL config, FMetaCCPL meta);
    double getPowerLawDimension(FConfigCCPL.Preset preset, FMetaCCPL meta);

    @Modificator
    FPlotBar getRefFPlotBar();

    // -------------------------------------------------------------------------------------------------

    default double getPowerLawDimension(FConfigCCPL config) {

        return getPowerLawDimension(config, null);
    }

    default double getPowerLawDimension(FConfigCCPL.Preset preset) {

        return getPowerLawDimension(preset, null);
    }
}

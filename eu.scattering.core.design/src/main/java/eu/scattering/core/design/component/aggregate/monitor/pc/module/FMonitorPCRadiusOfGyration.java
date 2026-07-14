package eu.scattering.core.design.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;
import eu.scattering.core.design.component.aggregate.meta.df.kinetic.pc.FMetaPCPL;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPC;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

public interface FMonitorPCRadiusOfGyration extends FMonitorPC {

    double getPowerLawDimension(FConfigPCPL config, FMetaPCPL meta);
    double getPowerLawDimension(FConfigPCPL.Preset preset, FMetaPCPL meta);

    @Modificator
    FPlot getRefFPlot();

    // -------------------------------------------------------------------------------------------------

    default double getPowerLawDimension(FConfigPCPL config) {

        return getPowerLawDimension(config, null);
    }

    default double getPowerLawDimension(FConfigPCPL.Preset preset) {

        return getPowerLawDimension(preset, null);
    }
}

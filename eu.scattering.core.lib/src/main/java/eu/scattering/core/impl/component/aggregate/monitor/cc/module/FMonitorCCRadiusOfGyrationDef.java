package eu.scattering.core.impl.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.utility.type.RadiusOfGyration;

public class FMonitorCCRadiusOfGyrationDef implements FMonitorCCRadiusOfGyration {
    private final RadiusOfGyration type;
    private final FPlotBar fPlotBar;

    private FMonitorCCRadiusOfGyrationDef(ScatFactory factory, RadiusOfGyration type) {

        this.type = type;
        this.fPlotBar = factory.getFPlotBar();

        this.fPlotBar.setName("Radius of gyration");
    }

    public static FMonitorCCRadiusOfGyration create(ScatFactory factory, RadiusOfGyration type) {

        return new FMonitorCCRadiusOfGyrationDef(factory, type);
    }

    @Override
    public FPlotBar getRefFPlotBar() {

        this.fPlotBar.sortX(true);

        return this.fPlotBar;
    }

    @Override
    public void accept(FAggregate aggA, FAggregate aggB) {

        if (aggA == null || aggA.getRefParticles().size() == 0) {
            this.fPlotBar.clear();
        } else {
            this.fPlotBar.add(aggA.getRefParticles().size(), aggA.getRadiusOfGyration(type));
        }
    }

    @Override
    public double getPowerLawDimension() {

        this.fPlotBar.sortX(true);

        FPlot regression = this.fPlotBar.toFPlot(FStat::mean);

        regression.swapXY();
        regression.mutateX(FStat::ln);
        regression.mutateY(FStat::ln);

        return regression.reg().fitSlope((int) (regression.size() * 0.9)).at(1);
    }
}

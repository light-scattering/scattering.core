package eu.scattering.core.impl.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.type.RadiusOfGyration;

public class FMonitorPCRadiusOfGyrationDef implements FMonitorPCRadiusOfGyration {
    private final RadiusOfGyration type;
    private final FPlot fPlot;

    private double skip = -1;

    private FMonitorPCRadiusOfGyrationDef(ScatFactory factory, RadiusOfGyration type) {

        this.type = type;
        this.fPlot = factory.getFPlot();

        this.fPlot.setName("Radius of gyration");
    }

    public static FMonitorPCRadiusOfGyration create(ScatFactory factory, int skip, RadiusOfGyration type) {
        FMonitorPCRadiusOfGyration results = new FMonitorPCRadiusOfGyrationDef(factory, type);

        results.setSkip(skip);

        return results;
    }

    @Override
    public void setSkip(int skip) {

        this.skip = skip;
    }

    @Override
    public FPlot getRefFPlot() {

        return this.fPlot;
    }

    @Override
    public void accept(FAggregate fAggregate, Shape shape) {

        if (fAggregate.getRefParticles().size() == 0) {
            fPlot.clear();
        } else {
            if (fAggregate.getRefParticles().size() > this.skip) {
                fPlot.add(fAggregate.getRefParticles().size(), fAggregate.getRadiusOfGyration(type));
            }
        }
    }

    @Override
    public double getPowerLawDimension() {
        FPlot reg = this.fPlot.copy();

        reg.swapXY();
        reg.mutateX(FStat::ln);
        reg.mutateY(FStat::ln);

        return reg.reg().fitSlope((int) (reg.size() * 0.9)).at(1);
    }
}

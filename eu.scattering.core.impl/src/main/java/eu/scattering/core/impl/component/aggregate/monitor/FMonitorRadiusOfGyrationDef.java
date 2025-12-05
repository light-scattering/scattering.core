package eu.scattering.core.impl.component.aggregate.monitor;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.common.module.FMonitorRadiusOfGyration;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;

public class FMonitorRadiusOfGyrationDef implements FMonitorRadiusOfGyration {
    private final FAggregate.RadiusOfGyration type;
    private final FPlot fPlot;

    private double skip = -1;

    private FMonitorRadiusOfGyrationDef(ScatFactory factory, FAggregate.RadiusOfGyration type) {

        this.type = type;
        this.fPlot = factory.getFPlot();

        this.fPlot.setName("Radius of gyration");
    }

    public static FMonitorRadiusOfGyration create(ScatFactory factory, int skip, FAggregate.RadiusOfGyration type) {
        FMonitorRadiusOfGyration results = new FMonitorRadiusOfGyrationDef(factory, type);

        results.setSkip(skip);

        return results;
    }

    @Override
    public void setSkip(int skip) {

        this.skip = skip;
    }

    @Override
    public FPlot getFPlot() {

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

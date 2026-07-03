package eu.scattering.core.impl.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

public class FMonitorPCRadiusOfGyrationDef implements FMonitorPCRadiusOfGyration {
    private final static Approximation approx = Approximation.WINDOW;

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

        this.fPlot.sortX(true);

        return this.fPlot;
    }

    @Override
    public void accept(FAggregate fAggregate, Shape shape) {

        if (fAggregate == null || fAggregate.getRefParticles().size() == 0) {
            this.fPlot.clear();
        } else {
            if (fAggregate.getRefParticles().size() > this.skip) {
                this.fPlot.add(fAggregate.getRefParticles().size(), fAggregate.getRadiusOfGyration(type));
            }
        }
    }

    @Override
    public double getPowerLawDimension() {

        this.fPlot.sortX(true);

        FPlot results = getResults();
        FPoly regression = getRegression(results);

        return regression.at(1);
    }

    private FPlot getResults() {
        FPlot results = this.fPlot.copy();

        results.swapXY();

        results.mutateX(FStat::ln);
        results.mutateY(FStat::ln);

        return results;
    }

    private FPoly getRegression(FPlot results) {

        if (approx.equals(Approximation.WINDOW)) {
            return results.reg().fitSlope((int) (results.size() * 0.9));
        }

        return results.reg().fitLinear((int) (results.size() * 0.3), results.size() - 1);
    }

    //-----------------------------------------------------------------------------------------------------

    private enum Approximation {
        WINDOW, OFFSET
    }
}

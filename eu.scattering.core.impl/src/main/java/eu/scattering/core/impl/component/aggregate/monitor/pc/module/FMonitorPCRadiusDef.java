package eu.scattering.core.impl.component.aggregate.monitor.pc.module;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadius;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.type.Center;

public class FMonitorPCRadiusDef implements FMonitorPCRadius {
    private final Center type;
    private final FPlot fPlot;

    private final FPoint center;

    private double skip = -1;

    private FMonitorPCRadiusDef(ScatFactory factory, Center type) {

        this.type = type;
        this.fPlot = factory.getFPlot();

        this.center = factory.getFPoint();

        this.fPlot.setName("Radius");
    }

    public static FMonitorPCRadius create(ScatFactory factory, int skip, Center type) {
        FMonitorPCRadius results = new FMonitorPCRadiusDef(factory, type);

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
                fAggregate.getCenter(this.center, type);

                this.fPlot.add(fAggregate.getRefParticles().size(), fAggregate.getRadius(this.center));
            }
        }
    }
}

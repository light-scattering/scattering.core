package eu.scattering.core.impl.component.aggregate.monitor;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.construct.FMonitorConstruct;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.construct.FPlot2D;

public class FMonitorRoGPolyDef implements FMonitorConstruct {
    private final FPlot2D fPlot2D;
    private double skip = -1;

    private FMonitorRoGPolyDef(ScatFactory factory) {

        this.fPlot2D = factory.getFPlot2D();

        this.fPlot2D.setName("Radius of gyration - Polydisperse");
    }

    public static FMonitorConstruct create(ScatFactory factory, int skip) {
        FMonitorConstruct results = new FMonitorRoGPolyDef(factory);

        results.setSkip(skip);

        return results;
    }

    @Override
    public void setSkip(int skip) {

        this.skip = skip;
    }

    @Override
    public FPlot2D getResults() {

        return this.fPlot2D;
    }

    @Override
    public void accept(FAggregate fAggregate, Shape shape) {

        if (fAggregate.getRefParticles().size() == 0) {
            fPlot2D.clear();
        } else {
            if (fAggregate.getRefParticles().size() > this.skip) {
                fPlot2D.add(fAggregate.getRefParticles().size(), fAggregate.getRadiusOfGyrationPolydisperse());
            }
        }
    }
}

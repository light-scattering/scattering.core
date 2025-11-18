package eu.scattering.core.impl.component.aggregate.monitor;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.construct.FMonitorConstruct;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.construct.FPlot;

public class FMonitorRoGDef implements FMonitorConstruct {
    private final FAggregate.RoG type;
    private final FPlot fPlot;

    private double skip = -1;

    private FMonitorRoGDef(ScatFactory factory, FAggregate.RoG type) {

        this.type = type;
        this.fPlot = factory.getFPlot();

        this.fPlot.setName("Radius of gyration");
    }

    public static FMonitorConstruct create(ScatFactory factory, int skip, FAggregate.RoG type) {
        FMonitorConstruct results = new FMonitorRoGDef(factory, type);

        results.setSkip(skip);

        return results;
    }

    @Override
    public void setSkip(int skip) {

        this.skip = skip;
    }

    @Override
    public FPlot getResults() {

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
}

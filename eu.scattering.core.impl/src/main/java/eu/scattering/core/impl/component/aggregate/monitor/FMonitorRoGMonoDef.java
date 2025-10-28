package eu.scattering.core.impl.component.aggregate.monitor;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.monitor.FMonitorRoGMono;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.construct.FPlot2D;

public class FMonitorRoGMonoDef implements FMonitorRoGMono {
    private final FPlot2D fPlot2D;

    private FMonitorRoGMonoDef(ScatFactory factory) {

        this.fPlot2D = factory.getFPlot2D();
    }

    public static FMonitorRoGMono create(ScatFactory factory) {

        return new FMonitorRoGMonoDef(factory);
    }

    @Override
    public FPlot2D getResults() {

        return this.fPlot2D;
    }

    @Override
    public void accept(FAggregate fAggregate, Shape shape) {

        if (fAggregate.getRefParticles().size() == 0) {
            fPlot2D.clear();
        }

        fPlot2D.add(fAggregate.getRefParticles().size(), fAggregate.getRadiusOfGyrationMonodisperse());
    }
}

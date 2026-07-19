package eu.scattering.core.impl.component.aggregate.monitor.cc.module;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadius;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.utility.type.variant.Center;

public class FMonitorCCRadiusDef implements FMonitorCCRadius {
    private final Center type;
    private final FPlotBar fPlotBar;

    private final FPoint center;

    private FMonitorCCRadiusDef(ScatterFactory factory, Center type) {

        this.type = type;
        this.fPlotBar = factory.getFPlotBar();

        this.center = factory.getFPoint();

        this.fPlotBar.setName("Radius");
    }

    public static FMonitorCCRadius create(ScatterFactory factory, Center type) {

        return new FMonitorCCRadiusDef(factory, type);
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
            aggA.getCenter(this.center, type);

            this.fPlotBar.add(aggA.size(), aggA.getRadiusFrom(this.center));
        }
    }
}

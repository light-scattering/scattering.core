package eu.scattering.core.impl.component.aggregate.monitor.cc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCCFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadius;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.impl.component.aggregate.monitor.cc.module.FMonitorCCRadiusDef;
import eu.scattering.core.impl.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyrationDef;

public class FMonitorCCFactoryContextDef implements FMonitorCCFactoryContext {
    private final ScatterFactory factory;

    private FMonitorCCFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FMonitorCCFactoryContext create(ScatterFactory factory) {

        return new FMonitorCCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FMonitorCCRadiusOfGyration radiusOfGyration(RadiusOfGyration type) {

        return FMonitorCCRadiusOfGyrationDef.create(this.factory, type);
    }

    @Override
    public FMonitorCCRadius radius(Center type) {

        return FMonitorCCRadiusDef.create(this.factory, type);
    }
}

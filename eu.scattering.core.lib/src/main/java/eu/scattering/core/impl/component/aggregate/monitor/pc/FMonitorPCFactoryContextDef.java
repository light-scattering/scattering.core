package eu.scattering.core.impl.component.aggregate.monitor.pc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPCFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadius;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.impl.component.aggregate.monitor.pc.module.FMonitorPCRadiusDef;
import eu.scattering.core.impl.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyrationDef;

public class FMonitorPCFactoryContextDef implements FMonitorPCFactoryContext {
    private final ScatterFactory factory;

    private FMonitorPCFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FMonitorPCFactoryContext create(ScatterFactory factory) {

        return new FMonitorPCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FMonitorPCRadiusOfGyration radiusOfGyration(RadiusOfGyration type, int skip) {

        return FMonitorPCRadiusOfGyrationDef.create(this.factory, skip, type);
    }

    @Override
    public FMonitorPCRadius radius(int skip, Center type) {

        return FMonitorPCRadiusDef.create(this.factory, skip, type);
    }
}

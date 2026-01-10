package eu.scattering.core.impl.component.aggregate.monitor.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPCFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.type.RadiusOfGyration;
import eu.scattering.core.impl.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyrationDef;

public class FMonitorPCFactoryContextDef implements FMonitorPCFactoryContext {
    private final ScatFactory factory;

    private FMonitorPCFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FMonitorPCFactoryContextDef create(ScatFactory factory) {

        return new FMonitorPCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FMonitorPCRadiusOfGyration radiusOfGyration(int skip, RadiusOfGyration type) {

        return FMonitorPCRadiusOfGyrationDef.create(this.factory, skip, type);
    }
}

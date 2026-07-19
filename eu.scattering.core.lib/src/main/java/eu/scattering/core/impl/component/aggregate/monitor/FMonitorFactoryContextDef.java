package eu.scattering.core.impl.component.aggregate.monitor;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCCFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPCFactoryContext;
import eu.scattering.core.impl.component.aggregate.monitor.cc.FMonitorCCFactoryContextDef;
import eu.scattering.core.impl.component.aggregate.monitor.pc.FMonitorPCFactoryContextDef;

public class FMonitorFactoryContextDef implements FMonitorFactoryContext {
    private final ScatterFactory factory;

    private FMonitorFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FMonitorFactoryContext create(ScatterFactory factory) {

        return new FMonitorFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FMonitorPCFactoryContext pc() {

        return FMonitorPCFactoryContextDef.create(this.factory);
    }

    @Override
    public FMonitorCCFactoryContext cc() {

        return FMonitorCCFactoryContextDef.create(this.factory);
    }
}

package eu.scattering.core.impl.component.aggregate.monitor;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.monitor.FMonitorFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.pc.FMonitorPCFactoryContext;
import eu.scattering.core.impl.component.aggregate.monitor.pc.FMonitorPCFactoryContextDef;

public class FMonitorFactoryContextDef implements FMonitorFactoryContext {
    private final ScatFactory factory;

    private FMonitorFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FMonitorFactoryContext create(ScatFactory factory) {

        return new FMonitorFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FMonitorPCFactoryContext pc() {

        return FMonitorPCFactoryContextDef.create(this.factory);
    }
}

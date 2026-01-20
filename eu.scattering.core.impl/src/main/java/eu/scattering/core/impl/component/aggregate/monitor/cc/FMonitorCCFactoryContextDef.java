package eu.scattering.core.impl.component.aggregate.monitor.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.monitor.cc.FMonitorCCFactoryContext;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.type.RadiusOfGyration;
import eu.scattering.core.impl.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyrationDef;

public class FMonitorCCFactoryContextDef implements FMonitorCCFactoryContext {
    private final ScatFactory factory;

    private FMonitorCCFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FMonitorCCFactoryContext create(ScatFactory factory) {

        return new FMonitorCCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FMonitorCCRadiusOfGyration radiusOfGyration(RadiusOfGyration type) {

        return FMonitorCCRadiusOfGyrationDef.create(this.factory, type);
    }
}

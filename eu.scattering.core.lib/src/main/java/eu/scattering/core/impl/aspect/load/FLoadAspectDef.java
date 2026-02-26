package eu.scattering.core.impl.aspect.load;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.load.FLoadAspect;
import eu.scattering.core.design.component.aggregate.FAggregateAspectLoad;
import eu.scattering.core.impl.component.aggregate.FAggregateAspectLoadDef;

public class FLoadAspectDef implements FLoadAspect {
    private final ScatFactory factory;

    private FLoadAspectDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FLoadAspect create(ScatFactory factory) {

        return new FLoadAspectDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregateAspectLoad getFAggregateContext() {

        return FAggregateAspectLoadDef.create(this.factory);
    }
}

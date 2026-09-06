package eu.scattering.core.impl.aspect.load;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.load.FLoadAspect;
import eu.scattering.core.design.component.aggregate.FAggregateLoader;
import eu.scattering.core.impl.component.aggregate.FAggregateLoaderDef;

public class FLoadAspectDef implements FLoadAspect {
    private final ScatterFactory factory;

    private FLoadAspectDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FLoadAspect create(ScatterFactory factory) {

        return new FLoadAspectDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FAggregateLoader aggregate() {

        return FAggregateLoaderDef.create(this.factory);
    }
}

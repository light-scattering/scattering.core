package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.model.FModelFactoryContext;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactoryContext;
import eu.scattering.core.impl.component.aggregate.model.cc.FModelCCFactoryContextDef;
import eu.scattering.core.impl.component.aggregate.model.pc.FModelPCFactoryContextDef;

public class FModelFactoryContextDef implements FModelFactoryContext {
    private final ScatFactory factory;

    private FModelFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FModelFactoryContext create(ScatFactory factory) {

        return new FModelFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FModelPCFactoryContext pc() {

        return FModelPCFactoryContextDef.create(this.factory);
    }

    @Override
    public FModelCCFactoryContext cc() {

        return FModelCCFactoryContextDef.create(this.factory);
    }
}

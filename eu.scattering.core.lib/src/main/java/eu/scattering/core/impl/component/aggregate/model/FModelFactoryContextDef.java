package eu.scattering.core.impl.component.aggregate.model;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.model.FModelFactoryContext;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactory;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactory;
import eu.scattering.core.impl.component.aggregate.model.cc.FModelCCFactoryDef;
import eu.scattering.core.impl.component.aggregate.model.pc.FModelPCFactoryDef;

public class FModelFactoryContextDef implements FModelFactoryContext {
    private final ScatterFactory factory;

    private FModelFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FModelFactoryContext create(ScatterFactory factory) {

        return new FModelFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FModelPCFactory pc() {

        return FModelPCFactoryDef.create(this.factory);
    }

    @Override
    public FModelCCFactory cc() {

        return FModelCCFactoryDef.create(this.factory);
    }
}

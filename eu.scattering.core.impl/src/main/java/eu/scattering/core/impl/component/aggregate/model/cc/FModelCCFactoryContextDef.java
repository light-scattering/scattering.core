package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;

public class FModelCCFactoryContextDef implements FModelCCFactoryContext {
    private final ScatFactory factory;

    private FModelCCFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FModelCCFactoryContext create(ScatFactory factory) {

        return new FModelCCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FModelCCBallistic createFModelCCBallistic3D(FAggregate aggregate) {

        return FModelCCBallistic3DDef.create(aggregate, this.factory);
    }
}

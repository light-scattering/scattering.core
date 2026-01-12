package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCA;
import eu.scattering.core.design.type.Dimension;

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
    public FModelCCBallistic ballistic(Dimension dimension, FAggregate aggregate) {

        return switch (dimension) {
            case D3 -> FModelCCBallistic3DDef.create(aggregate, this.factory);
            case D2 -> FModelCCBallistic2DDef.create(aggregate, this.factory);
        };
    }

    @Override
    public FModelCCRLCA rlca(Dimension dimension, FAggregate aggregate) {

        return switch (dimension) {
            case D3 -> FModelCCRLCA3DDef.create(aggregate, this.factory);
            case D2 -> FModelCCRLCA2DDef.create(aggregate, this.factory);
        };
    }
}

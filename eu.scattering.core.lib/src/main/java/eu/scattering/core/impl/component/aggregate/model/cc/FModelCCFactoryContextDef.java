package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;
import eu.scattering.core.design.component.aggregate.model.cc.dlca.FModelCCDLCA;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCA;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.utility.type.Dimension;

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

        return FModelCCBallisticDef.create(dimension, aggregate, this.factory);
    }

    @Override
    public FModelCCRLCA rlca(Dimension dimension, FAggregate aggregate) {

        return FModelCCRLCADef.create(dimension, aggregate, this.factory);
    }

    @Override
    public FModelCCDLCA dlca(Dimension dimension, FAggregate aggregate) {

        return FModelCCDLCADef.create(dimension, aggregate, this.factory);
    }

    @Override
    public FModelCCTunable tunable(Dimension dimension, FAggregate aggregate, double df, double kf) {

        return FModelCCTunableDef.create(dimension, aggregate, this.factory, df, kf);
    }
}

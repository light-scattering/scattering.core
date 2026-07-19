package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelPCRLA;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.utility.type.option.Dimension;

public class FModelPCFactoryContextDef implements FModelPCFactoryContext {
    private final ScatterFactory factory;

    private FModelPCFactoryContextDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FModelPCFactoryContext create(ScatterFactory factory) {

        return new FModelPCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FModelPCBallistic ballistic(Dimension dimension, FAggregate aggregate) {

        return FModelPCBallisticDef.create(dimension, aggregate, this.factory);
    }

    @Override
    public FModelPCDLA dla(Dimension dimension, FAggregate aggregate) {

        return FModelPCDLADef.create(dimension, aggregate, this.factory);
    }

    @Override
    public FModelPCRLA rla(Dimension dimension, FAggregate aggregate) {

        return FModelPCRLADef.create(dimension, aggregate, this.factory);
    }

    //--------------------------------------------------

    @Override
    public FModelPCTunable tunable(Dimension dimension, FAggregate aggregate, double df, double kf) {

        return FModelPCFilippovDef.create(dimension, aggregate, this.factory, df, kf);
    }
}

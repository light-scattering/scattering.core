package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.component.aggregate.model.pc.rla.FModelPCRLA;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.type.Dimension;

public class FModelPCFactoryContextDef implements FModelPCFactoryContext {
    private final ScatFactory factory;

    private FModelPCFactoryContextDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FModelPCFactoryContext create(ScatFactory factory) {

        return new FModelPCFactoryContextDef(factory);
    }

    //--------------------------------------------------

    @Override
    public FModelPCBallistic ballistic(Dimension dimension, FAggregate aggregate) {

        return switch (dimension) {
            case D3 -> FModelPCBallistic3DDef.create(aggregate, this.factory);
            case D2 -> FModelPCBallistic2DDef.create(aggregate, this.factory);
        };
    }

    @Override
    public FModelPCDLA dla(Dimension dimension, FAggregate aggregate) {

        return switch (dimension) {
            case D3 -> FModelPCDLA3DDef.create(aggregate, this.factory);
            case D2 -> FModelPCDLA2DDef.create(aggregate, this.factory);
        };
    }

    @Override
    public FModelPCRLA rla(Dimension dimension, FAggregate aggregate) {

        return switch (dimension) {
            case D3 -> FModelPCRLA3DDef.create(aggregate, this.factory);
            case D2 -> FModelPCRLA2DDef.create(aggregate, this.factory);
        };
    }

    //--------------------------------------------------

    @Override
    public FModelPCTunable tunable(Dimension dimension, FAggregate aggregate, double df, double kf) {

        return switch (dimension) {
            case D3 -> FModelPCFilippov3DDef.create(aggregate, this.factory, df, kf);
            case D2 -> FModelPCFilippov2DDef.create(aggregate, this.factory, df, kf);
        };
    }
}

package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCCFactory;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;
import eu.scattering.core.design.component.aggregate.model.cc.dlca.FModelCCDLCA;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCA;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.utility.type.option.Dimension;

public class FModelCCFactoryDef implements FModelCCFactory {
    private final ScatterFactory factory;

    private FModelCCFactoryDef(ScatterFactory factory) {

        this.factory = factory;
    }

    public static FModelCCFactory create(ScatterFactory factory) {

        return new FModelCCFactoryDef(factory);
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

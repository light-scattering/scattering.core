package eu.scattering.core.design.component.aggregate.model.cc.tunable;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.option.Dimension;

public interface FModelCCTunableFactory {

    FModelCCTunable tunable(Dimension dimension, FAggregate aggregate, double df, double kf);

    //--------------------------------------------------

    default FModelCCTunable tunable(FAggregate aggregate, double df, double kf) {

        return tunable(Dimension.D3, aggregate, df, kf);
    }
}

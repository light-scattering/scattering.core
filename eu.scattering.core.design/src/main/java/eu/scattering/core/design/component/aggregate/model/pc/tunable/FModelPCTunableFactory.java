package eu.scattering.core.design.component.aggregate.model.pc.tunable;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.Dimension;

public interface FModelPCTunableFactory {

    FModelPCTunable tunable(Dimension dimension, FAggregate fAggregate, double df, double kf);

    //--------------------------------------------------

    default FModelPCTunable tunable(FAggregate fAggregate, double df, double kf) {

        return tunable(Dimension.D3, fAggregate, df, kf);
    }
}

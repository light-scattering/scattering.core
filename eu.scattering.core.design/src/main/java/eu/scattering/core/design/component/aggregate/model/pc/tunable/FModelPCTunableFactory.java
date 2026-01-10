package eu.scattering.core.design.component.aggregate.model.pc.tunable;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.type.Dimension;

public interface FModelPCTunableFactory {

    FModelPCTunable filippov(Dimension dimension, FAggregate fAggregate);
    FModelPCTunable filippov(Dimension dimension, FAggregate fAggregate, double df, double kf);

    //--------------------------------------------------

    default FModelPCTunable filippov(FAggregate fAggregate) {

        return filippov(Dimension.D3, fAggregate);
    }

    default FModelPCTunable filippov(FAggregate fAggregate, double df, double kf) {

        return filippov(Dimension.D3, fAggregate, df, kf);
    }
}

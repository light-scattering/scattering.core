package eu.scattering.core.design.component.aggregate.model.pc.ballistic;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.option.Dimension;

public interface FModelPCBallisticFactory {

    FModelPCBallistic ballistic(Dimension dimension, FAggregate fAggregate);

    //--------------------------------------------------

    default FModelPCBallistic ballistic(FAggregate fAggregate) {

        return ballistic(Dimension.D3, fAggregate);
    }
}

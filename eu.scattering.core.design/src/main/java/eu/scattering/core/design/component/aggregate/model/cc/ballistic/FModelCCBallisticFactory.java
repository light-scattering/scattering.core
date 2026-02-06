package eu.scattering.core.design.component.aggregate.model.cc.ballistic;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.Dimension;

public interface FModelCCBallisticFactory {

    FModelCCBallistic ballistic(Dimension dimension, FAggregate aggregate);

    //--------------------------------------------------

    default FModelCCBallistic ballistic(FAggregate fAggregate) {

        return ballistic(Dimension.D3, fAggregate);
    }
}

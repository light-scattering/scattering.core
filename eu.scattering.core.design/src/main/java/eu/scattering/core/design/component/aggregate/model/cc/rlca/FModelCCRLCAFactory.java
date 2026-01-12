package eu.scattering.core.design.component.aggregate.model.cc.rlca;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.type.Dimension;

public interface FModelCCRLCAFactory {

    FModelCCRLCA rlca(Dimension dimension, FAggregate aggregate);

    //--------------------------------------------------

    default FModelCCRLCA rlca(FAggregate fAggregate) {

        return rlca(Dimension.D3, fAggregate);
    }
}

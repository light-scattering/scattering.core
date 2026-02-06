package eu.scattering.core.design.component.aggregate.model.pc.dla;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.Dimension;

public interface FModelPCDLAFactory {

    FModelPCDLA dla(Dimension dimension, FAggregate fAggregate);

    //--------------------------------------------------

    default FModelPCDLA dla(FAggregate fAggregate) {

        return dla(Dimension.D3, fAggregate);
    }
}

package eu.scattering.core.design.component.aggregate.model.pc.rla;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.Dimension;

public interface FModelPCRLAFactory {

    FModelPCRLA rla(Dimension dimension, FAggregate fAggregate);

    //--------------------------------------------------

    default FModelPCRLA rla(FAggregate fAggregate) {

        return rla(Dimension.D3, fAggregate);
    }
}

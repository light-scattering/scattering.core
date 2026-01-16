package eu.scattering.core.design.component.aggregate.model.cc.dlca;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.type.Dimension;

public interface FModelCCDLCAFactory {


    FModelCCDLCA dlca(Dimension dimension, FAggregate fAggregate);

    //--------------------------------------------------

    default FModelCCDLCA dlca(FAggregate fAggregate) {

        return dlca(Dimension.D3, fAggregate);
    }
}

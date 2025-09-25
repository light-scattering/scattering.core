package eu.scattering.core.design.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;

public interface FModelPCFactory {

    FModel createFModelRLA3D(FAggregate aggregate);
    FModel createFModelRLA2D(FAggregate aggregate);

    FModel createFModelBallistic3D(FAggregate aggregate);
    FModel createFModelBallistic2D(FAggregate aggregate);
}

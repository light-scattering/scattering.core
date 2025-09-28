package eu.scattering.core.design.component.aggregate.model.pc.ballistic;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FModelPCBallisticFactory {

    FModelPCBallistic createFModelBallistic3D(FAggregate aggregate);
    FModelPCBallistic createFModelBallistic2D(FAggregate aggregate);
}

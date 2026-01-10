package eu.scattering.core.design.component.aggregate.model.pc.ballistic;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FModelPCBallisticFactory {

    FModelPCBallistic createFModelPCBallistic3D(FAggregate aggregate);
    FModelPCBallistic createFModelPCBallistic2D(FAggregate aggregate);
}

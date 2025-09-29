package eu.scattering.core.design.component.aggregate.model.pc.dla;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FModelDLAFactory {

    FModelDLA createFModelDLA3D(FAggregate aggregate);
    FModelDLA createFModelDLA2D(FAggregate aggregate);
}

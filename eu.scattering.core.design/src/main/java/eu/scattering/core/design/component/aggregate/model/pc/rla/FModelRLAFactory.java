package eu.scattering.core.design.component.aggregate.model.pc.rla;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FModelRLAFactory {

    FModelRLA createFModelRLA3D(FAggregate aggregate);
    FModelRLA createFModelRLA2D(FAggregate aggregate);
}

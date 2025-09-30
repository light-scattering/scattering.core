package eu.scattering.core.design.component.aggregate.model.pc.tunable;

import eu.scattering.core.design.component.aggregate.FAggregate;

public interface FModelPCTunableFactory {

    FModelPCTunable createFModelFilippov3D(FAggregate aggregate);
    FModelPCTunable createFModelFilippov2D(FAggregate aggregate);

    FModelPCTunable createFModelFilippov3D(FAggregate aggregate, double df, double kf);
    FModelPCTunable createFModelFilippov2D(FAggregate aggregate, double df, double kf);
}

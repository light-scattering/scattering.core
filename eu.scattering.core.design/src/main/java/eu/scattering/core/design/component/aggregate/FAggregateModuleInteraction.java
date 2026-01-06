package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.geometry.base.vector.FVector;

public interface FAggregateModuleInteraction {

    double project(FAggregate target, FVector dir);
    double project(FAggregate target, FVector dir, double distLimit);
}

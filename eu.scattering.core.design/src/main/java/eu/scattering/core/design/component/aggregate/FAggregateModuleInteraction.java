package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.geometry.construct.ray.FRay;

public interface FAggregateModuleInteraction {

    double project(FAggregate aggregate, FRay ray);
}

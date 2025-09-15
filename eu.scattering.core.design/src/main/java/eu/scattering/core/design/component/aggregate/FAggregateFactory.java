package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;

public interface FAggregateFactory {

    FAggregate getFAggregate();

    FAggregate getFAggregate(FAssembly<Shape> particles);
}

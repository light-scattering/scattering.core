package eu.scattering.core.design.component;

import eu.scattering.core.design.component.aggregate.FAggregateFactory;
import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.number.NumberFactory;

public interface ComponentFactory extends FAggregateFactory, GeometryFactory, NumberFactory {
}

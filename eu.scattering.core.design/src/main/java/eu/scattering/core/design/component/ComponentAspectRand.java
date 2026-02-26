package eu.scattering.core.design.component;

import eu.scattering.core.design.component.aggregate.FAggregateAspectRand;
import eu.scattering.core.design.component.geometry.GeometryAspectRand;
import eu.scattering.core.design.component.number.NumberAspectRand;

public interface ComponentAspectRand extends GeometryAspectRand, NumberAspectRand, FAggregateAspectRand {
}

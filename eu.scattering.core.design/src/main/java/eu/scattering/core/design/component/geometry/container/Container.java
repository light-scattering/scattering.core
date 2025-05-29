package eu.scattering.core.design.component.geometry.container;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.Geometry;

public interface Container<T> extends Geometry, Component<T> {

    // Note, that the copy might not be equal to the original object.
    // Reference points simultaneously used in multiple geometries will be replaced by new objects.
    // Therefore, in some cases, the total number of points might increase.

    T copy();
}

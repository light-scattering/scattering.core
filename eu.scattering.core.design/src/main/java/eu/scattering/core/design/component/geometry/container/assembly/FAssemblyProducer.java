package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FAssemblyProducer<T extends Geometry> {

    FAssembly<T> produce();
}

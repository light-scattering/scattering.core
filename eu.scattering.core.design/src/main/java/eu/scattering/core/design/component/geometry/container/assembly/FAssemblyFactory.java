package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;

public interface FAssemblyFactory {

    <T extends Geometry> FAssemblyProducer<T> getFAssemblyProducer();

    <T extends Geometry> FAssembly<T> getFAssembly();
}

package eu.scattering.core.design.component.geometry.container.assembly;

import eu.scattering.core.design.component.geometry.Geometry;

import java.util.Collection;
import java.util.List;

public interface FAssemblyFactory {

    <T extends Geometry> FAssemblyProducer<T> getFAssemblyProducer();

    <T extends Geometry> FAssembly<T> getFAssembly();
    <T extends Geometry> FAssembly<T> getFAssembly(List<? extends T> elements);
}

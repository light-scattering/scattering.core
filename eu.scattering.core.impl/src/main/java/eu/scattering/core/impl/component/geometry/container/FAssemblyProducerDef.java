package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.container.ContainerFactory;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.function.Function;

public class FAssemblyProducerDef<T extends Geometry> implements FAssemblyProducer<T> {
    private final ProducerCoreDef<FAssemblyProducer<T>, FAssembly<T>> core;

    private final ContainerFactory factory;

    private FAssemblyProducerDef(ContainerFactory factory) {

        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, null);

        setPresetDefault();
    }

    public static <U extends Geometry> FAssemblyProducer<U> create(ContainerFactory factory) {

        return new FAssemblyProducerDef<>(factory);
    }

    @Override
    public FAssembly<T> produce() {

        return core.getFunction().apply(factory.getFAssembly());
    }

    // -------------------------------------------------------------------------------------------------

    private void setConfig(Function<FAssembly<T>, FAssembly<T>> function) {

        core.setConfig(function, 1);
    }

    private void setPresetDefault() {
        Function<FAssembly<T>, FAssembly<T>> function = (fAssembly) -> fAssembly;

        setConfig(function);
    }
}

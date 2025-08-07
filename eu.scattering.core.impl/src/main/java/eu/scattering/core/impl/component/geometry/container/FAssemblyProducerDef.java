package eu.scattering.core.impl.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyFactory;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FAssemblyProducerDef<T extends Geometry> implements FAssemblyProducer<T> {

    private final FAssemblyFactory factory;
    private final ProducerCoreDef<FAssembly<T>> processor;
    private final FRandEngine rndEngine;

    private FAssemblyProducerDef(FAssemblyFactory factory, FRandEngine randomizer) {

        this.factory = factory;
        this.rndEngine = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndEngine.getFRand());
    }

    public static <U extends Geometry> FAssemblyProducer<U> create(FAssemblyFactory factory, FRandEngine randomizer) {

        return new FAssemblyProducerDef<>(factory, randomizer);
    }

    @Override
    public FAssemblyProducer<T> withCustomRule(Function<FAssemblyFactory, FAssembly<T>> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FAssemblyProducer<T> withCustomRule(BiFunction<FAssemblyFactory, FRandEngine, FAssembly<T>> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndEngine), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FAssembly<T> produce() {

        return processor.produce();
    }

    @Override
    public List<FAssembly<T>> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FAssembly<T>> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FAssembly<T>> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public Stream<FAssembly<T>> stream() {

        return this.processor.stream();
    }
}

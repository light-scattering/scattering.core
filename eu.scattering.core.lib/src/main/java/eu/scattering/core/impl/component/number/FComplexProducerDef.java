package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexFactory;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FComplexProducerDef implements FComplexProducer {

    private final FComplexFactory factory;
    private final ProducerCoreDef<FComplex> processor;
    private final FRandEngine randomizer;

    private FComplexProducerDef(FComplexFactory factory, FRandEngine randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreDef<>(randomizer);
    }

    public static FComplexProducer create(FComplexFactory factory, FRandEngine randomizer) {

        return new FComplexProducerDef(factory, randomizer);
    }

    @Override
    public FComplexProducer withCustomRule(Function<FComplexFactory, FComplex> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FComplexProducer withCustomRule(BiFunction<FComplexFactory, FRandEngine, FComplex> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, randomizer), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplexProducer withZero(int weight) {
        Function<FComplexFactory, FComplex> function = FComplexFactory::getFComplex;

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex produce() {

        return processor.produce();
    }

    @Override
    public List<FComplex> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FComplex> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FComplex> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public FComplexProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FComplexProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FComplexProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FComplex> stream() {

        return this.processor.stream();
    }
}

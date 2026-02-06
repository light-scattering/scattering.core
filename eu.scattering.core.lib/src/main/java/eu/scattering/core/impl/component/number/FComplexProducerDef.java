package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexFactory;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FComplexProducerDef implements FComplexProducer {

    private final FComplexFactory factory;
    private final ProducerCoreDef<FComplex> processor;
    private final FRandGenerator randomizer;

    private FComplexProducerDef(FComplexFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreDef<>(randomizer);
    }

    public static FComplexProducer create(FComplexFactory factory, FRandGenerator randomizer) {

        return new FComplexProducerDef(factory, randomizer);
    }

    @Override
    public FComplexProducer withCustomRule(Function<FComplexFactory, FComplex> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FComplexProducer withCustomRule(BiFunction<FComplexFactory, FRandGenerator, FComplex> function, int weight) {

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
    public Stream<FComplex> stream() {

        return this.processor.stream();
    }
}

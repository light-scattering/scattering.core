package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexFactory;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FComplexProducerDef implements FComplexProducer {

    private final FComplexFactory factory;
    private final ProducerCoreDef<FComplex> processor;

    private FComplexProducerDef(FComplexFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
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
    public FComplex produce() {

        return processor.produce();
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
    public Stream<FComplex> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FComplex> iterator() {

        return this.processor.getIterator();
    }
}

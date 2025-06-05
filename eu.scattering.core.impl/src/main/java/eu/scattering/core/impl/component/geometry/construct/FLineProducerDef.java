package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FLineProducerDef implements FLineProducer {

    private final FLineFactory factory;
    private final ProducerCoreDef<FLine> processor;

    private FLineProducerDef(FLineFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.processor = new ProducerCoreDef<>(randomizer);
    }

    public static FLineProducer create(FLineFactory factory, FRandGenerator randomizer) {

        return new FLineProducerDef(factory, randomizer);
    }

    @Override
    public FLineProducer withCustomRule(Function<FLineFactory, FLine> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FLine produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FLineProducer withFVector(FVectorProducer origin, int weight) {
        Function<FLineFactory, FLine> function = (factory) ->
                factory.getRefFLine(origin.produce());

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FLine> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FLine> iterator() {

        return this.processor.getIterator();
    }
}

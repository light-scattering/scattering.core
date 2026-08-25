package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FLineProducerDef implements FLineProducer {

    private final FLineFactory factory;
    private final ProducerCoreDef<FLine> processor;
    private final FRandAspect rndAspect;

    private FLineProducerDef(FLineFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.rndAspect = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndAspect.getFRand());
    }

    public static FLineProducer create(FLineFactory factory, FRandAspect randomizer) {

        return new FLineProducerDef(factory, randomizer);
    }

    @Override
    public FLineProducer withCustomRule(Function<FLineFactory, FLine> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FLineProducer withCustomRule(BiFunction<FLineFactory, FRandAspect, FLine> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndAspect), weight);

        return this;
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
    public FLine produce() {

        return processor.produce();
    }

    @Override
    public List<FLine> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FLine> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FLine> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public FLineProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FLineProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FLineProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FLine> stream() {

        return this.processor.stream();
    }
}

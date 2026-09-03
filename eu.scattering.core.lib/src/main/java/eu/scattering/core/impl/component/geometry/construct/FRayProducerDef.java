package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FRayProducerDef implements FRayProducer {

    private final FRayFactory factory;
    private final ProducerCoreDef<FRay> processor;
    private final FRandAspect rndAspect;

    private FRayProducerDef(FRayFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.rndAspect = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndAspect.engine());
    }

    public static FRayProducer create(FRayFactory factory, FRandAspect randomizer) {

        return new FRayProducerDef(factory, randomizer);
    }

    @Override
    public FRayProducer withCustomRule(Function<FRayFactory, FRay> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FRayProducer withCustomRule(BiFunction<FRayFactory, FRandAspect, FRay> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndAspect), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRayProducer withFVector(FVectorProducer origin, int weight) {
        Function<FRayFactory, FRay> function = (factory) ->
                factory.getRefFRay(origin.produce());

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRay produce() {

        return processor.produce();
    }

    @Override
    public List<FRay> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FRay> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FRay> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public FRayProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FRayProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FRayProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FRay> stream() {

        return this.processor.stream();
    }
}

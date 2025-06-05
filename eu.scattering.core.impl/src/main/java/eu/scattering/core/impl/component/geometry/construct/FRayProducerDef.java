package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FRayProducerDef implements FRayProducer {

    private final FRayFactory factory;
    private final ProducerCoreDef<FRay> processor;

    private FRayProducerDef(FRayFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.processor = new ProducerCoreDef<>(randomizer);
    }

    public static FRayProducer create(FRayFactory factory, FRandGenerator randomizer) {

        return new FRayProducerDef(factory, randomizer);
    }

    @Override
    public FRayProducer withCustomRule(Function<FRayFactory, FRay> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FRay produce() {

        return processor.produce();
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
    public Stream<FRay> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FRay> iterator() {

        return this.processor.getIterator();
    }
}

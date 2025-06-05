package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneFactory;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FPlaneProducerDef implements FPlaneProducer {

    private final FPlaneFactory factory;
    private final ProducerCoreAdvancedDef<FPlane> processor;

    private FPlaneProducerDef(FPlaneFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.processor = new ProducerCoreAdvancedDef<>(randomizer);
    }

    public static FPlaneProducer create(FPlaneFactory factory, FRandGenerator randomizer) {

        return new FPlaneProducerDef(factory, randomizer);
    }

    @Override
    public FPlaneProducer withCustomRule(Function<FPlaneFactory, FPlane> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FPlane produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPlaneProducer withFVector(FVectorProducer origin, int weight) {
        Function<FPlaneFactory, FPlane> function = (factory) ->
                factory.getRefFPlane(origin.produce());

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FPlane> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FPlane> iterator() {

        return this.processor.getIterator();
    }
}

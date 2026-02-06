package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneFactory;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FPlaneProducerDef implements FPlaneProducer {

    private final FPlaneFactory factory;
    private final ProducerCoreDef<FPlane> processor;
    private final FRandAspect rndAspect;

    private FPlaneProducerDef(FPlaneFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.rndAspect = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndAspect.getFRand());
    }

    public static FPlaneProducer create(FPlaneFactory factory, FRandAspect randomizer) {

        return new FPlaneProducerDef(factory, randomizer);
    }

    @Override
    public FPlaneProducer withCustomRule(Function<FPlaneFactory, FPlane> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FPlaneProducer withCustomRule(BiFunction<FPlaneFactory, FRandAspect, FPlane> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndAspect), weight);

        return this;
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
    public FPlane produce() {

        return processor.produce();
    }

    @Override
    public List<FPlane> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FPlane> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FPlane> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public Stream<FPlane> stream() {

        return this.processor.stream();
    }
}

package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FPointProducerDef implements FPointProducer {

    private final FPointFactory factory;
    private final ProducerCoreDef<FPoint> processor;
    private final FRandGenerator randomizer;

    private FPointProducerDef(FPointFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreDef<>(this.randomizer);
    }

    public static FPointProducer create(FPointFactory factory, FRandGenerator randomizer) {

        return new FPointProducerDef(factory, randomizer);
    }

    @Override
    public FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FPoint produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPointProducer withZero(int weight) {
        Function<FPointFactory, FPoint> function = FPointFactory::getFPoint;

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withInRange(FPairPos3D range, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().applyStateFrom(randomizer.nextDouble3D(range));

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withInSphere(double radius, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().applyStateFrom(randomizer.nextDoubleInSphere(radius));

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withRadius(double radius, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().applyStateFrom(randomizer.nextDoubleOnSphere(radius));

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FPoint> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FPoint> iterator() {

        return this.processor.getIterator();
    }
}

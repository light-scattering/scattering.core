package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public class FPointProducerDef implements FPointProducer {

    private final FPointFactory factory;
    private final ProducerCoreAdvancedDef<FPoint> processor;
    private final FRandGenerator randomizer;

    private FPointProducerDef(FPointFactory factory, FRandGenerator randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreAdvancedDef<>(this.randomizer);
    }

    public static FPointProducer create(FPointFactory factory, FRandGenerator randomizer) {

        return new FPointProducerDef(factory, randomizer);
    }

    @Override
    public FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int probability) {

        this.processor.addConfig(() -> function.apply(factory), probability);

        return this;
    }

    @Override
    public FPoint produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPointProducer withZero(int probability) {
        Function<FPointFactory, FPoint> function = FPointFactory::getFPoint;

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FPointProducer withInRange(FPairPos3D range, int probability) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().applyStateFrom(randomizer.nextDouble3D(range));

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FPointProducer withInSphere(double radius, int probability) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().applyStateFrom(randomizer.nextDoubleInSphere(radius));

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FPointProducer withRadius(double radius, int probability) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().applyStateFrom(randomizer.nextDoubleOnSphere(radius));

        withCustomRule(function, probability);

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

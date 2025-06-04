package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FPointProducerDef implements FPointProducer {

    private final ProducerCoreAdvancedDef<FPoint> processor;
    private final FRandGenerator randomizer;

    private FPointProducerDef(Supplier<FPoint> supplier, FRandGenerator randomizer) {

        this.randomizer = randomizer;
        this.processor = new ProducerCoreAdvancedDef<>(supplier, this.randomizer);
    }

    public static FPointProducer create(Supplier<FPoint> supplier, FRandGenerator randomizer) {

        return new FPointProducerDef(supplier, randomizer);
    }

    @Override
    public FPointProducer withCustomRule(Function<FPoint, FPoint> function, int probability) {

        this.processor.addConfig(function, probability);

        return this;
    }

    @Override
    public FPoint produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPointProducer withZero(int probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint;

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FPointProducer withInRange(FPairPos3D range, int probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(randomizer.nextDouble3D(range));

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FPointProducer withInsideSphere(double radius, int probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(randomizer.nextDoubleInSphere(radius));

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FPointProducer withOnSphere(double radius, int probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(randomizer.nextDoubleOnSphere(radius));

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

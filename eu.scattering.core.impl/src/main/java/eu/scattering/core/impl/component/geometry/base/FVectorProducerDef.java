package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreAdvancedDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FVectorProducerDef implements FVectorProducer {

    private final ProducerCoreAdvancedDef<FVector> processor;
    private final FRandGenerator randomizer;

    private FVectorProducerDef(Supplier<FVector> supplier, FRandGenerator randomizer) {

        this.randomizer = randomizer;
        this.processor = new ProducerCoreAdvancedDef<>(supplier, this.randomizer);
    }

    public static FVectorProducer create(Supplier<FVector> supplier, FRandGenerator randomizer) {

        return new FVectorProducerDef(supplier, randomizer);
    }

    @Override
    public FVectorProducer withCustomRule(Function<FVector, FVector> function, int probability) {

        this.processor.addConfig(function, probability);

        return this;
    }

    @Override
    public FVector produce() {

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVectorProducer withUnitX(int probability) {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadX(1);

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FVectorProducer withUnitY(int probability) {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadY(1);

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FVectorProducer withUnitZ(int probability) {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadZ(1);

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FVectorProducer withInRange(FPairPos3D range, int probability) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(randomizer.nextDouble3D(range));

            return fVector;
        };

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FVectorProducer withInsideSphere(double radius, int probability) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(randomizer.nextDoubleInSphere(radius));

            return fVector;
        };

        withCustomRule(function, probability);

        return this;
    }

    @Override
    public FVectorProducer withOnSphere(double radius, int probability) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(randomizer.nextDoubleOnSphere(radius));

            return fVector;
        };

        withCustomRule(function, probability);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FVector> stream() {

        return this.processor.stream();
    }

    @Override
    public Iterator<FVector> iterator() {

        return this.processor.getIterator();
    }
}

package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorFactory;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreBasicDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;

public class FVectorProducerDef implements FVectorProducer {

    private final ProducerCoreBasicDef<FVectorProducer, FVector> core;

    private final FRandGenerator random;
    private final FVectorFactory factory;

    private FVectorProducerDef(FVectorFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreBasicDef<>(this, this.random);


    }

    public static FVectorProducer create(FVectorFactory factory, FRandGenerator random) {

        return new FVectorProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FVector, FVector> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FVectorProducer addConfig(Function<FVector, FVector> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FVector produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFVector());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetUnitX() {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadX(1);

        setConfig(function);
    }

    @Override
    public FVectorProducer addPresetUnitX(double probability) {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadX(1);

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetUnitY() {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadY(1);

        setConfig(function);
    }

    @Override
    public FVectorProducer addPresetUnitY(double probability) {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadY(1);

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetUnitZ() {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadZ(1);

        setConfig(function);
    }

    @Override
    public FVectorProducer addPresetUnitZ(double probability) {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadZ(1);

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetRange(FPairPos3D range) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDouble3D(range));

            return fVector;
        };

        setConfig(function);
    }

    @Override
    public FVectorProducer addPresetInRange(FPairPos3D range, double probability) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDouble3D(range));

            return fVector;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetInSphere(double radius) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDoubleInSphere(radius));

            return fVector;
        };

        setConfig(function);
    }

    @Override
    public FVectorProducer addPresetInSphere(double radius, double probability) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDoubleInSphere(radius));

            return fVector;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetOnSphere(double radius) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDoubleOnSphere(radius));

            return fVector;
        };

        setConfig(function);
    }

    @Override
    public FVectorProducer addPresetOnSphere(double radius, double probability) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDoubleOnSphere(radius));

            return fVector;
        };

        addConfig(function, probability);

        return this;
    }
}

package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorFactory;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public class FVectorProducerDef implements FVectorProducer {

    private final ProducerCoreDef<FVectorProducer, FVector> core;

    private final FRandGenerator random;
    private final FVectorFactory factory;

    private FVectorProducerDef(FVectorFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);

        setPresetUnitX();
    }

    public static FVectorProducer create(FVectorFactory factory, FRandGenerator random) {

        return new FVectorProducerDef(factory, random);
    }

    @Override
    public FVectorProducer setConfig(Function<FVector, FVector> function, double probability) {

        return core.setConfig(function, probability);
    }

    @Override
    public FVectorProducer addConfig(Function<FVector, FVector> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FVector produce() {

        return core.getFunction().apply(factory.getFVector());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVectorProducer setPresetUnitX() {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadX(1);

        setConfig(function);

        return this;
    }

    @Override
    public FVectorProducer setPresetUnitY() {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadY(1);

        setConfig(function);

        return this;
    }

    @Override
    public FVectorProducer setPresetUnitZ() {
        Function<FVector, FVector> function = (fVector) -> fVector.setHeadZ(1);

        setConfig(function);

        return this;
    }

    @Override
    public FVectorProducer setPresetInRange(FPos3D base, FPairPos3D range) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDouble3D(range));
            fVector.moveBase(base);

            return fVector;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FVectorProducer setPresetInSphere(FPos3D base, double radius) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDoubleInSphere(radius));
            fVector.moveBase(base);

            return fVector;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FVectorProducer setPresetOnSphere(FPos3D base, double radius) {
        Function<FVector, FVector> function = (fVector) -> {
            fVector.getRefHead().applyStateFrom(random.nextDoubleOnSphere(radius));
            fVector.moveBase(base);

            return fVector;
        };

        setConfig(function);

        return this;
    }
}

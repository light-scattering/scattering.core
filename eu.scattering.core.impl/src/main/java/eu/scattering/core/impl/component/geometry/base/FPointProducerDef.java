package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreBasicDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;

public class FPointProducerDef implements FPointProducer {

    private final ProducerCoreBasicDef<FPointProducer, FPoint> core;

    private final FRandGenerator random;
    private final FPointFactory factory;

    private FPointProducerDef(FPointFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreBasicDef<>(this, this.random);
    }

    public static FPointProducer create(FPointFactory factory, FRandGenerator random) {

        return new FPointProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FPoint, FPoint> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FPointProducer addConfig(Function<FPoint, FPoint> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FPoint produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }


        return core.getFunction().apply(factory.getFPoint());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetZero() {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint;

        setConfig(function);
    }

    @Override
    public FPointProducer addPresetZero(double probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint;

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetRange(FPairPos3D range) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDouble3D(range));

        setConfig(function);
    }

    @Override
    public FPointProducer addPresetInRange(FPairPos3D range, double probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDouble3D(range));

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetInSphere(double radius) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleInSphere(radius));

        setConfig(function);
    }

    @Override
    public FPointProducer addPresetInSphere(double radius, double probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleInSphere(radius));

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetOnSphere(double radius) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleOnSphere(radius));

        setConfig(function);
    }

    @Override
    public FPointProducer addPresetOnSphere(double radius, double probability) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleOnSphere(radius));

        addConfig(function, probability);

        return this;
    }
}

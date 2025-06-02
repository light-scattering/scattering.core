package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;

public class FPointProducerDef implements FPointProducer {

    private final ProducerCoreDef<FPointProducer, FPoint> core;

    private final FRandGenerator random;
    private final FPointFactory factory;

    private FPointProducerDef(FPointFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);

        setPresetDefault();
    }

    public static FPointProducer create(FPointFactory factory, FRandGenerator random) {

        return new FPointProducerDef(factory, random);
    }

    @Override
    public FPointProducer setConfig(Function<FPoint, FPoint> function, double probability) {

        return core.setConfig(function, probability);
    }

    @Override
    public FPointProducer addConfig(Function<FPoint, FPoint> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FPoint produce() {

        return core.getFunction().apply(factory.getFPoint());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPointProducer setPresetDefault() {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint;

        setConfig(function);

        return this;
    }

    @Override
    public FPointProducer setPresetInRange(FPairPos3D range) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDouble3D(range));

        setConfig(function);

        return this;
    }

    @Override
    public FPointProducer setPresetInSphere(double radius) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleInSphere(radius));

        setConfig(function);

        return this;
    }

    @Override
    public FPointProducer setPresetOnSphere(double radius) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleOnSphere(radius));

        setConfig(function);

        return this;
    }
}

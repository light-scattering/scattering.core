package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreBasicDef;

import java.util.function.Function;

public class FRayProducerDef implements FRayProducer {

    private final ProducerCoreBasicDef<FRayProducer, FRay> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FRayProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreBasicDef<>(this, this.random);
    }

    public static FRayProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FRayProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FRay, FRay> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FRayProducer addConfig(Function<FRay, FRay> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FRay produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFRay());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetOX() {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setX(1);

            return fRay;
        };

        setConfig(function);
    }

    @Override
    public FRayProducer addPresetOX(double probability) {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setX(1);

            return fRay;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetOY() {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setY(1);

            return fRay;
        };

        setConfig(function);
    }

    @Override
    public FRayProducer addPresetOY(double probability) {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setY(1);

            return fRay;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetOZ() {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setZ(1);

            return fRay;
        };

        setConfig(function);
    }

    @Override
    public FRayProducer addPresetOZ(double probability) {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setZ(1);

            return fRay;
        };

        addConfig(function, probability);

        return this;
    }
}

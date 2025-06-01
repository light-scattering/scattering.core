package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public class FRayProducerDef implements FRayProducer {

    private final ProducerCoreDef<FRayProducer, FRay> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FRayProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FRayProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FRayProducerDef(factory, random);
    }

    @Override
    public FRayProducer setConfig(Function<FRay, FRay> function) {

        return core.setConfig(function);
    }

    @Override
    public FRayProducer addConfig(Function<FRay, FRay> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FRay produce() {

        return core.getFunction().apply(factory.getFRay());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRayProducer setPresetEmpty() {
        Function<FRay, FRay> function = (fRay) -> fRay;

        setConfig(function);

        return this;
    }

    @Override
    public FRayProducer setPresetUnitX() {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setX(1);

            return fRay;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FRayProducer setPresetUnitY() {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setY(1);

            return fRay;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FRayProducer setPresetUnitZ() {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().setZ(1);

            return fRay;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FRayProducer setPresetFixedPoint(FPos3D point) {
        Function<FRay, FRay> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().applyStateFrom(random.nextDoubleOnSphere(1));
            fRay.getRefOrigin().moveBase(point);

            return fRay;
        };

        setConfig(function);

        return this;
    }
}

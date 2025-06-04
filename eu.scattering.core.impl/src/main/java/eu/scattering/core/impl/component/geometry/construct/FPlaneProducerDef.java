package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreBasicDef;

import java.util.function.Function;

public class FPlaneProducerDef implements FPlaneProducer {

    private final ProducerCoreBasicDef<FPlaneProducer, FPlane> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FPlaneProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreBasicDef<>(this, this.random);
    }

    public static FPlaneProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FPlaneProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FPlane, FPlane> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FPlaneProducer addConfig(Function<FPlane, FPlane> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FPlane produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFPlane());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetDirX() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setX(1);

            return fPlane;
        };

        setConfig(function);
    }

    @Override
    public FPlaneProducer addPresetDirX(double probability) {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setX(1);

            return fPlane;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetDirY() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setY(1);

            return fPlane;
        };

        setConfig(function);
    }

    @Override
    public FPlaneProducer addPresetDirY(double probability) {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setY(1);

            return fPlane;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetDirZ() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setZ(1);

            return fPlane;
        };

        setConfig(function);
    }

    @Override
    public FPlaneProducer addPresetDirZ(double probability) {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setZ(1);

            return fPlane;
        };

        addConfig(function, probability);

        return this;
    }
}

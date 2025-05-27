package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.function.Function;

public class FPlaneProducerDef implements FPlaneProducer {

    private final ProducerCoreDef<FPlaneProducer, FPlane> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FPlaneProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FPlaneProducerDef create(ConstructFactory factory, FRandGenerator random) {

        return new FPlaneProducerDef(factory, random);
    }

    @Override
    public FPlaneProducer setConfig(Function<FPlane, FPlane> function) {

        return core.setConfig(function);
    }

    @Override
    public FPlaneProducer addConfig(Function<FPlane, FPlane> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FPlane produce() {

        return core.getFunction().apply(factory.getFPlane());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPlaneProducer setPresetEmpty() {
        Function<FPlane, FPlane> function = (fPlane) -> fPlane;

        setConfig(function);

        return this;
    }

    @Override
    public FPlaneProducer setPresetUnitX() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setX(1);

            return fPlane;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FPlaneProducer setPresetUnitY() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setY(1);

            return fPlane;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FPlaneProducer setPresetUnitZ() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setZ(1);

            return fPlane;
        };

        setConfig(function);

        return this;
    }
}

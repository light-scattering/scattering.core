package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.plane.FPlaneProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public class FPlaneProducerDef implements FPlaneProducer {

    private final ProducerCoreDef<FPlaneProducer, FPlane> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FPlaneProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);

        setPresetDirX();
    }

    public static FPlaneProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FPlaneProducerDef(factory, random);
    }

    @Override
    public FPlaneProducer setConfig(Function<FPlane, FPlane> function, double probability) {

        return core.setConfig(function, probability);
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
    public FPlaneProducer setPresetDirX() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setX(1);

            return fPlane;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FPlaneProducer setPresetDirY() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setY(1);

            return fPlane;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FPlaneProducer setPresetDirZ() {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().setZ(1);

            return fPlane;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FPlaneProducer setPresetFixedPoint(FPos3D point) {
        Function<FPlane, FPlane> function = (fPlane) -> {
            fPlane.getRefOrigin().getRefHead().applyStateFrom(random.nextDoubleOnSphere(1));
            fPlane.getRefOrigin().moveBase(point);

            return fPlane;
        };

        setConfig(function);

        return this;
    }
}

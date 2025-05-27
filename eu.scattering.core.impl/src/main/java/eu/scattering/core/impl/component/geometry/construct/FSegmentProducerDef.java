package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public class FSegmentProducerDef implements FSegmentProducer {

    private final ProducerCoreDef<FSegmentProducer, FSegment> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FSegmentProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FSegmentProducerDef create(ConstructFactory factory, FRandGenerator random) {

        return new FSegmentProducerDef(factory, random);
    }

    @Override
    public FSegmentProducer setConfig(Function<FSegment, FSegment> function) {

        return core.setConfig(function);
    }

    @Override
    public FSegmentProducer addConfig(Function<FSegment, FSegment> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FSegment produce() {

        return core.getFunction().apply(factory.getFSegment());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSegmentProducer setPresetEmpty() {
        Function<FSegment, FSegment> function = (fSegment) -> fSegment;

        setConfig(function);

        return this;
    }

    @Override
    public FSegmentProducer setPresetUnitX() {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setX(1);

            return fSegment;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FSegmentProducer setPresetUnitY() {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setY(1);

            return fSegment;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FSegmentProducer setPresetUnitZ() {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setZ(1);

            return fSegment;
        };

        setConfig(function);

        return this;
    }

    @Override
    public FSegmentProducer setPresetFixedPoint(FPos3D point, double length) {
        Function<FSegment, FSegment> function = (fRay) -> {
            fRay.getRefOrigin().getRefHead().applyStateFrom(random.nextDoubleOnSphere(length));
            fRay.getRefOrigin().moveBase(point);
            fRay.getRefOrigin().shiftBackward(random.nextDouble(0, length));

            return fRay;
        };

        setConfig(function);

        return this;
    }
}

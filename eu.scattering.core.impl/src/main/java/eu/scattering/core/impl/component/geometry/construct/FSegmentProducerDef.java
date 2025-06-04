package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.construct.segment.FSegmentProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreBasicDef;

import java.util.function.Function;

public class FSegmentProducerDef implements FSegmentProducer {

    private final ProducerCoreBasicDef<FSegmentProducer, FSegment> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FSegmentProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreBasicDef<>(this, this.random);
    }

    public static FSegmentProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FSegmentProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FSegment, FSegment> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FSegmentProducer addConfig(Function<FSegment, FSegment> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FSegment produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFSegment());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetUnitOX() {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setX(1);

            return fSegment;
        };

        setConfig(function);
    }

    @Override
    public FSegmentProducer addPresetUnitOX(double probability) {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setX(1);

            return fSegment;
        };

        addConfig(function, 1);

        return this;
    }

    @Override
    public void setPresetUnitOY() {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setY(1);

            return fSegment;
        };

        setConfig(function);
    }

    @Override
    public FSegmentProducer addPresetUnitOY(double probability) {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setY(1);

            return fSegment;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetUnitOZ() {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setZ(1);

            return fSegment;
        };

        setConfig(function);
    }

    @Override
    public FSegmentProducer addPresetUnitOZ(double probability) {
        Function<FSegment, FSegment> function = (fSegment) -> {
            fSegment.getRefOrigin().getRefHead().setZ(1);

            return fSegment;
        };

        addConfig(function, probability);

        return this;
    }
}

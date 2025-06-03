package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.Function;

public class FLineProducerDef implements FLineProducer {

    private final ProducerCoreDef<FLineProducer, FLine> core;

    private final FRandGenerator random;
    private final ConstructFactory factory;

    private FLineProducerDef(ConstructFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FLineProducer create(ConstructFactory factory, FRandGenerator random) {

        return new FLineProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FLine, FLine> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FLineProducer addConfig(Function<FLine, FLine> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FLine produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFLine());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetOX() {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().setX(1);

            return fLine;
        };

        setConfig(function);
    }

    @Override
    public FLineProducer addPresetOX(double probability) {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().setX(1);

            return fLine;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetOY() {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().setY(1);

            return fLine;
        };

        setConfig(function);
    }

    @Override
    public FLineProducer addPresetOY(double probability) {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().setY(1);

            return fLine;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetOZ() {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().setZ(1);

            return fLine;
        };

        setConfig(function);
    }

    @Override
    public FLineProducer addPresetOZ(double probability) {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().setZ(1);

            return fLine;
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetFixedPoint(FPos3D point) {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().applyStateFrom(random.nextDoubleOnSphere(1));
            fLine.getRefOrigin().moveBase(point);

            return fLine;
        };

        setConfig(function);
    }

    @Override
    public FLineProducer addPresetFixedPoint(FPos3D point, double probability) {
        Function<FLine, FLine> function = (fLine) -> {
            fLine.getRefOrigin().getRefHead().applyStateFrom(random.nextDoubleOnSphere(1));
            fLine.getRefOrigin().moveBase(point);

            return fLine;
        };

        addConfig(function, probability);

        return this;
    }
}

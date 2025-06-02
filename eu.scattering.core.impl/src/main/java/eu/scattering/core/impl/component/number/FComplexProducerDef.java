package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexFactory;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.function.Function;

public class FComplexProducerDef implements FComplexProducer {

    private final ProducerCoreDef<FComplexProducer, FComplex> core;

    private final FRandGenerator random;
    private final FComplexFactory factory;

    private FComplexProducerDef(FComplexFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);

        setPresetDefault();
    }

    public static FComplexProducer create(FComplexFactory factory, FRandGenerator random) {

        return new FComplexProducerDef(factory, random);
    }

    @Override
    public FComplexProducer setConfig(Function<FComplex, FComplex> function, double probability) {

        return core.setConfig(function, probability);
    }

    @Override
    public FComplexProducer addConfig(Function<FComplex, FComplex> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FComplex produce() {

        return core.getFunction().apply(factory.getFComplex());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplexProducer setPresetDefault() {
        Function<FComplex, FComplex> function = (fComplex) -> fComplex;

        setConfig(function);

        return this;
    }
}

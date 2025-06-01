package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionFactory;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.function.Function;

public class FQuaternionProducerDef implements FQuaternionProducer {

    private final ProducerCoreDef<FQuaternionProducer, FQuaternion> core;

    private final FRandGenerator random;
    private final FQuaternionFactory factory;

    private FQuaternionProducerDef(FQuaternionFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FQuaternionProducer create(FQuaternionFactory factory, FRandGenerator random) {

        return new FQuaternionProducerDef(factory, random);
    }

    @Override
    public FQuaternionProducer setConfig(Function<FQuaternion, FQuaternion> function) {

        return core.setConfig(function);
    }

    @Override
    public FQuaternionProducer addConfig(Function<FQuaternion, FQuaternion> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FQuaternion produce() {

        return core.getFunction().apply(factory.getFQuaternion());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternionProducer setPresetEmpty() {
        Function<FQuaternion, FQuaternion> function = (fQuaternion) -> fQuaternion;

        setConfig(function);

        return this;
    }
}

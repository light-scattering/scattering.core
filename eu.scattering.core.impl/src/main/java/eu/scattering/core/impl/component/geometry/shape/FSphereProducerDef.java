package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.function.Function;

public class FSphereProducerDef implements FSphereProducer {

    private final ProducerCoreDef<FSphereProducer, FSphere> core;

    private final FRandGenerator random;
    private final FSphereFactory factory;

    private FSphereProducerDef(FSphereFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);

        setPresetDefault();
    }

    public static FSphereProducer create(FSphereFactory factory, FRandGenerator random) {

        return new FSphereProducerDef(factory, random);
    }

    @Override
    public FSphereProducer setConfig(Function<FSphere, FSphere> function, double probability) {

        return core.setConfig(function, probability);
    }

    @Override
    public FSphereProducer addConfig(Function<FSphere, FSphere> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FSphere produce() {

        return core.getFunction().apply(factory.getFSphere());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer setPresetDefault() {
        Function<FSphere, FSphere> function = (fSphere) -> fSphere;

        setConfig(function);

        return this;
    }

    @Override
    public FSphereProducer setPresetRndRadius(double min, double max) {
        Function<FSphere, FSphere> function = (fSphere) -> fSphere.setRadius(random.nextDouble(min, max));

        setConfig(function);

        return this;
    }
}
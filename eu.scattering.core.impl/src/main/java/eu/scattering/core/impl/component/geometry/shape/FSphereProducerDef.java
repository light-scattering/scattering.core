package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class FSphereProducerDef implements FSphereProducer {

    private final ProducerCoreDef<FSphereProducer, FSphere> core;

    private final FRandGenerator random;
    private final FSphereFactory factory;

    private FSphereProducerDef(FSphereFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.core = new ProducerCoreDef<>(this, this.random);
    }

    public static FSphereProducer create(FSphereFactory factory, FRandGenerator random) {

        return new FSphereProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FSphere, FSphere> function) {

        core.setConfig(function, 1);
    }

    @Override
    public FSphereProducer addConfig(Function<FSphere, FSphere> function, double probability) {

        return core.addConfig(function, probability);
    }

    @Override
    public FSphere produce() {

        if (core.getSize() == 0) {
            throw new IllegalStateException("The producer is not configured");
        }

        return core.getFunction().apply(factory.getFSphere());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void setPresetFixRadius(String tag, double radius) {
        AtomicInteger index = new AtomicInteger(0);

        Function<FSphere, FSphere> function = (fSphere) -> {
            fSphere.setIndex(index.getAndIncrement());
            fSphere.setTag(tag);

            return fSphere.setRadius(radius);
        };

        setConfig(function);
    }

    @Override
    public FSphereProducer addPresetFixRadius(String tag, double radius, double probability) {
        AtomicInteger index = new AtomicInteger(0);

        Function<FSphere, FSphere> function = (fSphere) -> {
            fSphere.setIndex(index.getAndIncrement());
            fSphere.setTag(tag);

            return fSphere.setRadius(radius);
        };

        addConfig(function, probability);

        return this;
    }

    @Override
    public void setPresetRndRadius(String tag, double min, double max) {
        AtomicInteger index = new AtomicInteger(0);

        Function<FSphere, FSphere> function = (fSphere) -> {
            fSphere.setIndex(index.getAndIncrement());
            fSphere.setTag(tag);

            return fSphere.setRadius(random.nextDouble(min, max));
        };

        setConfig(function);
    }

    @Override
    public FSphereProducer addPresetRndRadius(String tag, double min, double max, double probability) {
        AtomicInteger index = new AtomicInteger(0);

        Function<FSphere, FSphere> function = (fSphere) -> {
            fSphere.setIndex(index.getAndIncrement());
            fSphere.setTag(tag);

            return fSphere.setRadius(random.nextDouble(min, max));
        };

        addConfig(function, probability);

        return this;
    }
}
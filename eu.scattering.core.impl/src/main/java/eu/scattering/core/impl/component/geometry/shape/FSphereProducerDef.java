package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.impl.component.support.ProducerCoreDef;
import eu.scattering.core.transfer.container.buffer.cache.FCache;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class FSphereProducerDef implements FSphereProducer {
    private static final Consumer<List<FSphere>> ITERATOR_PROCESSOR;

    static {
        ITERATOR_PROCESSOR =  (list) -> {
            for (int i = 0 ; i < list.size() ; i++) {
                list.get(i).setIndex(i);
            }
        };
    }

    private final FSphereFactory factory;
    private final ProducerCoreDef<FSphere> processor;
    private final FRandEngine rndEngine;

    private String tag = null;
    private FCache cache = null;
    private Double delta = null;
    private Double epsilon = null;

    private boolean createCache = false;

    private FSphereProducerDef(FSphereFactory factory, FRandEngine randomizer) {

        this.factory = factory;
        this.rndEngine = randomizer;
        this.processor = new ProducerCoreDef<>(this.rndEngine.getFRand());
    }

    public static FSphereProducer create(FSphereFactory factory, FRandEngine randomizer) {

        return new FSphereProducerDef(factory, randomizer);
    }

    @Override
    public FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandEngine, FSphere> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndEngine), weight);

        return this;
    }

    @Override
    public FSphere produce() {
        FSphere fSphere = processor.produce();

        if (this.tag != null) {
            fSphere.setMeta(this.tag);
        }

        if (this.delta != null) {
            fSphere.setDelta(this.delta);
        }

        if (this.epsilon != null) {
            fSphere.setEpsilon(this.epsilon);
        }

        if (this.cache != null) {
            fSphere.setCache(this.cache);
        }

        if (this.createCache) {
            fSphere.createCache();
        }

        return fSphere;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer withFixedRadius(double radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FSphere fSphere = factory.getFSphere();

            fSphere.setRadius(radius);

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withDistRadius(FDist1D radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FSphere fSphere = factory.getFSphere();

            fSphere.setRadius(radius.produce());

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndFixedRadius(FDist3D dCenter, double radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> factory.getFSphere(dCenter.produce(), radius);

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndFixedRadius(Producer<FPoint> pCenter, double radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FPoint fPoint = pCenter.produce();
            FSphere fSphere = factory.getRefFSphere(fPoint);

            fSphere.setRadius(radius);

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndDistRadius(FDist3D dCenter, FDist1D radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> factory.getFSphere(dCenter.produce(), radius.produce());

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndDistRadius(Producer<FPoint> pCenter, FDist1D radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FPoint fPoint = pCenter.produce();
            FSphere fSphere = factory.getRefFSphere(fPoint);

            fSphere.setRadius(radius.produce());

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer setTag(String tag) {
        this.tag = tag;

        return this;
    }

    @Override
    public FSphereProducer setDelta(double delta) {
        this.delta = delta;

        return this;
    }

    @Override
    public FSphereProducer setEpsilon(double epsilon) {
        this.epsilon = epsilon;

        return this;
    }

    @Override
    public FSphereProducer setCache(FCache cache) {
        this.createCache = false;
        this.cache = cache;

        return this;
    }

    @Override
    public FSphereProducer createCache() {
        this.createCache = true;
        this.cache = null;

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Stream<FSphere> stream() {

        return this.processor.stream();
    }

    @Override
    public List<FSphere> getListAuto() {

        return this.processor.getListAdopted(ITERATOR_PROCESSOR);
    }

    @Override
    public List<FSphere> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity, ITERATOR_PROCESSOR);
    }

    @Override
    public List<FSphere> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity, ITERATOR_PROCESSOR);
    }
}


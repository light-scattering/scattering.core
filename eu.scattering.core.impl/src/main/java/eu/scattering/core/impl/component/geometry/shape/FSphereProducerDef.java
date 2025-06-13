package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

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

        return processor.produce();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer withFixedRadius(String tag, double radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FSphere fSphere = factory.getFSphere();

            fSphere.setTag(tag);
            fSphere.setRadius(radius);

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withDistRadius(String tag, FDist1D radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FSphere fSphere = factory.getFSphere();

            fSphere.setTag(tag);
            fSphere.setRadius(radius.produce());

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndFixedRadius(String tag, FPointProducer pCenter, double radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FPoint fPoint = pCenter.produce();
            FSphere fSphere = factory.getRefFSphere(fPoint);

            fSphere.setTag(tag);
            fSphere.setRadius(radius);

            return fSphere;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withCenterAndDistRadius(String tag, FPointProducer pCenter, FDist1D radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> {
            FPoint fPoint = pCenter.produce();
            FSphere fSphere = factory.getRefFSphere(fPoint);

            fSphere.setTag(tag);
            fSphere.setRadius(radius.produce());

            return fSphere;
        };

        withCustomRule(function, weight);

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


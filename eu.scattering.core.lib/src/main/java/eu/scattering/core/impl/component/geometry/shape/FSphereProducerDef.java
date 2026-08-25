package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereFactory;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class FSphereProducerDef implements FSphereProducer {
    private static final Consumer<List<FSphere>> MUTATION_ITERATION;

    static {
        MUTATION_ITERATION =  (list) -> {
            for (int i = 0 ; i < list.size() ; i++) {
                list.get(i).setIndex(i);
            }
        };
    }

    private final FSphereFactory factory;
    private final ProducerCoreDef<FSphere> processor;
    private final FRandAspect randomizer;

    private String[] meta = null;

    private Double delta = null;
    private Double epsilon = null;

    private FSphereProducerDef(FSphereFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.randomizer = randomizer;
        this.processor = new ProducerCoreDef<>(this.randomizer.getFRand());

        this.processor.addMutation(MUTATION_ITERATION);
    }

    public static FSphereProducer create(FSphereFactory factory, FRandAspect randomizer) {

        return new FSphereProducerDef(factory, randomizer);
    }

    @Override
    public FSphereProducer withCustomRule(Function<FSphereFactory, FSphere> function, int weight) {

        this.processor.addConfig(() -> updateConfig(function.apply(factory)), weight);

        return this;
    }

    @Override
    public FSphereProducer withCustomRule(BiFunction<FSphereFactory, FRandAspect, FSphere> function, int weight) {

        this.processor.addConfig(() -> updateConfig(function.apply(factory, randomizer)), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphereProducer withFixRadius(double radius, int weight) {

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
    public FSphereProducer withDistCenterAndFixRadius(FDist3D dCenter, double radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> factory.getFSphere(dCenter.produce(), radius);

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withProdCenterAndFixRadius(Producer<FPoint> pCenter, double radius, int weight) {

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
    public FSphereProducer withDistCenterAndDistRadius(FDist3D dCenter, FDist1D radius, int weight) {

        Function<FSphereFactory, FSphere> function = (factory) -> factory.getFSphere(dCenter.produce(), radius.produce());

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FSphereProducer withProdCenterAndDistRadius(Producer<FPoint> pCenter, FDist1D radius, int weight) {

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
    public FSphereProducer setMeta(String... meta) {
        this.meta = meta;

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
    public FSphereProducer mutateAddCoat(double... width) {
        this.processor.addMutation((results) -> results.forEach(e -> e.addCoat(width)));

        return this;
    }

    @Override
    public FSphereProducer correctAddCoat(double... width) {
        this.processor.addCorrection((fSphere, random) -> fSphere.addCoat(width));

        return this;
    }

    @Override
    public FSphereProducer validateNoOverlap() {
        this.processor.addValidation((fSphere, results) -> fSphere.overlaps(results) == 0);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphere produce() {

        return processor.produce();
    }

    @Override
    public List<FSphere> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FSphere> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FSphere> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public FSphereProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FSphereProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FSphereProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FSphere> stream() {

        return this.processor.stream();
    }

    @Override
    public FSphereProducer addMutation(Consumer<List<FSphere>> mutation) {

        this.processor.addMutation(mutation);

        return this;
    }

    @Override
    public FSphereProducer addValidation(BiFunction<FSphere, List<FSphere>, Boolean> validation) {

        this.processor.addValidation(validation);

        return this;
    }

    @Override
    public FSphereProducer addCorrection(BiConsumer<FSphere, FRandGenerator> correction) {

        this.processor.addCorrection(correction);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    private FSphere updateConfig(FSphere fSphere) {

        if (this.meta != null) {
            fSphere.setMeta(this.meta);
        }

        if (this.delta != null) {
            fSphere.setDelta(this.delta);
        }

        if (this.epsilon != null) {
            fSphere.setEpsilon(this.epsilon);
        }

        return fSphere;
    }
}


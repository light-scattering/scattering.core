package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FDist3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class FPointProducerDef implements FPointProducer {

    private final FPointFactory factory;
    private final ProducerCoreDef<FPoint> processor;
    private final FRandGenerator rndGenerator;
    private final FRandAspect rndAspect;

    private FPointProducerDef(FPointFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.rndAspect = randomizer;
        this.rndGenerator = randomizer.generator();
        this.processor = new ProducerCoreDef<>(this.rndGenerator);
    }

    public static FPointProducer create(FPointFactory factory, FRandAspect randomizer) {

        return new FPointProducerDef(factory, randomizer);
    }

    @Override
    public FPointProducer withCustomRule(Function<FPointFactory, FPoint> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FPointProducer withCustomRule(BiFunction<FPointFactory, FRandAspect, FPoint> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndAspect), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPointProducer withZero(int weight) {
        Function<FPointFactory, FPoint> function = FPointFactory::getFPoint;

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withInRange(FPairPos3D range, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().set(rndGenerator.nextDouble3D(range));

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withInSphere(double radius, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().set(rndGenerator.nextDoubleInSphere(radius));

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withInShell(double radiusMin, double radiusMax, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().set(rndGenerator.nextDoubleInShell(radiusMin, radiusMax));

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withOnSphere(double radius, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().set(rndGenerator.nextDoubleOnSphere(radius));

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FPointProducer withDist(FDist3D dist, int weight) {
        Function<FPointFactory, FPoint> function = (factory) ->
                factory.getFPoint().set(dist.produce());

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint produce() {

        return processor.produce();
    }

    @Override
    public List<FPoint> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FPoint> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FPoint> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public FPointProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FPointProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FPointProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FPoint> stream() {

        return this.processor.stream();
    }

    @Override
    public FPointProducer addMutation(Consumer<List<FPoint>> mutation) {

        this.processor.addMutation(mutation);

        return this;
    }

    @Override
    public FPointProducer addValidation(BiFunction<FPoint, List<FPoint>, Boolean> validation) {

        this.processor.addValidation(validation);

        return this;
    }

    @Override
    public FPointProducer addCorrection(BiConsumer<FPoint, FRandGenerator> correction) {

        this.processor.addCorrection(correction);

        return this;
    }
}

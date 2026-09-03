package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorFactory;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.impl.component.support.ProducerCoreDef;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class FVectorProducerDef implements FVectorProducer {

    private final FVectorFactory factory;
    private final ProducerCoreDef<FVector> processor;
    private final FRandEngine rndGenerator;
    private final FRandAspect rndAspect;

    private FVectorProducerDef(FVectorFactory factory, FRandAspect randomizer) {

        this.factory = factory;
        this.rndAspect = randomizer;
        this.rndGenerator = randomizer.engine();
        this.processor = new ProducerCoreDef<>(this.rndGenerator);
    }

    public static FVectorProducer create(FVectorFactory factory, FRandAspect randomizer) {

        return new FVectorProducerDef(factory, randomizer);
    }

    @Override
    public FVectorProducer withCustomRule(Function<FVectorFactory, FVector> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory), weight);

        return this;
    }

    @Override
    public FVectorProducer withCustomRule(BiFunction<FVectorFactory, FRandAspect, FVector> function, int weight) {

        this.processor.addConfig(() -> function.apply(factory, rndAspect), weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVectorProducer withDirOX(double length, int weight) {
        Function<FVectorFactory, FVector> function = (factory) ->
                factory.getFVector().setHeadX(length);

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withDirOY(double length, int weight) {
        Function<FVectorFactory, FVector> function = (factory) ->
                factory.getFVector().setHeadY(length);

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withDirOZ(double length, int weight) {
        Function<FVectorFactory, FVector> function = (factory) ->
                factory.getFVector().setHeadZ(length);

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBaseAndDirOX(FPointProducer pBase, double length, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = base.copy().addX(length);

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBaseAndDirOY(FPointProducer pBase, double length, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = base.copy().addY(length);

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBaseAndDirOZ(FPointProducer pBase, double length, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = base.copy().addZ(length);

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withInSphere(double radius, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FVector fVector = factory.getFVector();

            fVector.getRefHead().set(rndGenerator.nextDoubleInSphere(radius));

            return fVector;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withOnSphere(double radius, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FVector fVector = factory.getFVector();

            fVector.getRefHead().set(rndGenerator.nextDoubleOnSphere(radius));

            return fVector;
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBaseAndInSphere(FPointProducer pBase, double radius, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = base.copy().add(rndGenerator.nextDoubleInSphere(radius));

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBaseAndOnSphere(FPointProducer pBase, double radius, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = base.copy().add(rndGenerator.nextDoubleOnSphere(radius));

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBase(FPointProducer pBase, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = base.copy().set(0, 0, 0);

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withHead(FPointProducer pHead, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint head = pHead.produce();
            FPoint base = head.copy().set(0, 0, 0);

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    @Override
    public FVectorProducer withBaseAndHead(FPointProducer pBase, FPointProducer pHead, int weight) {
        Function<FVectorFactory, FVector> function = (factory) -> {
            FPoint base = pBase.produce();
            FPoint head = pHead.produce();

            return factory.getRefFVector(base, head);
        };

        withCustomRule(function, weight);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector produce() {

        return processor.produce();
    }

    @Override
    public List<FVector> getList() {

        return this.processor.getList();
    }

    @Override
    public List<FVector> getListRandomized(int quantity) {

        return this.processor.getListRandomized(quantity);
    }

    @Override
    public List<FVector> getListFixed(int quantity) {

        return this.processor.getListFixed(quantity);
    }

    @Override
    public FVectorProducer setRetriesLimited(int limit) {

        this.processor.setRetriesLimited(limit);

        return this;
    }

    @Override
    public FVectorProducer setRetriesInfinite() {

        this.processor.setRetriesInfinite();

        return this;
    }

    @Override
    public FVectorProducer setSkipOnFailure(boolean skip) {

        this.processor.setSkipOnFailure(skip);

        return this;
    }

    @Override
    public Stream<FVector> stream() {

        return this.processor.stream();
    }
}

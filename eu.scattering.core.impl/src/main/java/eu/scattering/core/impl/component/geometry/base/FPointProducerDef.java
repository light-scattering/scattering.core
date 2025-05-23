package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointFactory;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FPointProducerDef implements FPointProducer {

    private final FRandGenerator random;
    private final FPointFactory factory;

    private final List<AbstractMap.SimpleEntry<Double, Function<FPoint, FPoint>>> config;

    private FPointProducerDef(FPointFactory factory, FRandGenerator random) {

        this.random = random;
        this.factory = factory;

        this.config = new ArrayList<>();
    }

    public static FPointProducerDef create(FPointFactory factory, FRandGenerator random) {

        return new FPointProducerDef(factory, random);
    }

    @Override
    public void setConfig(Function<FPoint, FPoint> function) {
        config.clear();

        config.add(new AbstractMap.SimpleEntry<>(1.0, function));
    }

    @Override
    public void addConfig(Function<FPoint, FPoint> function, double probability) {

        config.add(new AbstractMap.SimpleEntry<>(probability, function));
    }

    @Override
    public FPoint produce() {

        if (config.isEmpty()) {
            return produceEmpty();
        }

        if (config.size() == 1) {
            return produceDefault();
        }

        return produceRandomized();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPointProducer setPresetInRange(FPairPos3D range) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDouble3D(range));

        setConfig(function);

        return this;
    }

    @Override
    public FPointProducer setPresetInSphere(double radius) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleInSphere(radius));

        setConfig(function);

        return this;
    }

    @Override
    public FPointProducer setPresetOnSphere(double radius) {
        Function<FPoint, FPoint> function = (fPoint) -> fPoint.applyStateFrom(random.nextDoubleOnSphere(radius));

        setConfig(function);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint produceEmpty() {

        return factory.getFPoint();
    }

    private FPoint produceDefault() {

        return config.get(0).getValue().apply(factory.getFPoint());
    }

    private FPoint produceRandomized() {
        double valueMax = config.stream().map(AbstractMap.SimpleEntry::getKey).reduce(0d, Double::sum);
        double valueRandom = random.nextDouble(0, valueMax);

        double value = 0;
        for (var record : config) {
            value += record.getKey();

            if (valueRandom < value) {
                return record.getValue().apply(factory.getFPoint());
            }
        }

        throw new IllegalStateException("The FPoint could not be created");
    }
}

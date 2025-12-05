package eu.scattering.core.test.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.extension.Producer;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FModel PC Filippov")
public class FModelFilippovTest {

    @Test
    @DisplayName("Aggregate 3D")
    void aggregate3D() {
        int quantity = 10;

        FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

        FAssembly<Shape> monitorAssembly = factory.getFAssembly();
        AtomicInteger monitorIndex = new AtomicInteger(0);
        BiConsumer<FAggregate, Shape> monitor = (aggregate, shape) -> {
            if (shape != null) {
                monitorAssembly.register(shape);
                monitorIndex.addAndGet(aggregate.getRefParticles().size());
            }
        };

        BiFunction<FAggregate, Shape, Boolean> validator = (aggregate, shape) ->
                shape.getCenterX() < 2 && shape.getCenterX() > -2;

        AtomicInteger acceptorIndex = new AtomicInteger(0);
        BiFunction<FAggregate, Integer, Boolean> acceptor = (aggregate, iteration) -> {
            acceptorIndex.addAndGet(1);

            if (iteration > 0) {
                return true;
            } else {
                monitorAssembly.clear();
                monitorIndex.set(0);

                return false;
            }
        };

        FModelPCTunable modelTunable = factory.createFModelFilippov3D(fAggregate, 1.8, 1.4);
        modelTunable.setEarlyStageCorrection(true);
        modelTunable.addStepMonitor(monitor);
        modelTunable.addStepAcceptor(validator);
        modelTunable.addCompletionValidator(acceptor);
        modelTunable.build();

        double overlap = fAggregate.getLinearOverlapFactor();

        for (Shape shape : fAggregate.getRefParticles()) {
            assertTrue(shape.getCenterX() < 2 && shape.getCenterX() > -2,
                    "The validator doesn't work as intended");
        }

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(fAggregate.isCompact(),
                        "Particles should be connected"),
                () -> assertEquals(fAggregate.getRefParticles().asList(), monitorAssembly.asList(),
                        "Particle assemblies should be equal (monitor)"),
                () -> assertEquals(45, monitorIndex.get(),
                        "The sum of particle indexes is incorrect (monitor)"),
                () -> assertEquals(2, acceptorIndex.get(),
                        "The number of builds is incorrect"),
                () -> assertEquals(0, overlap,
                        epsilon, "Particles should not overlap")
        );
    }

    @Test
    @DisplayName("Aggregate 2D")
    void aggregate2D() {
        int quantity = 10;

        FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

        FAssembly<Shape> monitorAssembly = factory.getFAssembly();
        AtomicInteger monitorIndex = new AtomicInteger(0);
        BiConsumer<FAggregate, Shape> monitor = (aggregate, shape) -> {
            if (shape != null) {
                monitorAssembly.register(shape);
                monitorIndex.addAndGet(aggregate.getRefParticles().size());
            }
        };

        BiFunction<FAggregate, Shape, Boolean> validator = (aggregate, shape) ->
                shape.getCenterX() < 2;

        AtomicInteger acceptorIndex = new AtomicInteger(0);
        BiFunction<FAggregate, Integer, Boolean> acceptor = (aggregate, iteration) -> {
            acceptorIndex.addAndGet(1);

            if (iteration > 0) {
                return true;
            } else {
                monitorAssembly.clear();
                monitorIndex.set(0);

                return false;
            }
        };

        FModelPCTunable modelTunable = factory.createFModelFilippov2D(fAggregate, 1.5, 1.1);
        modelTunable.setEarlyStageCorrection(true);
        modelTunable.addStepMonitor(monitor);
        modelTunable.addStepAcceptor(validator);
        modelTunable.addCompletionValidator(acceptor);
        modelTunable.build();

        double overlap = fAggregate.getLinearOverlapFactor();

        for (Shape shape : fAggregate.getRefParticles()) {
            assertTrue(shape.getCenterX() < 2,
                    "The validator doesn't work as intended");
        }

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(fAggregate.isCompact(),
                        "Particles should be connected"),
                () -> assertEquals(fAggregate.getRefParticles().asList(), monitorAssembly.asList(),
                        "Particle assemblies should be equal (monitor)"),
                () -> assertEquals(45, monitorIndex.get(),
                        "The sum of particle indexes is incorrect (monitor)"),
                () -> assertEquals(2, acceptorIndex.get(),
                        "The number of builds is incorrect"),
                () -> assertEquals(0, overlap,
                        epsilon, "Particles should not overlap")
        );
    }

    @Test
    @DisplayName("Consistency - Aggregate 3D")
    void consistency3D() {
        int quantity = 10;

        ScatFactory factoryA = FactoryDef.create(123);
        ScatFactory factoryB = FactoryDef.create(123);

        Producer<FSphere> fProducerA = factoryA.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyA = factoryA.getFAssembly(fProducerA.getListRandomized(quantity));
        FAggregate fAggregateA = factoryA.getRefFAggregate(fAssemblyA);

        FModelPC modelA = factoryA.createFModelFilippov3D(fAggregateA, 1.4, 1.4);
        modelA.build();

        Producer<FSphere> fProducerB = factoryB.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factoryB.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB= factoryB.getRefFAggregate(fAssemblyB);

        FModelPC modelB = factoryB.createFModelFilippov3D(fAggregateB, 1.4, 1.4);
        modelB.build();

        assertEquals(fAssemblyA.size(), fAssemblyB.size());

        for (int i = 0 ; i < fAssemblyA.size() ; i++) {
            assertTrue(fAssemblyA.asList().get(i).isExact(fAssemblyB.asList().get(i)));
        }
    }

    @Test
    @DisplayName("Consistency - Aggregate 2D")
    void consistency2D() {
        int quantity = 10;

        ScatFactory factoryA = FactoryDef.create(123);
        ScatFactory factoryB = FactoryDef.create(123);

        Producer<FSphere> fProducerA = factoryA.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyA = factoryA.getFAssembly(fProducerA.getListRandomized(quantity));
        FAggregate fAggregateA = factoryA.getRefFAggregate(fAssemblyA);

        FModelPC modelA = factoryA.createFModelFilippov2D(fAggregateA, 1.4, 1.4);
        modelA.build();

        Producer<FSphere> fProducerB = factoryB.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factoryB.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB= factoryB.getRefFAggregate(fAssemblyB);

        FModelPC modelB = factoryB.createFModelFilippov2D(fAggregateB, 1.4, 1.4);
        modelB.build();

        assertEquals(fAssemblyA.size(), fAssemblyB.size());

        for (int i = 0 ; i < fAssemblyA.size() ; i++) {
            assertTrue(fAssemblyA.asList().get(i).isExact(fAssemblyB.asList().get(i)));
        }
    }

    @Test
    @DisplayName("Tunability - Aggregate 3D")
    void tunability3D() {
        int quantity = 10;

        Producer<FSphere> fProducerA = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyA = factory.getFAssembly(fProducerA.getListRandomized(quantity));
        FAggregate fAggregateA = factory.getRefFAggregate(fAssemblyA);

        FModelPCTunable modelA = factory.createFModelFilippov3D(fAggregateA);
        modelA.setEarlyStageCorrection(true);
        modelA.setDf(2.6);
        modelA.setKf(1.2);
        modelA.build();

        Producer<FSphere> fProducerB = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factory.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB = factory.getRefFAggregate(fAssemblyB);

        FModelPCTunable modelB = factory.createFModelFilippov3D(fAggregateB);
        modelB.setEarlyStageCorrection(true);
        modelB.setDf(1.4);
        modelB.setKf(1.8);
        modelB.build();

        double rangeA = fAggregateA.getRadius(fAggregateA.getMassCenter());
        double rangeB = fAggregateB.getRadius(fAggregateB.getMassCenter());

        assertTrue(rangeA < rangeB);
    }

    @Test
    @DisplayName("Tunability - Aggregate 2D")
    void tunability2D() {
        int quantity = 10;

        Producer<FSphere> fProducerA = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyA = factory.getFAssembly(fProducerA.getListRandomized(quantity));
        FAggregate fAggregateA = factory.getRefFAggregate(fAssemblyA);

        FModelPCTunable modelA = factory.createFModelFilippov2D(fAggregateA);
        modelA.setEarlyStageCorrection(true);
        modelA.setDf(1.8);
        modelA.setKf(1.2);
        modelA.build();

        Producer<FSphere> fProducerB = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factory.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB = factory.getRefFAggregate(fAssemblyB);

        FModelPCTunable modelB = factory.createFModelFilippov2D(fAggregateB);
        modelB.setEarlyStageCorrection(true);
        modelB.setDf(1.2);
        modelB.setKf(1.8);
        modelB.build();

        double rangeA = fAggregateA.getRadius(fAggregateA.getMassCenter());
        double rangeB = fAggregateB.getRadius(fAggregateB.getMassCenter());

        assertTrue(rangeA < rangeB);
    }

    @Test
    @DisplayName("Configuration - Aggregate 3D")
    void configuration3D() {
        FAggregate fAggregate = factory.getFAggregatePreMono(10, 1);
        FModelPCTunable model = factory.createFModelFilippov3D(fAggregate);

        model.setDf(2.2);
        model.setKf(1.1);
        model.setEarlyStageCorrection(true);

        assertEquals(2.2, model.getDf());
        assertEquals(1.1, model.getKf());
        assertTrue(model.getEarlyStateCorrection());
    }

    @Test
    @DisplayName("Configuration - Aggregate 2D")
    void configuration2D() {
        FAggregate fAggregate = factory.getFAggregatePreMono(10, 1);
        FModelPCTunable model = factory.createFModelFilippov2D(fAggregate);

        model.setDf(2.2);
        model.setKf(1.1);
        model.setEarlyStageCorrection(true);

        assertEquals(2.2, model.getDf());
        assertEquals(1.1, model.getKf());
        assertTrue(model.getEarlyStateCorrection());
    }
}

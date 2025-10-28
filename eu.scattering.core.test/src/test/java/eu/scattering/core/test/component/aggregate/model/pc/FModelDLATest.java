package eu.scattering.core.test.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelDLA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.extension.Producer;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel DLA")
public class FModelDLATest {

    @Test
    @DisplayName("Aggregate 3D")
    void aggregate3D() {
        int quantity = 10;

        FAggregate fAggregate = factory.getFAggregateMono(quantity, 1, 0);

        FAssembly<Shape> monitorAssembly = factory.getFAssembly();
        AtomicInteger monitorIndex = new AtomicInteger(0);
        BiConsumer<FAssembly<Shape>, Shape> monitor = (assembly, shape) -> {
            if (shape != null) {
                monitorAssembly.register(shape);
                monitorIndex.addAndGet(assembly.size());
            }
        };

        BiFunction<FAssembly<Shape>, Shape, Boolean> validator = (assembly, shape) ->
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

        FModelDLA modelDLA = factory.createFModelDLA3D(fAggregate);
        modelDLA.addStepMonitor(monitor);
        modelDLA.addStepValidator(validator);
        modelDLA.addCompletionValidator(acceptor);
        modelDLA.build();

        double overlap = fAggregate.getOverlapFactorLinear();

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

        FAggregate fAggregate = factory.getFAggregateMono(quantity, 1, 0);

        FAssembly<Shape> monitorAssembly = factory.getFAssembly();
        AtomicInteger monitorIndex = new AtomicInteger(0);
        BiConsumer<FAssembly<Shape>, Shape> monitor = (assembly, shape) -> {
            if (shape != null) {
                monitorAssembly.register(shape);
                monitorIndex.addAndGet(assembly.size());
            }
        };

        BiFunction<FAssembly<Shape>, Shape, Boolean> validator = (assembly, shape) ->
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

        FModelDLA modelDLA = factory.createFModelDLA2D(fAggregate);
        modelDLA.addStepMonitor(monitor);
        modelDLA.addStepValidator(validator);
        modelDLA.addCompletionValidator(acceptor);
        modelDLA.build();

        double overlap = fAggregate.getOverlapFactorLinear();

        for (Shape shape : fAggregate.getRefParticles()) {
            assertEquals(0, shape.getCenterZ(),
                    "At least one particle has a non-zero Z value");
        }

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
    @DisplayName("Consistency - Aggregate 3D")
    void consistency3D() {
        int quantity = 10;

        ScatFactory factoryA = FactoryDef.create(123);
        ScatFactory factoryB = FactoryDef.create(123);

        Producer<FSphere> fProducerA = factoryA.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyA = factoryA.getFAssembly(fProducerA.getListRandomized(quantity));
        FAggregate fAggregateA = factoryA.getRefFAggregate(fAssemblyA, 0);

        FModelPC modelA = factoryA.createFModelDLA3D(fAggregateA);
        modelA.build();

        Producer<FSphere> fProducerB = factoryB.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factoryB.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB = factoryB.getRefFAggregate(fAssemblyB, 0);

        FModelPC modelB = factoryB.createFModelDLA3D(fAggregateB);
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
        FAggregate fAggregateA = factoryA.getRefFAggregate(fAssemblyA, 0);

        FModelPC modelA = factoryA.createFModelDLA2D(fAggregateA);
        modelA.build();

        Producer<FSphere> fProducerB = factoryB.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factoryB.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB = factoryB.getRefFAggregate(fAssemblyB, 0);

        FModelPC modelB = factoryB.createFModelDLA2D(fAggregateB);
        modelB.build();

        assertEquals(fAssemblyA.size(), fAssemblyB.size());

        for (int i = 0 ; i < fAssemblyA.size() ; i++) {
            assertTrue(fAssemblyA.asList().get(i).isExact(fAssemblyB.asList().get(i)));
        }
    }

    @Test
    @DisplayName("Configuration - Aggregate 3D")
    void configuration3D() {
        FAggregate fAggregate = factory.getFAggregateMono(10, 1, 0);
        FModelDLA model = factory.createFModelDLA3D(fAggregate);

        TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement = (assembly, random, point) -> point.add(1, 2, 3);

        model.setStep(1.1);
        model.setExileFactor(3.3);
        model.setSpawnFactor(2.2);
        model.setMovement(movement);

        assertEquals(1.1, model.getStep());
        assertEquals(3.3, model.getExileFactor());
        assertEquals(2.2, model.getSpawnFactor());
        assertSame(movement, model.getMovement());
    }

    @Test
    @DisplayName("Configuration - Aggregate 2D")
    void configuration2D() {
        FAggregate fAggregate = factory.getFAggregateMono(10, 1, 0);
        FModelDLA model = factory.createFModelDLA2D(fAggregate);

        TriConsumer<FAssembly<Shape>, FRandEngine, FPoint> movement = (assembly, random, point) -> point.add(1, 2, 3);

        model.setStep(1.1);
        model.setExileFactor(3.3);
        model.setSpawnFactor(2.2);
        model.setMovement(movement);

        assertEquals(1.1, model.getStep());
        assertEquals(3.3, model.getExileFactor());
        assertEquals(2.2, model.getSpawnFactor());
        assertSame(movement, model.getMovement());
    }
}

package eu.scattering.core.test.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.util.lambda.TriFunction;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FModel PC RLA")
public class FModelRLATest {

    @Test
    @DisplayName("Aggregate 3D")
    void aggregate3D() {
        int quantity = 10;

        FAggregate fAggregate = factory.getFAggregateMono(quantity, 1, 0);

        FAssembly<Shape> monitorAssembly = factory.getFAssembly();
        AtomicInteger monitorIndex = new AtomicInteger(0);
        BiConsumer<Shape, Integer> monitor = (shape, index) -> {
            monitorAssembly.register(shape);
            monitorIndex.addAndGet(index);
        };

        TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validator = (assembly, random, shape) ->
                shape.getCenterX() < 2 && shape.getCenterX() > -2;

        FModelPC modelRLA = factory.createFModelRLA3D(fAggregate);
        modelRLA.setMonitor(monitor);
        modelRLA.setValidator(validator);
        modelRLA.build();

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
        BiConsumer<Shape, Integer> monitor = (shape, index) -> {
            monitorAssembly.register(shape);
            monitorIndex.addAndGet(index);
        };

        TriFunction<FAssembly<Shape>, FRandEngine, Shape, Boolean> validator = (assembly, random, shape) ->
                shape.getCenterX() < 2 && shape.getCenterX() > -2;

        FModelPC modelRLA = factory.createFModelRLA2D(fAggregate);
        modelRLA.setMonitor(monitor);
        modelRLA.setValidator(validator);
        modelRLA.build();

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

        FModelPC modelA = factoryA.createFModelRLA3D(fAggregateA);
        modelA.build();

        Producer<FSphere> fProducerB = factoryB.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factoryB.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB = factoryB.getRefFAggregate(fAssemblyB, 0);

        FModelPC modelB = factoryB.createFModelRLA3D(fAggregateB);
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

        FModelPC modelA = factoryA.createFModelRLA2D(fAggregateA);
        modelA.build();

        Producer<FSphere> fProducerB = factoryB.getFSphereProducer(1);
        FAssembly<Shape> fAssemblyB = factoryB.getFAssembly(fProducerB.getListRandomized(quantity));
        FAggregate fAggregateB = factoryB.getRefFAggregate(fAssemblyB, 0);

        FModelPC modelB = factoryB.createFModelRLA2D(fAggregateB);
        modelB.build();

        assertEquals(fAssemblyA.size(), fAssemblyB.size());

        for (int i = 0 ; i < fAssemblyA.size() ; i++) {
            assertTrue(fAssemblyA.asList().get(i).isExact(fAssemblyB.asList().get(i)));
        }
    }
}

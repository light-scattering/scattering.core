package eu.scattering.core.test.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.type.Dimension;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel PC DLA")
public class FModelPCDLATest {

    @Disabled
    @Nested
    @Tag("Heavy")
    @DisplayName("Aggregation 3D - Heavy")
    class AggregationHeavyTest {

        @RepeatedTest(100)
        @DisplayName("Results")
        void results3DA() {
            int size = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().pc().dla(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @RepeatedTest(10)
        @DisplayName("Results")
        void results3DB() {
            int size = 10000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().pc().dla(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @RepeatedTest(100)
        @DisplayName("Results")
        void results2DA() {
            int size = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @RepeatedTest(10)
        @DisplayName("Results")
        void results2DB() {
            int size = 10000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }
    }

    @Nested
    @Tag("Aggregation 3D")
    @DisplayName("Aggregation 3D")
    class Aggregation3DTest {

        @Test
        @DisplayName("Results")
        void results() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().pc().dla(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 28;

            ScatFactory factoryA = FactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.getFModelContext().pc().dla(fAggregateA);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.getFModelContext().pc().dla(fAggregateB);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(fAggregate);

            AtomicInteger quantity = new AtomicInteger(0);

            BiConsumer<FAggregate, Shape> monitor = (aggregate, shape) -> {

                if (aggregate != null && shape != null) {
                    quantity.addAndGet(aggregate.getRefParticles().size());
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(45, quantity.get());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(fAggregate);

            Set<Shape> particles = new HashSet<>(fAggregate.size());

            BiConsumer<FAggregate, Shape> monitor = (agg, shape) -> {

                if (agg != null) {
                    agg.forEach(particles::add);
                }

                if (shape != null) {
                    particles.add(shape);
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertEquals(particles.size(), fAggregate.size());

            for (Shape particle : fAggregate) {
                assertFalse(particles.add(particle));
            }
        }

        @Test
        @DisplayName("Acceptor - A")
        void acceptorA() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(9 * 2, iteration.get());
        }

        @Test
        @DisplayName("Acceptor - B")
        void acceptorB() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(fAggregate);

            BiFunction<FAggregate, Shape, Boolean> acceptor = (aggregate, shape) ->
                    shape.getCenterX() < 2 && shape.getCenterX() > -2;

            fModel.addStepAcceptor(acceptor);
            fModel.build();

            for (Shape shape : fAggregate.getRefParticles()) {
                assertTrue(shape.getCenterX() < 2 && shape.getCenterX() > -2);
            }
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Configuration - Aggregate 3D")
        void configuration3D() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(10, 1);
            FModelPCDLA model = factory.getFModelContext().pc().dla(fAggregate);

            TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> movement = (assembly, random, point) -> point.add(1, 2, 3);

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

    @Nested
    @Tag("Aggregation 2D")
    @DisplayName("Aggregation 2D")
    class Aggregation2DTest {

        @RepeatedTest(1000)
        @DisplayName("Results")
        void results() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 28;

            ScatFactory factoryA = FactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.getFModelContext().pc().dla(Dimension.D2, fAggregateA);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.getFModelContext().pc().dla(Dimension.D2, fAggregateB);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            AtomicInteger quantity = new AtomicInteger(0);

            BiConsumer<FAggregate, Shape> monitor = (aggregate, shape) -> {

                if (aggregate != null && shape != null) {
                    quantity.addAndGet(aggregate.getRefParticles().size());
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(45, quantity.get());
            assertEquals(0, fAggregate.getQuantitativeOverlapFactor());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            Set<Shape> particles = new HashSet<>(fAggregate.size());

            BiConsumer<FAggregate, Shape> monitor = (agg, shape) -> {

                if (agg != null) {
                    agg.forEach(particles::add);
                }

                if (shape != null) {
                    particles.add(shape);
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertEquals(particles.size(), fAggregate.size());

            for (Shape particle : fAggregate) {
                assertFalse(particles.add(particle));
            }
        }

        @Test
        @DisplayName("Acceptor - A")
        void acceptorA() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(9 * 2, iteration.get());
        }

        @Test
        @DisplayName("Acceptor - B")
        void acceptorB() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            BiFunction<FAggregate, Shape, Boolean> acceptor = (aggregate, shape) ->
                    shape.getCenterX() < 2 && shape.getCenterX() > -2;

            fModel.addStepAcceptor(acceptor);
            fModel.build();

            for (Shape shape : fAggregate.getRefParticles()) {
                assertTrue(shape.getCenterX() < 2 && shape.getCenterX() > -2);
            }
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPC fModel = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Configuration - Aggregate 2D")
        void configuration2D() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(10, 1);
            FModelPCDLA model = factory.getFModelContext().pc().dla(Dimension.D2, fAggregate);

            TriConsumer<FAssembly<Shape>, FRandAspect, FPoint> movement = (assembly, random, point) -> point.add(1, 2, 3);

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
}

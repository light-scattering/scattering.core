package eu.scattering.core.test.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.Dimension;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel PC tunable")
public class FModelPCTunableTest {

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
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

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
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

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
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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
            FModelPCTunable fModelA = factoryA.getFModelContext().pc().tunable(fAggregateA, 1.8, 1.6);
            fModelA.setEarlyStageCorrection(true);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPCTunable fModelB = factoryB.getFModelContext().pc().tunable(fAggregateB, 1.8, 1.6);
            fModelB.setEarlyStageCorrection(true);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(9 * 2, iteration.get());
        }

        @Test
        @DisplayName("Acceptor - B")
        void acceptorB() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Tunability")
        void tunability() {
            int size = 28;

            FAggregate fAggregateA = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModelA = factory.getFModelContext().pc().tunable(fAggregateA, 2.4, 1.0);
            fModelA.setEarlyStageCorrection(true);

            FAggregate fAggregateB = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModelB = factory.getFModelContext().pc().tunable(fAggregateB, 1.8, 1.6);
            fModelB.setEarlyStageCorrection(true);

            FAggregate fAggregateC = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModelC = factory.getFModelContext().pc().tunable(fAggregateC, 1.2, 2.2);
            fModelC.setEarlyStageCorrection(true);

            fModelA.build();
            fModelB.build();
            fModelC.build();

            double rangeA = fAggregateA.getRadius(Center.MASS);
            double rangeB = fAggregateB.getRadius(Center.MASS);
            double rangeC = fAggregateC.getRadius(Center.MASS);

            assertTrue(rangeA < rangeB);
            assertTrue(rangeB < rangeC);
        }
    }

    @Nested
    @Tag("Aggregation 2D")
    @DisplayName("Aggregation 2D")
    class Aggregation2DTest {

        @Test
        @DisplayName("Results")
        void results() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

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
            FModelPCTunable fModelA = factoryA.getFModelContext().pc().tunable(Dimension.D2, fAggregateA, 1.6, 1.4);
            fModelA.setEarlyStageCorrection(true);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelPCTunable fModelB = factoryB.getFModelContext().pc().tunable(Dimension.D2, fAggregateB, 1.6, 1.4);
            fModelB.setEarlyStageCorrection(true);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

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
            assertEquals(0, fAggregate.getLinearOverlapFactor(), epsilon);
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

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

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(9 * 2, iteration.get());
        }

        @Test
        @DisplayName("Acceptor - B")
        void acceptorB() {
            int size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size,1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.4, 1.4);
            fModel.setEarlyStageCorrection(true);

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

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModel = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Tunability")
        void tunability() {
            int size = 28;

            FAggregate fAggregateB = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModelB = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregateB, 1.6, 1.4);
            fModelB.setEarlyStageCorrection(true);

            FAggregate fAggregateC = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelPCTunable fModelC = factory.getFModelContext().pc().tunable(Dimension.D2, fAggregateC, 1.2, 1.8);
            fModelC.setEarlyStageCorrection(true);

            fModelB.build();
            fModelC.build();

            double rangeB = fAggregateB.getRadius(Center.MASS);
            double rangeC = fAggregateC.getRadius(Center.MASS);

            assertTrue(rangeB < rangeC);
        }
    }
}

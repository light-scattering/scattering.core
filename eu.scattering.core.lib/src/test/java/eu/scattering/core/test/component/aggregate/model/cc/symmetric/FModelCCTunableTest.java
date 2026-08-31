package eu.scattering.core.test.component.aggregate.model.cc.symmetric;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.impl.factory.ScatterFactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel CC RLCA")
public class FModelCCTunableTest {

    @Disabled
    @Nested
    @DisplayName("Aggregation 3D - Predefined")
    class AggregationPredefinedTest {

        @Test
        @DisplayName("Aggregation 3D - Visual")
        void results3DA() {
            int size = 3000;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            String json = fAggregate.toJSON().toString();
            String model = factory.getSaveAspect().getComponentContext().toNGSolve(fAggregate);

            assertTrue(json.length() > 0);
            assertTrue(model.length() > 0);
        }

        @Test
        @DisplayName("Aggregation 2D - Visual")
        void results2DA() {
            int size = 3000;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            fModel.build();

            String json = fAggregate.toJSON().toString();
            String model = factory.getSaveAspect().getComponentContext().toNGSolve(fAggregate);

            assertTrue(json.length() > 0);
            assertTrue(model.length() > 0);
        }
    }

    @Disabled
    @Nested
    @DisplayName("Aggregation 3D - Heavy")
    class AggregationHeavyTest {

        @RepeatedTest(100)
        @DisplayName("Results")
        void results3DA() {
            int size = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @RepeatedTest(10)
        @DisplayName("Results")
        void results3DB() {
            int size = 6000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @RepeatedTest(100)
        @DisplayName("Results")
        void results2DA() {
            int size = 1000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @RepeatedTest(10)
        @DisplayName("Results")
        void results2DB() {
            int size = 6000;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }
    }

    @Nested
    @DisplayName("Aggregation 3D")
    class Aggregation3DTest {

        @Test
        @DisplayName("Results")
        void results() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 32;

            ScatterFactory factoryA = ScatterFactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelA = factoryA.getFModelContext().cc().tunable(fAggregateA, 1.8, 1.6);
            fModelA.setEarlyStageCorrection(true);

            ScatterFactory factoryB = ScatterFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelB = factoryB.getFModelContext().cc().tunable(fAggregateB, 1.8, 1.6);
            fModelB.setEarlyStageCorrection(true);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 32;
            int sizeFragment = 5;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger fragments = new AtomicInteger(0);
            AtomicInteger steps = new AtomicInteger(0);

            TriConsumer<FAggregate, FAggregate, Integer> monitor = (aggA, aggB, index) -> {

                if (aggA == null) {
                    fragments.incrementAndGet();
                } else if (aggB != null) {
                    steps.incrementAndGet();
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertEquals(size / sizeFragment, fragments.get());
            assertEquals(5, steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            Set<Shape> particles = new HashSet<>(fAggregate.size());

            TriConsumer<FAggregate, FAggregate, Integer> monitor = (aggA, aggB, index) -> {

               if (aggA != null) {
                   aggA.forEach(particles::add);
               }

                if (aggB != null) {
                    aggB.forEach(particles::add);
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
        @DisplayName("Acceptor")
        void acceptor() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(5 * 2, iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
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
            int size = 32;

            FAggregate fAggregateA = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModelA = factory.getFModelContext().cc().tunable(fAggregateA, 2.4, 1.0);
            fModelA.setEarlyStageCorrection(true);

            FAggregate fAggregateB = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModelB = factory.getFModelContext().cc().tunable(fAggregateB, 1.8, 1.6);
            fModelB.setEarlyStageCorrection(true);

            FAggregate fAggregateC = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModelC = factory.getFModelContext().cc().tunable(fAggregateC, 1.2, 2.2);
            fModelC.setEarlyStageCorrection(true);

            fModelA.build();
            fModelB.build();
            fModelC.build();

            double rangeA = fAggregateA.getRadiusFrom(Center.SPHERE);
            double rangeB = fAggregateB.getRadiusFrom(Center.SPHERE);
            double rangeC = fAggregateC.getRadiusFrom(Center.SPHERE);

            assertTrue(rangeA < rangeB);
            assertTrue(rangeB < rangeC);
        }

        @Test
        @DisplayName("Configuration")
        void configuration() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);

            assertTrue(fModel.getSymmetry());
            assertFalse(fModel.getEarlyStageCorrection());
            assertFalse(fModel.getCorrection());

            fModel.setSymmetry(true);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            assertTrue(fModel.getSymmetry());
            assertTrue(fModel.getEarlyStageCorrection());
            assertTrue(fModel.getCorrection());
        }
    }

    @Nested
    @DisplayName("Aggregation 2D")
    class Aggregation2DTest {

        @Test
        @DisplayName("Results")
        void results() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            fModel.build();

            for (Shape shape : fAggregate) {
                assertEquals(0, shape.getCenterZ(), 1E-8);
            }

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 32;

            ScatterFactory factoryA = ScatterFactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelA = factoryA.getFModelContext().cc().tunable(Dimension.D2, fAggregateA, 1.5, 1.5);
            fModelA.setEarlyStageCorrection(true);
            fModelA.setCorrection(true);

            ScatterFactory factoryB = ScatterFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelB = factoryB.getFModelContext().cc().tunable(Dimension.D2, fAggregateB, 1.5, 1.5);
            fModelB.setEarlyStageCorrection(true);
            fModelB.setCorrection(true);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 32;
            int sizeFragment = 5;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            AtomicInteger fragments = new AtomicInteger(0);
            AtomicInteger steps = new AtomicInteger(0);

            TriConsumer<FAggregate, FAggregate, Integer> monitor = (aggA, aggB, index) -> {

                if (aggA == null) {
                    fragments.incrementAndGet();
                } else if (aggB != null) {
                    steps.incrementAndGet();
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertEquals(size / sizeFragment, fragments.get());
            assertEquals(5, steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            Set<Shape> particles = new HashSet<>(fAggregate.size());

            TriConsumer<FAggregate, FAggregate, Integer> monitor = (aggA, aggB, index) -> {

                if (aggA != null) {
                    aggA.forEach(particles::add);
                }

                if (aggB != null) {
                    aggB.forEach(particles::add);
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
        @DisplayName("Acceptor")
        void acceptor() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(5 * 2, iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.5, 1.5);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3 ,iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Tunability")
        void tunability() {
            int size = 32;

            FAggregate fAggregateB = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelB = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregateB, 1.8, 1.6);
            fModelB.setEarlyStageCorrection(true);
            fModelB.setCorrection(true);

            FAggregate fAggregateC = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelC = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregateC, 1.2, 2.2);
            fModelC.setEarlyStageCorrection(true);
            fModelC.setCorrection(true);

            fModelB.build();
            fModelC.build();

            double rangeB = fAggregateB.getRadiusFrom(Center.MASS);
            double rangeC = fAggregateC.getRadiusFrom(Center.MASS);

            assertTrue(rangeB < rangeC);
        }

        @Test
        @DisplayName("Configuration")
        void configuration() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.8, 1.6);

            assertTrue(fModel.getSymmetry());
            assertFalse(fModel.getEarlyStageCorrection());
            assertFalse(fModel.getCorrection());

            fModel.setSymmetry(true);
            fModel.setEarlyStageCorrection(true);
            fModel.setCorrection(true);

            assertTrue(fModel.getSymmetry());
            assertTrue(fModel.getEarlyStageCorrection());
            assertTrue(fModel.getCorrection());
        }
    }
}

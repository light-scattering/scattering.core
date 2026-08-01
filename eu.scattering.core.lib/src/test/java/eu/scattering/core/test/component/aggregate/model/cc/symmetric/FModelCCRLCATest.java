package eu.scattering.core.test.component.aggregate.model.cc.symmetric;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.cc.rlca.FModelCCRLCA;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.impl.factory.ScatterFactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel CC RLCA")
public class FModelCCRLCATest {

    @Disabled
    @Nested
    @DisplayName("Aggregation 3D - Predefined")
    class AggregationPredefinedTest {

        @Test
        @DisplayName("Aggregation 3D - Visual")
        void results3DA() {
            int size = 3000;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCRLCA fModel = factory.getFModelContext().cc().rlca(fAggregate);

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
            FModelCCRLCA fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

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
            FModel fModel = factory.getFModelContext().cc().rlca(fAggregate);

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
            FModel fModel = factory.getFModelContext().cc().rlca(fAggregate);

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
            FModel fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

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
            FModel fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

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
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().cc().rlca(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 28;

            ScatterFactory factoryA = ScatterFactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.getFModelContext().cc().rlca(fAggregateA);

            ScatterFactory factoryB = ScatterFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.getFModelContext().cc().rlca(fAggregateB);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 28;
            int sizeFragment = 3;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(fAggregate);

            AtomicInteger fragments = new AtomicInteger(0);
            AtomicInteger steps = new AtomicInteger(0);

            BiConsumer<FAggregate, FAggregate> monitor = (aggA, aggB) -> {

                if (aggA == null) {
                    fragments.incrementAndGet();
                } else if (aggB != null) {
                    steps.incrementAndGet();
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertEquals(size / sizeFragment, fragments.get());
            assertEquals(8, steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(fAggregate);

            Set<Shape> particles = new HashSet<>(fAggregate.size());

            BiConsumer<FAggregate, FAggregate> monitor = (aggA, aggB) -> {

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
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(8 * 2, iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Configuration")
        void configuration() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(fAggregate);

            fModel.setSymmetry(true);

            assertTrue(fModel.getSymmetry());
        }
    }

    @Nested
    @DisplayName("Aggregation 2D")
    class Aggregation2DTest {

        @Test
        @DisplayName("Results")
        void results() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

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
            int size = 28;

            ScatterFactory factoryA = ScatterFactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.getFModelContext().cc().rlca(Dimension.D2, fAggregateA);

            ScatterFactory factoryB = ScatterFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.getFModelContext().cc().rlca(Dimension.D2, fAggregateB);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 28;
            int sizeFragment = 3;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

            AtomicInteger fragments = new AtomicInteger(0);
            AtomicInteger steps = new AtomicInteger(0);

            BiConsumer<FAggregate, FAggregate> monitor = (aggA, aggB) -> {

                if (aggA == null) {
                    fragments.incrementAndGet();
                } else if (aggB != null) {
                    steps.incrementAndGet();
                }
            };

            fModel.addStepMonitor(monitor);
            fModel.build();

            assertEquals(size / sizeFragment, fragments.get());
            assertEquals(8, steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

            Set<Shape> particles = new HashSet<>(fAggregate.size());

            BiConsumer<FAggregate, FAggregate> monitor = (aggA, aggB) -> {

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
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(8 * 2, iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Configuration")
        void configuration() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCC fModel = factory.getFModelContext().cc().rlca(Dimension.D2, fAggregate);

            fModel.setSymmetry(true);

            assertTrue(fModel.getSymmetry());
        }
    }
}

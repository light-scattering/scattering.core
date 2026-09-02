package eu.scattering.core.test.component.aggregate.model.cc.symmetric;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.impl.factory.ScatterFactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel CC ballistic")
public class FModelCCBallisticTest {

    @Disabled
    @Nested
    @DisplayName("Aggregation 3D - Predefined")
    class AggregationPredefinedTest {

        @Test
        @DisplayName("Aggregation 3D - Visual")
        void results3DA() {
            int size = 3000;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(size, 1);
            FModel fModel = factory.models().cc().ballistic(fAggregate);

            fModel.build();

            String json = fAggregate.toJSON().toString();
            String model = factory.save().components().toNGSolve(fAggregate);

            assertFalse(json.isEmpty());
            assertFalse(model.isEmpty());
        }

        @Test
        @DisplayName("Aggregation 2D - Visual")
        void results2DA() {
            int size = 3000;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(size, 1);
            FModel fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

            fModel.build();

            String json = fAggregate.toJSON().toString();
            String model = factory.save().components().toNGSolve(fAggregate);

            assertFalse(json.isEmpty());
            assertFalse(model.isEmpty());
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

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModel = factory.models().cc().ballistic(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @RepeatedTest(10)
        @DisplayName("Results")
        void results3DB() {
            int size = 60000;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModel = factory.models().cc().ballistic(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @RepeatedTest(100)
        @DisplayName("Results")
        void results2DA() {
            int size = 6000;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @RepeatedTest(10)
        @DisplayName("Results")
        void results2DB() {
            int size = 10000;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

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

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModel = factory.models().cc().ballistic(fAggregate);

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

            FAggregate fAggregateA = factoryA.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.models().cc().ballistic(fAggregateA);

            ScatterFactory factoryB = ScatterFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.models().cc().ballistic(fAggregateB);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Viewer")
        void viewer() {
            int size = 28;
            int sizeFragment = 3;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(fAggregate);

            AtomicInteger fragments = new AtomicInteger(0);

            Consumer<FAggregate> viewer = (fragment) -> fragments.incrementAndGet();
            Consumer<FAggregate> validator = (fragment) -> assertTrue(fragment.size() > 0);

            fModel.addFragmentViewer(List.of(viewer, validator));
            fModel.build();

            assertEquals(size / sizeFragment, fragments.get());
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(fAggregate);

            AtomicInteger stepsCount = new AtomicInteger(0);
            AtomicInteger stepsIndex = new AtomicInteger(0);

            TriConsumer<FAggregate, FAggregate, Integer> monitor = (aggA, aggB, index) -> {
                stepsCount.incrementAndGet();
                stepsIndex.set(index);
            };

            TriConsumer<FAggregate, FAggregate, Integer> monitorDummyA = (aggA, aggB, index) -> {};
            TriConsumer<FAggregate, FAggregate, Integer> monitorDummyB = (aggA, aggB, index) -> {};

            fModel.addStepMonitor(monitor);
            fModel.addStepMonitor(List.of(monitorDummyA, monitorDummyB));

            fModel.build();

            assertEquals(8, stepsCount.get() - 1);
            assertEquals(8, stepsIndex.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(fAggregate);

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
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(8 * 2, iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(fAggregate);

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

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(size, 1);
            FModelCC fModel = factory.models().cc().ballistic(fAggregate);

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

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

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

            FAggregate fAggregateA = factoryA.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.models().cc().ballistic(Dimension.D2, fAggregateA);

            ScatterFactory factoryB = ScatterFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.aggregates().templates().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.models().cc().ballistic(Dimension.D2, fAggregateB);

            fModelA.build();
            fModelB.build();

            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Viewer")
        void viewer() {
            int size = 28;
            int sizeFragment = 3;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

            AtomicInteger fragments = new AtomicInteger(0);

            Consumer<FAggregate> viewer = (fragment) -> fragments.incrementAndGet();
            Consumer<FAggregate> validator = (fragment) -> assertTrue(fragment.size() > 0);

            fModel.addFragmentViewer(List.of(viewer, validator));
            fModel.build();

            assertEquals(size / sizeFragment, fragments.get());
        }

        @Test
        @DisplayName("Monitor - A")
        void monitorA() {
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

            AtomicInteger stepsCount = new AtomicInteger(0);
            AtomicInteger stepsIndex = new AtomicInteger(0);

            TriConsumer<FAggregate, FAggregate, Integer> monitor = (aggA, aggB, index) -> {
                stepsCount.incrementAndGet();
                stepsIndex.set(index);
            };

            TriConsumer<FAggregate, FAggregate, Integer> monitorDummyA = (aggA, aggB, index) -> {};
            TriConsumer<FAggregate, FAggregate, Integer> monitorDummyB = (aggA, aggB, index) -> {};

            fModel.addStepMonitor(monitor);
            fModel.addStepMonitor(List.of(monitorDummyA, monitorDummyB));

            fModel.build();

            assertEquals(8, stepsCount.get() - 1);
            assertEquals(8, stepsIndex.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

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
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(8 * 2, iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.aggregates().templates().polydisperse(size, 10, 1);
            FModelCC fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

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

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(size, 1);
            FModelCC fModel = factory.models().cc().ballistic(Dimension.D2, fAggregate);

            fModel.setSymmetry(true);

            assertTrue(fModel.getSymmetry());
        }
    }
}

package eu.scattering.core.test.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.type.Dimension;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel CC ballistic")
public class FModelCCBallisticTest {

    @Nested
    @Tag("Aggregation 3D")
    @DisplayName("Aggregation 3D")
    class Aggregation3DTest {

        @Test
        @DisplayName("Results")
        void results() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModel = factory.getFModelContext().cc().ballistic(fAggregate);

            fModel.build();

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getLinearOverlapFactor(), epsilon);
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 28;

            ScatFactory factoryA = FactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.getFModelContext().cc().ballistic(fAggregateA);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.getFModelContext().cc().ballistic(fAggregateB);

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
            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);

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

            assertTrue(size / sizeFragment <= fragments.get());
            assertTrue(8 <= steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);

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
            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertTrue(8 * 2 <= iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertTrue(3 <= iteration.get());
            assertEquals(size, fAggregate.size());
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
            FModel fModel = factory.getFModelContext().cc().ballistic(Dimension.D2, fAggregate);

            fModel.build();

            for (Shape shape : fAggregate) {
                assertEquals(0, shape.getCenterZ(), 1E-8);
            }

            assertTrue(fAggregate.isCompact());
            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getLinearOverlapFactor(), epsilon);
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 28;

            ScatFactory factoryA = FactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelA = factoryA.getFModelContext().cc().ballistic(Dimension.D2, fAggregateA);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModel fModelB = factoryB.getFModelContext().cc().ballistic(Dimension.D2, fAggregateB);

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
            FModelCC fModel = factory.getFModelContext().cc().ballistic(Dimension.D2, fAggregate);

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

            assertTrue(size / sizeFragment <= fragments.get());
            assertTrue(8 <= steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().ballistic(Dimension.D2, fAggregate);

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
            FModelCC fModel = factory.getFModelContext().cc().ballistic(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertTrue(8 * 2 <= iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 28;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCC fModel = factory.getFModelContext().cc().ballistic(Dimension.D2, fAggregate);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertTrue(3 <= iteration.get());
            assertEquals(size, fAggregate.size());
        }
    }
}

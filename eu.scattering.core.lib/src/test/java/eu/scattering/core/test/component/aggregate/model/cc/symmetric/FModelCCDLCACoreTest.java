package eu.scattering.core.test.component.aggregate.model.cc.symmetric;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.dlca.FModelCCDLCA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.utility.type.Dimension;
import eu.scattering.core.impl.ScatFactoryDef;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel CC DLCA")
public class FModelCCDLCACoreTest {

    @Disabled
    @Nested
    @Tag("Predefined")
    @DisplayName("Aggregation 3D - Predefined")
    class AggregationPredefinedTest {

        @Test
        @DisplayName("Aggregation 3D - Visual")
        void results3DA() {
            int size = 3000;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

            fModel.build();

            String json = fAggregate.toJSON().toString();
            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertTrue(json.length() > 0);
            assertTrue(model.length() > 0);
        }

        @Test
        @DisplayName("Aggregation 2D - Visual")
        void results2DA() {
            int size = 3000;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

            fModel.build();

            String json = fAggregate.toJSON().toString();
            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            assertTrue(json.length() > 0);
            assertTrue(model.length() > 0);
        }
    }

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

            fModel.build();

            assertTrue(fAggregate.isConnected());
            assertEquals(size, fAggregate.size());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 28;

            ScatFactory factoryA = ScatFactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCDLCA fModelA = factoryA.getFModelContext().cc().dlca(fAggregateA);
            fModelA.setInternalSpawn(true);

            ScatFactory factoryB = ScatFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCDLCA fModelB = factoryB.getFModelContext().cc().dlca(fAggregateB);
            fModelB.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(fAggregate);
            fModel.setInternalSpawn(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Configuration")
        void configuration() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(10, 1);
            FModelCCDLCA model = factory.getFModelContext().cc().dlca(fAggregate);

            TriConsumer<FAggregate, FRandAspect, FPoint> movement = (assembly, random, point) -> point.add(1, 2, 3);

            model.setSymmetry(true);
            model.setInternalSpawn(true);
            model.setStepFactor(1.1);
            model.setExileFactor(3.3);
            model.setSpawnFactor(2.2);
            model.setMovement(movement);

            assertTrue(model.getSymmetry());
            assertTrue(model.getInternalSpawn());
            assertEquals(1.1, model.getStepFactor());
            assertEquals(3.3, model.getExileFactor());
            assertEquals(2.2, model.getSpawnFactor());
            assertSame(movement, model.getMovement());
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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

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

            ScatFactory factoryA = ScatFactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCDLCA fModelA = factoryA.getFModelContext().cc().dlca(Dimension.D2, fAggregateA);
            fModelA.setInternalSpawn(true);

            ScatFactory factoryB = ScatFactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCDLCA fModelB = factoryB.getFModelContext().cc().dlca(Dimension.D2, fAggregateB);
            fModelB.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

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
            FModelCCDLCA fModel = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);
            fModel.setInternalSpawn(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addCompletionValidator((aggA, aggB) -> iteration.incrementAndGet() > 2);
            fModel.build();

            assertEquals(3, iteration.get());
            assertEquals(size, fAggregate.size());
        }

        @Test
        @DisplayName("Configuration")
        void configuration() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(10, 1);
            FModelCCDLCA model = factory.getFModelContext().cc().dlca(Dimension.D2, fAggregate);

            TriConsumer<FAggregate, FRandAspect, FPoint> movement = (aggregate, random, point) -> point.add(1, 2, 3);

            model.setSymmetry(true);
            model.setInternalSpawn(true);
            model.setStepFactor(1.1);
            model.setExileFactor(3.3);
            model.setSpawnFactor(2.2);
            model.setMovement(movement);

            assertTrue(model.getSymmetry());
            assertTrue(model.getInternalSpawn());
            assertEquals(1.1, model.getStepFactor());
            assertEquals(3.3, model.getExileFactor());
            assertEquals(2.2, model.getSpawnFactor());
            assertSame(movement, model.getMovement());
        }
    }
}

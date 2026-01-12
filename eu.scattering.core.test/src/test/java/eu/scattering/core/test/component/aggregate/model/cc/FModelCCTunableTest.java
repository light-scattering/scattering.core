package eu.scattering.core.test.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.Dimension;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FModel CC RLCA")
public class FModelCCTunableTest {

    @Nested
    @Tag("Aggregation 3D")
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

            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-8);
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 32;

            ScatFactory factoryA = FactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelA = factoryA.getFModelContext().cc().tunable(fAggregateA, 1.8, 1.6);
            fModelA.setEarlyStageCorrection(true);

            ScatFactory factoryB = FactoryDef.create(123);

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
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(5 * 2 ,iteration.get());
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

            assertEquals(3 ,iteration.get());
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
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            for (Shape shape : fAggregate) {
                assertEquals(0, shape.getCenterZ(), 1E-8);
            }

            assertEquals(size, fAggregate.size());
            assertEquals(0, fAggregate.getLinearOverlapFactor(), 1E-8);
        }

        @Test
        @DisplayName("Randomization")
        void randomization() {
            int size = 32;

            ScatFactory factoryA = FactoryDef.create(123);

            FAggregate fAggregateA = factoryA.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelA = factoryA.getFModelContext().cc().tunable(Dimension.D2, fAggregateA, 1.8, 1.6);
            fModelA.setEarlyStageCorrection(true);

            ScatFactory factoryB = FactoryDef.create(123);

            FAggregate fAggregateB = factoryB.getFAggregateContext().base().polydisperse(size, 10, 1);
            FModelCCTunable fModelB = factoryB.getFModelContext().cc().tunable(Dimension.D2, fAggregateB, 1.8, 1.6);
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
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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
            assertEquals(5, steps.get());
        }

        @Test
        @DisplayName("Monitor - B")
        void monitorB() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

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
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.8, 1.6);
            fModel.setEarlyStageCorrection(true);

            AtomicInteger iteration = new AtomicInteger(0);

            fModel.addStepAcceptor((aggA, aggB) -> iteration.incrementAndGet() % 2 == 0);
            fModel.build();

            assertEquals(5 * 2 ,iteration.get());
        }

        @Test
        @DisplayName("Validator")
        void validator() {
            int size = 32;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregate, 1.6, 1.4);
            fModel.setEarlyStageCorrection(true);

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

            FAggregate fAggregateB = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModelB = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregateB, 1.8, 1.6);
            fModelB.setEarlyStageCorrection(true);

            FAggregate fAggregateC = factory.getFAggregateContext().base().monodisperse(size, 1);
            FModelCCTunable fModelC = factory.getFModelContext().cc().tunable(Dimension.D2, fAggregateC, 1.2, 2.2);
            fModelC.setEarlyStageCorrection(true);

            fModelB.build();
            fModelC.build();

            double rangeB = fAggregateB.getRadius(Center.MASS);
            double rangeC = fAggregateC.getRadius(Center.MASS);

            assertTrue(rangeB < rangeC);
        }
    }
}

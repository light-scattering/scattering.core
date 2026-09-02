package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.pc.FConfigPCPL;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadius;
import eu.scattering.core.design.component.aggregate.monitor.pc.module.FMonitorPCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import org.junit.jupiter.api.*;

import java.util.List;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FMonitor PC")
public class FMonitorPCTest {

    @Nested
    @DisplayName("FMonitor custom")
    class FMonitorCustomTest {

        @Test
        @DisplayName("Radius - Df = 1.8")
        void radiusMonodisperseDf18() {
            int quantity = 100;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1);
            FModelPCTunable fModel = factory.models().pc().tunable(fAggregate, df, kf);

            FStat radius = factory.getFStat();

            fModel.addStepMonitor((aggregate, particle) -> {

                if (aggregate == null || aggregate.size() < 3) {
                    return;
                }

                radius.add(aggregate.getRadiusFrom(Center.ORIGIN));
            });

            fModel.setEarlyStageCorrection(true);
            fModel.build();

            for (int i = 0 ; i < radius.size() - 1 ; i++) {
                assertTrue(radius.get(i) <= radius.get(i + 1));
            }
        }
    }

    @Nested
    @DisplayName("FMonitor radius")
    class FMonitorRadiusTest {

        @Test
        @DisplayName("Origin")
        void radiusOrigin() {
            int quantity = 100;
            int skip = 3;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1);

            FModelPC fModel = factory.models().pc().ballistic(fAggregate);
            FMonitorPCRadius fMonitor = factory.monitors().pc().radius(skip, Center.ORIGIN);

            fModel.addStepMonitor(fMonitor);

            fModel.build();

            FStat radius = fMonitor.getRefFPlot().getRefCoreY();

            for (int i = 0 ; i < radius.size() - 1 ; i++) {
                assertTrue(radius.get(i) <= radius.get(i + 1));
            }
        }
    }

    @Nested
    @DisplayName("FMonitor radius of gyration")
    class FMonitorRadiusOfGyrationTest {

        @Test
        @DisplayName("Radius of gyration - Df = 1.4")
        void rogMonodisperseDf14() {
            int quantity = 100;
            int skip = 3;
            double df = 1.4;
            double kf = 1.8;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1);

            FModelPCTunable fModel = factory.models().pc().tunable(fAggregate, df, kf);
            FMonitorPCRadiusOfGyration fMonitorA = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);
            FMonitorPCRadiusOfGyration fMonitorB = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot fPlot = fMonitorA.getRefFPlot();

            fPlot.swapXY();
            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

            FPoly slope = fPlot.copy().reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(factory.getFConfigPCPL(FConfigPCPL.Preset.DROP)), 0.05);

            assertEquals(quantity, fPlot.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 1.8")
        void rogMonodisperseDf18() {
            int quantity = 100;
            int skip = 3;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1);

            FModelPCTunable fModel = factory.models().pc().tunable(fAggregate, df, kf);
            FMonitorPCRadiusOfGyration fMonitorA = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);
            FMonitorPCRadiusOfGyration fMonitorB = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot fPlot = fMonitorA.getRefFPlot();

            fPlot.swapXY();
            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

            FPoly slope = fPlot.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(factory.getFConfigPCPL(FConfigPCPL.Preset.DROP)), 0.05);

            assertEquals(quantity, fPlot.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 2.2")
        void rogMonodisperseDf22() {
            int quantity = 100;
            int skip = 3;
            double df = 2.2;
            double kf = 1.0;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1);

            FModelPCTunable fModel = factory.models().pc().tunable(fAggregate, df, kf);
            FMonitorPCRadiusOfGyration fMonitorA = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);
            FMonitorPCRadiusOfGyration fMonitorB = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot fPlot = fMonitorA.getRefFPlot();

            fPlot.swapXY();
            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

            FPlot regression = fPlot.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(factory.getFConfigPCPL(FConfigPCPL.Preset.DROP)), 0.05);

            assertEquals(quantity, fPlot.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic")
        void rogMonodisperseBallistic() {
            int quantity = 100;
            double delta = 0.25;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelPC fModel = factory.models().pc().ballistic(fAggregate);
            FMonitorPCRadiusOfGyration fMonitor = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.VOLUMETRIC);
            FMonitorPCRadiusOfGyration fMonitorMono = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_06R1);
            FMonitorPCRadiusOfGyration fMonitorPoly = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);
            FMonitorPCRadiusOfGyration fMonitorFilippov = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            fModel.addStepMonitor(fMonitor);
            fModel.addStepMonitor(fMonitorMono);
            fModel.addStepMonitor(fMonitorPoly);
            fModel.addStepMonitor(fMonitorFilippov);

            fModel.build();

            FPlot results = fMonitor.getRefFPlot();
            FPlot resultsMono = fMonitorMono.getRefFPlot();
            FPlot resultsPoly = fMonitorPoly.getRefFPlot();
            FPlot resultsFilippov = fMonitorFilippov.getRefFPlot();

            assertTrue(results.getRefCoreY().isSimilarAbs(0.25,
                    resultsMono.getRefCoreY(), resultsPoly.getRefCoreY(), resultsFilippov.getRefCoreY()));
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic (skip)")
        void rogMonodisperseBallisticSkip() {
            int quantity = 100;
            double delta = 0.25;
            int skip = 5;

            FAggregate fAggregate = factory.aggregates().templates().monodisperse(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelPC fModel = factory.models().pc().ballistic(fAggregate);
            FMonitorPCRadiusOfGyration fMonitor = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.VOLUMETRIC, skip);
            FMonitorPCRadiusOfGyration fMonitorMono = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_06R1, skip);
            FMonitorPCRadiusOfGyration fMonitorPoly = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1, skip);
            FMonitorPCRadiusOfGyration fMonitorFilippov = factory.monitors().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, skip);

            fModel.addStepMonitor(fMonitor);
            fModel.addStepMonitor(fMonitorMono);
            fModel.addStepMonitor(fMonitorPoly);
            fModel.addStepMonitor(fMonitorFilippov);

            fModel.build();

            FPlot results = fMonitor.getRefFPlot();
            FPlot resultsMono = fMonitorMono.getRefFPlot();
            FPlot resultsPoly = fMonitorPoly.getRefFPlot();
            FPlot resultsFilippov = fMonitorFilippov.getRefFPlot();

            assertTrue(results.getRefCoreY().isSimilarAbs(0.15,
                    resultsMono.getRefCoreY(), resultsPoly.getRefCoreY(), resultsFilippov.getRefCoreY()));
        }
    }
}

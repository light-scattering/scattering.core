package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.kinetic.cc.FConfigCCPL;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadius;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FMonitor CC")
public class FMonitorCCTest {

    @Nested
    @DisplayName("FMonitor radius")
    class FMonitorRadiusTest {

        @Test
        @DisplayName("Sphere")
        void radiusSphere() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FMonitorCCRadius fMonitor = factory.getFMonitorContext().cc().radius(Center.SPHERE);

            fModel.addStepMonitor(fMonitor);

            fModel.build();

            FPlotBar radius = fMonitor.getRefFPlotBar();

            double radiusFinal = radius.getRefCoreY().get(radius.size() - 1).mean();

            double radiusFinalManual = fAggregate.getRadiusFrom(fAggregate.getCenter(Center.SPHERE));

            assertEquals(radiusFinalManual, radiusFinal, radiusFinalManual * 0.01);
        }
    }

    @Nested
    @DisplayName("FMonitor radius of gyration")
    class FMonitorRadiusOfGyrationTest {

        @Test
        @DisplayName("Radius of gyration - Df = 1.4")
        void rogMonodisperseDf14() {
            int quantity = 100;
            double df = 1.4;
            double kf = 1.8;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

            FMonitorCCRadiusOfGyration fMonitorA = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
            FMonitorCCRadiusOfGyration fMonitorB = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlotBar fPlotBar = fMonitorA.getRefFPlotBar();
            FPlot fPlot = fPlotBar.toFPlot(FStat::mean);

            fPlot.swapXY();
            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

            FPoly slope = fPlot.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(factory.getFConfigCCPL(FConfigCCPL.Preset.WINDOW)), 0.05);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 1.8")
        void rogMonodisperseDf18() {
            int quantity = 100;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

            FMonitorCCRadiusOfGyration fMonitorA = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
            FMonitorCCRadiusOfGyration fMonitorB = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlotBar fPlotBar = fMonitorA.getRefFPlotBar();
            FPlot fPlot = fPlotBar.toFPlot(FStat::mean);

            fPlot.swapXY();
            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

            FPoly slope = fPlot.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(factory.getFConfigCCPL(FConfigCCPL.Preset.WINDOW)), 0.05);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 2.2")
        void rogMonodisperseDf22() {
            int quantity = 100;
            double df = 2.2;
            double kf = 1.0;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

            FMonitorCCRadiusOfGyration fMonitorA = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);
            FMonitorCCRadiusOfGyration fMonitorB = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlotBar fPlotBar = fMonitorA.getRefFPlotBar();
            FPlot fPlot = fPlotBar.toFPlot(FStat::mean);

            fPlot.swapXY();
            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

            FPoly slope = fPlot.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(factory.getFConfigCCPL(FConfigCCPL.Preset.WINDOW)), 0.05);
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic")
        void rogMonodisperseBallistic() {
            int quantity = 100;
            double delta = 0.25;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FMonitorCCRadiusOfGyration fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.VOLUMETRIC);
            FMonitorCCRadiusOfGyration fMonitorMono = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_06R1);
            FMonitorCCRadiusOfGyration fMonitorPoly = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);
            FMonitorCCRadiusOfGyration fMonitorFilippov = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

            fModel.addStepMonitor(fMonitor);
            fModel.addStepMonitor(fMonitorMono);
            fModel.addStepMonitor(fMonitorPoly);
            fModel.addStepMonitor(fMonitorFilippov);

            fModel.build();

            FPlotBar results = fMonitor.getRefFPlotBar();
            FPlotBar resultsMono = fMonitorMono.getRefFPlotBar();
            FPlotBar resultsPoly = fMonitorPoly.getRefFPlotBar();
            FPlotBar resultsFilippov = fMonitorFilippov.getRefFPlotBar();

            FPlot resultsPlot = results.toFPlot(FStat::mean);
            FPlot resultsMonoPlot = resultsMono.toFPlot(FStat::mean);
            FPlot resultsPolyPlot = resultsPoly.toFPlot(FStat::mean);
            FPlot resultsFilippovPlot = resultsFilippov.toFPlot(FStat::mean);

            assertTrue(resultsPlot.getRefCoreY().isSimilarAbs(0.25,
                    resultsMonoPlot.getRefCoreY(), resultsPolyPlot.getRefCoreY(), resultsFilippovPlot.getRefCoreY()));
        }
    }
}

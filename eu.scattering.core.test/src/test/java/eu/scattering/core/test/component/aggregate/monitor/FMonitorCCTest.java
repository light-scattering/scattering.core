package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.type.RadiusOfGyration;
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
    @Tag("Radius of gyration")
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

            FMonitorCCRadiusOfGyration fMonitorA = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);
            FMonitorCCRadiusOfGyration fMonitorB = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);

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
            assertEquals(df, fMonitorB.getPowerLawDimension(), 0.05);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 1.8")
        void rogMonodisperseDf18() {
            int quantity = 100;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

            FMonitorCCRadiusOfGyration fMonitorA = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);
            FMonitorCCRadiusOfGyration fMonitorB = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);

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
            assertEquals(df, fMonitorB.getPowerLawDimension(), 0.05);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 2.2")
        void rogMonodisperseDf22() {
            int quantity = 100;
            double df = 2.2;
            double kf = 1.0;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

            FMonitorCCRadiusOfGyration fMonitorA = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);
            FMonitorCCRadiusOfGyration fMonitorB = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);

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
            assertEquals(df, fMonitorB.getPowerLawDimension(), 0.05);
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic")
        void rogMonodisperseBallistic() {
            int quantity = 100;
            double delta = 0.25;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FMonitorCCRadiusOfGyration fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.COMPLEX);
            FMonitorCCRadiusOfGyration fMonitorMono = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO);
            FMonitorCCRadiusOfGyration fMonitorPoly = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_POLY);
            FMonitorCCRadiusOfGyration fMonitorFilippov = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_FILIPPOV);

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

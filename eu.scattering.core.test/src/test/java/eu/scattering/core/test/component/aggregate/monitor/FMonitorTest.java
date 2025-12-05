package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.aggregate.monitor.common.module.FMonitorRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.transfer.primitive.FPoly;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FMonitor")
public class FMonitorTest {

    @Nested
    @Tag("Custom")
    @DisplayName("FMonitor custom")
    class FMonitorCustomTest {

        @Test
        @DisplayName("Radius - Df = 1.8")
        void radiusMonodisperseDf18() {
            int quantity = 100;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);

            FStat radius = factory.getFStat();

            fModel.addStepMonitor((aggregate, particle) -> {

                if (aggregate.size() < 3) {
                    return;
                }

                radius.add(aggregate.getRadiusFromOrigin());
            });
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            for (int i = 0 ; i < radius.size() - 1 ; i++) {
                assertTrue(radius.get(i) <= radius.get(i + 1));
            }
        }
    }

    @Nested
    @Tag("Construct")
    @DisplayName("FMonitor construct")
    class FMonitorConstructTest {

        @Test
        @DisplayName("Radius of gyration - Df = 1.4")
        void rogMonodisperseDf14() {
            int quantity = 100;
            int skip = 3;
            double df = 1.4;
            double kf = 1.8;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);
            FMonitorRadiusOfGyration fMonitorA = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);
            FMonitorRadiusOfGyration fMonitorB = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot resultsMono = fMonitorA.getRefFPlot();

            resultsMono.swapXY();
            resultsMono.mutateX(FStat::ln);
            resultsMono.mutateY(FStat::ln);

            FPlot regression = resultsMono.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(), 0.05);

            assertEquals(quantity, resultsMono.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 1.8")
        void rogMonodisperseDf18() {
            int quantity = 100;
            int skip = 3;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);
            FMonitorRadiusOfGyration fMonitorA = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);
            FMonitorRadiusOfGyration fMonitorB = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot resultsMono = fMonitorA.getRefFPlot();

            resultsMono.swapXY();
            resultsMono.mutateX(FStat::ln);
            resultsMono.mutateY(FStat::ln);

            FPlot regression = resultsMono.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(), 0.05);

            assertEquals(quantity, resultsMono.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 2.2")
        void rogMonodisperseDf22() {
            int quantity = 100;
            int skip = 3;
            double df = 2.2;
            double kf = 1.0;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);
            FMonitorRadiusOfGyration fMonitorA = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);
            FMonitorRadiusOfGyration fMonitorB = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(List.of(fMonitorA, fMonitorB));
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot resultsMono = fMonitorA.getRefFPlot();

            resultsMono.swapXY();
            resultsMono.mutateX(FStat::ln);
            resultsMono.mutateY(FStat::ln);

            FPlot regression = resultsMono.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);
            assertEquals(df, fMonitorB.getPowerLawDimension(), 0.05);

            assertEquals(quantity, resultsMono.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic")
        void rogMonodisperseBallistic() {
            int quantity = 100;
            double delta = 0.25;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelPC fModel = factory.createFModelBallistic3D(fAggregate);
            FMonitorRadiusOfGyration fMonitor = factory.getFMonitorRadiusOfGyration(FAggregate.RadiusOfGyration.COMPLEX);
            FMonitorRadiusOfGyration fMonitorMono = factory.getFMonitorRadiusOfGyration(FAggregate.RadiusOfGyration.SIMPLE_MONO);
            FMonitorRadiusOfGyration fMonitorPoly = factory.getFMonitorRadiusOfGyration(FAggregate.RadiusOfGyration.SIMPLE_POLY);
            FMonitorRadiusOfGyration fMonitorFilippov = factory.getFMonitorRadiusOfGyration(FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);

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
            int skip = 5;
            double delta = 0.25;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelPC fModel = factory.createFModelBallistic3D(fAggregate);
            FMonitorRadiusOfGyration fMonitor = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.COMPLEX);
            FMonitorRadiusOfGyration fMonitorMono = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_MONO);
            FMonitorRadiusOfGyration fMonitorPoly = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_POLY);
            FMonitorRadiusOfGyration fMonitorFilippov = factory.getFMonitorRadiusOfGyration(skip, FAggregate.RadiusOfGyration.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(fMonitor);
            fModel.addStepMonitor(fMonitorMono);
            fModel.addStepMonitor(fMonitorPoly);
            fModel.addStepMonitor(fMonitorFilippov);
            fModel.build();

            FPlot results = fMonitor.getRefFPlot();
            FPlot resultsMono = fMonitorMono.getRefFPlot();
            FPlot resultsPoly = fMonitorPoly.getRefFPlot();
            FPlot resultsFilippov = fMonitorFilippov.getRefFPlot();

            assertTrue(results.getRefCoreY().isSimilarAbs(0.1,
                    resultsMono.getRefCoreY(), resultsPoly.getRefCoreY(), resultsFilippov.getRefCoreY()));
        }
    }
}

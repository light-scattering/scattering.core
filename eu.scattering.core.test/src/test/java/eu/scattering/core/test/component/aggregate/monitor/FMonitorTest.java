package eu.scattering.core.test.component.aggregate.monitor;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.aggregate.monitor.construct.FMonitorConstruct;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.transfer.primitive.FPoly;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FMonitor")
public class FMonitorTest {

    @Nested
    @Tag("Construct")
    @DisplayName("FMonitor construct")
    class FMonitorConstructTest {

        @Test
        @DisplayName("Radius of gyration - Df = 1.4")
        void rotMonodisperseDf14() {
            int quantity = 100;
            int skip = 3;
            double df = 1.4;
            double kf = 1.8;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);
            FMonitorConstruct fMonitor = factory.getFMonitorRoG(skip, FAggregate.RoG.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(fMonitor);
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot resultsMono = fMonitor.getResults();

            resultsMono.swapXY();
            resultsMono.mutateX(FStat::ln);
            resultsMono.mutateY(FStat::ln);

            FPlot regression = resultsMono.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);

            assertEquals(quantity, resultsMono.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 1.8")
        void rotMonodisperseDf18() {
            int quantity = 100;
            int skip = 3;
            double df = 1.8;
            double kf = 1.6;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);
            FMonitorConstruct fMonitor = factory.getFMonitorRoG(skip, FAggregate.RoG.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(fMonitor);
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot resultsMono = fMonitor.getResults();

            resultsMono.swapXY();
            resultsMono.mutateX(FStat::ln);
            resultsMono.mutateY(FStat::ln);

            FPlot regression = resultsMono.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);

            assertEquals(quantity, resultsMono.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Df = 2.2")
        void rotMonodisperseDf22() {
            int quantity = 100;
            int skip = 3;
            double df = 2.2;
            double kf = 1.0;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1);

            FModelPCTunable fModel = factory.createFModelFilippov3D(fAggregate, df, kf);
            FMonitorConstruct fMonitor = factory.getFMonitorRoG(skip, FAggregate.RoG.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(fMonitor);
            fModel.setEarlyStageCorrection(true);
            fModel.build();

            FPlot resultsMono = fMonitor.getResults();

            resultsMono.swapXY();
            resultsMono.mutateX(FStat::ln);
            resultsMono.mutateY(FStat::ln);

            FPlot regression = resultsMono.copy();
            FPoly slope = regression.reg().poly(1);

            assertEquals(df, slope.at(1), 0.05);

            assertEquals(quantity, resultsMono.size() + skip);
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic")
        void rotMonodisperseBallistic() {
            int quantity = 100;
            double delta = 0.25;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelPC fModel = factory.createFModelBallistic3D(fAggregate);
            FMonitorConstruct fMonitor = factory.getFMonitorRoG(FAggregate.RoG.COMPLEX);
            FMonitorConstruct fMonitorMono = factory.getFMonitorRoG(FAggregate.RoG.SIMPLE_MONO);
            FMonitorConstruct fMonitorPoly = factory.getFMonitorRoG(FAggregate.RoG.SIMPLE_POLY);
            FMonitorConstruct fMonitorFilippov = factory.getFMonitorRoG(FAggregate.RoG.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(fMonitor);
            fModel.addStepMonitor(fMonitorMono);
            fModel.addStepMonitor(fMonitorPoly);
            fModel.addStepMonitor(fMonitorFilippov);
            fModel.build();

            FPlot results = fMonitor.getResults();
            FPlot resultsMono = fMonitorMono.getResults();
            FPlot resultsPoly = fMonitorPoly.getResults();
            FPlot resultsFilippov = fMonitorFilippov.getResults();

            assertTrue(results.getRefCoreY().isSimilarAbs(0.25,
                    resultsMono.getRefCoreY(), resultsPoly.getRefCoreY(), resultsFilippov.getRefCoreY()));
        }

        @Test
        @DisplayName("Radius of gyration - Ballistic (skip)")
        void rotMonodisperseBallisticSkip() {
            int quantity = 100;
            int skip = 5;
            double delta = 0.25;

            FAggregate fAggregate = factory.getFAggregatePreMono(quantity, 1).addFBuffer(1_000_000);

            fAggregate.getRefParticles().forEach(e -> e.setDelta(delta));

            FModelPC fModel = factory.createFModelBallistic3D(fAggregate);
            FMonitorConstruct fMonitor = factory.getFMonitorRoG(skip, FAggregate.RoG.COMPLEX);
            FMonitorConstruct fMonitorMono = factory.getFMonitorRoG(skip, FAggregate.RoG.SIMPLE_MONO);
            FMonitorConstruct fMonitorPoly = factory.getFMonitorRoG(skip, FAggregate.RoG.SIMPLE_POLY);
            FMonitorConstruct fMonitorFilippov = factory.getFMonitorRoG(skip, FAggregate.RoG.SIMPLE_FILIPPOV);

            fModel.addStepMonitor(fMonitor);
            fModel.addStepMonitor(fMonitorMono);
            fModel.addStepMonitor(fMonitorPoly);
            fModel.addStepMonitor(fMonitorFilippov);
            fModel.build();

            FPlot results = fMonitor.getResults();
            FPlot resultsMono = fMonitorMono.getResults();
            FPlot resultsPoly = fMonitorPoly.getResults();
            FPlot resultsFilippov = fMonitorFilippov.getResults();

            assertTrue(results.getRefCoreY().isSimilarAbs(0.1,
                    resultsMono.getRefCoreY(), resultsPoly.getRefCoreY(), resultsFilippov.getRefCoreY()));
        }
    }
}

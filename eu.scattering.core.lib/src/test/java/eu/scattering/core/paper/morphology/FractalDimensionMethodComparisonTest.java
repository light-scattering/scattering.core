package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;

@Disabled
@DisplayName("Paper - Morphology (Df method)")
public class FractalDimensionMethodComparisonTest {
    private final int size = 1000;
    private final int repetitions = 10;

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4")
    void df14() {
        double df = 1.4;
        double kf = 1.5;

        Container container = new Container(df, kf, size, repetitions);

        container.measure();
        container.show();
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8")
    void df18() {
        double df = 1.8;
        double kf = 1.3;

        Container container = new Container(df, kf, size, repetitions);

        container.measure();
        container.show();
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2")
    void df22() {
        double df = 2.2;
        double kf = 0.8;

        Container container = new Container(df, kf, size, repetitions);

        container.measure();
        container.show();
    }

    private static class Container {
        private final double df, kf;
        private final int size, repetitions;

        private final FStat pl = factory.getFStat();
        private final FStat bcRaw = factory.getFStat();
        private final FStat bcOptimized = factory.getFStat();
        private final FStat dcLimited = factory.getFStat();
        private final FStat dcFull = factory.getFStat();
        private final FStat mrLimited = factory.getFStat();
        private final FStat mrFull = factory.getFStat();

        private final boolean runPl = true;
        private final  boolean runBcRaw = false;
        private final boolean runBcOptimized = true;
        private final boolean runDcLimited = true;
        private final boolean runDcFull = false;
        private final boolean runMrLimited = true;
        private final boolean runMrFull = false;

        public Container(double df, double kf, int size, int repetitions) {
           this.df = df;
           this.kf = kf;
           this.size = size;
           this.repetitions = repetitions;
        }

        private void measure() {
            System.out.printf("Df=%s, Kf=%s\n", df, kf);

            for (int i = 0 ; i < repetitions ; i++) {

                if (i > 0 && i % 100 == 0) {
                    System.out.println();
                }

                try {
                    measureCore();

                    System.out.print(".");
                } catch (Exception e) {
                    System.out.print("X");
                }

                System.out.flush();
            }
        }

        private void measureCore() {
            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);
            fModel.addStepMonitor(fMonitor);

            fModel.build();

            update(fAggregate, fMonitor);
        }

        public void update(FAggregate aggregate, FMonitorCCRadiusOfGyration monitor) {

            if (runPl)          pl.add(monitor.getPowerLawDimension());
            if (runBcOptimized) bcOptimized.add(aggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED));
            if (runBcRaw)       bcRaw.add(aggregate.getFractalDimension(FractalDimension.BC_SIMPLIFIED));
            if (runDcLimited)   dcLimited.add(aggregate.getFractalDimension(FractalDimension.CORRELATION));
            if (runDcFull)      dcFull.add(aggregate.getFractalDimension(FractalDimension.CORRELATION_FULL));
            if (runMrLimited)   mrLimited.add(aggregate.getFractalDimension(FractalDimension.MASS));
            if (runMrFull)      mrFull.add(aggregate.getFractalDimension(FractalDimension.MASS_FULL));
        }

        public void show() {

            System.out.println();
            if (runPl)          System.out.printf("Power law:      %1.6f, %1.6f\n", pl.mean(), pl.std(true));
            if (runBcOptimized) System.out.printf("Box counting O: %1.6f, %1.6f\n", bcOptimized.mean(), bcOptimized.std(true));
            if (runBcRaw)       System.out.printf("Box counting R: %1.6f, %1.6f\n", bcRaw.mean(), bcRaw.std(true));
            if (runDcLimited)   System.out.printf("Density:        %1.6f, %1.6f\n", dcLimited.mean(), dcLimited.std(true));
            if (runDcFull)      System.out.printf("Density (full): %1.6f, %1.6f\n", dcFull.mean(), dcFull.std(true));
            if (runMrLimited)   System.out.printf("Mass:           %1.6f, %1.6f\n", mrLimited.mean(), mrLimited.std(true));
            if (runMrFull)      System.out.printf("Mass (full):    %1.6f, %1.6f\n", mrFull.mean(), mrFull.std(true));
        }
    }
}

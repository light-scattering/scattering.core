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
public class DfMethodComparisonTest {
    private final int size = 10000;
    private final int repetitions = 100;

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
        private final FStat bcOptimized = factory.getFStat();
        private final FStat dcLimited = factory.getFStat();
        private final FStat dcFull = factory.getFStat();
        private final FStat mrLimited = factory.getFStat();
        private final FStat mrFull = factory.getFStat();

        private final FStat bcOptimizedTime = factory.getFStat();
        private final FStat dcLimitedTime = factory.getFStat();
        private final FStat dcFullTime = factory.getFStat();
        private final FStat mrLimitedTime = factory.getFStat();
        private final FStat mrFullTime = factory.getFStat();

        private final boolean runPl = true;
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

            if (runPl) {
                pl.add(monitor.getPowerLawDimension());
            }
            if (runBcOptimized) {
                long time = System.currentTimeMillis();
                bcOptimized.add(aggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT_PCA));
                bcOptimizedTime.add(System.currentTimeMillis() - time);
            }
            if (runDcLimited) {
                long time = System.currentTimeMillis();
                dcLimited.add(aggregate.getFractalDimension(FractalDimension.CORRELATION));
                dcLimitedTime.add(System.currentTimeMillis() - time);
            }
            if (runDcFull) {
                long time = System.currentTimeMillis();
                dcFull.add(aggregate.getFractalDimension(FractalDimension.CORRELATION_FULL));
                dcFullTime.add(System.currentTimeMillis() - time);
            }
            if (runMrLimited) {
                long time = System.currentTimeMillis();
                mrLimited.add(aggregate.getFractalDimension(FractalDimension.MASS));
                mrLimitedTime.add(System.currentTimeMillis() - time);
            }
            if (runMrFull) {
                long time = System.currentTimeMillis();
                mrFull.add(aggregate.getFractalDimension(FractalDimension.MASS_FULL));
                mrFullTime.add(System.currentTimeMillis() - time);
            }
        }

        public void show() {

            System.out.println();
            if (runPl)          System.out.printf("Power law:      %1.6f, %1.6f\n%s\n",
                    pl.mean(), pl.std(true), pl.toJSON());
            if (runBcOptimized) System.out.printf("Box counting O: %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    bcOptimized.mean(), bcOptimized.std(true), bcOptimizedTime.mean(), bcOptimizedTime.std(true), bcOptimized.toJSON());
            if (runDcLimited)   System.out.printf("Density:        %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    dcLimited.mean(), dcLimited.std(true), dcLimitedTime.mean(), dcLimitedTime.std(true), dcLimited.toJSON());
            if (runDcFull)      System.out.printf("Density (full): %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    dcFull.mean(), dcFull.std(true), dcFullTime.mean(), dcFullTime.std(true), dcFull.toJSON());
            if (runMrLimited)   System.out.printf("Mass:           %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    mrLimited.mean(), mrLimited.std(true), mrLimitedTime.mean(), mrLimitedTime.std(true), mrLimited.toJSON());
            if (runMrFull)      System.out.printf("Mass (full):    %1.6f, %1.6f, %1.6f, %1.6f\n%s\n",
                    mrFull.mean(), mrFull.std(true), mrFullTime.mean(), mrFullTime.std(true), mrFull.toJSON());
            System.out.println();
        }
    }
}

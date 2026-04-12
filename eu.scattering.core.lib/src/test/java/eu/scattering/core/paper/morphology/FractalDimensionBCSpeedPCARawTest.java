package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;

@Disabled
@DisplayName("Paper - Morphology (BC PCA raw speed)")
public class FractalDimensionBCSpeedPCARawTest {
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

        private final FStat timeRaw = factory.getFStat();
        private final FStat timePCA = factory.getFStat();
        private final FStat dfError = factory.getFStat();

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

            fModel.build();

            update(fAggregate);
        }

        public void update(FAggregate aggregate) {
            long timeRefCheckA = System.currentTimeMillis();
            double dfRaw = aggregate.getFractalDimension(FractalDimension.BC_BRUTE_FORCE);
            long timeRawCheck = System.currentTimeMillis();

            aggregate.pca();

            long timeRefCheckB = System.currentTimeMillis();
            double dfPCA = aggregate.getFractalDimension(FractalDimension.BC_BRUTE_FORCE);
            long timePCACheck = System.currentTimeMillis();

            long timeRaw = timeRawCheck - timeRefCheckA;
            long timeOptimized = timePCACheck - timeRefCheckB;

            this.timeRaw.add(timeRaw);
            this.timePCA.add(timeOptimized);

            double dfError = 100 * Math.abs((dfRaw - dfPCA) / dfRaw);

            this.dfError.add(dfError);
        }

        public void show() {
            double timeRaw = this.timeRaw.mean();
            double timeOptimized = this.timePCA.mean();

            double timeGain = timeRaw / timeOptimized;

            System.out.println();
            System.out.printf("Time raw [ms]:       %1.6f, %1.6f\n", timeRaw, this.timeRaw.std(true));
            System.out.printf("Time PCA [ms]:       %1.6f, %1.6f\n", timeOptimized, this.timePCA.std(true));
            System.out.printf("Time gain:           %1.6f\n", timeGain);
            System.out.printf("Dimension error:     %1.6f\n", dfError.mean());
        }
    }
}

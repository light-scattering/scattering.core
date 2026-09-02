package eu.scattering.core.paper.man_07_2026;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.TestConfig.factory;

@Disabled
@Tag("sandbox")
@DisplayName("Paper - Morphology (BC implementation speed)")
public class BCSpeedTest {
    private final int size = 1000;
    private final int repetitions = 100;

    @Test
    @DisplayName("Dimension 1.4")
    void df14() {
        double df = 1.4;
        double kf = 1.5;

        Container container = new Container(df, kf, size, repetitions);

        container.measure();
        container.show();
    }

    @Test
    @DisplayName("Dimension 1.8")
    void df18() {
        double df = 1.8;
        double kf = 1.3;

        Container container = new Container(df, kf, size, repetitions);

        container.measure();
        container.show();
    }

    @Test
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

        private final FStat timeNaive = factory.getFStat();
        private final FStat timeBaseline = factory.getFStat();
        private final FStat timeGain = factory.getFStat();
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
            var fAggregate = factory.aggregates().templates().monodisperse(size, 1);

            var fModel = factory.models().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            update(fAggregate);
        }

        public void update(FAggregate aggregate) {
            long timeRefCheck = System.currentTimeMillis();
            double dfNaive = aggregate.getFractalDimension(FractalDimension.BC_NAIVE);
            long timeRawCheck = System.currentTimeMillis();
            double dfBaseline = aggregate.getFractalDimension(FractalDimension.BC_BASELINE);
            long timeBaselineCheck = System.currentTimeMillis();

            long timeNaive = timeRawCheck - timeRefCheck;
            long timeBaseline = timeBaselineCheck - timeRawCheck;

            this.timeNaive.add(timeNaive);
            this.timeBaseline.add(timeBaseline);
            this.timeGain.add((double) timeNaive / timeBaseline);

            double dfError = 100 * (dfBaseline - dfNaive) / dfNaive;

            this.dfError.add(dfError);
        }

        public void show() {
            double timeNaive = this.timeNaive.mean();
            double timeBaseline = this.timeBaseline.mean();

            double timeGainGroup = timeNaive / timeBaseline;

            System.out.println();
            System.out.printf("Time naive [ms]:     %1.6f, %1.6f\n", timeNaive, this.timeNaive.std(true));
            System.out.printf("Time baseline [ms]:  %1.6f, %1.6f\n", timeBaseline, this.timeBaseline.std(true));
            System.out.printf("Time gain (single):  %1.6f, %1.6f\n", timeGain.mean(), this.timeGain.std(true));
            System.out.printf("Time gain (group):   %1.6f\n", timeGainGroup);
            System.out.printf("Dimension error:     %1.6f\n", dfError.mean());
        }
    }
}

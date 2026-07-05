package eu.scattering.core.paper.morphology_07_2026;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
@DisplayName("Paper - Morphology (BC pre-processing shift factor)")
public class DfBCProcessShiftFactorTest {

    @Nested
    @Tag("Visual")
    @DisplayName("Visual - Factor")
    class VisualTest {
        private final int size = 10000;

        @Test
        @Tag("Visual")
        void df14() {
            double df = 1.4;
            double kf = 1.5;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            FBoxString plotA = factory.getFBoxString();
            fAggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT, plotA);
            assertFalse(plotA.getValue().isEmpty());

            FBoxString plotB = factory.getFBoxString();
            fAggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT_FACTOR, plotB);
            assertFalse(plotB.getValue().isEmpty());
        }

        @Test
        @Tag("Visual")
        void df18() {
            double df = 1.8;
            double kf = 1.3;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            FBoxString plotA = factory.getFBoxString();
            fAggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT, plotA);
            assertFalse(plotA.getValue().isEmpty());

            FBoxString plotB = factory.getFBoxString();
            fAggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT_FACTOR, plotB);
            assertFalse(plotB.getValue().isEmpty());
        }

        @Test
        @Tag("Visual")
        void df22() {
            double df = 2.2;
            double kf = 0.8;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            FBoxString plotA = factory.getFBoxString();
            fAggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT, plotA);
            assertFalse(plotA.getValue().isEmpty());

            FBoxString plotB = factory.getFBoxString();
            fAggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT_FACTOR, plotB);
            assertFalse(plotB.getValue().isEmpty());
        }
    }

    @Nested
    @Tag("Comparison")
    @DisplayName("Comparison - Factor")
    class ComparisonTest {
        private final int size = 5000;
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

            private final FStat dfRaw = factory.getFStat();
            private final FStat dfFactor = factory.getFStat();
            private final FStat dfError = factory.getFStat();

            public Container(double df, double kf, int size, int repetitions) {
                this.df = df;
                this.kf = kf;
                this.size = size;
                this.repetitions = repetitions;
            }

            private void measure() {
                System.out.printf("Df=%s, Kf=%s\n", df, kf);

                for (int i = 0; i < repetitions; i++) {

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
                var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

                var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
                fModel.setEarlyStageCorrection(true);

                fModel.build();

                update(fAggregate);
            }

            public void update(FAggregate aggregate) {
                double dfRaw = aggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT);
                double dfFactor = aggregate.getFractalDimension(FractalDimension.BC_MANUSCRIPT_SHIFT_FACTOR);

                double dfError = 100 * ((dfFactor - dfRaw) / dfRaw);

                this.dfRaw.add(dfRaw);
                this.dfFactor.add(dfFactor);
                this.dfError.add(dfError);
            }

            public void show() {
                System.out.println();
                System.out.printf("Dimension shift:     %1.6f, %1.6f\n", dfRaw.mean(), dfRaw.std(true));
                System.out.printf("Dimension factor:    %1.6f, %1.6f\n", dfFactor.mean(), dfFactor.std(true));
                System.out.printf("Dimension error:     %1.6f, %1.6f\n", dfError.mean(), dfError.std(true));
            }
        }
    }
}

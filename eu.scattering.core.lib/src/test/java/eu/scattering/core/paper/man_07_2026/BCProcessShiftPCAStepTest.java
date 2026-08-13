package eu.scattering.core.paper.man_07_2026;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.config.df.structural.FConfigBC;
import eu.scattering.core.design.component.aggregate.meta.df.structural.FMetaBC;
import eu.scattering.core.design.statistics.base.FStat;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
@Tag("sandbox")
@DisplayName("Paper - Morphology (BC pre-processing shift PCA step)")
public class BCProcessShiftPCAStepTest {

    @Nested
    @DisplayName("Visual - Factor")
    class VisualTest {
        private final int size = 10000;

        @Test
        @DisplayName("Visual - 1.4 / 1.5")
        void df14() {
            double df = 1.4;
            double kf = 1.5;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            FMetaBC meta = factory.getFMetaBC();

            fAggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA), meta);
            assertFalse(meta.getPythonRenderScript().isEmpty());

            fAggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA_STEP), meta);
            assertFalse(meta.getPythonRenderScript().isEmpty());
        }

        @Test
        @DisplayName("Visual - 1.8 / 1.3")
        void df18() {
            double df = 1.8;
            double kf = 1.3;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            FMetaBC meta = factory.getFMetaBC();

            fAggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA), meta);
            assertFalse(meta.getPythonRenderScript().isEmpty());

            fAggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA_STEP), meta);
            assertFalse(meta.getPythonRenderScript().isEmpty());
        }

        @Test
        @DisplayName("Visual - 2.2 / 0.8")
        void df22() {
            double df = 2.2;
            double kf = 0.8;

            var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

            var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            fModel.build();

            FMetaBC meta = factory.getFMetaBC();

            fAggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA), meta);
            assertFalse(meta.getPythonRenderScript().isEmpty());

            fAggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA_STEP), meta);
            assertFalse(meta.getPythonRenderScript().isEmpty());
        }
    }

    @Nested
    @DisplayName("Comparison - Factor")
    class ComparisonTest {
        private final int size = 10000;
        private final int repetitions = 10;

        @Test
        @Tag("sandbox")
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

            private final FStat dfPca = factory.getFStat();
            private final FStat dfStep = factory.getFStat();
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
                double dfPca = aggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA));
                double dfStep = aggregate.getFractalDimension(factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA_STEP));

                double dfError = 100 * ((dfStep - dfPca) / dfPca);

                this.dfPca.add(dfPca);
                this.dfStep.add(dfStep);
                this.dfError.add(dfError);
            }

            public void show() {
                System.out.println();
                System.out.printf("Dimension shift:     %1.6f, %1.6f\n", dfPca.mean(), dfPca.std(true));
                System.out.printf("Dimension step:      %1.6f, %1.6f\n", dfStep.mean(), dfStep.std(true));
                System.out.printf("Dimension error:     %1.6f, %1.6f\n", dfError.mean(), dfError.std(true));
            }
        }
    }
}

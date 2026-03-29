package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.tunable.FModelCCTunable;
import eu.scattering.core.design.component.aggregate.monitor.cc.module.FMonitorCCRadiusOfGyration;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;

//@Disabled
@DisplayName("Paper - Morphology (Df variation)")
public class FractalDimensionMethodComparisonTest {
    private final int repetitions = 100;
    private final int size = 1000;

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

    private record Container(
            double df,
            double kf,
            int size,
            int repetitions,
            FStat powerLaw,
            FStat boxCounting,
            FStat boxCountingBruteForce,
            FStat density,
            FStat densityFull,
            FStat mass,
            FStat massFull
    ) {
        public Container(double df, double kf, int size, int repetitions) {
            this(
                    df, kf, size, repetitions,
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat());
        }

        private void measure() {
            for (int i = 0 ; i < repetitions ; i++) {
                try {
                    System.out.print(".");
                    measureCore();
                } catch (Exception e) {
                    System.out.println();
                    System.out.println(e.getMessage());
                }
            }
        }

        private void measureCore() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

            FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
            fModel.setEarlyStageCorrection(true);

            FMonitorCCRadiusOfGyration fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);
            fModel.addStepMonitor(fMonitor);

            fModel.build();

            update(fAggregate, fMonitor);
        }

        public void update(FAggregate aggregate, FMonitorCCRadiusOfGyration monitor) {
            powerLaw.add(monitor.getPowerLawDimension());
//            boxCounting.add(aggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED));
//            boxCountingBruteForce.add(aggregate.getFractalDimension(FractalDimension.BC_SIMPLIFIED));
            density.add(aggregate.getFractalDimension(FractalDimension.CORRELATION));
//            densityFull.add(aggregate.getFractalDimension(FractalDimension.CORRELATION_FULL));
            mass.add(aggregate.getFractalDimension(FractalDimension.MASS));
//            massFull.add(aggregate.getFractalDimension(FractalDimension.MASS_FULL));
        }

        public void show() {
            System.out.printf("\nDf=%s, Df=%s\n", df, kf);
            System.out.println("Power law:      " + powerLaw.mean() + "," + powerLaw.std(true));
//            System.out.println("Box counting O: " + boxCounting.mean() + "," + boxCounting.std(true));
//            System.out.println("Box counting B: " + boxCountingBruteForce.mean() + "," + boxCountingBruteForce.std(true));
            System.out.println("Density:        " + density.mean() + "," + density.std(true));
//            System.out.println("Density (full): " + densityFull.mean() + "," + densityFull.std(true));
            System.out.println("Mass:           " + mass.mean() + "," + mass.std(true));
//            System.out.println("Mass (full):    " + massFull.mean() + "," + massFull.std(true));
        }
    }
}

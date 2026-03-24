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
    private final int repetitions = 10;
    private final int size = 3000;

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4")
    void df14() {
        Container container = new Container();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                System.out.println("Iteration: " + i);
                measure(container, 1.4, 1.5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        container.show();
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8")
    void df18() {
        Container container = new Container();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                System.out.println("Iteration: " + i);
                measure(container, 1.8, 1.3);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        container.show();
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2")
    void df22() {
        Container container = new Container();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                System.out.println("Iteration: " + i);
                measure(container, 2.2, 0.8);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        container.show();
    }

    private void measure(Container container, double df, double kf) {
        FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

        FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        FMonitorCCRadiusOfGyration fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);
        fModel.addStepMonitor(fMonitor);

        fModel.build();

       container.update(fAggregate, fMonitor);
    }

    private record Container(
            FStat powerLaw,
            FStat boxCounting,
            FStat boxCountingBruteForce,
            FStat density,
            FStat densityFull,
            FStat mass,
            FStat massFull
    ) {
        public Container() {
            this(
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat(),
                    factory.getFStat());
        }

        public void update(FAggregate aggregate, FMonitorCCRadiusOfGyration monitor) {
            powerLaw.add(monitor.getPowerLawDimension());
            boxCounting.add(aggregate.getFractalDimension(FractalDimension.BOX_ADVANCED_1));
            boxCountingBruteForce.add(aggregate.getFractalDimension(FractalDimension.BOX_FAST_BRUTE_FORCE));
            density.add(aggregate.getFractalDimension(FractalDimension.CORRELATION));
            densityFull.add(aggregate.getFractalDimension(FractalDimension.CORRELATION_FULL));
            mass.add(aggregate.getFractalDimension(FractalDimension.MASS));
            massFull.add(aggregate.getFractalDimension(FractalDimension.MASS_FULL));
        }

        public void show() {
            System.out.println("Power law:      " + powerLaw.mean() + "," + powerLaw.std(true));
            System.out.println("Box counting O: " + boxCounting.mean() + "," + boxCounting.std(true));
            System.out.println("Box counting B: " + boxCountingBruteForce.mean() + "," + boxCountingBruteForce.std(true));
            System.out.println("Density:        " + density.mean() + "," + density.std(true));
            System.out.println("Density (full): " + densityFull.mean() + "," + densityFull.std(true));
            System.out.println("Mass:           " + mass.mean() + "," + mass.std(true));
            System.out.println("Mass (full):    " + massFull.mean() + "," + massFull.std(true));
        }
    }
}

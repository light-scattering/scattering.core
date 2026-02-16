package eu.scattering.core.test.component.aggregate;

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

@DisplayName("FAggregate dimension")
@Disabled
public class FAggregateDimensionTest {
    private final int repetitions = 25;
    private final int size = 1000;

    @Test
    @Tag("Visual")
    @DisplayName("Dimension 1.4")
    void df14() {
        FStat powerLaw = factory.getFStat();
        FStat boxCounting = factory.getFStat();
        FStat boxCountingBrute = factory.getFStat();
        FStat densityCorrelation = factory.getFStat();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                System.out.println("Iteration: " + i);
                measure(powerLaw, boxCounting, boxCountingBrute, densityCorrelation, 1.4, 1.8);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n");

        System.out.println("Power law: " + powerLaw.mean() + "," + powerLaw.std(true));
        System.out.println("Box counting O: " + boxCounting.mean() + "," + boxCounting.std(true));
        System.out.println("Box counting B: " + boxCountingBrute.mean() + "," + boxCountingBrute.std(true));
        System.out.println("Density correlation: " + densityCorrelation.mean() + "," + densityCorrelation.std(true));
    }

    @Test
    @Tag("Visual")
    @DisplayName("Dimension 1.8")
    void df18() {
        FStat powerLaw = factory.getFStat();
        FStat boxCounting = factory.getFStat();
        FStat boxCountingBrute = factory.getFStat();
        FStat densityCorrelation = factory.getFStat();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                System.out.println("Iteration: " + i);
                measure(powerLaw, boxCounting, boxCountingBrute, densityCorrelation, 1.8, 1.6);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n");

        System.out.println("Power law: " + powerLaw.mean() + "," + powerLaw.std(true));
        System.out.println("Box counting O: " + boxCounting.mean() + "," + boxCounting.std(true));
        System.out.println("Box counting B: " + boxCountingBrute.mean() + "," + boxCountingBrute.std(true));
        System.out.println("Density correlation: " + densityCorrelation.mean() + "," + densityCorrelation.std(true));
    }

    @Test
    @Tag("Visual")
    @DisplayName("Dimension 2.2")
    void df22() {
        FStat powerLaw = factory.getFStat();
        FStat boxCounting = factory.getFStat();
        FStat boxCountingBrute = factory.getFStat();
        FStat densityCorrelation = factory.getFStat();

        for (int i = 0 ; i < repetitions ; i++) {
            try {
                System.out.println("Iteration: " + i);
                measure(powerLaw, boxCounting, boxCountingBrute, densityCorrelation, 2.2, 1.4);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n");

        System.out.println("Power law: " + powerLaw.mean() + "," + powerLaw.std(true));
        System.out.println("Box counting O: " + boxCounting.mean() + "," + boxCounting.std(true));
        System.out.println("Box counting B: " + boxCountingBrute.mean() + "," + boxCountingBrute.std(true));
        System.out.println("Density correlation: " + densityCorrelation.mean() + "," + densityCorrelation.std(true));
    }

    private void measure(FStat powerLaw, FStat boxCountingOptimized, FStat boxCountingBrute, FStat densityCorrelation, double df, double kf) {
        FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

        FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        FMonitorCCRadiusOfGyration fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);
        fModel.addStepMonitor(fMonitor);

        fModel.build();

        powerLaw.add(fMonitor.getPowerLawDimension());
        boxCountingOptimized.add(fAggregate.getFractalDimensionBox(0.9, 1.3, 5, false, false, true));
        boxCountingBrute.add(fAggregate.getFractalDimension(FractalDimension.BOX_FAST_BRUTE_FORCE));
        densityCorrelation.add(fAggregate.getFractalDimensionCorrelation(0.9, 1.1));
    }
}

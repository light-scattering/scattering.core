package eu.scattering.core.paper.morphology_07_2026;

import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;

@Disabled
@DisplayName("Paper - Morphology (Kf edge)")
public class DfPLProcessTest {
    int size = 10000;
    int repetitions = 2;

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.4 - 1000")
    void df14_1000() {
        double df = 1.4;
        double kf = 1.5;

        FStat data = factory.getFStat();

        for (int i = 0 ; i < repetitions ; i++) {
            data.add(measureDimension(size, df, kf));
        }

        System.out.printf("Power law:      %1.6f, %1.6f\n%s\n",
                data.mean(), data.std(true), data.toJSON());
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 1.8 - 1000")
    void df18_1000() {
        double df = 1.8;
        double kf = 1.3;

        FStat data = factory.getFStat();

        for (int i = 0 ; i < repetitions ; i++) {
            data.add(measureDimension(size, df, kf));
        }

        System.out.printf("Power law:      %1.6f, %1.6f\n%s\n",
                data.mean(), data.std(true), data.toJSON());
    }

    @Test
    @Tag("Comparison")
    @DisplayName("Dimension 2.2 - 1000")
    void df22_1000() {
        double df = 2.2;
        double kf = 0.8;

        FStat data = factory.getFStat();

        for (int i = 0 ; i < repetitions ; i++) {
            data.add(measureDimension(size, df, kf));
        }

        System.out.printf("Power law:      %1.6f, %1.6f\n%s\n",
                data.mean(), data.std(true), data.toJSON());
    }

    private double measureDimension(int size, double df, double kf) {
        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);

        fModel.addStepMonitor(fMonitor);

        fModel.build();

        return fMonitor.getPowerLawDimension();
    }
}

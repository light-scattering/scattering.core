package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;

//@Disabled
@DisplayName("Paper - PL Visual")
public class VisioPLTest {

    @Test
    @Tag("Visual")
    @DisplayName("PL - Image")
    void visio() {
        int size = 10000;
        double r = 1;
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);
        fModel.addStepMonitor(fMonitor);

        fModel.build();

        String plot = factory.getSaveAspect().getComponentContext().toChart(fMonitor, FStat::mean);

        assertFalse(plot.isEmpty());

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(model.isEmpty());

        String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

        assertFalse(geometry.isEmpty());
    }
}

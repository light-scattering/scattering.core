package eu.scattering.core.paper.morphology_07_2026;

import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Disabled
@DisplayName("Paper - Morphology")
public class VisualMorphologyTest {

    @Test
    @Tag("Visual")
    @DisplayName("Morphology - Df = 1.4")
    void df14() {
        int size = 10000;
        double r = 1;
        double df = 1.4;
        double kf = 1.5;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(model.isEmpty());
    }

    @Test
    @Tag("Visual")
    @DisplayName("Morphology - Df = 1.8")
    void df18() {
        int size = 10000;
        double r = 1;
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(model.isEmpty());
    }

    @Test
    @Tag("Visual")
    @DisplayName("Morphology - Df = 2.2")
    void df22() {
        int size = 10000;
        double r = 1;
        double df = 2.2;
        double kf = 0.8;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(model.isEmpty());
    }
}

package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@DisplayName("Paper - Visual")
public class FractalDimensionVisualTest {

    @Test
    @Tag("Visual")
    @DisplayName("BC dimension")
    void dim18() {
        int size = 8192;
        double df = 1.8;
        double kf = 1.3;

        FBoxString plot = factory.getFBoxString();

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        double results = fAggregate.getFractalDimension(FractalDimension.BC_SIMPLIFIED, plot);

        assertTrue(results > 1);
        assertFalse(plot.getValue().isEmpty());
    }

    @Test
    @Tag("Visual")
    @DisplayName("BC box")
    void box() {
        int size = 8192;
        double df = 2.2;
        double kf = 0.8;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        String modelA = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOX);

        assertFalse(modelA.isEmpty());

        String modelB = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.VISIO_BOX);

        assertFalse(modelB.isEmpty());
    }
}

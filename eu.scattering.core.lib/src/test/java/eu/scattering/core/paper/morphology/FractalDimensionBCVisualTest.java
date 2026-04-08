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

@Disabled
@DisplayName("Paper - BC Visual")
public class FractalDimensionBCVisualTest {

    @Test
    @Tag("Visual")
    @DisplayName("BC box")
    void visio() {
        int size = 8192;
        double df = 1.8;
        double kf = 1.6;

        FBoxString plot = factory.getFBoxString();

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, 0.99);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        String modelA = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(modelA.isEmpty());

        String modelB = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOX_COUNTING);

        assertFalse(modelB.isEmpty());

        double results = fAggregate.getFractalDimension(FractalDimension.BC_SIMPLIFIED, plot);

        assertTrue(results > 1);
        assertFalse(plot.getValue().isEmpty());
    }
}

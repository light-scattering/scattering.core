package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Disabled
@DisplayName("Paper - DC Visual")
public class VisioDCTest {

    @Test
    @Tag("Visual")
    @DisplayName("DC - Image")
    void visio() {
        int size = 10000;
        double r = 1;
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        FBoxString plotFull = factory.getFBoxString();

        double dimFull = fAggregate.getFractalDimension(FractalDimension.CORRELATION, plotFull);

        assertTrue(dimFull > 0);

        FBoxString plotReduced = factory.getFBoxString();

        double dimReduced = fAggregate.getFractalDimension(FractalDimension.CORRELATION_FULL, plotReduced);

        assertTrue(dimReduced > 0);
    }
}

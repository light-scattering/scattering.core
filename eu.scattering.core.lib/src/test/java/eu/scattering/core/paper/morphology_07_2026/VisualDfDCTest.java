package eu.scattering.core.paper.morphology_07_2026;

import eu.scattering.core.design.component.aggregate.meta.dc.FMetaDC;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@DisplayName("Paper - DC Visual")
public class VisualDfDCTest {

    @Test
    @Tag("Visual")
    @DisplayName("DC - Image")
    void visual() {
        int size = 10000;
        double r = 1;
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.pca();

        String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

        assertFalse(geometry.isEmpty());

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.REFERENCE);

        assertFalse(model.isEmpty());

        FBoxString plotFull = factory.getFBoxString();

        double dimFull = fAggregate.getFractalDimension(FractalDimension.DC_FULL, plotFull);

        assertTrue(dimFull > 0);

        FBoxString plotReduced = factory.getFBoxString();

        double dimReduced = fAggregate.getFractalDimension(FractalDimension.DC_RESTRICTED, plotReduced);

        assertTrue(dimReduced > 0);
    }

    @Test
    @DisplayName("Process")
    void process() {
        int size = 10000;
        double r = 1;
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.pca();

        String geometry = factory.getSaveAspect().getComponentContext().toBasic(fAggregate, ExBasic.MULTISPHERE);

        assertFalse(geometry.isEmpty());

        String model = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.REFERENCE);

        assertFalse(model.isEmpty());

        FMetaDC metaFull = factory.getFMetaDC();

        double dimFull = fAggregate.getFractalDimensionDensityCorrelation(0.9, RadiusOfGyration.SIMPLE_POLY_06R1, 1.1, false, metaFull);

        assertTrue(dimFull > 0);

        FMetaDC metaReduced = factory.getFMetaDC();

        double dimReduced = fAggregate.getFractalDimensionDensityCorrelation(0.9, RadiusOfGyration.SIMPLE_POLY_06R1, 1.1, true, metaReduced);

        assertTrue(dimReduced > 0);
    }
}

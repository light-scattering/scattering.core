package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.storage.transfer.box.variant.FBoxString;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@DisplayName("Paper - BC Visual")
public class FractalDimensionBCVisualTest {

    @Test
    @Tag("Visual")
    @DisplayName("BC box")
    void visio() {
        int size = 10000;
        double r = 1;
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(size, r);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);

        FPos3D length = fAggregate.getLength();
        double radius = fAggregate.getRadiusFrom(0, 0, 0);
        double diameter = fAggregate.getDiameter();
        double magnitude = radius / r;

        assertTrue(length.getD0() > 0 && length.getD1() > 0 && length.getD2() > 0);
        assertTrue(radius > 0);
        assertTrue(diameter > 0);
        assertTrue(magnitude > 0);

        String modelA = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOUNDARY);

        assertFalse(modelA.isEmpty());

        String modelB = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate, ExPovRay.BOX_COUNTING);

        assertFalse(modelB.isEmpty());

        FBoxString plot = factory.getFBoxString();
        double results = fAggregate.getFractalDimension(FractalDimension.BC_SIMPLIFIED, plot);

        assertTrue(results > 1);
        assertFalse(plot.getValue().isEmpty());
    }
}

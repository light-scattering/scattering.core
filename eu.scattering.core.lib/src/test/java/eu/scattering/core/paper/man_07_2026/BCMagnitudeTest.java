package eu.scattering.core.paper.man_07_2026;

import eu.scattering.core.design.utility.type.option.Length;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@Tag("sandbox")
@DisplayName("Paper - Morphology (magnitude)")
public class BCMagnitudeTest {

    @Test
    @DisplayName("Magnitude")
    void magnitudeTest() {
        double df = 1.8;
        double kf = 1.3;

        var fAggregate = factory.getFAggregateContext().base().monodisperse(10000, 1);

        var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);
        fModel.setEarlyStageCorrection(true);

        fModel.build();

        fAggregate.setSphericalCenterAsZero(1000);
        double radius = fAggregate.getRadiusFrom(0, 0, 0);

        double cutoffOuter = fAggregate.getLength(Length.MAX) * 0.5;
        double cutoffInner = 2;
        double magnitude = cutoffOuter / cutoffInner;

        System.out.println("Radius:       " + radius);
        System.out.println("Cutoff outer: " + cutoffOuter);
        System.out.println("Cutoff inner: " + cutoffInner);
        System.out.println("Magnitude:    " + magnitude);

        assertTrue(radius > 0);
    }
}

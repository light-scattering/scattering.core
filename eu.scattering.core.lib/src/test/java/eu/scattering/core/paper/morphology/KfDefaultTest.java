package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@DisplayName("Paper - Morphology (Kf edge)")
public class KfDefaultTest {

    @Test
    @Tag("Theory")
    @DisplayName("Dimension 1.0")
    void df10() {
        int size = 100;
        FAggregate aggregate = factory.getFAggregateContext().geometry().d1(size);

        double rg = aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO);

        double kfCalculated = size / (rg);

        double kfPredicted = Math.sqrt(3);

        assertEquals(kfPredicted, kfCalculated, 1E-3);
    }

    @Test
    @Tag("Theory")
    @DisplayName("Dimension 2.0")
    void df20() {
        int size = 25;
        FAggregate aggregate = factory.getFAggregateContext().geometry().d2Hex(size);

        double rg = aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO);

        double kfCalculated = aggregate.size() / (rg * rg);

        double kfPredicted = 1.8138;

        assertEquals(kfPredicted, kfCalculated, 1E-3);
    }

    @Test
    @Tag("Theory")
    @DisplayName("Dimension 3.0")
    void df30() {
        int size = 15;
        FAggregate aggregate = factory.getFAggregateContext().geometry().d3Hex(size);

        double rg = aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO);

        double kfCalculated = aggregate.size() / (rg * rg * rg);

        double kfPredicted = 1.593;

        assertEquals(kfPredicted, kfCalculated, 1E-3);
    }
}

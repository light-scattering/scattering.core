package eu.scattering.core.paper.visual;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Disabled
@DisplayName("Geometry")
public class GeometryTest {

    @Test
    void geometryManual() {
        FAggregate geo = factory.getFAggregateContext().geometry().d3(4, 5, 6);

        assertTrue(geo.isPointConnected());

        String resA = factory.getSaveAspect().getComponentContext().toPovRay(geo, ExPovRay.FREE);

        geo.getRefParticles().scalePosition(3);

        assertFalse(geo.isPointConnected());

        String resb = factory.getSaveAspect().getComponentContext().toPovRay(geo, ExPovRay.FREE);

        geo.forEach(p -> p.scaleSize(3));

        String resC = factory.getSaveAspect().getComponentContext().toPovRay(geo, ExPovRay.FREE);

        assertTrue(geo.isPointConnected());

        System.out.println("");
    }
}

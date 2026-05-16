package eu.scattering.core.paper.morphology;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@DisplayName("Paper - Geometry")
public class VisioGeometryTest {

    @Test
    @Tag("Visual")
    @DisplayName("Geometry - Basic")
    void basic() {
        FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(40);
        FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
        FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

        int particles1d = fAggregate1d.size();
        int particles2d = fAggregate2d.size();
        int particles3d = fAggregate3d.size();

        assertTrue(particles1d > 0);
        assertTrue(particles2d > 0);
        assertTrue(particles3d > 0);

        String fModel1d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate1d, ExPovRay.BOUNDARY);
        assertFalse(fModel1d.isEmpty());

        String fModel2d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate2d, ExPovRay.BOUNDARY);
        assertFalse(fModel2d.isEmpty());

        String fModel3d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate3d, ExPovRay.BOUNDARY);
        assertFalse(fModel3d.isEmpty());
    }

    @Test
    @Tag("Visual")
    @DisplayName("Geometry - Hexagonal")
    void hexagonal() {
        FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2Hex(30, 1);
        FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3Hex(20, 1);

        int particles2d = fAggregate2d.size();
        int particles3d = fAggregate3d.size();

        assertTrue(particles2d > 0);
        assertTrue(particles3d > 0);

        String fModel2d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate2d, ExPovRay.BOUNDARY);
        assertFalse(fModel2d.isEmpty());

        String fModel3d = factory.getSaveAspect().getComponentContext().toPovRay(fAggregate3d, ExPovRay.BOUNDARY);
        assertFalse(fModel3d.isEmpty());
    }
}

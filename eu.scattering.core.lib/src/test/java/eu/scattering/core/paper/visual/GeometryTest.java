package eu.scattering.core.paper.visual;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@DisplayName("Geometry")
public class GeometryTest {

    @Test
    void geometryManual() {
        FAggregate tmp = factory.getFAggregate();
        factory.monitors();
        factory.validators();


        factory.random().dist1D().custom(null);
        factory.random().engine().nextDouble();

        factory.random().attachLinear2D(null, null);
        factory.random().onAxis(null);
        factory.random().project(null, null);

        factory.random().engine().nextDouble();
        factory.random().dist1D().custom(null);


        FAggregate geo = factory.aggregates().geometries().grid3D(4, 5, 6);
        String resA = factory.save().components().toPovRay(geo, ExPovRay.FREE);

        geo.scalePosition(3);

        assertFalse(geo.isPointConnected());

        String resb = factory.save().components().toPovRay(geo, ExPovRay.FREE);

        geo.scaleSize(3);

        String resC = factory.save().components().toPovRay(geo, ExPovRay.FREE);

        assertTrue(geo.isPointConnected());

        System.out.println("");
    }
}

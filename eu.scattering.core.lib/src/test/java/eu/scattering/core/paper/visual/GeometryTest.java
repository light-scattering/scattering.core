package eu.scattering.core.paper.visual;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
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
        FAggregate agg = factory.aggregates().templates().monodisperse(1000, 1);

        FModel model = factory.models().cc().ballistic(agg);

        model.build();

        String res = factory.save().components().toPovRay(agg, ExPovRay.FREE);

        System.out.println("ll");
    }
}

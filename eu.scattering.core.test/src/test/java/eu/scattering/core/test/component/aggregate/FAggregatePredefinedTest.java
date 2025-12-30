package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FAggregate predefined")
public class FAggregatePredefinedTest {

    @Test
    @DisplayName("Construct monodisperse")
    void constructMono() {
        FAggregate fAggregate = factory.getFAggregatePreMono(100, 1);

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(100, fAggregate.getRefParticles().size(),
                        "The number of particles is incorrect"),
                () -> assertEquals(1, fAggregate.getFStatParticleRadius().mean(),
                        epsilon, "The particle radius is erroneous")
        );
    }

    @Test
    @DisplayName("Construct polydisperse")
    void constructPoly() {
//        long timestamp = System.currentTimeMillis();
        ScatFactory factory = FactoryDef.create(1766972568500L);
//        System.out.println(timestamp);

        FAggregate fAggregate = factory.getFAggregatePrePoly(100, 10, 1, 8);

        for (Shape shape : fAggregate.getRefParticles()) {
            assertTrue(shape.getRadius() > 8);
        }

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(100, fAggregate.getRefParticles().size(),
                        "The number of particles is incorrect"),
                () -> assertEquals(10, fAggregate.getFStatParticleRadius().mean(),
                        1, "The particle avg radius is erroneous"),
                () -> assertEquals(1, fAggregate.getFStatParticleRadius().std(true),
                        0.1, "The particle std radius is erroneous")
        );
    }
}

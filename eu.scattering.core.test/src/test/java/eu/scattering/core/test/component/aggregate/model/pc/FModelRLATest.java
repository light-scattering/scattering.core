package eu.scattering.core.test.component.aggregate.model.pc;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.util.support.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FModel PC RLA")
public class FModelRLATest {

    @Test
    @DisplayName("Aggregate 3D")
    void aggregate3D() {
        int quantity = 250;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly, 0);

        FModel modelRLA = factory.createFModelRLA3D(fAggregate);

        modelRLA.build();

        double overlap = fAggregate.getOverlapFactorLinear();

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(fAggregate.isCompact(),
                        "Particles should be connected"),
                () -> assertEquals(0, overlap,
                        epsilon, "Particles should not overlap")
        );
    }

    @Test
    @DisplayName("Aggregate 2D")
    void aggregate2D() {
        int quantity = 250;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly, 0);

        FModel modelRLA = factory.createFModelRLA2D(fAggregate);

        modelRLA.build();

        double overlap = fAggregate.getOverlapFactorLinear();

        for (Shape shape : fAssembly) {
            assertEquals(0, shape.getCenterZ(),
                    "At least one particle has a non-zero Z value");
        }

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(fAggregate.isCompact(),
                        "Particles should be connected"),
                () -> assertEquals(0, overlap,
                        epsilon, "Particles should not overlap")
        );
    }
}

package eu.scattering.core.paper.sandbox;

import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;

@Disabled
@DisplayName("Sandbox")
public class SandboxTest {

    @Test
    void sandbox() {

        factory.aggregates().geometries().grid2D(1, 2, 3);
        factory.export().toPovRay(null, ExPovRay.FREE);
    }
}

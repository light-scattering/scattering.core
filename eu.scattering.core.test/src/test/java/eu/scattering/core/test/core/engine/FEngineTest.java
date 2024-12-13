package eu.scattering.core.test.core.engine;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.data.position.FPos3D;
import eu.scattering.core.design.elements.engine.random.FRandom;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Configuration.*;

@Timeout(5)
@DisplayName("FEngine")
public class FEngineTest {

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FEngineAdvanced {

        @Test
        @DisplayName("Get position on unit sphere")
        void getPositionOnUnitSphere() {
            FRandom fRandom = factory.getFRandom();

            FPos3D fPos = fRandom.getPositionOnUnitSphere();

            FPoint fPoint = factory.getFPoint(fPos);

            double length = fPoint.getLength();
        }

    }
}

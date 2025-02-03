package eu.scattering.core.test.mutable.geometry.primitive.engine;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutable.geometry.primitive.support.FVectorTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FVectorRandom")
public class FVectorRandomTest {

    @Test
    @DisplayName("Set random angle")
    void setRandomAngle() {
        FPoint fPointBase = factory.getFPoint(1, 1, 0);
        FPoint fPointHead = factory.getFPoint(2, 1, 0);
        FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

        factory.getFRandEngine().rndAngle(fPointHead);

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(factory.getFPoint(1, 1, 0).isExact(fVector.getRefBase()),
                        "The base FPoint is erroneous"),
                () -> assertFalse(factory.getFPoint(2, 1, 0).isExact(fVector.getRefHead()),
                        "The head FPoint has not been randomized")
        );
    }

    @Test
    @DisplayName("Set random angle (validate)")
    void setRandomAngleValidate() {
        FVector fVector = TestHelper.getRandFVector();
        FRandEngine random = factory.getFRandEngine();

        FVectorTestHelper.testReference(random::rndAngle, fVector);
    }
}

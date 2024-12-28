package eu.scattering.core.test.core.mutable.geometry.simple;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.helpers.engine.FRandomHelper;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.core.mutable.geometry.simple.support.FVectorTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.factory;
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
        FVector fVector = factory.getFVector(fPointBase, fPointHead);

        factory.getFRandomHelper().rndAngle(fPointHead);

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
        FVector fVector = TestHelper.getRandomFVector();
        FRandomHelper random = factory.getFRandomHelper();

        FVectorTestHelper.testReference(random::rndAngle, fVector);
    }
}

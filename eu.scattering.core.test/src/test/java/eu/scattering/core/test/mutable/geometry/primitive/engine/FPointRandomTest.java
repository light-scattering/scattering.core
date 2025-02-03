package eu.scattering.core.test.mutable.geometry.primitive.engine;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutable.geometry.primitive.support.FPointTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static eu.scattering.core.test.Config.*;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(5)
@DisplayName("FPointRandom")
public class FPointRandomTest {

    @Test
    @DisplayName("Set random angle (validate vector magnitude)")
    void setRandomAngleValidateMagnitude() {
        double radius = Math.abs(rand.nextDouble());

        FPoint fPoint = factory.getFRandEngine().rndAngle(factory.getFPoint(radius));

        assertEquals(radius, fPoint.getMagnitude(),
                epsilon, "The radius is invalid");
    }

    @Test
    @DisplayName("Set random angle (validate correctness)")
    void setRandomAngleValidateCorrectness() {
        double radius = Math.abs(rand.nextDouble());

        FPoint fPointA = factory.getFRandEngine().rndAngle(factory.getFPoint(radius));
        FPoint fPointB = factory.getFRandEngine().rndAngle(factory.getFPoint(radius), fPointA);

        assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
    }

    @Test
    @DisplayName("Set random angle (validate timeout)")
    void setRandomAngleValidateTimeout() {
        double radius = Math.abs(rand.nextDouble());
        FPoint fPoint = factory.getFPoint(radius);

        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> factory.getFRandEngine().rndAngle(fPoint));
    }

    @Test
    @DisplayName("Set random angle (validate)")
    void setRandomAngleValidate() {
        FPoint fPoint = TestHelper.getRandFPoint();
        FRandEngine random = factory.getFRandEngine();

        FPointTestHelper.testReference(random::rndAngle, fPoint);
    }
}

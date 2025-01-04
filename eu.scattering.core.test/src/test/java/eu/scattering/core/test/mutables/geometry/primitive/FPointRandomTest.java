package eu.scattering.core.test.mutables.algebra.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.engines.random.FRandomEngine;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.algebra.geometry.primitive.support.FPointTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static eu.scattering.core.test.Configuration.*;
import static eu.scattering.core.test.Configuration.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(5)
@DisplayName("FPointRandom")
public class FPointRandomTest {

    @Test
    @DisplayName("Set random angle (validate vector magnitude)")
    void setRandomAngleValidateMagnitude() {
        double radius = Math.abs(random.nextDouble());

        FPoint fPoint = factory.getFRandomEngine().rndAngle(factory.getFPoint(radius));

        assertEquals(radius, fPoint.getLength(),
                jitter, "The radius is invalid");
    }

    @Test
    @DisplayName("Set random angle (validate correctness)")
    void setRandomAngleValidateCorrectness() {
        double radius = Math.abs(random.nextDouble());

        FPoint fPointA = factory.getFRandomEngine().rndAngle(factory.getFPoint(radius));
        FPoint fPointB = factory.getFRandomEngine().rndAngle(factory.getFPoint(radius), fPointA);

        assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
    }

    @Test
    @DisplayName("Set random angle (validate timeout)")
    void setRandomAngleValidateTimeout() {
        double radius = Math.abs(random.nextDouble());
        FPoint fPoint = factory.getFPoint(radius);

        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> factory.getFRandomEngine().rndAngle(fPoint));
    }

    @Test
    @DisplayName("Set random angle (validate)")
    void setRandomAngleValidate() {
        FPoint fPoint = TestHelper.getRandomFPoint();
        FRandomEngine random = factory.getFRandomEngine();

        FPointTestHelper.testReference(random::rndAngle, fPoint);
    }
}

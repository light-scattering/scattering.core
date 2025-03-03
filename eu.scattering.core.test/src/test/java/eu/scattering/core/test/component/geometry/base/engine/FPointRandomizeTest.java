package eu.scattering.core.test.component.geometry.base.engine;

import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.base.support.FPointTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointRandom")
public class FPointRandomizeTest {

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

    @Test
    @DisplayName("Set random position")
    void setRandomPosition() {
        FPoint fPoint = factory.getFPoint();
        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPos(fPoint, factory.getFPairPos3D(
                0.01, 0.01, 0.01, 0.02, 0.02, 0.02));

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPoint.getX() > 0.01 && fPoint.getX() < 0.02,
                        "The X value is incorrect"),
                () -> assertTrue(fPoint.getY() > 0.01 && fPoint.getY() < 0.02,
                        "The Y value is incorrect"),
                () -> assertTrue(fPoint.getZ() > 0.01 && fPoint.getZ() < 0.02,
                        "The Z value is incorrect"),
                () -> assertSame(fPoint, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position in sphere")
    void setRandomPositionInSphere() {
        FPoint fPoint = factory.getFPoint();
        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosInSphere(fPoint, 0.01);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPoint.getMagnitude() < 0.01, "The position is incorrect"),
                () -> assertSame(fPoint, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position on sphere")
    void setRandomPositionOnSphere() {
        FPoint fPoint = factory.getFPoint();
        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosOnSphere(fPoint, 0.01);

        Assertions.assertAll("Validate position",
                () -> assertEquals(0.01, fPoint.getMagnitude(), epsilon, "The position is incorrect"),
                () -> assertSame(fPoint, results, "The reference is erroneous")
        );
    }
}

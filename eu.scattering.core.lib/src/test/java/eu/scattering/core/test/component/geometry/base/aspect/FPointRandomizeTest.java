package eu.scattering.core.test.component.geometry.base.aspect;

import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.base.support.FPointTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;

import static eu.scattering.core.test.TestConfig.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointRandom")
public class FPointRandomizeTest {

    @Test
    @DisplayName("Set random angle (validate vector magnitude)")
    void setRandomAngleValidateMagnitude() {
        double radius = Math.abs(rand.nextDouble());

        FPoint fPoint = factory.random().onSphere(factory.getFPoint(radius));

        assertEquals(radius, fPoint.getMagnitude(),
                epsilon, "The radius is invalid");
    }

    @Test
    @DisplayName("Set random angle (validate timeout)")
    void setRandomAngleValidateTimeout() {
        double radius = Math.abs(rand.nextDouble());
        FPoint fPoint = factory.getFPoint(radius);

        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> factory.random().onSphere(fPoint));
    }

    @Test
    @DisplayName("Set random angle (validate)")
    void setRandomAngleValidate() {
        FPoint fPoint = TestHelper.getRandFPoint();
        FRandAspect random = factory.random();

        FPointTestHelper.testReference(random::onSphere, fPoint);
    }

    @Test
    @DisplayName("Set random position")
    void setRandomPosition() {
        FPoint fPoint = factory.getFPoint();
        FRandAspect random = factory.random();

        FPoint results = random.inRange(fPoint, factory.getFPairPos3D(
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
        FRandAspect random = factory.random();

        FPoint results = random.inSphere(fPoint, 0.01);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPoint.getMagnitude() < 0.01, "The magnitude is incorrect"),
                () -> assertSame(fPoint, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position in sphere (self)")
    void setRandomPositionInSphereSelf() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);
        FRandAspect random = factory.random();

        double magnitude = fPoint.getMagnitude();

        FPoint results = random.inSphere(fPoint);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPoint.getMagnitude() < magnitude, "The magnitude is incorrect"),
                () -> assertSame(fPoint, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position on sphere")
    void setRandomPositionOnSphere() {
        FPoint fPoint = factory.getFPoint();
        FRandAspect random = factory.random();

        FPoint results = random.onSphere(fPoint, 0.01);

        Assertions.assertAll("Validate position",
                () -> assertEquals(0.01, fPoint.getMagnitude(), epsilon, "The position is incorrect"),
                () -> assertSame(fPoint, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position base in circle A")
    void setRandomPositionBaseInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = factory.getFPoint(0, 0, 1);
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseInCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 0,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(fPointIn.isOrthogonal(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getMagnitude() < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position base in circle B")
    void setRandomPositionBaseInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = TestHelper.getRandFPoint();
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseInCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPointIn.isOrthogonal(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getMagnitude() < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position base on circle A")
    void setRandomPositionBaseOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = factory.getFPoint(0, 0, 1);
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseOnCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 0,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(fPointIn.isOrthogonal(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getMagnitude(),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position base on circle B")
    void setRandomPositionBaseOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = TestHelper.getRandFPoint();
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseOnCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPointIn.isOrthogonal(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getMagnitude(),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head in circle A")
    void setRandomPositionHeadInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = factory.getFPoint(0, 0, 1);
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadInCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(factory.getFVector(fPointDir, fPointIn).isOrthogonalBaseZero(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getDistance(fPointDir) < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head in circle B")
    void setRandomPositionHeadInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = TestHelper.getRandFPoint();
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadInCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(factory.getFVector(fPointDir, fPointIn).isOrthogonalBaseZero(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getDistance(fPointDir) < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head on circle A")
    void setRandomPositionHeadOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = factory.getFPoint(0, 0, 1);
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadOnCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(factory.getFVector(fPointDir, fPointIn).isOrthogonalBaseZero(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getDistance(fPointDir),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head on circle B")
    void setRandomPositionHeadOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = TestHelper.getRandFPoint();
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadOnCircle(fPointIn, fPointDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(factory.getFVector(fPointDir, fPointIn).isOrthogonalBaseZero(fPointDir),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getDistance(fPointDir),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position on axis A")
    void setRandomPositionOnAxisA() {
        FPoint fPointIn = factory.getFPoint(1, -2, 3);
        FPoint fPointDir = factory.getFPoint(0.001, 0.001, 0.001);

        FRandAspect random = factory.random();

        FPoint results = random.onAxis(fPointIn, fPointDir);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPointIn.isParallel(fPointDir),
                        "The elements should be parallel"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fPointIn.getMagnitude() <= fPointDir.getMagnitude(),
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position on axis B")
    void setRandomPositionOnAxisB() {
        FPoint fPointIn = factory.getFPoint();
        FPoint fPointDir = TestHelper.getRandFPoint();

        FRandAspect random = factory.random();

        FPoint results = random.onAxis(fPointIn, fPointDir);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPointIn.isParallel(fPointDir),
                        "The elements should be parallel"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fPointIn.getMagnitude() <= fPointDir.getMagnitude(),
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position on axis (self)")
    void setRandomPositionOnAxisSelf() {
        FPoint fPointIn = factory.getFPoint(1, -2, 3);

        double magnitude = fPointIn.getMagnitude();

        FRandAspect random = factory.random();

        FPoint results = random.onAxis(fPointIn);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fPointIn.isParallel(1, -2, 3),
                        "The elements should be parallel"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fPointIn.getMagnitude() <= magnitude,
                        "The magnitude is not correct")
        );
    }
}

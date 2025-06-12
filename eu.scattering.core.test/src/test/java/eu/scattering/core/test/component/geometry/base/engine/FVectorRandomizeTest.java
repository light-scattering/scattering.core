package eu.scattering.core.test.component.geometry.base.engine;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.base.support.FVectorTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorRandom")
public class FVectorRandomizeTest {

    @Test
    @DisplayName("Set random angle")
    void setRandomAngle() {
        FPoint fPointBase = factory.getFPoint(1, 1, 0);
        FPoint fPointHead = factory.getFPoint(2, 1, 0);
        FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

        factory.getFRandEngShared().varyAngle(fPointHead);

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
        FRandEngine random = factory.getFRandEngShared();

        FVectorTestHelper.testReference(random::varyAngle, fVector);
    }

    @Test
    @DisplayName("Set random position")
    void setRandomPosition() {
        FVector fVector = factory.getFVector();
        FRandEngine random = factory.getFRandEngShared();

        FVector results = random.rndPos(fVector, factory.getFPairPos3D(
                0.01, 0.01, 0.01, 0.02, 0.02, 0.02));

        Assertions.assertAll("Validate position",
                () -> assertTrue(fVector.getBaseX() > 0.01 && fVector.getBaseX() < 0.02,
                        "The base X value is incorrect"),
                () -> assertTrue(fVector.getBaseY() > 0.01 && fVector.getBaseY() < 0.02,
                        "The base Y value is incorrect"),
                () -> assertTrue(fVector.getBaseZ() > 0.01 && fVector.getBaseZ() < 0.02,
                        "The base Z value is incorrect"),
                () -> assertTrue(fVector.getHeadX() > 0.01 && fVector.getHeadX() < 0.02,
                        "The head X value is incorrect"),
                () -> assertTrue(fVector.getHeadY() > 0.01 && fVector.getHeadY() < 0.02,
                        "The head Y value is incorrect"),
                () -> assertTrue(fVector.getHeadZ() > 0.01 && fVector.getHeadZ() < 0.02,
                        "The Head Z value is incorrect"),
                () -> assertSame(fVector, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position in sphere")
    void setRandomPositionInSphere() {
        FVector fVector = factory.getFVector();
        FRandEngine random = factory.getFRandEngShared();

        FVector results = random.rndPosInSphere(fVector, 0.01);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fVector.getRefBase().getMagnitude() < 0.01,
                        "The base position is incorrect"),
                () -> assertTrue(fVector.getRefHead().getMagnitude() < 0.01,
                        "The head position is incorrect"),
                () -> assertSame(fVector, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position on sphere")
    void setRandomPositionOnSphere() {
        FVector fVector = factory.getFVector();
        FRandEngine random = factory.getFRandEngShared();

        FVector results = random.rndPosOnSphere(fVector, 0.01);

        Assertions.assertAll("Validate position",
                () -> assertEquals(0.01, fVector.getRefBase().getMagnitude(),
                        epsilon, "The base position is incorrect"),
                () -> assertEquals(0.01, fVector.getRefHead().getMagnitude(),
                        epsilon, "The head position is incorrect"),
                () -> assertSame(fVector, results, "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position base in circle A")
    void setRandomPositionBaseInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = factory.getFVector(1, 1, 1, 1, 1, 2);
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosBaseInCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(fVectorDir.isOrthogonalBaseCommon(fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getDistance(fVectorDir.getRefBase()) < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position base in circle B")
    void setRandomPositionBaseInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = TestHelper.getRandFVector();
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosBaseInCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fVectorDir.isOrthogonalBaseCommon(fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getDistance(fVectorDir.getRefBase()) < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position base on circle A")
    void setRandomPositionBaseOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = factory.getFVector(1, 1, 1, 1, 1, 2);
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosBaseOnCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(fVectorDir.isOrthogonalBaseCommon(fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getDistance(fVectorDir.getRefBase()),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position base on circle B")
    void setRandomPositionBaseOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = TestHelper.getRandFVector();
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosBaseOnCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fVectorDir.isOrthogonalBaseCommon(fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getDistance(fVectorDir.getRefBase()),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head in circle A")
    void setRandomPositionHeadInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = factory.getFVector(1, 1, 1, 1, 1, 2);
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosHeadInCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 2,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(fVectorDir.isOrthogonal(fVectorDir.getRefHead(), fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getDistance(fVectorDir.getRefHead()) < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head in circle B")
    void setRandomPositionHeadInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = TestHelper.getRandFVector();
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosHeadInCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fVectorDir.isOrthogonal(fVectorDir.getRefHead(), fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(results.getDistance(fVectorDir.getRefHead()) < radius,
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head on circle A")
    void setRandomPositionHeadOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = factory.getFVector(1, 1, 1, 1, 1, 2);
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosHeadOnCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 2,
                        epsilon, "The position is erroneous"),
                () -> assertTrue(fVectorDir.isOrthogonal(fVectorDir.getRefHead(), fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getDistance(fVectorDir.getRefHead()),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position head on circle B")
    void setRandomPositionHeadOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = TestHelper.getRandFVector();
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosHeadOnCircle(fPointIn, fVectorDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fVectorDir.isOrthogonal(fVectorDir.getRefHead(), fPointIn),
                        "The elements should be orthogonal"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, results.getDistance(fVectorDir.getRefHead()),
                        epsilon, "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position on axis A")
    void setRandomPositionOnAxisA() {
        FPoint fPointIn = factory.getFPoint(1, -2, 3);
        FVector fVectorDir = factory.getFVector(0.001, 0.001, 0.001, 0.002, 0.002, 0.002);

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosOnAxis(fPointIn, fVectorDir);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fVectorDir.getMagnitude(),
                        fPointIn.getDistance(fVectorDir.getRefBase()) + fPointIn.getDistance(fVectorDir.getRefHead()),
                        epsilon, "The elements should be parallel"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fPointIn.getDistance(fVectorDir.getRefBase()) <= fVectorDir.getMagnitude(),
                        "The magnitude is not correct")
        );
    }

    @Test
    @DisplayName("Set random position on axis B")
    void setRandomPositionOnAxisB() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = TestHelper.getRandFVector();

        FRandEngine random = factory.getFRandEngShared();

        FPoint results = random.rndPosOnAxis(fPointIn, fVectorDir);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fVectorDir.getMagnitude(),
                        fPointIn.getDistance(fVectorDir.getRefBase()) + fPointIn.getDistance(fVectorDir.getRefHead()),
                        epsilon, "The elements should be parallel"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fPointIn.getDistance(fVectorDir.getRefBase()) <= fVectorDir.getMagnitude(),
                        "The magnitude is not correct")
        );
    }
}

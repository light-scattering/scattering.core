package eu.scattering.core.test.component.geometry.base.aspect;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.base.support.FVectorTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.epsilon;
import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorRandom")
public class FVectorRandomizeTest {

    @Test
    @DisplayName("Set on sphere")
    void setOnSphere() {
        FPoint fPointBase = factory.getFPoint(1, 1, 0);
        FPoint fPointHead = factory.getFPoint(2, 1, 0);
        FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

        double magnitude = fVector.getMagnitude();

        factory.random().onSphere(fVector);

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(factory.getFPoint(1, 1, 0).isExact(fVector.getRefBase()),
                        "The base FPoint is erroneous"),
                () -> assertFalse(factory.getFPoint(2, 1, 0).isExact(fVector.getRefHead()),
                        "The head FPoint has not been randomized"),
                () -> assertEquals(magnitude, fVector.getMagnitude(),
                        epsilon, "The magnitude is incorrect")
        );
    }


    @Test
    @DisplayName("Set on sphere (validate)")
    void setOnSphereValidate() {
        FVector fVector = TestHelper.getRandFVector();
        FRandAspect random = factory.random();

        FVectorTestHelper.testReference(random::onSphere, fVector);
    }

    @Test
    @DisplayName("Set in sphere")
    void setInSphere() {
        FPoint fPointBase = factory.getFPoint(1, 1, 0);
        FPoint fPointHead = factory.getFPoint(2, 1, 0);
        FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

        double magnitude = fVector.getMagnitude();

        factory.random().inSphere(fVector);

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(factory.getFPoint(1, 1, 0).isExact(fVector.getRefBase()),
                        "The base FPoint is erroneous"),
                () -> assertFalse(factory.getFPoint(2, 1, 0).isExact(fVector.getRefHead()),
                        "The head FPoint has not been randomized"),
                () -> assertTrue(fVector.getMagnitude() < magnitude,
                        "The magnitude is incorrect")
        );
    }

    @Test
    @DisplayName("Set in sphere (validate)")
    void setInSphereValidate() {
        FVector fVector = TestHelper.getRandFVector();
        FRandAspect random = factory.random();

        FVectorTestHelper.testReference(random::inSphere, fVector);
    }

    @Test
    @DisplayName("Set random position base in circle A")
    void setRandomPositionBaseInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FVector fVectorDir = factory.getFVector(1, 1, 1, 1, 1, 2);
        double radius = 0.05;

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseInCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseInCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseOnCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToBaseOnCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadInCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadInCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadOnCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.ortToHeadOnCircle(fPointIn, fVectorDir, radius);

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

        FRandAspect random = factory.random();

        FPoint results = random.onAxis(fPointIn, fVectorDir);

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

        FRandAspect random = factory.random();

        FPoint results = random.onAxis(fPointIn, fVectorDir);

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

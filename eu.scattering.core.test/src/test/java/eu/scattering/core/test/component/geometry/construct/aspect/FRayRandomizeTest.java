package eu.scattering.core.test.component.geometry.construct.aspect;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.test.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRay")
public class FRayRandomizeTest {

    @Test
    @DisplayName("Set random position base in circle A")
    void setRandomPositionBaseInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FRay fRayDir = factory.getRefFRay(factory.getFVector(1, 1, 1, 1, 1, 2));
        double radius = 0.05;

        FRandAspect random = factory.getRandAspect();

        FPoint results = random.ortToBaseInCircle(fPointIn, fRayDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fRayDir.getDistance(fPointIn) < radius,
                        "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fRayDir.getRefOrigin()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position base in circle B")
    void setRandomPositionBaseInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FRay fRayDir = factory.getRefFRay(TestHelper.getRandFVector());
        double radius = 0.05;

        FRandAspect random = factory.getRandAspect();

        FPoint results = random.ortToBaseInCircle(fPointIn, fRayDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fRayDir.getDistance(fPointIn) < radius,
                        "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fRayDir.getRefOrigin()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position base on circle A")
    void setRandomPositionBaseOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FRay fRayDir = factory.getRefFRay(factory.getFVector(1, 1, 1, 1, 1, 2));
        double radius = 0.05;

        FRandAspect random = factory.getRandAspect();

        FPoint results = random.ortToBaseOnCircle(fPointIn, fRayDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, fRayDir.getDistance(fPointIn),
                        epsilon, "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fRayDir.getRefOrigin()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position base on circle B")
    void setRandomPositionBaseOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FRay fRayDir = factory.getRefFRay(TestHelper.getRandFVector());
        double radius = 0.05;

        FRandAspect random = factory.getRandAspect();

        FPoint results = random.ortToBaseOnCircle(fPointIn, fRayDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, fRayDir.getDistance(fPointIn),
                        epsilon, "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fRayDir.getRefOrigin()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }
}

package eu.scattering.core.test.component.geometry.construct.engine;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.test.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FSegment")
public class FSegmentRandomizeTest {

    @Test
    @DisplayName("Set random position base in circle A")
    void setRandomPositionBaseInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(factory.getFVector(1, 1, 1, 1, 1, 2));
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosBaseInCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fSegmentDir.getDistance(fPointIn) < radius,
                        "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.copy()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position base in circle B")
    void setRandomPositionBaseInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(TestHelper.getRandFVector());
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosBaseInCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fSegmentDir.getDistance(fPointIn) < radius,
                        "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.copy()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position base on circle A")
    void setRandomPositionBaseOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(factory.getFVector(1, 1, 1, 1, 1, 2));
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosBaseOnCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 1,
                        epsilon, "The position is erroneous"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, fSegmentDir.getDistance(fPointIn),
                        epsilon, "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.copy()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position base on circle B")
    void setRandomPositionBaseOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(TestHelper.getRandFVector());
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosBaseOnCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, fSegmentDir.getDistance(fPointIn),
                        epsilon, "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.copy()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position head in circle A")
    void setRandomPositionHeadInCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(factory.getFVector(1, 1, 1, 1, 1, 2));
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosHeadInCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 2,
                        epsilon, "The position is erroneous"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fSegmentDir.getDistance(fPointIn) < radius,
                        "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.getRefOrigin().copy().swapBaseWithHead()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position head in circle B")
    void setRandomPositionHeadInCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(TestHelper.getRandFVector());
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosHeadInCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertTrue(fSegmentDir.getDistance(fPointIn) < radius,
                        "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.getRefOrigin().copy().swapBaseWithHead()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position head on circle A")
    void setRandomPositionHeadOnCircleA() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(factory.getFVector(1, 1, 1, 1, 1, 2));
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosHeadOnCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertEquals(fPointIn.getZ(), 2,
                        epsilon, "The position is erroneous"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, fSegmentDir.getDistance(fPointIn),
                        epsilon, "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.getRefOrigin().copy().swapBaseWithHead()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position head on circle B")
    void setRandomPositionHeadOnCircleB() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(TestHelper.getRandFVector());
        double radius = 0.05;

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosHeadOnCircle(fPointIn, fSegmentDir, radius);

        Assertions.assertAll("Validate position",
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous"),
                () -> assertEquals(radius, fSegmentDir.getDistance(fPointIn),
                        epsilon, "The magnitude is not correct"),
                () -> assertTrue(factory.getRefFPlane(fSegmentDir.getRefOrigin().copy().swapBaseWithHead()).isPartOf(fPointIn),
                        "The point should be a part of a plane")
        );
    }

    @Test
    @DisplayName("Set random position on axis A")
    void setRandomPositionOnAxisA() {
        FPoint fPointIn = factory.getFPoint(1, -2, 3);
        FSegment fSegmentDir = factory.getRefFSegment(factory.getFVector(0.001, 0.001, 0.001, 0.002, 0.002, 0.002));

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosOnSegment(fPointIn, fSegmentDir);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fSegmentDir.isPartOf(fPointIn),
                        "The FPoint should be a part of the segment"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous")
        );
    }

    @Test
    @DisplayName("Set random position on axis B")
    void setRandomPositionOnAxisB() {
        FPoint fPointIn = factory.getFPoint();
        FSegment fSegmentDir = factory.getRefFSegment(TestHelper.getRandFVector());

        FRandEngine random = factory.getFRandEngine();

        FPoint results = random.rndPosOnSegment(fPointIn, fSegmentDir);

        Assertions.assertAll("Validate position",
                () -> assertTrue(fSegmentDir.isPartOf(fPointIn),
                        "The FPoint should be a part of the segment"),
                () -> assertSame(fPointIn, results,
                        "The reference is erroneous")
        );
    }
}

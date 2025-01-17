package eu.scattering.core.test.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.primitive.support.FPointTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FPointRotation")
public class FPointRotationTest {

    @Test
    @DisplayName("Rotate (simple, positive)")
    void rotateSimplePositive() {
        FPoint fPointA = factory.getFPoint(1, 1, 0);
        FPoint fPointB = factory.getFPoint(0, 1, 0);

        fPointA.apply(p -> rotation.rotate(p, fPointB, Math.PI));

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointA),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate (simple, negative)")
    void rotateSimpleNegative() {
        FPoint fPointA = factory.getFPoint(1, 1, 0);
        FPoint fPointB = factory.getFPoint(0, 1, 0);

        fPointA.apply(p -> rotation.rotate(p, fPointB, -Math.PI));

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointA),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate (throw IllegalArgumentException)")
    void rotateThrowIllegalArgumentException() {
        FPoint fPointA = factory.getFPoint(1, 1, 0);
        FPoint fPointB = factory.getFPoint();

        Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.rotate(fPointA, fPointB, Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Rotate (validate)")
    void rotateValidate() {
        FPoint fPointA = TestHelper.getRandomFPoint();
        FPoint fPointB = TestHelper.getRandomFPoint(fPointA);

        FPointTestHelper.testReference((a, b) -> rotation.rotate(a, b, Math.PI), fPointA, fPointB);
    }

    @Test
    @DisplayName("Set angle (simple)")
    void setAngleSimple() {
        FPoint fPointA = factory.getFPoint(1, 0, 0);
        FPoint fPointB = factory.getFPoint(0, 1, 0);

        fPointA.apply(p ->  rotation.setAngle(p, fPointB, Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointA.getAngle(fPointB),
                        jitter, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPointA),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle (simple, negative position)")
    void setAngleSimpleNegativePosition() {
        FPoint fPointA = factory.getFPoint(-1, 0, 0);
        FPoint fPointB = factory.getFPoint(0, 1, 0);

        fPointA.apply(p -> rotation.setAngle(p, fPointB, Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointA.getAngle(fPointB),
                        jitter, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointA),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle (simple, negative angle)")
    void setAngleSimpleNegativeAngle() {
        FPoint fPointA = factory.getFPoint(1, 0, 0);
        FPoint fPointB = factory.getFPoint(0, 1, 0);

        fPointA.apply(p -> rotation.setAngle(p, fPointB, -Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointA.getAngle(fPointB),
                        jitter, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointA),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle")
    void setAngle() {
        FPoint fPointA = factory.getFPoint(1, 0, 0);
        FPoint fPointB = factory.getFPoint(0, 1, 0);

        double magnitude = fPointA.getMagnitude();
        double angle = random.nextDouble() % (Math.PI);

        fPointA.apply(p -> rotation.setAngle(p, fPointB, angle));

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPointA.getMagnitude(),
                        jitter, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPointA.getAngle(fPointB),
                        jitter, "The angle is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle (throw IllegalArgumentException)")
    void setAngleThrowIllegalArgumentException() {
        FPoint fPoint = TestHelper.getRandomFPoint();

        Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.setAngle(fPoint, factory.getFPoint(), Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Set angle (validate)")
    void setAngleValidate() {
        FPoint fPointA = TestHelper.getRandomFPoint();
        FPoint fPointB = TestHelper.getRandomFPoint(fPointA);

        FPointTestHelper.testReference((a, b) -> rotation.setAngle(a, b, Math.PI), fPointA, fPointB);
    }
}

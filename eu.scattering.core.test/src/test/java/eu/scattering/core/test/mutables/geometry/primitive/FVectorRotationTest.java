package eu.scattering.core.test.mutables.algebra.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.algebra.geometry.primitive.support.FVectorTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FVectorRotation")
public class FVectorRotationTest {

    @Test
    @DisplayName("Set angle with FVector")
    void setAngleWithFVector() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        rotation.setAngle(fVectorA, fVectorB, angle);

        assertEquals(angle, fVectorA.getAngle(fVectorB),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set angle with FVector (negative)")
    void setAngleWithFVectorNegative() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = TestHelper.getRandomFVector();
        double angle = -Math.abs(random.nextDouble() % Math.PI);

        rotation.setAngle(fVectorA, fVectorB, angle);

        assertEquals(angle, -fVectorA.getAngle(fVectorB),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set angle with FVector (throw IllegalStateException, position)")
    void setAngleWithFVectorThrowIllegalStateExceptionPosition() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = fVectorA.copy();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class, () -> rotation.setAngle(fVectorA, fVectorB, angle),
                "Both FVectors are at the same position");
    }

    @Test
    @DisplayName("Set angle with FVector (throw IllegalArgumentException, direction)")
    void setAngleWithFVectorThrowIllegalArgumentExceptionDirection() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.setAngle(fVectorA, fVectorB, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Set angle with FVector (validate)")
    void setAngleWithFVectorValidate() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference((a, b) -> rotation.setAngle(a, b, Math.PI), fVectorA, fVectorB);
    }

    @Test
    @DisplayName("Rotate with FPoint (simple)")
    void rotateWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        rotation.rotate(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 1, -Math.sqrt(2)),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate with FPoint (simple, negative)")
    void rotateWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        rotation.rotate(fVector, fPoint, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 1, Math.sqrt(2)),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate with FPoint (throw IllegalStateException)")
    void rotateWithFPointThrowIllegalStateException() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = fVector.getRefBase().copy();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class, () -> rotation.rotate(fVector, fPoint, angle),
                "The argument FPoint is at the same position as the base FPoint");
    }

    @Test
    @DisplayName("Rotate with FPoint (validate)")
    void rotateWithFPointValidate() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();

        FVectorTestHelper.testReference((a, b) -> rotation.rotate(a, b, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate with FVector (simple)")
    void rotateWithFVectorSimple() {
        FVector fVectorA = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorB = factory.getFVector(0, 1, 0);

        rotation.rotate(fVectorA, fVectorB, Math.PI * 0.5);

        assertTrue(fVectorA.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate with FVector (simple, negative)")
    void rotateWithFVectorSimpleNegative() {
        FVector fVectorA = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorB = factory.getFVector(0, 1, 0);

        rotation.rotate(fVectorA, fVectorB, -(Math.PI * 0.5));

        assertTrue(fVectorA.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate with FVector (throw IllegalArgumentException)")
    void rotateWithFVectorThrowIllegalArgumentException() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.rotate(fVectorA, fVectorB, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate with FVector (validate)")
    void rotateWithFVectorValidate() {
        FVector fVectorA = TestHelper.getRandomFVector();
        FVector fVectorB = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference((a, b) -> rotation.rotate(a, b, Math.PI), fVectorA, fVectorB);
    }
}

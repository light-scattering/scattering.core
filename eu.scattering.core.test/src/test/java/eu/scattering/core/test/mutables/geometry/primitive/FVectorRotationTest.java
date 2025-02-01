package eu.scattering.core.test.mutables.geometry.primitive;

import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.primitive.support.FVectorTestHelper;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorRotation")
public class FVectorRotationTest {

    @Test
    @DisplayName("Rotate with FRotQt")
    void rotateWithFRotQt() {
        FRotationProcessor fRot = factory.getFRotationProcessor();

        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FRotQt qt = fRot.getRotQt(factory.getFVector(0, 2, 0).toFPairPos3D(), Math.PI * 0.5);

        FVector results = rotation.rotQt(fVector, qt);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle with FVector")
    void setAngleWithFVector() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        rotation.setQtAngle(fVectorArg, fVectorIn, angle);

        assertEquals(angle, fVectorIn.getAngle(fVectorArg),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set angle with FVector (negative)")
    void setAngleWithFVectorNegative() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector(fVectorIn);
        double angle = -Math.abs(random.nextDouble() % Math.PI);

        rotation.setQtAngle(fVectorArg, fVectorIn, angle);

        assertEquals(angle, -fVectorIn.getAngle(fVectorArg),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set angle with FVector (throw IllegalStateException, position)")
    void setAngleWithFVectorThrowIllegalStateExceptionPosition() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = fVectorIn.copy();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class, () -> rotation.setQtAngle(fVectorArg, fVectorIn, angle),
                "Both FVectors are at the same position");
    }

    @Test
    @DisplayName("Set angle with FVector (throw IllegalArgumentException, direction)")
    void setAngleWithFVectorThrowIllegalArgumentExceptionDirection() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.setQtAngle(fVectorArg, fVectorIn, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Set angle with FVector (validate)")
    void setAngleWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference((a, b) -> rotation.setQtAngle(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Set angle with primitives A")
    void setAngleWithPrimitivesA() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngle(
                fVectorArg.getBaseX(), fVectorArg.getBaseY(), fVectorArg.getBaseZ(),
                fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ(),
                fVectorIn, angle
        );

        assertEquals(angle, fVectorIn.getAngle(fVectorArg),
                jitter, "The angle is incorrect");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle with primitives B")
    void setAngleWithPrimitivesB() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngleSimple(
                fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ(),
                fVectorIn, angle
        );

        assertEquals(angle, fVectorIn.getAngleSimple(fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ()),
                jitter, "The angle is incorrect");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle with FPairPos3D")
    void setAngleWithFPairPos3D() {
        FVector fVector = TestHelper.getRandomFVector();
        FPairPos3D fPairPos3D = TestHelper.getRandomFVector().toFPairPos3D();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngle(fPairPos3D, fVector, angle);

        assertEquals(angle, fVector.getAngle(fPairPos3D),
                jitter, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle with FPoint")
    void setAngleWithFPoint() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngleSimple(fPoint, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(fPoint),
                jitter, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle with FPos3D")
    void setAngleWithFPos3D() {
        FVector fVector = TestHelper.getRandomFVector();
        FPos3D fPos3D = TestHelper.getRandomFPoint().toFPos3D();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngleSimple(fPos3D, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(fPos3D),
                jitter, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate FPoint with primitives")
    void rotateFPointWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rotation.rotQtAround(3, 1, 0, 1, 3, 0, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(3, 3, 0),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate FPoint with FVector")
    void rotateFPointWithFVector() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);
        FVector fVector = factory.getFVector(2, 1, 0, 1, 2, 0);

        FPoint results = rotation.rotQtAround(fVector, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(2, 2, 0),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate FPoint with FPairPos3D")
    void rotateFPointWithFPairPos3D() {
        FPoint fPoint = factory.getFPoint(0, 1, 1);
        FPairPos3D fPairPos3D = factory.getFPairPos3D(1, -1, 1, 1, 1, 1);

        FPoint results = rotation.rotQtAround(fPairPos3D, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(2, 1, 1),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FVector (simple)")
    void rotateWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAround(fVectorArg, fVectorIn, Math.PI * 0.5);

        assertTrue(fVectorIn.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FVector (simple, negative)")
    void rotateWithFVectorSimpleNegative() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAround(fVectorArg, fVectorIn, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FVector (validate)")
    void rotateWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference((a, b) -> rotation.rotQtAround(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate with FPoint (simple)")
    void rotateWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundSimple(fPoint, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FPoint (simple, negative)")
    void rotateWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundSimple(fPoint, fVector, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FPoint (validate)")
    void rotateWithFPointValidate() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();

        FVectorTestHelper.testReference((a, b) -> rotation.rotQtAroundSimple(b, a, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate with primitives A")
    void rotateWithPrimitivesA() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAround(0, 0, 0, 0, 2, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with primitives B")
    void rotateWithPrimitivesB() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAroundSimple(0, 2, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FPairPos3D")
    void rotateWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFVector(0, 2, 0).toFPairPos3D();

        FVector results = rotation.rotQtAround(fPairPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FPos3D")
    void rotateWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 2, 0);

        FVector results = rotation.rotQtAroundSimple(fPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FVector (simple)")
    void rotateAxisWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAroundAxis(fVectorArg, fVectorIn, Math.PI * 0.5);

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FVector (simple, negative)")
    void rotateAxisWithFVectorSimpleNegative() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAroundAxis(fVectorArg, fVectorIn, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FVector (throw IllegalArgumentException)")
    void rotateAxisWithFVectorThrowIllegalArgumentException() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.rotQtAroundAxis(fVectorArg, fVectorIn, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate axis with FVector (validate)")
    void rotateAxisWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference((a, b) -> rotation.rotQtAroundAxis(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate axis with FPoint axis (simple)")
    void rotateAxisWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundAxisSimple(fPoint, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FPoint axis (simple, negative)")
    void rotateAxisWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundAxisSimple(fPoint, fVector, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FPoint axis (validate)")
    void rotateAxisWithFPointValidate() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();

        FVectorTestHelper.testReference((a, b) -> rotation.rotQtAroundAxisSimple(b, a, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate axis with primitives A")
    void rotateAxisWithPrimitivesA() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAroundAxis(0, 0, 0, 0, 5, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with primitives B")
    void rotateAxisWithPrimitivesB() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAroundAxisSimple(0, 5, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FPairPos3D")
    void rotateAxisWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFVector(0, 1, 0).toFPairPos3D();

        FVector results = rotation.rotQtAroundAxis(fPairPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate axis with FPos3D")
    void rotateAxisWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rotation.rotQtAroundAxisSimple(fPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }
}

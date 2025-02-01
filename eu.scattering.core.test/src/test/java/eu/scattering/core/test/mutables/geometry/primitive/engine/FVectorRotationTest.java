package eu.scattering.core.test.mutables.geometry.primitive.engine;

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
    @DisplayName("Set Rg angle")
    void setRgAngle() {
        FVector fVectorArg = factory.getFVector(2, 2, 0, 3, 3, 0);
        FVector fVectorRef = factory.getFVector(-1, 0, 0, 1, 0, 0);
        double angle = Math.PI * 0.5;

        rotation.setRgAngle(fVectorRef, fVectorArg, angle);

        assertEquals(angle, fVectorArg.getAngle(fVectorRef),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Rg angle (random)")
    void setRgAngleRandom() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = TestHelper.getRandomFVector(fVectorArg);
        double angle = Math.abs(random.nextDouble() % Math.PI);

        rotation.setRgAngle(fVectorRef, fVectorArg, angle);

        assertEquals(angle, fVectorArg.getAngle(fVectorRef),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Rg angle (random, negative)")
    void setRgAngleRandomNegative() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = TestHelper.getRandomFVector(fVectorArg);
        double angle = -Math.abs(random.nextDouble() % Math.PI);

        rotation.setRgAngle(fVectorRef, fVectorArg, angle);

        assertEquals(angle, -fVectorArg.getAngle(fVectorRef),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Rg angle (throw IllegalStateException)")
    void setRgAngleThrowIllegalStateException() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = fVectorArg.copy();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class,
                () -> rotation.setRgAngle(fVectorRef, fVectorArg, angle),
                "Both FVectors are at the same position");
    }

    @Test
    @DisplayName("Set Rg angle (throw IllegalArgumentException)")
    void setRgAngleThrowIllegalArgumentException() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.setRgAngle(fVectorRef, fVectorArg, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Set Rg angle with FVector (validate)")
    void setRgAngleValidate() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = TestHelper.getRandomFVector(fVectorArg);

        FVectorTestHelper.testReference((a, b) -> rotation.setRgAngle(b, a, Math.PI), fVectorArg, fVectorRef);
    }

    @Test
    @DisplayName("Set Rg angle with primitives A")
    void setRgAngleWithPrimitivesA() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        double angle = Math.PI * 0.5;

        FVector results = rotation.setRgAngle(-1, 0, 0, 1, 0, 0, fVector, angle);

        assertEquals(angle, fVector.getAngle(-1, 0, 0, 1, 0, 0),
                jitter, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle with primitives B")
    void setRgAngleWithPrimitivesB() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        double angle = Math.PI * 0.5;

        FVector results = rotation.setRgAngleCompact(1, 0, 0, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(1, 0, 0),
                jitter, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle with FPairPos3D")
    void setRgAngleWithFPairPos3D() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        FPairPos3D fPairPos3D = factory.getFVector(-1, 0, 0, 1, 0, 0).toFPairPos3D();
        double angle = Math.PI * 0.5;

        FVector results = rotation.setRgAngle(fPairPos3D, fVector, angle);

        assertEquals(angle, fVector.getAngle(fPairPos3D),
                jitter, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle with FPoint")
    void setRgAngleWithFPoint() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        double angle = Math.PI * 0.5;

        FVector results = rotation.setRgAngleCompact(fPoint, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(fPoint),
                jitter, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle with FPos3D")
    void setRgAngleWithFPos3D() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        FPos3D fPos3D = factory.getFPos3D(1, 0, 0);
        double angle = Math.PI * 0.5;

        FVector results = rotation.setRgAngleCompact(fPos3D, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(fPos3D),
                jitter, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg FPoint with primitives")
    void rotateRgFPointWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rotation.rotRgAround(3, 1, 0, 1, 3, 0, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(3, 3, 0),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg FPoint with FVector")
    void rotateRgFPointWithFVector() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);
        FVector fVector = factory.getFVector(2, 1, 0, 1, 2, 0);

        FPoint results = rotation.rotRgAround(fVector, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(2, 2, 0),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg FPoint with FPairPos3D")
    void rotateRgFPointWithFPairPos3D() {
        FPoint fPoint = factory.getFPoint(0, 1, 1);
        FPairPos3D fPairPos3D = factory.getFPairPos3D(1, -1, 1, 1, 1, 1);

        FPoint results = rotation.rotRgAround(fPairPos3D, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(2, 1, 1),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg (simple)")
    void rotateRgWithFVectorSimple() {
        FVector fVectorArg = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorRef = factory.getFVector(0, 1, 0);

        rotation.rotRgAround(fVectorRef, fVectorArg, Math.PI * 0.5);

        assertTrue(fVectorArg.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (simple, negative)")
    void rotateRgWithFVectorSimpleNegative() {
        FVector fVectorArg = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorRef = factory.getFVector(0, 1, 0);

        rotation.rotRgAround(fVectorRef, fVectorArg, -Math.PI * 0.5);

        assertTrue(fVectorArg.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (throw IllegalArgumentException)")
    void rotateRgWithFVectorThrowIllegalArgumentException() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.rotRgAround(fVectorRef, fVectorArg, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Rg (validate)")
    void rotateRgWithFVectorValidate() {
        FVector fVectorArg = TestHelper.getRandomFVector();
        FVector fVectorRef = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.rotRgAround(b, a, Math.PI), fVectorArg, fVectorRef);
    }

    @Test
    @DisplayName("Rotate Rg with primitives A")
    void rotateRgWithPrimitivesA() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotRgAround(0, 0, 0, 0, 1, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg with primitives B")
    void rotateRgWithPrimitivesB() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotRgAroundCompact( 0, 1, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg with FPairPos3D")
    void rotateRgWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFPairPos3D(0, 0, 0, 0, 1, 0);

        FVector results = rotation.rotRgAround(fPairPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg with FPoint")
    void rotateRgWithFPoint() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 1, 0);

        FVector results = rotation.rotRgAroundCompact(fPoint, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg with FPos3D")
    void rotateRgWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rotation.rotRgAroundCompact(fPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (simple)")
    void rotateRgAxisWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotRgAroundBase(fVectorArg, fVectorIn, Math.PI * 0.5);

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (simple, negative)")
    void rotateRgAxisWithFVectorSimpleNegative() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotRgAroundBase(fVectorArg, fVectorIn, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (throw IllegalArgumentException)")
    void rotateRgAxisWithFVectorThrowIllegalArgumentException() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.rotRgAroundBase(fVectorArg, fVectorIn, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (validate)")
    void rotateRgAxisWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.rotRgAroundBase(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate Rg axis with FPoint axis (simple)")
    void rotateRgAxisWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotRgAroundBaseCompact(fPoint, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FPoint axis (simple, negative)")
    void rotateRgAxisWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotRgAroundBaseCompact(fPoint, fVector, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FPoint axis (validate)")
    void rotateRgAxisWithFPointValidate() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.rotRgAroundBaseCompact(b, a, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate Rg axis with primitives A")
    void rotateRgAxisWithPrimitivesA() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotRgAroundBase(0, 0, 0, 0, 5, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with primitives B")
    void rotateRgAxisWithPrimitivesB() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotRgAroundBaseCompact(0, 5, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FPairPos3D")
    void rotateRgAxisWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFVector(0, 1, 0).toFPairPos3D();

        FVector results = rotation.rotRgAroundBase(fPairPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FPos3D")
    void rotateRgAxisWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rotation.rotRgAroundBaseCompact(fPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle with FVector")
    void setQtAngleWithFVector() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        rotation.setQtAngle(fVectorArg, fVectorIn, angle);

        assertEquals(angle, fVectorIn.getAngle(fVectorArg),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (negative)")
    void setQtAngleWithFVectorNegative() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector(fVectorIn);
        double angle = -Math.abs(random.nextDouble() % Math.PI);

        rotation.setQtAngle(fVectorArg, fVectorIn, angle);

        assertEquals(angle, -fVectorIn.getAngle(fVectorArg),
                jitter, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (throw IllegalStateException, position)")
    void setQtAngleWithFVectorThrowIllegalStateExceptionPosition() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = fVectorIn.copy();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class,
                () -> rotation.setQtAngle(fVectorArg, fVectorIn, angle),
                "Both FVectors are at the same position");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (throw IllegalArgumentException, direction)")
    void setQtAngleWithFVectorThrowIllegalArgumentExceptionDirection() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.setQtAngle(fVectorArg, fVectorIn, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (validate)")
    void setQtAngleWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.setQtAngle(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Set Qt angle with primitives A")
    void setQtAngleWithPrimitivesA() {
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
    @DisplayName("Set Qt angle with primitives B")
    void setQtAngleWithPrimitivesB() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngleCompact(
                fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ(),
                fVectorIn, angle
        );

        assertEquals(angle, fVectorIn.getAngleSimple(fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ()),
                jitter, "The angle is incorrect");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle with FPairPos3D")
    void setQtAngleWithFPairPos3D() {
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
    @DisplayName("Set Qt angle with FPoint")
    void setQtAngleWithFPoint() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngleCompact(fPoint, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(fPoint),
                jitter, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle with FPos3D")
    void setQtAngleWithFPos3D() {
        FVector fVector = TestHelper.getRandomFVector();
        FPos3D fPos3D = TestHelper.getRandomFPoint().toFPos3D();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        FVector results = rotation.setQtAngleCompact(fPos3D, fVector, angle);

        assertEquals(angle, fVector.getAngleSimple(fPos3D),
                jitter, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt FPoint with primitives")
    void rotateQtFPointWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rotation.rotQtAround(3, 1, 0, 1, 3, 0, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(3, 3, 0),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt FPoint with FVector")
    void rotateQtFPointWithFVector() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);
        FVector fVector = factory.getFVector(2, 1, 0, 1, 2, 0);

        FPoint results = rotation.rotQtAround(fVector, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(2, 2, 0),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt FPoint with FPairPos3D")
    void rotateQtFPointWithFPairPos3D() {
        FPoint fPoint = factory.getFPoint(0, 1, 1);
        FPairPos3D fPairPos3D = factory.getFPairPos3D(1, -1, 1, 1, 1, 1);

        FPoint results = rotation.rotQtAround(fPairPos3D, fPoint, Math.PI);

        assertTrue(fPoint.isSimilar(2, 1, 1),
                "The position of the rotated FPoint is erroneous");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FVector (simple)")
    void rotateQtWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAround(fVectorArg, fVectorIn, Math.PI * 0.5);

        assertTrue(fVectorIn.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FVector (simple, negative)")
    void rotateQtWithFVectorSimpleNegative() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAround(fVectorArg, fVectorIn, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FVector (validate)")
    void rotateQtWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.rotQtAround(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate Qt with FPoint (simple)")
    void rotateQtWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundCompact(fPoint, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FPoint (simple, negative)")
    void rotateQtWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundCompact(fPoint, fVector, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FPoint (validate)")
    void rotateQtWithFPointValidate() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();

        FVectorTestHelper.testReference((a, b) -> rotation.rotQtAroundCompact(b, a, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate Qt with primitives A")
    void rotateQtWithPrimitivesA() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAround(0, 0, 0, 0, 2, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with primitives B")
    void rotateQtWithPrimitivesB() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAroundCompact(0, 2, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FPairPos3D")
    void rotateQtWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFVector(0, 2, 0).toFPairPos3D();

        FVector results = rotation.rotQtAround(fPairPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FPos3D")
    void rotateQtWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 2, 0);

        FVector results = rotation.rotQtAroundCompact(fPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (simple)")
    void rotateQtAxisWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAroundBase(fVectorArg, fVectorIn, Math.PI * 0.5);

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (simple, negative)")
    void rotateQtAxisWithFVectorSimpleNegative() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rotation.rotQtAroundBase(fVectorArg, fVectorIn, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (throw IllegalArgumentException)")
    void rotateQtAxisWithFVectorThrowIllegalArgumentException() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(random.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.rotQtAroundBase(fVectorArg, fVectorIn, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (validate)")
    void rotateQtAxisWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandomFVector();
        FVector fVectorArg = TestHelper.getRandomFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.rotQtAroundBase(b, a, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate Qt axis with FPoint axis (simple)")
    void rotateQtAxisWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundBaseCompact(fPoint, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FPoint axis (simple, negative)")
    void rotateQtAxisWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rotation.rotQtAroundBaseCompact(fPoint, fVector, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FPoint axis (validate)")
    void rotateQtAxisWithFPointValidate() {
        FVector fVector = TestHelper.getRandomFVector();
        FPoint fPoint = TestHelper.getRandomFPoint();

        FVectorTestHelper.testReference(
                (a, b) -> rotation.rotQtAroundBaseCompact(b, a, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate Qt axis with primitives A")
    void rotateQtAxisWithPrimitivesA() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAroundBase(0, 0, 0, 0, 5, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with primitives B")
    void rotateQtAxisWithPrimitivesB() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rotation.rotQtAroundBaseCompact(0, 5, 0, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FPairPos3D")
    void rotateQtAxisWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFVector(0, 1, 0).toFPairPos3D();

        FVector results = rotation.rotQtAroundBase(fPairPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FPos3D")
    void rotateQtAxisWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rotation.rotQtAroundBaseCompact(fPos3D, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

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
}

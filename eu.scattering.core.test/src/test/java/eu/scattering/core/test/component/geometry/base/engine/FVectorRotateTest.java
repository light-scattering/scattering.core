package eu.scattering.core.test.component.geometry.base.engine;

import eu.scattering.core.design.engine.rotate.generator.FRotGenerator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.base.support.FVectorTestHelper;
import eu.scattering.core.transfer.container.storage.FRotQt.FRotQt;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorRotation")
public class FVectorRotateTest {

    @Test
    @DisplayName("Set Rg angle")
    void setRgAngle() {
        FVector fVectorArg = factory.getFVector(2, 2, 0, 3, 3, 0);
        FVector fVectorRef = factory.getFVector(-1, 0, 0, 1, 0, 0);
        double angle = Math.PI * 0.5;

        rot.setRgAngle(fVectorArg, fVectorRef, angle);

        assertEquals(angle, fVectorArg.getAngle(fVectorRef),
                epsilon, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Rg angle (random)")
    void setRgAngleRandom() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = TestHelper.getRandFVector(fVectorArg);
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        rot.setRgAngle(fVectorArg, fVectorRef, angle);

        assertEquals(angle, fVectorArg.getAngle(fVectorRef),
                epsilon, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Rg angle (random, negative)")
    void setRgAngleRandomNegative() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = TestHelper.getRandFVector(fVectorArg);
        double angle = -Math.abs(rand.nextDouble() % Math.PI);

        rot.setRgAngle(fVectorArg, fVectorRef, angle);

        assertEquals(angle, -fVectorArg.getAngle(fVectorRef),
                epsilon, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Rg angle (throw IllegalStateException)")
    void setRgAngleThrowIllegalStateException() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = fVectorArg.copy();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.setRgAngle(fVectorArg, fVectorRef, angle),
                "Both FVectors are at the same position");
    }

    @Test
    @DisplayName("Set Rg angle (throw IllegalArgumentException)")
    void setRgAngleThrowIllegalArgumentException() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = factory.getFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.setRgAngle(fVectorArg, fVectorRef, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Set Rg angle with FVector (validate)")
    void setRgAngleValidate() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = TestHelper.getRandFVector(fVectorArg);

        FVectorTestHelper.testReference((a, b) -> rot.setRgAngle(a, b, Math.PI), fVectorArg, fVectorRef);
    }

    @Test
    @DisplayName("Set Rg angle with primitives")
    void setRgAngleWithPrimitives() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        double angle = Math.PI * 0.5;

        FVector results = rot.setRgAngle(fVector, -1, 0, 0, 1, 0, 0, angle);

        assertEquals(angle, fVector.getAngle(-1, 0, 0, 1, 0, 0),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle (zero) with primitives")
    void setRgAngleZeroWithPrimitives() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        double angle = Math.PI * 0.5;

        FVector results = rot.setRgAngleBaseZero(fVector, 1, 0, 0, angle);

        assertEquals(angle, fVector.getAngleBaseZero(1, 0, 0),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle (common) with primitives")
    void setRgAngleCommonWithPrimitives() {
        FVector fVector = TestHelper.getRandFVector();
        double angle = Math.PI * 0.75;

        FVector results = rot.setRgAngleBaseCommon(fVector, 1, 2, 3, angle);

        assertEquals(angle, fVector.getAngleBaseCommon(1, 2, 3),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle with FPairPos3D")
    void setRgAngleWithFPairPos3D() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        FPairPos3D fPairPos3D = factory.getFVector(-1, 0, 0, 1, 0, 0).toFPairPos3D();
        double angle = Math.PI * 0.5;

        FVector results = rot.setRgAngle(fVector, fPairPos3D, angle);

        assertEquals(angle, fVector.getAngle(fPairPos3D),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle (zero) with FPoint")
    void setRgAngleZeroWithFPoint() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        double angle = Math.PI * 0.5;

        FVector results = rot.setRgAngleBaseZero(fVector, fPoint, angle);

        assertEquals(angle, fVector.getAngleBaseZero(fPoint),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle (zero) with FPos3D")
    void setRgAngleZeroWithFPos3D() {
        FVector fVector = factory.getFVector(2, 2, 0, 3, 3, 0);
        FPos3D fPos3D = factory.getFPos3D(1, 0, 0);
        double angle = Math.PI * 0.5;

        FVector results = rot.setRgAngleBaseZero(fVector, fPos3D, angle);

        assertEquals(angle, fVector.getAngleBaseZero(fPos3D),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle (common) with FPoint")
    void setRgAngleCommonWithFPoint() {
        FVector fVector = TestHelper.getRandFVector();
        FPoint fPoint = TestHelper.getRandFPoint(fVector.getRefBase(), fVector.getRefHead());
        double angle = Math.PI * 0.25;

        FVector results = rot.setRgAngleBaseCommon(fVector, fPoint, angle);

        assertEquals(angle, fVector.getAngleBaseCommon(fPoint),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Rg angle (common) with FPos3D")
    void setRgAngleCommonWithFPos3D() {
        FVector fVector = TestHelper.getRandFVector();
        FPos3D fPos3D = TestHelper.getRandFPoint(fVector.getRefBase(), fVector.getRefHead()).toFPos3D();
        double angle = Math.PI * 0.25;

        FVector results = rot.setRgAngleBaseCommon(fVector, fPos3D, angle);

        assertEquals(angle, fVector.getAngleBaseCommon(fPos3D),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg FPoint with primitives")
    void rotateRgFPointWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rot.rotRgAround(fPoint, 3, 1, 0, 1, 3, 0, Math.PI);

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

        FPoint results = rot.rotRgAround(fPoint, fVector, Math.PI);

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

        FPoint results = rot.rotRgAround(fPoint, fPairPos3D, Math.PI);

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

        rot.rotRgAround(fVectorArg, fVectorRef, Math.PI * 0.5);

        assertTrue(fVectorArg.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (simple, negative)")
    void rotateRgWithFVectorSimpleNegative() {
        FVector fVectorArg = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorRef = factory.getFVector(0, 1, 0);

        rot.rotRgAround(fVectorArg, fVectorRef, -Math.PI * 0.5);

        assertTrue(fVectorArg.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (throw IllegalArgumentException)")
    void rotateRgWithFVectorThrowIllegalArgumentException() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = factory.getFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotRgAround(fVectorArg, fVectorRef, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Rg (validate)")
    void rotateRgWithFVectorValidate() {
        FVector fVectorArg = TestHelper.getRandFVector();
        FVector fVectorRef = TestHelper.getRandFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rot.rotRgAround(a, b, Math.PI), fVectorArg, fVectorRef);
    }

    @Test
    @DisplayName("Rotate Rg with primitives")
    void rotateRgWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotRgAround(fVector, 0, 0, 0, 0, 1, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg (zero) with primitives")
    void rotateRgZeroWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotRgAroundBaseZero(fVector, 0, 1, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg (common) with primitives")
    void rotateRgCommonWithPrimitives() {
        FVector fVector = factory.getFVector(0, 1, 1, 0, 2, 2);

        FVector results = rot.rotRgAroundBaseCommon(fVector, 0, 1, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, 1, -1, 1, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg with FPairPos3D")
    void rotateRgWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFPairPos3D(0, 0, 0, 0, 1, 0);

        FVector results = rot.rotRgAround(fVector, fPairPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg (zero) with FPoint")
    void rotateRgZeroWithFPoint() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 1, 0);

        FVector results = rot.rotRgAroundBaseZero(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg (zero) with FPos3D")
    void rotateRgZeroWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotRgAroundBaseZero(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg (common) with FPoint")
    void rotateRgCommonWithFPoint() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 1, 0);

        FVector results = rot.rotRgAroundBaseCommon(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -2, 1, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg (common) with FPos3D")
    void rotateRgCommonWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotRgAroundBaseCommon(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -2, 1, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (simple)")
    void rotateRgAxisWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rot.rotRgAroundFixed(fVectorIn, fVectorArg, Math.PI * 0.5);

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

        FVector results = rot.rotRgAroundFixed(fVectorIn, fVectorArg, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (throw IllegalArgumentException)")
    void rotateRgAxisWithFVectorThrowIllegalArgumentException() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotRgAroundFixed(fVectorIn, fVectorArg, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Rg axis with FVector (validate)")
    void rotateRgAxisWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rot.rotRgAroundFixed(a, b, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate Rg axis (zero) with FPoint axis (simple)")
    void rotateRgAxisZeroWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rot.rotRgAroundFixedBaseZero(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (zero) with FPoint axis (simple, negative)")
    void rotateRgAxisZeroWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rot.rotRgAroundFixedBaseZero(fVector, fPoint, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (common) with FPoint axis (simple)")
    void rotateRgAxisCommonWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(-1, 2, 0);

        FVector results = rot.rotRgAroundFixedBaseCommon(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (common) with FPoint axis (simple, negative)")
    void rotateRgAxisCommonWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(-1, 2, 0);

        FVector results = rot.rotRgAroundFixedBaseCommon(fVector, fPoint, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (zero) with FPoint axis (validate)")
    void rotateRgAxisZeroWithFPointValidate() {
        FVector fVector = TestHelper.getRandFVector();
        FPoint fPoint = TestHelper.getRandFPoint();

        FVectorTestHelper.testReference(
                (a, b) -> rot.rotRgAroundFixedBaseZero(a, b, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate Rg axis with primitives")
    void rotateRgAxisWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotRgAroundFixed(fVector, 0, 0, 0, 0, 5, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (zero) with primitives")
    void rotateRgAxisZeroWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotRgAroundFixedBaseZero(fVector, 0, 5, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (common) with primitives")
    void rotateRgAxisCommonWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotRgAroundFixedBaseCommon(fVector, -1, 5, 0, Math.PI * 0.5);

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

        FVector results = rot.rotRgAroundFixed(fVector, fPairPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (zero) with FPos3D")
    void rotateRgAxisZeroWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotRgAroundFixedBaseZero(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Rg axis (common) with FPos3D")
    void rotateRgAxisCommonWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotRgAroundFixedBaseCommon(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -2, 1, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle with FVector")
    void setQtAngleWithFVector() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        rot.setQtAngle(fVectorIn, fVectorArg, angle);

        assertEquals(angle, fVectorIn.getAngle(fVectorArg),
                epsilon, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (negative)")
    void setQtAngleWithFVectorNegative() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector(fVectorIn);
        double angle = -Math.abs(rand.nextDouble() % Math.PI);

        rot.setQtAngle(fVectorIn, fVectorArg, angle);

        assertEquals(angle, -fVectorIn.getAngle(fVectorArg),
                epsilon, "The angle is incorrect");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (throw IllegalStateException, position)")
    void setQtAngleWithFVectorThrowIllegalStateExceptionPosition() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = fVectorIn.copy();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.setQtAngle(fVectorIn, fVectorArg, angle),
                "Both FVectors are at the same position");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (throw IllegalArgumentException, direction)")
    void setQtAngleWithFVectorThrowIllegalArgumentExceptionDirection() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.setQtAngle(fVectorIn, fVectorArg, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Set Qt angle with FVector (validate)")
    void setQtAngleWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rot.setQtAngle(a, b, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Set Qt angle with primitives")
    void setQtAngleWithPrimitives() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        FVector results = rot.setQtAngle(
                fVectorIn, fVectorArg.getBaseX(), fVectorArg.getBaseY(), fVectorArg.getBaseZ(),
                fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ(),
                angle
        );

        assertEquals(angle, fVectorIn.getAngle(fVectorArg),
                epsilon, "The angle is incorrect");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle (zero) with primitives")
    void setQtAngleZeroWithPrimitives() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        FVector results = rot.setQtAngleBaseZero(
                fVectorIn, fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ(),
                angle
        );

        assertEquals(angle, fVectorIn.getAngleBaseZero(fVectorArg.getHeadX(), fVectorArg.getHeadY(), fVectorArg.getHeadZ()),
                epsilon, "The angle is incorrect");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle (common) with primitives")
    void setQtAngleCommonWithPrimitives() {
        FVector fVector = TestHelper.getRandFVector();
        double angle = Math.PI * 0.75;

        FVector results = rot.setQtAngleBaseCommon(fVector, 1, 2, 3, angle);

        assertEquals(angle, fVector.getAngleBaseCommon(1, 2, 3),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Qt angle with FPairPos3D")
    void setQtAngleWithFPairPos3D() {
        FVector fVector = TestHelper.getRandFVector();
        FPairPos3D fPairPos3D = TestHelper.getRandFVector().toFPairPos3D();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        FVector results = rot.setQtAngle(fVector, fPairPos3D, angle);

        assertEquals(angle, fVector.getAngle(fPairPos3D),
                epsilon, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle (zero) with FPoint")
    void setQtAngleZeroWithFPoint() {
        FVector fVector = TestHelper.getRandFVector();
        FPoint fPoint = TestHelper.getRandFPoint();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        FVector results = rot.setQtAngleBaseZero(fVector, fPoint, angle);

        assertEquals(angle, fVector.getAngleBaseZero(fPoint),
                epsilon, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle (zero) with FPos3D")
    void setQtAngleZeroWithFPos3D() {
        FVector fVector = TestHelper.getRandFVector();
        FPos3D fPos3D = TestHelper.getRandFPoint().toFPos3D();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        FVector results = rot.setQtAngleBaseZero(fVector, fPos3D, angle);

        assertEquals(angle, fVector.getAngleBaseZero(fPos3D),
                epsilon, "The angle is incorrect");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set Qt angle (common) with FPoint")
    void setQtAngleCommonWithFPoint() {
        FVector fVector = TestHelper.getRandFVector();
        FPoint fPoint = TestHelper.getRandFPoint(fVector.getRefBase(), fVector.getRefHead());
        double angle = Math.PI * 0.25;

        FVector results = rot.setQtAngleBaseCommon(fVector, fPoint, angle);

        assertEquals(angle, fVector.getAngleBaseCommon(fPoint),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Set Qt angle (common) with FPos3D")
    void setQtAngleCommonWithFPos3D() {
        FVector fVector = TestHelper.getRandFVector();
        FPos3D fPos3D = TestHelper.getRandFPoint(fVector.getRefBase(), fVector.getRefHead()).toFPos3D();
        double angle = Math.PI * 0.25;

        FVector results = rot.setQtAngleBaseCommon(fVector, fPos3D, angle);

        assertEquals(angle, fVector.getAngleBaseCommon(fPos3D),
                epsilon, "The angle is incorrect");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Qt FPoint with primitives")
    void rotateQtFPointWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rot.rotQtAround(fPoint, 3, 1, 0, 1, 3, 0, Math.PI);

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

        FPoint results = rot.rotQtAround(fPoint, fVector, Math.PI);

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

        FPoint results = rot.rotQtAround(fPoint, fPairPos3D, Math.PI);

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

        FVector results = rot.rotQtAround(fVectorIn, fVectorArg, Math.PI * 0.5);

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

        FVector results = rot.rotQtAround(fVectorIn, fVectorArg, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FVector (validate)")
    void rotateQtWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rot.rotQtAround(a, b, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate Qt (zero) with FPoint (simple)")
    void rotateQtZeroWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rot.rotQtAroundBaseZero(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt (zero) with FPoint (simple, negative)")
    void rotateQtZeroWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rot.rotQtAroundBaseZero(fVector, fPoint, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt (zero) with FPoint (validate)")
    void rotateQtZeroWithFPointValidate() {
        FVector fVector = TestHelper.getRandFVector();
        FPoint fPoint = TestHelper.getRandFPoint();

        FVectorTestHelper.testReference((a, b) -> rot.rotQtAroundBaseZero(a, b, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate Qt with primitives")
    void rotateQtWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotQtAround(fVector, 0, 0, 0, 0, 2, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt (zero) with primitives")
    void rotateQtZeroWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotQtAroundBaseZero(fVector, 0, 2, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt (common) with primitives")
    void rotateQtCommonWithPrimitives() {
        FVector fVector = factory.getFVector(0, 1, 1, 0, 2, 2);

        FVector results = rot.rotQtAroundBaseCommon(fVector, 0, 1, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, 1, -1, 1, 2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Qt with FPairPos3D")
    void rotateQtWithFPairPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPairPos3D fPairPos3D = factory.getFVector(0, 2, 0).toFPairPos3D();

        FVector results = rot.rotQtAround(fVector, fPairPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt (zero) with FPoint")
    void rotateQtZeroWithFPoint() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 1, 0);

        FVector results = rot.rotQtAroundBaseZero(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Qt (zero) with FPos3D")
    void rotateQtZeroWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 2, 0);

        FVector results = rot.rotQtAroundBaseZero(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt (common) with FPoint")
    void rotateQtCommonWithFPoint() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 1, 0);

        FVector results = rot.rotQtAroundBaseCommon(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -2, 1, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Qt (common) with FPos3D")
    void rotateQtCommonWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotQtAroundBaseCommon(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -2, 1, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(results, fVector,
                "The reference should not change");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (simple)")
    void rotateQtAxisWithFVectorSimple() {
        FVector fVectorIn = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FVector fVectorArg = factory.getFVector(0, 1, 0);

        FVector results = rot.rotQtAroundFixed(fVectorIn, fVectorArg, Math.PI * 0.5);

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

        FVector results = rot.rotQtAroundFixed(fVectorIn, fVectorArg, -(Math.PI * 0.5));

        assertTrue(fVectorIn.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVectorIn, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (throw IllegalArgumentException)")
    void rotateQtAxisWithFVectorThrowIllegalArgumentException() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = factory.getFVector();
        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotQtAroundFixed(fVectorIn, fVectorArg, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Qt axis with FVector (validate)")
    void rotateQtAxisWithFVectorValidate() {
        FVector fVectorIn = TestHelper.getRandFVector();
        FVector fVectorArg = TestHelper.getRandFVector();

        FVectorTestHelper.testReference(
                (a, b) -> rot.rotQtAroundFixed(a, b, Math.PI), fVectorIn, fVectorArg);
    }

    @Test
    @DisplayName("Rotate Qt axis (zero) with FPoint axis (simple)")
    void rotateQtAxisZeroWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rot.rotQtAroundFixedBaseZero(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (zero) with FPoint axis (simple, negative)")
    void rotateQtAxisZeroWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        FVector results = rot.rotQtAroundFixedBaseZero(fVector, fPoint, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (common) with FPoint axis (simple)")
    void rotateQtAxisCommonWithFPointSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(-1, 2, 0);

        FVector results = rot.rotQtAroundFixedBaseCommon(fVector, fPoint, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (common) with FPoint axis (simple, negative)")
    void rotateQtAxisCommonWithFPointSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPoint fPoint = factory.getFPoint(-1, 2, 0);

        FVector results = rot.rotQtAroundFixedBaseCommon(fVector, fPoint, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, 1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (zero) with FPoint axis (validate)")
    void rotateQtAxisZeroWithFPointValidate() {
        FVector fVector = TestHelper.getRandFVector();
        FPoint fPoint = TestHelper.getRandFPoint();

        FVectorTestHelper.testReference(
                (a, b) -> rot.rotQtAroundFixedBaseZero(a, b, Math.PI), fVector, fPoint);
    }

    @Test
    @DisplayName("Rotate Qt axis with primitives")
    void rotateQtAxisWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotQtAroundFixed(fVector, 0, 0, 0, 0, 5, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (zero) with primitives")
    void rotateQtAxisZeroWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotQtAroundFixedBaseZero(fVector, 0, 5, 0, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (common) with primitives")
    void rotateQtAxisCommonWithPrimitives() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FVector results = rot.rotQtAroundFixedBaseCommon(fVector, -1, 5, 0, Math.PI * 0.5);

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

        FVector results = rot.rotQtAroundFixed(fVector, fPairPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (zero) with FPos3D")
    void rotateQtAxisZeroWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotQtAroundFixedBaseZero(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -1, 2, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt axis (common) with FPos3D")
    void rotateQtAxisCommonWithFPos3D() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FVector results = rot.rotQtAroundFixedBaseCommon(fVector, fPos3D, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(-1, 1, 0, -2, 1, -1),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FRotQt")
    void rotateWithFRotQt() {
        FRotGenerator fRot = factory.getFRotGenerator();

        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);

        FRotQt qt = fRot.getRotQt(factory.getFVector(0, 2, 0).toFPairPos3D(), Math.PI * 0.5);

        FVector results = rot.rotQt(fVector, qt);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
        assertSame(fVector, results,
                "The reference should stay the same");
    }
}

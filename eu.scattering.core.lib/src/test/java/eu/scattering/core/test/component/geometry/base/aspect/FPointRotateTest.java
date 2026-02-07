package eu.scattering.core.test.component.geometry.base.aspect;

import eu.scattering.core.design.aspect.rotate.generator.FRotGenerator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import eu.scattering.core.design.transfer.complex.FRotQt;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.base.support.FPointTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointRotation")
public class FPointRotateTest {

    @Test
    @DisplayName("Rotate Rg with primitives (zero)")
    void rotateRgWithPrimitivesZero() {
        FPoint fPoint = factory.getFPoint(0, 0, 0);

        rot.rotRgAround(fPoint, 1, 0, 0, Math.PI / 2);

        assertTrue(factory.getFPoint(0, 0, 0).isSimilar(fPoint),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg with primitives A (simple, positive)")
    void rotateRgWithPrimitivesSimplePositiveA() {
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        rot.rotRgAround(fPoint, 1, 1, 0, Math.PI / 2);

        assertTrue(factory.getFPoint(1, 1, -Math.sqrt(2)).isSimilar(fPoint),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg with primitives B (simple, positive)")
    void rotateRgWithPrimitivesSimplePositiveB() {
        FPoint fPoint = factory.getFPoint(2, 2, 0);

        rot.rotRgAround(fPoint, 5, 0, 0, Math.PI / 2);

        assertTrue(factory.getFPoint(2, 0, -2).isSimilar(fPoint),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg with primitives A (simple, negative)")
    void rotateRgWithPrimitivesSimpleNegativeA() {
        FPoint fPoint = factory.getFPoint(0, 2, 0);

        rot.rotRgAround(fPoint, 1, 1, 0, -Math.PI / 2);

        assertTrue(factory.getFPoint(1, 1, Math.sqrt(2)).isSimilar(fPoint),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg with primitives B (simple, negative)")
    void rotateRgWithPrimitivesSimpleNegativeB() {
        FPoint fPoint = factory.getFPoint(2, 2, 0);

        rot.rotRgAround(fPoint, 5, 0, 0, -Math.PI / 2);

        assertTrue(factory.getFPoint(2, 0, 2).isSimilar(fPoint),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg with primitives (throw IllegalArgumentException)")
    void rotateRgWithPrimitivesThrowIllegalArgumentException() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotRgAround(fPoint, 0, 0, 0, Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Rotate Rg with primitives (validate)")
    void rotateRgWithPrimitivesValidate() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);

        FPointTestHelper.testReference(p -> rot.rotRgAround(p, 4, 5, 6, Math.PI), fPoint);
    }

    @Test
    @DisplayName("Rotate Rg A (simple, positive)")
    void rotateRgSimplePositiveA() {
        FPoint fPointArg = factory.getFPoint(5, 1, 0);
        FPoint fPointRef = factory.getFPoint(0, 5, 0);

        rot.rotRgAround(fPointArg, fPointRef, Math.PI / 2);

        assertTrue(factory.getFPoint(0, 1, 5).isSimilar(fPointArg),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg B (simple, positive)")
    void rotateRgSimplePositiveB() {
        FPoint fPointArg = factory.getFPoint(2, 2, 0);
        FPoint fPointRef = factory.getFPoint(0, 0, 3);

        rot.rotRgAround(fPointArg, fPointRef, Math.PI / 2);

        assertTrue(factory.getFPoint(2, -2, 0).isSimilar(fPointArg),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg A (simple, negative)")
    void rotateRgSimpleNegativeA() {
        FPoint fPointArg = factory.getFPoint(5, 1, 0);
        FPoint fPointRef = factory.getFPoint(0, 5, 0);

        rot.rotRgAround(fPointArg, fPointRef, -Math.PI / 2);

        assertTrue(factory.getFPoint(0, 1, -5).isSimilar(fPointArg),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg B (simple, negative)")
    void rotateRgSimpleNegativeB() {
        FPoint fPointArg = factory.getFPoint(2, 2, 0);
        FPoint fPointRef = factory.getFPoint(0, 0, 3);

        rot.rotRgAround(fPointArg, fPointRef, -Math.PI / 2);

        assertTrue(factory.getFPoint(-2, 2, 0).isSimilar(fPointArg),
                "The FPoint position is incorrect");
    }

    @Test
    @DisplayName("Rotate Rg (throw IllegalArgumentException)")
    void rotateRgThrowIllegalArgumentException() {
        FPoint fPointArg = factory.getFPoint(1, 1, 0);
        FPoint fPointRef = factory.getFPoint();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotRgAround(fPointArg, fPointRef, Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Rotate (validate)")
    void rotateValidate() {
        FPoint fPointArg = TestHelper.getRandFPoint();
        FPoint fPointRef = TestHelper.getRandFPoint(fPointArg);

        FPointTestHelper.testReference(
                (a, b) -> rot.rotRgAround(a, b, Math.PI), fPointRef, fPointArg);
    }

    @Test
    @DisplayName("Rotate Rg with FPos3D A (simple, positive)")
    void rotateRgWithFPos3DSimplePositiveA() {
        FPoint fPoint = factory.getFPoint(5, 1, 0);
        FPos3D fPos3D = factory.getFPoint(0, 5, 0).toFPos3D();

        FPoint result = rot.rotRgAround(fPoint, fPos3D, Math.PI / 2);

        assertTrue(factory.getFPoint(0, 1, 5).isSimilar(fPoint),
                "The FPoint position is incorrect");
        assertSame(result, fPoint,
                "The reference should be the same");
    }

    @Test
    @DisplayName("Rotate Rg with FPos3D B (simple, positive)")
    void rotateRgWithFPos3DSimplePositiveB() {
        FPoint fPoint = factory.getFPoint(2, 2, 0);
        FPos3D fPos3D = factory.getFPoint(0, 0, 3).toFPos3D();

        FPoint result = rot.rotRgAround(fPoint, fPos3D, Math.PI / 2);

        assertTrue(factory.getFPoint(2, -2, 0).isSimilar(fPoint),
                "The FPoint position is incorrect");
        assertSame(result, fPoint,
                "The reference should be the same");
    }

    @Test
    @DisplayName("Set Rg angle with primitives A (simple)")
    void setRgAngleWithPrimitivesSimpleA() {
        FPoint fPoint = factory.getFPoint(2, 0, 0);

        rot.setRgAngle(fPoint, 0, 5, 0, Math.PI * 0.25);

        double position = 2 / Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.25, fPoint.getAngle(0, 1, 0),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPoint),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle with primitives B (simple)")
    void setRgAngleWithPrimitivesSimpleB() {
        FPoint fPoint = factory.getFPoint(2, 2, 0);

        rot.setRgAngle(fPoint, 5, 0, 0, Math.PI * 0.5);

        double position = 2 * Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.5, fPoint.getAngle(1, 0, 0),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(fPoint.isSimilar(0, position, 0),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle with primitives (simple, negative position)")
    void setRgAngleWithPrimitivesSimpleNegativePosition() {
        FPoint fPoint = factory.getFPoint(-3, 0, 0);

        rot.setRgAngle(fPoint, 0, 2, 0, Math.PI * 0.25);

        double position = 3 / Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.25, fPoint.getAngle(0, 1, 0),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPoint),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle with primitives (simple, negative angle)")
    void setRgAngleWithPrimitivesSimpleNegativeAngle() {
        FPoint fPoint = factory.getFPoint(3, 0, 0);

        rot.setRgAngle(fPoint, 0, 2, 0, -Math.PI * 0.25);

        double position = 3 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPoint.getAngle(0, 1, 0),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPoint),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle with primitives (random)")
    void setAngleWithPrimitivesRandom() {
        FPoint fPoint = TestHelper.getRandFPoint();

        double opX = rand.nextDouble(-1000, 1000);
        double opY = rand.nextDouble(-1000, 1000);
        double opZ = rand.nextDouble(-1000, 1000);

        double magnitude = fPoint.getMagnitude();
        double angle = rand.nextDouble() % (Math.PI);

        rot.setRgAngle(fPoint, opX, opY, opZ, angle);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(magnitude, fPoint.getMagnitude(),
                        epsilon, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPoint.getAngle(opX, opY, opZ),
                        epsilon, "The angle is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle with primitives (throw IllegalArgumentException)")
    void setRgAngleWithPrimitivesThrowIllegalArgumentException() {
        FPoint fPoint = TestHelper.getRandFPoint();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.setRgAngle(fPoint, 0, 0, 0, Math.PI),
                "The axis is not defined");
    }

    @Test
    @DisplayName("Set Rg angle with primitives (throw IllegalStateException)")
    void setRgAngleWithPrimitivesThrowIllegalStateException() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.setRgAngle(fPoint, 1, 2, 3, Math.PI),
                "The axes are similar");
    }

    @Test
    @DisplayName("Set Rg angle with primitives (validate)")
    void setAngleWithPrimitivesValidate() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);

        FPointTestHelper.testReference(p -> rot.setRgAngle(p, 4, 5, 6, Math.PI), fPoint);
    }

    @Test
    @DisplayName("Set Rg angle A (simple)")
    void setRgAngleSimpleA() {
        FPoint fPointArg = factory.getFPoint(2, 0, 0);
        FPoint fPointRef = factory.getFPoint(0, 5, 0);

        rot.setRgAngle(fPointArg, fPointRef, Math.PI * 0.25);

        double position = 2 / Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.25, fPointArg.getAngle(fPointRef),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPointArg),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle B (simple)")
    void setRgAngleSimpleB() {
        FPoint fPointArg = factory.getFPoint(2, 2, 0);
        FPoint fPointRef = factory.getFPoint(5, 0, 0);

        rot.setRgAngle(fPointArg, fPointRef, Math.PI * 0.5);

        double position = 2 * Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.5, fPointArg.getAngle(fPointRef),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(0, position, 0).isSimilar(fPointArg),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle (simple, negative position)")
    void setRgAngleSimpleNegativePosition() {
        FPoint fPointArg = factory.getFPoint(-1, 0, 0);
        FPoint fPointRef = factory.getFPoint(0, 1, 0);

        rot.setRgAngle(fPointArg, fPointRef, Math.PI * 0.25);

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.25, fPointArg.getAngle(fPointRef),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointArg),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle (simple, negative angle)")
    void setRgAngleSimpleNegativeAngle() {
        FPoint fPointArg = factory.getFPoint(1, 0, 0);
        FPoint fPointRef = factory.getFPoint(0, 1, 0);

        rot.setRgAngle(fPointArg, fPointRef, -Math.PI * 0.25);

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointArg.getAngle(fPointRef),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointArg),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set Rg angle (throw IllegalArgumentException)")
    void setRgAngleThrowIllegalArgumentException() {
        FPoint fPoint = TestHelper.getRandFPoint();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.setRgAngle(fPoint, factory.getFPoint(), Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Set angle (throw IllegalStateException)")
    void setAngleThrowIllegalStateException() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.setRgAngle(fPoint, factory.getFPoint(1, 2, 3), Math.PI),
                "The axes are similar");
    }

    @Test
    @DisplayName("Set angle (validate)")
    void setAngleValidate() {
        FPoint fPointRef = factory.getFPoint(1, 2, 3);
        FPoint fPointArg = factory.getFPoint(4, 5, 6);

        FPointTestHelper.testReference((a, b) -> rot.setRgAngle(a, b, Math.PI), fPointRef, fPointArg);
    }

    @Test
    @DisplayName("Set Rg angle with FPos3D A (simple)")
    void setRgAngleWithFPos3DSimpleA() {
        FPoint fPoint = factory.getFPoint(2, 0, 0);
        FPos3D fPos3D = factory.getFPoint(0, 5, 0).toFPos3D();

        FPoint results = rot.setRgAngle(fPoint, fPos3D, Math.PI * 0.25);

        double position = 2 / Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.25, fPoint.getAngle(fPos3D),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPoint),
                        "The position is erroneous"),
                () -> assertSame(fPoint, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Set Rg angle with FPos3D B (simple)")
    void setRgAngleWithFPos3DSimpleB() {
        FPoint fPoint = factory.getFPoint(2, 2, 0);
        FPos3D fPos3D = factory.getFPoint(5, 0, 0).toFPos3D();

        FPoint results = rot.setRgAngle(fPoint, fPos3D, Math.PI * 0.5);

        double position = 2 * Math.sqrt(2);

        Assertions.assertAll("Validate angle",
                () -> assertEquals(Math.PI * 0.5, fPoint.getAngle(fPos3D),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(0, position, 0).isSimilar(fPoint),
                        "The position is erroneous"),
                () -> assertSame(fPoint, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Rotate Qt (zero)")
    void rotateQtZero() {
        FPoint fPointIn = factory.getFPoint(0, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rot.rotQtAround(p, fPointArg, Math.PI));

        assertTrue(factory.getFPoint(0, 0, 0).isSimilar(fPointIn),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate Qt (simple, positive)")
    void rotateQtSimplePositive() {
        FPoint fPointIn = factory.getFPoint(1, 1, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rot.rotQtAround(p, fPointArg, Math.PI));

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointIn),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate Qt (simple, negative)")
    void rotateQtSimpleNegative() {
        FPoint fPointIn = factory.getFPoint(1, 1, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rot.rotQtAround(p, fPointArg, -Math.PI));

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointIn),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate Qt (throw IllegalArgumentException)")
    void rotateQtThrowIllegalArgumentException() {
        FPoint fPointIn = factory.getFPoint(1, 1, 0);
        FPoint fPointArg = factory.getFPoint();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotQtAround(fPointIn, fPointArg, Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Rotate Qt (validate)")
    void rotateQtValidate() {
        FPoint fPointIn = TestHelper.getRandFPoint();
        FPoint fPointArg = TestHelper.getRandFPoint(fPointIn);

        FPointTestHelper.testReference((a, b) -> rot.rotQtAround(a, b, Math.PI), fPointIn, fPointArg);
    }

    @Test
    @DisplayName("Rotate Qt with primitives")
    void rotateQtWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rot.rotQtAround(fPoint, 0, 1, 0, Math.PI);

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPoint),
                "The position is incorrect");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate Qt with FPos3D")
    void rotateQtWithFPos3D() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FPoint results = rot.rotQtAround(fPoint, fPos3D, Math.PI);

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPoint),
                "The position is incorrect");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle Qt (simple)")
    void setAngleQtSimple() {
        FPoint fPointIn = factory.getFPoint(1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p ->  rot.setQtAngle(p, fPointArg, Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointIn.getAngle(fPointArg),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPointIn),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle Qt (simple, negative position)")
    void setAngleQtSimpleNegativePosition() {
        FPoint fPointIn = factory.getFPoint(-1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rot.setQtAngle(p, fPointArg, Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointIn.getAngle(fPointArg),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointIn),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle Qt (simple, negative angle)")
    void setAngleQtSimpleNegativeAngle() {
        FPoint fPointIn = factory.getFPoint(1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rot.setQtAngle(p, fPointArg, -Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointIn.getAngle(fPointArg),
                        epsilon, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointIn),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle Qt")
    void setAngleQt() {
        FPoint fPointIn = factory.getFPoint(1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        double magnitude = fPointIn.getMagnitude();
        double angle = rand.nextDouble() % (Math.PI);

        fPointIn.apply(p -> rot.setQtAngle(p, fPointArg, angle));

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPointIn.getMagnitude(),
                        epsilon, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPointIn.getAngle(fPointArg),
                        epsilon, "The angle is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle Qt (throw IllegalArgumentException)")
    void setAngleQtThrowIllegalArgumentException() {
        FPoint fPoint = TestHelper.getRandFPoint();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.setQtAngle(factory.getFPoint(), fPoint, Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Set angle Qt(validate)")
    void setAngleQtValidate() {
        FPoint fPointIn = TestHelper.getRandFPoint();
        FPoint fPointArg = TestHelper.getRandFPoint(fPointIn);

        FPointTestHelper.testReference(
                (a, b) -> rot.setQtAngle(a, b, Math.PI), fPointIn, fPointArg);
    }

    @Test
    @DisplayName("Set angle Qt with primitives")
    void setAngleQtWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);

        double magnitude = fPoint.getMagnitude();
        double angle = rand.nextDouble() % (Math.PI);

        FPoint results = rot.setQtAngle(fPoint, 0, 1, 0, angle);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPoint.getMagnitude(),
                        epsilon, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPoint.getAngle(0, 1, 0),
                        epsilon, "The angle is erroneous"),
                () -> assertSame(fPoint, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Set angle Qt with FPos3D")
    void setAngleQtWithPos3D() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        double magnitude = fPoint.getMagnitude();
        double angle = rand.nextDouble() % (Math.PI);

        FPoint results = rot.setQtAngle(fPoint, fPos3D, angle);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPoint.getMagnitude(),
                        epsilon, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPoint.getAngle(fPos3D),
                        epsilon, "The angle is erroneous"),
                () -> assertSame(fPoint, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Rotate Qt")
    void rotateQt() {
        FRotGenerator fRot = factory.getFRot();

        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FRotQt qt = fRot.getRotQt(factory.getFPos3D(0, 2, 0), Math.PI * 0.5);

        FPoint results = rot.rotQt(fPoint, qt);

        assertTrue(fPoint.isSimilar(0, 1, 1),
                "The position is incorrect");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }
}

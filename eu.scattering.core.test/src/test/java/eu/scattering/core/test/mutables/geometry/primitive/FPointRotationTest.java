package eu.scattering.core.test.mutables.geometry.primitive;

import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.primitive.support.FPointTestHelper;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointRotation")
public class FPointRotationTest {

    @Test
    @DisplayName("Rotate with FRotQt")
    void rotateWithFRotQt() {
        FRotationProcessor fRot = factory.getFRotationProcessor();

        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FRotQt qt = fRot.getRotQt(factory.getFPos3D(0, 2, 0), Math.PI * 0.5);

        FPoint results = rotation.rotQt(fPoint, qt);

        assertTrue(fPoint.isSimilar(0, 1, 1),
                "The position is incorrect");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate (simple, positive)")
    void rotateSimplePositive() {
        FPoint fPointIn = factory.getFPoint(1, 1, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rotation.rotQtAround(fPointArg, p, Math.PI));

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointIn),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate (simple, negative)")
    void rotateSimpleNegative() {
        FPoint fPointIn = factory.getFPoint(1, 1, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rotation.rotQtAround(fPointArg, p, -Math.PI));

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointIn),
                "The position is incorrect");
    }

    @Test
    @DisplayName("Rotate (throw IllegalArgumentException)")
    void rotateThrowIllegalArgumentException() {
        FPoint fPointIn = factory.getFPoint(1, 1, 0);
        FPoint fPointArg = factory.getFPoint();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rotation.rotQtAround(fPointArg, fPointIn, Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Rotate (validate)")
    void rotateValidate() {
        FPoint fPointIn = TestHelper.getRandomFPoint();
        FPoint fPointArg = TestHelper.getRandomFPoint(fPointIn);

        FPointTestHelper.testReference((a, b) -> rotation.rotQtAround(b, a, Math.PI), fPointIn, fPointArg);
    }

    @Test
    @DisplayName("Rotate with primitives")
    void rotateWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);

        FPoint results = rotation.rotQtAround(0, 1, 0, fPoint, Math.PI);

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPoint),
                "The position is incorrect");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Rotate with FPos3D")
    void rotateWithFPos3D() {
        FPoint fPoint = factory.getFPoint(1, 1, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        FPoint results = rotation.rotQtAround(fPos3D, fPoint, Math.PI);

        assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPoint),
                "The position is incorrect");
        assertSame(fPoint, results,
                "The reference should stay the same");
    }

    @Test
    @DisplayName("Set angle (simple)")
    void setAngleSimple() {
        FPoint fPointIn = factory.getFPoint(1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p ->  rotation.setQtAngle(fPointArg, p, Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointIn.getAngle(fPointArg),
                        jitter, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPointIn),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle (simple, negative position)")
    void setAngleSimpleNegativePosition() {
        FPoint fPointIn = factory.getFPoint(-1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rotation.setQtAngle(fPointArg, p, Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointIn.getAngle(fPointArg),
                        jitter, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointIn),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle (simple, negative angle)")
    void setAngleSimpleNegativeAngle() {
        FPoint fPointIn = factory.getFPoint(1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        fPointIn.apply(p -> rotation.setQtAngle(fPointArg, p, -Math.PI * 0.25));

        double position = 1 / Math.sqrt(2);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(Math.PI * 0.25, fPointIn.getAngle(fPointArg),
                        jitter, "The angle is erroneous"),
                () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointIn),
                        "The position is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle")
    void setAngle() {
        FPoint fPointIn = factory.getFPoint(1, 0, 0);
        FPoint fPointArg = factory.getFPoint(0, 1, 0);

        double magnitude = fPointIn.getMagnitude();
        double angle = random.nextDouble() % (Math.PI);

        fPointIn.apply(p -> rotation.setQtAngle(fPointArg, p, angle));

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPointIn.getMagnitude(),
                        jitter, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPointIn.getAngle(fPointArg),
                        jitter, "The angle is erroneous")
        );
    }

    @Test
    @DisplayName("Set angle (throw IllegalArgumentException)")
    void setAngleThrowIllegalArgumentException() {
        FPoint fPoint = TestHelper.getRandomFPoint();

        Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.setQtAngle(fPoint, factory.getFPoint(), Math.PI),
                "The rotation axis is not defined");
    }

    @Test
    @DisplayName("Set angle (validate)")
    void setAngleValidate() {
        FPoint fPointIn = TestHelper.getRandomFPoint();
        FPoint fPointArg = TestHelper.getRandomFPoint(fPointIn);

        FPointTestHelper.testReference((a, b) -> rotation.setQtAngle(b, a, Math.PI), fPointIn, fPointArg);
    }

    @Test
    @DisplayName("Set angle with primitives")
    void setAngleWithPrimitives() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);

        double magnitude = fPoint.getMagnitude();
        double angle = random.nextDouble() % (Math.PI);

        FPoint results = rotation.setQtAngle(0, 1, 0, fPoint, angle);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPoint.getMagnitude(),
                        jitter, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPoint.getAngle(0, 1, 0),
                        jitter, "The angle is erroneous"),
                () -> assertSame(fPoint, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Set angle with FPos3D")
    void setAngleWithPos3D() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FPos3D fPos3D = factory.getFPos3D(0, 1, 0);

        double magnitude = fPoint.getMagnitude();
        double angle = random.nextDouble() % (Math.PI);

        FPoint results = rotation.setQtAngle(fPos3D, fPoint, angle);

        Assertions.assertAll("Validate rotation",
                () -> assertEquals(magnitude, fPoint.getMagnitude(),
                        jitter, "The magnitude is erroneous"),
                () -> assertEquals(Math.abs(angle), fPoint.getAngle(fPos3D),
                        jitter, "The angle is erroneous"),
                () -> assertSame(fPoint, results,
                        "The reference should stay the same")
        );
    }
}

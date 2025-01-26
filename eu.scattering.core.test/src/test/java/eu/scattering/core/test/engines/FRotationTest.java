package eu.scattering.core.test.engines;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.transfer.containers.engine.FRotQt.FRotQt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRotation")
public class FRotationTest {
    private FRotationProcessor fRot = factory.getFRotationProcessor();
    private FRotationEngine fRotHelper = factory.getFRotationEngine();

    @Test
    @DisplayName("Create with FPoint (positive angle)")
    public void createWithFPointPositiveAngle() {
        FPoint axis = TestHelper.getRandomFPoint();
        double angle = Math.abs(random.nextDouble() % (2 * Math.PI));

        FRotQt rotor = fRot.getRotationQt(axis.toFPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(angle, fRot.getAngle(rotor),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRot.getAxis(rotor)).isParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FPoint (negative angle)")
    public void createWithFPointNegativeAngle() {
        FPoint axis = TestHelper.getRandomFPoint();
        double angle = -Math.abs(random.nextDouble() % (2 * Math.PI));

        FRotQt rotor = fRot.getRotationQt(axis.toFPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(-angle, fRot.getAngle(rotor),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRot.getAxis(rotor)).isAntiParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (positive angle)")
    public void createWithFVectorPositiveAngle() {
        FVector axis = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % (2 * Math.PI));

        FRotQt rotor = fRot.getRotationQt(axis.toFPairPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(angle, fRot.getAngle(rotor),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRot.getAxis(rotor)).isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (negative angle)")
    public void createWithFVectorNegativeAngle() {
        FVector axis = TestHelper.getRandomFVector();
        double angle = -Math.abs(random.nextDouble() % (2 * Math.PI));

        FRotQt rotor = fRot.getRotationQt(axis.toFPairPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(-angle, fRot.getAngle(rotor),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRot.getAxis(rotor)).isAntiParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Get hash code")
    public void getHashCode() {
        FVector fVectorRefA = TestHelper.getRandomFVector();
        FVector fVectorRefB = fVectorRefA.copy();

        FRotQt rotorA = fRot.getRotationQt(fVectorRefA.toFPairPos3D(), 1);
        FRotQt rotorB = fRot.getRotationQt(fVectorRefB.toFPairPos3D(), 1);

        assertEquals(rotorA.hashCode(), rotorB.hashCode(),
                jitter, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Get hash code (fail)")
    public void getHashCodeFail() {
        FVector fVectorRefA = TestHelper.getRandomFVector();
        FVector fVectorRefB = TestHelper.getRandomFVector();

        FRotQt rotorA = fRot.getRotationQt(fVectorRefA.toFPairPos3D(), 1);
        FRotQt rotorB = fRot.getRotationQt(fVectorRefB.toFPairPos3D(), 1);

        assertNotEquals(rotorA.hashCode(), rotorB.hashCode(),
                jitter, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Equals")
    public void equals() {
        FVector fVector = TestHelper.getRandomFVector();

        FRotQt rotorA = fRot.getRotationQt(fVector.toFPairPos3D(), 1);
        FRotQt rotorB = fRot.getRotationQt(fVector.toFPairPos3D(), 1);

        assertEquals(rotorA, rotorB, "FRot instances have the same core");
    }

    @Test
    @DisplayName("Equals (false, wrong axis)")
    public void equalsFalseAxis() {
        FVector fVectorRefA = TestHelper.getRandomFVector();
        FVector fVectorRefB = TestHelper.getRandomFVector();

        FRotQt rotorA = fRot.getRotationQt(fVectorRefA.toFPairPos3D(), 1);
        FRotQt rotorB = fRot.getRotationQt(fVectorRefB.toFPairPos3D(), 1);

        assertNotEquals(rotorA, rotorB, "FRot instances have different core (axis)");
    }

    @Test
    @DisplayName("Equals (false, wrong angle)")
    public void equalsFalseAngle() {
        FVector fVector = TestHelper.getRandomFVector();

        FRotQt rotorA = fRot.getRotationQt(fVector.toFPairPos3D(), 1);
        FRotQt rotorB = fRot.getRotationQt(fVector.toFPairPos3D(), 2);

        assertNotEquals(rotorA, rotorB, "FRot instances have different core (angle)");
    }

    @Test
    @DisplayName("Rotate (validate magnitude)")
    public void rotateValidateLength() {
        FPoint fPoint = TestHelper.getRandomFPoint();
        double length = fPoint.getMagnitude();
        double angle = random.nextDouble() % (2 * Math.PI);
        FRotQt rotor = fRot.getRotationQt(TestHelper.getRandomFPoint().toFPos3D(), angle);

        fRotHelper.rotate(fPoint, rotor);

        assertEquals(length, fPoint.getMagnitude(), jitter, "The magnitude is invalid");
    }

    @Test
    @DisplayName("Rotate A (simple)")
    public void rotateSimpleA() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRotQt rotor = fRot.getRotationQt(factory.getFPoint(0, 0, 1).toFPos3D(), Math.PI * 0.5);

        fRotHelper.rotate(fPoint, rotor);

        assertTrue(factory.getFPoint(0, -1, 0).isSimilar(fPoint), "The position is invalid");

    }

    @Test
    @DisplayName("Rotate B (simple)")
    public void rotateSimpleB() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRotQt rotor = fRot.getRotationQt(factory.getFPoint(0, 0, 1).toFPos3D(), Math.PI * 1.5);

        fRotHelper.rotate(fPoint, rotor);

        assertTrue(factory.getFPoint(0, 1, 0).isSimilar(fPoint), "The position is invalid");

    }

    @Test
    @DisplayName("Rotate C (simple)")
    public void rotateSimpleC() {
        FPoint fPoint = factory.getFPoint(1, 1, 1);
        FRotQt rotor = fRot.getRotationQt(factory.getFPoint(-1, 1, 0).toFPos3D(), Math.PI);

        fRotHelper.rotate(fPoint, rotor);

        assertTrue(factory.getFPoint(-1, -1, -1).isSimilar(fPoint), "The position is invalid");
    }
}

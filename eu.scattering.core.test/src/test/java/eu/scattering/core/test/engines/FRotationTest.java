package eu.scattering.core.test.mutables.immutable;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.engines.rotation.processor.FRotationProcessor;
import eu.scattering.core.design.engines.rotation.FRotationEngine;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.transfer.containers.engine.FRot.FRot;
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

        FRot rotor = fRot.getRotation(axis.toFPos3D(), angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(angle, rotor.getAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(rotor.getAxis()).isParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FPoint (negative angle)")
    public void createWithFPointNegativeAngle() {
        FPoint axis = TestHelper.getRandomFPoint();
        double angle = -Math.abs(random.nextDouble() % (2 * Math.PI));

        FRot rotor = fRot.getRotation(axis.toFPos3D(), angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(-angle, rotor.getAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(rotor.getAxis()).isAntiParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (positive angle)")
    public void createWithFVectorPositiveAngle() {
        FVector axis = TestHelper.getRandomFVector();
        double angle = Math.abs(random.nextDouble() % (2 * Math.PI));

        FRot rotor = fRot.getRotation(axis.toFPairPos3D(), angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(angle, rotor.getAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(rotor.getAxis()).isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (negative angle)")
    public void createWithFVectorNegativeAngle() {
        FVector axis = TestHelper.getRandomFVector();
        double angle = -Math.abs(random.nextDouble() % (2 * Math.PI));

        FRot rotor = fRot.getRotation(axis.toFPairPos3D(), angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(-angle, rotor.getAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(rotor.getAxis()).isAntiParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Get hash code")
    public void getHashCode() {
        FVector fVectorRefA = TestHelper.getRandomFVector();
        FVector fVectorRefB = fVectorRefA.copy();

        FRot rotorA = fRot.getRotation(fVectorRefA.toFPairPos3D(), 1);
        FRot rotorB = fRot.getRotation(fVectorRefB.toFPairPos3D(), 1);

        assertEquals(rotorA.hashCode(), rotorB.hashCode(),
                jitter, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Get hash code (fail)")
    public void getHashCodeFail() {
        FVector fVectorRefA = TestHelper.getRandomFVector();
        FVector fVectorRefB = TestHelper.getRandomFVector();

        FRot rotorA = fRot.getRotation(fVectorRefA.toFPairPos3D(), 1);
        FRot rotorB = fRot.getRotation(fVectorRefB.toFPairPos3D(), 1);

        assertNotEquals(rotorA.hashCode(), rotorB.hashCode(),
                jitter, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Equals")
    public void equals() {
        FVector fVector = TestHelper.getRandomFVector();

        FRot rotorA = fRot.getRotation(fVector.toFPairPos3D(), 1);
        FRot rotorB = fRot.getRotation(fVector.toFPairPos3D(), 1);

        assertEquals(rotorA, rotorB, "FRot instances have the same core");
    }

    @Test
    @DisplayName("Equals (false, wrong axis)")
    public void equalsFalseAxis() {
        FVector fVectorRefA = TestHelper.getRandomFVector();
        FVector fVectorRefB = TestHelper.getRandomFVector();

        FRot rotorA = fRot.getRotation(fVectorRefA.toFPairPos3D(), 1);
        FRot rotorB = fRot.getRotation(fVectorRefB.toFPairPos3D(), 1);

        assertNotEquals(rotorA, rotorB, "FRot instances have different core (axis)");
    }

    @Test
    @DisplayName("Equals (false, wrong angle)")
    public void equalsFalseAngle() {
        FVector fVector = TestHelper.getRandomFVector();

        FRot rotorA = fRot.getRotation(fVector.toFPairPos3D(), 1);
        FRot rotorB = fRot.getRotation(fVector.toFPairPos3D(), 2);

        assertNotEquals(rotorA, rotorB, "FRot instances have different core (angle)");
    }

    @Test
    @DisplayName("Rotate (validate magnitude)")
    public void rotateValidateLength() {
        FPoint fPoint = TestHelper.getRandomFPoint();
        double length = fPoint.getLength();
        double angle = random.nextDouble() % (2 * Math.PI);
        FRot rotor = fRot.getRotation(TestHelper.getRandomFPoint().toFPos3D(), angle);

        fRotHelper.rotate(fPoint, rotor);

        assertEquals(length, fPoint.getLength(), jitter, "The magnitude is invalid");
    }

    @Test
    @DisplayName("Rotate A (simple)")
    public void rotateSimpleA() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRot rotor = fRot.getRotation(factory.getFPoint(0, 0, 1).toFPos3D(), Math.PI * 0.5);

        fRotHelper.rotate(fPoint, rotor);

        assertTrue(factory.getFPoint(0, -1, 0).isSimilar(fPoint), "The position is invalid");

    }

    @Test
    @DisplayName("Rotate B (simple)")
    public void rotateSimpleB() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRot rotor = fRot.getRotation(factory.getFPoint(0, 0, 1).toFPos3D(), Math.PI * 1.5);

        fRotHelper.rotate(fPoint, rotor);

        assertTrue(factory.getFPoint(0, 1, 0).isSimilar(fPoint), "The position is invalid");

    }

    @Test
    @DisplayName("Rotate C (simple)")
    public void rotateSimpleC() {
        FPoint fPoint = factory.getFPoint(1, 1, 1);
        FRot rotor = fRot.getRotation(factory.getFPoint(-1, 1, 0).toFPos3D(), Math.PI);

        fRotHelper.rotate(fPoint, rotor);

        assertTrue(factory.getFPoint(-1, -1, -1).isSimilar(fPoint), "The position is invalid");
    }
}

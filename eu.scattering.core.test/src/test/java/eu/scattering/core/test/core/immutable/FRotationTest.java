package eu.scattering.core.test.core.immutable;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.rotation.FRotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRotation")
public class FRotationTest {

    @Test
    @DisplayName("Create with FPoint (positive angle)")
    public void createWithFPointPositiveAngle() {
        FPoint axis = random.getFPoint();
        double angle = Math.abs(random.getDouble() % (2 * Math.PI));

        FRotation fRotation = factory.getFRotation(axis, angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(angle, fRotation.getRotationAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRotation.getRotationAxis()).isParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FPoint (negative angle)")
    public void createWithFPointNegativeAngle() {
        FPoint axis = random.getFPoint();
        double angle = -Math.abs(random.getDouble() % (2 * Math.PI));

        FRotation fRotation = factory.getFRotation(axis, angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(-angle, fRotation.getRotationAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRotation.getRotationAxis()).isAntiParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (positive angle)")
    public void createWithFVectorPositiveAngle() {
        FVector axis = random.getFVector();
        double angle = Math.abs(random.getDouble() % (2 * Math.PI));

        FRotation fRotation = factory.getFRotation(axis, angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(angle, fRotation.getRotationAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRotation.getRotationAxis()).isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (negative angle)")
    public void createWithFVectorNegativeAngle() {
        FVector axis = random.getFVector();
        double angle = -Math.abs(random.getDouble() % (2 * Math.PI));

        FRotation fRotation = factory.getFRotation(axis, angle);

        Assertions.assertAll("Validate FPoint",
                () -> assertEquals(-angle, fRotation.getRotationAngle(),
                        jitter, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector().set(fRotation.getRotationAxis()).isAntiParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FRotation fRotation = factory.getFRotation("{\"rotor\":[0.5,1,2,3]}");

        assertTrue(factory.getFQuaternion(0.5, 1, 2,3 ).isExact(fRotation.getCore()),
                "The core value is incorrect");
    }

    @Test
    @DisplayName("Create with String (throw IllegalArgumentException)")
    public void createWithStringThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> factory.getFRotation("{\"rotor\":[1,2,3,4]}"),
                "The provided value is erroneous");
    }

    @Test
    @DisplayName("Parse JSON")
    public void parseJSON() {
        FRotation fRotationRefA = factory.getFRotation(random.getFVector(), random.getDouble());
        FRotation fRotationRefB = factory.getFRotation(fRotationRefA.exportToJSON().toString());

        assertEquals(fRotationRefB, fRotationRefA, "The generated JSON object is erroneous");
    }

    @Test
    @DisplayName("Mutability")
    public void mutability() {
        FVector fVectorRef = random.getFVector();
        FRotation fRotationRef = factory.getFRotation(fVectorRef, 1);

        FQuaternion coreRef = fRotationRef.getCore();

        fVectorRef.invertDirection().setLength(random.getDouble());

        assertTrue(coreRef.isExact(fRotationRef.getCore()), "The instance is mutable");
    }

    @Test
    @DisplayName("Get hash code")
    public void getHashCode() {
        FVector fVectorRefA = random.getFVector();
        FVector fVectorRefB = fVectorRefA.copy();

        FRotation fRotationRefA = factory.getFRotation(fVectorRefA, 1);
        FRotation fRotationRefB = factory.getFRotation(fVectorRefB, 1);

        assertEquals(fRotationRefA.hashCode(), fRotationRefB.hashCode(),
                jitter, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Get hash code (fail)")
    public void getHashCodeFail() {
        FVector fVectorRefA = random.getFVector();
        FVector fVectorRefB = random.getFVector();

        FRotation fRotationRefA = factory.getFRotation(fVectorRefA, 1);
        FRotation fRotationRefB = factory.getFRotation(fVectorRefB, 1);

        assertNotEquals(fRotationRefA.hashCode(), fRotationRefB.hashCode(),
                jitter, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Equals")
    public void equals() {
        FVector fVector = random.getFVector();

        FRotation fRotationRefA = factory.getFRotation(fVector, 1);
        FRotation fRotationRefB = factory.getFRotation(fVector, 1);

        assertEquals(fRotationRefB, fRotationRefA, "FRotation instances have the same core");
    }

    @Test
    @DisplayName("Equals (false, wrong axis)")
    public void equalsFalseAxis() {
        FVector fVectorRefA = random.getFVector();
        FVector fVectorRefB = random.getFVector();

        FRotation fRotationRefA = factory.getFRotation(fVectorRefA, 1);
        FRotation fRotationRefB = factory.getFRotation(fVectorRefB, 1);

        assertNotEquals(fRotationRefB, fRotationRefA, "FRotation instances have different core (axis)");
    }

    @Test
    @DisplayName("Equals (false, wrong angle)")
    public void equalsFalseAngle() {
        FVector fVector = random.getFVector();

        FRotation fRotationRefA = factory.getFRotation(fVector, 1);
        FRotation fRotationRefB = factory.getFRotation(fVector, 2);

        assertNotEquals(fRotationRefB, fRotationRefA, "FRotation instances have different core (angle)");
    }

    @Test
    @DisplayName("Rotate (validate magnitude)")
    public void rotateValidateLength() {
        FPoint fPoint = random.getFPoint();
        double length = fPoint.getLength();
        double angle = random.getDouble() % (2 * Math.PI);
        FRotation fRotation = factory.getFRotation(random.getFPoint(), angle);

        fPoint.ext(fRotation.rotate());

        assertEquals(length, fPoint.getLength(), jitter,
                "The magnitude is invalid");
    }

    @Test
    @DisplayName("Rotate A (simple)")
    public void rotateSimpleA() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRotation fRotation = factory.getFRotation(factory.getFPoint(0, 0, 1), Math.PI * 0.5);

        fPoint.ext(fRotation.rotate());

        assertTrue(factory.getFPoint(0, -1, 0).isSimilar(fPoint),
                "The position is invalid");

    }

    @Test
    @DisplayName("Rotate B (simple)")
    public void rotateSimpleB() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRotation fRotation = factory.getFRotation(factory.getFPoint(0, 0, 1), Math.PI * 1.5);

        fPoint.ext(fRotation.rotate());

        assertTrue(factory.getFPoint(0, 1, 0).isSimilar(fPoint),
                "The position is invalid");

    }

    @Test
    @DisplayName("Rotate C (simple)")
    public void rotateSimpleC() {
        FPoint fPoint = factory.getFPoint(1, 1, 1);
        FRotation fRotation = factory.getFRotation(factory.getFPoint(-1, 1, 0), Math.PI);

        fPoint.ext(fRotation.rotate());
        System.out.println(fPoint);
        assertTrue(factory.getFPoint(-1, -1, -1).isSimilar(fPoint),
                "The position is invalid");

    }

}

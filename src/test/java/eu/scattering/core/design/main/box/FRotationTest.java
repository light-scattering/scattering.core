package eu.scattering.core.design.main.box;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.box.rotation.FRotation;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.Config.mainFactory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRotation")
public class FRotationTest {

    @Test
    @DisplayName("Create with FPoint (positive angle)")
    public void createWithFPointPositiveAngle() {
        FPoint axis = RandomHelper.getTestPoint();
        double angle = Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotation fRotation = mainFactory.getFRotation(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(angle, fRotation.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotation.getRotationAxis().isParallel(mainFactory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FPoint (negative angle)")
    public void createWithFPointNegativeAngle() {
        FPoint axis = RandomHelper.getTestPoint();
        double angle = -Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotation fRotation = mainFactory.getFRotation(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(-angle, fRotation.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotation.getRotationAxis().isAntiParallel(mainFactory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (positive angle)")
    public void createWithFVectorPositiveAngle() {
        FVector axis = RandomHelper.getTestVector();
        double angle = Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotation fRotation = mainFactory.getFRotation(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(angle, fRotation.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotation.getRotationAxis().isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (negative angle)")
    public void createWithFVectorNegativeAngle() {
        FVector axis = RandomHelper.getTestVector();
        double angle = -Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotation fRotation = mainFactory.getFRotation(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(-angle, fRotation.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotation.getRotationAxis().isAntiParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FRotation fRotation = mainFactory.getFRotation("{\"rotor\":[0.5,1,2,3]}");

        assertTrue(mainFactory.getFQuaternion(0.5, 1, 2,3 ).isExact(fRotation.getCore()),
                "The core value is incorrect");
    }

    @Test
    @DisplayName("Create with String (throw IllegalArgumentException)")
    public void createWithStringThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> mainFactory.getFRotation("{\"rotor\":[1,2,3,4]}"),
                "The provided value is erroneous");
    }

    @Test
    @DisplayName("Parse JSON")
    public void parseJSON() {
        FRotation fRotationRefA = mainFactory.getFRotation(RandomHelper.getTestVector(), RandomHelper.getTestValue());
        FRotation fRotationRefB = mainFactory.getFRotation(fRotationRefA.exportToJSON().toString());

        assertTrue(fRotationRefA.equals(fRotationRefB), "The generated JSON object is erroneous");
    }

    @Test
    @DisplayName("Mutability")
    public void mutability() {
        FVector fVectorRef = RandomHelper.getTestVector();
        FRotation fRotationRef = mainFactory.getFRotation(fVectorRef, 1);

        FQuaternion coreRef = fRotationRef.getCore();

        fVectorRef.invertDirection().setLength(RandomHelper.getTestValue());

        assertTrue(coreRef.equals(fRotationRef.getCore()), "The instance is mutable");
    }

    @Test
    @DisplayName("Get hash code")
    public void getHashCode() {
        FVector fVectorRefA = RandomHelper.getTestVector();
        FVector fVectorRefB = fVectorRefA.copy();

        FRotation fRotationRefA = mainFactory.getFRotation(fVectorRefA, 1);
        FRotation fRotationRefB = mainFactory.getFRotation(fVectorRefB, 1);

        assertEquals(fRotationRefA.hashCode(), fRotationRefB.hashCode(),
                Config.getJitter(), "The hash code is erroneous");
    }

    @Test
    @DisplayName("Get hash code (fail)")
    public void getHashCodeFail() {
        FVector fVectorRefA = RandomHelper.getTestVector();
        FVector fVectorRefB = RandomHelper.getTestVector();

        FRotation fRotationRefA = mainFactory.getFRotation(fVectorRefA, 1);
        FRotation fRotationRefB = mainFactory.getFRotation(fVectorRefB, 1);

        assertNotEquals(fRotationRefA.hashCode(), fRotationRefB.hashCode(),
                Config.getJitter(), "The hash code is erroneous");
    }

    @Test
    @DisplayName("Equals")
    public void equals() {
        FVector fVector = RandomHelper.getTestVector();

        FRotation fRotationRefA = mainFactory.getFRotation(fVector, 1);
        FRotation fRotationRefB = mainFactory.getFRotation(fVector, 1);

        assertTrue(fRotationRefA.equals(fRotationRefB),
                 "FRotation instances have the same core");
    }

    @Test
    @DisplayName("Equals (false, wrong axis)")
    public void equalsFalseAxis() {
        FVector fVectorRefA = RandomHelper.getTestVector();
        FVector fVectorRefB = RandomHelper.getTestVector();

        FRotation fRotationRefA = mainFactory.getFRotation(fVectorRefA, 1);
        FRotation fRotationRefB = mainFactory.getFRotation(fVectorRefB, 1);

        assertFalse(fRotationRefA.equals(fRotationRefB),
                "FRotation instances have different core (axis)");
    }

    @Test
    @DisplayName("Equals (false, wrong angle)")
    public void equalsFalseAngle() {
        FVector fVector = RandomHelper.getTestVector();

        FRotation fRotationRefA = mainFactory.getFRotation(fVector, 1);
        FRotation fRotationRefB = mainFactory.getFRotation(fVector, 2);

        assertFalse(fRotationRefA.equals(fRotationRefB),
                "FRotation instances have different core (angle)");
    }

    @Test
    @DisplayName("Rotate (validate magnitude)")
    public void rotateValidateLength() {
        FPoint fPoint = RandomHelper.getTestPoint();
        double length = fPoint.getLength();
        double angle = RandomHelper.getTestValue() % (2 * Math.PI);
        FRotation fRotation = mainFactory.getFRotation(RandomHelper.getTestPoint(), angle);

        fPoint.ext(fRotation.rotate());

        assertEquals(length, fPoint.getLength(), Config.getJitter(),
                "The magnitude is invalid");
    }

    @Test
    @DisplayName("Rotate A (simple)")
    public void rotateSimpleA() {
        FPoint fPoint = mainFactory.getFPoint(1, 0, 0);
        FRotation fRotation = mainFactory.getFRotation(mainFactory.getFPoint(0, 0, 1), Math.PI * 0.5);

        fPoint.ext(fRotation.rotate());

        assertTrue(mainFactory.getFPoint(0, -1, 0).isSimilar(fPoint),
                "The position is invalid");

    }

    @Test
    @DisplayName("Rotate B (simple)")
    public void rotateSimpleB() {
        FPoint fPoint = mainFactory.getFPoint(1, 0, 0);
        FRotation fRotation = mainFactory.getFRotation(mainFactory.getFPoint(0, 0, 1), Math.PI * 1.5);

        fPoint.ext(fRotation.rotate());

        assertTrue(mainFactory.getFPoint(0, 1, 0).isSimilar(fPoint),
                "The position is invalid");

    }

    @Test
    @DisplayName("Rotate C (simple)")
    public void rotateSimpleC() {
        FPoint fPoint = mainFactory.getFPoint(1, 1, 1);
        FRotation fRotation = mainFactory.getFRotation(mainFactory.getFPoint(-1, 1, 0), Math.PI);

        fPoint.ext(fRotation.rotate());
        System.out.println(fPoint);
        assertTrue(mainFactory.getFPoint(-1, -1, -1).isSimilar(fPoint),
                "The position is invalid");

    }

}

package eu.scattering.core.design.main.vo;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.Config.mainFactory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRotor")
public class FRotorTest {

    @Test
    @DisplayName("Create with FPoint (positive angle)")
    public void createWithFPointPositiveAngle() {
        FPoint axis = RandomHelper.getTestPoint();
        double angle = Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotor fRotor = mainFactory.getFRotor(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(angle, fRotor.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotor.getRotationAxis().isParallel(mainFactory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FPoint (negative angle)")
    public void createWithFPointNegativeAngle() {
        FPoint axis = RandomHelper.getTestPoint();
        double angle = -Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotor fRotor = mainFactory.getFRotor(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(-angle, fRotor.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotor.getRotationAxis().isAntiParallel(mainFactory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (positive angle)")
    public void createWithFVectorPositiveAngle() {
        FVector axis = RandomHelper.getTestVector();
        double angle = Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotor fRotor = mainFactory.getFRotor(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(angle, fRotor.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotor.getRotationAxis().isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (negative angle)")
    public void createWithFVectorNegativeAngle() {
        FVector axis = RandomHelper.getTestVector();
        double angle = -Math.abs(RandomHelper.getTestValue() % (2 * Math.PI));

        FRotor fRotor = mainFactory.getFRotor(axis, angle);

        assertAll("Validate FPoint",
                () -> assertEquals(-angle, fRotor.getRotationAngle(),
                        Config.getJitter(), "The angle is incorrect"),
                () -> assertTrue(fRotor.getRotationAxis().isAntiParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FRotor fRotor = mainFactory.getFRotor("{\"rotor\":[0.5,1,2,3]}");

        assertTrue(mainFactory.getFQuaternion(0.5, 1, 2,3 ).isExact(fRotor.getCore()),
                "The core value is incorrect");
    }

    @Test
    @DisplayName("Create with String (throw IllegalArgumentException)")
    public void createWithStringThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> mainFactory.getFRotor("{\"rotor\":[1,2,3,4]}"),
                "The provided value is erroneous");
    }

    @Test
    @DisplayName("Parse JSON")
    public void parseJSON() {
        FRotor fRotorRefA = mainFactory.getFRotor(RandomHelper.getTestVector(), RandomHelper.getTestValue());
        FRotor fRotorRefB = mainFactory.getFRotor(fRotorRefA.exportToJSON().toString());

        assertTrue(fRotorRefA.equals(fRotorRefB), "The generated JSON object is erroneous");
    }

    @Test
    @DisplayName("Mutability")
    public void mutability() {
        FVector fVectorRef = RandomHelper.getTestVector();
        FRotor fRotorRef = mainFactory.getFRotor(fVectorRef, 1);

        FQuaternion coreRef = fRotorRef.getCore();

        fVectorRef.invertDirection().setLength(RandomHelper.getTestValue());

        assertTrue(coreRef.equals(fRotorRef.getCore()), "The instance is mutable");
    }

    @Test
    @DisplayName("Get hash code")
    public void getHashCode() {
        FVector fVectorRefA = RandomHelper.getTestVector();
        FVector fVectorRefB = fVectorRefA.copy();

        FRotor fRotorRefA = mainFactory.getFRotor(fVectorRefA, 1);
        FRotor fRotorRefB = mainFactory.getFRotor(fVectorRefB, 1);

        assertEquals(fRotorRefA.hashCode(), fRotorRefB.hashCode(),
                Config.getJitter(), "The hash code is erroneous");
    }

    @Test
    @DisplayName("Get hash code (fail)")
    public void getHashCodeFail() {
        FVector fVectorRefA = RandomHelper.getTestVector();
        FVector fVectorRefB = RandomHelper.getTestVector();

        FRotor fRotorRefA = mainFactory.getFRotor(fVectorRefA, 1);
        FRotor fRotorRefB = mainFactory.getFRotor(fVectorRefB, 1);

        assertNotEquals(fRotorRefA.hashCode(), fRotorRefB.hashCode(),
                Config.getJitter(), "The hash code is erroneous");
    }

    @Test
    @DisplayName("Equals")
    public void equals() {
        FVector fVector = RandomHelper.getTestVector();

        FRotor fRotorRefA = mainFactory.getFRotor(fVector, 1);
        FRotor fRotorRefB = mainFactory.getFRotor(fVector, 1);

        assertTrue(fRotorRefA.equals(fRotorRefB),
                 "FRotor instances have the same core");
    }

    @Test
    @DisplayName("Equals (false, wrong axis)")
    public void equalsFalseAxis() {
        FVector fVectorRefA = RandomHelper.getTestVector();
        FVector fVectorRefB = RandomHelper.getTestVector();

        FRotor fRotorRefA = mainFactory.getFRotor(fVectorRefA, 1);
        FRotor fRotorRefB = mainFactory.getFRotor(fVectorRefB, 1);

        assertFalse(fRotorRefA.equals(fRotorRefB),
                "FRotor instances have different core (axis)");
    }

    @Test
    @DisplayName("Equals (false, wrong angle)")
    public void equalsFalseAngle() {
        FVector fVector = RandomHelper.getTestVector();

        FRotor fRotorRefA = mainFactory.getFRotor(fVector, 1);
        FRotor fRotorRefB = mainFactory.getFRotor(fVector, 2);

        assertFalse(fRotorRefA.equals(fRotorRefB),
                "FRotor instances have different core (angle)");
    }

    @Test
    @DisplayName("Rotate (validate magnitude)")
    public void rotateValidateLength() {
        FPoint fPoint = RandomHelper.getTestPoint();
        double length = fPoint.getLength();
        double angle = RandomHelper.getTestValue() % (2 * Math.PI);
        FRotor fRotor = mainFactory.getFRotor(RandomHelper.getTestPoint(), angle);

        fPoint.ext(fRotor.rotate());

        assertEquals(length, fPoint.getLength(), Config.getJitter(),
                "The magnitude is invalid");
    }

    @Test
    @DisplayName("Rotate A (simple)")
    public void rotateSimpleA() {
        FPoint fPoint = mainFactory.getFPoint(1, 0, 0);
        FRotor fRotor = mainFactory.getFRotor(mainFactory.getFPoint(0, 0, 1), Math.PI * 0.5);

        fPoint.ext(fRotor.rotate());

        assertTrue(mainFactory.getFPoint(0, -1, 0).isSimilar(fPoint),
                "The position is invalid");

    }

    @Test
    @DisplayName("Rotate B (simple)")
    public void rotateSimpleB() {
        FPoint fPoint = mainFactory.getFPoint(1, 0, 0);
        FRotor fRotor = mainFactory.getFRotor(mainFactory.getFPoint(0, 0, 1), Math.PI * 1.5);

        fPoint.ext(fRotor.rotate());

        assertTrue(mainFactory.getFPoint(0, 1, 0).isSimilar(fPoint),
                "The position is invalid");

    }

    @Test
    @DisplayName("Rotate C (simple)")
    public void rotateSimpleC() {
        FPoint fPoint = mainFactory.getFPoint(1, 1, 1);
        FRotor fRotor = mainFactory.getFRotor(mainFactory.getFPoint(-1, 1, 0), Math.PI);

        fPoint.ext(fRotor.rotate());
        System.out.println(fPoint);
        assertTrue(mainFactory.getFPoint(-1, -1, -1).isSimilar(fPoint),
                "The position is invalid");

    }

}

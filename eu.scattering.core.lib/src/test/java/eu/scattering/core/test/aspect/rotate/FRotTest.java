package eu.scattering.core.test.aspect.rotate;

import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.test.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRotation")
public class FRotTest {

    @Test
    @DisplayName("Create with FPoint (positive angle)")
    public void createWithFPointPositiveAngle() {
        FPoint axis = TestHelper.getRandFPoint();
        double angle = Math.abs(rand.nextDouble() % (2 * Math.PI));

        FRotState rotor = factory.rotate().state().aroundAxis(axis.toFPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(angle, rotor.getAngle(),
                        epsilon, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector()
                                .set(rotor.getAxis())
                                .isParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FPoint (negative angle)")
    public void createWithFPointNegativeAngle() {
        FPoint axis = TestHelper.getRandFPoint();
        double angle = -Math.abs(rand.nextDouble() % (2 * Math.PI));

        FRotState rotor = factory.rotate().state().aroundAxis(axis.toFPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(-angle, rotor.getAngle(),
                        epsilon, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector()
                                .set(rotor.getAxis())
                                .isAntiParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (positive angle)")
    public void createWithFVectorPositiveAngle() {
        FVector axis = TestHelper.getRandFVector();
        double angle = Math.abs(rand.nextDouble() % (2 * Math.PI));

        FRotState rotor = factory.rotate().state().aroundAxis(axis.toFPairPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(angle, rotor.getAngle(),
                        epsilon, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector()
                                .set(rotor.getAxis())
                                .isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with FVector (negative angle)")
    public void createWithFVectorNegativeAngle() {
        FVector axis = TestHelper.getRandFVector();
        double angle = -Math.abs(rand.nextDouble() % (2 * Math.PI));

        FRotState rotor = factory.rotate().state().aroundAxis(axis.toFPairPos3D(), angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(-angle, rotor.getAngle(),
                        epsilon, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector()
                                .set(rotor.getAxis())
                                .isAntiParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with primitives A")
    public void createWithPrimitivesA() {
        FVector axis = TestHelper.getRandFVector();

        double bX = axis.getBaseX();
        double bY = axis.getBaseY();
        double bZ = axis.getBaseZ();
        double hX = axis.getHeadX();
        double hY = axis.getHeadY();
        double hZ = axis.getHeadZ();

        double angle = Math.abs(rand.nextDouble() % (2 * Math.PI));

        FRotState rotor = factory.rotate().state().aroundAxis(bX, bY, bZ, hX, hY, hZ, angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(angle, rotor.getAngle(),
                        epsilon, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector()
                                .set(rotor.getAxis())
                                .isParallel(axis),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Create with primitives B")
    public void createWithPrimitivesB() {
        FPoint axis = TestHelper.getRandFPoint();

        double hX = axis.getX();
        double hY = axis.getY();
        double hZ = axis.getZ();

        double angle = Math.abs(rand.nextDouble() % (2 * Math.PI));

        FRotState rotor = factory.rotate().state().aroundAxis(hX, hY, hZ, angle);

        Assertions.assertAll("Validate FRotQt",
                () -> assertEquals(angle, rotor.getAngle(),
                        epsilon, "The angle is incorrect"),
                () -> assertTrue(factory.getFVector()
                                .set(rotor.getAxis())
                                .isParallel(factory.getFVector(axis)),
                        "The axis is incorrect")
        );
    }

    @Test
    @DisplayName("Get hash code")
    public void getHashCode() {
        FVector fVectorRefA = TestHelper.getRandFVector();
        FVector fVectorRefB = fVectorRefA.copy();

        FRotState rotorA = factory.rotate().state().aroundAxis(fVectorRefA.toFPairPos3D(), 1);
        FRotState rotorB = factory.rotate().state().aroundAxis(fVectorRefB.toFPairPos3D(), 1);

        assertEquals(rotorA.hashCode(), rotorB.hashCode(),
                epsilon, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Get hash code (fail)")
    public void getHashCodeFail() {
        FVector fVectorRefA = TestHelper.getRandFVector();
        FVector fVectorRefB = TestHelper.getRandFVector();

        FRotState rotorA = factory.rotate().state().aroundAxis(fVectorRefA.toFPairPos3D(), 1);
        FRotState rotorB = factory.rotate().state().aroundAxis(fVectorRefB.toFPairPos3D(), 1);

        assertNotEquals(rotorA.hashCode(), rotorB.hashCode(),
                epsilon, "The hash code is erroneous");
    }

    @Test
    @DisplayName("Equals")
    public void equals() {
        FVector fVector = TestHelper.getRandFVector();

        FRotState rotorA = factory.rotate().state().aroundAxis(fVector.toFPairPos3D(), 1);
        FRotState rotorB = factory.rotate().state().aroundAxis(fVector.toFPairPos3D(), 1);

        assertEquals(rotorA, rotorB, "FRot instances have the same core");
    }

    @Test
    @DisplayName("Equals (false, wrong axis)")
    public void equalsFalseAxis() {
        FVector fVectorRefA = TestHelper.getRandFVector();
        FVector fVectorRefB = TestHelper.getRandFVector();

        FRotState rotorA = factory.rotate().state().aroundAxis(fVectorRefA.toFPairPos3D(), 1);
        FRotState rotorB = factory.rotate().state().aroundAxis(fVectorRefB.toFPairPos3D(), 1);

        assertNotEquals(rotorA, rotorB, "FRot instances have different core (axis)");
    }

    @Test
    @DisplayName("Equals (false, wrong angle)")
    public void equalsFalseAngle() {
        FVector fVector = TestHelper.getRandFVector();

        FRotState rotorA = factory.rotate().state().aroundAxis(fVector.toFPairPos3D(), 1);
        FRotState rotorB = factory.rotate().state().aroundAxis(fVector.toFPairPos3D(), 2);

        assertNotEquals(rotorA, rotorB, "FRot instances have different core (angle)");
    }

    @Test
    @DisplayName("Rotate (validate magnitude)")
    public void rotateValidateLength() {
        FPoint fPoint = TestHelper.getRandFPoint();
        double length = fPoint.getMagnitude();
        double angle = rand.nextDouble() % (2 * Math.PI);
        FRotState rotor = factory.rotate().state().aroundAxis(TestHelper.getRandFPoint().toFPos3D(), angle);

        factory.rotate().mutate().rotQt(fPoint, rotor);

        assertEquals(length, fPoint.getMagnitude(), epsilon, "The magnitude is invalid");
    }

    @Test
    @DisplayName("Rotate A (simple)")
    public void rotateSimpleA() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRotState rotor = factory.rotate().state().aroundAxis(factory.getFPoint(0, 0, 1).toFPos3D(), Math.PI * 0.5);

        factory.rotate().mutate().rotQt(fPoint, rotor);

        assertTrue(factory.getFPoint(0, -1, 0).isSimilar(fPoint), "The position is invalid");

    }

    @Test
    @DisplayName("Rotate B (simple)")
    public void rotateSimpleB() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);
        FRotState rotor = factory.rotate().state().aroundAxis(factory.getFPoint(0, 0, 1).toFPos3D(), Math.PI * 1.5);

        factory.rotate().mutate().rotQt(fPoint, rotor);

        assertTrue(factory.getFPoint(0, 1, 0).isSimilar(fPoint), "The position is invalid");

    }

    @Test
    @DisplayName("Rotate C (simple)")
    public void rotateSimpleC() {
        FPoint fPoint = factory.getFPoint(1, 1, 1);
        FRotState rotor = factory.rotate().state().aroundAxis(factory.getFPoint(-1, 1, 0).toFPos3D(), Math.PI);

        factory.rotate().mutate().rotQt(fPoint, rotor);

        assertTrue(factory.getFPoint(-1, -1, -1).isSimilar(fPoint), "The position is invalid");
    }
}

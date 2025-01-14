package eu.scattering.core.test.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.Mutable;
import eu.scattering.core.design.mutables.geometry.primitive.Primitive;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.primitive.support.FPointTestHelper;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPoint")
public class FPointTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FPointBasicTest {

        private final double refX = random.nextDouble();
        private final double refY = random.nextDouble();
        private final double refZ = random.nextDouble();

        @Test
        @DisplayName("Construct")
        void construct() {
            FPoint fPoint = factory.getFPoint();

            assertNotNull(fPoint, "The instance is null");

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with X")
        void constructWithX() {
            FPoint fPoint = factory.getFPoint(refX);

            assertNotNull(fPoint, "The instance is null");

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with XY")
        void constructWithXY() {
            FPoint fPoint = factory.getFPoint(refX, refY);

            assertNotNull(fPoint, "The instance is null");

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with XYZ")
        void constructWithXYZ() {
            FPoint fPoint = factory.getFPoint(refX, refY, refZ);

            assertNotNull(fPoint, "The instance is null");

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPos3D")
        void constructWithFPos3D() {
            FPos3D fPos3DRef = factory.getFPos3D(refX, refY, refZ);
            FPoint fPoint = factory.getFPoint(fPos3DRef);

            assertNotNull(fPoint, "The instance is null");

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(fPos3DRef.getD0(), fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(fPos3DRef.getD1(), fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(fPos3DRef.getD2(), fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            FPoint fPoint = factory.getFPoint();

            fPoint.set(refX, refY, refZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives (validate)")
        void setWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint();

            FPointTestHelper.testReference(p -> p.set(refX, refY, refZ), fPointRef);
        }

        @Test
        @DisplayName("Set values with FPoint")
        void setWithFPoint() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPoint = factory.getFPoint();

            fPoint.applyStateFrom(fPointRef);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FPoint (validate)")
        void setWithFPointValidate() {
            FPoint fPointRef = factory.getFPoint();
            FPoint fPointArg = factory.getFPoint(refX, refY, refZ);

            FPointTestHelper.testReference(Primitive::applyStateFrom, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Set values with FPos3D")
        void setWithFPos3D() {
            FPos3D fPos3DRef = factory.getFPos3D(refX, refY, refZ);
            FPoint fPoint = factory.getFPoint();

            fPoint.set(fPos3DRef);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FPos3D (validate)")
        void setWithFPosValidate() {
            FPoint fPointRef = factory.getFPoint();
            FPos3D fPos3DArg = factory.getFPos3D(refX, refY, refZ);

            FPointTestHelper.testReference(p -> p.set(fPos3DArg), fPointRef);
        }

        @Test
        @DisplayName("Set X")
        void setX() {
            FPoint fPoint = factory.getFPoint();

            fPoint.setX(refX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set X (validate)")
        void setXValidate() {
            FPoint fPointRef = factory.getFPoint();

            FPointTestHelper.testReference(p -> p.setX(1), fPointRef);
        }

        @Test
        @DisplayName("Set Y")
        void setY() {
            FPoint fPoint = factory.getFPoint();

            fPoint.setY(refY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set Y (validate)")
        void setYValidate() {
            FPoint fPointRef = factory.getFPoint();

            FPointTestHelper.testReference(p -> p.setY(1), fPointRef);
        }

        @Test
        @DisplayName("Set Z")
        void setZ() {
            FPoint fPoint = factory.getFPoint();

            fPoint.setZ(refZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set Z (validate)")
        void setZValidate() {
            FPoint fPointRef = factory.getFPoint();

            FPointTestHelper.testReference(p -> p.setZ(1), fPointRef);
        }

        @Test
        @DisplayName("Export to FPos3D")
        void toFPos3D() {
            FPoint fPoint = factory.getFPoint(refX, refY, refZ);

            FPos3D fPos3D = fPoint.toFPos3D();

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPos3D.getD0(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPos3D.getD1(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPos3D.getD2(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Export to FPos3D (validate)")
        void toFPos3DValidate() {
            FPoint fPointRef = factory.getFPoint();

            FPointTestHelper.testValue(FPoint::toFPos3D, fPointRef);
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FPointAdvancedTest {

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FPoint fPoint = TestHelper.getRandomFPoint();

            assertEquals(1, fPoint.normalize().getLength(),
                    jitter, "The magnitude of the normalized vector should be equal to one");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {
            FPoint fPoint = factory.getFPoint();

            assertThrows(IllegalStateException.class, fPoint::normalize,
                    "The magnitude of the input vector should not be close to zero");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(FPoint::normalize, fPointRef);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = random.nextDouble();
            double refY = random.nextDouble();
            double refZ = random.nextDouble();

            FPoint fPoint = factory.getFPoint(refX, refY, refZ).reflectThroughCenter();

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(FPoint::reflectThroughCenter, fPointRef);
        }

        @Test
        @DisplayName("Reflect with primitives")
        void reflectWithPrimitives() {
            FPoint fPoint = factory.getFPoint(1, 2, 3).reflect(4, 5, 6);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(7, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(8, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(9, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect with primitives (validate)")
        void reflectWithPrimitivesValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(p -> p.reflect(1, 2, 3), fPointRef);
        }

        @Test
        @DisplayName("Reflect through FPoint")
        void reflectThroughFPoint() {
            double refAX = random.nextDouble();
            double refAY = random.nextDouble();
            double refAZ = random.nextDouble();
            FPoint fPointA = factory.getFPoint(refAX, refAY, refAZ);

            double refBX = random.nextDouble();
            double refBY = random.nextDouble();
            double refBZ = random.nextDouble();
            FPoint fPointB = factory.getFPoint(refBX, refBY, refBZ);

            fPointA.reflect(fPointB);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refBX - (refAX - refBX), fPointA.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(refBY - (refAY - refBY), fPointA.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(refBZ - (refAZ - refBZ), fPointA.getZ(),
                            "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect through point (validate)")
        void reflectThroughFPointValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();
            FPoint fPointArg = TestHelper.getRandomFPoint(fPointRef);

            FPointTestHelper.testReference(FPoint::reflect, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Get length (axes: 1)")
        void getLengthAxes1() {
            double ref = random.nextDouble();
            double expected = Math.abs(ref);

            Assertions.assertAll("Validate FPoint radius",
                    () -> assertEquals(expected, factory.getFPoint().setX(ref).getLength(),
                            jitter, "The magnitude is invalid [X]"),
                    () -> assertEquals(expected, factory.getFPoint().setX(-ref).getLength(),
                            jitter, "The magnitude is invalid [-X]"),
                    () -> assertEquals(expected, factory.getFPoint().setY(ref).getLength(),
                            jitter, "The magnitude is invalid [Y]"),
                    () -> assertEquals(expected, factory.getFPoint().setY(-ref).getLength(),
                            jitter, "The magnitude is invalid [-Y]"),
                    () -> assertEquals(expected, factory.getFPoint().setZ(ref).getLength(),
                            jitter, "The magnitude is invalid [Z]"),
                    () -> assertEquals(expected, factory.getFPoint().setZ(-ref).getLength(),
                            jitter, "The magnitude is invalid [-Z]")
            );
        }

        @Test
        @DisplayName("Get length (axes: 2)")
        void getLengthAxes2() {
            double ref = random.nextDouble();
            double expected = Math.abs(ref * Math.sqrt(2));

            Assertions.assertAll("Validate FPoint radius",
                    () -> assertEquals(expected, factory.getFPoint(ref, ref, 0).getLength(),
                            jitter, "The magnitude is invalid [X, Y]"),
                    () -> assertEquals(expected, factory.getFPoint(ref, -ref, 0).getLength(),
                            jitter, "The magnitude is invalid [X, -Y]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, ref, 0).getLength(),
                            jitter, "The magnitude is invalid [-X, Y]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, -ref, 0).getLength(),
                            jitter, "The magnitude is invalid [-X, -Y]"),
                    () -> assertEquals(expected, factory.getFPoint(ref, 0, ref).getLength(),
                            jitter, "The magnitude is invalid [X, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(ref, 0, -ref).getLength(),
                            jitter, "The magnitude is invalid [X, -Z]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, 0, ref).getLength(),
                            jitter, "The magnitude is invalid [-X, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, 0, -ref).getLength(),
                            jitter, "The magnitude is invalid [-X, -Z]"),
                    () -> assertEquals(expected, factory.getFPoint(0, ref, ref).getLength(),
                            jitter, "The magnitude is invalid [Y, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(0, ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [Y, -Z]"),
                    () -> assertEquals(expected, factory.getFPoint(0, -ref, ref).getLength(),
                            jitter, "The magnitude is invalid [-Y, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(0, -ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [-Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get length (axes: 3)")
        void getLengthAxes3() {
            double ref = random.nextDouble();
            double expected = Math.abs(ref * Math.sqrt(3));

            Assertions.assertAll("Validate radius",
                    () -> assertEquals(expected, factory.getFPoint(ref, ref, ref).getLength(),
                            jitter, "The magnitude is invalid [X, Y, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(ref, ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [X, Y, -Z]"),
                    () -> assertEquals(expected, factory.getFPoint(ref, -ref, ref).getLength(),
                            jitter, "The magnitude is invalid [X, -Y, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(ref, -ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [X, -Y, -Z]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, ref, ref).getLength(),
                            jitter, "The magnitude is invalid [-X, Y, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [-X, Y, -Z]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, -ref, ref).getLength(),
                            jitter, "The magnitude is invalid [-X, -Y, Z]"),
                    () -> assertEquals(expected, factory.getFPoint(-ref, -ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [-X, -Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get length (validate)")
        void getLengthValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testValue(FPoint::getLength, fPointRef);
        }

        @Test
        @DisplayName("Get length P2")
        void getLengthP2() {
            double x = random.nextDouble();
            double y = random.nextDouble();
            double z = random.nextDouble();

            FPoint fPoint = factory.getFPoint(x, y, z);
            double lengthP2 = (x * x) + (y * y) + (z * z);

            assertEquals(lengthP2, fPoint.getLengthP2(),
                    jitter, "The squared length is erroneous");
        }

        @Test
        @DisplayName("Get length P2 (validate)")
        void getLengthP2Validate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testValue(FPoint::getLengthP2, fPointRef);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPoint = factory.getFPoint(1, 2, 3).setLength(9);

            assertEquals(9, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (negative)")
        void setLengthNegative() {
            FPoint fPoint = factory.getFPoint(1, 2, 3).setLength(-9);

            assertEquals(9, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length position")
        void setLengthPosition() {
            FPoint fPoint = factory.getFPoint(1, 1, 1).setLength(5 * Math.sqrt(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(5, fPoint.getX(), jitter, "The X value is incorrect"),
                    () -> assertEquals(5, fPoint.getY(), jitter, "The Y value is incorrect"),
                    () -> assertEquals(5, fPoint.getZ(), jitter, "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set length position (negative)")
        void setLengthPositionNegative() {
            FPoint fPoint = factory.getFPoint(1, 1, 1).setLength(-5 * Math.sqrt(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-5, fPoint.getX(), jitter, "The X value is incorrect"),
                    () -> assertEquals(-5, fPoint.getY(), jitter, "The Y value is incorrect"),
                    () -> assertEquals(-5, fPoint.getZ(), jitter, "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set length (throw IllegalStateException)")
        void setLengthThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class,
                    () -> factory.getFPoint().setLength(1),
                    "The position of the reference FPoint must not be zero");
        }

        @Test
        @DisplayName("Set length (validate)")
        void setLengthValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(e -> e.setLength(1), fPointRef);
        }

        @Test
        @DisplayName("Get inclination (constant azimuthal angle)")
        void getInclinationConstantAzimuthalAngle() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(0.00, factory.getFPoint(0, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [0/8 rad]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1/8 rad]"),
                    () -> assertEquals(Math.PI * 0.50, factory.getFPoint(1, 0, 0).getInclination(),
                            jitter, "The polar angle is incorrect [2/8 rad]"),
                    () -> assertEquals(Math.PI * 0.75, factory.getFPoint(1, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [3/8 rad]"),
                    () -> assertEquals(Math.PI, factory.getFPoint(0, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get inclination (variable azimuthal angle)")
        void getInclinationVariableAzimuthalAngle() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1,1,0]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(0, 1, 1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,1]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(-1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [-1,1,0]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(0, 1, -1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,-1]")
            );
        }

        @Test
        @DisplayName("Get inclination (validate)")
        void getInclinationValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testValue(FPoint::getInclination, fPointRef);
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, positive values)")
        void getAzimuthConstantPolarAnglePositive() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(0.00, factory.getFPoint(1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0/8 rad]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1/8 rad]"),
                    () -> assertEquals(Math.PI * 0.50, factory.getFPoint(0, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [2/8 rad]"),
                    () -> assertEquals(Math.PI * 0.75, factory.getFPoint(-1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [3/8 rad]"),
                    () -> assertEquals(Math.PI, factory.getFPoint(-1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, negative values)")
        void getAzimuthConstantPolarAngleNegative() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(-Math.PI * 0.25, factory.getFPoint(1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-1/8 rad]"),
                    () -> assertEquals(-Math.PI * 0.50, factory.getFPoint(0, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-2/8 rad]"),
                    () -> assertEquals(-Math.PI * 0.75, factory.getFPoint(-1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-3/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (variable polar angle)")
        void getAzimuthVariablePolarAngle() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,1,1]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 0, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,0,0]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, -1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,-1,1]")
            );
        }

        @Test
        @DisplayName("Get azimuth (validate)")
        void getAzimuthValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testValue(FPoint::getAzimuth, fPointRef);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            FPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = factory.getFPoint(1, 0, 1).normalize().setInclination(angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = factory.getFPoint(1, 0, 1).normalize().setInclination(-angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set inclination (angle overflow)")
        void setInclinationOverflow() {
            double angle = 1.5 * Math.PI;
            FPoint fPointRef = factory.getFPoint(1, 0, 1).normalize().setInclination(angle);

            assertEquals(0.5 * Math.PI, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set inclination (full circle)")
        void setInclinationFull() {
            double angle = 2.0 * Math.PI;
            FPoint fPointRef = factory.getFPoint(1, 0, 1).normalize().setInclination(angle);

            assertEquals(0, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set inclination (validate)")
        void setInclinationValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(e -> e.setInclination(Math.PI * 0.5), fPointRef);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            FPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = factory.getFPoint(1, 1, 0).normalize().setAzimuth(angle);

                assertEquals(angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(Math.PI * 0.25, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = factory.getFPoint(1, 1, 0).normalize().setAzimuth(-angle);

                assertEquals(-angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(Math.PI * 0.25, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set azimuth (angle overflow)")
        void setAzimuthOverflow() {
            double angle = 1.5 * Math.PI;
            FPoint fPointRef = factory.getFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(-Math.PI * 0.5, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(Math.PI * 0.25, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set azimuth (full circle)")
        void setAzimuthFull() {
            double angle = 2.0 * Math.PI;
            FPoint fPointRef = factory.getFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(0, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(Math.PI * 0.25, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");

        }

        @Test
        @DisplayName("Set azimuth (validate)")
        void setAzimuthValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(e -> e.setAzimuth(Math.PI * 0.5), fPointRef);
        }

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            double radius = Math.abs(random.nextDouble());

            double inclination = Math.abs(random.nextDouble()) % Math.PI;
            double azimuth = Math.abs(random.nextDouble()) % Math.PI;

            FPoint fPointRef = factory.getFPoint(radius).setSphericalCoordinates(inclination, azimuth);

            assertNotNull(fPointRef, "The instance is null");

            Assertions.assertAll("Validate spherical coordinates",
                    () -> assertEquals(inclination, fPointRef.getInclination(),
                            jitter, "The inclination is incorrect"),
                    () -> assertEquals(azimuth, fPointRef.getAzimuth(),
                            jitter, "The azimuth is incorrect")
            );
        }

        @Test
        @DisplayName("Set spherical coordinates (validate)")
        void setSphericalCoordinatesValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testReference(e -> e.setSphericalCoordinates(Math.PI, Math.PI), fPointRef);
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {

            assertTrue(factory.getFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            FPoint fPointRef = factory.getFPoint().set(1, 2, 3);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidateRef() {
            FPoint fPoint = TestHelper.getRandomFPoint();

            FPointTestHelper.testValue(FPoint::isZero, fPoint);
        }

        @Test
        @DisplayName("Is non-directional")
        void isNonDirectional() {

            assertTrue(factory.getFPoint().isNearZero(),
                    "The reference point should be non-directional");
        }

        @Test
        @DisplayName("Is non-directional (fail)")
        void isNonDirectionalFail() {
            FPoint fPointRef = factory.getFPoint().set(1, 2, 3);

            assertFalse(fPointRef.isNearZero(),
                    "The reference point should be directional");
        }

        @Test
        @DisplayName("Is non-directional (validate)")
        void isNonDirectionalValidate() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            FPointTestHelper.testValue(FPoint::isNearZero, fPointRef);
        }

        @Test
        @DisplayName("Get dot product with primitives")
        void getDotProductWithPrimitives() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            double result = fPointRef.getDotProduct(1, 2, 3);

            double dimX = fPointRef.getX() * 1;
            double dimY = fPointRef.getY() * 2;
            double dimZ = fPointRef.getZ() * 3;

            Assertions.assertEquals(dimX + dimY + dimZ, result,
                    jitter, "The dot product value is erroneous");
        }

        @Test
        @DisplayName("Get dot product with primitives (validate)")
        void getDotProductWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(p -> p.getDotProduct(4, 5, 6), fPointRef);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            FPoint fPointA = TestHelper.getRandomFPoint();
            FPoint fPointB = TestHelper.getRandomFPoint(fPointA);

            double result = fPointA.getDotProduct(fPointB);

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            Assertions.assertEquals(dimX + dimY + dimZ, result,
                    jitter, "The dot product value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testValue(FPoint::getDotProduct, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Set cross product with primitives")
        void setCrossProductWithPrimitives() {
            double refAX = random.nextDouble();
            double refAY = random.nextDouble();
            double refAZ = random.nextDouble();
            FPoint fPointRef = factory.getFPoint(refAX, refAY, refAZ);

            FPoint fPointRes = fPointRef.copy().setCrossProduct(1, 2, 3);

            double dimX = (fPointRef.getY() * 3) - (fPointRef.getZ() * 2);
            double dimY = (fPointRef.getZ() * 1) - (fPointRef.getX() * 3);
            double dimZ = (fPointRef.getX() * 2) - (fPointRef.getY() * 1);

            assertTrue(fPointRes.isSimilar(dimX, dimY, dimZ),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product with primitives (validate)")
        void setCrossProductWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(p -> p.setCrossProduct(4, 5, 6), fPointRef);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            double refAX = random.nextDouble();
            double refAY = random.nextDouble();
            double refAZ = random.nextDouble();
            FPoint fPointA = factory.getFPoint(refAX, refAY, refAZ);

            double refBX = random.nextDouble();
            double refBY = random.nextDouble();
            double refBZ = random.nextDouble();
            FPoint fPointB = factory.getFPoint(refBX, refBY, refBZ);

            FPoint fPointRes = fPointA.copy().setCrossProduct(fPointB);

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            assertTrue(fPointRes.isSimilar(dimX, dimY, dimZ),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference(FPoint::setCrossProduct, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Get angle with primitives")
        void getAngleWithPrimitives() {
            FPoint fPointRef = factory.getFPoint(2, 2, 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fPointRef.getAngle(4, -4, 0),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle with primitives (parallel)")
        void getAngleWithPrimitivesParallel() {
            FPoint fPointRef = factory.getFPoint(2, 2, 2);

            assertEquals(0, fPointRef.getAngle(4, 4, 4),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle with primitives (antiparallel)")
        void getAngleWithPrimitivesAntiparallel() {
            FPoint fPointRef = factory.getFPoint(2, 2, 2);

            assertEquals(0, fPointRef.getAngle(-4, -4, -4),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle with primitives (orthogonal)")
        void getAngleWithPrimitivesOrthogonal() {
            FPoint fPointRef = factory.getFPoint(0, 1, 0);

            assertEquals(Math.PI * 0.5, fPointRef.getAngle(5, 0, 5),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle with primitives (throw IllegalStateException, input)")
        void getAngleWithPrimitivesThrowIllegalStateExceptionInput() {
            FPoint fPointRef = factory.getFPoint();

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.getAngle(1, 2, 3),
                    "The direction of the input vector is not defined");
        }

        @Test
        @DisplayName("Get angle with primitives (throw IllegalArgumentException, argument)")
        void getAngleWithPrimitivesThrowIllegalStateExceptionArgument() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPointRef.getAngle(0, 0, 0),
                    "The direction of the argument vector is not defined");
        }

        @Test
        @DisplayName("Get angle with primitives (throw IllegalStateException, similarity)")
        void getAngleWithPrimitivesThrowIllegalStateExceptionSimilarity() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.getAngle(1, 2, 3),
                    "The direction of the argument vector is not defined");
        }

        @Test
        @DisplayName("Get angle with primitives (validate)")
        void getAngleWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(p -> p.getAngle(4, 5, 6), fPointRef);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointRef = factory.getFPoint(2, 2, 0);
            FPoint fPointArg = factory.getFPoint(4, -4, 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fPointRef.getAngle(fPointArg),
                            jitter, "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fPointArg.getAngle(fPointRef),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            FPoint fPointRef = factory.getFPoint(2, 2, 2);
            FPoint fPointArg = factory.getFPoint(4, 4, 4);

            assertEquals(0, fPointRef.getAngle(fPointArg),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FPoint fPointRef = factory.getFPoint(2, 2, 2);
            FPoint fPointArg = factory.getFPoint(-4, -4, -4);

            assertEquals(0, fPointRef.getAngle(fPointArg),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FPoint fPointRef = factory.getFPoint(0, 1, 0);
            FPoint fPointArg = TestHelper.getRandomFPoint().setY(0);

            assertEquals(Math.PI * 0.5, fPointRef.getAngle(fPointArg),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, input)")
        void getAngleThrowIllegalStateExceptionInput() {
            FPoint fPointRef = factory.getFPoint();
            FPoint fPointArg = TestHelper.getRandomFPoint();

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.getAngle(fPointArg),
                    "The direction of the input vector is not defined");
        }

        @Test
        @DisplayName("Get angle (throw IllegalArgumentException, argument)")
        void getAngleThrowIllegalStateExceptionArgument() {
            FPoint fPointRef = TestHelper.getRandomFPoint();
            FPoint fPointArg = factory.getFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPointRef.getAngle(fPointArg),
                    "The direction of the argument vector is not defined");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, similarity)")
        void getAngleThrowIllegalStateExceptionSimilarity() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.getAngle(fPointArg),
                    "The direction of the argument vector is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testValue(FPoint::getAngle, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Rotate (simple, positive)")
        void rotateSimplePositive() {
            FPoint fPointA = factory.getFPoint(1, 1, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.apply(p -> rotation.rotate(p, fPointB, Math.PI));

            assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointA),
                    "The rotation position is incorrect");
        }

        @Test
        @DisplayName("Rotate (simple, negative)")
        void rotateSimpleNegative() {
            FPoint fPointA = factory.getFPoint(1, 1, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.apply(p -> rotation.rotate(p, fPointB, -Math.PI));

            assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointA),
                    "The rotation position is incorrect");
        }

        @Test
        @DisplayName("Rotate (throw IllegalArgumentException)")
        void rotateThrowIllegalArgumentException() {
            FPoint fPointA = factory.getFPoint(1, 1, 0);
            FPoint fPointB = factory.getFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> rotation.rotate(fPointA, fPointB, Math.PI),
                    "The rotation axis is not defined");
        }

        @Test
        @DisplayName("Rotate (validate)")
        void rotateValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference((a, b) -> rotation.rotate(a, b, Math.PI), fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Set angle with primitives (simple)")
        void setAngleWithPrimitivesSimple() {
            FPoint fPointRef = factory.getFPoint(1, 0, 0);

            fPointRef.setAngle(0, 1, 0, Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate angle",
                    () -> assertEquals(Math.PI * 0.25, fPointRef.getAngle(0, 1, 0),
                            jitter, "The angle is erroneous"),
                    () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPointRef),
                            "The position is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle with primitives (simple, negative position)")
        void setAngleWithPrimitivesSimpleNegativePosition() {
            FPoint fPointRef = factory.getFPoint(-1, 0, 0);

            fPointRef.setAngle(0, 1, 0, Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate angle",
                    () -> assertEquals(Math.PI * 0.25, fPointRef.getAngle(0, 1, 0),
                            jitter, "The angle is erroneous"),
                    () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointRef),
                            "The position is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle with primitives (simple, negative angle)")
        void setAngleWithPrimitivesSimpleNegativeAngle() {
            FPoint fPointRef = factory.getFPoint(1, 0, 0);

            fPointRef.setAngle(0, 1, 0, -Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate rotation",
                    () -> assertEquals(Math.PI * 0.25, fPointRef.getAngle(0, 1, 0),
                            jitter, "The angle is erroneous"),
                    () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointRef),
                            "The position is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle with primitives")
        void setAngleWithPrimitives() {
            FPoint fPointRef = factory.getFPoint(1, 0, 0);

            double magnitude = fPointRef.getLength();
            double angle = random.nextDouble() % (Math.PI);

            fPointRef.setAngle(0, 1, 0, angle);

            Assertions.assertAll("Validate angle",
                    () -> assertEquals(magnitude, fPointRef.getLength(),
                            jitter, "The magnitude is erroneous"),
                    () -> assertEquals(Math.abs(angle), fPointRef.getAngle(0, 1, 0),
                            jitter, "The angle is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle with primitives (throw IllegalArgumentException)")
        void setAngleWithPrimitivesThrowIllegalArgumentException() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPointRef.setAngle(0, 0, 0, Math.PI),
                    "The axis is not defined");
        }

        @Test
        @DisplayName("Set angle with primitives (throw IllegalStateException)")
        void setAngleWithPrimitivesThrowIllegalStateException() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.setAngle(1, 2, 3, Math.PI),
                    "The axes are similar");
        }

        @Test
        @DisplayName("Set angle with primitives (validate)")
        void setAngleWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(p -> p.setAngle(4, 5, 6, Math.PI), fPointRef);
        }

        @Test
        @DisplayName("Set angle (simple)")
        void setAngleSimple() {
            FPoint fPointA = factory.getFPoint(1, 0, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.setAngle(fPointB, Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate angle",
                    () -> assertEquals(Math.PI * 0.25, fPointA.getAngle(fPointB),
                            jitter, "The angle is erroneous"),
                    () -> assertTrue(factory.getFPoint(position, position, 0).isSimilar(fPointA),
                            "The position is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle (simple, negative position)")
        void setAngleSimpleNegativePosition() {
            FPoint fPointA = factory.getFPoint(-1, 0, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.setAngle(fPointB, Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate angle",
                    () -> assertEquals(Math.PI * 0.25, fPointA.getAngle(fPointB),
                            jitter, "The angle is erroneous"),
                    () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointA),
                            "The position is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle (simple, negative angle)")
        void setAngleSimpleNegativeAngle() {
            FPoint fPointA = factory.getFPoint(1, 0, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.setAngle(fPointB, -Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate rotation",
                    () -> assertEquals(Math.PI * 0.25, fPointA.getAngle(fPointB),
                            jitter, "The angle is erroneous"),
                    () -> assertTrue(factory.getFPoint(-position, position, 0).isSimilar(fPointA),
                            "The position is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle")
        void setAngle() {
            FPoint fPointA = factory.getFPoint(1, 0, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            double magnitude = fPointA.getLength();
            double angle = random.nextDouble() % (Math.PI);

            fPointA.setAngle(fPointB, angle);

            Assertions.assertAll("Validate angle",
                    () -> assertEquals(magnitude, fPointA.getLength(),
                            jitter, "The magnitude is erroneous"),
                    () -> assertEquals(Math.abs(angle), fPointA.getAngle(fPointB),
                            jitter, "The angle is erroneous")
            );
        }

        @Test
        @DisplayName("Set angle (throw IllegalArgumentException)")
        void setAngleThrowIllegalArgumentException() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPointRef.setAngle(factory.getFPoint(), Math.PI),
                    "The rotation axis is not defined");
        }

        @Test
        @DisplayName("Set angle (throw IllegalStateException)")
        void setAngleThrowIllegalStateException() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.setAngle(factory.getFPoint(1, 2, 3), Math.PI),
                    "The axes are similar");
        }

        @Test
        @DisplayName("Set angle (validate)")
        void setAngleValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference((a, b) -> a.setAngle(b, Math.PI), fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Get distance with primitives")
        void getDistanceWithPrimitives() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            double dimX = fPointRef.getX() - 1;
            double dimY = fPointRef.getY() - 2;
            double dimZ = fPointRef.getZ() - 3;
            double reference = Math.sqrt((dimX * dimX) + (dimY * dimY) + (dimZ * dimZ));

            assertEquals(reference, fPointRef.getDistance(1, 2, 3),
                    jitter, "The distance between FPoints is incorrect");
        }

        @Test
        @DisplayName("Get distance with primitives (validate)")
        void getDistanceWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(p -> p.getDistance(4, 5, 6), fPointRef);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPoint fPointA = TestHelper.getRandomFPoint();
            FPoint fPointB = TestHelper.getRandomFPoint();

            double dimX = fPointA.getX() - fPointB.getX();
            double dimY = fPointA.getY() - fPointB.getY();
            double dimZ = fPointA.getZ() - fPointB.getZ();
            double reference = Math.sqrt((dimX * dimX) + (dimY * dimY) + (dimZ * dimZ));

            assertEquals(reference, fPointA.getDistance(fPointB),
                    jitter, "The distance between FPoints is incorrect");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testValue(FPoint::getDistance, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Get distance with primitives P2")
        void getDistanceWithPrimitivesP2() {
            FPoint fPointRef = TestHelper.getRandomFPoint();

            double dimX = fPointRef.getX() - 1;
            double dimY = fPointRef.getY() - 2;
            double dimZ = fPointRef.getZ() - 3;
            double reference = (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);

            assertEquals(reference, fPointRef.getDistanceP2(1, 2, 3),
                    jitter, "The distance between FPoints is incorrect");
        }

        @Test
        @DisplayName("Get distance P2 with primitives (validate)")
        void getDistanceWithPrimitivesP2Validate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(p -> p.getDistanceP2(4, 5, 6), fPointRef);
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FPoint fPointA = TestHelper.getRandomFPoint();
            FPoint fPointB = TestHelper.getRandomFPoint();

            double dimX = fPointA.getX() - fPointB.getX();
            double dimY = fPointA.getY() - fPointB.getY();
            double dimZ = fPointA.getZ() - fPointB.getZ();
            double reference = (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);

            assertEquals(reference, fPointA.getDistanceP2(fPointB),
                    jitter, "The distance between FPoints is incorrect");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testValue(FPoint::getDistanceP2, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Set distance with primitives")
        void setDistanceWithPrimitives() {
            double distance = Math.abs(random.nextDouble());
            FPoint fPointRef = TestHelper.getRandomFPoint();

            fPointRef.setDistance(1, 2, 3, distance);

            assertEquals(distance, fPointRef.getDistance(1, 2, 3),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance with primitives (negative)")
        void setDistanceWithPrimitivesNegative() {
            double distance = Math.abs(random.nextDouble());
            FPoint fPointRef = TestHelper.getRandomFPoint();

            fPointRef.setDistance(1, 2, 3, -distance);

            assertEquals(distance, fPointRef.getDistance(1, 2, 3),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance with primitives position")
        void setDistanceWithPrimitivesPosition() {
            FPoint fPointRef = factory.getFPoint(1, 1, 1);

            fPointRef.setDistance(-1, -1, -1, 6 * Math.sqrt(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(5, fPointRef.getX(), jitter, "The X value is incorrect"),
                    () -> assertEquals(5, fPointRef.getY(), jitter, "The Y value is incorrect"),
                    () -> assertEquals(5, fPointRef.getZ(), jitter, "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set distance with primitives position (negative)")
        void setDistanceWithPrimitivesPositionNegative() {
            FPoint fPointRef = factory.getFPoint(1, 1, 1);

            fPointRef.setDistance(-1, -1, -1, -4 * Math.sqrt(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-5, fPointRef.getX(), jitter, "The X value is incorrect"),
                    () -> assertEquals(-5, fPointRef.getY(), jitter, "The Y value is incorrect"),
                    () -> assertEquals(-5, fPointRef.getZ(), jitter, "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set distance with primitives (throw IllegalStateException)")
        void setDistanceWithPrimitivesThrowIllegalStateException() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fPointRef.setDistance(1, 2, 3, 1),
                    "FPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance with primitives (validate)")
        void setDistanceWithPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(p-> p.setDistance(4, 5, 6, 1), fPointRef);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            double distance = Math.abs(random.nextDouble());
            FPoint fPointA = TestHelper.getRandomFPoint();
            FPoint fPointB = TestHelper.getRandomFPoint(fPointA);

            fPointA.setDistance(fPointB, distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (negative)")
        void setDistanceNegative() {
            double distance = Math.abs(random.nextDouble());
            FPoint fPointA = TestHelper.getRandomFPoint();
            FPoint fPointB = TestHelper.getRandomFPoint(fPointA);

            fPointA.setDistance(fPointB, -distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance position")
        void setDistancePosition() {
            FPoint fPointA = factory.getFPoint(1, 1, 1);
            FPoint fPointB = factory.getFPoint(-1, -1, -1);

            fPointA.setDistance(fPointB, 6 * Math.sqrt(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(5, fPointA.getX(), jitter, "The X value is incorrect"),
                    () -> assertEquals(5, fPointA.getY(), jitter, "The Y value is incorrect"),
                    () -> assertEquals(5, fPointA.getZ(), jitter, "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set distance position (negative)")
        void setDistancePositionNegative() {
            FPoint fPointA = factory.getFPoint(1, 1, 1);
            FPoint fPointB = factory.getFPoint(-1, -1, -1);

            fPointA.setDistance(fPointB, -4 * Math.sqrt(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-5, fPointA.getX(), jitter, "The X value is incorrect"),
                    () -> assertEquals(-5, fPointA.getY(), jitter, "The Y value is incorrect"),
                    () -> assertEquals(-5, fPointA.getZ(), jitter, "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPoint fPointA = TestHelper.getRandomFPoint();
            FPoint fPointB = fPointA.copy();

            Assertions.assertThrows(IllegalStateException.class, () -> fPointA.setDistance(fPointB, 1),
                    "FPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference((a, b) -> a.setDistance(b, 1), fPointRef, fPointArg);
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FPointCoreTest {

        private final double refX = random.nextDouble();
        private final double refY = random.nextDouble();
        private final double refZ = random.nextDouble();

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPointArg = factory.getFPoint().applyStateFrom(fPointRef.toJSON());

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPointArg.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointArg.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointArg.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("JSON parser (validate)")
        void parseJSONValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(Mutable::toJSON, fPointRef);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPointArg = factory.getFPoint(refX, refY, refZ);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPointRef.isExact(fPointArg), "FPoints should be equal"),
                    () -> assertTrue(fPointArg.isExact(fPointRef), "FPoints should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPointArg = factory.getFPoint(refX, refY, refZ).add(0.5 * jitter);

            Assertions.assertAll("Check combinations",
                    () -> assertFalse(fPointRef.isExact(fPointArg), "FPoints should not be equal"),
                    () -> assertFalse(fPointArg.isExact(fPointRef), "FPoints should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testValue(Mutable::isExact, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);

            assertTrue(fPointRef.isExact(refX, refY, refZ), "FPoint values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);

            assertFalse(fPointRef.isExact(0, 0, 0), "FPoint values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(e -> e.isExact(0, 0, 0), fPointRef);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            double ref = jitter * 0.5;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef)),
                            "FPoints should be similar (same position)"),
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).addX(ref)),
                            "FPoints should be similar (positive X)"),
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).subX(ref)),
                            "FPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).addY(ref)),
                            "FPoints should be similar (positive Y)"),
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).subY(ref)),
                            "FPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).addZ(ref)),
                            "FPoints should be similar (positive Z)"),
                    () -> assertTrue(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).subZ(ref)),
                            "FPoints should be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            double ref = jitter * 2;

            Assertions.assertAll("Check combinations (false)",
                    () -> assertFalse(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).addX(ref)),
                            "FPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).subX(ref)),
                            "FPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).addY(ref)),
                            "FPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).subY(ref)),
                            "FPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).addZ(ref)),
                            "FPoints should not be similar (positive Z)"),
                    () -> assertFalse(fPointRef.isSimilar(factory.getFPoint().add(fPointRef).subZ(ref)),
                            "FPoints should not be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testValue(Mutable::isSimilar, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            FPoint fPoint = factory.getFPoint(refX, refY, refZ);

            assertTrue(fPoint.isSimilar(
                    refX + (0.5 * jitter),
                    refY + (0.5 * jitter),
                    refZ + (0.5 * jitter)),
                    "FPoint values should be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FPoint fPoint = factory.getFPoint(refX, refY, refZ);

            assertFalse(fPoint.isSimilar(refX + (1.5 * jitter), refY + (1.5 * jitter), refZ + (1.5 * jitter)),
                    "FPoint values should not be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(e -> e.isSimilar(0, 0, 0), fPointRef);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FPoint fPointRefA = factory.getFPoint(refX, refY, refZ);
            FPoint fPointRefB = factory.getFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.hashCode(), fPointRefB.hashCode(),
                    "Two identical FPoints should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);

            assertNotEquals(fPointRef.hashCode(), factory.getFPoint().hashCode(),
                    "Two different FPoints should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(FPoint::hashCode, fPointRef);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPoint = fPointRef.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fPointRef, fPoint,
                            "FPoints represent different objects"),
                    () -> assertTrue(fPointRef.isExact(fPoint),
                            "FPoints should have the same values"),
                    () -> assertFalse(fPointRef.isExact(fPoint.add(fPointRef)),
                            "FPoints should have different values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(FPoint::copy, fPointRef);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPoint = fPointRef.copyZero();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fPointRef, fPoint,
                            "FPoints represent different objects"),
                    () -> assertEquals(0, fPoint.getX(),
                            "FPoints X values are incorrect"),
                    () -> assertEquals(0, fPoint.getY(),
                            "FPoints Y Im values are incorrect"),
                    () -> assertEquals(0, fPoint.getZ(),
                            "FPoints Y Im values are incorrect")
            );
        }

        @Test
        @DisplayName("Copy zero (validate)")
        void copyZeroValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testValue(FPoint::copyZero, fPointRef);
        }
    }

    @Nested
    @Tag("Mutable")
    @DisplayName("Base mutable")
    class FPointMutableTest {

        private final double refX = random.nextDouble();
        private final double refY = random.nextDouble();
        private final double refZ = random.nextDouble();
        private final double argX = random.nextDouble();
        private final double argY = random.nextDouble();
        private final double argZ = random.nextDouble();
        private FPoint fPoint;

        @BeforeEach
        void beforeEach() {

            fPoint = factory.getFPoint(refX, refY, refZ);
        }

        @Test
        @DisplayName("Add FPoint")
        void addFPoint() {
            FPoint fPointArg = factory.getFPoint(argX, argY, argZ);

            fPoint.add(fPointArg);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add FPoint (validate)")
        void addFPointValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference(FPoint::add, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fPoint.add(argX, argY, argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.add(0, 0, 0), fPointRef);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = argX * argY * argZ;

            fPoint.add(op);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.add(1), fPointRef);
        }

        @Test
        @DisplayName("Add X")
        void addX() {

            fPoint.addX(argX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add X (validate)")
        void addXValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.addX(1), fPointRef);
        }

        @Test
        @DisplayName("Add Y")
        void addY() {

            fPoint.addY(argY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.addY(1), fPointRef);
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {

            fPoint.addZ(argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.addZ(1), fPointRef);
        }

        @Test
        @DisplayName("Sub FPoint")
        void subFPoint() {
            FPoint fPointOp = factory.getFPoint(argX, argY, argZ);

            fPoint.sub(fPointOp);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FPoint (validate)")
        void subFPointValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference(FPoint::sub, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fPoint.sub(argX, argY, argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.sub(0, 0, 0), fPointRef);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = argX * argY * argZ;

            fPoint.sub(op);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.sub(1), fPointRef);
        }

        @Test
        @DisplayName("Sub X")
        void subX() {

            fPoint.subX(argX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.subX(1), fPointRef);
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {

            fPoint.subY(argY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.subY(1), fPointRef);
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {

            fPoint.subZ(argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Z (validate)")
        void subZValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.subZ(1), fPointRef);
        }

        @Test
        @DisplayName("Mul FPoint")
        void mulFPoint() {
            FPoint fPointArg = factory.getFPoint(argX, argY, argZ);

            fPoint.mul(fPointArg);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FPoint (validate)")
        void mulFPointValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference(FPoint::mul, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fPoint.mul(argX, argY, argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.mul(0, 0, 0), fPointRef);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = argX * argY * argZ;

            fPoint.mul(op);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.mul(1), fPointRef);
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {

            fPoint.mulX(argX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.mulX(1), fPointRef);
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {

            fPoint.mulY(argY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.mulY(1), fPointRef);
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {

            fPoint.mulZ(argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.mulZ(1), fPointRef);
        }

        @Test
        @DisplayName("Div FPoint")
        void divFPoint() {
            FPoint fPointArg = factory.getFPoint(argX, argY, argZ);

            fPoint.div(fPointArg);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div FPoint (throw ArithmeticException)")
        void divFPointThrowArithmeticException() {

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fPoint.div(factory.getFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fPoint.div(factory.getFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fPoint.div(factory.getFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div FPoint (validate)")
        void divFPointValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);
            FPoint fPointArg = factory.getFPoint(4, 5, 6);

            FPointTestHelper.testReference(FPoint::mul, fPointRef, fPointArg);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fPoint.div(argX, argY, argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fPoint.div(0, 1, 1), "The X value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fPoint.div(1, 0, 1), "The Y value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fPoint.div(0, 1, 1), "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.div(1, 1, 1), fPointRef);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double arg = argX * argY * argZ;

            fPoint.div(arg);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / arg, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / arg, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / arg, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class, () -> fPoint.div(0), "The factor is zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.div(1), fPointRef);
        }

        @Test
        @DisplayName("Div X")
        void divX() {

            fPoint.divX(argX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / argX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div X (throw ArithmeticException)")
        void divXThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class, () -> fPoint.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div X (validate)")
        void divXValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.divX(1), fPointRef);
        }

        @Test
        @DisplayName("Div Y")
        void divY() {

            fPoint.divY(argY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / argY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div Y (throw ArithmeticException)")
        void divYThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class, () -> fPoint.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Y (validate)")
        void divYValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.divY(1), fPointRef);
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {

            fPoint.divZ(argZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / argZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div Z (ArithmeticException)")
        void divZThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class, () -> fPoint.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Div Z (validate)")
        void divZValidate() {
            FPoint fPointRef = factory.getFPoint(1, 2, 3);

            FPointTestHelper.testReference(e -> e.divZ(1), fPointRef);
        }

        @Test
        @DisplayName("Get FPoint list")
        void getFPoints() {
            List<FPoint> list = fPoint.disassemble();

            Assertions.assertAll("Validate FPoint list",
                    () -> Assertions.assertEquals(1, list.size(), "The size of the list is incorrect"),
                    () -> assertEquals(refX, list.get(0).getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, list.get(0).getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, list.get(0).getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FPoint fPointRef = factory.getFPoint();

            fPoint.applyStateTo(fPointRef);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The reference X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The reference Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The reference Z value is incorrect"),
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FPoint fPointArg = factory.getFPoint();

            FPoint fPointRef = fPointArg.applyStateTo(fPoint);

            Assertions.assertAll("Validate references",
                    () -> assertNotSame(fPoint, fPointArg, "FPoint references should be different"),
                    () -> assertSame(fPointArg, fPointRef, "The FPoint reference should not change")
            );
        }
    }

    @Nested
    @Tag("Extension")
    @DisplayName("Extension")
    class FPointExtensionTest {

        @Test
        @DisplayName("Apply")
        void apply() {
            FPoint fPoint = factory.getFPoint(0, 0, 0);

            var fPointRes = fPoint.apply(p -> p.setX(1).setY(2).setZ(3));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(2, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(3, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertSame(fPoint, fPointRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Apply with fixed state")
        void applyWithFixedState() {
            FPoint fPoint = factory.getFPoint(0, 0, 0);

            List<Double> intermediate = new ArrayList<>();

            var fPointRes = fPoint.applyWithFixedState(p -> intermediate.add(p.set(1, 2, 3).getLengthP2()));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                    () -> assertEquals(14, intermediate.get(0), jitter, "The value is incorrect"),
                    () -> assertSame(fPoint, fPointRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Apply with fixed length")
        void applyWithFixedLength() {
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            var fPointRes = fPoint.applyWithFixedLength(p -> p.set(-10, 0, 0));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-1, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertSame(fPoint, fPointRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with double")
        void terminateWithDouble() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            var res = fPoint.toDouble(p -> {
                p.reflectThroughCenter();
                return p.getX() + p.getY() + p.getZ();
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-1, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-2, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-3, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertEquals(-6, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean")
        void terminateWithBoolean() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            var res = fPoint.toBoolean(p -> {
                p.reflectThroughCenter();
                return p.getX() + p.getY() + p.getZ() == -6;
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-1, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-2, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-3, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertTrue(res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with double (fixed state)")
        void terminateWithDoubleFixedState() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            var res = fPoint.toDoubleWithFixedState(p -> {
                p.reflectThroughCenter();
                return p.getX() + p.getY() + p.getZ();
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(2, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(3, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertEquals(-6, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean (fixed state)")
        void terminateWithBooleanFixedState() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            var res = fPoint.toBooleanWithFixedState(p -> {
                p.reflectThroughCenter();
                return p.getX() + p.getY() + p.getZ() == -6;
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(2, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(3, fPoint.getZ(), "The Z value is incorrect"),
                    () -> assertTrue(res, "The value is incorrect")
            );
        }
    }
}

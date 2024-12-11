package eu.scattering.core.test.core.mutable.geometry.simple;

import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.test.core.mutable.geometry.simple.support.FPointTestHelper;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPoint")
public class FPointTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FPointBase {

        private double refX = random.getDouble();
        private double refY = random.getDouble();
        private double refZ = random.getDouble();

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

            Assertions.assertAll("Updated values are incorrect",
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

            Assertions.assertAll("Updated values are incorrect",
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

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint")
        void constructWithFPoint() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPoint = factory.getFPoint(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created FPoints should be different");
            assertNotNull(fPoint, "The instance is null");

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(fPointRef.getX(), fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            FPoint fPoint = factory.getFPoint();

            fPoint.set(refX, refY, refZ);

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with an FPoint")
        void setWithFPoint() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPoint = factory.getFPoint();

            fPoint.set(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created FPoints should be different");

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
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

    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FPointAdvanced {

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FPoint fPoint = random.getFPoint();

            assertEquals(1, fPoint.normalize().getLength(),
                    jitter, "The magnitude of the normalized vector should be equal to one");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {
            FPoint fPoint = factory.getFPoint();

            assertThrows(IllegalStateException.class, fPoint::normalize,
                    "The FPoints must not be on the same position");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(FPoint::normalize, fPoint);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = random.getDouble();
            double refY = random.getDouble();
            double refZ = random.getDouble();

            FPoint fPoint = factory.getFPoint(refX, refY, refZ).reflect();

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(FPoint::reflect, fPoint);
        }

        @Test
        @DisplayName("Reflect by FPoint")
        void reflectByFPoint() {
            double refAX = random.getDouble();
            double refAY = random.getDouble();
            double refAZ = random.getDouble();
            FPoint fPointA = factory.getFPoint(refAX, refAY, refAZ);

            double refBX = random.getDouble();
            double refBY = random.getDouble();
            double refBZ = random.getDouble();
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
        @DisplayName("Reflect (validate)")
        void reflectByFPointValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testReference(FPoint::reflect, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get length (axes: 1)")
        void getLengthAxes1() {
            double ref = random.getDouble();
            double expected = Math.abs(ref);

            Assertions.assertAll("Validate radius",
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
            double ref = random.getDouble();
            double expected = Math.abs(ref * Math.sqrt(2));

            Assertions.assertAll("Validate radius",
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
            double ref = random.getDouble();
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::getLength, fPoint);
        }

        @Test
        @DisplayName("Get length P2")
        void getLengthP2() {
            double x = random.getDouble();
            double y = random.getDouble();
            double z = random.getDouble();

            FPoint fPoint = factory.getFPoint(x, y, z);
            double lengthP2 = (x * x) + (y * y) + (z * z);

            assertEquals(lengthP2, fPoint.getLengthP2(),
                    jitter, "The P2 length is erroneous");
        }

        @Test
        @DisplayName("Get length P2 (validate)")
        void getLengthP2Validate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::getLengthP2, fPoint);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPoint;

            double refX = random.getDouble();
            double refY = random.getDouble();
            double refZ = random.getDouble();

            fPoint = factory.getFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(random.getDouble(magnitude));
            fPoint.setLength(magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (negative)")
        void setLengthNegative() {
            FPoint fPoint;

            double refX = random.getDouble();
            double refY = random.getDouble();
            double refZ = random.getDouble();

            fPoint = factory.getFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(random.getDouble(magnitude));
            fPoint.setLength(-magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.setLength(1), fPoint);
        }

        @Test
        @DisplayName("Set random angle (validate vector magnitude)")
        void setRandomAngleValidateMagnitude() {
            double radius = Math.abs(random.getDouble());

            FPoint fPoint = factory.getFPoint(radius).setRandomAngle();

            assertEquals(radius, fPoint.getLength(),
                    jitter, "The radius is invalid");
        }

        @Test
        @DisplayName("Set random angle (validate correctness)")
        void setRandomAngleValidateCorrectness() {
            double radius = Math.abs(random.getDouble());

            FPoint fPointA = factory.getFPoint(radius).setRandomAngle();
            FPoint fPointB = factory.getFPoint(radius).setRandomAngle(fPointA);

            assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
        }

        @Test
        @DisplayName("Set random angle (validate timeout)")
        void setRandomAngleValidateTimeout() {
            double radius = Math.abs(random.getDouble());
            FPoint fPoint = factory.getFPoint(radius);

            Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fPoint.setRandomAngle(fPoint));
        }

        @Test
        @DisplayName("Set random angle (validate)")
        void setRandomAngleValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(FPoint::setRandomAngle, fPoint);
        }

        @Test
        @DisplayName("Get inclination (constant azimuthal angle)")
        void getInclinationConstantAzimuthalAngle() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(Math.PI * 0.00, factory.getFPoint(0, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [0/8 rad]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1/8 rad]"),
                    () -> assertEquals(Math.PI * 0.50, factory.getFPoint(1, 0, 0).getInclination(),
                            jitter, "The polar angle is incorrect [2/8 rad]"),
                    () -> assertEquals(Math.PI * 0.75, factory.getFPoint(1, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [3/8 rad]"),
                    () -> assertEquals(Math.PI * 1.00, factory.getFPoint(0, -1, 0).getInclination(),
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::getInclination, fPoint);
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, positive values)")
        void getAzimuthConstantPolarAnglePositive() {

            Assertions.assertAll("Validate angle values",
                    () -> assertEquals(Math.PI * 0.00, factory.getFPoint(1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0/8 rad]"),
                    () -> assertEquals(Math.PI * 0.25, factory.getFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1/8 rad]"),
                    () -> assertEquals(Math.PI * 0.50, factory.getFPoint(0, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [2/8 rad]"),
                    () -> assertEquals(Math.PI * 0.75, factory.getFPoint(-1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [3/8 rad]"),
                    () -> assertEquals(Math.PI * 1.00, factory.getFPoint(-1, 1, 0).getAzimuth(),
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::getAzimuth, fPoint);
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.setInclination(Math.PI * 0.5), fPoint);
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.setAzimuth(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            double radius = Math.abs(random.getDouble());

            double inclination = Math.abs(random.getDouble()) % Math.PI;
            double azimuth = Math.abs(random.getDouble()) % Math.PI;

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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.setSphericalCoordinates(Math.PI, Math.PI), fPoint);
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {

            assertTrue(factory.getFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            double refX = random.getDouble();
            double refY = random.getDouble();
            double refZ = random.getDouble();

            FPoint fPointRef = factory.getFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::isZero, fPoint);
        }

        @Test
        @DisplayName("Is non-directional")
        void isNonDirectional() {

            assertTrue(factory.getFPoint().isNonDirectional(),
                    "The reference point should be non-directional");
        }

        @Test
        @DisplayName("Is non-directional (fail)")
        void isNonDirectionalFail() {
            double refX = random.getDouble();
            double refY = random.getDouble();
            double refZ = random.getDouble();

            FPoint fPointRef = factory.getFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isNonDirectional(),
                    "The reference point should be directional");
        }

        @Test
        @DisplayName("Is non-directional (validate)")
        void isNonDirectionalValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::isNonDirectional, fPoint);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            double result = fPointA.getDotProduct(fPointB);

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            Assertions.assertEquals(dimX + dimY + dimZ, result, jitter, "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testValue(FPoint::getDotProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            double refAX = random.getDouble();
            double refAY = random.getDouble();
            double refAZ = random.getDouble();
            FPoint fPointA = factory.getFPoint(refAX, refAY, refAZ);

            double refBX = random.getDouble();
            double refBY = random.getDouble();
            double refBZ = random.getDouble();
            FPoint fPointB = factory.getFPoint(refBX, refBY, refBZ);

            FPoint fPointRes = fPointA.copy().setCrossProduct(fPointB);

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            FPoint fPointRef = factory.getFPoint(dimX, dimY, dimZ);

            assertTrue(fPointRes.isSimilar(fPointRef),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testReference(FPoint::setCrossProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Rotate (simple, positive)")
        void rotateSimplePositive() {
            FPoint fPointA = factory.getFPoint(1, 1, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.rotate(fPointB, Math.PI);

            assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointA),
                    "The position is incorrect");
        }

        @Test
        @DisplayName("Rotate (simple, negative)")
        void rotateSimpleNegative() {
            FPoint fPointA = factory.getFPoint(1, 1, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.rotate(fPointB, -Math.PI);

            assertTrue(factory.getFPoint(-1, 1, 0).isSimilar(fPointA),
                    "The position is incorrect");
        }

        @Test
        @DisplayName("Rotate (throw IllegalArgumentException)")
        void rotateThrowIllegalArgumentException() {
            FPoint fPointA = factory.getFPoint(1, 1, 0);
            FPoint fPointB = factory.getFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPointA.rotate(fPointB, Math.PI),
                    "The rotation axis is not defined");
        }

        @Test
        @DisplayName("Rotate (validate)")
        void rotateValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testReference((a, b) -> a.rotate(b, Math.PI), fPointA, fPointB);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointA = factory.getFPoint(2, 2, 0);
            FPoint fPointB = factory.getFPoint(4, -4, 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                            jitter, "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fPointB.getAngle(fPointA),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            FPoint fPointA = factory.getFPoint(2, 2, 2);
            FPoint fPointB = factory.getFPoint(4, 4, 4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FPoint fPointA = factory.getFPoint(2, 2, 2);
            FPoint fPointB = factory.getFPoint(-4, -4, -4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FPoint fPointA = factory.getFPoint(0, 1, 0);
            FPoint fPointB = random.getFPoint().setY(0);

            assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, input)")
        void getAngleThrowIllegalStateExceptionInput() {
            FPoint fPointA = factory.getFPoint();
            FPoint fPointB = random.getFPoint();

            Assertions.assertThrows(IllegalStateException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the input FPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, argument)")
        void getAngleThrowIllegalStateExceptionArgument() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = factory.getFPoint();

            Assertions.assertThrows(IllegalStateException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the argument FPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testValue(FPoint::getAngle, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set angle (simple)")
        void setAngleSimple() {
            FPoint fPointA = factory.getFPoint(1, 0, 0);
            FPoint fPointB = factory.getFPoint(0, 1, 0);

            fPointA.setAngle(fPointB, Math.PI * 0.25);

            double position = 1 / Math.sqrt(2);

            Assertions.assertAll("Validate rotation",
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

            Assertions.assertAll("Validate rotation",
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
            double angle = random.getDouble() % (Math.PI);
            fPointA.setAngle(fPointB, angle);

            Assertions.assertAll("Validate rotation",
                    () -> assertEquals(magnitude, fPointA.getLength(),
                            jitter, "The magnitude is erroneous"),
                    () -> assertEquals(Math.abs(angle), fPointA.getAngle(fPointB),
                            jitter, "The angle is erroneous")
                    );
        }

        @Test
        @DisplayName("Set angle (throw IllegalArgumentException)")
        void setAngleThrowIllegalArgumentException() {
            FPoint fPoint = random.getFPoint();

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPoint.setAngle(factory.getFPoint(), Math.PI),
                    "The rotation axis is not defined");
        }

        @Test
        @DisplayName("Set angle (validate)")
        void setAngleValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testReference((a, b) -> a.setAngle(b, Math.PI), fPointA, fPointB);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint();

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
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testValue(FPoint::getDistance, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint();

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
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testValue(FPoint::getDistanceP2, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            double distance = Math.abs(random.getDouble());
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            fPointA.setDistance(fPointB, distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            double distance = Math.abs(random.getDouble());
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            fPointA.setDistance(fPointB, -distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            double distance = Math.abs(random.getDouble());
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPoint fPointRefA = fPointA.copy().setDistance(fPointB, distance);
            FPoint fPointRefB = fPointA.copy().setDistance(fPointB, -distance);

            assertEquals(2 * distance, fPointRefA.getDistance(fPointRefB),
                    jitter, "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = fPointA.copy();

            Assertions.assertThrows(IllegalStateException.class, () -> fPointA.setDistance(fPointB, 1),
                    "FPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint(fPointA);

            FPointTestHelper.testReference((a, b) -> a.setDistance(b, 1), fPointA, fPointB);
        }

    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class CoreFeatures {

        private double refX = random.getDouble();
        private double refY = random.getDouble();
        private double refZ = random.getDouble();

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPointOp = factory.getFPoint().importFromJSON(fPointRef.exportToJSON());

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPointOp = factory.getFPoint(refX, refY, refZ);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPointRef.isExact(fPointOp), "FPoints should be equal"),
                    () -> assertTrue(fPointOp.isExact(fPointRef), "FPoints should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FPoint fPointRef = factory.getFPoint(refX, refY, refZ);
            FPoint fPointOp = factory.getFPoint(refX, refY, refZ).add(0.5 * jitter);

            Assertions.assertAll("Check combinations",
                    () -> assertFalse(fPointRef.isExact(fPointOp), "FPoints should not be equal"),
                    () -> assertFalse(fPointOp.isExact(fPointRef), "FPoints should not be equal")
            );
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(e -> e.isExact(0, 0, 0), fPoint);
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(e -> e.isSimilar(0, 0, 0), fPoint);
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
            FPoint fPointRefA = factory.getFPoint(refX, refY, refZ);

            assertNotEquals(fPointRefA.hashCode(), factory.getFPoint().hashCode(),
                    "Two different FPoints should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::hashCode, fPoint);
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testValue(FPoint::copy, fPoint);
        }

    }

    @Nested
    @Tag("Mutable")
    @DisplayName("Base mutable")
    class BaseMutable {

        private double refX = random.getDouble();
        private double refY = random.getDouble();
        private double refZ = random.getDouble();
        private double opX = random.getDouble();
        private double opY = random.getDouble();
        private double opZ = random.getDouble();
        private FPoint fPoint;

        @BeforeEach
        void beforeEach() {

            fPoint = factory.getFPoint(refX, refY, refZ);
        }

        @Test
        @DisplayName("Add FPoint")
        void addFPoint() {
            FPoint fPointOp = factory.getFPoint(opX, opY, opZ);

            fPoint.add(fPointOp);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add FPoint (validate)")
        void addFPointValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint();

            FPointTestHelper.testReference(FPoint::add, fPointA, fPointB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fPoint.add(opX, opY, opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.add(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opX * opY * opZ;

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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.add(1), fPoint);
        }

        @Test
        @DisplayName("Add X")
        void addX() {

            fPoint.addX(opX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add X (validate)")
        void addXValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.addX(1), fPoint);
        }

        @Test
        @DisplayName("Add Y")
        void addY() {

            fPoint.addY(opY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.addY(1), fPoint);
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {

            fPoint.addZ(opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.addZ(1), fPoint);
        }

        @Test
        @DisplayName("Sub FPoint")
        void subFPoint() {
            FPoint fPointOp = factory.getFPoint(opX, opY, opZ);

            fPoint.sub(fPointOp);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FPoint (validate)")
        void subFPointValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint();

            FPointTestHelper.testReference(FPoint::sub, fPointA, fPointB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fPoint.sub(opX, opY, opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.sub(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opX * opY * opZ;

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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.sub(1), fPoint);
        }

        @Test
        @DisplayName("Sub X")
        void subX() {

            fPoint.subX(opX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.subX(1), fPoint);
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {

            fPoint.subY(opY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.subY(1), fPoint);
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {

            fPoint.subZ(opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Z (validate)")
        void subZValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.subZ(1), fPoint);
        }

        @Test
        @DisplayName("Mul FPoint")
        void mulFPoint() {
            FPoint fPointOp = factory.getFPoint(opX, opY, opZ);

            fPoint.mul(fPointOp);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FPoint (validate)")
        void mulFPointValidate() {
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint();

            FPointTestHelper.testReference(FPoint::mul, fPointA, fPointB);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fPoint.mul(opX, opY, opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.mul(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opX * opY * opZ;

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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.mul(1), fPoint);
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {

            fPoint.mulX(opX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.mulX(1), fPoint);
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {

            fPoint.mulY(opY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.mulY(1), fPoint);
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {

            fPoint.mulZ(opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.mulZ(1), fPoint);
        }

        @Test
        @DisplayName("Div FPoint")
        void divFPoint() {
            FPoint fPointOp = factory.getFPoint(opX, opY, opZ);

            fPoint.div(fPointOp);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
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
            FPoint fPointA = random.getFPoint();
            FPoint fPointB = random.getFPoint();

            FPointTestHelper.testReference(FPoint::mul, fPointA, fPointB);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fPoint.div(opX, opY, opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.div(1, 1, 1), fPoint);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opX * opY * opZ;

            fPoint.div(op);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / op, fPoint.getZ(), "The Z value is incorrect")
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.div(1), fPoint);
        }

        @Test
        @DisplayName("Div X")
        void divX() {

            fPoint.divX(opX);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.divX(1), fPoint);
        }

        @Test
        @DisplayName("Div Y")
        void divY() {

            fPoint.divY(opY);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.divY(1), fPoint);
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {

            fPoint.divZ(opZ);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
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
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.divZ(1), fPoint);
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

            fPoint.imprint(fPointRef);

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
            FPoint fPointOp = factory.getFPoint();

            FPoint fPointRef = fPointOp.imprint(fPoint);

            Assertions.assertAll("Validate references",
                    () -> assertNotSame(fPoint, fPointOp, "FPoint references should be different"),
                    () -> assertSame(fPointOp, fPointRef, "The FPoint reference should not change")
            );
        }

        @Test
        @DisplayName("Custom function - chain")
        void trans() {

            fPoint.trans(e -> e.addX(opX).addY(opY).addZ(opZ));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Custom function - chain (validate)")
        void transValidate() {
            FPoint fPoint = random.getFPoint();

            FPointTestHelper.testReference(e -> e.addX(opX).addY(opY).addZ(opZ), fPoint);
        }

        @Test
        @DisplayName("Custom function - value")
        void transVal() {

            assertEquals(refX + refY + refZ, fPoint.transDouble(e -> e.getX() + e.getY() + e.getZ()),
                    "The resulting value is erroneous");
        }

        @Test
        @DisplayName("Custom function - value")
        void transLog() {

            assertTrue(fPoint.transBoolean(e -> e.getX() != 0), "The resulting value is erroneous");
        }

    }
}

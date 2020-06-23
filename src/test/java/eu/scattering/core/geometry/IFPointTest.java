package eu.scattering.core.geometry;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static eu.scattering.core.Configuration.*;

@Timeout(5)
@DisplayName("IFPoint")
public class IFPointTest {

    static double angle08 = Math.PI * 0.00;
    static double angle18 = Math.PI * 0.25;
    static double angle28 = Math.PI * 0.50;
    static double angle38 = Math.PI * 0.75;
    static double angle48 = Math.PI * 1.00;

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFPointBase {

        private double refX, refY, refZ;

        @BeforeEach
        void beforeEach() {
            refX = HelperRandom.getTestValue();
            refY = HelperRandom.getTestValue();
            refZ = HelperRandom.getTestValue();
        }

        @Test
        @DisplayName("Constructor")
        void construct() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            assertNotNull(fPoint, "The instance is null");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with X")
        void constructWithX() {
            IFPoint fPoint = FactoryGeometry.getIFPoint(refX);

            assertNotNull(fPoint, "The instance is null");

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with XY")
        void constructWithXY() {
            IFPoint fPoint = FactoryGeometry.getIFPoint(refX, refY);

            assertNotNull(fPoint, "The instance is null");

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with XYZ")
        void constructWithXYZ() {
            IFPoint fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertNotNull(fPoint, "The instance is null");

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint")
        void constructWithIFPoint() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPoint = FactoryGeometry.getIFPoint(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created IFPoints should be different");
            assertNotNull(fPointRef, "The instance is null");

            assertAll("Updated values are incorrect",
                    () -> assertEquals(fPointRef.getX(), fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            fPoint.set(refX, refY, refZ);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with an IFPoint")
        void setWithIFPoint() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            fPoint.set(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created IFPoints should be different");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with an IFPoint (throw NullPointerException)")
        void setWithIFPointThrowNullPointerException() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            assertThrows(NullPointerException.class, () -> fPoint.set(null), "The reference cannot be null" );
        }

        @Test
        @DisplayName("Set X")
        void setX() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            fPoint.setX(refX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set Y")
        void setY() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            fPoint.setY(refY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set Z")
        void setZ() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            fPoint.setZ(refZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class IFPointAdvanced {

        @Test
        @DisplayName("Normalize")
        void normalize() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            assertEquals(1, fPoint.normalize().getRadius(),
                    jitter, "The magnitude of the normalized vector should be equal to one");
        }

        @Test
        @DisplayName("Normalize (validate references)")
        void normalizeValidateReferences() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFPoint fPointRef = fPoint.normalize();

            assertSame(fPoint, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ).reflect();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(-refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate references)")
        void reflectValidateReferences() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFPoint fPointRef = fPoint.reflect();

            assertSame(fPoint, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect by IFPoint")
        void reflectByIFPoint() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            IFPoint fPointA = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);

            fPointA.reflect(fPointB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refBX - (refAX - refBX), fPointA.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(refBY - (refAY - refBY), fPointA.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(refBZ - (refAZ - refBZ), fPointA.getZ(),
                            "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect by IFPoint (validate references)")
        void reflectByIFPointValidateReferences() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            IFPoint fPointRef = fPointA.reflect(fPointB);

            assertSame(fPointA, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect by IFPoint (validate positions)")
        void reflectByIFPointValidatePositions() {
            IFPoint fPointA = HelperRandom.getTestPoint();

            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();
            IFPoint fPointB = FactoryGeometry.getIFPoint(refX, refY, refZ);

            fPointA.reflect(fPointB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointB.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointB.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointB.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get radius (axes: 1)")
        void getRadiusAxes1() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref);

            assertAll("Validate radius",
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().setX(ref).getRadius(),
                            jitter, "The magnitude is invalid [X]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().setX(-ref).getRadius(),
                            jitter, "The magnitude is invalid [-X]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().setY(ref).getRadius(),
                            jitter, "The magnitude is invalid [Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().setY(-ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().setZ(ref).getRadius(),
                            jitter, "The magnitude is invalid [Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().setZ(-ref).getRadius(),
                            jitter, "The magnitude is invalid [-Z]")
            );
        }

        @Test
        @DisplayName("Get radius (axes: 2)")
        void getRadiusAxes2() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(2));

            assertAll("Validate radius",
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [X, Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, -ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, -ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, 0, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, 0, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, 0, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, 0, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(0, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(0, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(0, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(0, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get radius (axes: 3)")
        void getRadiusAxes3() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(3));

            assertAll("Validate radius",
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(ref, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint(-ref, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get radius (validate positions)")
        void getRadiusValidatePositions() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ);

            fPoint.getRadius();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set radius")
        void setRadius() {
            IFPoint fPoint;

            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ);
            double magnitude = fPoint.getRadius();

            double magnitudeExpected = Math.abs(HelperRandom.getTestValue(magnitude));
            fPoint.setRadius(magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getRadius(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set radius (throw IllegalArgumentException)")
        void setRadiusThrowIllegalArgumentException() {

            assertThrows(IllegalArgumentException.class,
                    () -> FactoryGeometry.getIFPoint().set(1, 1, 1).setRadius(-1),
                    "It should not be possible to set negative radius");
        }

        @Test
        @DisplayName("Set radius (throw SamePositionException)")
        void setRadiusThrowSamePositionException() {

            assertThrows(SamePositionException.class,
                    () -> FactoryGeometry.getIFPoint().setRadius(1),
                    "The position of the reference IFPoint must not be zero");
        }

        @Test
        @DisplayName("Set radius (validate references)")
        void setRadiusValidateReferences() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFPoint fPointRef = fPoint.setRadius(Math.abs(HelperRandom.getTestValue()));

            assertSame(fPoint, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set random position (validate vector magnitude)")
        void setRandomPositionValidateMagnitude() {
            double radius = Math.abs(HelperRandom.getTestValue());

            IFPoint fPoint = FactoryGeometry.getIFPoint(radius).setRandom();

            assertEquals(radius, fPoint.getRadius(),
                    jitter, "The radius is invalid");
        }

        @Test
        @DisplayName("Set random position (validate correctness)")
        void setRandomPositionValidateCorrectness() {
            double radius = Math.abs(HelperRandom.getTestValue());

            IFPoint fPointA = FactoryGeometry.getIFPoint(radius).setRandom();
            IFPoint fPointB = FactoryGeometry.getIFPoint(radius).setRandom(fPointA);

            assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
        }

        @Test
        @DisplayName("Set random position (validate timeout)")
        void setRandomPositionValidateTimeout() {
            double radius = Math.abs(HelperRandom.getTestValue());
            IFPoint fPoint = FactoryGeometry.getIFPoint(radius);

            assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fPoint.setRandom(fPoint));
        }

        @Test
        @DisplayName("Get inclination (constant azimuthal angle)")
        void getInclinationConstantAzimuthalAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle08, FactoryGeometry.getIFPoint(0, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, FactoryGeometry.getIFPoint(1, 0, 0).getInclination(),
                            jitter, "The polar angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, FactoryGeometry.getIFPoint(1, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, FactoryGeometry.getIFPoint(0, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get inclination (variable azimuthal angle)")
        void getInclinationVariableAzimuthalAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1,1,0]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(0, 1, 1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,1]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(-1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [-1,1,0]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(0, 1, -1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,-1]")
            );
        }

        @Test
        @DisplayName("Get inclination (validate positions)")
        void getInclinationValidatePositions() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ);

            fPoint.getInclination();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, positive values)")
        void getAzimuthConstantPolarAnglePositive() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle08, FactoryGeometry.getIFPoint(1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, FactoryGeometry.getIFPoint(0, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, FactoryGeometry.getIFPoint(-1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, FactoryGeometry.getIFPoint(-1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, negative values)")
        void getAzimuthConstantPolarAngleNegative() {

            assertAll("Validate angle values",
                    () -> assertEquals(-angle18, FactoryGeometry.getIFPoint(1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-1/8 rad]"),
                    () -> assertEquals(-angle28, FactoryGeometry.getIFPoint(0, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-2/8 rad]"),
                    () -> assertEquals(-angle38, FactoryGeometry.getIFPoint(-1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-3/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (variable polar angle)")
        void getAzimuthVariablePolarAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,1,1]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(1, 0, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,0,0]"),
                    () -> assertEquals(angle18, FactoryGeometry.getIFPoint(1, -1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,-1,1]")
            );
        }

        @Test
        @DisplayName("Get azimuth (validate positions)")
        void getAzimuthValidatePositions() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ);

            fPoint.getAzimuth();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            IFPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = FactoryGeometry.getIFPoint(1, 0, 1).normalize().setInclination(angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = FactoryGeometry.getIFPoint(1, 0, 1).normalize().setInclination(-angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set inclination (angle overflow)")
        void setInclinationOverflow() {
            double angle = 1.5 * Math.PI;
            IFPoint fPointRef = FactoryGeometry.getIFPoint(1, 0, 1).normalize().setInclination(angle);

            assertEquals(0.5 * Math.PI, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set inclination (full circle)")
        void setInclinationFull() {
            double angle = 2.0 * Math.PI;
            IFPoint fPointRef = FactoryGeometry.getIFPoint(1, 0, 1).normalize().setInclination(angle);

            assertEquals(0, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set inclination (validate references)")
        void setInclinationValidateReferences() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFPoint fPointRef = fPoint.setInclination(Math.PI * 0.5);

            assertSame(fPoint, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            IFPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = FactoryGeometry.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

                assertEquals(angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = FactoryGeometry.getIFPoint(1, 1, 0).normalize().setAzimuth(-angle);

                assertEquals(-angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set azimuth (angle overflow)")
        void setAzimuthOverflow() {
            double angle = 1.5 * Math.PI;
            IFPoint fPointRef = FactoryGeometry.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(-Math.PI * 0.5, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set azimuth (full circle)")
        void setAzimuthFull() {
            double angle = 2.0 * Math.PI;
            IFPoint fPointRef = FactoryGeometry.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(0, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");

        }

        @Test
        @DisplayName("Set azimuth (validate references)")
        void setAzimuthValidateReferences() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFPoint fPointRef = fPoint.setAzimuth(Math.PI * 0.5);

            assertSame(fPoint, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            double radius = Math.abs(HelperRandom.getTestValue());

            double inclination = Math.abs(HelperRandom.getTestValue()) % Math.PI;
            double azimuth = Math.abs(HelperRandom.getTestValue()) % Math.PI;

            IFPoint fPointRef = FactoryGeometry.getIFPoint(radius).setSphericalCoordinates(inclination, azimuth);

            assertNotNull(fPointRef, "The instance is null");

            assertAll("Validate spherical coordinates",
                    () -> assertEquals(inclination, fPointRef.getInclination(),
                            jitter, "The inclination is incorrect"),
                    () -> assertEquals(azimuth, fPointRef.getAzimuth(),
                            jitter, "The azimuth is incorrect")
            );
        }

        @Test
        @DisplayName("Set spherical coordinates (validate references)")
        void setSphericalCoordinatesValidateReferences() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFPoint fPointRef = fPoint.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5);

            assertSame(fPoint, fPointRef, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {

            assertTrue(FactoryGeometry.getIFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = FactoryGeometry.getIFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
        }

        @Test
        @DisplayName("Get dot product")
        void dProd() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            double result = fPointA.dProd(fPointB);

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            assertEquals(dimX + dimY + dimZ, result, jitter, "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate references)")
        void dProdValidateReferences() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            fPointA.dProd(fPointB);

            assertNotSame(fPointA, fPointB, "IFPoints should point at different objects");
        }

        @Test
        @DisplayName("Get dot product (validate positions)")
        void dProdValidatePositions() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            IFPoint fPointA = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);

            fPointA.dProd(fPointB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refAX, fPointA.getX(),
                            "IFPoint A - The X value is incorrect"),
                    () -> assertEquals(refAY, fPointA.getY(),
                            "IFPoint A - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fPointA.getZ(),
                            "IFPoint A - The Z value is incorrect"),
                    () -> assertEquals(refBX, fPointB.getX(),
                            "IFPoint B - The X value is incorrect"),
                    () -> assertEquals(refBY, fPointB.getY(),
                            "IFPoint B - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fPointB.getZ(),
                            "IFPoint B - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get dot product (throw NullPointerException)")
        void dProdThrowNullPointerException() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            assertThrows(NullPointerException.class, () -> fPoint.dProd(null),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Get cross product")
        void cProd() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            IFPoint fPointA = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);

            IFPoint fPointRes = fPointA.copy().cProd(fPointB);

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            IFPoint fPointRef = FactoryGeometry.getIFPoint(dimX, dimY, dimZ);

            assertTrue(fPointRes.isSimilar(fPointRef),"The value is not correct");
        }

        @Test
        @DisplayName("Get cross product (validate references)")
        void cProdValidateReferences() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            IFPoint fPointRef = fPointA.cProd(fPointB);

            assertAll("Validate references",
                    () -> assertSame(fPointRef, fPointA,
                            "The returned reference should point at the same object"),
                    () -> assertNotSame(fPointA, fPointB,
                            "References should point at different objects")
            );
        }

        @Test
        @DisplayName("Get cross product (throw NullPointerException")
        void cProdThrowNullPointerException() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            assertThrows(NullPointerException.class, () -> fPoint.cProd(null),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            IFPoint fPointA = FactoryGeometry.getIFPoint(2, 2, 0);
            IFPoint fPointB = FactoryGeometry.getIFPoint(4, -4, 0);

            assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                            jitter, "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fPointB.getAngle(fPointA),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            IFPoint fPointA = FactoryGeometry.getIFPoint(2, 2, 2);
            IFPoint fPointB = FactoryGeometry.getIFPoint(4, 4, 4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            IFPoint fPointA = FactoryGeometry.getIFPoint(2, 2, 2);
            IFPoint fPointB = FactoryGeometry.getIFPoint(-4, -4, -4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            IFPoint fPointA = FactoryGeometry.getIFPoint(0, 1, 0);
            IFPoint fPointB = HelperRandom.getTestPoint().setY(0);

            assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (validate positions)")
        void getAngleValidatePositions() {
            IFPoint fPointA = FactoryGeometry.getIFPoint(1, 2, 3);
            IFPoint fPointB = FactoryGeometry.getIFPoint(4, 5, 6);

            fPointA.getAngle(fPointB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fPointA.getX(),
                            "IFPoint A - The X value is incorrect"),
                    () -> assertEquals(2, fPointA.getY(),
                            "IFPoint A - The Y value is incorrect"),
                    () -> assertEquals(3, fPointA.getZ(),
                            "IFPoint A - The Z value is incorrect"),
                    () -> assertEquals(4, fPointB.getX(),
                            "IFPoint B - The X value is incorrect"),
                    () -> assertEquals(5, fPointB.getY(),
                            "IFPoint B - The Y value is incorrect"),
                    () -> assertEquals(6, fPointB.getZ(),
                            "IFPoint B - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (validate references)")
        void getAngleValidateReferences() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            fPointA.getAngle(fPointB);

            assertNotSame(fPointA, fPointB, "IFPoints should point to different objects");
        }

        @Test
        @DisplayName("Get angle (throw NullPointerException)")
        void getAngleThrowNullPointerException() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            assertThrows(NullPointerException.class, () -> fPoint.getAngle(null),
                    "The reference IFVector cannot be null");
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            double dimX = fPointA.getX() - fPointB.getX();
            double dimY = fPointA.getY() - fPointB.getY();
            double dimZ = fPointA.getZ() - fPointB.getZ();
            double reference = Math.sqrt((dimX * dimX) + (dimY * dimY) + (dimZ * dimZ));

            assertEquals(reference, fPointA.getDistance(fPointB),
                    jitter, "The distance between IFPoints is incorrect");
        }

        @Test
        @DisplayName("Get distance (validate positions)")
        void getDistanceValidatePositions() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            IFPoint fPointA = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);

            fPointA.getDistance(fPointB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refAX, fPointA.getX(),
                            "IFPoint A - The X value is incorrect"),
                    () -> assertEquals(refAY, fPointA.getY(),
                            "IFPoint A - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fPointA.getZ(),
                            "IFPoint A - The Z value is incorrect"),
                    () -> assertEquals(refBX, fPointB.getX(),
                            "IFPoint B - The X value is incorrect"),
                    () -> assertEquals(refBY, fPointB.getY(),
                            "IFPoint B - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fPointB.getZ(),
                            "IFPoint B - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get distance (throw NullPointerException)")
        void getDistanceThrowNullPointerException() {
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            assertThrows(NullPointerException.class, () -> fPoint.getDistance(null),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            fPointA.setDistance(fPointB, distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (validate positions)")
        void setDistanceValidatePositions() {
            IFPoint fPointA = HelperRandom.getTestPoint();

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);

            fPointA.setDistance(fPointB, 1);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refBX, fPointB.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(refBY, fPointB.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(refBZ, fPointB.getZ(),
                            "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set distance (validate references)")
        void setDistanceValidateReferences() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            IFPoint fPointRef = fPointA.setDistance(fPointB, 1);

            assertAll("Validate references",
                    () -> assertSame(fPointRef, fPointA,
                            "The returned reference should point at the same object"),
                    () -> assertNotSame(fPointA, fPointB,
                            "References should point at different objects")
            );
        }

        @Test
        @DisplayName("Set distance (throw NullPointerException)")
        void setDistanceThrowNullPointerException() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            assertThrows(NullPointerException.class, () -> fPoint.setDistance(null, 1),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Set distance (throw SamePositionException)")
        void setDistanceThrowSamePositionException() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = fPointA.copy();

            assertThrows(SamePositionException.class, () -> fPointA.setDistance(fPointB, 1),
                    "IFPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance (throw IllegalArgumentException)")
        void setDistanceThrowIllegalArgumentException() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            assertThrows(IllegalArgumentException.class, () -> fPointA.setDistance(fPointB, -1),
                    "The distance cannot be lower then zero");
        }
    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        private double refX, refY, refZ;

        @BeforeEach
        void beforeAll() {
            refX = HelperRandom.getTestValue();
            refY = HelperRandom.getTestValue();
            refZ = HelperRandom.getTestValue();
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = FactoryGeometry.getIFPoint().importFromJSON(fPointRef.exportToJSON());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertAll("Check combinations",
                    () -> assertTrue(fPointRef.isExact(fPointOp), "IFPoints should be equal"),
                    () -> assertTrue(fPointOp.isExact(fPointRef), "IFPoints should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (throw NullPointerException)")
        void isExactThrowNullPointerException() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertThrows(NullPointerException.class,
                    () -> fPointRef.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            double ref = jitter * 0.5;

            assertAll("Check combinations (true)",
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef)),
                            "IFPoints should be similar (same position)"),
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).addX(ref)),
                            "IFPoints should be similar (positive X)"),
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).subX(ref)),
                            "IFPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).addY(ref)),
                            "IFPoints should be similar (positive Y)"),
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).subY(ref)),
                            "IFPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).addZ(ref)),
                            "IFPoints should be similar (positive Z)"),
                    () -> assertTrue(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).subZ(ref)),
                            "IFPoints should be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            double ref = jitter * 2;

            assertAll("Check combinations (false)",
                    () -> assertFalse(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).addX(ref)),
                            "IFPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).subX(ref)),
                            "IFPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).addY(ref)),
                            "IFPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).subY(ref)),
                            "IFPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).addZ(ref)),
                            "IFPoints should not be similar (positive Z)"),
                    () -> assertFalse(fPointRef.isSimilar(FactoryGeometry.getIFPoint().add(fPointRef).subZ(ref)),
                            "IFPoints should not be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFPoint fPointRefA = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPointRefB = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.hashCode(), fPointRefB.hashCode(),
                    "Two identical points should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFPoint fPointRefA = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertNotEquals(fPointRefA.hashCode(), FactoryGeometry.getIFPoint().hashCode(),
                    "The different points should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPoint = fPointRef.copy();

            assertAll("Validate similarity",
                    () -> assertNotSame(fPointRef, fPoint,
                            "FPoints represent different objects"),
                    () -> assertEquals(fPointRef, fPoint,
                            "FPoints should have the same values"),
                    () -> assertNotEquals(fPointRef, fPoint.add(fPointRef),
                            "FPoints should have different values")
            );
        }

    }

    @Nested
    @DisplayName("Base algebra")
    class IBaseAlgebra {

        private double refX, refY, refZ;
        private IFPoint fPoint;
        private double opX, opY, opZ;

        @BeforeEach
        void beforeEach() {
            refX = HelperRandom.getTestValue();
            refY = HelperRandom.getTestValue();
            refZ = HelperRandom.getTestValue();

            fPoint = FactoryGeometry.getIFPoint(refX, refY, refZ);

            opX = HelperRandom.getTestValue();
            opY = HelperRandom.getTestValue();
            opZ = HelperRandom.getTestValue();
        }

        @Test
        @DisplayName("Add IFPoint")
        void addIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.add(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add IFPoint (validate references)")
        void addIFPointValidateReferences() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            IFPoint fPointRef = fPoint.add(fPointOp);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Add IFPoint (validate positions)")
        void addIFPointValidatePositions() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.add(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(opX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(opY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(opZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add IFPoint (throw NullPointerException)")
        void addIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class,
                    () -> fPoint.add(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fPoint.add(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate references)")
        void addPrimitivesValidateReferences() {
            IFPoint fPointRef = fPoint.add(opX, opY, opZ);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opX * opY * opZ;

            fPoint.add(op);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate references)")
        void addFactorValidateReferences() {
            double op = opX * opY * opZ;

            IFPoint fPointRef = fPoint.add(op);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Add X")
        void addX() {

            fPoint.addX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add X (validate references)")
        void addXValidateReferences() {
            IFPoint fPointRef = fPoint.addX(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Add Y")
        void addY() {

            fPoint.addY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Y (validate references)")
        void addYValidateReferences() {
            IFPoint fPointRef = fPoint.addY(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {

            fPoint.addZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Z (validate references)")
        void addZValidateReferences() {
            IFPoint fPointRef = fPoint.addZ(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Sub IFPoint")
        void subIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.sub(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFPoint (validate positions)")
        void subIFPointValidatePositions() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.sub(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(opX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(opY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(opZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFPoint (validate references)")
        void subIFPointValidateReferences() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            IFPoint fPointRef = fPoint.sub(fPointOp);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Sub IFPoint (throw NullPointerException)")
        void subIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class,
                    () -> fPoint.sub(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fPoint.sub(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate references)")
        void subPrimitivesValidateReferences() {
            IFPoint fPointRef = fPoint.sub(opX, opY, opZ);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opX * opY * opZ;

            fPoint.sub(op);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate references)")
        void subFactorValidateReferences() {
            double op = opX * opY * opZ;

            IFPoint fPointRef = fPoint.sub(op);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Sub X")
        void subX() {

            fPoint.subX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub X (validate references)")
        void subXValidateReferences() {
            IFPoint fPointRef = fPoint.subX(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {

            fPoint.subY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Y (validate references)")
        void subYValidateReferences() {
            IFPoint fPointRef = fPoint.subY(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {

            fPoint.subZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Z (validate references)")
        void subZValidateReferences() {
            IFPoint fPointRef = fPoint.subZ(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Mul IFPoint")
        void mulIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.mul(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul IFPoint (validate positions)")
        void mulIFPointValidatePositions() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.mul(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(opX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(opY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(opZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul IFPoint (validate references)")
        void mulIFPointValidateReferences() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            IFPoint fPointRef = fPoint.mul(fPointOp);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Mul IFPoint (throw NullPointerException)")
        void mulIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class,
                    () -> fPoint.mul(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fPoint.mul(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate references)")
        void mulPrimitivesValidateReferences() {
            IFPoint fPointRef = fPoint.mul(opX, opY, opZ);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opX * opY * opZ;

            fPoint.mul(op);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate references)")
        void mulFactorValidateReferences() {
            double op = opX * opY * opZ;

            IFPoint fPointRef = fPoint.mul(op);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {

            fPoint.mulX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul X (validate references)")
        void mulXValidateReferences() {
            IFPoint fPointRef = fPoint.mulX(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {

            fPoint.mulY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Y (validate references)")
        void mulYValidateReferences() {
            IFPoint fPointRef = fPoint.mulY(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {

            fPoint.mulZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Z (validate references)")
        void mulZValidateReferences() {
            IFPoint fPointRef = fPoint.mulZ(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div IFPoint")
        void divIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.div(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div IFPoint (validate positions)")
        void divIFPointValidatePositions() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPoint.div(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(opX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(opY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(opZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div IFPoint (validate references)")
        void divIFPointValidateReferences() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            IFPoint fPointRef = fPoint.div(fPointOp);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div IFPoint (throw ArithmeticException)")
        void divIFPointThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(FactoryGeometry.getIFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(FactoryGeometry.getIFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(FactoryGeometry.getIFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div IFPoint (NullPointerException)")
        void divIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPoint.div(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fPoint.div(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (validate references)")
        void divPrimitivesValidateReferences() {
            IFPoint fPointRef = fPoint.div(opX, opY, opZ);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(0, 1, 1), "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(1, 0, 1), "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(0, 1, 1), "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opX * opY * opZ;

            fPoint.div(op);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (validate references)")
        void divFactorValidateReferences() {
            double op = opX * opY * opZ;

            IFPoint fPointRef = fPoint.div(op);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {
            double op = opX * opY * opZ;

            assertThrows(ArithmeticException.class, () -> fPoint.div(0), "The factor is zero");
        }

        @Test
        @DisplayName("Div X")
        void divX() {

            fPoint.divX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div X (validate references)")
        void divXValidateReferences() {
            IFPoint fPointRef = fPoint.divX(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div X (throw ArithmeticException)")
        void divXThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div Y")
        void divY() {

            fPoint.divY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div Y (validate references)")
        void divYValidateReferences() {
            IFPoint fPointRef = fPoint.divY(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div Y (throw ArithmeticException)")
        void divYThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {

            fPoint.divZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div Z (validate references)")
        void divZValidateReferences() {
            IFPoint fPointRef = fPoint.divZ(opX);

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Div Z (ArithmeticException)")
        void divZThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Get IFPoint list")
        void getIFPoints() {
            List<IFPoint> list = fPoint.disassemble();

            assertAll("Validate IFPoint list",
                    () -> assertEquals(1, list.size(), "The size of the list is incorrect"),
                    () -> assertEquals(refX, list.get(0).getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, list.get(0).getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, list.get(0).getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Swap")
        void swap() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPointOp.swap(fPoint);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The reference X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The reference Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The reference Z value is incorrect"),
                    () -> assertEquals(opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Swap (validate references)")
        void swapValidateReferences() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            IFPoint fPointRef = fPointOp.swap(fPoint);

            assertAll("Validate references",
                    () -> assertNotSame(fPoint, fPointOp, "IFPoint references should be different"),
                    () -> assertSame(fPointOp, fPointRef, "The IFPoint reference should not change")
            );
        }

        @Test
        @DisplayName("Swap (throw NullPointerException)")
        void swapThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPoint.swap(null),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            IFPoint fPointRef = FactoryGeometry.getIFPoint();

            fPoint.imprint(fPointRef);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The reference X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The reference Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The reference Z value is incorrect"),
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate references")
        void imprintValidateReferences() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint();

            IFPoint fPointRef = fPointOp.imprint(fPoint);

            assertAll("Validate references",
                    () -> assertNotSame(fPoint, fPointOp, "IFPoint references should be different"),
                    () -> assertSame(fPointOp, fPointRef, "The IFPoint reference should not change")
            );
        }

        @Test
        @DisplayName("Imprint (throw NullPointerException)")
        void imprintThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPoint.imprint(null),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Custom function - chain")
        void fun() {

            fPoint.fun(e -> e.addX(opX).addY(opY).addZ(opZ));

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Custom function - chain (validate references)")
        void funValidateReferences() {
            IFPoint fPointRef = fPoint.fun(e -> e.addX(opX).addY(opY).addZ(opZ));

            assertSame(fPointRef, fPoint, "The IFPoint reference is erroneous");

        }

        @Test
        @DisplayName("Custom function - chain (throw NullPointerException)")
        void funThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPoint.fun(null),
                    "The reference expression must not be null");
        }

        @Test
        @DisplayName("Custom function - value")
        void funVal() {

            assertEquals(refX + refY + refZ, fPoint.funVal(e -> e.getX() + e.getY() + e.getZ()),
                    "The resulting value is erroneous");
        }

        @Test
        @DisplayName("Custom function - number (throw NullPointerException)")
        void funValThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPoint.funVal(null),
                    "The reference expression must not be null");
        }

        @Test
        @DisplayName("Custom function - value")
        void funLog() {

            assertEquals(true, fPoint.funLog(e -> e.getX() != 0),
                    "The resulting value is erroneous");
        }

        @Test
        @DisplayName("Custom function - number (throw NullPointerException)")
        void funLogThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPoint.funLog(null),
                    "The reference expression must not be null");
        }

    }

}

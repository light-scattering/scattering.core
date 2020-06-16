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
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertEquals(1, fPointRef.normalize().getRadius(),
                    jitter, "The magnitude of the normalized vector should be one");
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ).reflect();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(-refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPointRef.getZ(), "The Z value is incorrect")
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
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [X, Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, -ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, -ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, 0, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, 0, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, 0, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, 0, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(0, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(0, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(0, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(0, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get radius (axes: 3)")
        void getRadiusAxes3() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(3));

            assertAll("Validate radius",
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(ref, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y, -Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y, Z]"),
                    () -> assertEquals(expected, FactoryGeometry.getIFPoint().set(-ref, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y, -Z]")
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
            double magnitudeCurrent = fPoint.getRadius();

            double magnitudeExpected = Math.abs(HelperRandom.getTestValue(magnitudeCurrent));
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
                    "The position of the IFPoint must not be zero (the vector points to an unknown direction");
        }

        @Test
        @DisplayName("Set random position (validate vector magnitude)")
        void setRandomPositionValidateMagnitude() {
            double radius = Math.abs(HelperRandom.getTestValue());

            IFPoint fPointRef = FactoryGeometry.getIFPoint(radius).setRandom();

            assertNotNull(fPointRef, "The instance is null");

            assertEquals(radius, fPointRef.getRadius(),
                    jitter, "The radius is invalid");
        }

        @Test
        @DisplayName("Set random position (validate correctness)")
        void setRandomPositionValidateCorrectness() {
            double radius = Math.abs(HelperRandom.getTestValue());

            IFPoint fPointRefA = FactoryGeometry.getIFPoint(radius).setRandom();
            IFPoint fPointRefB = FactoryGeometry.getIFPoint(radius).setRandom(fPointRefA);

            assertNotEquals(fPointRefA, fPointRefB, "Two randomly generated points should be different");
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
        @DisplayName("Is zero")
        void isZeroTrue() {
            assertTrue(FactoryGeometry.getIFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is not zero")
        void isZeroFalse() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = FactoryGeometry.getIFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
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

            assertThrows(NullPointerException.class,
                    () -> fPointRef.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Similarity (positive)")
        void isSimilarPositive() {
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
        @DisplayName("Similarity (negative)")
        void isSimilarNegative() {
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
        @DisplayName("Get hash code (positive)")
        void getHashCodePositive() {
            IFPoint fPointRefA = FactoryGeometry.getIFPoint(refX, refY, refZ);
            IFPoint fPointRefB = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.getHashCode(), fPointRefB.getHashCode(),
                    "Two identical points should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (negative)")
        void getHashCodeNegative() {
            IFPoint fPointRefA = FactoryGeometry.getIFPoint(refX, refY, refZ);

            assertNotEquals(fPointRefA.getHashCode(), FactoryGeometry.getIFPoint().getHashCode(),
                    "The different points should not have the same hash code");
        }

        @Test
        @DisplayName("Make copy")
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
        private IFPoint fPointRef;
        private double opX, opY, opZ;

        @BeforeEach
        void beforeEach() {
            refX = HelperRandom.getTestValue();
            refY = HelperRandom.getTestValue();
            refZ = HelperRandom.getTestValue();

            fPointRef = FactoryGeometry.getIFPoint(refX, refY, refZ);

            opX = HelperRandom.getTestValue();
            opY = HelperRandom.getTestValue();
            opZ = HelperRandom.getTestValue();
        }

        @Test
        @DisplayName("Add IFPoint")
        void addIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPointRef.add(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add IFPoint (throw NullPointerException)")
        void addIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class,
                    () -> fPointRef.add(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fPointRef.add(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add X")
        void addX() {

            fPointRef.addX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Y")
        void addY() {

            fPointRef.addY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {

            fPointRef.addZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFPoint")
        void subIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPointRef.sub(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFPoint (throw NullPointerException)")
        void subIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class,
                    () -> fPointRef.sub(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fPointRef.sub(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub X")
        void subX() {

            fPointRef.subX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {

            fPointRef.subY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {

            fPointRef.subZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul IFPoint")
        void mulIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPointRef.mul(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul IFPoint (throw NullPointerException)")
        void mulIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class,
                    () -> fPointRef.mul(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fPointRef.mul(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {

            fPointRef.mulX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {

            fPointRef.mulY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {

            fPointRef.mulZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div IFPoint")
        void divIFPoint() {
            IFPoint fPointOp = FactoryGeometry.getIFPoint(opX, opY, opZ);

            fPointRef.div(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div IFPoint (throw ArithmeticException)")
        void divIFPointThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPointRef.div(FactoryGeometry.getIFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPointRef.div(FactoryGeometry.getIFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPointRef.div(FactoryGeometry.getIFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div IFPoint (NullPointerException)")
        void divIFPointThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> fPointRef.div(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fPointRef.div(opX, opY, opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPointRef.div(0, 1, 1), "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPointRef.div(1, 0, 1), "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPointRef.div(0, 1, 1), "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div X")
        void divX() {

            fPointRef.divX(opX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div X (throw ArithmeticException)")
        void divXThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPointRef.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div Y")
        void divY() {

            fPointRef.divY(opY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div Y (throw ArithmeticException)")
        void divYThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPointRef.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {

            fPointRef.divZ(opZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div Z (ArithmeticException)")
        void divZThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPointRef.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Scale")
        void scale() {
            double op = opX * opY * opZ;

            fPointRef.scale(op);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * op, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * op, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * op, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get IFPoint list")
        void getIFPoints() {
            List<IFPoint> list = fPointRef.getIFPoints();

            assertAll("Validate IFPoint list",
                    () -> assertEquals(1, list.size(), "The Size of the list is incorrect"),
                    () -> assertEquals(refX, list.get(0).getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, list.get(0).getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, list.get(0).getZ(), "The Z value is incorrect")
            );
        }

    }

}

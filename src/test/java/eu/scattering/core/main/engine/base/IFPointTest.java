package eu.scattering.core.main.engine.base;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.main.MainFactory;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.helper.HelperIFPoint;
import eu.scattering.core.support.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static eu.scattering.core.Config.*;

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
        @DisplayName("Construct")
        void construct() {
            IFPoint fPoint = MainFactory.getIFPoint();

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
            IFPoint fPoint = MainFactory.getIFPoint(refX);

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
            IFPoint fPoint = MainFactory.getIFPoint(refX, refY);

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
            IFPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ);

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
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPoint = MainFactory.getIFPoint(fPointRef);

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
            IFPoint fPoint = MainFactory.getIFPoint();

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
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPoint = MainFactory.getIFPoint();

            fPoint.set(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created IFPoints should be different");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set X")
        void setX() {
            IFPoint fPoint = MainFactory.getIFPoint();

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
            IFPoint fPoint = MainFactory.getIFPoint();

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
            IFPoint fPoint = MainFactory.getIFPoint();

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

            assertEquals(1, fPoint.normalize().getLength(),
                    jitter, "The magnitude of the normalized vector should be equal to one");
        }

        @Test
        @DisplayName("Normalize (throw DirectionException)")
        void normalizeThrowDirectionException() {
            IFPoint fPoint = MainFactory.getIFPoint();

            assertThrows(DirectionException.class, fPoint::normalize,
                    "The IFPoints must not be on the same position");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::normalize, fPoint);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ).reflect();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(-refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::reflect, fPoint);
        }

        @Test
        @DisplayName("Reflect by IFPoint")
        void reflectByIFPoint() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            IFPoint fPointA = MainFactory.getIFPoint(refAX, refAY, refAZ);

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = MainFactory.getIFPoint(refBX, refBY, refBZ);

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
        @DisplayName("Reflect (validate)")
        void reflectByIFPointValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            HelperIFPoint.validateRef(IFPoint::reflect, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get length (axes: 1)")
        void getLengthAxes1() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref);

            assertAll("Validate radius",
                    () -> assertEquals(expected, MainFactory.getIFPoint().setX(ref).getLength(),
                            jitter, "The magnitude is invalid [X]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint().setX(-ref).getLength(),
                            jitter, "The magnitude is invalid [-X]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint().setY(ref).getLength(),
                            jitter, "The magnitude is invalid [Y]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint().setY(-ref).getLength(),
                            jitter, "The magnitude is invalid [-Y]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint().setZ(ref).getLength(),
                            jitter, "The magnitude is invalid [Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint().setZ(-ref).getLength(),
                            jitter, "The magnitude is invalid [-Z]")
            );
        }

        @Test
        @DisplayName("Get length (axes: 2)")
        void getLengthAxes2() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(2));

            assertAll("Validate radius",
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, ref, 0).getLength(),
                            jitter, "The magnitude is invalid [X, Y]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, -ref, 0).getLength(),
                            jitter, "The magnitude is invalid [X, -Y]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, ref, 0).getLength(),
                            jitter, "The magnitude is invalid [-X, Y]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, -ref, 0).getLength(),
                            jitter, "The magnitude is invalid [-X, -Y]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, 0, ref).getLength(),
                            jitter, "The magnitude is invalid [X, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, 0, -ref).getLength(),
                            jitter, "The magnitude is invalid [X, -Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, 0, ref).getLength(),
                            jitter, "The magnitude is invalid [-X, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, 0, -ref).getLength(),
                            jitter, "The magnitude is invalid [-X, -Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(0, ref, ref).getLength(),
                            jitter, "The magnitude is invalid [Y, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(0, ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [Y, -Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(0, -ref, ref).getLength(),
                            jitter, "The magnitude is invalid [-Y, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(0, -ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [-Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get length (axes: 3)")
        void getLengthAxes3() {
            double ref = HelperRandom.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(3));

            assertAll("Validate radius",
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, ref, ref).getLength(),
                            jitter, "The magnitude is invalid [X, Y, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [X, Y, -Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, -ref, ref).getLength(),
                            jitter, "The magnitude is invalid [X, -Y, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(ref, -ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [X, -Y, -Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, ref, ref).getLength(),
                            jitter, "The magnitude is invalid [-X, Y, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [-X, Y, -Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, -ref, ref).getLength(),
                            jitter, "The magnitude is invalid [-X, -Y, Z]"),
                    () -> assertEquals(expected, MainFactory.getIFPoint(-ref, -ref, -ref).getLength(),
                            jitter, "The magnitude is invalid [-X, -Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get length (validate)")
        void getLengthValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(IFPoint::getLength, fPoint);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            IFPoint fPoint;

            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            fPoint = MainFactory.getIFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(HelperRandom.getTestValue(magnitude));
            fPoint.setLength(magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (negative)")
        void setLengthNegative() {
            IFPoint fPoint;

            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            fPoint = MainFactory.getIFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(HelperRandom.getTestValue(magnitude));
            fPoint.setLength(-magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (throw DirectionException)")
        void setLengthThrowDirectionException() {

            assertThrows(DirectionException.class,
                    () -> MainFactory.getIFPoint().setLength(1),
                    "The position of the reference IFPoint must not be zero");
        }

        @Test
        @DisplayName("Set length (validate)")
        void setLengthValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.setLength(1), fPoint);
        }

        @Test
        @DisplayName("Set random angle (validate vector magnitude)")
        void setRandomAngleValidateMagnitude() {
            double radius = Math.abs(HelperRandom.getTestValue());

            IFPoint fPoint = MainFactory.getIFPoint(radius).setRandomAngle();

            assertEquals(radius, fPoint.getLength(),
                    jitter, "The radius is invalid");
        }

        @Test
        @DisplayName("Set random angle (validate correctness)")
        void setRandomAngleValidateCorrectness() {
            double radius = Math.abs(HelperRandom.getTestValue());

            IFPoint fPointA = MainFactory.getIFPoint(radius).setRandomAngle();
            IFPoint fPointB = MainFactory.getIFPoint(radius).setRandomAngle(fPointA);

            assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
        }

        @Test
        @DisplayName("Set random angle (validate timeout)")
        void setRandomAngleValidateTimeout() {
            double radius = Math.abs(HelperRandom.getTestValue());
            IFPoint fPoint = MainFactory.getIFPoint(radius);

            assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fPoint.setRandomAngle(fPoint));
        }

        @Test
        @DisplayName("Set random angle (validate)")
        void setRandomAngleValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::setRandomAngle, fPoint);
        }

        @Test
        @DisplayName("Get inclination (constant azimuthal angle)")
        void getInclinationConstantAzimuthalAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle08, MainFactory.getIFPoint(0, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, MainFactory.getIFPoint(1, 0, 0).getInclination(),
                            jitter, "The polar angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, MainFactory.getIFPoint(1, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, MainFactory.getIFPoint(0, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get inclination (variable azimuthal angle)")
        void getInclinationVariableAzimuthalAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle18, MainFactory.getIFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1,1,0]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(0, 1, 1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,1]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(-1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [-1,1,0]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(0, 1, -1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,-1]")
            );
        }

        @Test
        @DisplayName("Get inclination (validate)")
        void getInclinationValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(IFPoint::getInclination, fPoint);
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, positive values)")
        void getAzimuthConstantPolarAnglePositive() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle08, MainFactory.getIFPoint(1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, MainFactory.getIFPoint(0, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, MainFactory.getIFPoint(-1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, MainFactory.getIFPoint(-1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, negative values)")
        void getAzimuthConstantPolarAngleNegative() {

            assertAll("Validate angle values",
                    () -> assertEquals(-angle18, MainFactory.getIFPoint(1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-1/8 rad]"),
                    () -> assertEquals(-angle28, MainFactory.getIFPoint(0, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-2/8 rad]"),
                    () -> assertEquals(-angle38, MainFactory.getIFPoint(-1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-3/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (variable polar angle)")
        void getAzimuthVariablePolarAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle18, MainFactory.getIFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,1,1]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(1, 0, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,0,0]"),
                    () -> assertEquals(angle18, MainFactory.getIFPoint(1, -1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,-1,1]")
            );
        }

        @Test
        @DisplayName("Get azimuth (validate)")
        void getAzimuthValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(IFPoint::getAzimuth, fPoint);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            IFPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = MainFactory.getIFPoint(1, 0, 1).normalize().setInclination(angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = MainFactory.getIFPoint(1, 0, 1).normalize().setInclination(-angle);

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
            IFPoint fPointRef = MainFactory.getIFPoint(1, 0, 1).normalize().setInclination(angle);

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
            IFPoint fPointRef = MainFactory.getIFPoint(1, 0, 1).normalize().setInclination(angle);

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
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.setInclination(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            IFPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = MainFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

                assertEquals(angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = MainFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(-angle);

                assertEquals(-angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set azimuth (angle overflow)")
        void setAzimuthOverflow() {
            double angle = 1.5 * Math.PI;
            IFPoint fPointRef = MainFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(-Math.PI * 0.5, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set azimuth (full circle)")
        void setAzimuthFull() {
            double angle = 2.0 * Math.PI;
            IFPoint fPointRef = MainFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(0, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    jitter, "The magnitude is incorrect [" + angle + " rad]");

        }

        @Test
        @DisplayName("Set azimuth (validate)")
        void setAzimuthValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.setAzimuth(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            double radius = Math.abs(HelperRandom.getTestValue());

            double inclination = Math.abs(HelperRandom.getTestValue()) % Math.PI;
            double azimuth = Math.abs(HelperRandom.getTestValue()) % Math.PI;

            IFPoint fPointRef = MainFactory.getIFPoint(radius).setSphericalCoordinates(inclination, azimuth);

            assertNotNull(fPointRef, "The instance is null");

            assertAll("Validate spherical coordinates",
                    () -> assertEquals(inclination, fPointRef.getInclination(),
                            jitter, "The inclination is incorrect"),
                    () -> assertEquals(azimuth, fPointRef.getAzimuth(),
                            jitter, "The azimuth is incorrect")
            );
        }

        @Test
        @DisplayName("Set spherical coordinates (validate)")
        void setSphericalCoordinatesValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {

            assertTrue(MainFactory.getIFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = MainFactory.getIFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(IFPoint::isZero, fPoint);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            double result = fPointA.getDotProduct(fPointB);

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            assertEquals(dimX + dimY + dimZ, result, jitter, "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            HelperIFPoint.validateVal(IFPoint::getDotProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            IFPoint fPointA = MainFactory.getIFPoint(refAX, refAY, refAZ);

            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointB = MainFactory.getIFPoint(refBX, refBY, refBZ);

            IFPoint fPointRes = fPointA.copy().setCrossProduct(fPointB);

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            IFPoint fPointRef = MainFactory.getIFPoint(dimX, dimY, dimZ);

            assertTrue(fPointRes.isSimilar(fPointRef),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            HelperIFPoint.validateRef(IFPoint::setCrossProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            IFPoint fPointA = MainFactory.getIFPoint(2, 2, 0);
            IFPoint fPointB = MainFactory.getIFPoint(4, -4, 0);

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
            IFPoint fPointA = MainFactory.getIFPoint(2, 2, 2);
            IFPoint fPointB = MainFactory.getIFPoint(4, 4, 4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            IFPoint fPointA = MainFactory.getIFPoint(2, 2, 2);
            IFPoint fPointB = MainFactory.getIFPoint(-4, -4, -4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            IFPoint fPointA = MainFactory.getIFPoint(0, 1, 0);
            IFPoint fPointB = HelperRandom.getTestPoint().setY(0);

            assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw DirectionException, input)")
        void getAngleThrowDirectionExceptionInput() {
            IFPoint fPointA = MainFactory.getIFPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            assertThrows(DirectionException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the input IFPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (throw DirectionException, argument)")
        void getAngleThrowDirectionExceptionArgument() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = MainFactory.getIFPoint();

            assertThrows(DirectionException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the argument IFPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            HelperIFPoint.validateVal(IFPoint::getAngle, fPointA, fPointB);
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
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            HelperIFPoint.validateVal(IFPoint::getDistance, fPointA, fPointB);
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
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            fPointA.setDistance(fPointB, -distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            IFPoint fPointRefA = fPointA.copy().setDistance(fPointB, distance);
            IFPoint fPointRefB = fPointA.copy().setDistance(fPointB, -distance);

            assertEquals(2 * distance, fPointRefA.getDistance(fPointRefB),
                    jitter, "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw DirectionException)")
        void setDistanceThrowDirectionException() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = fPointA.copy();

            assertThrows(DirectionException.class, () -> fPointA.setDistance(fPointB, 1),
                    "IFPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint(fPointA);

            HelperIFPoint.validateRef((a, b) -> a.setDistance(b, 1), fPointA, fPointB);
        }

    }

    @Nested
    @Tag("Core")
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
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = MainFactory.getIFPoint().importFromJSON(fPointRef.exportToJSON());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = MainFactory.getIFPoint(refX, refY, refZ);

            assertAll("Check combinations",
                    () -> assertTrue(fPointRef.isExact(fPointOp), "IFPoints should be equal"),
                    () -> assertTrue(fPointOp.isExact(fPointRef), "IFPoints should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = MainFactory.getIFPoint(refX, refY, refZ).add(0.5 * jitter);

            assertAll("Check combinations",
                    () -> assertFalse(fPointRef.isExact(fPointOp), "IFPoints should not be equal"),
                    () -> assertFalse(fPointOp.isExact(fPointRef), "IFPoints should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);

            assertTrue(fPointRef.isExact(refX, refY, refZ), "IFPoint values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);

            assertFalse(fPointRef.isExact(0, 0, 0), "IFPoint values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(e -> e.isExact(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            double ref = jitter * 0.5;

            assertAll("Check combinations (true)",
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef)),
                            "IFPoints should be similar (same position)"),
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).addX(ref)),
                            "IFPoints should be similar (positive X)"),
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).subX(ref)),
                            "IFPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).addY(ref)),
                            "IFPoints should be similar (positive Y)"),
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).subY(ref)),
                            "IFPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).addZ(ref)),
                            "IFPoints should be similar (positive Z)"),
                    () -> assertTrue(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).subZ(ref)),
                            "IFPoints should be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            double ref = jitter * 2;

            assertAll("Check combinations (false)",
                    () -> assertFalse(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).addX(ref)),
                            "IFPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).subX(ref)),
                            "IFPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).addY(ref)),
                            "IFPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).subY(ref)),
                            "IFPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).addZ(ref)),
                            "IFPoints should not be similar (positive Z)"),
                    () -> assertFalse(fPointRef.isSimilar(MainFactory.getIFPoint().add(fPointRef).subZ(ref)),
                            "IFPoints should not be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            IFPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ);

            assertTrue(fPoint.isSimilar(refX + (0.5 * jitter), refY + (0.5 * jitter), refZ + (0.5 * jitter)),
                    "IFPoint values should be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            IFPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ);

            assertFalse(fPoint.isSimilar(refX + (1.5 * jitter), refY + (1.5 * jitter), refZ + (1.5 * jitter)),
                    "IFPoint values should not be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(e -> e.isSimilar(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFPoint fPointRefA = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointRefB = MainFactory.getIFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.hashCode(), fPointRefB.hashCode(),
                    "Two identical IFPoints should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFPoint fPointRefA = MainFactory.getIFPoint(refX, refY, refZ);

            assertNotEquals(fPointRefA.hashCode(), MainFactory.getIFPoint().hashCode(),
                    "The different IFPoints should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(IFPoint::hashCode, fPoint);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPoint = fPointRef.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fPointRef, fPoint,
                            "FPoints represent different objects"),
                    () -> assertEquals(fPointRef, fPoint,
                            "FPoints should have the same values"),
                    () -> assertNotEquals(fPointRef, fPoint.add(fPointRef),
                            "FPoints should have different values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateVal(IFPoint::copy, fPoint);
        }

    }

    @Nested
    @Tag("Algebra")
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

            fPoint = MainFactory.getIFPoint(refX, refY, refZ);

            opX = HelperRandom.getTestValue();
            opY = HelperRandom.getTestValue();
            opZ = HelperRandom.getTestValue();
        }

        @Test
        @DisplayName("Add IFPoint")
        void addIFPoint() {
            IFPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

            fPoint.add(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add IFPoint (validate)")
        void addIFPointValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::add, fPointA, fPointB);
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
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.add(0, 0, 0), fPoint);
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
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.add(1), fPoint);
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
        @DisplayName("Add X (validate)")
        void addXValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.addX(1), fPoint);
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
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.addY(1), fPoint);
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
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.addZ(1), fPoint);
        }

        @Test
        @DisplayName("Sub IFPoint")
        void subIFPoint() {
            IFPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

            fPoint.sub(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFPoint (validate)")
        void subIFPointValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::sub, fPointA, fPointB);
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
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.sub(0, 0, 0), fPoint);
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
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.sub(1), fPoint);
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
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.subX(1), fPoint);
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
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.subY(1), fPoint);
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
        @DisplayName("Sub Z (validate)")
        void subZValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.subZ(1), fPoint);
        }

        @Test
        @DisplayName("Mul IFPoint")
        void mulIFPoint() {
            IFPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

            fPoint.mul(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul IFPoint (validate)")
        void mulIFPointValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::mul, fPointA, fPointB);
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
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.mul(0, 0, 0), fPoint);
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
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.mul(1), fPoint);
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
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.mulX(1), fPoint);
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
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.mulY(1), fPoint);
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
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.mulZ(1), fPoint);
        }

        @Test
        @DisplayName("Div IFPoint")
        void divIFPoint() {
            IFPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

            fPoint.div(fPointOp);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div IFPoint (throw ArithmeticException)")
        void divIFPointThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(MainFactory.getIFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(MainFactory.getIFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(MainFactory.getIFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div IFPoint (validate)")
        void divIFPointValidate() {
            IFPoint fPointA = HelperRandom.getTestPoint();
            IFPoint fPointB = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(IFPoint::mul, fPointA, fPointB);
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
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.div(1, 1, 1), fPoint);
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
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.div(0), "The factor is zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.div(1), fPoint);
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
        @DisplayName("Div X (throw ArithmeticException)")
        void divXThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div X (validate)")
        void divXValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.divX(1), fPoint);
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
        @DisplayName("Div Y (throw ArithmeticException)")
        void divYThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Y (validate)")
        void divYValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.divY(1), fPoint);
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
        @DisplayName("Div Z (ArithmeticException)")
        void divZThrowArithmeticException() {

            assertThrows(ArithmeticException.class, () -> fPoint.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Div Z (validate)")
        void divZValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.divZ(1), fPoint);
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
        @DisplayName("Imprint")
        void imprint() {
            IFPoint fPointRef = MainFactory.getIFPoint();

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
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            IFPoint fPointOp = MainFactory.getIFPoint();

            IFPoint fPointRef = fPointOp.imprint(fPoint);

            assertAll("Validate references",
                    () -> assertNotSame(fPoint, fPointOp, "IFPoint references should be different"),
                    () -> assertSame(fPointOp, fPointRef, "The IFPoint reference should not change")
            );
        }

        @Test
        @DisplayName("Custom function - chain")
        void fun() {

            fPoint.cus(e -> e.addX(opX).addY(opY).addZ(opZ));

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Custom function - chain (validate)")
        void funValidate() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            HelperIFPoint.validateRef(e -> e.addX(opX).addY(opY).addZ(opZ), fPoint);
        }

        @Test
        @DisplayName("Custom function - value")
        void funVal() {

            assertEquals(refX + refY + refZ, fPoint.cusDouble(e -> e.getX() + e.getY() + e.getZ()),
                    "The resulting value is erroneous");
        }

        @Test
        @DisplayName("Custom function - value")
        void funLog() {

            assertTrue(fPoint.cusBoolean(e -> e.getX() != 0), "The resulting value is erroneous");
        }

    }

}

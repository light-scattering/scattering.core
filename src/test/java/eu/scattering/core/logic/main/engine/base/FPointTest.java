package eu.scattering.core.logic.main.engine.base;

import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.factory.MainFactory;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.base.helper.HelperFPoint;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static eu.scattering.core.Config.*;

@Timeout(5)
@DisplayName("IFPoint")
public class FPointTest {

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
            refX = RandomHelper.getTestValue();
            refY = RandomHelper.getTestValue();
            refZ = RandomHelper.getTestValue();
        }

        @Test
        @DisplayName("Construct")
        void construct() {
            FPoint fPoint = MainFactory.getIFPoint();

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
            FPoint fPoint = MainFactory.getIFPoint(refX);

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
            FPoint fPoint = MainFactory.getIFPoint(refX, refY);

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
            FPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ);

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
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPoint = MainFactory.getIFPoint(fPointRef);

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
            FPoint fPoint = MainFactory.getIFPoint();

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
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPoint = MainFactory.getIFPoint();

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
            FPoint fPoint = MainFactory.getIFPoint();

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
            FPoint fPoint = MainFactory.getIFPoint();

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
            FPoint fPoint = MainFactory.getIFPoint();

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
            FPoint fPoint = RandomHelper.getTestPoint();

            assertEquals(1, fPoint.normalize().getLength(),
                    jitter, "The magnitude of the normalized vector should be equal to one");
        }

        @Test
        @DisplayName("Normalize (throw DirectionException)")
        void normalizeThrowDirectionException() {
            FPoint fPoint = MainFactory.getIFPoint();

            assertThrows(DirectionException.class, fPoint::normalize,
                    "The IFPoints must not be on the same position");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::normalize, fPoint);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            FPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ).reflect();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(-refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::reflect, fPoint);
        }

        @Test
        @DisplayName("Reflect by IFPoint")
        void reflectByIFPoint() {
            double refAX = RandomHelper.getTestValue();
            double refAY = RandomHelper.getTestValue();
            double refAZ = RandomHelper.getTestValue();
            FPoint fPointA = MainFactory.getIFPoint(refAX, refAY, refAZ);

            double refBX = RandomHelper.getTestValue();
            double refBY = RandomHelper.getTestValue();
            double refBZ = RandomHelper.getTestValue();
            FPoint fPointB = MainFactory.getIFPoint(refBX, refBY, refBZ);

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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            HelperFPoint.validateRef(FPoint::reflect, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get length (axes: 1)")
        void getLengthAxes1() {
            double ref = RandomHelper.getTestValue();
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
            double ref = RandomHelper.getTestValue();
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
            double ref = RandomHelper.getTestValue();
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(FPoint::getLength, fPoint);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPoint;

            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            fPoint = MainFactory.getIFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(RandomHelper.getTestValue(magnitude));
            fPoint.setLength(magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (negative)")
        void setLengthNegative() {
            FPoint fPoint;

            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            fPoint = MainFactory.getIFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(RandomHelper.getTestValue(magnitude));
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.setLength(1), fPoint);
        }

        @Test
        @DisplayName("Set random angle (validate vector magnitude)")
        void setRandomAngleValidateMagnitude() {
            double radius = Math.abs(RandomHelper.getTestValue());

            FPoint fPoint = MainFactory.getIFPoint(radius).setRandomAngle();

            assertEquals(radius, fPoint.getLength(),
                    jitter, "The radius is invalid");
        }

        @Test
        @DisplayName("Set random angle (validate correctness)")
        void setRandomAngleValidateCorrectness() {
            double radius = Math.abs(RandomHelper.getTestValue());

            FPoint fPointA = MainFactory.getIFPoint(radius).setRandomAngle();
            FPoint fPointB = MainFactory.getIFPoint(radius).setRandomAngle(fPointA);

            assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
        }

        @Test
        @DisplayName("Set random angle (validate timeout)")
        void setRandomAngleValidateTimeout() {
            double radius = Math.abs(RandomHelper.getTestValue());
            FPoint fPoint = MainFactory.getIFPoint(radius);

            assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fPoint.setRandomAngle(fPoint));
        }

        @Test
        @DisplayName("Set random angle (validate)")
        void setRandomAngleValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::setRandomAngle, fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(FPoint::getInclination, fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(FPoint::getAzimuth, fPoint);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            FPoint fPointRef;

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
            FPoint fPointRef = MainFactory.getIFPoint(1, 0, 1).normalize().setInclination(angle);

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
            FPoint fPointRef = MainFactory.getIFPoint(1, 0, 1).normalize().setInclination(angle);

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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.setInclination(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            FPoint fPointRef;

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
            FPoint fPointRef = MainFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

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
            FPoint fPointRef = MainFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.setAzimuth(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            double radius = Math.abs(RandomHelper.getTestValue());

            double inclination = Math.abs(RandomHelper.getTestValue()) % Math.PI;
            double azimuth = Math.abs(RandomHelper.getTestValue()) % Math.PI;

            FPoint fPointRef = MainFactory.getIFPoint(radius).setSphericalCoordinates(inclination, azimuth);

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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {

            assertTrue(MainFactory.getIFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            FPoint fPointRef = MainFactory.getIFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(FPoint::isZero, fPoint);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            double result = fPointA.getDotProduct(fPointB);

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            assertEquals(dimX + dimY + dimZ, result, jitter, "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            HelperFPoint.validateVal(FPoint::getDotProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            double refAX = RandomHelper.getTestValue();
            double refAY = RandomHelper.getTestValue();
            double refAZ = RandomHelper.getTestValue();
            FPoint fPointA = MainFactory.getIFPoint(refAX, refAY, refAZ);

            double refBX = RandomHelper.getTestValue();
            double refBY = RandomHelper.getTestValue();
            double refBZ = RandomHelper.getTestValue();
            FPoint fPointB = MainFactory.getIFPoint(refBX, refBY, refBZ);

            FPoint fPointRes = fPointA.copy().setCrossProduct(fPointB);

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            FPoint fPointRef = MainFactory.getIFPoint(dimX, dimY, dimZ);

            assertTrue(fPointRes.isSimilar(fPointRef),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            HelperFPoint.validateRef(FPoint::setCrossProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointA = MainFactory.getIFPoint(2, 2, 0);
            FPoint fPointB = MainFactory.getIFPoint(4, -4, 0);

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
            FPoint fPointA = MainFactory.getIFPoint(2, 2, 2);
            FPoint fPointB = MainFactory.getIFPoint(4, 4, 4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FPoint fPointA = MainFactory.getIFPoint(2, 2, 2);
            FPoint fPointB = MainFactory.getIFPoint(-4, -4, -4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FPoint fPointA = MainFactory.getIFPoint(0, 1, 0);
            FPoint fPointB = RandomHelper.getTestPoint().setY(0);

            assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw DirectionException, input)")
        void getAngleThrowDirectionExceptionInput() {
            FPoint fPointA = MainFactory.getIFPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            assertThrows(DirectionException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the input IFPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (throw DirectionException, argument)")
        void getAngleThrowDirectionExceptionArgument() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = MainFactory.getIFPoint();

            assertThrows(DirectionException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the argument IFPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            HelperFPoint.validateVal(FPoint::getAngle, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            HelperFPoint.validateVal(FPoint::getDistance, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            fPointA.setDistance(fPointB, distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            fPointA.setDistance(fPointB, -distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    jitter, "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPoint fPointRefA = fPointA.copy().setDistance(fPointB, distance);
            FPoint fPointRefB = fPointA.copy().setDistance(fPointB, -distance);

            assertEquals(2 * distance, fPointRefA.getDistance(fPointRefB),
                    jitter, "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw DirectionException)")
        void setDistanceThrowDirectionException() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = fPointA.copy();

            assertThrows(DirectionException.class, () -> fPointA.setDistance(fPointB, 1),
                    "IFPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            HelperFPoint.validateRef((a, b) -> a.setDistance(b, 1), fPointA, fPointB);
        }

    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class ICoreFeatures {

        private double refX, refY, refZ;

        @BeforeEach
        void beforeAll() {
            refX = RandomHelper.getTestValue();
            refY = RandomHelper.getTestValue();
            refZ = RandomHelper.getTestValue();
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPointOp = MainFactory.getIFPoint().importFromJSON(fPointRef.exportToJSON());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPointOp = MainFactory.getIFPoint(refX, refY, refZ);

            assertAll("Check combinations",
                    () -> assertTrue(fPointRef.isExact(fPointOp), "IFPoints should be equal"),
                    () -> assertTrue(fPointOp.isExact(fPointRef), "IFPoints should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPointOp = MainFactory.getIFPoint(refX, refY, refZ).add(0.5 * jitter);

            assertAll("Check combinations",
                    () -> assertFalse(fPointRef.isExact(fPointOp), "IFPoints should not be equal"),
                    () -> assertFalse(fPointOp.isExact(fPointRef), "IFPoints should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);

            assertTrue(fPointRef.isExact(refX, refY, refZ), "IFPoint values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);

            assertFalse(fPointRef.isExact(0, 0, 0), "IFPoint values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(e -> e.isExact(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
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
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
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
            FPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ);

            assertTrue(fPoint.isSimilar(refX + (0.5 * jitter), refY + (0.5 * jitter), refZ + (0.5 * jitter)),
                    "IFPoint values should be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FPoint fPoint = MainFactory.getIFPoint(refX, refY, refZ);

            assertFalse(fPoint.isSimilar(refX + (1.5 * jitter), refY + (1.5 * jitter), refZ + (1.5 * jitter)),
                    "IFPoint values should not be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(e -> e.isSimilar(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FPoint fPointRefA = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPointRefB = MainFactory.getIFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.hashCode(), fPointRefB.hashCode(),
                    "Two identical IFPoints should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FPoint fPointRefA = MainFactory.getIFPoint(refX, refY, refZ);

            assertNotEquals(fPointRefA.hashCode(), MainFactory.getIFPoint().hashCode(),
                    "The different IFPoints should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(FPoint::hashCode, fPoint);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FPoint fPointRef = MainFactory.getIFPoint(refX, refY, refZ);
            FPoint fPoint = fPointRef.copy();

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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateVal(FPoint::copy, fPoint);
        }

    }

    @Nested
    @Tag("Algebra")
    @DisplayName("Base algebra")
    class IBaseAlgebra {

        private double refX, refY, refZ;
        private FPoint fPoint;
        private double opX, opY, opZ;

        @BeforeEach
        void beforeEach() {
            refX = RandomHelper.getTestValue();
            refY = RandomHelper.getTestValue();
            refZ = RandomHelper.getTestValue();

            fPoint = MainFactory.getIFPoint(refX, refY, refZ);

            opX = RandomHelper.getTestValue();
            opY = RandomHelper.getTestValue();
            opZ = RandomHelper.getTestValue();
        }

        @Test
        @DisplayName("Add IFPoint")
        void addIFPoint() {
            FPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::add, fPointA, fPointB);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.add(0, 0, 0), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.add(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.addX(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.addY(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.addZ(1), fPoint);
        }

        @Test
        @DisplayName("Sub IFPoint")
        void subIFPoint() {
            FPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::sub, fPointA, fPointB);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.sub(0, 0, 0), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.sub(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.subX(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.subY(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.subZ(1), fPoint);
        }

        @Test
        @DisplayName("Mul IFPoint")
        void mulIFPoint() {
            FPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::mul, fPointA, fPointB);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.mul(0, 0, 0), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.mul(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.mulX(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.mulY(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.mulZ(1), fPoint);
        }

        @Test
        @DisplayName("Div IFPoint")
        void divIFPoint() {
            FPoint fPointOp = MainFactory.getIFPoint(opX, opY, opZ);

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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(FPoint::mul, fPointA, fPointB);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.div(1, 1, 1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.div(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.divX(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.divY(1), fPoint);
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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.divZ(1), fPoint);
        }

        @Test
        @DisplayName("Get IFPoint list")
        void getIFPoints() {
            List<FPoint> list = fPoint.disassemble();

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
            FPoint fPointRef = MainFactory.getIFPoint();

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
            FPoint fPointOp = MainFactory.getIFPoint();

            FPoint fPointRef = fPointOp.imprint(fPoint);

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
            FPoint fPoint = RandomHelper.getTestPoint();

            HelperFPoint.validateRef(e -> e.addX(opX).addY(opY).addZ(opZ), fPoint);
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

package eu.scattering.core.design.engine.base;

import eu.scattering.core.Config;
import eu.scattering.core.injection.EngineFactory;
import eu.scattering.core.design.engine.base.point.FPoint;
import eu.scattering.core.design.engine.base.helper.FPointHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPoint")
public class FPointTest {

    static double angle08 = Math.PI * 0.00;
    static double angle18 = Math.PI * 0.25;
    static double angle28 = Math.PI * 0.50;
    static double angle38 = Math.PI * 0.75;
    static double angle48 = Math.PI * 1.00;

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FPointBase {

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
            FPoint fPoint = EngineFactory.getFPoint();

            assertNotNull(fPoint, "The instance is null");

            assertAll("Validate FPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with X")
        void constructWithX() {
            FPoint fPoint = EngineFactory.getFPoint(refX);

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
            FPoint fPoint = EngineFactory.getFPoint(refX, refY);

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
            FPoint fPoint = EngineFactory.getFPoint(refX, refY, refZ);

            assertNotNull(fPoint, "The instance is null");

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint")
        void constructWithFPoint() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            FPoint fPoint = EngineFactory.getFPoint(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created FPoints should be different");
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
            FPoint fPoint = EngineFactory.getFPoint();

            fPoint.set(refX, refY, refZ);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with an FPoint")
        void setWithFPoint() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            FPoint fPoint = EngineFactory.getFPoint();

            fPoint.set(fPointRef);

            assertNotSame(fPointRef, fPoint, "References to the two created FPoints should be different");

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set X")
        void setX() {
            FPoint fPoint = EngineFactory.getFPoint();

            fPoint.setX(refX);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set Y")
        void setY() {
            FPoint fPoint = EngineFactory.getFPoint();

            fPoint.setY(refY);

            assertAll("Validate FPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set Z")
        void setZ() {
            FPoint fPoint = EngineFactory.getFPoint();

            fPoint.setZ(refZ);

            assertAll("Validate FPoint values",
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
            FPoint fPoint = RandomHelper.getTestPoint();

            assertEquals(1, fPoint.normalize().getLength(),
                    Config.getJitter(), "The magnitude of the normalized vector should be equal to one");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {
            FPoint fPoint = EngineFactory.getFPoint();

            assertThrows(IllegalStateException.class, fPoint::normalize,
                    "The FPoints must not be on the same position");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::normalize, fPoint);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            FPoint fPoint = EngineFactory.getFPoint(refX, refY, refZ).reflect();

            assertAll("Validate FPoint values",
                    () -> assertEquals(-refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::reflect, fPoint);
        }

        @Test
        @DisplayName("Reflect by FPoint")
        void reflectByFPoint() {
            double refAX = RandomHelper.getTestValue();
            double refAY = RandomHelper.getTestValue();
            double refAZ = RandomHelper.getTestValue();
            FPoint fPointA = EngineFactory.getFPoint(refAX, refAY, refAZ);

            double refBX = RandomHelper.getTestValue();
            double refBY = RandomHelper.getTestValue();
            double refBZ = RandomHelper.getTestValue();
            FPoint fPointB = EngineFactory.getFPoint(refBX, refBY, refBZ);

            fPointA.reflect(fPointB);

            assertAll("Validate FPoint values",
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
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPointHelper.validateRef(FPoint::reflect, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get length (axes: 1)")
        void getLengthAxes1() {
            double ref = RandomHelper.getTestValue();
            double expected = Math.abs(ref);

            assertAll("Validate radius",
                    () -> assertEquals(expected, EngineFactory.getFPoint().setX(ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint().setX(-ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint().setY(ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [Y]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint().setY(-ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-Y]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint().setZ(ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint().setZ(-ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-Z]")
            );
        }

        @Test
        @DisplayName("Get length (axes: 2)")
        void getLengthAxes2() {
            double ref = RandomHelper.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(2));

            assertAll("Validate radius",
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, ref, 0).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, Y]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, -ref, 0).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, -Y]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, ref, 0).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, Y]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, -ref, 0).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, -Y]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, 0, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, 0, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, -Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, 0, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, 0, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, -Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(0, ref, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [Y, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(0, ref, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [Y, -Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(0, -ref, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-Y, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(0, -ref, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get length (axes: 3)")
        void getLengthAxes3() {
            double ref = RandomHelper.getTestValue();
            double expected = Math.abs(ref * Math.sqrt(3));

            assertAll("Validate radius",
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, ref, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, Y, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, ref, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, Y, -Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, -ref, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, -Y, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(ref, -ref, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [X, -Y, -Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, ref, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, Y, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, ref, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, Y, -Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, -ref, ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, -Y, Z]"),
                    () -> assertEquals(expected, EngineFactory.getFPoint(-ref, -ref, -ref).getLength(),
                            Config.getJitter(), "The magnitude is invalid [-X, -Y, -Z]")
            );
        }

        @Test
        @DisplayName("Get length (validate)")
        void getLengthValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(FPoint::getLength, fPoint);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPoint;

            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            fPoint = EngineFactory.getFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(RandomHelper.getTestValue(magnitude));
            fPoint.setLength(magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    Config.getJitter(), "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (negative)")
        void setLengthNegative() {
            FPoint fPoint;

            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            fPoint = EngineFactory.getFPoint(refX, refY, refZ);
            double magnitude = fPoint.getLength();

            double magnitudeExpected = Math.abs(RandomHelper.getTestValue(magnitude));
            fPoint.setLength(-magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getLength(),
                    Config.getJitter(), "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set length (throw IllegalStateException)")
        void setLengthThrowIllegalStateException() {

            assertThrows(IllegalStateException.class,
                    () -> EngineFactory.getFPoint().setLength(1),
                    "The position of the reference FPoint must not be zero");
        }

        @Test
        @DisplayName("Set length (validate)")
        void setLengthValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.setLength(1), fPoint);
        }

        @Test
        @DisplayName("Set random angle (validate vector magnitude)")
        void setRandomAngleValidateMagnitude() {
            double radius = Math.abs(RandomHelper.getTestValue());

            FPoint fPoint = EngineFactory.getFPoint(radius).setRandomAngle();

            assertEquals(radius, fPoint.getLength(),
                    Config.getJitter(), "The radius is invalid");
        }

        @Test
        @DisplayName("Set random angle (validate correctness)")
        void setRandomAngleValidateCorrectness() {
            double radius = Math.abs(RandomHelper.getTestValue());

            FPoint fPointA = EngineFactory.getFPoint(radius).setRandomAngle();
            FPoint fPointB = EngineFactory.getFPoint(radius).setRandomAngle(fPointA);

            assertNotEquals(fPointA, fPointB, "Two randomly generated points should be different");
        }

        @Test
        @DisplayName("Set random angle (validate timeout)")
        void setRandomAngleValidateTimeout() {
            double radius = Math.abs(RandomHelper.getTestValue());
            FPoint fPoint = EngineFactory.getFPoint(radius);

            assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fPoint.setRandomAngle(fPoint));
        }

        @Test
        @DisplayName("Set random angle (validate)")
        void setRandomAngleValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::setRandomAngle, fPoint);
        }

        @Test
        @DisplayName("Get inclination (constant azimuthal angle)")
        void getInclinationConstantAzimuthalAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle08, EngineFactory.getFPoint(0, 1, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(1, 1, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, EngineFactory.getFPoint(1, 0, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, EngineFactory.getFPoint(1, -1, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, EngineFactory.getFPoint(0, -1, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get inclination (variable azimuthal angle)")
        void getInclinationVariableAzimuthalAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle18, EngineFactory.getFPoint(1, 1, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [1,1,0]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(0, 1, 1).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [0,1,1]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(-1, 1, 0).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [-1,1,0]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(0, 1, -1).getInclination(),
                            Config.getJitter(), "The polar angle is incorrect [0,1,-1]")
            );
        }

        @Test
        @DisplayName("Get inclination (validate)")
        void getInclinationValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(FPoint::getInclination, fPoint);
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, positive values)")
        void getAzimuthConstantPolarAnglePositive() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle08, EngineFactory.getFPoint(1, 1, 0).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(1, 1, 1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, EngineFactory.getFPoint(0, 1, 1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, EngineFactory.getFPoint(-1, 1, 1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, EngineFactory.getFPoint(-1, 1, 0).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [4/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (constant polar angle, negative values)")
        void getAzimuthConstantPolarAngleNegative() {

            assertAll("Validate angle values",
                    () -> assertEquals(-angle18, EngineFactory.getFPoint(1, 1, -1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [-1/8 rad]"),
                    () -> assertEquals(-angle28, EngineFactory.getFPoint(0, 1, -1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [-2/8 rad]"),
                    () -> assertEquals(-angle38, EngineFactory.getFPoint(-1, 1, -1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [-3/8 rad]")
            );
        }

        @Test
        @DisplayName("Get azimuth (variable polar angle)")
        void getAzimuthVariablePolarAngle() {

            assertAll("Validate angle values",
                    () -> assertEquals(angle18, EngineFactory.getFPoint(1, 1, 1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [1,1,1]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(1, 0, 1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [1,0,0]"),
                    () -> assertEquals(angle18, EngineFactory.getFPoint(1, -1, 1).getAzimuth(),
                            Config.getJitter(), "The azimuthal angle is incorrect [1,-1,1]")
            );
        }

        @Test
        @DisplayName("Get azimuth (validate)")
        void getAzimuthValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(FPoint::getAzimuth, fPoint);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            FPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = EngineFactory.getFPoint(1, 0, 1).normalize().setInclination(angle);

                assertEquals(angle, fPointRef.getInclination(),
                        Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = EngineFactory.getFPoint(1, 0, 1).normalize().setInclination(-angle);

                assertEquals(angle, fPointRef.getInclination(),
                        Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set inclination (angle overflow)")
        void setInclinationOverflow() {
            double angle = 1.5 * Math.PI;
            FPoint fPointRef = EngineFactory.getFPoint(1, 0, 1).normalize().setInclination(angle);

            assertEquals(0.5 * Math.PI, fPointRef.getInclination(),
                    Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set inclination (full circle)")
        void setInclinationFull() {
            double angle = 2.0 * Math.PI;
            FPoint fPointRef = EngineFactory.getFPoint(1, 0, 1).normalize().setInclination(angle);

            assertEquals(0, fPointRef.getInclination(),
                    Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set inclination (validate)")
        void setInclinationValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.setInclination(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            FPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = EngineFactory.getFPoint(1, 1, 0).normalize().setAzimuth(angle);

                assertEquals(angle, fPointRef.getAzimuth(),
                        Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = EngineFactory.getFPoint(1, 1, 0).normalize().setAzimuth(-angle);

                assertEquals(-angle, fPointRef.getAzimuth(),
                        Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getLength(),
                        Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");
            }
        }

        @Test
        @DisplayName("Set azimuth (angle overflow)")
        void setAzimuthOverflow() {
            double angle = 1.5 * Math.PI;
            FPoint fPointRef = EngineFactory.getFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(-Math.PI * 0.5, fPointRef.getAzimuth(),
                    Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");
        }

        @Test
        @DisplayName("Set azimuth (full circle)")
        void setAzimuthFull() {
            double angle = 2.0 * Math.PI;
            FPoint fPointRef = EngineFactory.getFPoint(1, 1, 0).normalize().setAzimuth(angle);

            assertEquals(0, fPointRef.getAzimuth(),
                    Config.getJitter(), "The azimuthal angle is incorrect [" + angle + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    Config.getJitter(), "The polar angle is incorrect [" + angle + " rad]");
            assertEquals(1, fPointRef.getLength(),
                    Config.getJitter(), "The magnitude is incorrect [" + angle + " rad]");

        }

        @Test
        @DisplayName("Set azimuth (validate)")
        void setAzimuthValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.setAzimuth(Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            double radius = Math.abs(RandomHelper.getTestValue());

            double inclination = Math.abs(RandomHelper.getTestValue()) % Math.PI;
            double azimuth = Math.abs(RandomHelper.getTestValue()) % Math.PI;

            FPoint fPointRef = EngineFactory.getFPoint(radius).setSphericalCoordinates(inclination, azimuth);

            assertNotNull(fPointRef, "The instance is null");

            assertAll("Validate spherical coordinates",
                    () -> assertEquals(inclination, fPointRef.getInclination(),
                            Config.getJitter(), "The inclination is incorrect"),
                    () -> assertEquals(azimuth, fPointRef.getAzimuth(),
                            Config.getJitter(), "The azimuth is incorrect")
            );
        }

        @Test
        @DisplayName("Set spherical coordinates (validate)")
        void setSphericalCoordinatesValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5), fPoint);
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {

            assertTrue(EngineFactory.getFPoint().isZero(), "The reference point should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            double refX = RandomHelper.getTestValue();
            double refY = RandomHelper.getTestValue();
            double refZ = RandomHelper.getTestValue();

            FPoint fPointRef = EngineFactory.getFPoint().set(refX, refY, refZ);

            assertFalse(fPointRef.isZero(), "The reference point should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(FPoint::isZero, fPoint);
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

            assertEquals(dimX + dimY + dimZ, result, Config.getJitter(), "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPointHelper.validateVal(FPoint::getDotProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            double refAX = RandomHelper.getTestValue();
            double refAY = RandomHelper.getTestValue();
            double refAZ = RandomHelper.getTestValue();
            FPoint fPointA = EngineFactory.getFPoint(refAX, refAY, refAZ);

            double refBX = RandomHelper.getTestValue();
            double refBY = RandomHelper.getTestValue();
            double refBZ = RandomHelper.getTestValue();
            FPoint fPointB = EngineFactory.getFPoint(refBX, refBY, refBZ);

            FPoint fPointRes = fPointA.copy().setCrossProduct(fPointB);

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            FPoint fPointRef = EngineFactory.getFPoint(dimX, dimY, dimZ);

            assertTrue(fPointRes.isSimilar(fPointRef),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPointHelper.validateRef(FPoint::setCrossProduct, fPointA, fPointB);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointA = EngineFactory.getFPoint(2, 2, 0);
            FPoint fPointB = EngineFactory.getFPoint(4, -4, 0);

            assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                            Config.getJitter(), "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fPointB.getAngle(fPointA),
                            Config.getJitter(), "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            FPoint fPointA = EngineFactory.getFPoint(2, 2, 2);
            FPoint fPointB = EngineFactory.getFPoint(4, 4, 4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FPoint fPointA = EngineFactory.getFPoint(2, 2, 2);
            FPoint fPointB = EngineFactory.getFPoint(-4, -4, -4);

            assertEquals(0, fPointA.getAngle(fPointB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FPoint fPointA = EngineFactory.getFPoint(0, 1, 0);
            FPoint fPointB = RandomHelper.getTestPoint().setY(0);

            assertEquals(Math.PI * 0.5, fPointA.getAngle(fPointB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, input)")
        void getAngleThrowIllegalStateExceptionInput() {
            FPoint fPointA = EngineFactory.getFPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            assertThrows(IllegalStateException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the input FPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, argument)")
        void getAngleThrowIllegalStateExceptionArgument() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = EngineFactory.getFPoint();

            assertThrows(IllegalStateException.class, () -> fPointA.getAngle(fPointB),
                    "The direction of the argument FPoint is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPointHelper.validateVal(FPoint::getAngle, fPointA, fPointB);
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
                    Config.getJitter(), "The distance between FPoints is incorrect");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPointHelper.validateVal(FPoint::getDistance, fPointA, fPointB);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            fPointA.setDistance(fPointB, distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    Config.getJitter(), "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            fPointA.setDistance(fPointB, -distance);

            assertEquals(distance, fPointA.getDistance(fPointB),
                    Config.getJitter(), "The distance between FPoints is erroneous");
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
                    Config.getJitter(), "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = fPointA.copy();

            assertThrows(IllegalStateException.class, () -> fPointA.setDistance(fPointB, 1),
                    "FPoints cannot be at the same position");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint(fPointA);

            FPointHelper.validateRef((a, b) -> a.setDistance(b, 1), fPointA, fPointB);
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
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            FPoint fPointOp = EngineFactory.getFPoint().importFromJSON(fPointRef.exportToJSON());

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            FPoint fPointOp = EngineFactory.getFPoint(refX, refY, refZ);

            assertAll("Check combinations",
                    () -> assertTrue(fPointRef.isExact(fPointOp), "FPoints should be equal"),
                    () -> assertTrue(fPointOp.isExact(fPointRef), "FPoints should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            FPoint fPointOp = EngineFactory.getFPoint(refX, refY, refZ).add(0.5 * Config.getJitter());

            assertAll("Check combinations",
                    () -> assertFalse(fPointRef.isExact(fPointOp), "FPoints should not be equal"),
                    () -> assertFalse(fPointOp.isExact(fPointRef), "FPoints should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);

            assertTrue(fPointRef.isExact(refX, refY, refZ), "FPoint values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);

            assertFalse(fPointRef.isExact(0, 0, 0), "FPoint values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(e -> e.isExact(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            double ref = Config.getJitter() * 0.5;

            assertAll("Check combinations (true)",
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef)),
                            "FPoints should be similar (same position)"),
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).addX(ref)),
                            "FPoints should be similar (positive X)"),
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).subX(ref)),
                            "FPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).addY(ref)),
                            "FPoints should be similar (positive Y)"),
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).subY(ref)),
                            "FPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).addZ(ref)),
                            "FPoints should be similar (positive Z)"),
                    () -> assertTrue(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).subZ(ref)),
                            "FPoints should be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
            double ref = Config.getJitter() * 2;

            assertAll("Check combinations (false)",
                    () -> assertFalse(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).addX(ref)),
                            "FPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).subX(ref)),
                            "FPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).addY(ref)),
                            "FPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).subY(ref)),
                            "FPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).addZ(ref)),
                            "FPoints should not be similar (positive Z)"),
                    () -> assertFalse(fPointRef.isSimilar(EngineFactory.getFPoint().add(fPointRef).subZ(ref)),
                            "FPoints should not be similar (negative Z)")
            );
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            FPoint fPoint = EngineFactory.getFPoint(refX, refY, refZ);

            assertTrue(fPoint.isSimilar(refX + (0.5 * Config.getJitter()), refY + (0.5 * Config.getJitter()), refZ + (0.5 * Config.getJitter())),
                    "FPoint values should be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FPoint fPoint = EngineFactory.getFPoint(refX, refY, refZ);

            assertFalse(fPoint.isSimilar(refX + (1.5 * Config.getJitter()), refY + (1.5 * Config.getJitter()), refZ + (1.5 * Config.getJitter())),
                    "FPoint values should not be equal");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(e -> e.isSimilar(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FPoint fPointRefA = EngineFactory.getFPoint(refX, refY, refZ);
            FPoint fPointRefB = EngineFactory.getFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.hashCode(), fPointRefB.hashCode(),
                    "Two identical FPoints should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FPoint fPointRefA = EngineFactory.getFPoint(refX, refY, refZ);

            assertNotEquals(fPointRefA.hashCode(), EngineFactory.getFPoint().hashCode(),
                    "The different FPoints should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateVal(FPoint::hashCode, fPoint);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FPoint fPointRef = EngineFactory.getFPoint(refX, refY, refZ);
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

            FPointHelper.validateVal(FPoint::copy, fPoint);
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

            fPoint = EngineFactory.getFPoint(refX, refY, refZ);

            opX = RandomHelper.getTestValue();
            opY = RandomHelper.getTestValue();
            opZ = RandomHelper.getTestValue();
        }

        @Test
        @DisplayName("Add FPoint")
        void addFPoint() {
            FPoint fPointOp = EngineFactory.getFPoint(opX, opY, opZ);

            fPoint.add(fPointOp);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add FPoint (validate)")
        void addFPointValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::add, fPointA, fPointB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fPoint.add(opX, opY, opZ);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.add(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opX * opY * opZ;

            fPoint.add(op);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX + op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.add(1), fPoint);
        }

        @Test
        @DisplayName("Add X")
        void addX() {

            fPoint.addX(opX);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add X (validate)")
        void addXValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.addX(1), fPoint);
        }

        @Test
        @DisplayName("Add Y")
        void addY() {

            fPoint.addY(opY);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.addY(1), fPoint);
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {

            fPoint.addZ(opZ);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.addZ(1), fPoint);
        }

        @Test
        @DisplayName("Sub FPoint")
        void subFPoint() {
            FPoint fPointOp = EngineFactory.getFPoint(opX, opY, opZ);

            fPoint.sub(fPointOp);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FPoint (validate)")
        void subFPointValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::sub, fPointA, fPointB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fPoint.sub(opX, opY, opZ);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.sub(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opX * opY * opZ;

            fPoint.sub(op);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX - op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.sub(1), fPoint);
        }

        @Test
        @DisplayName("Sub X")
        void subX() {

            fPoint.subX(opX);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX - opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.subX(1), fPoint);
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {

            fPoint.subY(opY);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY - opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.subY(1), fPoint);
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {

            fPoint.subZ(opZ);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ - opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Z (validate)")
        void subZValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.subZ(1), fPoint);
        }

        @Test
        @DisplayName("Mul FPoint")
        void mulFPoint() {
            FPoint fPointOp = EngineFactory.getFPoint(opX, opY, opZ);

            fPoint.mul(fPointOp);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FPoint (validate)")
        void mulFPointValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::mul, fPointA, fPointB);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fPoint.mul(opX, opY, opZ);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.mul(0, 0, 0), fPoint);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opX * opY * opZ;

            fPoint.mul(op);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX * op, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * op, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * op, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.mul(1), fPoint);
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {

            fPoint.mulX(opX);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX * opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.mulX(1), fPoint);
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {

            fPoint.mulY(opY);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY * opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.mulY(1), fPoint);
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {

            fPoint.mulZ(opZ);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ * opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.mulZ(1), fPoint);
        }

        @Test
        @DisplayName("Div FPoint")
        void divFPoint() {
            FPoint fPointOp = EngineFactory.getFPoint(opX, opY, opZ);

            fPoint.div(fPointOp);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX / opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY / opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ / opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Div FPoint (throw ArithmeticException)")
        void divFPointThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(EngineFactory.getFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(EngineFactory.getFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fPoint.div(EngineFactory.getFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div FPoint (validate)")
        void divFPointValidate() {
            FPoint fPointA = RandomHelper.getTestPoint();
            FPoint fPointB = RandomHelper.getTestPoint();

            FPointHelper.validateRef(FPoint::mul, fPointA, fPointB);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fPoint.div(opX, opY, opZ);

            assertAll("Validate FPoint values",
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

            FPointHelper.validateRef(e -> e.div(1, 1, 1), fPoint);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opX * opY * opZ;

            fPoint.div(op);

            assertAll("Validate FPoint values",
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

            FPointHelper.validateRef(e -> e.div(1), fPoint);
        }

        @Test
        @DisplayName("Div X")
        void divX() {

            fPoint.divX(opX);

            assertAll("Validate FPoint values",
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

            FPointHelper.validateRef(e -> e.divX(1), fPoint);
        }

        @Test
        @DisplayName("Div Y")
        void divY() {

            fPoint.divY(opY);

            assertAll("Validate FPoint values",
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

            FPointHelper.validateRef(e -> e.divY(1), fPoint);
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {

            fPoint.divZ(opZ);

            assertAll("Validate FPoint values",
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

            FPointHelper.validateRef(e -> e.divZ(1), fPoint);
        }

        @Test
        @DisplayName("Get FPoint list")
        void getFPoints() {
            List<FPoint> list = fPoint.disassemble();

            assertAll("Validate FPoint list",
                    () -> assertEquals(1, list.size(), "The size of the list is incorrect"),
                    () -> assertEquals(refX, list.get(0).getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, list.get(0).getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, list.get(0).getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FPoint fPointRef = EngineFactory.getFPoint();

            fPoint.imprint(fPointRef);

            assertAll("Validate FPoint values",
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
            FPoint fPointOp = EngineFactory.getFPoint();

            FPoint fPointRef = fPointOp.imprint(fPoint);

            assertAll("Validate references",
                    () -> assertNotSame(fPoint, fPointOp, "FPoint references should be different"),
                    () -> assertSame(fPointOp, fPointRef, "The FPoint reference should not change")
            );
        }

        @Test
        @DisplayName("Custom function - chain")
        void fun() {

            fPoint.cus(e -> e.addX(opX).addY(opY).addZ(opZ));

            assertAll("Validate FPoint values",
                    () -> assertEquals(refX + opX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY + opY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ + opZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Custom function - chain (validate)")
        void funValidate() {
            FPoint fPoint = RandomHelper.getTestPoint();

            FPointHelper.validateRef(e -> e.addX(opX).addY(opY).addZ(opZ), fPoint);
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

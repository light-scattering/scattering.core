package eu.scattering.core.geometry;

import eu.scattering.core.factory.GeometryFactory;
import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.d0.IFPoint;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static eu.scattering.core.Configuration.*;

@DisplayName("IFPoint")
public class IFPointTest {

    static double angle08 = Math.PI * 0.00;
    static double angle18 = Math.PI * 0.25;
    static double angle28 = Math.PI * 0.50;
    static double angle38 = Math.PI * 0.75;
    static double angle48 = Math.PI * 1.00;

    @Nested
    @Timeout(5)
    @DisplayName("Basic functionality")
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
        void create() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            assertNotNull(fPoint, "The instance is null");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Constructor with parameters")
        void createWithParameters() {
            IFPoint fPoint = GeometryFactory.getIFPoint(refX, refY, refZ);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set values using primitives")
        void setPrimitives() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.set(refX, refY, refZ);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Set values using an IFPoint")
        void setIFPoint() {
            IFPoint fPoint = GeometryFactory.getIFPoint();
            IFPoint position = GeometryFactory.getIFPoint(refX, refY, refZ);

            fPoint.set(position);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Set values using an IFPoint (NullPointerException)")
        void resetWithIFPointThrowNullPointerException() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            assertThrows(NullPointerException.class, () -> fPoint.set(null), "The reference cannot be null" );

        }

        @Test
        @DisplayName("Modify X")
        void modifyX() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.setX(refX);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Modify Y")
        void modifyY() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.setY(refY);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Modify Z")
        void modifyZ() {
            IFPoint fPoint = GeometryFactory.getIFPoint();

            fPoint.setZ(refZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                    () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPoint.getZ(), "The Z value is incorrect")
            );

        }

    }

    @Nested
    @Timeout(5)
    @DisplayName("Advanced functionality")
    class IFPointAdvanced {

        @Test
        @DisplayName("Get radius")
        void getRadius() {
            double ref = HelperRandom.getTestValue();
            double resultSqrt1 = Math.abs(ref);

            assertAll("Validate radius (basic)",
                    () -> assertEquals(resultSqrt1, GeometryFactory.getIFPoint().setX(ref).getRadius(),
                            jitter, "The magnitude is invalid (positive X)"),
                    () -> assertEquals(resultSqrt1, GeometryFactory.getIFPoint().setX(-ref).getRadius(),
                            jitter, "The magnitude is invalid (negative X)"),
                    () -> assertEquals(resultSqrt1, GeometryFactory.getIFPoint().setY(ref).getRadius(),
                            jitter, "The magnitude is invalid (positive Y)"),
                    () -> assertEquals(resultSqrt1, GeometryFactory.getIFPoint().setY(-ref).getRadius(),
                            jitter, "The magnitude is invalid (negative Y)"),
                    () -> assertEquals(resultSqrt1, GeometryFactory.getIFPoint().setZ(ref).getRadius(),
                            jitter, "The magnitude is invalid (positive Z)"),
                    () -> assertEquals(resultSqrt1, GeometryFactory.getIFPoint().setZ(-ref).getRadius(),
                            jitter, "The magnitude is invalid (negative Z)")
            );

            double resultSqrt2 = Math.abs(ref * Math.sqrt(2));

            assertAll("Validate radius (advanced)",
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(ref, ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [X, Y]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(ref, -ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(-ref, ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(-ref, -ref, 0).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(ref, 0, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(ref, 0, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(-ref, 0, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(-ref, 0, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(0, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [Y, Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(0, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [Y, -Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(0, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y, Z]"),
                    () -> assertEquals(resultSqrt2, GeometryFactory.getIFPoint().set(0, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-Y, -Z]")
            );

            double resultSqrt3 = Math.abs(ref * Math.sqrt(3));

            assertAll("Validate radius (advanced)",
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(ref, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Y, Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(ref, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, Y, -Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(ref, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y, Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(ref, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [X, -Y, -Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(-ref, ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y, Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(-ref, ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, Y, -Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(-ref, -ref, ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y, Z]"),
                    () -> assertEquals(resultSqrt3, GeometryFactory.getIFPoint().set(-ref, -ref, -ref).getRadius(),
                            jitter, "The magnitude is invalid [-X, -Y, -Z]")
            );

        }

        @Test
        @DisplayName("Set radius")
        void setRadius() {

            assertThrows(IllegalArgumentException.class,
                    () -> GeometryFactory.getIFPoint().set(1, 1, 1).setRadius(-1),
                    "It should not be possible to set negative radius");

            assertThrows(SamePositionException.class,
                    () ->GeometryFactory.getIFPoint().setRadius(1),
                    "The position of the IFPoint must not be zero (the vector points to an unknown direction");

            IFPoint fPoint;

            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            fPoint = GeometryFactory.getIFPoint().set(refX, refY, refZ);
            double magnitudeCurrent = fPoint.getRadius();

            double magnitudeExpected = Math.abs(HelperRandom.getTestValue(magnitudeCurrent));
            fPoint.setRadius(magnitudeExpected);

            assertEquals(magnitudeExpected, fPoint.getRadius(),
                    jitter, "The magnitude of the vector is incorrect");
        }

        @Test
        @DisplayName("Set random")
        void setRandom() {
            IFPoint fPointRefA = GeometryFactory.getIFPoint().setRandom();
            IFPoint fPointRefB = GeometryFactory.getIFPoint().setRandom(fPointRefA);

            assertAll("Validate magnitude",
                    () -> assertEquals(1, fPointRefA.getRadius(),
                            jitter, "The radius is invalid"),
                    () -> assertEquals(1, fPointRefB.getRadius(),
                            jitter, "The radius is invalid")
            );

            assertNotEquals(fPointRefA, fPointRefB, "Two randomly generated points should be different");
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ);

            assertEquals(1, fPointRef.normalize().getRadius(),
                    jitter, "The magnitude of the normalized vector should be one");
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            double refX = HelperRandom.getTestValue();
            double refY = HelperRandom.getTestValue();
            double refZ = HelperRandom.getTestValue();

            IFPoint fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ).reflect();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(-refX, fPointRef.getX(), "The X value is incorrect"),
                    () -> assertEquals(-refY, fPointRef.getY(), "The Y value is incorrect"),
                    () -> assertEquals(-refZ, fPointRef.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get inclination")
        void getInclination() {

            assertAll("Validate angle values (const azimuthal angle)",
                    () -> assertEquals(angle08, GeometryFactory.getIFPoint(0, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, GeometryFactory.getIFPoint(1, 0, 0).getInclination(),
                            jitter, "The polar angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, GeometryFactory.getIFPoint(1, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, GeometryFactory.getIFPoint(0, -1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [4/8 rad]")
            );

            assertAll("Validate angle values (const polar angle)",
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [1,1,0]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(0, 1, 1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,1]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(-1, 1, 0).getInclination(),
                            jitter, "The polar angle is incorrect [-1,1,0]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(0, 1, -1).getInclination(),
                            jitter, "The polar angle is incorrect [0,1,-1]")
            );
        }

        @Test
        @DisplayName("Get azimuth")
        void getAzimuth() {

            assertAll("Validate angle values (const polar angle, positive)",
                    () -> assertEquals(angle08, GeometryFactory.getIFPoint(1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0/8 rad]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1/8 rad]"),
                    () -> assertEquals(angle28, GeometryFactory.getIFPoint(0, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [2/8 rad]"),
                    () -> assertEquals(angle38, GeometryFactory.getIFPoint(-1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [3/8 rad]"),
                    () -> assertEquals(angle48, GeometryFactory.getIFPoint(-1, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [4/8 rad]")
            );

            assertAll("Validate angle values (const polar angle, negative)",
                    () -> assertEquals(-angle18, GeometryFactory.getIFPoint(1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-1/8 rad]"),
                    () -> assertEquals(-angle28, GeometryFactory.getIFPoint(0, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-2/8 rad]"),
                    () -> assertEquals(-angle38, GeometryFactory.getIFPoint(-1, 1, -1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [-3/8 rad]")
            );

            assertAll("Validate angle values (const azimuthal angle)",
                    () -> assertEquals(angle08, GeometryFactory.getIFPoint(0, 1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0,1,0]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(1, 1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,1,1]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(1, 0, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,0,0]"),
                    () -> assertEquals(angle18, GeometryFactory.getIFPoint(1, -1, 1).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [1,-1,1]"),
                    () -> assertEquals(angle08, GeometryFactory.getIFPoint(0, -1, 0).getAzimuth(),
                            jitter, "The azimuthal angle is incorrect [0,-1,0]")
            );

        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            IFPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = GeometryFactory.getIFPoint(1, 0, 1).normalize().setInclination(angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = GeometryFactory.getIFPoint(1, 0, 1).normalize().setInclination(-angle);

                assertEquals(angle, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }

            double angleA = 1.5 * Math.PI;
            fPointRef = GeometryFactory.getIFPoint(1, 0, 1).normalize().setInclination(angleA);

            assertEquals(0.5 * Math.PI, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angleA + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    jitter, "The azimuthal angle is incorrect [" + angleA + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angleA + " rad]");

            double angleB = 2.0 * Math.PI;
            fPointRef = GeometryFactory.getIFPoint(1, 0, 1).normalize().setInclination(angleB);

            assertEquals(0, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angleB + " rad]");
            assertEquals(0, fPointRef.getX() - fPointRef.getZ(),
                    jitter, "The azimuthal angle is incorrect [" + angleB + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angleB + " rad]");
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            IFPoint fPointRef;

            for (double angle = 0 ; angle < Math.PI ; angle += Math.PI * 0.1) {
                fPointRef = GeometryFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angle);

                assertEquals(angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");

                fPointRef = GeometryFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(-angle);

                assertEquals(-angle, fPointRef.getAzimuth(),
                        jitter, "The azimuthal angle is incorrect [" + angle + " rad]");
                assertEquals(angle18, fPointRef.getInclination(),
                        jitter, "The polar angle is incorrect [" + angle + " rad]");
                assertEquals(1, fPointRef.getRadius(),
                        jitter, "The magnitude is incorrect [" + angle + " rad]");
            }

            double angleA = 1.5 * Math.PI;
            fPointRef = GeometryFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angleA);

            assertEquals(-Math.PI * 0.5, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angleA + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angleA + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angleA + " rad]");

            double angleB = 2.0 * Math.PI;
            fPointRef = GeometryFactory.getIFPoint(1, 1, 0).normalize().setAzimuth(angleB);

            assertEquals(0, fPointRef.getAzimuth(),
                    jitter, "The azimuthal angle is incorrect [" + angleB + " rad]");
            assertEquals(angle18, fPointRef.getInclination(),
                    jitter, "The polar angle is incorrect [" + angleB + " rad]");
            assertEquals(1, fPointRef.getRadius(),
                    jitter, "The magnitude is incorrect [" + angleB + " rad]");
        }

    }

    @Nested
    @Timeout(5)
    @DisplayName("Object")
    class IBaseObject {

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
            IFPoint fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = GeometryFactory.getIFPoint().importFromJSON(fPointRef.exportToJSON());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refX, fPointOp.getX(), "The X value is incorrect"),
                    () -> assertEquals(refY, fPointOp.getY(), "The Y value is incorrect"),
                    () -> assertEquals(refZ, fPointOp.getZ(), "The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Is exact")
        void isExact() {
            IFPoint fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = GeometryFactory.getIFPoint(refX, refY, refZ);

            assertAll("Check combinations",
                    () -> assertTrue(fPointRef.isExact(fPointOp), "IFPoints should be equal"),
                    () -> assertTrue(fPointOp.isExact(fPointRef), "IFPoints should be equal")
            );

            assertThrows(NullPointerException.class,
                    () -> fPointRef.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Is similar")
        void isSimilar() {
            IFPoint fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ);

            double refA = jitter * 0.5;

            assertAll("Check combinations (true)",
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef)),
                            "IFPoints should be similar (same position)"),
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).addX(refA)),
                            "IFPoints should be similar (positive X)"),
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).subX(refA)),
                            "IFPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).addY(refA)),
                            "IFPoints should be similar (positive Y)"),
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).subY(refA)),
                            "IFPoints should be similar (negative X)"),
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).addZ(refA)),
                            "IFPoints should be similar (positive Z)"),
                    () -> assertTrue(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).subZ(refA)),
                            "IFPoints should be similar (negative Z)")
            );

            double refB = jitter * 2;

            assertAll("Check combinations (false)",
                    () -> assertFalse(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).addX(refB)),
                            "IFPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).subX(refB)),
                            "IFPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).addY(refB)),
                            "IFPoints should not be similar (positive X)"),
                    () -> assertFalse(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).subY(refB)),
                            "IFPoints should not be similar (negative X)"),
                    () -> assertFalse(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).addZ(refB)),
                            "IFPoints should not be similar (positive Z)"),
                    () -> assertFalse(fPointRef.isSimilar(GeometryFactory.getIFPoint().add(fPointRef).subZ(refB)),
                            "IFPoints should not be similar (negative Z)")
            );

        }

        @Test
        @DisplayName("Validate hash code")
        void validateHashCode() {
            IFPoint fPointRefA = GeometryFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointRefB = GeometryFactory.getIFPoint(refX, refY, refZ);

            assertEquals(fPointRefA.getHashCode(), fPointRefB.getHashCode(),
                    "Two identical points should have the same hash code");

            assertNotEquals(fPointRefA.getHashCode(), GeometryFactory.getIFPoint().getHashCode(),
                    "The different points should not have the same hash code");

        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFPoint fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ);
            IFPoint fPointOp = fPointRef.copy();

            assertAll("Validate similarity",
                    () -> assertNotSame(fPointRef, fPointOp,
                            "FPoints represent different objects"),
                    () -> assertEquals(fPointRef, fPointOp,
                            "FPoints should have the same values"),
                    () -> assertNotEquals(fPointRef, fPointOp.add(fPointRef),
                            "FPoints should have different values")
            );
        }

    }

    @Nested
    @DisplayName("Algebra")
    class IBaseAlgebra {

        private double refX, refY, refZ;
        private IFPoint fPointRef;
        private double opX, opY, opZ;

        @BeforeEach
        void beforeEach() {
            refX = HelperRandom.getTestValue();
            refY = HelperRandom.getTestValue();
            refZ = HelperRandom.getTestValue();

            fPointRef = GeometryFactory.getIFPoint(refX, refY, refZ);

            opX = HelperRandom.getTestValue();
            opY = HelperRandom.getTestValue();
            opZ = HelperRandom.getTestValue();
        }

        @Nested
        @DisplayName("Addition")
        class Addition {

            @Test
            @DisplayName("Add IFPoint")
            void addIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint(opX, opY, opZ);

                fPointRef.add(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX + opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY + opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ + opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Add IFPoint (NullPointerException)")
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

        }

        @Nested
        @DisplayName("Subtraction")
        class Subtraction {

            @Test
            @DisplayName("Sub IFPoint")
            void subIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint(opX, opY, opZ);

                fPointRef.sub(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX - opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY - opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ - opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Sub IFPoint (NullPointerException)")
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

        }

        @Nested
        @DisplayName("Multiplication")
        class Multiplication {

            @Test
            @DisplayName("Mul IFPoint")
            void mulIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint(opX, opY, opZ);

                fPointRef.mul(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX * opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY * opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ * opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Mul IFPoint (NullPointerException)")
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

        }

        @Nested
        @DisplayName("Division")
        class Division {

            @Test
            @DisplayName("Div IFPoint")
            void divIFPoint() {
                IFPoint fPointOp = GeometryFactory.getIFPoint(opX, opY, opZ);

                fPointRef.div(fPointOp);

                assertAll("Validate IFPoint values",
                        () -> assertEquals(refX / opX, fPointRef.getX(), "The X value is incorrect"),
                        () -> assertEquals(refY / opY, fPointRef.getY(), "The Y value is incorrect"),
                        () -> assertEquals(refZ / opZ, fPointRef.getZ(), "The Z value is incorrect")
                );
            }

            @Test
            @DisplayName("Div IFPoint (ArithmeticException)")
            void divIFPointThrowArithmeticException() {

                assertAll("Division by zero",
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(GeometryFactory.getIFPoint(0, 1, 1)),
                                "The X value is zero"),
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(GeometryFactory.getIFPoint(1, 0, 1)),
                                "The Y value is zero"),
                        () -> assertThrows(ArithmeticException.class,
                                () -> fPointRef.div(GeometryFactory.getIFPoint(0, 1, 1)),
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
            @DisplayName("Div primitives (ArithmeticException)")
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
            @DisplayName("Div X (ArithmeticException)")
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
            @DisplayName("Div Y (ArithmeticException)")
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

        }

        @Nested
        @DisplayName("Other")
        class Other {

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
            @DisplayName("IFPoint list")
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

}

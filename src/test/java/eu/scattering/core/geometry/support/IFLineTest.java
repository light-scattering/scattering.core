package eu.scattering.core.geometry.support;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.geometry.support.plane.IFPlane;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import static eu.scattering.core.Configuration.jitter;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFLine")
public class IFLineTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFLineBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            IFLine fLine = FactoryGeometry.getIFLine();

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate positions)")
        void constructValidatePositions() {
            IFLine fLine = FactoryGeometry.getIFLine();

            assertEquals(FactoryGeometry.getIFVector(), fLine.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector (validate positions)")
        void constructWithIFVectorValidatePositions() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointBase = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refAX, fLine.getOrigin().getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fLine.getOrigin().getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fLine.getOrigin().getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fLine.getOrigin().getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fLine.getOrigin().getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fLine.getOrigin().getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFVector (throw NullPointerException)")
        void constructWithIFVectorThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> FactoryGeometry.getIFLine(null),
                    "The reference cannot be null" );
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            IFLine fLine = FactoryGeometry.getIFLine(fVectorA);

            IFLine fLineRef = fLine.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            IFLine fLine = FactoryGeometry.getIFLine(HelperRandom.getTestVector());

            assertThrows(NullPointerException.class, () -> fLine.setOriginRef(null),
                    "The reference cannot be null" );
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());

            assertEquals(fVector, fLine.getOrigin(), "The IFVector positions are erroneous");
        }

    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = FactoryGeometry.getIFLine().importFromJSON(fLineA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fLineA, fLineB,
                            "IFLine references should point at different objects"),
                    () -> assertEquals(fLineA.getOrigin(), fLineB.getOrigin(),
                            "The origin of IFLines should be exact")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isExact(fLineB), "IFLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "IFLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "IFLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "IFLines should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (throw NullPointerException)")
        void isExactThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertThrows(NullPointerException.class,
                    () -> fLine.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(1.5 * jitter).setOrthogonal(fVector));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "IFLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "IFLines should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head)")
        void isSimilarAboveHead() {
            IFVector fVector = HelperRandom.getTestVector().normalize();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            fLineB.getOrigin().moveForward(10);

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head, invert)")
        void isSimilarAboveHeadInvert() {
            IFVector fVector = HelperRandom.getTestVector().normalize();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            fLineB.getOrigin().moveForward(10).reflectHead();

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base)")
        void isSimilarBelowBase() {
            IFVector fVector = HelperRandom.getTestVector().normalize();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            fLineB.getOrigin().moveBackward(10);

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base, invert)")
        void isSimilarBelowBaseInvert() {
            IFVector fVector = HelperRandom.getTestVector().normalize();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            fLineB.getOrigin().moveBackward(10).reflectHead();

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            assertEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two identical IFLines should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = FactoryGeometry.getIFLine(HelperRandom.getTestVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different IFLines should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = fLineA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "IFLines represent different objects"),
                    () -> assertEquals(fLineA, fLineB,
                            "IFLines should have the same values")
            );
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFLineAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project());

            assertTrue(FactoryGeometry.getIFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (validate references)")
        void projectValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.project());

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Project (validate positions)")
        void projectValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.project());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Project (position base)")
        void projectPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project (position head)")
        void projectPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect());

            assertTrue(FactoryGeometry.getIFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate references)")
        void reflectValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.reflect());

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect (validate positions)")
        void reflectValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Reflect (position base)")
        void reflectPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect (position head)")
        void reflectPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fPoint.extLog(fLine.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fPoint.extLog(fLine.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (validate positions)")
        void isPartOfValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 1, 1).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Location (position base)")
        void isPartOfPositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(-4, -4, -4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location (position head)")
        void isPartOfPositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, 4, 4).addY(0.5 * jitter);

            fPoint.extLog(fLine.isPartOf());
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extVal(fLine.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate positions)")
        void getDistanceValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.extVal(fLine.getDistance());

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Get distance (position base)")
        void getDistancePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.extVal(fLine.getDistance());
        }

        @Test
        @DisplayName("Distance (position head)")
        void getDistancePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.extVal(fLine.getDistance());
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1));

            assertTrue(Math.abs(fPoint.extVal(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (position base)")
        void setDistancePositionBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance (position head)")
        void setDistancePositionHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance (validate positions)")
        void setDistanceValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fLine.setDistance(1));

            assertEquals(fVector, fLine.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Set distance (validate references)")
        void setDistanceValidateReferences() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fLine.setDistance(1));

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Set distance (throw IllegalArgumentException")
        void setDistanceThrowIllegalArgumentException() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(2, 2, 2));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            assertThrows(IllegalArgumentException.class, () -> fPoint.ext(fLine.setDistance(-1)),
                    "The distance must be positive");
        }

        @Test
        @DisplayName("Project on ray")
        void isProjectableOnRay() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            assertTrue(fPoint.extLog(fLine.isPartOfRay()).get(0),
                    "The IFPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (below base)")
        void isProjectableOnRayBelowBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertFalse(fPoint.extLog(fLine.isPartOfRay()).get(0),
                    "The IFPoint is not a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (over head)")
        void isProjectableOnRayOverHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            assertTrue(fPoint.extLog(fLine.isPartOfRay()).get(0),
                    "The IFPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on segment")
        void isProjectableOnSegment() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint();

            assertTrue(fPoint.extLog(fLine.isPartOfSegment()).get(0),
                    "The IFPoint is a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (below base)")
        void isProjectableOnSegmentBelowBase() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, -9, 0);

            assertFalse(fPoint.extLog(fLine.isPartOfSegment()).get(0),
                    "The IFPoint is not a of the segment");
        }

        @Test
        @DisplayName("Project on segment (over head)")
        void isProjectableOnSegmentOverHead() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 9, 0);

            assertFalse(fPoint.extLog(fLine.isPartOfSegment()).get(0),
                    "The IFPoint is not a part of the segment");
        }

        @Test
        @DisplayName("Move forward")
        void moveForward() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 0, 0);

            fPoint.ext(fLine.moveForward(Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(FactoryGeometry.getIFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (negative)")
        void moveForwardNegative() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 0, 0);

            fPoint.ext(fLine.moveForward(-Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(FactoryGeometry.getIFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 0, 0);

            fPoint.ext(fLine.moveBackward(Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(FactoryGeometry.getIFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (negative)")
        void moveBackwardNegative() {
            IFVector fVector = FactoryGeometry.getIFVector(4, 4, 4).sub(2);
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(1, 0, 0);

            fPoint.ext(fLine.moveBackward(-Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(FactoryGeometry.getIFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Get intersecting point")
        void getIntersectingPoint() {
            IFLine fLineA = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(1, 0, 0));
            IFLine fLineB = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(-1, 1, 0, 1, 1, 0));

            fLineA.getIntersectingIFPoint(fLineB);
        }

        @Test
        @DisplayName("Get intersecting point (throw IllegalArgumentException)")
        void getIntersectingPointParallel() {
            IFLine fLineA = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(1, 1, 1));
            IFLine fLineB = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(-1, -1, -1));

            assertThrows(IllegalArgumentException.class, () -> fLineA.getIntersectingIFPoint(fLineB),
                    "IFLines are parallel, an exception should be thrown");
        }

    }
}

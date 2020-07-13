package eu.scattering.core.geometry.support;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.util.Objects;
import java.util.Optional;

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
                    () -> assertEquals(refAX, fLine.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fLine.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fLine.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fLine.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fLine.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fLine.getHead().getZ(),
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
            IFLine fLineA = FactoryGeometry.getIFLine(HelperRandom.getTestVector());
            IFLine fLineB = FactoryGeometry.getIFLine(HelperRandom.getTestVector(fLineA.getOrigin()));

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
        @DisplayName("Get IFPoint")
        void getIFPoint() {
            IFLine fLine = FactoryGeometry.getIFLine(HelperRandom.getTestVector());
            double length = fLine.getOrigin().getLength();

            assertAll("Validate IFPoint",
                    () -> assertTrue(fLine.getIFPoint(0).isSimilar(fLine.getBase()),
                            "The IFPoint base is incorrect"),
                    () -> assertTrue(fLine.getIFPoint(length).isSimilar(fLine.getHead()),
                            "The IFPoint head is incorrect"),
                    () -> assertTrue(fLine.getIFPoint(-length).isSimilar(fLine.getOrigin().reflectHead().getHead()),
                            "The IFPoint inverse head is incorrect")
            );
        }

        @Test
        @DisplayName("Get IFPoint (validate positions)")
        void getIFPointValidatePositions() {
            IFVector fVectorOrigin = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVectorOrigin.copy());

            fLine.getIFPoint(0);

            assertEquals(fVectorOrigin, fLine.getOrigin(), "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get IFPoint at X")
        void getIFPointAtX() {
            IFPoint base = HelperRandom.getTestPoint();
            IFPoint head = HelperRandom.getTestPoint(base);
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(base.copy(), head.copy()));

            assertAll("Validate IFPoint",
                    () -> assertTrue(fLine.getIFPointAtX(0).isPresent(),
                            "The IFPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getIFPointAtX(base.getX()).orElse(null))
                                    .isSimilar(fLine.getBase()),
                            "The IFPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getIFPointAtX(head.getX()).orElse(null))
                                    .isSimilar(fLine.getHead()),
                            "The IFPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get IFPoint at X (empty)")
        void getIFPointAtXEmpty() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(0, 1, 2));

            assertTrue(fLine.getIFPointAtX(0).isEmpty(),
                    "The IFPoint should not be available");
        }

        @Test
        @DisplayName("Get IFPoint at X (validate positions)")
        void getIFPointAtXValidatePositions() {
            IFVector fVectorOrigin = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVectorOrigin.copy());

            fLine.getIFPointAtX(0);

            assertEquals(fVectorOrigin, fLine.getOrigin(), "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get IFPoint at Y")
        void getIFPointAtY() {
            IFPoint base = HelperRandom.getTestPoint();
            IFPoint head = HelperRandom.getTestPoint(base);
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(base.copy(), head.copy()));

            assertAll("Validate IFPoint",
                    () -> assertTrue(fLine.getIFPointAtY(0).isPresent(),
                            "The IFPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getIFPointAtY(base.getY()).orElse(null))
                                    .isSimilar(fLine.getBase()),
                            "The IFPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getIFPointAtY(head.getY()).orElse(null))
                                    .isSimilar(fLine.getHead()),
                            "The IFPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get IFPoint at Y (empty)")
        void getIFPointAtYEmpty() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(1, 0, 2));

            assertTrue(fLine.getIFPointAtY(0).isEmpty(),
                    "The IFPoint should not be available");
        }

        @Test
        @DisplayName("Get IFPoint at Y (validate positions)")
        void getIFPointAtYValidatePositions() {
            IFVector fVectorOrigin = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVectorOrigin.copy());

            fLine.getIFPointAtY(0);

            assertEquals(fVectorOrigin, fLine.getOrigin(), "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get IFPoint at Z")
        void getIFPointAtZ() {
            IFPoint base = HelperRandom.getTestPoint();
            IFPoint head = HelperRandom.getTestPoint(base);
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(base.copy(), head.copy()));

            assertAll("Validate IFPoint",
                    () -> assertTrue(fLine.getIFPointAtZ(0).isPresent(),
                            "The IFPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getIFPointAtZ(base.getZ()).orElse(null))
                                    .isSimilar(fLine.getBase()),
                            "The IFPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getIFPointAtZ(head.getZ()).orElse(null))
                                    .isSimilar(fLine.getHead()),
                            "The IFPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get IFPoint at Z (empty)")
        void getIFPointAtZEmpty() {
            IFLine fLine = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(1, 2, 0));

            assertTrue(fLine.getIFPointAtZ(0).isEmpty(),
                    "The IFPoint should not be available");
        }

        @Test
        @DisplayName("Get IFPoint at Z (validate positions)")
        void getIFPointAtZValidatePositions() {
            IFVector fVectorOrigin = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVectorOrigin.copy());

            fLine.getIFPointAtZ(0);

            assertEquals(fVectorOrigin, fLine.getOrigin(), "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get common IFPoint 2D XY")
        void getCommonIFPoint2DXY() {
            IFVector fLineAOrigin = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);

            fLineAOrigin.getBase().setZ(0);
            fLineAOrigin.getHead().setZ(0);

            while (fLineAOrigin.isNonDirectional()) {
                fLineAOrigin.set(HelperRandom.getTestVector());

                fLineAOrigin.getBase().setZ(0);
                fLineAOrigin.getHead().setZ(0);
            }

            IFPoint fLineBOriginBase = HelperRandom.getTestPoint();
            IFPoint fLineBOriginHead = fLineA.getIFPoint(HelperRandom.getTestValue());
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(fLineBOriginBase, fLineBOriginHead);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            fLineBOriginBase.setZ(0);
            fLineBOriginBase.setZ(0);

            while (fLineBOriginBase.extLog(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(HelperRandom.getTestPoint());

                fLineBOriginBase.setZ(0);
            }

            Optional<IFPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertTrue(fPointRes.get().extLog(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extVal(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extLog(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extVal(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint 2D XY (simple)")
        void getCommonIFPoint2DXYSimple() {
            IFVector fLineAOrigin = FactoryGeometry.getIFVector(0, 0, 0, 1, 0, 0);
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(1, -1, 0, 3, 1, 0);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            IFPoint fPointRel = HelperRandom.getTestPoint().setZ(0);

            fLineA.getOrigin().add(fPointRel);
            fLineB.getOrigin().add(fPointRel);

            Optional<IFPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertTrue(fPointRes.get().extLog(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1"),
                    () -> assertTrue(fPointRes.get().extLog(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2")
            );
        }

        @Test
        @DisplayName("Get common IFPoint 2D XY (fail)")
        void getCommonIFPoint2DXYFail() {
            IFVector fLineAOrigin = FactoryGeometry.getIFVector(1, 0, 0);
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(-1, 1, 0, 1, 1, 0);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "The intersecting point is non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint")
        void getCommonIFPoint() {
            IFLine fLineA = FactoryGeometry.getIFLine(HelperRandom.getTestVector());

            IFPoint fLineBOriginBase = HelperRandom.getTestPoint();
            IFPoint fLineBOriginHead = fLineA.getIFPoint(HelperRandom.getTestValue());
            IFLine fLineB = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(fLineBOriginBase, fLineBOriginHead));

            while (fLineBOriginBase.extLog(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(HelperRandom.getTestPoint());
            }

            Optional<IFPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertTrue(fPointRes.get().extLog(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extVal(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extLog(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extVal(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (static X)")
        void getCommonIFPointStaticX() {
            IFVector fLineAOrigin = FactoryGeometry.getIFVector(1, 1, 0, 1, 1, 1);
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(1, 0, 0, 1, 3, 0);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            Optional<IFPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertEquals(fPointRes.get(), FactoryGeometry.getIFPoint(1, 1, 0),
                            "The IFPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extLog(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extVal(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extLog(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extVal(fLineB.getDistance()).get(0)+ ")")
            );
        }
        @Test
        @DisplayName("Get common IFPoint (static Y)")
        void getCommonIFPointStaticY() {
            IFVector fLineAOrigin = FactoryGeometry.getIFVector(1, 1, 0, 1, 1, 1);
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(0, 1, 0, 3, 1, 0);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            Optional<IFPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertEquals(fPointRes.get(), FactoryGeometry.getIFPoint(1, 1, 0),
                            "The IFPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extLog(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extVal(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extLog(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extVal(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (static Z)")
        void getCommonIFPointStaticZ() {
            IFVector fLineAOrigin = FactoryGeometry.getIFVector(0, 1, 1, 2, 1, 1);
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(1, 0, 1, 1, 2, 1);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            Optional<IFPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertEquals(fPointRes.get(), FactoryGeometry.getIFPoint(1, 1, 1),
                            "The IFPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extLog(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extVal(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extLog(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extVal(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (fail)")
        void getCommonIFPointFail() {
            IFLine fLineA = FactoryGeometry.getIFLine(HelperRandom.getTestVector());

            IFPoint fLineBOriginHead = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());

            while (fLineBOriginHead.extLog(fLineA.isPartOf()).get(0)) {
                fLineBOriginHead = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            }

            IFPoint fLineBOriginBase = fLineBOriginHead.copy().ext(fLineA.project());
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(fLineBOriginBase, fLineBOriginHead);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            fLineBOrigin.moveBase(fLineA.getBase());

            IFVector fVectorDrift = fLineA.getOrigin().copy().setCrossProduct(fLineBOrigin).setLength(1.5 * jitter);

            fLineBOrigin.getBase().set(fVectorDrift.getHead());

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint (fail, simple)")
        void getCommonIFPointFailSimple() {
            IFVector fLineAOrigin = FactoryGeometry.getIFVector(1, 0, 0);
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = FactoryGeometry.getIFVector(0, 1, 0, 0, 0, 1);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint (fail, same line)")
        void getCommonIFPointFailSameLine() {
            IFLine fLineA = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(1, 1, 1));
            IFLine fLineB = FactoryGeometry.getIFLine(FactoryGeometry.getIFVector(-1, -1, -1));

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "Origins form the same IFLine, the intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint (validate positions)")
        void getCommonIFPointValidatePositions() {
            IFVector fLineAOrigin = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin.copy());
            IFVector fLineBOrigin = HelperRandom.getTestVector(fLineAOrigin);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin.copy());

            fLineA.getCommonIFPoint(fLineB);

            assertAll("Validate positions",
                    () -> assertEquals(fLineAOrigin, fLineA.getOrigin(),
                            "The IFLine A position should remain unchanged"),
                    () -> assertEquals(fLineBOrigin, fLineB.getOrigin(),
                            "The IFLine B position should remain unchanged")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (validate references)")
        void getCommonIFPointValidateReferences() {
            IFVector fLineAOrigin = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fLineAOrigin);
            IFVector fLineBOrigin = HelperRandom.getTestVector(fLineAOrigin);
            IFLine fLineB = FactoryGeometry.getIFLine(fLineBOrigin);

            fLineA.getCommonIFPoint(fLineB);

            assertAll("Validate references",
                    () -> assertSame(fLineAOrigin, fLineA.getOrigin(),
                            "The IFLine A reference should remain unchanged"),
                    () -> assertSame(fLineBOrigin, fLineB.getOrigin(),
                            "The IFLine B reference should remain unchanged")
            );
        }
    }
}

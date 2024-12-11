package eu.scattering.core.test.core.mutable.geometry.advanced;

import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.core.algebra.geometry.construct.line.FLine;
import eu.scattering.core.test.core.mutable.geometry.advanced.support.FLineTestHelper;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FLine")
public class FLineTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FLineBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            FLine fLine = factory.getFLine();

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidatePositions() {
            FLine fLine = factory.getFLine();

            assertTrue(factory.getFVector().isExact(fLine.getOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = random.getFVector();
            FLine fLine = factory.getFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = random.getFVector();
            FLine fLine = factory.getFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with FVector (validate positions)")
        void constructWithFVectorValidatePositions() {
            double refAX = random.getDouble();
            double refAY = random.getDouble();
            double refAZ = random.getDouble();
            double refBX = random.getDouble();
            double refBY = random.getDouble();
            double refBZ = random.getDouble();
            FPoint fPointBase = factory.getFPoint(refAX, refAY, refAZ);
            FPoint fPointHead = factory.getFPoint(refBX, refBY, refBZ);
            FVector fVector = factory.getFVector(fPointBase, fPointHead);
            FLine fLine = factory.getFLine(fVector);

            Assertions.assertAll("Validate FPoint values",
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
        @DisplayName("Set origin ref")
        void setOriginRef() {
            FVector fVectorA = random.getFVector();
            FVector fVectorB = random.getFVector(fVectorA);
            FLine fLine = factory.getFLine(fVectorA);

            FLine fLineRef = fLine.setOriginRef(fVectorB);

            Assertions.assertAll("Validate FLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The FLine reference should not change")
            );
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = random.getFVector();
            FLine fLine = factory.getFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = random.getFVector();
            FLine fLine = factory.getFLine(fVector.copy());

            assertTrue(fVector.isExact(fLine.getOrigin()), "The FVector positions are erroneous");
        }

    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector);
            FLine fLineB = factory.getFLine().importFromJSON(fLineA.exportToJSON());

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLine references should point at different objects"),
                    () -> assertTrue(fLineA.getOrigin().isExact(fLineB.getOrigin()),
                            "The origin of FLines should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FLine fLine = factory.getFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::exportToJSON, fLine);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isExact(fLineB), "FLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "FLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "FLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "FLines should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FLine fLineA = factory.getFLine(factory.getFVector());
            FLine fLineB = factory.getFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::isExact, fLineA, fLineB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FLine fLineA = factory.getFLine(random.getFVector());
            FLine fLineB = factory.getFLine(random.getFVector(fLineA.getOrigin()));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "FLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "FLines should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head)")
        void isSimilarAboveHead() {
            FVector fVector = random.getFVector().normalize();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy());

            fLineB.getOrigin().moveForward(10);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head, inverted)")
        void isSimilarAboveHeadInverted() {
            FVector fVector = random.getFVector().normalize();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy());

            fLineB.getOrigin().moveForward(10).reflectHead();

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base)")
        void isSimilarBelowBase() {
            FVector fVector = random.getFVector().normalize();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy());

            fLineB.getOrigin().moveBackward(10);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base, inverted)")
        void isSimilarBelowBaseInverted() {
            FVector fVector = random.getFVector().normalize();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy());

            fLineB.getOrigin().moveBackward(10).reflectHead();

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FLine fLineA = factory.getFLine(random.getFVector());
            FLine fLineB = factory.getFLine(random.getFVector());

            FLineTestHelper.testValue(FLine::isSimilar, fLineA, fLineB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector.copy());
            FLine fLineB = factory.getFLine(fVector.copy());

            assertEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two identical FLines should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector);
            FLine fLineB = factory.getFLine(random.getFVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different FLines should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FLine fLine = factory.getFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::hashCode, fLine);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = random.getFVector();
            FLine fLineA = factory.getFLine(fVector);
            FLine fLineB = fLineA.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLines represent different objects"),
                    () -> assertTrue(fLineA.isExact(fLineB),
                            "FLines should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FLine fLine = factory.getFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::copy, fLine);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class FLineAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project());

            assertTrue(factory.getFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (position base)")
        void projectPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project (position head)")
        void projectPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.project()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::project, fLine);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect());

            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (position base)")
        void reflectPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect (position head)")
        void reflectPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.reflect()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::reflect, fLine);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fPoint.extBoolean(fLine.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fPoint.extBoolean(fLine.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (position base)")
        void isPartOfPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(-4, -4, -4).addY(0.5 * jitter);

            fPoint.extBoolean(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location (position head)")
        void isPartOfPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(4, 4, 4).addY(0.5 * jitter);

            fPoint.extBoolean(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fLine.isPartOf()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location (validate)")
        void isPartOfValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::isPartOf, fLine);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extDouble(fLine.getDistance()).get(0),
                    jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fLine.getDistance()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::getDistance, fLine);
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(6, fPoint.extDouble(fLine.getDistanceP2()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 (throw IllegalStateException)")
        void getDistanceP2ThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fLine.getDistanceP2()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::getDistanceP2, fLine);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1));

            Assertions.assertTrue(Math.abs(fPoint.extDouble(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (position base)")
        void setDistancePositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance (position head)")
        void setDistancePositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(-1));

            Assertions.assertTrue(Math.abs(fPoint.extDouble(fLine.getDistance()).get(0) - 1) < jitter,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            FLine fLine = factory.getFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            FPoint fPointA = fPoint.copy().ext(fLine.setDistance(1));
            FPoint fPointB = fPoint.copy().ext(fLine.setDistance(-1));

            Assertions.assertTrue(fPointA.getDistance(fPointB) - 2 < jitter,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.setDistance(1)),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.setDistance(1), fLine);
        }

        @Test
        @DisplayName("Project on ray")
        void isProjectableOnRay() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint();

            assertTrue(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The FPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (below base)")
        void isProjectableOnRayBelowBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The FPoint is not a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (over head)")
        void isProjectableOnRayOverHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            assertTrue(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The FPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (throw IllegalStateException)")
        void isProjectableOnRayThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fLine.isPartOfRay()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project on ray (validate)")
        void projectOnRayValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::isPartOfRay, fLine);
        }

        @Test
        @DisplayName("Project on segment")
        void isProjectableOnSegment() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint();

            assertTrue(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The FPoint is a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (below base)")
        void isProjectableOnSegmentBelowBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The FPoint is not a of the segment");
        }

        @Test
        @DisplayName("Project on segment (over head)")
        void isProjectableOnSegmentOverHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The FPoint is not a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (throw IllegalStateException)")
        void isProjectableOnSegmantThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fLine.isPartOfSegment()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project on segment (validate)")
        void projectOnSegmentValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(FLine::isPartOfSegment, fLine);
        }

        @Test
        @DisplayName("Move forward")
        void moveForward() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveForward(Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(factory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (negative)")
        void moveForwardNegative() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveForward(-Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(factory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (throw IllegalStateException)")
        void moveForwardThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint();

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.moveForward(Math.sqrt(3))),
                    "The direction of the FLine is not defined");
        }

        @Test
        @DisplayName("Move forward (validate)")
        void moveForwardValidate() {
            FLine fLine = factory.getFLine(factory.getFVector());

            FLineTestHelper.testValue(e -> e.moveForward(1), fLine);
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveBackward(Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(factory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (negative)")
        void moveBackwardNegative() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveBackward(-Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(factory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (throw IllegalStateException)")
        void moveBackwardThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint();

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.moveBackward(Math.sqrt(3))),
                    "The direction of the FLine is not defined");
        }

        @Test
        @DisplayName("Move backward (validate)")
        void moveBackwardValidate() {
            FLine fLine = factory.getFLine(factory.getFVector());

            FLineTestHelper.testValue(e -> e.moveBackward(1), fLine);
        }

        @Test
        @DisplayName("Rotate (simple)")
        void rotateSimple() {
            FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
            FLine fLine = factory.getFLine(factory.getFVector(0, 1, 0));

            fVector.ext(fLine.rotate(Math.PI * 0.5));

            assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                    "The position of the rotated FVector is erroneous");
        }

        @Test
        @DisplayName("Rotate (simple, negative)")
        void rotateSimpleNegative() {
            FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
            FLine fLine = factory.getFLine(factory.getFVector(0, 1, 0));

            fVector.ext(fLine.rotate(-(Math.PI * 0.5)));

            assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                    "The position of the rotated FVector is erroneous");
        }

        @Test
        @DisplayName("Rotate  (throw IllegalStateException)")
        void rotateThrowIllegalStateException() {
            FVector fVector = random.getFVector();
            FLine fLine = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fVector.ext(fLine.rotate(Math.PI * 0.5)),
                    "The direction of the FLine is not defined");
        }

        @Test
        @DisplayName("Rotate (validate)")
        void rotateValidate() {
            FLine fLine = factory.getFLine(random.getFVector());

            FLineTestHelper.testValue(e -> e.rotate(Math.PI * 0.5), fLine);
        }

        @Test
        @DisplayName("Get FPoint")
        void getFPoint() {
            FLine fLine = factory.getFLine(random.getFVector());
            double length = fLine.getOrigin().getLength();

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPoint(0).isSimilar(fLine.getBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(fLine.getFPoint(length).isSimilar(fLine.getHead()),
                            "The FPoint head is incorrect"),
                    () -> assertTrue(fLine.getFPoint(-length).isSimilar(fLine.getOrigin().reflectHead().getHeadRef()),
                            "The FPoint inverse head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint (throw IllegalStateException)")
        void getFPointThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPoint(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint (validate)")
        void getFPointValidatePositions() {
            FVector fVectorOrigin = random.getFVector();
            FLine fLine = factory.getFLine(fVectorOrigin.copy());

            fLine.getFPoint(0);

            assertTrue(fVectorOrigin.isExact(fLine.getOrigin()),
                    "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get FPoint at X")
        void getFPointAtX() {
            FPoint base = random.getFPoint();
            FPoint head = random.getFPoint(base);
            FLine fLine = factory.getFLine(factory.getFVector(base.copy(), head.copy()));

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPointAtX(0).isPresent(),
                            "The FPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtX(base.getX()).orElse(null))
                                    .isSimilar(fLine.getBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtX(head.getX()).orElse(null))
                                    .isSimilar(fLine.getHead()),
                            "The FPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint at X (empty)")
        void getFPointAtXEmpty() {
            FLine fLine = factory.getFLine(factory.getFVector(0, 1, 2));

            assertTrue(fLine.getFPointAtX(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at X (throw IllegalStateException)")
        void getFPointAtXThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPointAtX(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at X (validate)")
        void getFPointAtXValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.getFPointAtX(0), fLine);
        }

        @Test
        @DisplayName("Get FPoint at Y")
        void getFPointAtY() {
            FPoint base = random.getFPoint();
            FPoint head = random.getFPoint(base);
            FLine fLine = factory.getFLine(factory.getFVector(base.copy(), head.copy()));

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPointAtY(0).isPresent(),
                            "The FPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtY(base.getY()).orElse(null))
                                    .isSimilar(fLine.getBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtY(head.getY()).orElse(null))
                                    .isSimilar(fLine.getHead()),
                            "The FPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint at Y (empty)")
        void getFPointAtYEmpty() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 0, 2));

            assertTrue(fLine.getFPointAtY(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at Y (throw IllegalStateException)")
        void getFPointAtYThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPointAtY(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at Y (validate)")
        void getFPointAtYValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.getFPointAtY(0), fLine);
        }

        @Test
        @DisplayName("Get FPoint at Z")
        void getFPointAtZ() {
            FPoint base = random.getFPoint();
            FPoint head = random.getFPoint(base);
            FLine fLine = factory.getFLine(factory.getFVector(base.copy(), head.copy()));

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPointAtZ(0).isPresent(),
                            "The FPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtZ(base.getZ()).orElse(null))
                                    .isSimilar(fLine.getBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtZ(head.getZ()).orElse(null))
                                    .isSimilar(fLine.getHead()),
                            "The FPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint at Z (empty)")
        void getFPointAtZEmpty() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 2, 0));

            assertTrue(fLine.getFPointAtZ(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at Z (throw IllegalStateException)")
        void getFPointAtZThrowIllegalStateException() {
            FLine fLine = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPointAtZ(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at Z (validate)")
        void getFPointAtZValidate() {
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.getFPointAtZ(0), fLine);
        }

        @Test
        @DisplayName("Get common FPoint 2D XY")
        void getCommonFPoint2DXY() {
            FVector fLineAOrigin = random.getFVector();
            FLine fLineA = factory.getFLine(fLineAOrigin);

            fLineAOrigin.getBaseRef().setZ(0);
            fLineAOrigin.getHeadRef().setZ(0);

            while (fLineAOrigin.isNonDirectional()) {
                fLineAOrigin.set(random.getFVector());

                fLineAOrigin.getBaseRef().setZ(0);
                fLineAOrigin.getHeadRef().setZ(0);
            }

            FPoint fLineBOriginBase = random.getFPoint();
            FPoint fLineBOriginHead = fLineA.getFPoint(random.getDouble());
            FVector fLineBOrigin = factory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            fLineBOriginBase.setZ(0);
            fLineBOriginBase.setZ(0);

            while (fLineBOriginBase.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(random.getFPoint());

                fLineBOriginBase.setZ(0);
            }

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint 2D XY (simple)")
        void getCommonFPoint2DXYSimple() {
            FVector fLineAOrigin = factory.getFVector(0, 0, 0, 1, 0, 0);
            FLine fLineA = factory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(1, -1, 0, 3, 1, 0);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            FPoint fPointRel = random.getFPoint().setZ(0);

            fLineA.getOrigin().add(fPointRel);
            fLineB.getOrigin().add(fPointRel);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2")
            );
        }

        @Test
        @DisplayName("Get common FPoint 2D XY (fail)")
        void getCommonFPoint2DXYFail() {
            FVector fLineAOrigin = factory.getFVector(1, 0, 0);
            FLine fLineA = factory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(-1, 1, 0, 1, 1, 0);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "The intersecting point is non-existent");
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FLine fLineA = factory.getFLine(random.getFVector());

            FPoint fLineBOriginBase = random.getFPoint();
            FPoint fLineBOriginHead = fLineA.getFPoint(random.getDouble());
            FLine fLineB = factory.getFLine(factory.getFVector(fLineBOriginBase, fLineBOriginHead));

            while (fLineBOriginBase.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(random.getFPoint());
            }

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint (static X)")
        void getCommonFPointStaticX() {
            FVector fLineAOrigin = factory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = factory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(1, 0, 0, 1, 3, 0);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().isExact(factory.getFPoint(1, 1, 0)),
                            "The FPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }
        @Test
        @DisplayName("Get common FPoint (static Y)")
        void getCommonFPointStaticY() {
            FVector fLineAOrigin = factory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = factory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(0, 1, 0, 3, 1, 0);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().isExact(factory.getFPoint(1, 1, 0)),
                            "The FPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint (static Z)")
        void getCommonFPointStaticZ() {
            FVector fLineAOrigin = factory.getFVector(0, 1, 1, 2, 1, 1);
            FLine fLineA = factory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(1, 0, 1, 1, 2, 1);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().isExact(factory.getFPoint(1, 1, 1)),
                            "The FPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint (fail)")
        void getCommonFPointFail() {
            FLine fLineA = factory.getFLine(random.getFVector());

            FPoint fLineBOriginHead = factory.getFPoint(random.getFPoint());

            while (fLineBOriginHead.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginHead = factory.getFPoint(random.getFPoint());
            }

            FPoint fLineBOriginBase = fLineBOriginHead.copy().ext(fLineA.project());
            FVector fLineBOrigin = factory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            fLineBOrigin.moveBase(fLineA.getBase());

            FVector fVectorDrift = fLineA.getOrigin().copy()
                    .setCrossProduct(fLineBOrigin)
                    .setLength(1.5 * jitter);

            fLineBOrigin.getBaseRef().set(fVectorDrift.getHeadRef());

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (fail, simple)")
        void getCommonFPointFailSimple() {
            FVector fLineAOrigin = factory.getFVector(1, 0, 0);
            FLine fLineA = factory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(0, 1, 0, 0, 0, 1);
            FLine fLineB = factory.getFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (fail, same line)")
        void getCommonFPointFailSameLine() {
            FLine fLineA = factory.getFLine(factory.getFVector(1, 1, 1));
            FLine fLineB = factory.getFLine(factory.getFVector(-1, -1, -1));

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "Origins form the same FLine, the intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FLine fLineA = factory.getFLine(factory.getFVector());
            FLine fLineB = factory.getFLine(factory.getFVector(-1, -1, -1));

            Assertions.assertThrows(IllegalStateException.class, () -> fLineA.getCommonFPoint(fLineB),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, argument)")
        void getCommonFPointThrowIllegalStateExceptionArgument() {
            FLine fLineA = factory.getFLine(factory.getFVector(-1, -1, -1));
            FLine fLineB = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLineA.getCommonFPoint(fLineB),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FLine fLineA = factory.getFLine(random.getFVector());
            FLine fLineB = factory.getFLine(random.getFVector());

            FLineTestHelper.testValue(FLine::getCommonFPoint, fLineA, fLineB);
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FLine fLine = factory.getFLine();
            List<FPoint> disassembly = fLine.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fLine.getBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fLine.getHead()),
                            "The FPoint head value is erroneous")
            );
        }

    }

}

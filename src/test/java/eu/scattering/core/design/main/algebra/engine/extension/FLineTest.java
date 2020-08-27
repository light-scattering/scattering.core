package eu.scattering.core.design.main.algebra.engine.extension;

import eu.scattering.core.Config;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.helper.FLineHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            FLine fLine = MainFactory.getFLine();

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidatePositions() {
            FLine fLine = MainFactory.getFLine();

            assertEquals(MainFactory.getFVector(), fLine.getOrigin(),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with FVector (validate positions)")
        void constructWithFVectorValidatePositions() {
            double refAX = RandomHelper.getTestValue();
            double refAY = RandomHelper.getTestValue();
            double refAZ = RandomHelper.getTestValue();
            double refBX = RandomHelper.getTestValue();
            double refBY = RandomHelper.getTestValue();
            double refBZ = RandomHelper.getTestValue();
            FPoint fPointBase = MainFactory.getFPoint(refAX, refAY, refAZ);
            FPoint fPointHead = MainFactory.getFPoint(refBX, refBY, refBZ);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);
            FLine fLine = MainFactory.getFLine(fVector);

            assertAll("Validate FPoint values",
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
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            FLine fLine = MainFactory.getFLine(fVectorA);

            FLine fLineRef = fLine.setOriginRef(fVectorB);

            assertAll("Validate FLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The FLine reference should not change")
            );
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector.copy());

            assertEquals(fVector, fLine.getOrigin(), "The FVector positions are erroneous");
        }

    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector);
            FLine fLineB = MainFactory.getFLine().importFromJSON(fLineA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLine references should point at different objects"),
                    () -> assertEquals(fLineA.getOrigin(), fLineB.getOrigin(),
                            "The origin of FLines should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(FLine::exportToJSON, fLine);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isExact(fLineB), "FLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "FLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "FLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "FLines should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FLine fLineA = MainFactory.getFLine(MainFactory.getFVector());
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(FLine::isExact, fLineA, fLineB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector(fLineA.getOrigin()));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "FLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "FLines should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head)")
        void isSimilarAboveHead() {
            FVector fVector = RandomHelper.getTestVector().normalize();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy());

            fLineB.getOrigin().moveForward(10);

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head, inverted)")
        void isSimilarAboveHeadInverted() {
            FVector fVector = RandomHelper.getTestVector().normalize();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy());

            fLineB.getOrigin().moveForward(10).reflectHead();

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base)")
        void isSimilarBelowBase() {
            FVector fVector = RandomHelper.getTestVector().normalize();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy());

            fLineB.getOrigin().moveBackward(10);

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base, inverted)")
        void isSimilarBelowBaseInverted() {
            FVector fVector = RandomHelper.getTestVector().normalize();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy());

            fLineB.getOrigin().moveBackward(10).reflectHead();

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector());

            FLineHelper.validateVal(FLine::isSimilar, fLineA, fLineB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy());

            assertEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two identical FLines should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector);
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different FLines should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(FLine::hashCode, fLine);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector);
            FLine fLineB = fLineA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLines represent different objects"),
                    () -> assertEquals(fLineA, fLineB,
                            "FLines should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(FLine::copy, fLine);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class FLineAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.project());

            assertTrue(MainFactory.getFPoint(1, 1, 1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (position base)")
        void projectPositionBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project (position head)")
        void projectPositionHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            fPoint.ext(fLine.project());
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.project()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(FLine::project, fLine);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            fPoint.ext(fLine.reflect());

            assertTrue(MainFactory.getFPoint(2, -1, 2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (position base)")
        void reflectPositionBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect (position head)")
        void reflectPositionHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            fPoint.ext(fLine.reflect());
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.reflect()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(FLine::reflect, fLine);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(1, 1, 1).addY(0.5 * Config.getJitter());

            assertTrue(fPoint.extBoolean(fLine.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(1, 1, 1).addY(1.5 * Config.getJitter());

            assertFalse(fPoint.extBoolean(fLine.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (position base)")
        void isPartOfPositionBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(-4, -4, -4).addY(0.5 * Config.getJitter());

            fPoint.extBoolean(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location (position head)")
        void isPartOfPositionHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(4, 4, 4).addY(0.5 * Config.getJitter());

            fPoint.extBoolean(fLine.isPartOf());
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fLine.isPartOf()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location (validate)")
        void isPartOfValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(FLine::isPartOf, fLine);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(6), fPoint.extDouble(fLine.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (position base)")
        void getDistancePositionBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            fPoint.extDouble(fLine.getDistance());
        }

        @Test
        @DisplayName("Get distance (position head)")
        void getDistancePositionHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            fPoint.extDouble(fLine.getDistance());
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fLine.getDistance()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(FLine::getDistance, fLine);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(1));

            assertTrue(Math.abs(fPoint.extDouble(fLine.getDistance()).get(0) - 1) < Config.getJitter(),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (position base)")
        void setDistancePositionBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance (position head)")
        void setDistancePositionHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            fPoint.ext(fLine.setDistance(1));
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fLine.setDistance(-1));

            assertTrue(Math.abs(fPoint.extDouble(fLine.getDistance()).get(0) - 1) < Config.getJitter(),
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(2, 2, 2));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fLine.getOrigin().add(relocation);
            fPoint.add(relocation);

            FPoint fPointA = fPoint.copy().ext(fLine.setDistance(1));
            FPoint fPointB = fPoint.copy().ext(fLine.setDistance(-1));

            assertTrue(fPointA.getDistance(fPointB) - 2 < Config.getJitter(),
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.setDistance(1)),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(e -> e.setDistance(1), fLine);
        }

        @Test
        @DisplayName("Project on ray")
        void isProjectableOnRay() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint();

            assertTrue(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The FPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (below base)")
        void isProjectableOnRayBelowBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The FPoint is not a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (over head)")
        void isProjectableOnRayOverHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            assertTrue(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The FPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (throw IllegalStateException)")
        void isProjectableOnRayThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fLine.isPartOfRay()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project on ray (validate)")
        void projectOnRayValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(FLine::isPartOfRay, fLine);
        }

        @Test
        @DisplayName("Project on segment")
        void isProjectableOnSegment() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint();

            assertTrue(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The FPoint is a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (below base)")
        void isProjectableOnSegmentBelowBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The FPoint is not a of the segment");
        }

        @Test
        @DisplayName("Project on segment (over head)")
        void isProjectableOnSegmentOverHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The FPoint is not a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (throw IllegalStateException)")
        void isProjectableOnSegmantThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fLine.isPartOfSegment()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project on segment (validate)")
        void projectOnSegmentValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(FLine::isPartOfSegment, fLine);
        }

        @Test
        @DisplayName("Move forward")
        void moveForward() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveForward(Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(MainFactory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (negative)")
        void moveForwardNegative() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveForward(-Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(MainFactory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (throw IllegalStateException)")
        void moveForwardThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint();

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.moveForward(Math.sqrt(3))),
                    "The direction of the FLine is not defined");
        }

        @Test
        @DisplayName("Move forward (validate)")
        void moveForwardValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.moveForward(1), fLine);
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveBackward(Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(MainFactory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (negative)")
        void moveBackwardNegative() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(1, 0, 0);

            fPoint.ext(fLine.moveBackward(-Math.sqrt(3)));

            assertTrue(fPoint.isSimilar(MainFactory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (throw IllegalStateException)")
        void moveBackwardThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint();

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fLine.moveBackward(Math.sqrt(3))),
                    "The direction of the FLine is not defined");
        }

        @Test
        @DisplayName("Move backward (validate)")
        void moveBackwardValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.moveBackward(1), fLine);
        }

        @Test
        @DisplayName("Get FPoint")
        void getFPoint() {
            FLine fLine = MainFactory.getFLine(RandomHelper.getTestVector());
            double length = fLine.getOrigin().getLength();

            assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPoint(0).isSimilar(fLine.getBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(fLine.getFPoint(length).isSimilar(fLine.getHead()),
                            "The FPoint head is incorrect"),
                    () -> assertTrue(fLine.getFPoint(-length).isSimilar(fLine.getOrigin().reflectHead().getHead()),
                            "The FPoint inverse head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint (throw IllegalStateException)")
        void getFPointThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fLine.getFPoint(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint (validate)")
        void getFPointValidatePositions() {
            FVector fVectorOrigin = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVectorOrigin.copy());

            fLine.getFPoint(0);

            assertEquals(fVectorOrigin, fLine.getOrigin(), "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get FPoint at X")
        void getFPointAtX() {
            FPoint base = RandomHelper.getTestPoint();
            FPoint head = RandomHelper.getTestPoint(base);
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(base.copy(), head.copy()));

            assertAll("Validate FPoint",
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
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(0, 1, 2));

            assertTrue(fLine.getFPointAtX(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at X (throw IllegalStateException)")
        void getFPointAtXThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fLine.getFPointAtX(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at X (validate)")
        void getFPointAtXValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(e -> e.getFPointAtX(0), fLine);
        }

        @Test
        @DisplayName("Get FPoint at Y")
        void getFPointAtY() {
            FPoint base = RandomHelper.getTestPoint();
            FPoint head = RandomHelper.getTestPoint(base);
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(base.copy(), head.copy()));

            assertAll("Validate FPoint",
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
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 0, 2));

            assertTrue(fLine.getFPointAtY(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at Y (throw IllegalStateException)")
        void getFPointAtYThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fLine.getFPointAtY(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at Y (validate)")
        void getFPointAtYValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(e -> e.getFPointAtY(0), fLine);
        }

        @Test
        @DisplayName("Get FPoint at Z")
        void getFPointAtZ() {
            FPoint base = RandomHelper.getTestPoint();
            FPoint head = RandomHelper.getTestPoint(base);
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(base.copy(), head.copy()));

            assertAll("Validate FPoint",
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
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 2, 0));

            assertTrue(fLine.getFPointAtZ(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at Z (throw IllegalStateException)")
        void getFPointAtZThrowIllegalStateException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fLine.getFPointAtZ(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at Z (validate)")
        void getFPointAtZValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));

            FLineHelper.validateVal(e -> e.getFPointAtZ(0), fLine);
        }

        @Test
        @DisplayName("Get common FPoint 2D XY")
        void getCommonFPoint2DXY() {
            FVector fLineAOrigin = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);

            fLineAOrigin.getBase().setZ(0);
            fLineAOrigin.getHead().setZ(0);

            while (fLineAOrigin.isNonDirectional()) {
                fLineAOrigin.set(RandomHelper.getTestVector());

                fLineAOrigin.getBase().setZ(0);
                fLineAOrigin.getHead().setZ(0);
            }

            FPoint fLineBOriginBase = RandomHelper.getTestPoint();
            FPoint fLineBOriginHead = fLineA.getFPoint(RandomHelper.getTestValue());
            FVector fLineBOrigin = MainFactory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            fLineBOriginBase.setZ(0);
            fLineBOriginBase.setZ(0);

            while (fLineBOriginBase.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(RandomHelper.getTestPoint());

                fLineBOriginBase.setZ(0);
            }

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            assertAll("Validate FPoint",
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
            FVector fLineAOrigin = MainFactory.getFVector(0, 0, 0, 1, 0, 0);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(1, -1, 0, 3, 1, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            FPoint fPointRel = RandomHelper.getTestPoint().setZ(0);

            fLineA.getOrigin().add(fPointRel);
            fLineB.getOrigin().add(fPointRel);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The FPoint should be part of FLine 1"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The FPoint should be part of FLine 2")
            );
        }

        @Test
        @DisplayName("Get common FPoint 2D XY (fail)")
        void getCommonFPoint2DXYFail() {
            FVector fLineAOrigin = MainFactory.getFVector(1, 0, 0);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(-1, 1, 0, 1, 1, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "The intersecting point is non-existent");
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());

            FPoint fLineBOriginBase = RandomHelper.getTestPoint();
            FPoint fLineBOriginHead = fLineA.getFPoint(RandomHelper.getTestValue());
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector(fLineBOriginBase, fLineBOriginHead));

            while (fLineBOriginBase.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(RandomHelper.getTestPoint());
            }

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            assertAll("Validate FPoint",
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
            FVector fLineAOrigin = MainFactory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(1, 0, 0, 1, 3, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            assertAll("Validate FPoint",
                    () -> assertEquals(fPointRes.get(), MainFactory.getFPoint(1, 1, 0),
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
            FVector fLineAOrigin = MainFactory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(0, 1, 0, 3, 1, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            assertAll("Validate FPoint",
                    () -> assertEquals(fPointRes.get(), MainFactory.getFPoint(1, 1, 0),
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
            FVector fLineAOrigin = MainFactory.getFVector(0, 1, 1, 2, 1, 1);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(1, 0, 1, 1, 2, 1);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            assertAll("Validate FPoint",
                    () -> assertEquals(fPointRes.get(), MainFactory.getFPoint(1, 1, 1),
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
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());

            FPoint fLineBOriginHead = MainFactory.getFPoint(RandomHelper.getTestPoint());

            while (fLineBOriginHead.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginHead = MainFactory.getFPoint(RandomHelper.getTestPoint());
            }

            FPoint fLineBOriginBase = fLineBOriginHead.copy().ext(fLineA.project());
            FVector fLineBOrigin = MainFactory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            fLineBOrigin.moveBase(fLineA.getBase());

            FVector fVectorDrift = fLineA.getOrigin().copy()
                    .setCrossProduct(fLineBOrigin)
                    .setLength(1.5 * Config.getJitter());

            fLineBOrigin.getBase().set(fVectorDrift.getHead());

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (fail, simple)")
        void getCommonFPointFailSimple() {
            FVector fLineAOrigin = MainFactory.getFVector(1, 0, 0);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(0, 1, 0, 0, 0, 1);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (fail, same line)")
        void getCommonFPointFailSameLine() {
            FLine fLineA = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector(-1, -1, -1));

            assertTrue(fLineA.getCommonFPoint(fLineB).isEmpty(),
                    "Origins form the same FLine, the intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FLine fLineA = MainFactory.getFLine(MainFactory.getFVector());
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector(-1, -1, -1));

            assertThrows(IllegalStateException.class, () -> fLineA.getCommonFPoint(fLineB),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, argument)")
        void getCommonFPointThrowIllegalStateExceptionArgument() {
            FLine fLineA = MainFactory.getFLine(MainFactory.getFVector(-1, -1, -1));
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fLineA.getCommonFPoint(fLineB),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector());

            FLineHelper.validateVal(FLine::getCommonFPoint, fLineA, fLineB);
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FLine fLine = MainFactory.getFLine();
            List<FPoint> disassembly = fLine.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            assertAll("Validate FPoints",
                    () -> assertTrue(MainFactory.getFPoint(1, 2, 3).isExact(fLine.getBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(MainFactory.getFPoint(4, 5, 6).isExact(fLine.getHead()),
                            "The FPoint head value is erroneous")
            );
        }

    }

}

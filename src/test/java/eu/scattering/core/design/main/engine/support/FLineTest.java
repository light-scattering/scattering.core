package eu.scattering.core.design.main.engine.support;

import eu.scattering.core.Config;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;
import eu.scattering.core.design.main.engine.support.line.FLine;
import eu.scattering.core.design.main.engine.support.helper.FLineHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFLine")
public class FLineTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFLineBase {

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
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector (validate positions)")
        void constructWithIFVectorValidatePositions() {
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
        @DisplayName("Set origin ref")
        void setOriginRef() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            FLine fLine = MainFactory.getFLine(fVectorA);

            FLine fLineRef = fLine.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVector.copy());

            assertEquals(fVector, fLine.getOrigin(), "The IFVector positions are erroneous");
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
                            "IFLine references should point at different objects"),
                    () -> assertEquals(fLineA.getOrigin(), fLineB.getOrigin(),
                            "The origin of IFLines should be exact")
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
                    () -> assertTrue(fLineA.isExact(fLineB), "IFLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "IFLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector.copy());
            FLine fLineB = MainFactory.getFLine(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "IFLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "IFLines should not be equal")
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
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector(fLineA.getOrigin()));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "IFLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "IFLines should not be similar")
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
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
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
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
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
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
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
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
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
                    "Two identical IFLines should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = RandomHelper.getTestVector();
            FLine fLineA = MainFactory.getFLine(fVector);
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different IFLines should not have the same hash code");
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
                            "IFLines represent different objects"),
                    () -> assertEquals(fLineA, fLineB,
                            "IFLines should have the same values")
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
    class IFLineAdvanced {

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
        @DisplayName("Project (validate)")
        void projectValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

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
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

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
        @DisplayName("Location (validate)")
        void locationValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

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
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

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
                    "The distance between IFPoints is erroneous");
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
                    "The distance between IFPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.setDistance(1), fLine);
        }

        @Test
        @DisplayName("Project on ray")
        void isProjectableOnRay() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint();

            assertTrue(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The IFPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (below base)")
        void isProjectableOnRayBelowBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The IFPoint is not a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (over head)")
        void isProjectableOnRayOverHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            assertTrue(fPoint.extBoolean(fLine.isPartOfRay()).get(0),
                    "The IFPoint is a part of the ray");
        }

        @Test
        @DisplayName("Project on ray (validate)")
        void projectOnRayValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(FLine::isPartOfRay, fLine);
        }

        @Test
        @DisplayName("Project on segment")
        void isProjectableOnSegment() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint();

            assertTrue(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The IFPoint is a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (below base)")
        void isProjectableOnSegmentBelowBase() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, -9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The IFPoint is not a of the segment");
        }

        @Test
        @DisplayName("Project on segment (over head)")
        void isProjectableOnSegmentOverHead() {
            FVector fVector = MainFactory.getFVector(4, 4, 4).sub(2);
            FLine fLine = MainFactory.getFLine(fVector.copy());
            FPoint fPoint = MainFactory.getFPoint(0, 9, 0);

            assertFalse(fPoint.extBoolean(fLine.isPartOfSegment()).get(0),
                    "The IFPoint is not a part of the segment");
        }

        @Test
        @DisplayName("Project on segment (validate)")
        void projectOnSegmentValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

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
        @DisplayName("Move forward (throw DirectionException)")
        void moveForwardThrowDirectionException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint();

            assertThrows(DirectionException.class, () -> fPoint.ext(fLine.moveForward(Math.sqrt(3))),
                    "The direction of the IFLine is not defined");
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
        @DisplayName("Move backward (throw DirectionException)")
        void moveBackwardThrowDirectionException() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());
            FPoint fPoint = MainFactory.getFPoint();

            assertThrows(DirectionException.class, () -> fPoint.ext(fLine.moveBackward(Math.sqrt(3))),
                    "The direction of the IFLine is not defined");
        }

        @Test
        @DisplayName("Move backward (validate)")
        void moveBackwardValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.moveBackward(1), fLine);
        }

        @Test
        @DisplayName("Get IFPoint")
        void getIFPoint() {
            FLine fLine = MainFactory.getFLine(RandomHelper.getTestVector());
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
        @DisplayName("Get IFPoint (validate)")
        void getIFPointValidatePositions() {
            FVector fVectorOrigin = RandomHelper.getTestVector();
            FLine fLine = MainFactory.getFLine(fVectorOrigin.copy());

            fLine.getIFPoint(0);

            assertEquals(fVectorOrigin, fLine.getOrigin(), "The position should remain unchanged");
        }

        @Test
        @DisplayName("Get IFPoint at X")
        void getIFPointAtX() {
            FPoint base = RandomHelper.getTestPoint();
            FPoint head = RandomHelper.getTestPoint(base);
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(base.copy(), head.copy()));

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
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(0, 1, 2));

            assertTrue(fLine.getIFPointAtX(0).isEmpty(),
                    "The IFPoint should not be available");
        }

        @Test
        @DisplayName("Get IFPoint at X (validate)")
        void getIFPointAtXValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.getIFPointAtX(0), fLine);
        }

        @Test
        @DisplayName("Get IFPoint at Y")
        void getIFPointAtY() {
            FPoint base = RandomHelper.getTestPoint();
            FPoint head = RandomHelper.getTestPoint(base);
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(base.copy(), head.copy()));

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
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 0, 2));

            assertTrue(fLine.getIFPointAtY(0).isEmpty(),
                    "The IFPoint should not be available");
        }

        @Test
        @DisplayName("Get IFPoint at Y (validate)")
        void getIFPointAtYValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.getIFPointAtY(0), fLine);
        }

        @Test
        @DisplayName("Get IFPoint at Z")
        void getIFPointAtZ() {
            FPoint base = RandomHelper.getTestPoint();
            FPoint head = RandomHelper.getTestPoint(base);
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(base.copy(), head.copy()));

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
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector(1, 2, 0));

            assertTrue(fLine.getIFPointAtZ(0).isEmpty(),
                    "The IFPoint should not be available");
        }

        @Test
        @DisplayName("Get IFPoint at Z (validate)")
        void getIFPointAtZValidate() {
            FLine fLine = MainFactory.getFLine(MainFactory.getFVector());

            FLineHelper.validateVal(e -> e.getIFPointAtZ(0), fLine);
        }

        @Test
        @DisplayName("Get common IFPoint 2D XY")
        void getCommonIFPoint2DXY() {
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
            FPoint fLineBOriginHead = fLineA.getIFPoint(RandomHelper.getTestValue());
            FVector fLineBOrigin = MainFactory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            fLineBOriginBase.setZ(0);
            fLineBOriginBase.setZ(0);

            while (fLineBOriginBase.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(RandomHelper.getTestPoint());

                fLineBOriginBase.setZ(0);
            }

            Optional<FPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint 2D XY (simple)")
        void getCommonIFPoint2DXYSimple() {
            FVector fLineAOrigin = MainFactory.getFVector(0, 0, 0, 1, 0, 0);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(1, -1, 0, 3, 1, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            FPoint fPointRel = RandomHelper.getTestPoint().setZ(0);

            fLineA.getOrigin().add(fPointRel);
            fLineB.getOrigin().add(fPointRel);

            Optional<FPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2")
            );
        }

        @Test
        @DisplayName("Get common IFPoint 2D XY (fail)")
        void getCommonIFPoint2DXYFail() {
            FVector fLineAOrigin = MainFactory.getFVector(1, 0, 0);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(-1, 1, 0, 1, 1, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "The intersecting point is non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint")
        void getCommonIFPoint() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());

            FPoint fLineBOriginBase = RandomHelper.getTestPoint();
            FPoint fLineBOriginHead = fLineA.getIFPoint(RandomHelper.getTestValue());
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector(fLineBOriginBase, fLineBOriginHead));

            while (fLineBOriginBase.extBoolean(fLineA.isPartOf()).get(0)) {
                fLineBOriginBase.set(RandomHelper.getTestPoint());
            }

            Optional<FPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (static X)")
        void getCommonIFPointStaticX() {
            FVector fLineAOrigin = MainFactory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(1, 0, 0, 1, 3, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertEquals(fPointRes.get(), MainFactory.getFPoint(1, 1, 0),
                            "The IFPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }
        @Test
        @DisplayName("Get common IFPoint (static Y)")
        void getCommonIFPointStaticY() {
            FVector fLineAOrigin = MainFactory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(0, 1, 0, 3, 1, 0);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertEquals(fPointRes.get(), MainFactory.getFPoint(1, 1, 0),
                            "The IFPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (static Z)")
        void getCommonIFPointStaticZ() {
            FVector fLineAOrigin = MainFactory.getFVector(0, 1, 1, 2, 1, 1);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(1, 0, 1, 1, 2, 1);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getCommonIFPoint(fLineB);
            assertTrue(fPointRes.isPresent(),"IFLines should have one intersecting IFPoint");

            assertAll("Validate IFPoint",
                    () -> assertEquals(fPointRes.get(), MainFactory.getFPoint(1, 1, 1),
                            "The IFPoint is erroneous"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineA.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 1 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineA.getDistance()).get(0)+ ")"),
                    () -> assertTrue(fPointRes.get().extBoolean(fLineB.isPartOf()).get(0),
                            "The IFPoint should be part of IFLine 2 " +
                                    "(distance: " + fPointRes.get().extDouble(fLineB.getDistance()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common IFPoint (fail)")
        void getCommonIFPointFail() {
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

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint (fail, simple)")
        void getCommonIFPointFailSimple() {
            FVector fLineAOrigin = MainFactory.getFVector(1, 0, 0);
            FLine fLineA = MainFactory.getFLine(fLineAOrigin);
            FVector fLineBOrigin = MainFactory.getFVector(0, 1, 0, 0, 0, 1);
            FLine fLineB = MainFactory.getFLine(fLineBOrigin);

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint (fail, same line)")
        void getCommonIFPointFailSameLine() {
            FLine fLineA = MainFactory.getFLine(MainFactory.getFVector(1, 1, 1));
            FLine fLineB = MainFactory.getFLine(MainFactory.getFVector(-1, -1, -1));

            assertTrue(fLineA.getCommonIFPoint(fLineB).isEmpty(),
                    "Origins form the same IFLine, the intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common IFPoint (validate)")
        void getCommonIFPointValidate() {
            FLine fLineA = MainFactory.getFLine(RandomHelper.getTestVector());
            FLine fLineB = MainFactory.getFLine(RandomHelper.getTestVector());

            FLineHelper.validateVal(FLine::getCommonIFPoint, fLineA, fLineB);
        }

    }

}

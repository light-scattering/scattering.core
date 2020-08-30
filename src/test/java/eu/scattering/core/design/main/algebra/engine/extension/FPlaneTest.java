package eu.scattering.core.design.main.algebra.engine.extension;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.design.main.algebra.engine.extension.support.FPlaneTestHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static eu.scattering.core.Config.mainFactory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPlane")
public class FPlaneTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FPlaneBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            FPlane fPlane = mainFactory.getFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidate() {
            FPlane fPlane = mainFactory.getFPlane();

            assertEquals(mainFactory.getFVector(), fPlane.getOrigin(),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = mainFactory.getFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = mainFactory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The FVector reference is erroneous");
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
            FPoint fPointBase = mainFactory.getFPoint(refAX, refAY, refAZ);
            FPoint fPointHead = mainFactory.getFPoint(refBX, refBY, refBZ);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);
            FPlane fPlane = mainFactory.getFPlane(fVector);

            assertAll("Validate FPoint values",
                    () -> assertEquals(refAX, fPlane.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fPlane.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fPlane.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fPlane.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fPlane.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fPlane.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FVector (throw NullPointerException)")
        void constructWithFVectorThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> mainFactory.getFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            FPlane fPlane = mainFactory.getFPlane(fVectorA);

            FPlane fPlaneRef = fPlane.setOriginRef(fVectorB);

            assertAll("Validate FLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fPlane.getOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fPlaneRef, fPlane, "The FLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = mainFactory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = mainFactory.getFPlane(fVector.copy());

            assertEquals(fVector, fPlane.getOrigin(), "The FVector positions are erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector);
            FPlane fPlaneB = mainFactory.getFPlane().importFromJSON(fPlaneA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlane references should point at different objects"),
                    () -> assertEquals(fPlaneA.getOrigin(), fPlaneB.getOrigin(),
                            "The origin of FPlanes should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());

            FPlaneTestHelper.testValue(FPlane::exportToJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = mainFactory.getFPlane(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "FPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "FPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = mainFactory.getFPlane(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "FPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "FPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FPlane fPlaneA = mainFactory.getFPlane(mainFactory.getFVector());
            FPlane fPlaneB = mainFactory.getFPlane(mainFactory.getFVector());

            FPlaneTestHelper.testValue(FPlane::isExact, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = mainFactory.getFPlane(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (inverted)")
        void isSimilarInverted() {
            FPlane fPlaneA = mainFactory.getFPlane(RandomHelper.getTestVector());
            FPlane fPlaneB = fPlaneA.copy();

            fPlaneB.getOrigin().reflectHead().moveBase(RandomHelper.getTestPoint());
            fPlaneB.getOrigin().moveBase(fPlaneB.copy().getBase().ext(fPlaneA.project()));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = mainFactory.getFPlane(fVector.copy().moveForward(1.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "FPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "FPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FPlane fPlaneA = mainFactory.getFPlane(mainFactory.getFVector(0, 1, 0));
            FPlane fPlaneB = mainFactory.getFPlane(mainFactory.getFVector(0, 1, 0));

            FPlaneTestHelper.testValue(FPlane::isSimilar, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = mainFactory.getFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical FPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector);
            FPlane fPlaneB = mainFactory.getFPlane(RandomHelper.getTestVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different FPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());

            FPlaneTestHelper.testValue(FPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = mainFactory.getFPlane(fVector);
            FPlane fPlaneB = fPlaneA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlanes represent different objects"),
                    () -> assertEquals(fPlaneA, fPlaneB,
                            "FPlanes should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());

            FPlaneTestHelper.testValue(FPlane::copy, fPlane);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class FPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(mainFactory.getFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.project()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(FPlane::project, fPlane);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(mainFactory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.reflect()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(FPlane::reflect, fPlane);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFPoint(-1, 2, -1).add(0.5 * Config.getJitter());

            assertTrue(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFPoint(-1, 2, -1).add(1.5 * Config.getJitter());

            assertFalse(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fPlane.isPartOf()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location (validate)")
        void locationValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(FPlane::isPartOf, fPlane);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extDouble(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fPlane.getDistance()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(FPlane::getDistance, fPlane);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());
            FPoint fPoint = RandomHelper.getTestPoint();

            fPoint.ext(fPlane.setDistance(1));

            assertEquals(1, fPoint.extDouble(fPlane.getDistance()).get(0),
                    Config.getJitter(), "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.setDistance(1)),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(e -> e.setDistance(1), fPlane);
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFVector(1, 1, 1)
                    .mul(Config.getJitter())
                    .moveBase(-1, 2, -1)
                    .getHead();

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertTrue(fPoint.extBoolean(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (fail)")
        void isInHalfSpaceFail() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FPoint fPoint = mainFactory.getFVector(1, 1, 1)
                    .mul(Config.getJitter())
                    .reflectHead()
                    .moveBase(-1, 2, -1)
                    .getHead();

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertFalse(fPoint.extBoolean(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (throw IllegalStateException)")
        void isInHalfSpaceThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FPoint fPoint = mainFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fPlane.isInHalfSpace()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine half-space (validate)")
        void isInHalfSpaceValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(FPlane::isInHalfSpace, fPlane);
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FVector fVector = mainFactory.getFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FVector fVector = mainFactory.getFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FVector fVector = mainFactory.getFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(1, 1, 1));
            FVector fVector = mainFactory.getFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection (throw IllegalStateException)")
        void isCutThrowIllegalStateException() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FVector fVector = mainFactory.getFVector(-1, -1, -1, -2, -2, -2);

            assertThrows(IllegalStateException.class, () -> fPlane.isCut(fVector),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());
            FVector fVector = mainFactory.getFVector();

            FPlaneTestHelper.testValue(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(0, 1, 0));
            FLine fLine = mainFactory.getFLine(
                    mainFactory.getFVector(-1, 1, 0, 1, -1, 0));

            FPoint fPointRel = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            Optional<FPoint> fPointOpt = fPlane.getCommonFPoint(fLine);

            if (fPointOpt.isEmpty()) {
                fail("The common FPoint should exist");
            }

            FPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(mainFactory.getFPoint(fPointRel)),
                    "The intersecting FPoint is erroneous");
        }

        @Test
        @DisplayName("Get common FPoint (empty)")
        void getCommonFPointEmpty() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(0, 1, 0));
            FLine fLine = mainFactory.getFLine(
                    mainFactory.getFVector(-1, 1, 0, 1, 1, 0));

            FPoint fPointRel = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            assertTrue(fPlane.getCommonFPoint(fLine).isEmpty(),
                    "The FLine does not intersect with the FPlane");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector());
            FLine fLine = mainFactory.getFLine(mainFactory.getFVector(1, 1, 1));

            assertThrows(IllegalStateException.class, () -> fPlane.getCommonFPoint(fLine),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, argument)")
        void getCommonFPointThrowIllegalStateExceptionArgument() {
            FPlane fPlane = mainFactory.getFPlane(mainFactory.getFVector(0, 1, 0));
            FLine fLine = mainFactory.getFLine(mainFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fPlane.getCommonFPoint(fLine),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FPlane fPlane = mainFactory.getFPlane(RandomHelper.getTestVector());
            FLine fLine = mainFactory.getFLine(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(e -> fPlane.getCommonFPoint(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common FLine")
        void getCommonFLine() {
            FVector fVector1 = mainFactory.getFVector(1, 0, 0);
            FVector fVector2 = mainFactory.getFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = mainFactory.getFVector(1, 0, 0);
                fVector2 = mainFactory.getFVector(1, 0, 0);

                fVector1.setRandomAngle();
                fVector2.setRandomAngle(fVector1.getHead());

                fVector1.moveBase(RandomHelper.getTestPoint().div(100));
                fVector2.moveBase(RandomHelper.getTestPoint().div(100));
            }

            FPlane fPlane1 = mainFactory.getFPlane(fVector1);
            FPlane fPlane2 = mainFactory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonFLine(fPlane2);

            if (fLineOpt.isEmpty()) {
                fail("The common FLine should exist");
            }

            FLine fLine = fLineOpt.get();

            assertAll("Validate FLine",
                    () -> assertTrue(fLine.getBase().extBoolean(fPlane1.isPartOf()).get(0),
                            "The FLine base does not belong to FPlane 1"),
                    () -> assertTrue(fLine.getHead().extBoolean(fPlane1.isPartOf()).get(0),
                            "The FLine head does not belong to FPlane 1"),
                    () -> assertTrue(fLine.getBase().extBoolean(fPlane2.isPartOf()).get(0),
                            "The FLine base does not belong to FPlane 2"),
                    () -> assertTrue(fLine.getHead().extBoolean(fPlane2.isPartOf()).get(0),
                            "The FLine head does not belong to FPlane 2")
            );
        }

        @Test
        @DisplayName("Get common FLine (fail)")
        void getCommonFLineFail() {
            FVector fVector1 = mainFactory.getFVector(1, 0, 0);
            FVector fVector2 = mainFactory.getFVector(1, 0, 0);

            FPoint fPoint = RandomHelper.getTestPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            FPlane fPlane1 = mainFactory.getFPlane(fVector1);
            FPlane fPlane2 = mainFactory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonFLine(fPlane2);

            if (fLineOpt.isPresent()) {
                fail("The common FLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, origin)")
        void getCommonFLineThrowIllegalStateExceptionOrigin() {
            FVector fVector1 = mainFactory.getFVector();
            FVector fVector2 = mainFactory.getFVector(1, 1, 1);

            FPlane fPlane1 = mainFactory.getFPlane(fVector1);
            FPlane fPlane2 = mainFactory.getFPlane(fVector2);

            assertThrows(IllegalStateException.class, () -> fPlane1.getCommonFLine(fPlane2),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, argument)")
        void getCommonFLineThrowIllegalStateExceptionArgument() {
            FVector fVector1 = mainFactory.getFVector(1, 1, 1);
            FVector fVector2 = mainFactory.getFVector();

            FPlane fPlane1 = mainFactory.getFPlane(fVector1);
            FPlane fPlane2 = mainFactory.getFPlane(fVector2);

            assertThrows(IllegalStateException.class, () -> fPlane1.getCommonFLine(fPlane2),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (validate)")
        void getCommonFLineValidate() {
            FPlane fPlane1 = mainFactory.getFPlane(RandomHelper.getTestVector());
            FPlane fPlane2 = mainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneTestHelper.testValue(FPlane::getCommonFLine, fPlane1, fPlane2);
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FPlane fPlane = mainFactory.getFPlane();
            List<FPoint> disassembly = fPlane.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            assertAll("Validate FPoints",
                    () -> assertTrue(mainFactory.getFPoint(1, 2, 3).isExact(fPlane.getBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(mainFactory.getFPoint(4, 5, 6).isExact(fPlane.getHead()),
                            "The FPoint head value is erroneous")
            );
        }

    }
}

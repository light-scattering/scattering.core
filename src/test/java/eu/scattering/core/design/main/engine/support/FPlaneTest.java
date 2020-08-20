package eu.scattering.core.design.main.engine.support;

import eu.scattering.core.Config;
import eu.scattering.core.injection.EngineFactory;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;
import eu.scattering.core.design.main.engine.support.line.FLine;
import eu.scattering.core.design.main.engine.support.plane.FPlane;
import eu.scattering.core.design.main.engine.support.helper.FPlaneHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.util.Optional;

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
            FPlane fPlane = EngineFactory.getFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidate() {
            FPlane fPlane = EngineFactory.getFPlane();

            assertEquals(EngineFactory.getFVector(), fPlane.getOrigin(),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = EngineFactory.getFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = EngineFactory.getFPlane(fVector);

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
            FPoint fPointBase = EngineFactory.getFPoint(refAX, refAY, refAZ);
            FPoint fPointHead = EngineFactory.getFPoint(refBX, refBY, refBZ);
            FVector fVector = EngineFactory.getFVector(fPointBase, fPointHead);
            FPlane fPlane = EngineFactory.getFPlane(fVector);

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

            assertThrows(NullPointerException.class, () -> EngineFactory.getFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            FPlane fPlane = EngineFactory.getFPlane(fVectorA);

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
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = EngineFactory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = EngineFactory.getFPlane(fVector.copy());

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
            FPlane fPlaneA = EngineFactory.getFPlane(fVector);
            FPlane fPlaneB = EngineFactory.getFPlane().importFromJSON(fPlaneA.exportToJSON());

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
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::exportToJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = EngineFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = EngineFactory.getFPlane(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "FPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "FPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = EngineFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = EngineFactory.getFPlane(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "FPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "FPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FPlane fPlaneA = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPlane fPlaneB = EngineFactory.getFPlane(EngineFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::isExact, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = EngineFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = EngineFactory.getFPlane(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (inverted)")
        void isSimilarInverted() {
            FPlane fPlaneA = EngineFactory.getFPlane(RandomHelper.getTestVector());
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
            FPlane fPlaneA = EngineFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = EngineFactory.getFPlane(fVector.copy().moveForward(1.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "FPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "FPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FPlane fPlaneA = EngineFactory.getFPlane(EngineFactory.getFVector(0, 1, 0));
            FPlane fPlaneB = EngineFactory.getFPlane(EngineFactory.getFVector(0, 1, 0));

            FPlaneHelper.validateVal(FPlane::isSimilar, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = EngineFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = EngineFactory.getFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical FPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = EngineFactory.getFPlane(fVector);
            FPlane fPlaneB = EngineFactory.getFPlane(RandomHelper.getTestVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different FPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = EngineFactory.getFPlane(fVector);
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
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::copy, fPlane);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class FPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(EngineFactory.getFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.project()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::project, fPlane);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(EngineFactory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.reflect()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::reflect, fPlane);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFPoint(-1, 2, -1).add(0.5 * Config.getJitter());

            assertTrue(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFPoint(-1, 2, -1).add(1.5 * Config.getJitter());

            assertFalse(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fPlane.isPartOf()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location (validate)")
        void locationValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::isPartOf, fPlane);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extDouble(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fPlane.getDistance()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::getDistance, fPlane);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());
            FPoint fPoint = RandomHelper.getTestPoint();

            fPoint.ext(fPlane.setDistance(1));

            assertEquals(1, fPoint.extDouble(fPlane.getDistance()).get(0),
                    Config.getJitter(), "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.setDistance(1)),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(e -> e.setDistance(1), fPlane);
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFVector(1, 1, 1)
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
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FPoint fPoint = EngineFactory.getFVector(1, 1, 1)
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
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FPoint fPoint = EngineFactory.getFPoint(0, 3, 0);

            assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fPlane.isInHalfSpace()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine half-space (validate)")
        void isInHalfSpaceValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::isInHalfSpace, fPlane);
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FVector fVector = EngineFactory.getFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FVector fVector = EngineFactory.getFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FVector fVector = EngineFactory.getFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(1, 1, 1));
            FVector fVector = EngineFactory.getFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection (throw IllegalStateException)")
        void isCutThrowIllegalStateException() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FVector fVector = EngineFactory.getFVector(-1, -1, -1, -2, -2, -2);

            assertThrows(IllegalStateException.class, () -> fPlane.isCut(fVector),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());
            FVector fVector = EngineFactory.getFVector();

            FPlaneHelper.validateVal(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(0, 1, 0));
            FLine fLine = EngineFactory.getFLine(
                    EngineFactory.getFVector(-1, 1, 0, 1, -1, 0));

            FPoint fPointRel = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            Optional<FPoint> fPointOpt = fPlane.getCommonFPoint(fLine);

            if (fPointOpt.isEmpty()) {
                fail("The common FPoint should exist");
            }

            FPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(EngineFactory.getFPoint(fPointRel)),
                    "The intersecting FPoint is erroneous");
        }

        @Test
        @DisplayName("Get common FPoint (empty)")
        void getCommonFPointEmpty() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(0, 1, 0));
            FLine fLine = EngineFactory.getFLine(
                    EngineFactory.getFVector(-1, 1, 0, 1, 1, 0));

            FPoint fPointRel = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            assertTrue(fPlane.getCommonFPoint(fLine).isEmpty(),
                    "The FLine does not intersect with the FPlane");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector());
            FLine fLine = EngineFactory.getFLine(EngineFactory.getFVector(1, 1, 1));

            assertThrows(IllegalStateException.class, () -> fPlane.getCommonFPoint(fLine),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, argument)")
        void getCommonFPointThrowIllegalStateExceptionArgument() {
            FPlane fPlane = EngineFactory.getFPlane(EngineFactory.getFVector(0, 1, 0));
            FLine fLine = EngineFactory.getFLine(EngineFactory.getFVector());

            assertThrows(IllegalStateException.class, () -> fPlane.getCommonFPoint(fLine),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FPlane fPlane = EngineFactory.getFPlane(RandomHelper.getTestVector());
            FLine fLine = EngineFactory.getFLine(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(e -> fPlane.getCommonFPoint(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common FLine")
        void getCommonFLine() {
            FVector fVector1 = EngineFactory.getFVector(1, 0, 0);
            FVector fVector2 = EngineFactory.getFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = EngineFactory.getFVector(1, 0, 0);
                fVector2 = EngineFactory.getFVector(1, 0, 0);

                fVector1.setRandomAngle();
                fVector2.setRandomAngle(fVector1.getHead());

                fVector1.moveBase(RandomHelper.getTestPoint().div(100));
                fVector2.moveBase(RandomHelper.getTestPoint().div(100));
            }

            FPlane fPlane1 = EngineFactory.getFPlane(fVector1);
            FPlane fPlane2 = EngineFactory.getFPlane(fVector2);

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
            FVector fVector1 = EngineFactory.getFVector(1, 0, 0);
            FVector fVector2 = EngineFactory.getFVector(1, 0, 0);

            FPoint fPoint = RandomHelper.getTestPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            FPlane fPlane1 = EngineFactory.getFPlane(fVector1);
            FPlane fPlane2 = EngineFactory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonFLine(fPlane2);

            if (fLineOpt.isPresent()) {
                fail("The common FLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, origin)")
        void getCommonFLineThrowIllegalStateExceptionOrigin() {
            FVector fVector1 = EngineFactory.getFVector();
            FVector fVector2 = EngineFactory.getFVector(1, 1, 1);

            FPlane fPlane1 = EngineFactory.getFPlane(fVector1);
            FPlane fPlane2 = EngineFactory.getFPlane(fVector2);

            assertThrows(IllegalStateException.class, () -> fPlane1.getCommonFLine(fPlane2),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, argument)")
        void getCommonFLineThrowIllegalStateExceptionArgument() {
            FVector fVector1 = EngineFactory.getFVector(1, 1, 1);
            FVector fVector2 = EngineFactory.getFVector();

            FPlane fPlane1 = EngineFactory.getFPlane(fVector1);
            FPlane fPlane2 = EngineFactory.getFPlane(fVector2);

            assertThrows(IllegalStateException.class, () -> fPlane1.getCommonFLine(fPlane2),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (validate)")
        void getCommonFLineValidate() {
            FPlane fPlane1 = EngineFactory.getFPlane(RandomHelper.getTestVector());
            FPlane fPlane2 = EngineFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::getCommonFLine, fPlane1, fPlane2);
        }

    }
}

package eu.scattering.core.design.main.engine.support;

import eu.scattering.core.Config;
import eu.scattering.core.injection.MainFactory;
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
@DisplayName("IFPlane")
public class FPlaneTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFPlaneBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            FPlane fPlane = MainFactory.getFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidate() {
            FPlane fPlane = MainFactory.getFPlane();

            assertEquals(MainFactory.getFVector(), fPlane.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = MainFactory.getFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = MainFactory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
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
            FPlane fPlane = MainFactory.getFPlane(fVector);

            assertAll("Validate IFPoint values",
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
        @DisplayName("Construct with IFVector (throw NullPointerException)")
        void constructWithIFVectorThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> MainFactory.getFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            FPlane fPlane = MainFactory.getFPlane(fVectorA);

            FPlane fPlaneRef = fPlane.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fPlane.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fPlaneRef, fPlane, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = MainFactory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlane = MainFactory.getFPlane(fVector.copy());

            assertEquals(fVector, fPlane.getOrigin(), "The IFVector positions are erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector);
            FPlane fPlaneB = MainFactory.getFPlane().importFromJSON(fPlaneA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "IFPlane references should point at different objects"),
                    () -> assertEquals(fPlaneA.getOrigin(), fPlaneB.getOrigin(),
                            "The origin of IFPlanes should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::exportToJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = MainFactory.getFPlane(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "IFPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "IFPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = MainFactory.getFPlane(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "IFPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "IFPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FPlane fPlaneA = MainFactory.getFPlane(MainFactory.getFVector());
            FPlane fPlaneB = MainFactory.getFPlane(MainFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::isExact, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = MainFactory.getFPlane(fVector.copy().add(0.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "IFPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "IFPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (inverted)")
        void isSimilarInverted() {
            FPlane fPlaneA = MainFactory.getFPlane(RandomHelper.getTestVector());
            FPlane fPlaneB = fPlaneA.copy();

            fPlaneB.getOrigin().reflectHead().moveBase(RandomHelper.getTestPoint());
            fPlaneB.getOrigin().moveBase(fPlaneB.copy().getBase().ext(fPlaneA.project()));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "IFPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "IFPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = MainFactory.getFPlane(fVector.copy().moveForward(1.5 * Config.getJitter()));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "IFPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "IFPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FPlane fPlaneA = MainFactory.getFPlane(MainFactory.getFVector(0, 1, 0));
            FPlane fPlaneB = MainFactory.getFPlane(MainFactory.getFVector(0, 1, 0));

            FPlaneHelper.validateVal(FPlane::isSimilar, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector.copy());
            FPlane fPlaneB = MainFactory.getFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical IFPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector);
            FPlane fPlaneB = MainFactory.getFPlane(RandomHelper.getTestVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different IFPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = RandomHelper.getTestVector();
            FPlane fPlaneA = MainFactory.getFPlane(fVector);
            FPlane fPlaneB = fPlaneA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "IFPlanes represent different objects"),
                    () -> assertEquals(fPlaneA, fPlaneB,
                            "IFPlanes should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector());

            FPlaneHelper.validateVal(FPlane::copy, fPlane);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(MainFactory.getFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::project, fPlane);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(MainFactory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::reflect, fPlane);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFPoint(-1, 2, -1).add(0.5 * Config.getJitter());

            assertTrue(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFPoint(-1, 2, -1).add(1.5 * Config.getJitter());

            assertFalse(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (validate)")
        void locationValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::isPartOf, fPlane);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFPoint(0, 3, 0);

            FPoint relocation = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extDouble(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::getDistance, fPlane);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());
            FPoint fPoint = RandomHelper.getTestPoint();

            fPoint.ext(fPlane.setDistance(1));

            assertEquals(1, fPoint.extDouble(fPlane.getDistance()).get(0),
                    Config.getJitter(), "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(e -> e.setDistance(1), fPlane);
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFVector(1, 1, 1)
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
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FPoint fPoint = MainFactory.getFVector(1, 1, 1)
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
        @DisplayName("Determine half-space (validate)")
        void isInHalfSpaceValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::isInHalfSpace, fPlane);
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FVector fVector = MainFactory.getFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FVector fVector = MainFactory.getFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FVector fVector = MainFactory.getFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(1, 1, 1));
            FVector fVector = MainFactory.getFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());
            FVector fVector = MainFactory.getFVector();

            FPlaneHelper.validateVal(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common IFPoint")
        void getCommonIFPoint() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(0, 1, 0));
            FLine fLine = MainFactory.getFLine(
                    MainFactory.getFVector(-1, 1, 0, 1, -1, 0));

            FPoint fPointRel = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            Optional<FPoint> fPointOpt = fPlane.getCommonIFPoint(fLine);

            if (fPointOpt.isEmpty()) {
                fail("The common IFPoint should exist");
            }

            FPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(MainFactory.getFPoint(fPointRel)),
                    "The intersecting IFPoint is erroneous");
        }

        @Test
        @DisplayName("Get common IFPoint (empty)")
        void getCommonIFPointEmpty() {
            FPlane fPlane = MainFactory.getFPlane(MainFactory.getFVector(0, 1, 0));
            FLine fLine = MainFactory.getFLine(
                    MainFactory.getFVector(-1, 1, 0, 1, 1, 0));

            FPoint fPointRel = RandomHelper.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            assertTrue(fPlane.getCommonIFPoint(fLine).isEmpty(),
                    "The IFLine does not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Get common IFPoint (validate)")
        void getCommonIFPointValidate() {
            FPlane fPlane = MainFactory.getFPlane(RandomHelper.getTestVector());
            FLine fLine = MainFactory.getFLine(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(e -> fPlane.getCommonIFPoint(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common IFLine")
        void getCommonIFLine() {
            FVector fVector1 = MainFactory.getFVector(1, 0, 0);
            FVector fVector2 = MainFactory.getFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = MainFactory.getFVector(1, 0, 0);
                fVector2 = MainFactory.getFVector(1, 0, 0);

                fVector1.setRandomAngle();
                fVector2.setRandomAngle(fVector1.getHead());

                fVector1.moveBase(RandomHelper.getTestPoint().div(100));
                fVector2.moveBase(RandomHelper.getTestPoint().div(100));
            }

            FPlane fPlane1 = MainFactory.getFPlane(fVector1);
            FPlane fPlane2 = MainFactory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonIFLine(fPlane2);

            if (fLineOpt.isEmpty()) {
                fail("The common IFLine should exist");
            }

            FLine fLine = fLineOpt.get();

            assertAll("Validate IFLine",
                    () -> assertTrue(fLine.getBase().extBoolean(fPlane1.isPartOf()).get(0),
                            "The IFLine base does not belong to IFPlane 1"),
                    () -> assertTrue(fLine.getHead().extBoolean(fPlane1.isPartOf()).get(0),
                            "The IFLine head does not belong to IFPlane 1"),
                    () -> assertTrue(fLine.getBase().extBoolean(fPlane2.isPartOf()).get(0),
                            "The IFLine base does not belong to IFPlane 2"),
                    () -> assertTrue(fLine.getHead().extBoolean(fPlane2.isPartOf()).get(0),
                            "The IFLine head does not belong to IFPlane 2")
            );
        }

        @Test
        @DisplayName("Get common IFLine (fail)")
        void getCommonIFLineFail() {
            FVector fVector1 = MainFactory.getFVector(1, 0, 0);
            FVector fVector2 = MainFactory.getFVector(1, 0, 0);

            FPoint fPoint = RandomHelper.getTestPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            FPlane fPlane1 = MainFactory.getFPlane(fVector1);
            FPlane fPlane2 = MainFactory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonIFLine(fPlane2);

            if (fLineOpt.isPresent()) {
                fail("The common IFLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common IFLine (validate)")
        void getCommonIFLineValidate() {
            FPlane fPlane1 = MainFactory.getFPlane(RandomHelper.getTestVector());
            FPlane fPlane2 = MainFactory.getFPlane(RandomHelper.getTestVector());

            FPlaneHelper.validateVal(FPlane::getCommonIFLine, fPlane1, fPlane2);
        }

    }
}

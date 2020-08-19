package eu.scattering.core.main.engine.extension;

import eu.scattering.core.main.MainFactory;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.vector.IFVector;
import eu.scattering.core.main.engine.extension.line.IFLine;
import eu.scattering.core.main.engine.extension.plane.IFPlane;
import eu.scattering.core.main.engine.extension.helper.HelperIFPlane;
import eu.scattering.core.support.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static eu.scattering.core.Config.jitter;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFPlane")
public class IFPlaneTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFPlaneBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            IFPlane fPlane = MainFactory.getIFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidate() {
            IFPlane fPlane = MainFactory.getIFPlane();

            assertEquals(MainFactory.getIFVector(), fPlane.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = MainFactory.getIFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = MainFactory.getIFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
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
            IFPoint fPointBase = MainFactory.getIFPoint(refAX, refAY, refAZ);
            IFPoint fPointHead = MainFactory.getIFPoint(refBX, refBY, refBZ);
            IFVector fVector = MainFactory.getIFVector(fPointBase, fPointHead);
            IFPlane fPlane = MainFactory.getIFPlane(fVector);

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

            assertThrows(NullPointerException.class, () -> MainFactory.getIFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            IFPlane fPlane = MainFactory.getIFPlane(fVectorA);

            IFPlane fPlaneRef = fPlane.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fPlane.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fPlaneRef, fPlane, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = MainFactory.getIFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = MainFactory.getIFPlane(fVector.copy());

            assertEquals(fVector, fPlane.getOrigin(), "The IFVector positions are erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector);
            IFPlane fPlaneB = MainFactory.getIFPlane().importFromJSON(fPlaneA.exportToJSON());

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
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector());

            HelperIFPlane.validateVal(IFPlane::exportToJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector.copy());
            IFPlane fPlaneB = MainFactory.getIFPlane(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "IFPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "IFPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector.copy());
            IFPlane fPlaneB = MainFactory.getIFPlane(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "IFPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "IFPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            IFPlane fPlaneA = MainFactory.getIFPlane(MainFactory.getIFVector());
            IFPlane fPlaneB = MainFactory.getIFPlane(MainFactory.getIFVector());

            HelperIFPlane.validateVal(IFPlane::isExact, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector.copy());
            IFPlane fPlaneB = MainFactory.getIFPlane(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "IFPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "IFPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (inverted)")
        void isSimilarInverted() {
            IFPlane fPlaneA = MainFactory.getIFPlane(HelperRandom.getTestVector());
            IFPlane fPlaneB = fPlaneA.copy();

            fPlaneB.getOrigin().reflectHead().moveBase(HelperRandom.getTestPoint());
            fPlaneB.getOrigin().moveBase(fPlaneB.copy().getBase().ext(fPlaneA.project()));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "IFPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "IFPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector.copy());
            IFPlane fPlaneB = MainFactory.getIFPlane(fVector.copy().moveForward(1.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "IFPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "IFPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            IFPlane fPlaneA = MainFactory.getIFPlane(MainFactory.getIFVector(0, 1, 0));
            IFPlane fPlaneB = MainFactory.getIFPlane(MainFactory.getIFVector(0, 1, 0));

            HelperIFPlane.validateVal(IFPlane::isSimilar, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector.copy());
            IFPlane fPlaneB = MainFactory.getIFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical IFPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector);
            IFPlane fPlaneB = MainFactory.getIFPlane(HelperRandom.getTestVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different IFPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector());

            HelperIFPlane.validateVal(IFPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = MainFactory.getIFPlane(fVector);
            IFPlane fPlaneB = fPlaneA.copy();

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
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector());

            HelperIFPlane.validateVal(IFPlane::copy, fPlane);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(MainFactory.getIFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::project, fPlane);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(MainFactory.getIFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::reflect, fPlane);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFPoint(-1, 2, -1).add(0.5 * jitter);

            assertTrue(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFPoint(-1, 2, -1).add(1.5 * jitter);

            assertFalse(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (validate)")
        void locationValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::isPartOf, fPlane);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extDouble(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::getDistance, fPlane);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());
            IFPoint fPoint = HelperRandom.getTestPoint();

            fPoint.ext(fPlane.setDistance(1));

            assertEquals(1, fPoint.extDouble(fPlane.getDistance()).get(0),
                    jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(e -> e.setDistance(1), fPlane);
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .moveBase(-1, 2, -1)
                    .getHead();

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertTrue(fPoint.extBoolean(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (fail)")
        void isInHalfSpaceFail() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFPoint fPoint = MainFactory.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .reflectHead()
                    .moveBase(-1, 2, -1)
                    .getHead();

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertFalse(fPoint.extBoolean(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (validate)")
        void isInHalfSpaceValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::isInHalfSpace, fPlane);
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFVector fVector = MainFactory.getIFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFVector fVector = MainFactory.getIFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFVector fVector = MainFactory.getIFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(1, 1, 1));
            IFVector fVector = MainFactory.getIFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());
            IFVector fVector = MainFactory.getIFVector();

            HelperIFPlane.validateVal(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common IFPoint")
        void getCommonIFPoint() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(0, 1, 0));
            IFLine fLine = MainFactory.getIFLine(
                    MainFactory.getIFVector(-1, 1, 0, 1, -1, 0));

            IFPoint fPointRel = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            Optional<IFPoint> fPointOpt = fPlane.getCommonIFPoint(fLine);

            if (fPointOpt.isEmpty()) {
                fail("The common IFPoint should exist");
            }

            IFPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(MainFactory.getIFPoint(fPointRel)),
                    "The intersecting IFPoint is erroneous");
        }

        @Test
        @DisplayName("Get common IFPoint (empty)")
        void getCommonIFPointEmpty() {
            IFPlane fPlane = MainFactory.getIFPlane(MainFactory.getIFVector(0, 1, 0));
            IFLine fLine = MainFactory.getIFLine(
                    MainFactory.getIFVector(-1, 1, 0, 1, 1, 0));

            IFPoint fPointRel = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            assertTrue(fPlane.getCommonIFPoint(fLine).isEmpty(),
                    "The IFLine does not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Get common IFPoint (validate)")
        void getCommonIFPointValidate() {
            IFPlane fPlane = MainFactory.getIFPlane(HelperRandom.getTestVector());
            IFLine fLine = MainFactory.getIFLine(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(e -> fPlane.getCommonIFPoint(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common IFLine")
        void getCommonIFLine() {
            IFVector fVector1 = MainFactory.getIFVector(1, 0, 0);
            IFVector fVector2 = MainFactory.getIFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = MainFactory.getIFVector(1, 0, 0);
                fVector2 = MainFactory.getIFVector(1, 0, 0);

                fVector1.setRandomAngle();
                fVector2.setRandomAngle(fVector1.getHead());

                fVector1.moveBase(HelperRandom.getTestPoint().div(100));
                fVector2.moveBase(HelperRandom.getTestPoint().div(100));
            }

            IFPlane fPlane1 = MainFactory.getIFPlane(fVector1);
            IFPlane fPlane2 = MainFactory.getIFPlane(fVector2);

            Optional<IFLine> fLineOpt = fPlane1.getCommonIFLine(fPlane2);

            if (fLineOpt.isEmpty()) {
                fail("The common IFLine should exist");
            }

            IFLine fLine = fLineOpt.get();

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
            IFVector fVector1 = MainFactory.getIFVector(1, 0, 0);
            IFVector fVector2 = MainFactory.getIFVector(1, 0, 0);

            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            IFPlane fPlane1 = MainFactory.getIFPlane(fVector1);
            IFPlane fPlane2 = MainFactory.getIFPlane(fVector2);

            Optional<IFLine> fLineOpt = fPlane1.getCommonIFLine(fPlane2);

            if (fLineOpt.isPresent()) {
                fail("The common IFLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common IFLine (validate)")
        void getCommonIFLineValidate() {
            IFPlane fPlane1 = MainFactory.getIFPlane(HelperRandom.getTestVector());
            IFPlane fPlane2 = MainFactory.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::getCommonIFLine, fPlane1, fPlane2);
        }

    }
}

package eu.scattering.core.geometry.support;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.geometry.support.plane.IFPlane;
import eu.scattering.core.geometry.support.helper.HelperIFPlane;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static eu.scattering.core.Configuration.jitter;
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
            IFPlane fPlane = FactoryGeometry.getIFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane();

            assertEquals(FactoryGeometry.getIFVector(), fPlane.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

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
            IFPoint fPointBase = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

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

            assertThrows(NullPointerException.class, () -> FactoryGeometry.getIFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorA);

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
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector.copy());

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
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector);
            IFPlane fPlaneB = FactoryGeometry.getIFPlane().importFromJSON(fPlaneA.exportToJSON());

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
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector());

            HelperIFPlane.validateVal(IFPlane::exportToJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "IFPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "IFPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "IFPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "IFPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector());

            HelperIFPlane.validateVal(IFPlane::isExact, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "IFPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "IFPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (inverted)")
        void isSimilarInverted() {
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());
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
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy().moveForward(1.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "IFPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "IFPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(0, 1, 0));
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(0, 1, 0));

            HelperIFPlane.validateVal(IFPlane::isSimilar, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical IFPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector);
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(HelperRandom.getTestVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different IFPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector());

            HelperIFPlane.validateVal(IFPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector);
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
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector());

            HelperIFPlane.validateVal(IFPlane::copy, fPlane);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(FactoryGeometry.getIFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::project, fPlane);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(FactoryGeometry.getIFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::reflect, fPlane);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(-1, 2, -1).add(0.5 * jitter);

            assertTrue(fPoint.extLog(fPlane.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(-1, 2, -1).add(1.5 * jitter);

            assertFalse(fPoint.extLog(fPlane.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (validate)")
        void locationValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::isPartOf, fPlane);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extVal(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::getDistance, fPlane);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());
            IFPoint fPoint = HelperRandom.getTestPoint();

            fPoint.ext(fPlane.setDistance(1));

            assertEquals(1, fPoint.extVal(fPlane.getDistance()).get(0),
                    jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(e -> e.setDistance(1), fPlane);
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .moveBase(-1, 2, -1)
                    .getHead();

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertTrue(fPoint.extLog(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (fail)")
        void isInHalfSpaceFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .reflectHead()
                    .moveBase(-1, 2, -1)
                    .getHead();

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertFalse(fPoint.extLog(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (validate)")
        void isInHalfSpaceValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::isInHalfSpace, fPlane);
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());
            IFVector fVector = FactoryGeometry.getIFVector();

            HelperIFPlane.validateVal(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common IFPoint")
        void getCommonIFPoint() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(0, 1, 0));
            IFLine fLine = FactoryGeometry.getIFLine(
                    FactoryGeometry.getIFVector(-1, 1, 0, 1, -1, 0));

            IFPoint fPointRel = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            Optional<IFPoint> fPointOpt = fPlane.getCommonIFPoint(fLine);

            if (fPointOpt.isEmpty()) {
                fail("The common IFPoint should exist");
            }

            IFPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(FactoryGeometry.getIFPoint(fPointRel)),
                    "The intersecting IFPoint is erroneous");
        }

        @Test
        @DisplayName("Get common IFPoint (empty)")
        void getCommonIFPointEmpty() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(0, 1, 0));
            IFLine fLine = FactoryGeometry.getIFLine(
                    FactoryGeometry.getIFVector(-1, 1, 0, 1, 1, 0));

            IFPoint fPointRel = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            assertTrue(fPlane.getCommonIFPoint(fLine).isEmpty(),
                    "The IFLine does not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Get common IFPoint (validate)")
        void getCommonIFPointValidate() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());
            IFLine fLine = FactoryGeometry.getIFLine(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(e -> fPlane.getCommonIFPoint(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common IFLine")
        void getCommonIFLine() {
            IFVector fVector1 = FactoryGeometry.getIFVector(1, 0, 0);
            IFVector fVector2 = FactoryGeometry.getIFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = FactoryGeometry.getIFVector(1, 0, 0);
                fVector2 = FactoryGeometry.getIFVector(1, 0, 0);

                fVector1.setRandomAngle();
                fVector2.setRandomAngle(fVector1.getHead());

                fVector1.moveBase(HelperRandom.getTestPoint().div(100));
                fVector2.moveBase(HelperRandom.getTestPoint().div(100));
            }

            IFPlane fPlane1 = FactoryGeometry.getIFPlane(fVector1);
            IFPlane fPlane2 = FactoryGeometry.getIFPlane(fVector2);

            Optional<IFLine> fLineOpt = fPlane1.getCommonIFLine(fPlane2);

            if (fLineOpt.isEmpty()) {
                fail("The common IFLine should exist");
            }

            IFLine fLine = fLineOpt.get();

            assertAll("Validate IFLine",
                    () -> assertTrue(fLine.getBase().extLog(fPlane1.isPartOf()).get(0),
                            "The IFLine base does not belong to IFPlane 1"),
                    () -> assertTrue(fLine.getHead().extLog(fPlane1.isPartOf()).get(0),
                            "The IFLine head does not belong to IFPlane 1"),
                    () -> assertTrue(fLine.getBase().extLog(fPlane2.isPartOf()).get(0),
                            "The IFLine base does not belong to IFPlane 2"),
                    () -> assertTrue(fLine.getHead().extLog(fPlane2.isPartOf()).get(0),
                            "The IFLine head does not belong to IFPlane 2")
            );
        }

        @Test
        @DisplayName("Get common IFLine (fail)")
        void getCommonIFLineFail() {
            IFVector fVector1 = FactoryGeometry.getIFVector(1, 0, 0);
            IFVector fVector2 = FactoryGeometry.getIFVector(1, 0, 0);

            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            IFPlane fPlane1 = FactoryGeometry.getIFPlane(fVector1);
            IFPlane fPlane2 = FactoryGeometry.getIFPlane(fVector2);

            Optional<IFLine> fLineOpt = fPlane1.getCommonIFLine(fPlane2);

            if (fLineOpt.isPresent()) {
                fail("The common IFLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common IFLine (validate)")
        void getCommonIFLineValidate() {
            IFPlane fPlane1 = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());
            IFPlane fPlane2 = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            HelperIFPlane.validateVal(IFPlane::getCommonIFLine, fPlane1, fPlane2);
        }

    }
}

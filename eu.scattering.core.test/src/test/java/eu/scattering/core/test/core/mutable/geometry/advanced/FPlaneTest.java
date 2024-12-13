package eu.scattering.core.test.core.mutable.geometry.advanced;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.geometry.construct.line.FLine;
import eu.scattering.core.design.elements.algebra.geometry.construct.plane.FPlane;
import eu.scattering.core.test.core.mutable.geometry.advanced.support.FPlaneTestHelper;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static eu.scattering.core.test.Configuration.*;
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
            FPlane fPlane = factory.getFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidate() {
            FPlane fPlane = factory.getFPlane();

            assertTrue(factory.getFVector().isExact(fPlane.getOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = random.getFVector();
            FPlane fPlane = factory.getFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = random.getFVector();
            FPlane fPlane = factory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The FVector reference is erroneous");
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
            FPlane fPlane = factory.getFPlane(fVector);

            Assertions.assertAll("Validate FPoint values",
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

            Assertions.assertThrows(NullPointerException.class, () -> factory.getFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            FVector fVectorA = random.getFVector();
            FVector fVectorB = random.getFVector(fVectorA);
            FPlane fPlane = factory.getFPlane(fVectorA);

            FPlane fPlaneRef = fPlane.setOriginRef(fVectorB);

            Assertions.assertAll("Validate FLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fPlane.getOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fPlaneRef, fPlane, "The FLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            Assertions.assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            FVector fVector = random.getFVector();
            FPlane fPlane = factory.getFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = random.getFVector();
            FPlane fPlane = factory.getFPlane(fVector.copy());

            assertTrue(fVector.isExact(fPlane.getOrigin()), "The FVector positions are erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector);
            FPlane fPlaneB = factory.getFPlane().importFromJSON(fPlaneA.exportToJSON());

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlane references should point at different objects"),
                    () -> assertTrue(fPlaneA.getOrigin().isExact(fPlaneB.getOrigin()),
                            "The origin of FPlanes should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::exportToJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector.copy());
            FPlane fPlaneB = factory.getFPlane(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "FPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "FPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector.copy());
            FPlane fPlaneB = factory.getFPlane(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "FPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "FPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FPlane fPlaneA = factory.getFPlane(factory.getFVector());
            FPlane fPlaneB = factory.getFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::isExact, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector.copy());
            FPlane fPlaneB = factory.getFPlane(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (inverted)")
        void isSimilarInverted() {
            FPlane fPlaneA = factory.getFPlane(random.getFVector());
            FPlane fPlaneB = fPlaneA.copy();

            fPlaneB.getOrigin().reflectHead().moveBase(random.getFPoint());
            fPlaneB.getOrigin().moveBase(fPlaneB.copy().getBase().ext(fPlaneA.project()));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector.copy());
            FPlane fPlaneB = factory.getFPlane(fVector.copy().moveForward(1.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "FPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "FPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FPlane fPlaneA = factory.getFPlane(factory.getFVector(0, 1, 0));
            FPlane fPlaneB = factory.getFPlane(factory.getFVector(0, 1, 0));

            FPlaneTestHelper.testValue(FPlane::isSimilar, fPlaneA, fPlaneB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector.copy());
            FPlane fPlaneB = factory.getFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical FPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector);
            FPlane fPlaneB = factory.getFPlane(random.getFVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different FPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = random.getFVector();
            FPlane fPlaneA = factory.getFPlane(fVector);
            FPlane fPlaneB = fPlaneA.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlanes represent different objects"),
                    () -> assertTrue(fPlaneA.isExact(fPlaneB),
                            "FPlanes should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::copy, fPlane);
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class FPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(factory.getFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.project()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Project (validate)")
        void projectValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::project, fPlane);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(factory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.reflect()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::reflect, fPlane);
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).add(0.5 * jitter);

            assertTrue(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).add(1.5 * jitter);

            assertFalse(fPoint.extBoolean(fPlane.isPartOf()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fPlane.isPartOf()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location (validate)")
        void locationValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::isPartOf, fPlane);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extDouble(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fPlane.getDistance()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::getDistance, fPlane);
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = random.getFPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(3, fPoint.extDouble(fPlane.getDistanceP2()).get(0),
                    jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 (throw IllegalStateException)")
        void getDistanceP2ThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extDouble(fPlane.getDistanceP2()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::getDistanceP2, fPlane);
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FPlane fPlane = factory.getFPlane(random.getFVector());
            FPoint fPoint = random.getFPoint();

            fPoint.ext(fPlane.setDistance(1));

            assertEquals(1, fPoint.extDouble(fPlane.getDistance()).get(0),
                    jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.ext(fPlane.setDistance(1)),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance (validate)")
        void setDistanceValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(e -> e.setDistance(1), fPlane);
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFVector(1, 1, 1)
                    .mul(jitter)
                    .moveBase(-1, 2, -1)
                    .getRefHead();

            FPoint relocation = random.getFPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertTrue(fPoint.extBoolean(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (fail)")
        void isInHalfSpaceFail() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFVector(1, 1, 1)
                    .mul(jitter)
                    .reflectHead()
                    .moveBase(-1, 2, -1)
                    .getRefHead();

            FPoint relocation = random.getFPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertFalse(fPoint.extBoolean(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (throw IllegalStateException)")
        void isInHalfSpaceThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPoint.extBoolean(fPlane.isInHalfSpace()),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine half-space (validate)")
        void isInHalfSpaceValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::isInHalfSpace, fPlane);
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection (throw IllegalStateException)")
        void isCutThrowIllegalStateException() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FVector fVector = factory.getFVector(-1, -1, -1, -2, -2, -2);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isCut(fVector),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());
            FVector fVector = factory.getFVector();

            FPlaneTestHelper.testValue(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(0, 1, 0));
            FLine fLine = factory.getFLine(
                    factory.getFVector(-1, 1, 0, 1, -1, 0));

            FPoint fPointRel = random.getFPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            Optional<FPoint> fPointOpt = fPlane.getCommonFPoint(fLine);

            if (fPointOpt.isEmpty()) {
                Assertions.fail("The common FPoint should exist");
            }

            FPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(factory.getFPoint(fPointRel)),
                    "The intersecting FPoint is erroneous");
        }

        @Test
        @DisplayName("Get common FPoint (empty)")
        void getCommonFPointEmpty() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(0, 1, 0));
            FLine fLine = factory.getFLine(
                    factory.getFVector(-1, 1, 0, 1, 1, 0));

            FPoint fPointRel = random.getFPoint();

            fPlane.getOrigin().add(fPointRel);
            fLine.getOrigin().add(fPointRel);

            assertTrue(fPlane.getCommonFPoint(fLine).isEmpty(),
                    "The FLine does not intersect with the FPlane");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FPlane fPlane = factory.getFPlane(factory.getFVector());
            FLine fLine = factory.getFLine(factory.getFVector(1, 1, 1));

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.getCommonFPoint(fLine),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, argument)")
        void getCommonFPointThrowIllegalStateExceptionArgument() {
            FPlane fPlane = factory.getFPlane(factory.getFVector(0, 1, 0));
            FLine fLine = factory.getFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.getCommonFPoint(fLine),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FPlane fPlane = factory.getFPlane(random.getFVector());
            FLine fLine = factory.getFLine(random.getFVector());

            FPlaneTestHelper.testValue(e -> fPlane.getCommonFPoint(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common FLine")
        void getCommonFLine() {
            FVector fVector1 = factory.getFVector(1, 0, 0);
            FVector fVector2 = factory.getFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = factory.getFVector(1, 0, 0);
                fVector2 = factory.getFVector(1, 0, 0);

                fVector1.setRandomAngle();
                fVector2.setRandomAngle(fVector1.getRefHead());

                fVector1.moveBase(random.getFPoint().div(100));
                fVector2.moveBase(random.getFPoint().div(100));
            }

            FPlane fPlane1 = factory.getFPlane(fVector1);
            FPlane fPlane2 = factory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonFLine(fPlane2);

            if (fLineOpt.isEmpty()) {
                Assertions.fail("The common FLine should exist");
            }

            FLine fLine = fLineOpt.get();

            Assertions.assertAll("Validate FLine",
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
            FVector fVector1 = factory.getFVector(1, 0, 0);
            FVector fVector2 = factory.getFVector(1, 0, 0);

            FPoint fPoint = random.getFPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            FPlane fPlane1 = factory.getFPlane(fVector1);
            FPlane fPlane2 = factory.getFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getCommonFLine(fPlane2);

            if (fLineOpt.isPresent()) {
                Assertions.fail("The common FLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, origin)")
        void getCommonFLineThrowIllegalStateExceptionOrigin() {
            FVector fVector1 = factory.getFVector();
            FVector fVector2 = factory.getFVector(1, 1, 1);

            FPlane fPlane1 = factory.getFPlane(fVector1);
            FPlane fPlane2 = factory.getFPlane(fVector2);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane1.getCommonFLine(fPlane2),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, argument)")
        void getCommonFLineThrowIllegalStateExceptionArgument() {
            FVector fVector1 = factory.getFVector(1, 1, 1);
            FVector fVector2 = factory.getFVector();

            FPlane fPlane1 = factory.getFPlane(fVector1);
            FPlane fPlane2 = factory.getFPlane(fVector2);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane1.getCommonFLine(fPlane2),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (validate)")
        void getCommonFLineValidate() {
            FPlane fPlane1 = factory.getFPlane(random.getFVector());
            FPlane fPlane2 = factory.getFPlane(random.getFVector());

            FPlaneTestHelper.testValue(FPlane::getCommonFLine, fPlane1, fPlane2);
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FPlane fPlane = factory.getFPlane();
            List<FPoint> disassembly = fPlane.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fPlane.getBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fPlane.getHead()),
                            "The FPoint head value is erroneous")
            );
        }

    }
}

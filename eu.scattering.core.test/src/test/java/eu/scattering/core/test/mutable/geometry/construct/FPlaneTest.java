package eu.scattering.core.test.mutable.geometry.construct;

import eu.scattering.core.design.mutable.geometry.construct.line.FLine;
import eu.scattering.core.design.mutable.geometry.construct.plane.FPlane;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutable.geometry.construct.support.FPlaneTestHelper;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPlane")
public class FPlaneTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FPlaneBasicTest {

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

            assertTrue(factory.getFVector().isExact(fPlane.getRefOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlane = factory.getRefFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlane = factory.getRefFPlane(fVector);

            assertSame(fVector, fPlane.getRefOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with FVector (validate positions)")
        void constructWithFVectorValidatePositions() {
            double refAX = rand.nextDouble();
            double refAY = rand.nextDouble();
            double refAZ = rand.nextDouble();
            double refBX = rand.nextDouble();
            double refBY = rand.nextDouble();
            double refBZ = rand.nextDouble();
            FPoint fPointBase = factory.getFPoint(refAX, refAY, refAZ);
            FPoint fPointHead = factory.getFPoint(refBX, refBY, refBZ);
            FVector fVector = factory.getFVector(fPointBase, fPointHead);
            FPlane fPlane = factory.getRefFPlane(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refAX, fPlane.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fPlane.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fPlane.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fPlane.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fPlane.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fPlane.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPairPos3D")
        void constructWithFPairPos3D() {
            FPairPos3D fVector = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            FPlane fPlane = factory.getFPlane(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fPlane.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fPlane.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fPlane.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fPlane.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fPlane.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fPlane.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference core")
        void setRefCore() {
            FVector fVectorA = TestHelper.getRandFVector();
            FVector fVectorB = TestHelper.getRandFVector(fVectorA);

            FPlane fPlane = factory.getRefFPlane(fVectorA);
            FPlane fPlaneRef = fPlane.setRefOrigin(fVectorB);

            Assertions.assertAll("Validate FLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fPlane.getRefOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fPlaneRef, fPlane, "The FLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            FPlane fPlane = factory.getRefFPlane(TestHelper.getRandFVector());

            Assertions.assertThrows(NullPointerException.class, () -> fPlane.setRefOrigin(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get core")
        void getCore() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlane = factory.getRefFPlane(fVector);

            assertSame(fVector, fPlane.getRefOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlane = factory.getRefFPlane(fVector.copy());

            assertTrue(fVector.isExact(fPlane.getRefOrigin()), "The FVector positions are erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class FPlaneCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector);

            JSONObject json = fPlaneA.toJSON();

            FPlane fPlaneB = factory.getFPlane().applyStateFrom(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlane references should point at different objects"),
                    () -> assertNotSame(fPlaneA.getRefOrigin(), fPlaneB.getRefOrigin(),
                            "FPlane core references should point at different objects"),
                    () -> assertTrue(fPlaneA.getRefOrigin().isExact(fPlaneB.getRefOrigin()),
                            "The origin of FPlane should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::toJSON, fPlane);
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "FPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "FPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "FPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "FPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Is coplanar")
        void isCoplanar() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSamePlane(fPlaneB), "FPlanes should be coplanar"),
                    () -> assertTrue(fPlaneB.isSamePlane(fPlaneA), "FPlanes should be coplanar")
            );
        }

        @Test
        @DisplayName("Is coplanar (inverted)")
        void isCoplanarInverted() {
            FPlane fPlaneA = factory.getRefFPlane(TestHelper.getRandFVector());
            FPlane fPlaneB = fPlaneA.copy();

            fPlaneB.getRefOrigin().reflectHead().moveBase(TestHelper.getRandFPoint());

            var fPointA = fPlaneB.copy().getRefOrigin().getRefBase();
            fPlaneA.project(fPointA);

            fPlaneB.getRefOrigin().moveBase(fPointA);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSamePlane(fPlaneB), "FPlanes should be coplanar"),
                    () -> assertTrue(fPlaneB.isSamePlane(fPlaneA), "FPlanes should be coplanar")
            );
        }

        @Test
        @DisplayName("Is coplanar x (fail)")
        void isCoplanarXFail() {
            FVector fVector = factory.getFVector(1, 0, 0);
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy().addX(1.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSamePlane(fPlaneB), "FPlanes should not be coplanar"),
                    () -> assertFalse(fPlaneB.isSamePlane(fPlaneA), "FPlanes should not be coplanar")
            );
        }

        @Test
        @DisplayName("Is coplanar y (fail)")
        void isCoplanarYFail() {
            FVector fVector = factory.getFVector(0, 1, 0);
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy().addY(1.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSamePlane(fPlaneB), "FPlanes should not be coplanar"),
                    () -> assertFalse(fPlaneB.isSamePlane(fPlaneA), "FPlanes should not be coplanar")
            );
        }

        @Test
        @DisplayName("Is coplanar z (fail)")
        void isCoplanarZFail() {
            FVector fVector = factory.getFVector(0, 0, 1);
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy().addZ(1.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSamePlane(fPlaneB), "FPlanes should not be coplanar"),
                    () -> assertFalse(fPlaneB.isSamePlane(fPlaneA), "FPlanes should not be coplanar")
            );
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FPlane fPlaneA = factory.getRefFPlane(TestHelper.getRandFVector());
            FPlane fPlaneB = factory.getRefFPlane(TestHelper.getRandFVector(fPlaneA.getRefOrigin()));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "FPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "FPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector.copy());
            FPlane fPlaneB = factory.getRefFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical FPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector);
            FPlane fPlaneB = factory.getRefFPlane(TestHelper.getRandFVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different FPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::hashCode, fPlane);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector);
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
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::copy, fPlane);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector);
            FPlane fPlaneB = fPlaneA.copyZero();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlanes represent different objects"),
                    () -> assertTrue(fPlaneB.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                            "FPlanes should have the same values")
            );
        }

        @Test
        @DisplayName("Copy zero (validate)")
        void copyZeroValidate() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::copyZero, fPlane);
        }

        @Test
        @DisplayName("Export to FPairPos3D")
        void toPairPos3D() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FPlane fPlane = factory.getRefFPlane(fVector);
            FPairPos3D fPairPos3D = fPlane.toFPairPos3D();

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fPairPos3D.getPosA().getD0(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fPairPos3D.getPosA().getD1(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fPairPos3D.getPosA().getD2(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fPairPos3D.getPosB().getD0(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fPairPos3D.getPosB().getD1(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fPairPos3D.getPosB().getD2(),
                            "Head - The Y value is incorrect")
            );
        }
    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class FPlaneAdvancedTest {

        @Test
        @DisplayName("Project simple - X")
        void projectX() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.project(fPoint);

            assertTrue(factory.getFPoint(0, 2, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Y")
        void projectY() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.project(fPoint);

            assertTrue(factory.getFPoint(1, 0, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Z")
        void projectZ() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 0, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.project(fPoint);

            assertTrue(factory.getFPoint(1, 2, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project with offset")
        void projectWithOffset() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            fPlane.project(fPoint);

            assertTrue(factory.getFPoint(-1, 2, -1).addXYZ(offset).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.project(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect simple - X")
        void reflectX() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.reflect(fPoint);

            assertTrue(factory.getFPoint(-1, 2, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Y")
        void reflectY() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.reflect(fPoint);

            assertTrue(factory.getFPoint(1, -2, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Z")
        void reflectZ() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 0, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.reflect(fPoint);

            assertTrue(factory.getFPoint(1, 2, -3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fPlane.reflect(fPoint);

            assertTrue(factory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.reflect(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).addFactor(0.5 * epsilon);

            assertTrue(fPlane.isPartOf(fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).addFactor(1.5 * epsilon);

            assertFalse(fPlane.isPartOf(fPoint),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isPartOf(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            assertEquals(Math.sqrt(3), fPlane.getAtomicDistance(fPoint).get(0),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.getAtomicDistance(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FPlane fPlane = factory.getRefFPlane(TestHelper.getRandFVector());
            FPoint fPoint = TestHelper.getRandFPoint();

            fPlane.setDistance(fPoint, 1);

            assertEquals(1, fPlane.getAtomicDistance(fPoint).get(0),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.setDistance(fPoint, 1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFVector(1, 1, 1)
                    .mulFactor(epsilon)
                    .moveBase(-1, 2, -1)
                    .getRefHead();

            FPoint relocation = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            assertTrue(fPlane.isOnSide(fPoint),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (fail)")
        void isInHalfSpaceFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFVector(1, 1, 1)
                    .mulFactor(epsilon)
                    .reflectHead()
                    .moveBase(-1, 2, -1)
                    .getRefHead();

            FPoint relocation = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            assertFalse(fPlane.isOnSide(fPoint),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (throw IllegalStateException)")
        void isInHalfSpaceThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isOnSide(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCutA() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCutB() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCut(fVector), "The FVector should intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCutAFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCutBFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FVector fVector = factory.getFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCut(fVector), "The FVector should not intersect with the FPlane");
        }

        @Test
        @DisplayName("Determine intersection (throw IllegalStateException)")
        void isCutThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FVector fVector = factory.getFVector(-1, -1, -1, -2, -2, -2);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isCut(fVector),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine intersection (validate)")
        void isCutValidate() {
            FPlane fPlane = factory.getRefFPlane(TestHelper.getRandFVector());
            FVector fVector = factory.getFVector();

            FPlaneTestHelper.testValue(e -> e.isCut(fVector), fPlane);
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FLine fLine = factory.getRefFLine(
                    factory.getFVector(-1, 1, 0, 1, -1, 0));

            FPoint fPointRel = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(fPointRel);
            fLine.getRefOrigin().addXYZ(fPointRel);

            Optional<FPoint> fPointOpt = fPlane.getFPointAtIntersection(fLine);

            if (fPointOpt.isEmpty()) {
                Assertions.fail("The common FPoint should exist");
            }

            FPoint fPoint = fPointOpt.get();

            assertTrue(fPoint.isSimilar(fPointRel.copy()),
                    "The intersecting FPoint is erroneous");
        }

        @Test
        @DisplayName("Get common FPoint (empty)")
        void getCommonFPointEmpty() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FLine fLine = factory.getRefFLine(
                    factory.getFVector(-1, 1, 0, 1, 1, 0));

            FPoint fPointRel = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(fPointRel);
            fLine.getRefOrigin().addXYZ(fPointRel);

            assertTrue(fPlane.getFPointAtIntersection(fLine).isEmpty(),
                    "The FLine does not intersect with the FPlane");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FLine fLine = factory.getRefFLine(factory.getFVector(1, 1, 1));

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.getFPointAtIntersection(fLine),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalArgumentException)")
        void getCommonFPointThrowIllegalArgumentException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FLine fLine = factory.getRefFLine(factory.getFVector());

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPlane.getFPointAtIntersection(fLine),
                    "The argument is a non-directional FVector");
        }

        // TODO - reference error
        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FPlane fPlane = factory.getRefFPlane(TestHelper.getRandFVector());
            FLine fLine = factory.getRefFLine(TestHelper.getRandFVector());

            FPlaneTestHelper.testValue(e -> e.getFPointAtIntersection(fLine), fPlane);
        }

        @Test
        @DisplayName("Get common FLine")
        void getCommonFLine() {
            FVector fVector1 = factory.getFVector(1, 0, 0);
            FVector fVector2 = factory.getFVector(1, 0, 0);

            while (fVector1.isParallel(fVector2) || fVector1.isAntiParallel(fVector2)) {
                fVector1 = factory.getFVector(1, 0, 0);
                fVector2 = factory.getFVector(1, 0, 0);

                factory.getFRandEngine().rndAngle(fVector1);
                factory.getFRandEngine().rndAngle(fVector2, fVector1.getRefHead());

                fVector1.moveBase(TestHelper.getRandFPoint().divFactor(100));
                fVector2.moveBase(TestHelper.getRandFPoint().divFactor(100));
            }

            FPlane fPlane1 = factory.getRefFPlane(fVector1);
            FPlane fPlane2 = factory.getRefFPlane(fVector2);

            Optional<FVector> fLineOpt = fPlane1.getFLineOriginAtIntersection(fPlane2);

            if (fLineOpt.isEmpty()) {
                Assertions.fail("The common FLine should exist");
            }

            FVector fVector = fLineOpt.get();

            Assertions.assertAll("Validate FLine",
                    () -> assertTrue(fPlane1.isPartOf(fVector.getRefBase()),
                            "The FLine base does not belong to FPlane 1"),
                    () -> assertTrue(fPlane1.isPartOf(fVector.getRefHead()),
                            "The FLine head does not belong to FPlane 1"),
                    () -> assertTrue(fPlane2.isPartOf(fVector.getRefBase()),
                            "The FLine base does not belong to FPlane 2"),
                    () -> assertTrue(fPlane2.isPartOf(fVector.getRefHead()),
                            "The FLine head does not belong to FPlane 2")
            );
        }

        @Test
        @DisplayName("Get common FLine (fail)")
        void getCommonFLineFail() {
            FVector fVector1 = factory.getFVector(1, 0, 0);
            FVector fVector2 = factory.getFVector(1, 0, 0);

            FPoint fPoint = TestHelper.getRandFPoint();

            fVector1.moveBase(fPoint);
            fVector2.moveBase(fPoint);

            FPlane fPlane1 = factory.getRefFPlane(fVector1);
            FPlane fPlane2 = factory.getRefFPlane(fVector2);

            Optional<FVector> fVectorOp = fPlane1.getFLineOriginAtIntersection(fPlane2);

            if (fVectorOp.isPresent()) {
                Assertions.fail("The common FLine should not exist");
            }
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalStateException, origin)")
        void getCommonFLineThrowIllegalStateExceptionOrigin() {
            FVector fVector1 = factory.getFVector();
            FVector fVector2 = factory.getFVector(1, 1, 1);

            FPlane fPlane1 = factory.getRefFPlane(fVector1);
            FPlane fPlane2 = factory.getRefFPlane(fVector2);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane1.getFLineOriginAtIntersection(fPlane2),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalArgumentException)")
        void getCommonFLineThrowIllegalArgumentException() {
            FVector fVector1 = factory.getFVector(1, 1, 1);
            FVector fVector2 = factory.getFVector();

            FPlane fPlane1 = factory.getRefFPlane(fVector1);
            FPlane fPlane2 = factory.getRefFPlane(fVector2);

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPlane1.getFLineOriginAtIntersection(fPlane2),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FPlane fPlane = factory.getFPlane();
            List<FPoint> disassembly = fPlane.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fPlane.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fPlane.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }

    }
}

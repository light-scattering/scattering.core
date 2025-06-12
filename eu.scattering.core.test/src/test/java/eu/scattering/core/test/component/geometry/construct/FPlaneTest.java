package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.construct.support.FPlaneTestHelper;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.Iterator;
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
        @DisplayName("Construct with Construct")
        void constructWithConstruct() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRay = factory.getRefFRay(fVector);
            FPlane fPlane = factory.getRefFPlane(fRay);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with Construct (validate references)")
        void constructWithConstructValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRay = factory.getRefFRay(fVector);
            FPlane fPlane = factory.getRefFPlane(fRay);

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
        @DisplayName("Set reference core (throw NullPointerException)")
        void setRefCoreThrowNullPointerException() {
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

            FPlane fPlaneB = factory.getFPlane().set(json);

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
        @DisplayName("Apply state from")
        void applyStateFrom() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 2, 3, 4, 5, 6));
            FRay fPay = factory.getRefFRay(factory.getFVector(9, 8, 7, 6, 5, 4));

            FPlane results = fPlane.applyStateFrom(fPay);

            Assertions.assertAll("Validate FPlane",
                    () -> assertSame(fPlane, results,
                            "The reference should not change"),
                    () -> assertNotSame(fPlane.getRefOrigin(), fPay.getRefOrigin(),
                            "FPlane core references should point at different objects"),
                    () -> assertTrue(fPlane.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous"),
                    () -> assertTrue(fPay.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Apply state to")
        void applyStateTo() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 2, 3, 4, 5, 6));
            FRay fRay = factory.getRefFRay(factory.getFVector(9, 8, 7, 6, 5, 4));

            FPlane results = fPlane.applyStateTo(fRay);

            Assertions.assertAll("Validate FPlane",
                    () -> assertSame(fPlane, results,
                            "The reference should not change"),
                    () -> assertNotSame(fPlane.getRefOrigin(), fRay.getRefOrigin(),
                            "FPlane core references should point at different objects"),
                    () -> assertTrue(fPlane.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous"),
                    () -> assertTrue(fRay.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());

            FPlaneTestHelper.testValue(FPlane::toJSON, fPlane);
        }

        @Test
        @DisplayName("Set core with FPoint A")
        void setWithFPointA() {
            FPlane fPlane = factory.getFPlane();
            FPoint ptBase = factory.getFPoint(1, 1, 0);
            FPoint ptA = factory.getFPoint(2, 1, 0);
            FPoint ptB = factory.getFPoint(1, 2, 0);

            fPlane.set(ptBase, ptA, ptB);

            Assertions.assertAll("Validate FPlane",
                    () -> assertTrue(fPlane.isPartOf(factory.getFPoint(-1, -2, 0)),
                            "The FPoint should be a part of the plane"),
                    () -> assertFalse(fPlane.isPartOf(factory.getFPoint(1, 2, 3)),
                            "The FPoint should not be a part of the plane"),
                    () -> assertTrue(fPlane.isOnSide(factory.getFPoint(0, 0, 1)),
                            "The FPoint should be in a half-plane"),
                    () -> assertFalse(fPlane.isOnSide(factory.getFPoint(0, 0, -1)),
                            "The FPoint should not be in a half-plane")
            );
        }

        @Test
        @DisplayName("Set core with FPoint B")
        void setWithFPointB() {
            FPlane fPlane = factory.getFPlane();
            FPoint ptBase = factory.getFPoint(1, 1, 0);
            FPoint ptA = factory.getFPoint(2, 1, 0);
            FPoint ptB = factory.getFPoint(1, 2, 0);

            fPlane.set(ptBase, ptB, ptA);

            Assertions.assertAll("Validate FPlane",
                    () -> assertTrue(fPlane.isPartOf(factory.getFPoint(-1, -2, 0)),
                            "The FPoint should be a part of the plane"),
                    () -> assertFalse(fPlane.isPartOf(factory.getFPoint(1, 2, 3)),
                            "The FPoint should not be a part of the plane"),
                    () -> assertTrue(fPlane.isOnSide(factory.getFPoint(0, 0, -1)),
                            "The FPoint should be in a half-plane"),
                    () -> assertFalse(fPlane.isOnSide(factory.getFPoint(0, 0, 1)),
                            "The FPoint should not be in a half-plane")
            );
        }

        @Test
        @DisplayName("Set core with FPos3D A")
        void setWithFPos3DA() {
            FPlane fPlane = factory.getFPlane();
            FPos3D ptBase = factory.getFPos3D(1, 1, 0);
            FPos3D ptA = factory.getFPos3D(2, 1, 0);
            FPos3D ptB = factory.getFPos3D(1, 2, 0);

            fPlane.set(ptBase, ptA, ptB);

            Assertions.assertAll("Validate FPlane",
                    () -> assertTrue(fPlane.isPartOf(factory.getFPoint(-1, -2, 0)),
                            "The FPoint should be a part of the plane"),
                    () -> assertFalse(fPlane.isPartOf(factory.getFPoint(1, 2, 3)),
                            "The FPoint should not be a part of the plane"),
                    () -> assertTrue(fPlane.isOnSide(factory.getFPoint(0, 0, 1)),
                            "The FPoint should be in a half-plane"),
                    () -> assertFalse(fPlane.isOnSide(factory.getFPoint(0, 0, -1)),
                            "The FPoint should not be in a half-plane")
            );
        }

        @Test
        @DisplayName("Set core with FPos3D B")
        void setWithFPos3DB() {
            FPlane fPlane = factory.getFPlane();
            FPos3D ptBase = factory.getFPos3D(1, 1, 0);
            FPos3D ptA = factory.getFPos3D(2, 1, 0);
            FPos3D ptB = factory.getFPos3D(1, 2, 0);

            fPlane.set(ptBase, ptB, ptA);

            Assertions.assertAll("Validate FPlane",
                    () -> assertTrue(fPlane.isPartOf(factory.getFPoint(-1, -2, 0)),
                            "The FPoint should be a part of the plane"),
                    () -> assertFalse(fPlane.isPartOf(factory.getFPoint(1, 2, 3)),
                            "The FPoint should not be a part of the plane"),
                    () -> assertTrue(fPlane.isOnSide(factory.getFPoint(0, 0, -1)),
                            "The FPoint should be in a half-plane"),
                    () -> assertFalse(fPlane.isOnSide(factory.getFPoint(0, 0, 1)),
                            "The FPoint should not be in a half-plane")
            );
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
        @DisplayName("Exactness (geometry)")
        void isExactGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fPlaneA = factory.getRefFPlane(fVector.copy());
            Geometry fPlaneB = factory.getRefFPlane(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "FPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "FPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry, fail) A")
        void isExactGeometryFailA() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fPlane = factory.getRefFPlane(fVector.copy());
            Geometry fRay = factory.getRefFRay(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fPlane.isExact(fRay), "Geometries should not be equal"),
                    () -> assertFalse(fRay.isExact(fPlane), "Geometries should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry, fail) B")
        void isExactGeometryFailB() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fPlaneA = factory.getRefFPlane(fVector.copy());
            Geometry fPlaneB = factory.getRefFPlane(fVector.copy().addFactor(0.5 * epsilon));

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

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FPlane fPlaneA = factory.getRefFPlane(TestHelper.getRandFVector());
            FPlane fPlaneB = factory.getRefFPlane(TestHelper.getRandFVector(fPlaneA.getRefOrigin()));

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "FPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "FPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry)")
        void isSimilarGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fPlaneA = factory.getRefFPlane(fVector.copy());
            Geometry fPlaneB = factory.getRefFPlane(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "FPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "FPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry, fail) A")
        void isSimilarGeometryFailA() {
            Geometry fPlane = factory.getRefFPlane(TestHelper.getRandFVector());
            Geometry fRay = factory.getRefFRay(TestHelper.getRandFVector());

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fPlane.isSimilar(fRay), "Geometries should not be similar"),
                    () -> assertFalse(fRay.isSimilar(fPlane), "Geometries should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry, fail) B")
        void isSimilarGeometryFailB() {
            Geometry fPlaneA = factory.getRefFPlane(TestHelper.getRandFVector());
            Geometry fPlaneB = factory.getRefFPlane(TestHelper.getRandFVector());

            Assertions.assertAll("Validate similarity",
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
        @DisplayName("Copy")
        void copyGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlaneA = factory.getRefFPlane(fVector);
            Geometry fPlaneB = fPlaneA.copyGeometry();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "FPlanes represent different objects"),
                    () -> assertTrue(fPlaneA.isExact((FPlane) fPlaneB),
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
        @DisplayName("Project unit")
        void projectUnit() {
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
        @DisplayName("Project simple - X")
        void projectX() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(0, 2, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Y")
        void projectY() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(1, 0, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Z")
        void projectZ() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 0, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.project((Geometry) fPoint);

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

            fPlane.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(-1, 2, -1).addXYZ(offset).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.project((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect unit")
        void reflectUnit() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fPlane.reflect(fPoint);

            assertTrue(factory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect simple - X")
        void reflectX() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(-1, 2, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Y")
        void reflectY() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 1, 0));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(1, -2, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Z")
        void reflectZ() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 0, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fPlane.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(1, 2, -3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fPlane.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.reflect((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location unit")
        void isUnitPartOf() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).addFactor(0.5 * epsilon);

            assertTrue(fPlane.isPartOf(fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).addFactor(0.5 * epsilon);

            assertTrue(fPlane.isPartOf((Geometry) fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location unit (fail)")
        void isUnitPartOfFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).addFactor(1.5 * epsilon);

            assertFalse(fPlane.isPartOf(fPoint),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(-1, 2, -1).addFactor(1.5 * epsilon);

            assertFalse(fPlane.isPartOf((Geometry) fPoint),
                    "The distance should not be negligible");
        }
        @Test
        @DisplayName("Location unit epsilon")
        void isUnitPartOfEpsilon() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 0.1, 0));
            FPoint fPoint = factory.getFPoint(1, 4, 3);

            assertTrue(fPlane.isPartOf(fPoint, 5),
                    "The distance should be correct");
        }

        @Test
        @DisplayName("Location epsilon")
        void isPartOfEpsilon() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, -0.1, 0));
            FPoint fPoint = factory.getFPoint(1, 4, 3);

            assertTrue(fPlane.isPartOf((Geometry) fPoint, 5),
                    "The distance should be correct");
        }

        @Test
        @DisplayName("Location unit epsilon (fail)")
        void isUnitPartOfEpsilonFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, 0.1, 0));
            FPoint fPoint = factory.getFPoint(1, 4, 3);

            assertFalse(fPlane.isPartOf(fPoint, 3),
                    "The distance should not be correct");
        }

        @Test
        @DisplayName("Location epsilon (fail)")
        void isPartOfEpsilonFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(0, -0.1, 0));
            FPoint fPoint = factory.getFPoint(1, 4, 3);

            assertFalse(fPlane.isPartOf((Geometry) fPoint, 3),
                    "The distance should not be correct");
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isPartOf((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get unit distance")
        void getUnitDistance() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            assertEquals(Math.sqrt(3), fPlane.getDistance(fPoint),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.getDistance(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set unit distance")
        void setUnitDistance() {
            FPlane fPlane = factory.getRefFPlane(TestHelper.getRandFVector());
            FPoint fPoint = TestHelper.getRandFPoint();

            fPlane.setDistance(fPoint, 1);

            assertEquals(1, fPlane.getDistance(fPoint),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FPlane fPlane = factory.getRefFPlane(TestHelper.getRandFVector());
            FPoint fPoint = TestHelper.getRandFPoint();

            fPlane.setDistance((Geometry) fPoint, 1);

            assertEquals(1, fPlane.getDistance(fPoint),
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
        @DisplayName("Determine half-space with FPoint")
        void isFPointInHalfSpace() {
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
        @DisplayName("Determine half-space with FPoint (fail)")
        void isFPointInHalfSpaceFail() {
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
        @DisplayName("Determine half-space with FPoint (throw IllegalStateException)")
        void isFPointInHalfSpaceThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isOnSide(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Determine half-space with geometry")
        void isGeometryInHalfSpace() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            Geometry fGeometry = factory.getFVector(1, 1, 1)
                    .mulFactor(epsilon)
                    .moveBase(-1, 2, -1)
                    .getRefHead();

            FPoint relocation = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(relocation);
            fGeometry.toFPoints().iterator().next().addXYZ(relocation);

            assertTrue(fPlane.isOnSide(fGeometry),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space with geometry (fail)")
        void isGeometryInHalfSpaceFail() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(1, 1, 1));
            Geometry fGeometry = factory.getFVector(1, 1, 1)
                    .mulFactor(epsilon)
                    .reflectHead()
                    .moveBase(-1, 2, -1)
                    .getRefHead();

            FPoint relocation = TestHelper.getRandFPoint();

            fPlane.getRefOrigin().addXYZ(relocation);
            fGeometry.toFPoints().iterator().next().addXYZ(relocation);

            assertFalse(fPlane.isOnSide(fGeometry),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space with geometry (throw IllegalStateException)")
        void isGeometryInHalfSpaceThrowIllegalStateException() {
            FPlane fPlane = factory.getRefFPlane(factory.getFVector());
            Geometry fGeometry = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane.isOnSide(fGeometry),
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

                factory.getFRandEngShared().varyAngle(fVector1);
                factory.getFRandEngShared().varyAngle(fVector2);

                fVector1.moveBase(TestHelper.getRandFPoint().divFactor(100));
                fVector2.moveBase(TestHelper.getRandFPoint().divFactor(100));
            }

            FPlane fPlane1 = factory.getRefFPlane(fVector1);
            FPlane fPlane2 = factory.getRefFPlane(fVector2);

            Optional<FLine> fLineOpt = fPlane1.getFLineAtIntersection(fPlane2);

            if (fLineOpt.isEmpty()) {
                Assertions.fail("The common FLine should exist");
            }

            FVector fVector = fLineOpt.get().getRefOrigin();

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

            Optional<FLine> fVectorOp = fPlane1.getFLineAtIntersection(fPlane2);

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

            Assertions.assertThrows(IllegalStateException.class, () -> fPlane1.getFLineAtIntersection(fPlane2),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FLine (throw IllegalArgumentException)")
        void getCommonFLineThrowIllegalArgumentException() {
            FVector fVector1 = factory.getFVector(1, 1, 1);
            FVector fVector2 = factory.getFVector();

            FPlane fPlane1 = factory.getRefFPlane(fVector1);
            FPlane fPlane2 = factory.getRefFPlane(fVector2);

            Assertions.assertThrows(IllegalArgumentException.class, () -> fPlane1.getFLineAtIntersection(fPlane2),
                    "The argument is a non-directional FVector");
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FPlane fPlane = factory.getFPlane();
            Collection<FPoint> disassembly = fPlane.toFPoints();
            Iterator<FPoint> iterator = disassembly.iterator();

            iterator.next().set(1, 2, 3);
            iterator.next().set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fPlane.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fPlane.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }
    }
}

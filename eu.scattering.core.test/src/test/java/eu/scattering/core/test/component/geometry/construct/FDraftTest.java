package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.test.TestHelper;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.Iterator;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FDraft")
public class FDraftTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FDraftBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FDraft fDraft = factory.getFDraft();

            assertNotNull(fDraft, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidatePositions() {
            FDraft fDraft = factory.getFDraft();

            assertTrue(factory.getFVector().isExact(fDraft.getRefOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraft = factory.getRefFDraft(fVector);

            assertNotNull(fDraft, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraft = factory.getRefFDraft(fVector);

            assertSame(fVector, fDraft.getRefOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with Construct")
        void constructWithConstruct() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlane = factory.getRefFPlane(fVector);
            FDraft fDraft = factory.getRefFDraft(fPlane);

            assertNotNull(fDraft, "The instance is null");
        }

        @Test
        @DisplayName("Construct with Construct (validate references)")
        void constructWithConstructValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FPlane fPlane = factory.getRefFPlane(fVector);
            FDraft fDraft = factory.getRefFDraft(fPlane);

            assertSame(fVector, fDraft.getRefOrigin(), "The FVector reference is erroneous");
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
            FDraft fDraft = factory.getRefFDraft(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refAX, fDraft.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fDraft.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fDraft.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fDraft.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fDraft.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fDraft.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPairPos3D")
        void constructWithFPairPos3D() {
            var fVector = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            FDraft fDraft = factory.getFDraft(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fDraft.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fDraft.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fDraft.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fDraft.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fDraft.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fDraft.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference core")
        void setRefCore() {
            FVector fVectorA = TestHelper.getRandFVector();
            FVector fVectorB = TestHelper.getRandFVector(fVectorA);

            FDraft fDraft = factory.getRefFDraft(fVectorA);
            FDraft fDraftRef = fDraft.setRefOrigin(fVectorB);

            Assertions.assertAll("Validate FDraft references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fDraft.getRefOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fDraftRef, fDraft, "The FDraft reference should not change")
            );
        }

        @Test
        @DisplayName("Get core")
        void getCore() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraft = factory.getRefFDraft(fVector);

            assertSame(fVector, fDraft.getRefOrigin(), "The FVector reference is erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class FDraftCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector);

            JSONObject json = fDraftA.toJSON();

            FDraft fDraftB = factory.getFDraft().set(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fDraftA, fDraftB,
                            "FDraft references should point at different objects"),
                    () -> assertNotSame(fDraftA.getRefOrigin(), fDraftB.getRefOrigin(),
                            "FDraft core references should point at different objects"),
                    () -> assertTrue(fDraftA.getRefOrigin().isExact(fDraftB.getRefOrigin()),
                            "The origin of FDrafts should be exact")
            );
        }

        @Test
        @DisplayName("Apply state from")
        void applyStateFrom() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3, 4, 5, 6));
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(9, 8, 7, 6, 5, 4));

            FDraft results = fDraft.applyStateFrom(fPlane);

            Assertions.assertAll("Validate FDraft",
                    () -> assertSame(fDraft, results,
                            "The reference should not change"),
                    () -> assertNotSame(fDraft.getRefOrigin(), fPlane.getRefOrigin(),
                            "FDraft core references should point at different objects"),
                    () -> assertTrue(fDraft.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous"),
                    () -> assertTrue(fPlane.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Apply state to")
        void applyStateTo() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3, 4, 5, 6));
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(9, 8, 7, 6, 5, 4));

            FDraft results = fDraft.applyStateTo(fPlane);

            Assertions.assertAll("Validate FDraft",
                    () -> assertSame(fDraft, results,
                            "The reference should not change"),
                    () -> assertNotSame(fDraft.getRefOrigin(), fPlane.getRefOrigin(),
                            "FDraft core references should point at different objects"),
                    () -> assertTrue(fDraft.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous"),
                    () -> assertTrue(fPlane.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector.copy());
            FDraft fDraftB = factory.getRefFDraft(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fDraftA.isExact(fDraftB), "FDrafts should be equal"),
                    () -> assertTrue(fDraftB.isExact(fDraftB), "FDrafts should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector.copy());
            FDraft fDraftB = factory.getRefFDraft(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fDraftA.isExact(fDraftB), "FDrafts should not be equal"),
                    () -> assertFalse(fDraftB.isExact(fDraftA), "FDrafts should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry)")
        void isExactGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fDraftA = factory.getRefFDraft(fVector.copy());
            Geometry fDraftB = factory.getRefFDraft(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fDraftA.isExact(fDraftB), "FDrafts should be equal"),
                    () -> assertTrue(fDraftB.isExact(fDraftB), "FDrafts should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry, fail) A")
        void isExactGeometryFailA() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fDraft = factory.getRefFDraft(fVector.copy());
            Geometry fPlane = factory.getRefFPlane(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fDraft.isExact(fPlane), "Geometries should not be equal"),
                    () -> assertFalse(fPlane.isExact(fDraft), "Geometries should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry, fail) B")
        void isExactGeometryFailB() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fDraftA = factory.getRefFDraft(fVector.copy());
            Geometry fDraftB = factory.getRefFDraft(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fDraftA.isExact(fDraftB), "FDrafts should not be equal"),
                    () -> assertFalse(fDraftB.isExact(fDraftA), "FDrafts should not be equal")
            );
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector.copy());
            FDraft fDraftB = factory.getRefFDraft(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fDraftA.isSimilar(fDraftB), "FDrafts should be similar"),
                    () -> assertTrue(fDraftB.isSimilar(fDraftA), "FDrafts should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FDraft fDraftA = factory.getRefFDraft(TestHelper.getRandFVector());
            FDraft fDraftB = factory.getRefFDraft(TestHelper.getRandFVector(fDraftA.getRefOrigin()));

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fDraftA.isSimilar(fDraftB), "FDrafts should not be similar"),
                    () -> assertFalse(fDraftB.isSimilar(fDraftA), "FDrafts should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry)")
        void isSimilarGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fDraftA = factory.getRefFDraft(fVector.copy());
            Geometry fDraftB = factory.getRefFDraft(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fDraftA.isSimilar(fDraftB), "FDrafts should be similar"),
                    () -> assertTrue(fDraftB.isSimilar(fDraftA), "FDrafts should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry, fail) A")
        void isSimilarGeometryFailA() {
            Geometry fDraft = factory.getRefFDraft(TestHelper.getRandFVector());
            Geometry fPlane = factory.getRefFPlane(TestHelper.getRandFVector());

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fDraft.isSimilar(fPlane), "Geometries should not be similar"),
                    () -> assertFalse(fPlane.isSimilar(fDraft), "Geometries should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry, fail) B")
        void isSimilarGeometryFailB() {
            Geometry fDraftA = factory.getRefFDraft(TestHelper.getRandFVector());
            Geometry fDraftB = factory.getRefFDraft(TestHelper.getRandFVector());

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fDraftA.isSimilar(fDraftB), "FDrafts should not be similar"),
                    () -> assertFalse(fDraftB.isSimilar(fDraftA), "FDrafts should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector.copy());
            FDraft fDraftB = factory.getRefFDraft(fVector.copy());

            assertEquals(fDraftA.hashCode(), fDraftB.hashCode(),
                    "Two identical FDrafts should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector);
            FDraft fDraftB = factory.getRefFDraft(TestHelper.getRandFVector(fVector));

            assertNotEquals(fDraftA.hashCode(), fDraftB.hashCode(),
                    "Two different FDrafts should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector);
            FDraft fDraftB = fDraftA.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fDraftA, fDraftB,
                            "FDrafts represent different objects"),
                    () -> assertTrue(fDraftA.isExact(fDraftB),
                            "FDrafts should have the same values")
            );
        }

        @Test
        @DisplayName("Copy geometry")
        void copyGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            FDraft fDraftA = factory.getRefFDraft(fVector);
            Geometry fDraftB = fDraftA.copyGeometry();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fDraftA, fDraftB,
                            "FDrafts represent different objects"),
                    () -> assertTrue(fDraftA.isExact((FDraft) fDraftB),
                            "FDrafts should have the same values")
            );
        }

        @Test
        @DisplayName("Export to FPairPos3D")
        void toPairPos3D() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FDraft fDraft = factory.getRefFDraft(fVector);
            FPairPos3D fPairPos3D = fDraft.toFPairPos3D();

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
    class FDraftAdvancedTest {

        @Test
        @DisplayName("As FVector")
        void asFVector() {
            FDraft fDraft = factory.getFDraft();
            FVector fVector = fDraft.asFVector();

            Assertions.assertAll("Validate geometry",
                    () -> assertSame(fVector, fDraft.getRefOrigin(),
                            "The FVector reference should be the same")
            );
        }

        @Test
        @DisplayName("As FRay")
        void asFRay() {
            FDraft fDraft = factory.getFDraft();
            FRay fRay = fDraft.asFRay();

            Assertions.assertAll("Validate geometry",
                    () -> assertSame(fRay.getRefOrigin(), fDraft.getRefOrigin(),
                            "The FVector reference should be the same")
            );
        }

        @Test
        @DisplayName("As FLine")
        void asFLine() {
            FDraft fDraft = factory.getFDraft();
            FLine fLine = fDraft.asFLine();

            Assertions.assertAll("Validate geometry",
                    () -> assertSame(fLine.getRefOrigin(), fDraft.getRefOrigin(),
                            "The FVector reference should be the same")
            );
        }

        @Test
        @DisplayName("As FPlane")
        void asFPlane() {
            FDraft fDraft = factory.getFDraft();
            FPlane fPlane = fDraft.asFPlane();

            Assertions.assertAll("Validate geometry",
                    () -> assertSame(fPlane.getRefOrigin(), fDraft.getRefOrigin(),
                            "The FVector reference should be the same")
            );
        }

        @Test
        @DisplayName("As FSegment")
        void asFSegment() {
            FDraft fDraft = factory.getFDraft();
            FSegment fSegment = fDraft.asFSegment();

            Assertions.assertAll("Validate geometry",
                    () -> assertSame(fSegment.getRefOrigin(), fDraft.getRefOrigin(),
                            "The FVector reference should be the same")
            );
        }

        @Test
        @DisplayName("Project primitives")
        void projectPrimitives() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));

            assertThrows(RuntimeException.class, () -> fDraft.project(4, 5, 6),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Project unit")
        void projectUnit() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.project(fPoint),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Project geometry")
        void projectGeometry() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.project((Geometry) fPoint),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Reflect primitives")
        void reflectPrimitives() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));

            assertThrows(RuntimeException.class, () -> fDraft.reflect(4, 5, 6),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Reflect unit")
        void reflectUnit() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.reflect(fPoint),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Reflect geometry")
        void reflectGeometry() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.reflect((Geometry) fPoint),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Location unit")
        void locationUnit() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.isPartOf(fPoint),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Location geometry")
        void locationGeometry() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.isPartOf((Geometry) fPoint),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Location unit (epsilon)")
        void locationUnitEpsilon() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.isPartOf(fPoint, 1),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Location geometry (epsilon)")
        void locationGeometryEpsilon() {
            FDraft fDraft = factory.getRefFDraft(factory.getFVector(1, 2, 3));
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            assertThrows(RuntimeException.class, () -> fDraft.isPartOf((Geometry) fPoint, 1),
                    "The method should not be implemented");
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FDraft fDraft = factory.getFDraft();
            Collection<FPoint> disassembly = fDraft.toFPoints();
            Iterator<FPoint> iterator = disassembly.iterator();

            iterator.next().set(1, 2, 3);
            iterator.next().set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fDraft.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fDraft.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }
    }
}

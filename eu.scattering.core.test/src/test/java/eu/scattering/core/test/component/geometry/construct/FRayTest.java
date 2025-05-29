package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.construct.support.FRayTestHelper;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.Iterator;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FRay")
public class FRayTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FRayBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FRay fRay = factory.getFRay();

            assertNotNull(fRay, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidatePositions() {
            FRay fRay = factory.getFRay();

            assertTrue(factory.getFVector().isExact(fRay.getRefOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRay = factory.getRefFRay(fVector);

            assertNotNull(fRay, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRay = factory.getRefFRay(fVector);

            assertSame(fVector, fRay.getRefOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with Construct")
        void constructWithConstruct() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);
            FRay fRay = factory.getRefFRay(fSegment);

            assertNotNull(fRay, "The instance is null");
        }

        @Test
        @DisplayName("Construct with Segment (validate references)")
        void constructWithSegmentValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);
            FRay fRay = factory.getRefFRay(fSegment);

            assertSame(fVector, fRay.getRefOrigin(), "The FVector reference is erroneous");
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
            FRay fRay = factory.getRefFRay(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refAX, fRay.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fRay.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fRay.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fRay.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fRay.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fRay.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPairPos3D")
        void constructWithFPairPos3D() {
            var fVector = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            FRay fRay = factory.getFRay(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fRay.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fRay.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fRay.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fRay.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fRay.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fRay.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference core")
        void setRefCore() {
            FVector fVectorA = TestHelper.getRandFVector();
            FVector fVectorB = TestHelper.getRandFVector(fVectorA);

            FRay fRay = factory.getRefFRay(fVectorA);
            FRay fRayRef = fRay.setRefOrigin(fVectorB);

            Assertions.assertAll("Validate FRay references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fRay.getRefOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fRayRef, fRay, "The FRay reference should not change")
            );
        }

        @Test
        @DisplayName("Get core")
        void getCore() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRay = factory.getRefFRay(fVector);

            assertSame(fVector, fRay.getRefOrigin(), "The FVector reference is erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class FRayCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector);

            JSONObject json = fRayA.toJSON();

            FRay fRayB = factory.getFRay().set(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fRayA, fRayB,
                            "FRay references should point at different objects"),
                    () -> assertNotSame(fRayA.getRefOrigin(), fRayB.getRefOrigin(),
                            "FRay core references should point at different objects"),
                    () -> assertTrue(fRayA.getRefOrigin().isExact(fRayB.getRefOrigin()),
                            "The origin of FRay should be exact")
            );
        }

        @Test
        @DisplayName("Apply state from")
        void applyStateFrom() {
            FRay fRay = factory.getRefFRay(factory.getFVector(1, 2, 3, 4, 5, 6));
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(9, 8, 7, 6, 5, 4));

            FRay results = fRay.applyStateFrom(fSegment);

            Assertions.assertAll("Validate FRay",
                    () -> assertSame(fRay, results,
                            "The reference should not change"),
                    () -> assertNotSame(fRay.getRefOrigin(), fSegment.getRefOrigin(),
                            "FRay core references should point at different objects"),
                    () -> assertTrue(fRay.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous"),
                    () -> assertTrue(fSegment.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Apply state to")
        void applyStateTo() {
            FRay fRay = factory.getRefFRay(factory.getFVector(1, 2, 3, 4, 5, 6));
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(9, 8, 7, 6, 5, 4));

            FRay results = fRay.applyStateTo(fSegment);

            Assertions.assertAll("Validate FRay",
                    () -> assertSame(fRay, results,
                            "The reference should not change"),
                    () -> assertNotSame(fRay.getRefOrigin(), fSegment.getRefOrigin(),
                            "FRay core references should point at different objects"),
                    () -> assertTrue(fRay.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous"),
                    () -> assertTrue(fSegment.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector.copy());
            FRay fRayB = factory.getRefFRay(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fRayA.isExact(fRayB), "FRays should be equal"),
                    () -> assertTrue(fRayB.isExact(fRayB), "FRays should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector.copy());
            FRay fRayB = factory.getRefFRay(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fRayA.isExact(fRayB), "FRays should not be equal"),
                    () -> assertFalse(fRayB.isExact(fRayA), "FRays should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FRay fRayA = factory.getRefFRay(factory.getFVector());
            FRay fRayB = factory.getRefFRay(factory.getFVector());

            FRayTestHelper.testValue(FRay::isExact, fRayA, fRayB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector.copy());
            FRay fRayB = factory.getRefFRay(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fRayA.isSimilar(fRayB), "FRays should be similar"),
                    () -> assertTrue(fRayB.isSimilar(fRayA), "FRays should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FRay fRayA = factory.getRefFRay(TestHelper.getRandFVector());
            FRay fRayB = factory.getRefFRay(TestHelper.getRandFVector(fRayA.getRefOrigin()));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fRayA.isSimilar(fRayB), "FRays should not be similar"),
                    () -> assertFalse(fRayB.isSimilar(fRayA), "FRays should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector.copy());
            FRay fRayB = factory.getRefFRay(fVector.copy());

            assertEquals(fRayA.hashCode(), fRayB.hashCode(),
                    "Two identical FRays should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector);
            FRay fRayB = factory.getRefFRay(TestHelper.getRandFVector(fVector));

            assertNotEquals(fRayA.hashCode(), fRayB.hashCode(),
                    "Two different FRays should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FRay fRay = factory.getRefFRay(factory.getFVector());

            FRayTestHelper.testValue(FRay::hashCode, fRay);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = TestHelper.getRandFVector();
            FRay fRayA = factory.getRefFRay(fVector);
            FRay fRayB = fRayA.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fRayA, fRayB,
                            "FRays represent different objects"),
                    () -> assertTrue(fRayA.isExact(fRayB),
                            "FRays should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FRay fRay = factory.getRefFRay(factory.getFVector());

            FRayTestHelper.testValue(FRay::copy, fRay);
        }

        @Test
        @DisplayName("Export to FPairPos3D")
        void toPairPos3D() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FRay fRay = factory.getRefFRay(fVector);
            FPairPos3D fPairPos3D = fRay.toFPairPos3D();

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
    class FRayAdvancedTest {

        @Test
        @DisplayName("Project unit")
        void projectUnit() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            fRay.project(fPoint);

            assertTrue(factory.getFPoint(1, 1, 1).addXYZ(offset).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - X")
        void projectX() {
            FRay fRay = factory.getRefFRay(factory.getFVector(-1, 5, 5, 1, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fRay.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(1, 5, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Y")
        void projectY() {
            FRay fRay = factory.getRefFRay(factory.getFVector(5, -1, 5, 5, 1, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fRay.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(5, 2, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Z")
        void projectZ() {
            FRay fRay = factory.getRefFRay(factory.getFVector(5, 5, -1, 5, 5, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fRay.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(5, 5, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project with offset")
        void projectWithOffset() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            fRay.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(1, 1, 1).addXYZ(offset).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (below base)")
        void projectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FRay fRay = factory.getRefFRay(fVector);
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fRay.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(0, -9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (above head)")
        void projectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fRay.project((Geometry) fPoint);

            assertTrue(factory.getFPoint(3, 3, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fRay.project((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect unit")
        void reflectUnit() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fRay.reflect(fPoint);

            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - X")
        void reflectX() {
            FRay fRay = factory.getRefFRay(factory.getFVector(-1, 5, 5, 1, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fRay.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(1, 8, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Y")
        void reflectY() {
            FRay fRay = factory.getRefFRay(factory.getFVector(5, -1, 5, 5, 1, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fRay.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(9, 2, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Z")
        void reflectZ() {
            FRay fRay = factory.getRefFRay(factory.getFVector(5, 5, -1, 5, 5, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fRay.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(9, 8, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fRay.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (below base)")
        void reflectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fRay.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(0, -9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (above head")
        void reflectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fRay.reflect((Geometry) fPoint);

            assertTrue(factory.getFPoint(6, -3, 6).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fRay.reflect((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location unit")
        void isUnitPartOf() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * epsilon);

            assertTrue(fRay.isPartOf(fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * epsilon);

            assertTrue(fRay.isPartOf((Geometry) fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location unit (fail)")
        void isUnitPartOfFail() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * epsilon);

            assertFalse(fRay.isPartOf(fPoint),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * epsilon);

            assertFalse(fRay.isPartOf((Geometry) fPoint),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location unit epsilon")
        void isUnitPartOfEpsilon() {
            FRay fRay = factory.getRefFRay(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 3, 1);

            assertTrue(fRay.isPartOf(fPoint, 5),
                    "The distance should be correct");
        }

        @Test
        @DisplayName("Location epsilon")
        void isPartOfEpsilon() {
            FRay fRay = factory.getRefFRay(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 3, 1);

            assertTrue(fRay.isPartOf((Geometry) fPoint, 5),
                    "The distance should be correct");
        }

        @Test
        @DisplayName("Location unit epsilon (fail)")
        void isUnitPartOfEpsilonFail() {
            FRay fRay = factory.getRefFRay(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 1, 5);

            assertFalse(fRay.isPartOf(fPoint),
                    "The distance should not be correct");
        }

        @Test
        @DisplayName("Location epsilon (fail)")
        void isPartOfEpsilonFail() {
            FRay fRay = factory.getRefFRay(factory.getFVector(1, 0, 0));
            FPoint fPoint = factory.getFPoint(1, 5, 1);

            assertFalse(fRay.isPartOf((Geometry) fPoint),
                    "The distance should not be correct");
        }

        @Test
        @DisplayName("Location (below base)")
        void isPartOfPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(-4, -4, -4);

            assertFalse(fRay.isPartOf((Geometry) fPoint));
        }

        @Test
        @DisplayName("Location (above head)")
        void isPartOfPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(4, 4, 4).addY(0.5 * epsilon);

            assertTrue(fRay.isPartOf((Geometry) fPoint));
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fRay.isPartOf((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get unit distance")
        void getUnitDistance() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            assertEquals(Math.sqrt(6), fRay.getDistance(fPoint));
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            assertEquals(Math.sqrt(6), fRay.getDistance(fPoint));
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fRay.getDistance(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set unit distance")
        void setUnitDistance() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            fRay.setDistance(fPoint, 1);

            Assertions.assertTrue(Math.abs(fRay.getDistance(fPoint) - 1) < epsilon,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            fRay.setDistance((Geometry) fPoint, 1);

            Assertions.assertTrue(Math.abs(fRay.getDistance(fPoint) - 1) < epsilon,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (below base)")
        void setDistancePositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fRay.setDistance(fPoint, 1);

            assertTrue(fPoint.isSimilar(0, -9, 0));
        }

        @Test
        @DisplayName("Set distance (above head)")
        void setDistancePositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fRay.setDistance(fPoint, 1);

            assertEquals(1, fRay.getDistance(fPoint), epsilon);
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            fRay.setDistance(fPoint, -1);

            Assertions.assertTrue(Math.abs(fRay.getDistance(fPoint) - 1) < epsilon,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            FRay fRay = factory.getRefFRay(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fRay.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            FPoint fPointA = fPoint.copy();
            fRay.setDistance(fPointA,1);

            FPoint fPointB = fPoint.copy();
            fRay.setDistance(fPointB, -1);

            Assertions.assertTrue(fPointA.getDistance(fPointB) - 2 < epsilon,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fRay.setDistance(fPoint, 1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Move unit forward")
        void moveUnitForward() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fRay.shiftForward(fPoint, Math.sqrt(3));

            assertTrue(fPoint.isSimilar(factory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward")
        void moveForward() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fRay.shiftForward((Geometry) fPoint, Math.sqrt(3));

            assertTrue(fPoint.isSimilar(factory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (negative)")
        void moveForwardNegative() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fRay.shiftForward((Geometry) fPoint, -Math.sqrt(3));

            assertTrue(fPoint.isSimilar(factory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move forward (throw IllegalStateException)")
        void moveForwardThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint();

            Assertions.assertThrows(IllegalStateException.class,
                    () -> fRay.shiftForward((Geometry) fPoint, Math.sqrt(3)),
                    "The direction of the FRay is not defined");
        }

        @Test
        @DisplayName("Move unit backward")
        void moveUnitBackward() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fRay.shiftBackward(fPoint, Math.sqrt(3));

            assertTrue(fPoint.isSimilar(factory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fRay.shiftBackward((Geometry) fPoint, Math.sqrt(3));

            assertTrue(fPoint.isSimilar(factory.getFPoint(0, -1, -1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (negative)")
        void moveBackwardNegative() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FRay fRay = factory.getRefFRay(fVector.copy());
            FPoint fPoint = factory.getFPoint(1, 0, 0);

            fRay.shiftBackward((Geometry) fPoint, -Math.sqrt(3));

            assertTrue(fPoint.isSimilar(factory.getFPoint(2, 1, 1)),
                    "The translation is erroneous");
        }

        @Test
        @DisplayName("Move backward (throw IllegalStateException)")
        void moveBackwardThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());
            FPoint fPoint = factory.getFPoint();

            Assertions.assertThrows(IllegalStateException.class,
                    () -> fRay.shiftBackward((Geometry) fPoint, Math.sqrt(3)),
                    "The direction of the FRay is not defined");
        }

        @Test
        @DisplayName("Get FPoint")
        void getFPoint() {
            FRay fRay = factory.getRefFRay(TestHelper.getRandFVector());
            double length = fRay.getRefOrigin().getMagnitude();

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fRay.getFPointAtDistance(0).isSimilar(fRay.getRefOrigin().getRefBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(fRay.getFPointAtDistance(length).isSimilar(fRay.getRefOrigin().getRefHead()),
                            "The FPoint head is incorrect"),
                    () -> assertNotNull(fRay.getFPointAtDistance(2 * length),
                            "The distant FPoint exists")
            );
        }

        @Test
        @DisplayName("Get FPoint (throw IllegalStateException)")
        void getFPointThrowIllegalStateException() {
            FRay fRay = factory.getRefFRay(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fRay.getFPointAtDistance(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint (throw IllegalArgumentException)")
        void getFPointThrowIllegalArgumentException() {
            FRay fRay = factory.getRefFRay(TestHelper.getRandFVector());

            Assertions.assertThrows(IllegalArgumentException.class, () -> fRay.getFPointAtDistance(-1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint (validate)")
        void getFPointValidatePositions() {
            FVector fVectorOrigin = TestHelper.getRandFVector();
            FRay fRay = factory.getRefFRay(fVectorOrigin.copy());

            fRay.getFPointAtDistance(0);

            assertTrue(fVectorOrigin.isExact(fRay.getRefOrigin()),
                    "The position should remain unchanged");
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FRay fRay = factory.getFRay();
            Collection<FPoint> disassembly = fRay.explode();
            Iterator<FPoint> iterator = disassembly.iterator();

            iterator.next().set(1, 2, 3);
            iterator.next().set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fRay.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fRay.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }
    }
}

package eu.scattering.core.test.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.segment.FSegment;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.construct.support.FSegmentTestHelper;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.List;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FSegment")
public class FSegmentTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FSegmentBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FSegment fSegment = factory.getFSegment();

            assertNotNull(fSegment, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidatePositions() {
            FSegment fSegment = factory.getFSegment();

            assertTrue(factory.getFVector().isExact(fSegment.getRefOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertNotNull(fSegment, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertSame(fVector, fSegment.getRefOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with FVector (validate positions)")
        void constructWithFVectorValidatePositions() {
            double refAX = random.nextDouble();
            double refAY = random.nextDouble();
            double refAZ = random.nextDouble();
            double refBX = random.nextDouble();
            double refBY = random.nextDouble();
            double refBZ = random.nextDouble();
            FPoint fPointBase = factory.getFPoint(refAX, refAY, refAZ);
            FPoint fPointHead = factory.getFPoint(refBX, refBY, refBZ);
            FVector fVector = factory.getFVector(fPointBase, fPointHead);
            FSegment fSegment = factory.getRefFSegment(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refAX, fSegment.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fSegment.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fSegment.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fSegment.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fSegment.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fSegment.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPairPos3D")
        void constructWithFPairPos3D() {
            var fVector = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            FSegment fSegment = factory.getFSegment(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fSegment.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fSegment.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fSegment.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fSegment.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fSegment.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fSegment.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference core")
        void setRefCore() {
            FVector fVectorA = TestHelper.getRandomFVector();
            FVector fVectorB = TestHelper.getRandomFVector(fVectorA);

            FSegment fSegment = factory.getRefFSegment(fVectorA);
            FSegment fSegmentRef = fSegment.setRefOrigin(fVectorB);

            Assertions.assertAll("Validate FSegment references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fSegment.getRefOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fSegmentRef, fSegment, "The FSegment reference should not change")
            );
        }

        @Test
        @DisplayName("Get core")
        void getCore() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertSame(fVector, fSegment.getRefOrigin(), "The FVector reference is erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class FSegmentCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);

            JSONObject json = fSegmentA.toJSON();

            FSegment fSegmentB = factory.getFSegment().applyStateFrom(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fSegmentA, fSegmentB,
                            "FSegment references should point at different objects"),
                    () -> assertNotSame(fSegmentA.getRefOrigin(), fSegmentB.getRefOrigin(),
                            "FSegment core references should point at different objects"),
                    () -> assertTrue(fSegmentA.getRefOrigin().isExact(fSegmentB.getRefOrigin()),
                            "The origin of FSegment should be exact")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fSegmentA.isExact(fSegmentB), "FSegments should be equal"),
                    () -> assertTrue(fSegmentB.isExact(fSegmentB), "FSegments should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fSegmentA.isExact(fSegmentB), "FSegments should not be equal"),
                    () -> assertFalse(fSegmentB.isExact(fSegmentA), "FSegments should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FSegment fSegmentA = factory.getRefFSegment(factory.getFVector());
            FSegment fSegmentB = factory.getRefFSegment(factory.getFVector());

            FSegmentTestHelper.testValue(FSegment::isExact, fSegmentA, fSegmentB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fSegmentA.isSimilar(fSegmentB), "FSegments should be similar"),
                    () -> assertTrue(fSegmentB.isSimilar(fSegmentA), "FSegments should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FSegment fSegmentA = factory.getRefFSegment(TestHelper.getRandomFVector());
            FSegment fSegmentB = factory.getRefFSegment(TestHelper.getRandomFVector(fSegmentA.getRefOrigin()));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fSegmentA.isSimilar(fSegmentB), "FSegments should not be similar"),
                    () -> assertFalse(fSegmentB.isSimilar(fSegmentA), "FSegments should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy());

            assertEquals(fSegmentA.hashCode(), fSegmentB.hashCode(),
                    "Two identical FSegments should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);
            FSegment fSegmentB = factory.getRefFSegment(TestHelper.getRandomFVector(fVector));

            assertNotEquals(fSegmentA.hashCode(), fSegmentB.hashCode(),
                    "Two different FSegments should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());

            FSegmentTestHelper.testValue(FSegment::hashCode, fSegment);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);
            FSegment fSegmentB = fSegmentA.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fSegmentA, fSegmentB,
                            "FSegments represent different objects"),
                    () -> assertTrue(fSegmentA.isExact(fSegmentB),
                            "FSegments should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());

            FSegmentTestHelper.testValue(FSegment::copy, fSegment);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FVector fVector = TestHelper.getRandomFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);
            FSegment fSegmentB = fSegmentA.copyZero();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fSegmentA, fSegmentB,
                            "FSegments represent different objects"),
                    () -> assertTrue(fSegmentB.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                            "FSegments should have the same values")
            );
        }

        @Test
        @DisplayName("Copy zero (validate)")
        void copyZeroValidate() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());

            FSegmentTestHelper.testValue(FSegment::copyZero, fSegment);
        }

        @Test
        @DisplayName("Export to FPairPos3D")
        void toPairPos3D() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FSegment fSegment = factory.getRefFSegment(fVector);
            FPairPos3D fPairPos3D = fSegment.toFPairPos3D();

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
    class FSegmentAdvancedTest {

        @Test
        @DisplayName("Project simple - X")
        void projectX() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 5, 5, 1, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fSegment.project(fPoint);

            assertTrue(factory.getFPoint(1, 5, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Y")
        void projectY() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, -5, 5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fSegment.project(fPoint);

            assertTrue(factory.getFPoint(5, 2, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Z")
        void projectZ() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, -5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fSegment.project(fPoint);

            assertTrue(factory.getFPoint(5, 5, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project with offset")
        void projectWithOffset() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandomFPoint();

            fSegment.getRefOrigin().add(offset);
            fPoint.add(offset);

            fSegment.project(fPoint);

            assertTrue(factory.getFPoint(1, 1, 1).add(offset).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (below base)")
        void projectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector);
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fSegment.project(fPoint);

            assertTrue(factory.getFPoint(0, -9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (above head)")
        void projectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fSegment.project(fPoint);

            assertTrue(factory.getFPoint(0, 9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.project(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect simple - X")
        void reflectX() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-5, 5, 5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fSegment.reflect(fPoint);

            assertTrue(factory.getFPoint(1, 8, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Y")
        void reflectY() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, -5, 5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fSegment.reflect(fPoint);

            assertTrue(factory.getFPoint(9, 2, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Z")
        void reflectZ() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, -5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fSegment.reflect(fPoint);

            assertTrue(factory.getFPoint(9, 8, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fSegment.reflect(fPoint);

            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (below base)")
        void reflectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fSegment.reflect(fPoint);

            assertTrue(factory.getFPoint(0, -9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (above head")
        void reflectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fSegment.reflect(fPoint);

            assertTrue(factory.getFPoint(0, 9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.reflect(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fSegment.isPartOf(fPoint).get(0), "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fSegment.isPartOf(fPoint).get(0), "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (below base)")
        void isPartOfPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(-4, -4, -4);

            assertFalse(fSegment.isPartOf(fPoint).get(0));
        }

        @Test
        @DisplayName("Location (above head)")
        void isPartOfPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(4, 4, 4).addY(0.5 * jitter);

            assertFalse(fSegment.isPartOf(fPoint).get(0));
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.isPartOf(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandomFPoint();

            fSegment.getRefOrigin().add(offset);
            fPoint.add(offset);

            assertEquals(Math.sqrt(6), fSegment.getDistance(fPoint).get(0).orElseThrow());
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.getDistance(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fSegment.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(6, fSegment.getDistanceP2(fPoint).get(0).orElseThrow());
        }

        @Test
        @DisplayName("Get distance P2 (throw IllegalStateException)")
        void getDistanceP2ThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.getDistanceP2(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fSegment.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            fSegment.setDistance(fPoint, 1);

            Assertions.assertTrue(Math.abs(fSegment.getDistance(fPoint).get(0).orElseThrow() - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (below base)")
        void setDistancePositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fSegment.setDistance(fPoint, 1);

            assertTrue(fPoint.isSimilar(0, -9, 0));
        }

        @Test
        @DisplayName("Set distance (above head)")
        void setDistancePositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fSegment.setDistance(fPoint, 1);

            assertTrue(fPoint.isSimilar(0, 9, 0));
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fSegment.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            fSegment.setDistance(fPoint, -1);

            Assertions.assertTrue(Math.abs(fSegment.getDistance(fPoint).get(0).orElseThrow() - 1) < jitter,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fSegment.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            FPoint fPointA = fPoint.copy();
            fSegment.setDistance(fPointA,1);

            FPoint fPointB = fPoint.copy();
            fSegment.setDistance(fPointB, -1);

            Assertions.assertTrue(fPointA.getDistance(fPointB) - 2 < jitter,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.setDistance(fPoint, 1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FSegment fSegment = factory.getFSegment();
            List<FPoint> disassembly = fSegment.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fSegment.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fSegment.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }
    }
}

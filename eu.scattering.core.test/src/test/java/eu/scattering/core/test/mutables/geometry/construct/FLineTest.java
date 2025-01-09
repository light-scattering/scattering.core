package eu.scattering.core.test.mutables.geometry.construct;

import eu.scattering.core.design.mutables.geometry.construct.line.FLine;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.construct.support.FLineTestHelper;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FLine")
public class FLineTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FLineBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FLine fLine = factory.getFLine();

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate)")
        void constructValidatePositions() {
            FLine fLine = factory.getFLine();

            assertTrue(factory.getFVector().isExact(fLine.getRefOrigin()),
                    "The initial FVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLine = factory.getRefFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLine = factory.getRefFLine(fVector);

            assertSame(fVector, fLine.getRefOrigin(), "The FVector reference is erroneous");
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
            FLine fLine = factory.getRefFLine(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(refAX, fLine.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fLine.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fLine.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fLine.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fLine.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fLine.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPairPos3D")
        void constructWithFPairPos3D() {
            var fVector = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            FLine fLine = factory.getFLine(fVector);

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fLine.getRefOrigin().getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fLine.getRefOrigin().getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fLine.getRefOrigin().getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fLine.getRefOrigin().getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fLine.getRefOrigin().getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fLine.getRefOrigin().getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference core")
        void setRefCore() {
            FVector fVectorA = TestHelper.getRandomFVector();
            FVector fVectorB = TestHelper.getRandomFVector(fVectorA);

            FLine fLine = factory.getRefFLine(fVectorA);
            FLine fLineRef = fLine.setRefOrigin(fVectorB);

            Assertions.assertAll("Validate FLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "FVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getRefOrigin(), "The FVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The FLine reference should not change")
            );
        }

        @Test
        @DisplayName("Get core")
        void getCore() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLine = factory.getRefFLine(fVector);

            assertSame(fVector, fLine.getRefOrigin(), "The FVector reference is erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class FLineCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector);

            JSONObject json = fLineA.toJSON();

            FLine fLineB = factory.getFLine().applyStateFrom(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLine references should point at different objects"),
                    () -> assertNotSame(fLineA.getRefOrigin(), fLineB.getRefOrigin(),
                            "FLine core references should point at different objects"),
                    () -> assertTrue(fLineA.getRefOrigin().isExact(fLineB.getRefOrigin()),
                            "The origin of FLines should be exact")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isExact(fLineB), "FLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "FLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "FLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "FLines should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FLine fLineA = factory.getRefFLine(factory.getFVector());
            FLine fLineB = factory.getRefFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::isExact, fLineA, fLineB);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy().add(0.5 * jitter));

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FLine fLineA = factory.getRefFLine(TestHelper.getRandomFVector());
            FLine fLineB = factory.getRefFLine(TestHelper.getRandomFVector(fLineA.getRefOrigin()));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "FLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "FLines should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head)")
        void isSimilarLineAboveHead() {
            FVector fVector = TestHelper.getRandomFVector().normalize();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy());

            fLineB.getRefOrigin().shiftForward(10);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isCollinear(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isCollinear(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (above head, inverted)")
        void isSimilarLineAboveHeadInverted() {
            FVector fVector = TestHelper.getRandomFVector().normalize();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy());

            fLineB.getRefOrigin().shiftForward(10).reflectHead();

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isCollinear(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isCollinear(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base)")
        void isSimilarLineBelowBase() {
            FVector fVector = TestHelper.getRandomFVector().normalize();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy());

            fLineB.getRefOrigin().shiftBackward(10);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isCollinear(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isCollinear(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (below base, inverted)")
        void isSimilarLineBelowBaseInverted() {
            FVector fVector = TestHelper.getRandomFVector().normalize();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy());

            fLineB.getRefOrigin().shiftBackward(10).reflectHead();

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isCollinear(fLineB), "FLines should be similar"),
                    () -> assertTrue(fLineB.isCollinear(fLineA), "FLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarLineValidate() {
            FLine fLineA = factory.getRefFLine(TestHelper.getRandomFVector());
            FLine fLineB = factory.getRefFLine(TestHelper.getRandomFVector());

            FLineTestHelper.testValue(FLine::isCollinear, fLineA, fLineB);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector.copy());
            FLine fLineB = factory.getRefFLine(fVector.copy());

            assertEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two identical FLines should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector);
            FLine fLineB = factory.getRefFLine(TestHelper.getRandomFVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different FLines should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FLine fLine = factory.getRefFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::hashCode, fLine);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector);
            FLine fLineB = fLineA.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLines represent different objects"),
                    () -> assertTrue(fLineA.isExact(fLineB),
                            "FLines should have the same values")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FLine fLine = factory.getRefFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::copy, fLine);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FVector fVector = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fVector);
            FLine fLineB = fLineA.copyZero();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "FLines represent different objects"),
                    () -> assertTrue(fLineB.getRefOrigin().isExact(0, 0, 0, 0, 0, 0),
                            "FLines should have the same values")
            );
        }

        @Test
        @DisplayName("Copy zero (validate)")
        void copyZeroValidate() {
            FLine fLine = factory.getRefFLine(factory.getFVector());

            FLineTestHelper.testValue(FLine::copyZero, fLine);
        }

        @Test
        @DisplayName("Export to FPairPos3D")
        void toPairPos3D() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FLine fLine = factory.getRefFLine(fVector);
            FPairPos3D fPairPos3D = fLine.toFPairPos3D();

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
    class FLineAdvancedTest {

        @Test
        @DisplayName("Project simple - X")
        void projectX() {
            FLine fLine = factory.getRefFLine(factory.getFVector(-1, 5, 5, 1, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fLine.project(fPoint);

            assertTrue(factory.getFPoint(1, 5, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Y")
        void projectY() {
            FLine fLine = factory.getRefFLine(factory.getFVector(5, -1, 5, 5, 1, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fLine.project(fPoint);

            assertTrue(factory.getFPoint(5, 2, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Z")
        void projectZ() {
            FLine fLine = factory.getRefFLine(factory.getFVector(5, 5, -1, 5, 5, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fLine.project(fPoint);

            assertTrue(factory.getFPoint(5, 5, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project with offset")
        void projectWithOffset() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandomFPoint();

            fLine.getRefOrigin().add(offset);
            fPoint.add(offset);

            fLine.project(fPoint);

            assertTrue(factory.getFPoint(1, 1, 1).add(offset).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (below base)")
        void projectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FLine fLine = factory.getRefFLine(fVector);
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fLine.project(fPoint);

            assertTrue(factory.getFPoint(-3, -3, -3).isSimilar(fPoint));

            var angle = factory.getFTrigHelper().getAngleBetweenVectors(
                    factory.getFPos3D(-3, -3, -3),
                    factory.getFPos3D(0, 0, 0),
                    factory.getFPos3D(0, -9, 0)
            );

            assertTrue(Math.abs(angle - Math.PI / 2) < jitter);
        }

        @Test
        @DisplayName("Project (above head)")
        void projectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fLine.project(fPoint);

            assertTrue(factory.getFPoint(3, 3, 3).isSimilar(fPoint));

            var angle = factory.getFTrigHelper().getAngleBetweenVectors(
                    factory.getFPos3D(3, 3, 3),
                    factory.getFPos3D(0, 0, 0),
                    factory.getFPos3D(0, 9, 0)
            );

            assertTrue(Math.abs(angle - Math.PI / 2) < jitter);
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.project(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect simple - X")
        void reflectX() {
            FLine fLine = factory.getRefFLine(factory.getFVector(-1, 5, 5, 1, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fLine.reflect(fPoint);

            assertTrue(factory.getFPoint(1, 8, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Y")
        void reflectY() {
            FLine fLine = factory.getRefFLine(factory.getFVector(5, -1, 5, 5, 1, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fLine.reflect(fPoint);

            assertTrue(factory.getFPoint(9, 2, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Z")
        void reflectZ() {
            FLine fLine = factory.getRefFLine(factory.getFVector(5, 5, -1, 5, 5, 1));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            fLine.reflect(fPoint);

            assertTrue(factory.getFPoint(9, 8, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            fLine.reflect(fPoint);

            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (below base)")
        void reflectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fLine.reflect(fPoint);

            assertTrue(factory.getFPoint(-6, 3, -6).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (above head")
        void reflectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fLine.reflect(fPoint);

            assertTrue(factory.getFPoint(6, -3, 6).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.reflect(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * jitter);

            assertTrue(fLine.isPartOf(fPoint), "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * jitter);

            assertFalse(fLine.isPartOf(fPoint), "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (below base)")
        void isPartOfPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(-4, -4, -4).addY(0.5 * jitter);

            assertTrue(fLine.isPartOf(fPoint));
        }

        @Test
        @DisplayName("Location (above head)")
        void isPartOfPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(4, 4, 4).addY(0.5 * jitter);

            assertTrue(fLine.isPartOf(fPoint));
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.isPartOf(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandomFPoint();

            fLine.getRefOrigin().add(offset);
            fPoint.add(offset);

            assertEquals(Math.sqrt(6), fLine.getAtomicDistance(fPoint).get(0));
        }

        @Test
        @DisplayName("Get distance (throw IllegalStateException)")
        void getDistanceThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getAtomicDistance(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fLine.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(6, fLine.getAtomicDistanceP2(fPoint).get(0));
        }

        @Test
        @DisplayName("Get distance P2 (throw IllegalStateException)")
        void getDistanceP2ThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getAtomicDistanceP2(fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fLine.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            fLine.setDistance(fPoint, 1);

            Assertions.assertTrue(Math.abs(fLine.getAtomicDistance(fPoint).get(0) - 1) < jitter,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (below base)")
        void setDistancePositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fLine.setDistance(fPoint, 1);

            assertEquals(1, fLine.getAtomicDistance(fPoint).get(0), jitter);
        }

        @Test
        @DisplayName("Set distance (above head)")
        void setDistancePositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).sub(2);
            FLine fLine = factory.getRefFLine(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            fLine.setDistance(fPoint, 1);

            assertEquals(1, fLine.getAtomicDistance(fPoint).get(0), jitter);
        }

        @Test
        @DisplayName("Set distance A (negative)")
        void setDistanceNegativeA() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fLine.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            fLine.setDistance(fPoint, -1);

            Assertions.assertTrue(Math.abs(fLine.getAtomicDistance(fPoint).get(0) - 1) < jitter,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            FLine fLine = factory.getRefFLine(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandomFPoint();

            fLine.getRefOrigin().add(relocation);
            fPoint.add(relocation);

            FPoint fPointA = fPoint.copy();
            fLine.setDistance(fPointA,1);

            FPoint fPointB = fPoint.copy();
            fLine.setDistance(fPointB, -1);

            Assertions.assertTrue(fPointA.getDistance(fPointB) - 2 < jitter,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance (throw IllegalStateException)")
        void setDistanceThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.setDistance(fPoint, 1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at X")
        void getFPointAtX() {
            FPoint base = TestHelper.getRandomFPoint();
            FPoint head = TestHelper.getRandomFPoint(base);
            FLine fLine = factory.getRefFLine(factory.getFVector(base.copy(), head.copy()));

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPointAtX(0).isPresent(),
                            "The FPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtX(base.getX()).orElse(null))
                                    .isSimilar(fLine.getRefOrigin().getRefBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtX(head.getX()).orElse(null))
                                    .isSimilar(fLine.getRefOrigin().getRefHead()),
                            "The FPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint at X (empty)")
        void getFPointAtXEmpty() {
            FLine fLine = factory.getRefFLine(factory.getFVector(0, 1, 2));

            assertTrue(fLine.getFPointAtX(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at X (throw IllegalStateException)")
        void getFPointAtXThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPointAtX(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at X (validate)")
        void getFPointAtXValidate() {
            FLine fLine = factory.getRefFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.getFPointAtX(0), fLine);
        }

        @Test
        @DisplayName("Get FPoint at Y")
        void getFPointAtY() {
            FPoint base = TestHelper.getRandomFPoint();
            FPoint head = TestHelper.getRandomFPoint(base);
            FLine fLine = factory.getRefFLine(factory.getFVector(base.copy(), head.copy()));

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPointAtY(0).isPresent(),
                            "The FPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtY(base.getY()).orElse(null))
                                    .isSimilar(fLine.getRefOrigin().getRefBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtY(head.getY()).orElse(null))
                                    .isSimilar(fLine.getRefOrigin().getRefHead()),
                            "The FPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint at Y (empty)")
        void getFPointAtYEmpty() {
            FLine fLine = factory.getRefFLine(factory.getFVector(1, 0, 2));

            assertTrue(fLine.getFPointAtY(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at Y (throw IllegalStateException)")
        void getFPointAtYThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPointAtY(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at Y (validate)")
        void getFPointAtYValidate() {
            FLine fLine = factory.getRefFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.getFPointAtY(0), fLine);
        }

        @Test
        @DisplayName("Get FPoint at Z")
        void getFPointAtZ() {
            FPoint base = TestHelper.getRandomFPoint();
            FPoint head = TestHelper.getRandomFPoint(base);
            FLine fLine = factory.getRefFLine(factory.getFVector(base.copy(), head.copy()));

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLine.getFPointAtZ(0).isPresent(),
                            "The FPoint should be available"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtZ(base.getZ()).orElse(null))
                                    .isSimilar(fLine.getRefOrigin().getRefBase()),
                            "The FPoint base is incorrect"),
                    () -> assertTrue(Objects.requireNonNull(fLine.getFPointAtZ(head.getZ()).orElse(null))
                                    .isSimilar(fLine.getRefOrigin().getRefHead()),
                            "The FPoint head is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint at Z (empty)")
        void getFPointAtZEmpty() {
            FLine fLine = factory.getRefFLine(factory.getFVector(1, 2, 0));

            assertTrue(fLine.getFPointAtZ(0).isEmpty(),
                    "The FPoint should not be available");
        }

        @Test
        @DisplayName("Get FPoint at Z (throw IllegalStateException)")
        void getFPointAtZThrowIllegalStateException() {
            FLine fLine = factory.getRefFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLine.getFPointAtZ(1),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get FPoint at Z (validate)")
        void getFPointAtZValidate() {
            FLine fLine = factory.getRefFLine(factory.getFVector(1, 1, 1));

            FLineTestHelper.testValue(e -> e.getFPointAtZ(0), fLine);
        }

        @Test
        @DisplayName("Get common FPoint 2D XY")
        void getCommonFPoint2DXY() {
            FVector fLineAOrigin = TestHelper.getRandomFVector();
            FLine fLineA = factory.getRefFLine(fLineAOrigin);

            fLineAOrigin.getRefBase().setZ(0);
            fLineAOrigin.getRefHead().setZ(0);

            while (fLineAOrigin.isNonDirectional()) {
                fLineAOrigin.applyStateFrom(TestHelper.getRandomFVector());

                fLineAOrigin.getRefBase().setZ(0);
                fLineAOrigin.getRefHead().setZ(0);
            }

            FPoint fLineBOriginBase = TestHelper.getRandomFPoint();
            // TODO - Get random point
            FPoint fLineBOriginHead = fLineA.getRefOrigin().getRefHead();
            FVector fLineBOrigin = factory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            fLineBOriginBase.setZ(0);
            fLineBOriginBase.setZ(0);

            while (fLineA.isPartOf(fLineBOriginBase)) {
                fLineBOriginBase.applyStateFrom(TestHelper.getRandomFPoint());

                fLineBOriginBase.setZ(0);
            }

            Optional<FPoint> fPointRes = fLineA.getFPointAtIntersection(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLineA.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fLineA.getAtomicDistance(fPointRes.get()).get(0)+ ")"),
                    () -> assertTrue(fLineB.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fLineB.getAtomicDistance(fPointRes.get()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint 2D XY (simple)")
        void getCommonFPoint2DXYSimple() {
            FVector fLineAOrigin = factory.getFVector(0, 0, 0, 1, 0, 0);
            FLine fLineA = factory.getRefFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(1, -1, 0, 3, 1, 0);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            FPoint fPointRel = TestHelper.getRandomFPoint().setZ(0);

            fLineA.getRefOrigin().add(fPointRel);
            fLineB.getRefOrigin().add(fPointRel);

            Optional<FPoint> fPointRes = fLineA.getFPointAtIntersection(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLineA.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 1"),
                    () -> assertTrue(fLineB.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 2")
            );
        }

        @Test
        @DisplayName("Get common FPoint 2D XY (fail)")
        void getCommonFPoint2DXYFail() {
            FVector fLineAOrigin = factory.getFVector(1, 0, 0);
            FLine fLineA = factory.getRefFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(-1, 1, 0, 1, 1, 0);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            assertTrue(fLineA.getFPointAtIntersection(fLineB).isEmpty(),
                    "The intersecting point is non-existent");
        }

        @Test
        @DisplayName("Get common FPoint")
        void getCommonFPoint() {
            FLine fLineA = factory.getRefFLine(TestHelper.getRandomFVector());

            FPoint fLineBOriginBase = TestHelper.getRandomFPoint();
            // TODO - Get random point on the line
            FPoint fLineBOriginHead = fLineA.getRefOrigin().getRefHead();
            FLine fLineB = factory.getRefFLine(factory.getFVector(fLineBOriginBase, fLineBOriginHead));

            while (fLineA.isPartOf(fLineBOriginBase)) {
                fLineBOriginBase.applyStateFrom(TestHelper.getRandomFPoint());
            }

            Optional<FPoint> fPointRes = fLineA.getFPointAtIntersection(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fLineA.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fLineA.getAtomicDistance(fPointRes.get()).get(0)+ ")"),
                    () -> assertTrue(fLineB.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fLineB.getAtomicDistance(fPointRes.get()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint (static X)")
        void getCommonFPointStaticX() {
            FVector fLineAOrigin = factory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = factory.getRefFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(1, 0, 0, 1, 3, 0);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getFPointAtIntersection(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().isExact(factory.getFPoint(1, 1, 0)),
                            "The FPoint is erroneous"),
                    () -> assertTrue(fLineA.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fLineA.getAtomicDistance(fPointRes.get()).get(0)+ ")"),
                    () -> assertTrue(fLineB.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fLineB.getAtomicDistance(fPointRes.get()).get(0)+ ")")
            );
        }
        @Test
        @DisplayName("Get common FPoint (static Y)")
        void getCommonFPointStaticY() {
            FVector fLineAOrigin = factory.getFVector(1, 1, 0, 1, 1, 1);
            FLine fLineA = factory.getRefFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(0, 1, 0, 3, 1, 0);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getFPointAtIntersection(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().isExact(factory.getFPoint(1, 1, 0)),
                            "The FPoint is erroneous"),
                    () -> assertTrue(fLineA.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fLineA.getAtomicDistance(fPointRes.get()).get(0)+ ")"),
                    () -> assertTrue(fLineB.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fLineB.getAtomicDistance(fPointRes.get()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint (static Z)")
        void getCommonFPointStaticZ() {
            FVector fLineAOrigin = factory.getFVector(0, 1, 1, 2, 1, 1);
            FLine fLineA = factory.getRefFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(1, 0, 1, 1, 2, 1);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            Optional<FPoint> fPointRes = fLineA.getFPointAtIntersection(fLineB);
            Assertions.assertTrue(fPointRes.isPresent(),"FLines should have one intersecting FPoint");

            Assertions.assertAll("Validate FPoint",
                    () -> assertTrue(fPointRes.get().isExact(factory.getFPoint(1, 1, 1)),
                            "The FPoint is erroneous"),
                    () -> assertTrue(fLineA.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 1 " +
                                    "(distance: " + fLineA.getAtomicDistance(fPointRes.get()).get(0)+ ")"),
                    () -> assertTrue(fLineB.isPartOf(fPointRes.get()),
                            "The FPoint should be part of FLine 2 " +
                                    "(distance: " + fLineB.getAtomicDistance(fPointRes.get()).get(0)+ ")")
            );
        }

        @Test
        @DisplayName("Get common FPoint (fail)")
        void getCommonFPointFail() {
            FLine fLineA = factory.getRefFLine(TestHelper.getRandomFVector());

            FPoint fLineBOriginHead = TestHelper.getRandomFPoint();

            while (fLineA.isPartOf(fLineBOriginHead)) {
                fLineBOriginHead = TestHelper.getRandomFPoint();
            }

            var fLineBOriginBase = fLineBOriginHead.copy();
            fLineA.project(fLineBOriginBase);

            FVector fLineBOrigin = factory.getFVector(fLineBOriginBase, fLineBOriginHead);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            fLineBOrigin.moveBase(fLineA.getRefOrigin().getRefBase());

            FVector fVectorDrift = fLineA.getRefOrigin().copy()
                    .setCrossProduct(fLineBOrigin)
                    .setLength(1.5 * jitter);

            fLineBOrigin.getRefBase().applyStateFrom(fVectorDrift.getRefHead());

            assertTrue(fLineA.getFPointAtIntersection(fLineB).isEmpty(),
                    "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (fail, simple)")
        void getCommonFPointFailSimple() {
            FVector fLineAOrigin = factory.getFVector(1, 0, 0);
            FLine fLineA = factory.getRefFLine(fLineAOrigin);
            FVector fLineBOrigin = factory.getFVector(0, 1, 0, 0, 0, 1);
            FLine fLineB = factory.getRefFLine(fLineBOrigin);

            assertTrue(fLineA.getFPointAtIntersection(fLineB).isEmpty(), "The intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (fail, same line)")
        void getCommonFPointFailSameLine() {
            FLine fLineA = factory.getRefFLine(factory.getFVector(1, 1, 1));
            FLine fLineB = factory.getRefFLine(factory.getFVector(-1, -1, -1));

            assertTrue(fLineA.getFPointAtIntersection(fLineB).isEmpty(),
                    "Origins form the same FLine, the intersecting point should be non-existent");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, origin)")
        void getCommonFPointThrowIllegalStateExceptionOrigin() {
            FLine fLineA = factory.getRefFLine(factory.getFVector());
            FLine fLineB = factory.getRefFLine(factory.getFVector(-1, -1, -1));

            Assertions.assertThrows(IllegalStateException.class, () -> fLineA.getFPointAtIntersection(fLineB),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (throw IllegalStateException, argument)")
        void getCommonFPointThrowIllegalStateExceptionArgument() {
            FLine fLineA = factory.getRefFLine(factory.getFVector(-1, -1, -1));
            FLine fLineB = factory.getRefFLine(factory.getFVector());

            Assertions.assertThrows(IllegalStateException.class, () -> fLineA.getFPointAtIntersection(fLineB),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get common FPoint (validate)")
        void getCommonFPointValidate() {
            FLine fLineA = factory.getRefFLine(TestHelper.getRandomFVector());
            FLine fLineB = factory.getRefFLine(TestHelper.getRandomFVector());

            FLineTestHelper.testValue(FLine::getFPointAtIntersection, fLineA, fLineB);
        }

        @Test
        @DisplayName("Disassemble")
        void disassemble() {
            FLine fLine = factory.getFLine();
            List<FPoint> disassembly = fLine.disassemble();

            disassembly.get(0).set(1, 2, 3);
            disassembly.get(1).set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fLine.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fLine.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }
    }
}

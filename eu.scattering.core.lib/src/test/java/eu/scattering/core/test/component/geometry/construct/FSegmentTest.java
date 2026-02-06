package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.geometry.construct.support.FSegmentTestHelper;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.Iterator;

import static eu.scattering.core.test.Config.*;
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
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertNotNull(fSegment, "The instance is null");
        }

        @Test
        @DisplayName("Construct with FVector (validate references)")
        void constructWithFVectorValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertSame(fVector, fSegment.getRefOrigin(), "The FVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with Construct")
        void constructWithConstruct() {
            FVector fVector = TestHelper.getRandFVector();
            FLine fLine = factory.getRefFLine(fVector);
            FSegment fSegment = factory.getRefFSegment(fLine);

            assertNotNull(fSegment, "The instance is null");
        }

        @Test
        @DisplayName("Construct with Construct (validate references)")
        void constructWithConstructValidateReferences() {
            FVector fVector = TestHelper.getRandFVector();
            FLine fLine = factory.getRefFLine(fVector);
            FSegment fSegment = factory.getRefFSegment(fLine);

            assertSame(fVector, fSegment.getRefOrigin(), "The FVector reference is erroneous");
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
            FVector fVectorA = TestHelper.getRandFVector();
            FVector fVectorB = TestHelper.getRandFVector(fVectorA);

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
            FVector fVector = TestHelper.getRandFVector();
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
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);

            JSONObject json = fSegmentA.toJSON();

            FSegment fSegmentB = factory.getFSegment().set(json);

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
        @DisplayName("Apply state from")
        void applyStateFrom() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(1, 2, 3, 4, 5, 6));
            FLine fLine = factory.getRefFLine(factory.getFVector(9, 8, 7, 6, 5, 4));

            FSegment results = fSegment.set(fLine);

            Assertions.assertAll("Validate FSegment",
                    () -> assertSame(fSegment, results,
                            "The reference should not change"),
                    () -> assertNotSame(fSegment.getRefOrigin(), fLine.getRefOrigin(),
                            "FSegment core references should point at different objects"),
                    () -> assertTrue(fSegment.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous"),
                    () -> assertTrue(fLine.getRefOrigin().isExact(9, 8, 7, 6, 5, 4),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Apply state to")
        void applyStateTo() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(1, 2, 3, 4, 5, 6));
            FLine fLine = factory.getRefFLine(factory.getFVector(9, 8, 7, 6, 5, 4));

            FSegment results = fSegment.applyStateTo(fLine);

            Assertions.assertAll("Validate FLine",
                    () -> assertSame(fSegment, results,
                            "The reference should not change"),
                    () -> assertNotSame(fSegment.getRefOrigin(), fLine.getRefOrigin(),
                            "FLine core references should point at different objects"),
                    () -> assertTrue(fSegment.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous"),
                    () -> assertTrue(fLine.getRefOrigin().isExact(1, 2, 3, 4, 5, 6),
                            "The value is erroneous")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FVector fVector = TestHelper.getRandFVector();
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
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy().addFactor(0.5 * epsilon));

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
        @DisplayName("Exactness (geometry)")
        void isExactGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fSegmentA = factory.getRefFSegment(fVector.copy());
            Geometry fSegmentB = factory.getRefFSegment(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fSegmentA.isExact(fSegmentB), "FSegments should be equal"),
                    () -> assertTrue(fSegmentB.isExact(fSegmentB), "FSegments should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry, fail) A")
        void isExactGeometryFailA() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fSegment = factory.getRefFSegment(fVector.copy());
            Geometry fLine = factory.getRefFLine(fVector.copy());

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fSegment.isExact(fLine), "Geometries should not be equal"),
                    () -> assertFalse(fLine.isExact(fSegment), "Geometries should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (geometry, fail) B")
        void isExactGeometryFailB() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fSegmentA = factory.getRefFSegment(fVector.copy());
            Geometry fSegmentB = factory.getRefFSegment(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fSegmentA.isExact(fSegmentB), "FSegments should not be equal"),
                    () -> assertFalse(fSegmentB.isExact(fSegmentA), "FSegments should not be equal")
            );
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fSegmentA.isSimilar(fSegmentB), "FSegments should be similar"),
                    () -> assertTrue(fSegmentB.isSimilar(fSegmentA), "FSegments should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FSegment fSegmentA = factory.getRefFSegment(TestHelper.getRandFVector());
            FSegment fSegmentB = factory.getRefFSegment(TestHelper.getRandFVector(fSegmentA.getRefOrigin()));

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fSegmentA.isSimilar(fSegmentB), "FSegments should not be similar"),
                    () -> assertFalse(fSegmentB.isSimilar(fSegmentA), "FSegments should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry)")
        void isSimilarGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            Geometry fSegmentA = factory.getRefFSegment(fVector.copy());
            Geometry fSegmentB = factory.getRefFSegment(fVector.copy().addFactor(0.5 * epsilon));

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fSegmentA.isSimilar(fSegmentB), "FSegments should be similar"),
                    () -> assertTrue(fSegmentB.isSimilar(fSegmentA), "FSegments should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry, fail) A")
        void isSimilarGeometryFailA() {
            Geometry fSegment = factory.getRefFSegment(TestHelper.getRandFVector());
            Geometry fLine = factory.getRefFLine(TestHelper.getRandFVector());

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fSegment.isSimilar(fLine), "Geometries should not be similar"),
                    () -> assertFalse(fLine.isSimilar(fSegment), "Geometries should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (geometry, fail) B")
        void isSimilarGeometryFailB() {
            Geometry fSegmentA = factory.getRefFSegment(TestHelper.getRandFVector());
            Geometry fSegmentB = factory.getRefFSegment(TestHelper.getRandFVector());

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fSegmentA.isSimilar(fSegmentB), "FSegments should not be similar"),
                    () -> assertFalse(fSegmentB.isSimilar(fSegmentA), "FSegments should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector.copy());
            FSegment fSegmentB = factory.getRefFSegment(fVector.copy());

            assertEquals(fSegmentA.hashCode(), fSegmentB.hashCode(),
                    "Two identical FSegments should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);
            FSegment fSegmentB = factory.getRefFSegment(TestHelper.getRandFVector(fVector));

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
            FVector fVector = TestHelper.getRandFVector();
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
        @DisplayName("Copy geometry")
        void copyGeometry() {
            FVector fVector = TestHelper.getRandFVector();
            FSegment fSegmentA = factory.getRefFSegment(fVector);
            Geometry fSegmentB = fSegmentA.copyGeometry();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fSegmentA, fSegmentB,
                            "FSegments represent different objects"),
                    () -> assertTrue(fSegmentA.isExact((FSegment) fSegmentB),
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
        @DisplayName("Is projectable (below base)")
        void isProjectableBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertFalse(fSegment.isProjectable(0, -9, 0));
            assertFalse(fSegment.isProjectable(factory.getFPoint(0, -9, 0)));
            assertFalse(fSegment.isProjectable(factory.getFPos3D(0, -9, 0)));
        }

        @Test
        @DisplayName("Is projectable (above head)")
        void isProjectableAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector);

            assertFalse(fSegment.isProjectable(0, 9, 0));
            assertFalse(fSegment.isProjectable(factory.getFPoint(0, 9, 0)));
            assertFalse(fSegment.isProjectable(factory.getFPos3D(0, 9, 0)));
        }

        @Test
        @DisplayName("Project primitives")
        void projectPrimitives() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));

            FPoint offset = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(offset);

            FPos3D results = fSegment.project(offset.getX(), offset.getY() + 3, offset.getZ());

            assertNotNull(results);
            assertTrue(factory.getFPoint(1, 1, 1).addXYZ(offset).isSimilar(results));
        }

        @Test
        @DisplayName("Project unit")
        void projectUnit() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            boolean results = fSegment.project(fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(1, 1, 1).addXYZ(offset).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - X")
        void projectX() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 5, 5, 1, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            boolean results = fSegment.project((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(1, 5, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Y")
        void projectY() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, -5, 5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            boolean results = fSegment.project((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(5, 2, 5).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project simple - Z")
        void projectZ() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, -5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            boolean results = fSegment.project((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(5, 5, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project with offset")
        void projectWithOffset() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            boolean results = fSegment.project((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(1, 1, 1).addXYZ(offset).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (below base)")
        void projectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector);
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            boolean results = fSegment.project((Geometry) fPoint);

            assertFalse(results);
            assertTrue(factory.getFPoint(0, -9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (above head)")
        void projectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            boolean results = fSegment.project((Geometry) fPoint);

            assertFalse(results);
            assertTrue(factory.getFPoint(0, 9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Project (throw IllegalStateException)")
        void projectThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.project((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Reflect primitives")
        void reflectPrimitives() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));

            FPos3D results = fSegment.reflect(0, 3, 0);

            assertNotNull(results);
            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(results));
        }

        @Test
        @DisplayName("Reflect unit")
        void reflectUnit() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            boolean results = fSegment.reflect(fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - X")
        void reflectX() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-5, 5, 5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            boolean results = fSegment.reflect((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(1, 8, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Y")
        void reflectY() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, -5, 5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            boolean results = fSegment.reflect((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(9, 2, 7).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect simple - Z")
        void reflectZ() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, -5, 5, 5, 5));
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            boolean results = fSegment.reflect((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(9, 8, 3).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(5, 5, 5));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            boolean results = fSegment.reflect((Geometry) fPoint);

            assertTrue(results);
            assertTrue(factory.getFPoint(2, -1, 2).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (below base)")
        void reflectBelowBase() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            boolean results = fSegment.reflect((Geometry) fPoint);

            assertFalse(results);
            assertTrue(factory.getFPoint(0, -9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (above head")
        void reflectAboveHead() {
            FVector fVector = factory.getFVector(-1, -1, -1, 1, 1, 1);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, 9, 0);

            boolean results = fSegment.reflect((Geometry) fPoint);

            assertFalse(results);
            assertTrue(factory.getFPoint(0, 9, 0).isSimilar(fPoint));
        }

        @Test
        @DisplayName("Reflect (throw IllegalStateException)")
        void reflectThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.reflect((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Location unit")
        void isUnitPartOf() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * epsilon);

            assertTrue(fSegment.isPartOf(fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location")
        void isPartOf() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(0.5 * epsilon);

            assertTrue(fSegment.isPartOf((Geometry) fPoint),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location unit (fail)")
        void isUnitPartOfFail() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * epsilon);

            assertFalse(fSegment.isPartOf(fPoint),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isPartOfFail() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(1, 1, 1).addY(1.5 * epsilon);

            assertFalse(fSegment.isPartOf((Geometry) fPoint),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location unit epsilon")
        void isUnitPartOfEpsilon() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 0, 0, 1, 0, 0));
            FPoint fPoint = factory.getFPoint(0, 3, 1);

            assertTrue(fSegment.isPartOf(fPoint, 5),
                    "The distance should be correct");
        }

        @Test
        @DisplayName("Location epsilon")
        void isPartOfEpsilon() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 0, 0, 1, 0, 0));
            FPoint fPoint = factory.getFPoint(0, 1, 3);

            assertTrue(fSegment.isPartOf((Geometry) fPoint, 5),
                    "The distance should be correct");
        }

        @Test
        @DisplayName("Location unit epsilon (fail)")
        void isUnitPartOfEpsilonFail() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 0, 0, 1, 0, 0));
            FPoint fPoint = factory.getFPoint(0, 5, 1);

            assertFalse(fSegment.isPartOf(fPoint, 3),
                    "The distance should not be correct");
        }

        @Test
        @DisplayName("Location epsilon (fail)")
        void isPartOfEpsilonFail() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 0, 0, 1, 0, 0));
            FPoint fPoint = factory.getFPoint(0, 1, 5);

            assertFalse(fSegment.isPartOf((Geometry) fPoint, 3),
                    "The distance should not be correct");
        }

        @Test
        @DisplayName("Location (below base)")
        void isPartOfPositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(-4, -4, -4);

            assertFalse(fSegment.isPartOf((Geometry) fPoint));
        }

        @Test
        @DisplayName("Location (above head)")
        void isPartOfPositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(4, 4, 4).addY(0.5 * epsilon);

            assertFalse(fSegment.isPartOf((Geometry) fPoint));
        }

        @Test
        @DisplayName("Location (throw IllegalStateException)")
        void isPartOfThrowIllegalStateException() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector());
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fSegment.isPartOf((Geometry) fPoint),
                    "The origin is a non-directional FVector");
        }

        @Test
        @DisplayName("Get unit distance")
        void getUnitDistance() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            assertEquals(Math.sqrt(6), fSegment.getDistance(fPoint));
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint offset = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(offset);
            fPoint.addXYZ(offset);

            assertEquals(Math.sqrt(6), fSegment.getDistance(fPoint));
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
        @DisplayName("Set unit distance")
        void setUnitDistance() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            fSegment.setDistance(fPoint, 1);

            Assertions.assertTrue(Math.abs(fSegment.getDistance(fPoint) - 1) < epsilon,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance")
        void setDistance() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            fSegment.setDistance((Geometry) fPoint, 1);

            Assertions.assertTrue(Math.abs(fSegment.getDistance(fPoint) - 1) < epsilon,
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Set distance (below base)")
        void setDistancePositionBase() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
            FSegment fSegment = factory.getRefFSegment(fVector.copy());
            FPoint fPoint = factory.getFPoint(0, -9, 0);

            fSegment.setDistance(fPoint, 1);

            assertTrue(fPoint.isSimilar(0, -9, 0));
        }

        @Test
        @DisplayName("Set distance (above head)")
        void setDistancePositionHead() {
            FVector fVector = factory.getFVector(4, 4, 4).subFactor(2);
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

            FPoint relocation = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            fSegment.setDistance(fPoint, -1);

            Assertions.assertTrue(Math.abs(fSegment.getDistance(fPoint) - 1) < epsilon,
                    "The distance between FPoints is erroneous");
        }

        @Test
        @DisplayName("Set distance B (negative)")
        void setDistanceNegativeB() {
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(2, 2, 2));
            FPoint fPoint = factory.getFPoint(0, 3, 0);

            FPoint relocation = TestHelper.getRandFPoint();

            fSegment.getRefOrigin().addXYZ(relocation);
            fPoint.addXYZ(relocation);

            FPoint fPointA = fPoint.copy();
            fSegment.setDistance(fPointA,1);

            FPoint fPointB = fPoint.copy();
            fSegment.setDistance(fPointB, -1);

            Assertions.assertTrue(fPointA.getDistance(fPointB) - 2 < epsilon,
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
            Collection<FPoint> disassembly = fSegment.toFPoints();
            Iterator<FPoint> iterator = disassembly.iterator();

            iterator.next().set(1, 2, 3);
            iterator.next().set(4, 5, 6);

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(factory.getFPoint(1, 2, 3).isExact(fSegment.getRefOrigin().getRefBase()),
                            "The FPoint base value is erroneous"),
                    () -> assertTrue(factory.getFPoint(4, 5, 6).isExact(fSegment.getRefOrigin().getRefHead()),
                            "The FPoint head value is erroneous")
            );
        }
    }
}

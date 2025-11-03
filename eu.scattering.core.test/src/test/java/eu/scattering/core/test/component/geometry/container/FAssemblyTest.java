package eu.scattering.core.test.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.Base;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FAssembly")
public class FAssemblyTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FPointBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FAssembly<FSphere> fAssembly = factory.getFAssembly();

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(0, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with varargs")
        void constructWithVarArgs() {
            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            FAssembly<FVector> fAssembly = factory.getFAssembly(List.of(fVectorA, fVectorB, fVectorC));

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Get geometries")
        void getGeometries() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorX = factory.getFVector(1, 0, 0);
            FVector fVectorY = factory.getFVector(0, 1, 0);
            FVector fVectorZ = factory.getFVector(0, 0, 1);

            fAssembly.registerWithCheck(fVectorX);
            fAssembly.registerWithCheck(fVectorY);
            fAssembly.registerWithCheck(fVectorZ);

            Collection<FVector> geometries = fAssembly.asList();

            Assertions.assertAll("Validate geometries",
                    () -> assertEquals(3, geometries.size(),
                            "The collection should contain 3 elements"),
                    () -> assertTrue(geometries.contains(fVectorX),
                            "FVector X should be a part of the FAssembly"),
                    () -> assertTrue(geometries.contains(fVectorY),
                            "FVector Y should be a part of the FAssembly"),
                    () -> assertTrue(geometries.contains(fVectorZ),
                            "FVector Z should be a part of the FAssembly")
            );
        }

        @Test
        @DisplayName("Get points")
        void getFPoints() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorX = factory.getFVector(1, 0, 0);
            FVector fVectorY = factory.getFVector(0, 1, 0);
            FVector fVectorZ = factory.getFVector(0, 0, 1);

            fAssembly.registerWithCheck(fVectorX);
            fAssembly.registerWithCheck(fVectorY);
            fAssembly.registerWithCheck(fVectorZ);

            Collection<FPoint> list = fAssembly.toFPoints();

            Assertions.assertAll("Validate FPoints",
                    () -> Assertions.assertEquals(6, list.size(),
                            "The FAssembly should consist of 6 FPoints")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FPointCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FAssembly<Geometry> fAssembly = factory.getFAssembly();

            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FVector fVector = factory.getFVector(4, 5, 6, 7, 8, 9);
            FDraft fDraft = factory.getFDraft();
            FLine fLine = factory.getRefFLine(factory.getFVector(-1, -2, -3, -4, -5, -6));
            FPlane fPlane = factory.getRefFPlane(factory.getFVector(-6, -5, -4, -3, -2, -1));
            FRay fRay = factory.getRefFRay(factory.getFVector(1, -2, 3, -4, 5, -6));
            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 2, -3, 4, -5, 6));
            FSphere fSphere = factory.getFSphere(2);

            fAssembly.registerWithCheck(fPoint);
            fAssembly.registerWithCheck(fVector);
            fAssembly.registerWithCheck(fDraft);
            fAssembly.registerWithCheck(fLine);
            fAssembly.registerWithCheck(fPlane);
            fAssembly.registerWithCheck(fRay);
            fAssembly.registerWithCheck(fSegment);
            fAssembly.registerWithCheck(fSphere);

            JSONObject json = fAssembly.toJSON();

            FAssembly<Geometry> fAssemblyCopy = factory.getFAssembly();
            fAssemblyCopy.set(json);

            Assertions.assertAll("Validate FAssembly values",
                    () -> assertEquals(8, fAssembly.size(),
                            "The number of geometries is incorrect"),
                    () -> assertEquals(14, fAssembly.toFPoints().size(),
                            "The number of elements is incorrect"),
                    () -> assertTrue(fAssembly.isExact(fAssemblyCopy),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyCopy.isExact(fAssembly),
                            "FAssemblies should be equal")
            );
        }

        @Test
        @DisplayName("JSON parser (nested)")
        void parseNestedJSON() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyC = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyD = factory.getFAssembly();

            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FVector fVector = factory.getFVector(4, 5, 6, 7, 8, 9);

            FDraft fDraft = factory.getFDraft();
            FLine fLine = factory.getRefFLine(factory.getFVector(-1, -2, -3, -4, -5, -6));

            FPlane fPlane = factory.getRefFPlane(factory.getFVector(-6, -5, -4, -3, -2, -1));
            FRay fRay = factory.getRefFRay(factory.getFVector(1, -2, 3, -4, 5, -6));

            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 2, -3, 4, -5, 6));
            FSphere fSphere = factory.getFSphere(2);

            fAssemblyA.registerWithCheck(fPoint);
            fAssemblyA.registerWithCheck(fVector);

            fAssemblyB.registerWithCheck(fDraft);
            fAssemblyB.registerWithCheck(fLine);

            fAssemblyC.registerWithCheck(fPlane);
            fAssemblyC.registerWithCheck(fRay);

            fAssemblyD.registerWithCheck(fSegment);
            fAssemblyD.registerWithCheck(fSphere);

            fAssemblyA.registerWithCheck(fAssemblyB);
            fAssemblyB.registerWithCheck(fAssemblyC);
            fAssemblyC.registerWithCheck(fAssemblyD);

            JSONObject json = fAssemblyA.toJSON();

            FAssembly<Geometry> fAssemblyCopy = factory.getFAssembly();
            fAssemblyCopy.set(json);

            Assertions.assertAll("Validate FAssembly values",
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyCopy),
                            "FAssemblies should contain same values"),
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyCopy),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyCopy.isExact(fAssemblyA),
                            "FAssemblies should be equal")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FAssembly<Base<?>> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyB),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyB.isExact(fAssemblyA),
                            "FAssemblies should be equal")
            );
        }

        @Test
        @DisplayName("Exactness with FPoint")
        void isExactWithFPoint() {
            FAssembly<FPoint> fAssemblyA = factory.getFAssembly();

            FPoint fPointA1 = factory.getFPoint(1, 2, 3);
            FPoint fPointA2 = factory.getFPoint(1, 2, 3);

            fAssemblyA.registerWithCheck(fPointA1);
            fAssemblyA.registerWithCheck(fPointA2);

            FAssembly<FPoint> fAssemblyB = factory.getFAssembly();

            FPoint fPointB1 = factory.getFPoint(1, 2, 3);
            FPoint fPointB2 = factory.getFPoint(1, 2, 3);

            fAssemblyB.registerWithCheck(fPointB2);
            fAssemblyB.registerWithCheck(fPointB1);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyB),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyB.isExact(fAssemblyA),
                            "FAssemblies should be equal")
            );
        }

        @Test
        @DisplayName("Exactness with geometry")
        void isExactWithGeometry() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();

            Geometry fPointA = factory.getFPoint(1, 2, 3);
            Geometry fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            Geometry fPointB = factory.getFPoint(1, 2, 3);
            Geometry fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyB),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyB.isExact(fAssemblyA),
                            "FAssemblies should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail, value)")
        void isExactFailValue() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(3, 2, 1);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);
            fAssemblyB.registerWithCheck(fVectorA);
            fAssemblyB.registerWithCheck(fPointA);

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fAssemblyA.isExact(fAssemblyB),
                            "FAssemblies should not be equal"),
                    () -> assertFalse(fAssemblyB.isExact(fAssemblyA),
                            "FAssemblies should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail, quantity)")
        void isExactFailQuantity() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);
            fAssemblyB.registerWithCheck(fVectorA);
            fAssemblyB.registerWithCheck(fPointA);

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fAssemblyA.isExact(fAssemblyB),
                            "FAssemblies should not be equal"),
                    () -> assertFalse(fAssemblyB.isExact(fAssemblyA),
                            "FAssemblies should not be equal")
            );
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FAssembly<Base<?>> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(1 + (0.5 * epsilon), 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fAssemblyA.isSimilar(fAssemblyB),
                            "FAssemblies should be similar"),
                    () -> assertTrue(fAssemblyB.isSimilar(fAssemblyA),
                            "FAssemblies should be similar")
            );
        }

        @Test
        @DisplayName("Similarity with FPoint")
        void isSimilarWithFPoint() {
            FAssembly<FPoint> fAssemblyA = factory.getFAssembly();

            FPoint fPointA1 = factory.getFPoint(1, 2, 3);
            FPoint fPointA2 = factory.getFPoint(1, 2, 3);

            fAssemblyA.registerWithCheck(fPointA1);
            fAssemblyA.registerWithCheck(fPointA2);

            FAssembly<FPoint> fAssemblyB = factory.getFAssembly();

            FPoint fPointB1 = factory.getFPoint(1 + (0.5 * epsilon), 2, 3);
            FPoint fPointB2 = factory.getFPoint(1, 2 - (0.5 * epsilon), 3);

            fAssemblyB.registerWithCheck(fPointB2);
            fAssemblyB.registerWithCheck(fPointB1);

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fAssemblyA.isSimilar(fAssemblyB),
                            "FAssemblies should be similar"),
                    () -> assertTrue(fAssemblyB.isSimilar(fAssemblyA),
                            "FAssemblies should be similar")
            );
        }

        @Test
        @DisplayName("Similarity with geometry")
        void isSimilarWithGeometry() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();

            Geometry fPointA = factory.getFPoint(1, 2, 3);
            Geometry fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            Geometry fPointB = factory.getFPoint(1, 2 + (0.5 * epsilon), 3);
            Geometry fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);

            Assertions.assertAll("Validate similarity",
                    () -> assertTrue(fAssemblyA.isSimilar(fAssemblyB),
                            "FAssemblies should be similar"),
                    () -> assertTrue(fAssemblyB.isSimilar(fAssemblyA),
                            "FAssemblies should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail, value)")
        void isSimilarFailValue() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(1, 2 + (2 * epsilon), 3);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);
            fAssemblyB.registerWithCheck(fVectorA);
            fAssemblyB.registerWithCheck(fPointA);

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fAssemblyA.isSimilar(fAssemblyB),
                            "FAssemblies should not be similar"),
                    () -> assertFalse(fAssemblyB.isSimilar(fAssemblyA),
                            "FAssemblies should not be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail, quantity)")
        void isSimilarFailQuantity() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);
            fAssemblyB.registerWithCheck(fVectorA);
            fAssemblyB.registerWithCheck(fPointA);

            Assertions.assertAll("Validate similarity",
                    () -> assertFalse(fAssemblyA.isSimilar(fAssemblyB),
                            "FAssemblies should not be similar"),
                    () -> assertFalse(fAssemblyB.isSimilar(fAssemblyA),
                            "FAssemblies should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCodeFail() {
            FAssembly<Base<?>> fAssemblyA = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FVector fVectorA = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyA.registerWithCheck(fPointA);
            fAssemblyA.registerWithCheck(fVectorA);

            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();

            FPoint fPointB = factory.getFPoint(3, 2, 1);
            FVector fVectorB = factory.getFVector(4, 5, 6, 7, 8, 9);

            fAssemblyB.registerWithCheck(fVectorB);
            fAssemblyB.registerWithCheck(fPointB);

            assertNotEquals(fAssemblyA.hashCode(), fAssemblyB.hashCode(),
                    "Two different FAssemblies should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyC = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyD = factory.getFAssembly();

            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FVector fVector = factory.getFVector(4, 5, 6, 7, 8, 9);

            FDraft fDraft = factory.getFDraft();
            FLine fLine = factory.getRefFLine(factory.getFVector(-1, -2, -3, -4, -5, -6));

            FPlane fPlane = factory.getRefFPlane(factory.getFVector(-6, -5, -4, -3, -2, -1));
            FRay fRay = factory.getRefFRay(factory.getFVector(1, -2, 3, -4, 5, -6));

            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 2, -3, 4, -5, 6));
            FSphere fSphere = factory.getFSphere(2);

            fAssemblyA.registerWithCheck(fPoint);
            fAssemblyA.registerWithCheck(fVector);

            fAssemblyB.registerWithCheck(fDraft);
            fAssemblyB.registerWithCheck(fLine);

            fAssemblyC.registerWithCheck(fPlane);
            fAssemblyC.registerWithCheck(fRay);

            fAssemblyD.registerWithCheck(fSegment);
            fAssemblyD.registerWithCheck(fSphere);

            fAssemblyA.registerWithCheck(fAssemblyB);
            fAssemblyB.registerWithCheck(fAssemblyC);
            fAssemblyC.registerWithCheck(fAssemblyD);

            FAssembly<Geometry> fAssemblyCopy = fAssemblyA.copy();

            Assertions.assertAll("Validate FAssembly values",
                    () -> assertNotSame(fAssemblyA, fAssemblyCopy,
                            "FAssemblies should point to different instances"),
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyCopy),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyCopy.isExact(fAssemblyA),
                            "FAssemblies should be equal")
            );
        }

        @Test
        @DisplayName("Copy geometry")
        void copyGeometry() {
            FAssembly<Geometry> fAssemblyA = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyB = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyC = factory.getFAssembly();
            FAssembly<Geometry> fAssemblyD = factory.getFAssembly();

            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FVector fVector = factory.getFVector(4, 5, 6, 7, 8, 9);

            FDraft fDraft = factory.getFDraft();
            FLine fLine = factory.getRefFLine(factory.getFVector(-1, -2, -3, -4, -5, -6));

            FPlane fPlane = factory.getRefFPlane(factory.getFVector(-6, -5, -4, -3, -2, -1));
            FRay fRay = factory.getRefFRay(factory.getFVector(1, -2, 3, -4, 5, -6));

            FSegment fSegment = factory.getRefFSegment(factory.getFVector(-1, 2, -3, 4, -5, 6));
            FSphere fSphere = factory.getFSphere(2);

            fAssemblyA.registerWithCheck(fPoint);
            fAssemblyA.registerWithCheck(fVector);

            fAssemblyB.registerWithCheck(fDraft);
            fAssemblyB.registerWithCheck(fLine);

            fAssemblyC.registerWithCheck(fPlane);
            fAssemblyC.registerWithCheck(fRay);

            fAssemblyD.registerWithCheck(fSegment);
            fAssemblyD.registerWithCheck(fSphere);

            fAssemblyA.registerWithCheck(fAssemblyB);
            fAssemblyB.registerWithCheck(fAssemblyC);
            fAssemblyC.registerWithCheck(fAssemblyD);

            Geometry fAssemblyCopy = fAssemblyA.copyGeometry();

            Assertions.assertAll("Validate FAssembly values",
                    () -> assertNotSame(fAssemblyA, fAssemblyCopy,
                            "FAssemblies should point to different instances"),
                    () -> assertTrue(fAssemblyA.isExact(fAssemblyCopy),
                            "FAssemblies should be equal"),
                    () -> assertTrue(fAssemblyCopy.isExact(fAssemblyA),
                            "FAssemblies should be equal")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FPointAdvancedTest {

        @Test
        @DisplayName("Use iterator")
        void useIterator() {
            FAssembly<FPoint> fAssembly = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FPoint fPointB = factory.getFPoint(4, 5, 6);

            fAssembly.registerWithCheck(fPointA);
            fAssembly.registerWithCheck(fPointB);

            for (FPoint fPoint : fAssembly) {
                fPoint.addXYZ(1, 2, 3);
            }

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(fPointA.isExact(2, 4, 6),
                            "FPoint A is erroneous"),
                    () -> assertTrue(fPointB.isExact(5, 7, 9),
                            "FPoint B is erroneous")
            );
        }

        @Test
        @DisplayName("Register elements with check")
        void registerElementsWithCheck() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

            var registerA = fAssembly.registerWithCheck(fVectorA);
            var registerB = fAssembly.registerWithCheck(fVectorB);

            var registerRedundant = fAssembly.registerWithCheck(fVectorB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertTrue(registerA,
                            "The addition of FVector A should be successful"),
                    () -> assertTrue(registerB,
                            "The addition of FVector B should be successful"),
                    () -> assertFalse(registerRedundant,
                            "The addition of FVector is redundant"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Register elements with check and rule")
        void registerElementsWithCheckAndRule() {
            FAssembly<Shape> fAssembly = factory.getFAssembly();

            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 1, 0, 1);

            BiFunction<Shape, Collection<Shape>, Boolean> rule = (single, all) -> single.overlaps(all) == 0;

            var registerA = fAssembly.registerWithCheck(fSphereA, rule);
            var registerB = fAssembly.registerWithCheck(fSphereB, rule);
            var registerC = fAssembly.registerWithCheck(fSphereC, rule);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertTrue(registerA,
                            "The addition of FSphere A should be successful"),
                    () -> assertTrue(registerB,
                            "The addition of FSphere B should be successful"),
                    () -> assertFalse(registerC,
                            "The addition of FSphere C should be successful"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Register elements")
        void registerElements() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

            fAssembly.register(fVectorA);
            fAssembly.register(fVectorB);

            FAssembly<FVector> results = fAssembly.register(fVectorB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(2, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Register elements with rule")
        void registerElementsWithRule() {
            FAssembly<Shape> fAssembly = factory.getFAssembly();

            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 1, 0, 1);

            BiFunction<Shape, Collection<Shape>, Boolean> rule = (single, all) -> single.overlaps(all) == 0;

            FAssembly<Shape> results = fAssembly
                    .register(fSphereA, rule)
                    .register(fSphereB, rule)
                    .register(fSphereC, rule);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(2, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Register elements with check (collection)")
        void registerElementsWithCheckCollection() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            Collection<FVector> collectionA = new ArrayList<>();
            collectionA.add(fVectorA);
            collectionA.add(fVectorB);
            collectionA.add(fVectorC);

            boolean isDuplicatedA = fAssembly.registerWithCheck(collectionA);

            Collection<FVector> collectionB = new ArrayList<>();
            collectionB.add(fVectorA);
            collectionB.add(fVectorB);

            boolean isDuplicatedB = fAssembly.registerWithCheck(collectionB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertTrue(isDuplicatedA,
                            "The structure should be updated"),
                    () -> assertFalse(isDuplicatedB,
                            "The structure should not be updated")
            );
        }

        @Test
        @DisplayName("Register elements with check and rule (collection)")
        void registerElementsWithCheckAndRuleCollection() {
            FAssembly<Shape> fAssembly = factory.getFAssembly();

            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 1, 0, 1);

            Collection<FSphere> collection = new ArrayList<>();
            collection.add(fSphereA);
            collection.add(fSphereB);
            collection.add(fSphereC);

            BiFunction<Shape, Collection<Shape>, Boolean> rule = (single, all) -> single.overlaps(all) == 0;

            var register = fAssembly.registerWithCheck(collection, rule);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertTrue(register,
                            "At least one element should be added"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Register elements (collection)")
        void registerElementsCollection() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            Collection<FVector> collection = new ArrayList<>();
            collection.add(fVectorA);
            collection.add(fVectorB);
            collection.add(fVectorC);

            collection.add(fVectorC);

            FAssembly<FVector> results = fAssembly.register(collection);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Register elements with rule (collection)")
        void registerElementsWithRuleCollection() {
            FAssembly<Shape> fAssembly = factory.getFAssembly();

            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 1, 0, 1);

            Collection<FSphere> collection = new ArrayList<>();
            collection.add(fSphereA);
            collection.add(fSphereB);
            collection.add(fSphereC);

            BiFunction<Shape, Collection<Shape>, Boolean> rule = (single, all) -> single.overlaps(all) == 0;

            FAssembly<Shape> results = fAssembly.register(collection, rule);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(2, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Register elements (chain)")
        void registerElementsChain() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

            var registerA = fAssembly.register(fVectorA);
            var registerB = fAssembly.register(fVectorB);

            var registerRedundant = fAssembly.register(fVectorA);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertSame(fAssembly, registerA,
                            "The reference should not change"),
                    () -> assertSame(fAssembly, registerB,
                            "The reference should not change"),
                    () -> assertSame(fAssembly, registerRedundant,
                            "The reference should not change"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Register elements (duplicate points)")
        void registerElementsDuplicatePoints() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(6, 5, 4));

            fAssembly.registerWithCheck(fVectorA);
            fAssembly.registerWithCheck(fVectorB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Register elements (various types)")
        void registerElementsVariousTypes() {
            FAssembly<Geometry> fAssembly = factory.getFAssembly();

            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FPoint fPoint = factory.getFPoint(7, 8, 9);

            var registerA = fAssembly.registerWithCheck(fVector);
            var registerB = fAssembly.registerWithCheck(fPoint);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertTrue(registerA,
                            "The addition of FVector A should be successful"),
                    () -> assertTrue(registerB,
                            "The addition of FVector B should be successful"),
                    () -> assertEquals(3, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }

        @Test
        @DisplayName("Deregister elements with check")
        void deregisterElementsWithCheck() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

            fAssembly
                    .register(fVectorA)
                    .register(fVectorB);

            var isDuplicatedA = fAssembly.deregisterWithCheck(fVectorA);
            var isDuplicatedB = fAssembly.deregisterWithCheck(fVectorA);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(1, fAssembly.size(),
                            "The number of geometries is incorrect"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertTrue(isDuplicatedA,
                            "The element should be removed"),
                    () -> assertFalse(isDuplicatedB,
                            "The element should be removed")
            );
        }

        @Test
        @DisplayName("Deregister elements")
        void deregisterElements() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

            var results = fAssembly
                    .register(fVectorA)
                    .register(fVectorB)
                    .register(fVectorA)
                    .deregister(fVectorA);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(1, results.size(),
                            "The number of geometries is incorrect"),
                    () -> assertEquals(2, results.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Deregister elements with check (collection)")
        void deregisterElementsWithCheckCollection() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            Collection<FVector> collectionA = new ArrayList<>();
            collectionA.add(fVectorA);
            collectionA.add(fVectorB);
            collectionA.add(fVectorC);

            fAssembly.registerWithCheck(collectionA);

            Collection<FVector> collectionB = new ArrayList<>();
            collectionB.add(fVectorA);
            collectionB.add(fVectorB);

            var isDuplicatedA = fAssembly.deregisterWithCheck(collectionB);
            var isDuplicatedB = fAssembly.deregisterWithCheck(collectionB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(1, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertTrue(isDuplicatedA,
                            "The elements should be removed"),
                    () -> assertFalse(isDuplicatedB,
                            "The elements should not be removed")
            );
        }

        @Test
        @DisplayName("Deregister elements (collection)")
        void deregisterElementsCollection() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            Collection<FVector> collectionA = new ArrayList<>();
            collectionA.add(fVectorA);
            collectionA.add(fVectorB);
            collectionA.add(fVectorC);

            fAssembly.registerWithCheck(collectionA);

            Collection<FVector> collectionB = new ArrayList<>();
            collectionB.add(fVectorA);
            collectionB.add(fVectorB);

            var results = fAssembly.deregister(collectionB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(1, fAssembly.asList().size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(2, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Deregister elements (duplicate points)")
        void deregisterElementsDuplicatePoints() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            var results = fAssembly
                    .register(fVectorA)
                    .register(fVectorB)
                    .register(fVectorC)
                    .deregister(fVectorA);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(2, results.asList().size(),
                            "The number of geometries is incorrect"),
                    () -> assertEquals(3, results.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Mutate")
        void mutate() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FSphere fSphere = factory.getFSphere(1, 2, 3, 4);

            assertEquals(0, fSphere.getCoatCount(),
                    "The FSphere is not coated");

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fPoint, fVector, fSphere));

            FAssembly<Geometry> results = fAssembly.mutate(Shape.class, (e) -> e.addCoat(1, 2, 3));

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fSphere.getCoatCount(),
                            "The number of coats is erroneous"),
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of FAssembly is erroneous"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Translate with primitives")
        void translateWithPrimitives() {
            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            FAssembly<FVector> fAssembly = factory.getFAssembly(List.of(fVectorA, fVectorB, fVectorC));

            FAssembly<FVector> results = fAssembly.translate(1, 2, 3);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertTrue(base.isExact(2, 4, 6),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVectorA.isExact(2, 4, 6, 2, 4, 6),
                            "The FVector A is erroneous"),
                    () -> assertTrue(fVectorB.isExact(2, 4, 6, 5, 7, 9),
                            "The FVector B is erroneous"),
                    () -> assertTrue(fVectorC.isExact(2, 4, 6, 8, 10, 12),
                            "The FVector C is erroneous"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Translate with FPos3D")
        void translateWithFPos3D() {
            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            FAssembly<FVector> fAssembly = factory.getFAssembly(List.of(fVectorA, fVectorB, fVectorC));

            FAssembly<FVector> results = fAssembly.translate(factory.getFPos3D(1, 2, 3));

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertTrue(base.isExact(2, 4, 6),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVectorA.isExact(2, 4, 6, 2, 4, 6),
                            "The FVector A is erroneous"),
                    () -> assertTrue(fVectorB.isExact(2, 4, 6, 5, 7, 9),
                            "The FVector B is erroneous"),
                    () -> assertTrue(fVectorC.isExact(2, 4, 6, 8, 10, 12),
                            "The FVector C is erroneous"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Scale")
        void scale() {
            FPoint base = factory.getFPoint(1,2, 3);

            FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(1, 2, 3));
            FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
            FVector fVectorC = factory.getRefFVector(base, factory.getFPoint(7, 8, 9));

            FAssembly<FVector> fAssembly = factory.getFAssembly(List.of(fVectorA, fVectorB, fVectorC));

            FAssembly<FVector> results = fAssembly.scale(2);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertTrue(base.isExact(2, 4, 6),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVectorA.isExact(2, 4, 6, 2, 4, 6),
                            "The FVector A is erroneous"),
                    () -> assertTrue(fVectorB.isExact(2, 4, 6, 8, 10, 12),
                            "The FVector B is erroneous"),
                    () -> assertTrue(fVectorC.isExact(2, 4, 6, 14, 16, 18),
                            "The FVector C is erroneous"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Get spatial dimension, 1x FPoint")
        void getSpatialDimension1xFPoint() {
            FPoint fPoint = factory.getFPoint(1,2, 3);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fPoint));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(1, 2, 3, 1, 2, 3), range,
                            "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 2x FPoint")
        void getSpatialDimension2xFPoint() {
            FPoint fPointA = factory.getFPoint(2,-5, 1);
            FPoint fPointB = factory.getFPoint(-3,-1, 5);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fPointA, fPointB));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-3, -5, 1, 2, -1, 5), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 3x FPoint")
        void getSpatialDimension3xFPoint() {
            FPoint fPointA = factory.getFPoint(2,-5, 1);
            FPoint fPointB = factory.getFPoint(-3,-1, 5);
            FPoint fPointC = factory.getFPoint(0,-3, 3);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fPointA, fPointB, fPointC));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-3, -5, 1, 2, -1, 5), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 1x FVector")
        void getSpatialDimension1xFVector() {
            FVector fVector = factory.getFVector(-1, -3, -5, 5, 4, 3);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fVector));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-1, -3, -5, 5, 4, 3), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 1x FSphere")
        void getSpatialDimension1xFSphere() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 2);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fSphere));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-1, 0, 1, 3, 4, 5), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 2x FSphere, close")
        void getSpatialDimension2xFSphereClose() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 5);
            FSphere fSphereB = factory.getFSphere(-1, -2, -3, 4);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-5, -6, -7, 6, 7, 8), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 2x FSphere, distant")
        void getSpatialDimension2xFSphereDistant() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 1);
            FSphere fSphereB = factory.getFSphere(-1, -2, -3, 1);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-2, -3, -4, 2, 3, 4), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, illegal construct")
        void getSpatialDimensionIllegalConstruct() {
            FPoint fPoint = factory.getFPoint(1,2, 3);
            FVector fVector = factory.getFVector(-1, -2, -3, 3, 2, 1);

            FAssembly<Geometry> fAssembly;

            fAssembly = factory.getFAssembly(List.of(fPoint, factory.getRefFDraft(fVector)));

            assertThrows(IllegalStateException.class, fAssembly::getBoundary,
                    "The FAssembly cannot contain FDraft");

            fAssembly = factory.getFAssembly(List.of(fPoint, factory.getRefFLine(fVector)));

            assertThrows(IllegalStateException.class, fAssembly::getBoundary,
                    "The FAssembly cannot contain FLine");

            fAssembly = factory.getFAssembly(List.of(fPoint, factory.getRefFPlane(fVector)));

            assertThrows(IllegalStateException.class, fAssembly::getBoundary,
                    "The FAssembly cannot contain FPlane");

            fAssembly = factory.getFAssembly(List.of(fPoint, factory.getRefFRay(fVector)));

            assertThrows(IllegalStateException.class, fAssembly::getBoundary,
                    "The FAssembly cannot contain FRay");
        }

        @Test
        @DisplayName("Get spatial dimension, 1x FSegment")
        void getSpatialDimension1xFSegment() {
            FVector fVector = factory.getFVector(-1, -3, -5, 5, 4, 3);

            FAssembly<Geometry> fAssembly = factory.getFAssembly(List.of(factory.getRefFSegment(fVector)));

            FPairPos3D range = fAssembly.getBoundary();

            assertEquals(factory.getFPairPos3D(-1, -3, -5, 5, 4, 3), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial dimension, 1x FAssembly")
        void getSpatialDimension1xFAssembly() {
            FPoint fPointA = factory.getFPoint(2,-5, 1);
            FPoint fPointB = factory.getFPoint(-3,-1, 5);
            FPoint fPointC = factory.getFPoint(0,-3, 3);

            FAssembly<Geometry> fAssemblyA = factory.getFAssembly(List.of(fPointA, fPointB, fPointC));
            FAssembly<Geometry> fAssemblyB = factory.getFAssembly(List.of(fAssemblyA));

            FPairPos3D range = fAssemblyB.getBoundary();

            assertEquals(factory.getFPairPos3D(-3, -5, 1, 2, -1, 5), range,
                    "The dimension is erroneous");
        }

        @Test
        @DisplayName("Get spatial center")
        void getSpatialCenter() {
            FSphere fSphereA = factory.getFSphere(1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(-1, 0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FPoint center = factory.getFPoint();
            fAssembly.getSpatialCenter(center);

            assertTrue(center.isSimilar(0, 0, 0),
                    "The range center is erroneous");

            FPos3D offset = factory.getFPos3D(1, 2, 3);

            fAssembly.translate(offset);

            fAssembly.getSpatialCenter(center);

            assertTrue(center.isSimilar(1, 2, 3),
                    "The range center is erroneous");
        }

        @Test
        @DisplayName("Get spherical center - A")
        void getSphericalCenterA() {
            FSphere fSphereA = factory.getFSphere(-3, 0, 0);
            FSphere fSphereB = factory.getFSphere(-1, 0, 0);
            FSphere fSphereC = factory.getFSphere(1, 0, 0);
            FSphere fSphereD = factory.getFSphere(3, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FPos3D offset = factory.getFRand().nextDoubleInSphere(1000);

            fAssembly.translate(offset);

            FPoint center = factory.getFPoint();
            fAssembly.getSphericalCenter(center);

            double relErrX = factory.getStatisticsHelper().getRelErr(offset.getD0(), center.getX());
            double relErrY = factory.getStatisticsHelper().getRelErr(offset.getD1(), center.getY());
            double relErrZ = factory.getStatisticsHelper().getRelErr(offset.getD2(), center.getZ());

            Assertions.assertAll("Validate position",
                    () -> assertTrue(relErrX < 0.01),
                    () -> assertTrue(relErrY < 0.01),
                    () -> assertTrue(relErrZ < 0.01)
            );
        }

        @Test
        @DisplayName("Get spherical center - B")
        void getSphericalCenterB() {
            FSphere fSphereA = factory.getFSphere(-1.5, 0, 0);
            FSphere fSphereB = factory.getFSphere(1.5, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 1.5 * Math.sqrt(3), 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FPos3D offset = factory.getFRand().nextDoubleInSphere(1000);

            fAssembly.translate(offset);

            FPoint center = factory.getFPoint();
            fAssembly.getSphericalCenter(center);

            double relErrX = factory.getStatisticsHelper().getRelErr(offset.getD0(), center.getX());
            double relErrY = factory.getStatisticsHelper().getRelErr(offset.getD1() + (0.5 * Math.sqrt(3)), center.getY());
            double relErrZ = factory.getStatisticsHelper().getRelErr(offset.getD2(), center.getZ());

            Assertions.assertAll("Validate position",
                    () -> assertTrue(relErrX < 0.01),
                    () -> assertTrue(relErrY < 0.01),
                    () -> assertTrue(relErrZ < 0.01)
            );
        }
    }
}

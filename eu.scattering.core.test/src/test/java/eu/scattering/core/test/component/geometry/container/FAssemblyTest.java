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
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
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

            FAssembly<FVector> fAssembly = factory.getFAssembly(fVectorA, fVectorB, fVectorC);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(3, fAssembly.getListGeometry().size(),
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

            Collection<FVector> geometries = fAssembly.getListGeometry();

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
                    () -> assertEquals(8, fAssembly.getListGeometry().size(),
                            "The number of geometries is incorrect"),
                    () -> assertEquals(14, fAssembly.toFPoints().size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(fAssembly, fAssemblyCopy,
                            "FAssemblies should contain same values"),
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
                    () -> assertEquals(fAssemblyA, fAssemblyCopy,
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
        void getHashCode() {
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

            assertEquals(fAssemblyA.hashCode(), fAssemblyB.hashCode(),
                    "Two identical FAssemblies should have the same hash code");
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
                    () -> assertEquals(fAssemblyA, fAssemblyCopy,
                            "FAssemblies should contain same values"),
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
                    () -> assertEquals(fAssemblyA, fAssemblyCopy,
                            "FAssemblies should contain same values"),
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
        @DisplayName("Apply FPoint")
        void applyFPoint() {
            FAssembly<FPoint> fAssembly = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FPoint fPointB = factory.getFPoint(4, 5, 6);

            fAssembly.registerWithCheck(fPointA);
            fAssembly.registerWithCheck(fPointB);

            FAssembly<FPoint> results = fAssembly.applyFPoint((fPoint) -> fPoint.addXYZ(1, 2, 3));

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(fPointA.isExact(2, 4, 6),
                            "FPoint A is erroneous"),
                    () -> assertTrue(fPointB.isExact(5, 7, 9),
                            "FPoint B is erroneous"),
                    () -> assertSame(results, fAssembly,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Apply geometry")
        void applyGeometry() {
            FAssembly<FPoint> fAssembly = factory.getFAssembly();

            FPoint fPointA = factory.getFPoint(1, 2, 3);
            FPoint fPointB = factory.getFPoint(4, 5, 6);

            fAssembly.registerWithCheck(fPointA);
            fAssembly.registerWithCheck(fPointB);

            FAssembly<FPoint> results = fAssembly.applyGeometry((fPoint) -> fPoint.addXYZ(1, 2, 3));

            Assertions.assertAll("Validate FPoints",
                    () -> assertTrue(fPointA.isExact(2, 4, 6),
                            "FPoint A is erroneous"),
                    () -> assertTrue(fPointB.isExact(5, 7, 9),
                            "FPoint B is erroneous"),
                    () -> assertSame(results, fAssembly,
                            "The reference should not change")
            );
        }

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
        @DisplayName("Register elements")
        void registerElements() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

            fAssembly.register(fVectorA);
            fAssembly.register(fVectorB);

            FAssembly<FVector> results = fAssembly.register(fVectorB);

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(2, fAssembly.getListGeometry().size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
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
                    () -> assertEquals(3, fAssembly.getListGeometry().size(),
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
                    () -> assertEquals(3, fAssembly.getListGeometry().size(),
                            "The size of the FAssembly is erroneous"),
                    () -> assertEquals(4, fAssembly.toFPoints().size(),
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
                    () -> assertEquals(1, fAssembly.getListGeometry().size(),
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
                    () -> assertEquals(1, results.getListGeometry().size(),
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
                    () -> assertEquals(1, fAssembly.getListGeometry().size(),
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
                    () -> assertEquals(1, fAssembly.getListGeometry().size(),
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
                    () -> assertEquals(2, results.getListGeometry().size(),
                            "The number of geometries is incorrect"),
                    () -> assertEquals(3, results.toFPoints().size(),
                            "The number of FPoints is incorrect"),
                    () -> assertSame(fAssembly, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Apply element")
        void applyElement() {
            FAssembly<FVector> fAssembly = factory.getFAssembly();

            FVector fVectorX = factory.getFVector(1, 0, 0);
            FVector fVectorY = factory.getFVector(0, 1, 0);
            FVector fVectorZ = factory.getFVector(0, 0, 1);

            fAssembly.registerWithCheck(fVectorX);
            fAssembly.registerWithCheck(fVectorY);
            fAssembly.registerWithCheck(fVectorZ);

            fAssembly.applyGeometry(e -> e.shiftForward(1));

            Assertions.assertAll("Validate FAssembly",
                    () -> assertEquals(6, fAssembly.toFPoints().size(),
                            "The number of FPoints is incorrect")
            );
        }
    }
}

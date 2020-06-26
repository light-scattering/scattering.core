package eu.scattering.core.geometry.support;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.plane.IFPlane;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import static eu.scattering.core.Configuration.jitter;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFPlane")
public class IFPlaneTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFPlaneBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            IFPlane fPlane = FactoryGeometry.getIFPlane();

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate positions)")
        void constructValidatePositions() {
            IFPlane fPlane = FactoryGeometry.getIFPlane();

            assertEquals(FactoryGeometry.getIFVector(), fPlane.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertNotNull(fPlane, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector (validate positions)")
        void constructWithIFVectorValidatePositions() {
            double refAX = HelperRandom.getTestValue();
            double refAY = HelperRandom.getTestValue();
            double refAZ = HelperRandom.getTestValue();
            double refBX = HelperRandom.getTestValue();
            double refBY = HelperRandom.getTestValue();
            double refBZ = HelperRandom.getTestValue();
            IFPoint fPointBase = FactoryGeometry.getIFPoint(refAX, refAY, refAZ);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(refBX, refBY, refBZ);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refAX, fPlane.getOrigin().getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fPlane.getOrigin().getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fPlane.getOrigin().getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fPlane.getOrigin().getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fPlane.getOrigin().getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fPlane.getOrigin().getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFVector (throw NullPointerException)")
        void constructWithIFVectorThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> FactoryGeometry.getIFPlane(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorA);

            IFPlane fPlaneRef = fPlane.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fPlane.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fPlaneRef, fPlane, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(HelperRandom.getTestVector());

            assertThrows(NullPointerException.class, () -> fPlane.setOriginRef(null),
                    "The reference cannot be null");
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertSame(fVector, fPlane.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector.copy());

            assertEquals(fVector, fPlane.getOrigin(), "The IFVector positions are erroneous");
        }
    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector);
            IFPlane fPlaneB = FactoryGeometry.getIFPlane().importFromJSON(fPlaneA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "IFPlane references should point at different objects"),
                    () -> assertEquals(fPlaneA.getOrigin(), fPlaneB.getOrigin(),
                            "The origin of IFPlanes should be exact")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isExact(fPlaneB), "IFPlanes should be equal"),
                    () -> assertTrue(fPlaneB.isExact(fPlaneB), "IFPlanes should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isExact(fPlaneB), "IFPlanes should not be equal"),
                    () -> assertFalse(fPlaneB.isExact(fPlaneA), "IFPlanes should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (throw NullPointerException)")
        void isExactThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVector);

            assertThrows(NullPointerException.class,
                    () -> fPlane.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertTrue(fPlaneA.isSimilar(fPlaneB), "IFPlanes should be similar"),
                    () -> assertTrue(fPlaneB.isSimilar(fPlaneA), "IFPlanes should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy().add(1.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fPlaneA.isSimilar(fPlaneB), "IFPlanes should not be similar"),
                    () -> assertFalse(fPlaneB.isSimilar(fPlaneA), "IFPlanes should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector.copy());
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(fVector.copy());

            assertEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two identical IFPlanes should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector);
            IFPlane fPlaneB = FactoryGeometry.getIFPlane(HelperRandom.getTestVector(fVector));

            assertNotEquals(fPlaneA.hashCode(), fPlaneB.hashCode(),
                    "Two different IFPlanes should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPlane fPlaneA = FactoryGeometry.getIFPlane(fVector);
            IFPlane fPlaneB = fPlaneA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fPlaneA, fPlaneB,
                            "IFPlanes represent different objects"),
                    () -> assertEquals(fPlaneA, fPlaneB,
                            "IFPlanes should have the same values"),
                    () -> assertNotEquals(fPlaneA, fPlaneB.getOrigin().add(jitter),
                            "IFPlanes should have different values")
            );
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFPlaneAdvanced {

        @Test
        @DisplayName("Project")
        void project() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            fPoint.ext(fPlane.project());

            assertTrue(FactoryGeometry.getIFPoint(-1, 2, -1).add(relocation).isSimilar(fPoint),
                    "The projection is erroneous");
        }

        @Test
        @DisplayName("Project (validate references)")
        void projectValidateReferences() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fPlane.project());

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Project (validate positions)")
        void projectValidatePositions() {
            IFVector fVectorOrigin = FactoryGeometry.getIFVector(1, 1, 1);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorOrigin.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fPlane.project());

            assertEquals(fVectorOrigin, fPlane.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertTrue(FactoryGeometry.getIFPoint(-2, 1, -2).isSimilar(fPoint),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate references)")
        void reflectValidateReferences() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint fPointRef = fPoint.ext(fPlane.reflect());

            assertSame(fPointRef, fPoint, "The reference should remain unchanged");
        }

        @Test
        @DisplayName("Reflect (validate positions)")
        void reflectValidatePositions() {
            IFVector fVectorOrigin = FactoryGeometry.getIFVector(1, 1, 1);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorOrigin.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.ext(fPlane.reflect());

            assertEquals(fVectorOrigin, fPlane.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Location")
        void isCloseTo() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(-1, 2, -1).add(0.5 * jitter);

            assertTrue(fPoint.extLog(fPlane.isCloseTo()).get(0),
                    "The distance should be negligible");
        }

        @Test
        @DisplayName("Location (fail)")
        void isCloseToFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(-1, 2, -1).add(1.5 * jitter);

            assertFalse(fPoint.extLog(fPlane.isCloseTo()).get(0),
                    "The distance should not be negligible");
        }

        @Test
        @DisplayName("Location (validate positions)")
        void isCloseToValidatePositions() {
            IFVector fVectorOrigin = FactoryGeometry.getIFVector(1, 1, 1);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorOrigin.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(-1, 2, -1).add(0.5 * jitter);

            fPoint.extLog(fPlane.isCloseTo());

            assertEquals(fVectorOrigin, fPlane.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Distance")
        void getDistance() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertEquals(Math.sqrt(3), fPoint.extVal(fPlane.getDistance()).get(0),
                    "The distance is erroneous");
        }

        @Test
        @DisplayName("Distance (validate positions)")
        void getDistanceValidatePositions() {
            IFVector fVectorOrigin = FactoryGeometry.getIFVector(1, 1, 1);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorOrigin.copy());
            IFPoint fPoint = FactoryGeometry.getIFPoint(0, 3, 0);

            fPoint.extVal(fPlane.getDistance());

            assertEquals(fVectorOrigin, fPlane.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Determine half-space")
        void isInHalfSpace() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .relocateBase(-1, 2, -1)
                    .getHead();

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertTrue(fPoint.extLog(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (fail)")
        void isInHalfSpaceFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFPoint fPoint = FactoryGeometry.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .reflect()
                    .relocateBase(-1, 2, -1)
                    .getHead();

            IFPoint relocation = HelperRandom.getTestPoint();

            fPlane.getOrigin().add(relocation);
            fPoint.add(relocation);

            assertFalse(fPoint.extLog(fPlane.isInHalfSpace()).get(0),"The half-space is erroneous");
        }

        @Test
        @DisplayName("Determine half-space (validate positions)")
        void isInHalfSpaceValidatePositions() {
            IFVector fVectorOrigin = FactoryGeometry.getIFVector(1, 1, 1);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorOrigin.copy());
            IFPoint fPoint = FactoryGeometry.getIFVector(1, 1, 1)
                    .mul(jitter)
                    .reflect()
                    .relocateBase(-1, 2, -1)
                    .getHead();

            fPoint.extLog(fPlane.isInHalfSpace());

            assertEquals(fVectorOrigin, fPlane.getOrigin(), "The origin values should remain unchanged");
        }

        @Test
        @DisplayName("Determine intersection A")
        void isCuttingA() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, -1, -1, -1);

            assertTrue(fPlane.isCutting(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B")
        void isCuttingB() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(-1, -1, -1, 1, 1, 1);

            assertTrue(fPlane.isCutting(fVector), "The IFVector should intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection A (fail)")
        void isCuttingAFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, 2, 2, 2);

            assertFalse(fPlane.isCutting(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection B (fail)")
        void isCuttingBFail() {
            IFPlane fPlane = FactoryGeometry.getIFPlane(FactoryGeometry.getIFVector(1, 1, 1));
            IFVector fVector = FactoryGeometry.getIFVector(-1, -1, -1, -2, -2, -2);

            assertFalse(fPlane.isCutting(fVector), "The IFVector should not intersect with the IFPlane");
        }

        @Test
        @DisplayName("Determine intersection (validate positions)")
        void isCuttingValidatePositions() {
            IFVector fVectorOrigin = FactoryGeometry.getIFVector(1, 1, 1);
            IFPlane fPlane = FactoryGeometry.getIFPlane(fVectorOrigin.copy());
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, -1, -1, -1);

            fPlane.isCutting(fVector);

            assertEquals(fVectorOrigin, fPlane.getOrigin(), "The origin values should remain unchanged");
        }

    }
}

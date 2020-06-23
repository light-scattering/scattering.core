package eu.scattering.core.geometry.support;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import static eu.scattering.core.Configuration.jitter;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(5)
@DisplayName("IFLine")
public class IFLineTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFLineBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            IFLine fLine = FactoryGeometry.getIFLine();

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct (validate positions)")
        void constructValidatePositions() {
            IFLine fLine = FactoryGeometry.getIFLine();

            assertEquals(FactoryGeometry.getIFVector(), fLine.getOrigin(),
                    "The initial IFVector values are erroneous");
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertNotNull(fLine, "The instance is null");
        }

        @Test
        @DisplayName("Construct with IFVector (validate references)")
        void constructWithIFVectorValidateReferences() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
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
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(refAX, fLine.getOrigin().getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(refAY, fLine.getOrigin().getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(refAZ, fLine.getOrigin().getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(refBX, fLine.getOrigin().getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(refBY, fLine.getOrigin().getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(refBZ, fLine.getOrigin().getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFVector (throw NullPointerException)")
        void constructWithIFVectorThrowNullPointerException() {

            assertThrows(NullPointerException.class, () -> FactoryGeometry.getIFLine(null),
                    "The reference cannot be null" );
        }

        @Test
        @DisplayName("Set origin ref")
        void setOriginRef() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            IFLine fLine = FactoryGeometry.getIFLine(fVectorA);

            IFLine fLineRef = fLine.setOriginRef(fVectorB);

            assertAll("Validate IFLine references",
                    () -> assertNotSame(fVectorA, fVectorB, "IFVectors should point at different objects"),
                    () -> assertSame(fVectorB, fLine.getOrigin(), "The IFVector reference is erroneous"),
                    () -> assertSame(fLineRef, fLine, "The IFLine reference should not change")
            );
        }

        @Test
        @DisplayName("Set origin ref (throw NullPointerException)")
        void setOriginRefThrowNullPointerException() {
            IFLine fLine = FactoryGeometry.getIFLine(HelperRandom.getTestVector());

            assertThrows(NullPointerException.class, () -> fLine.setOriginRef(null),
                    "The reference cannot be null" );
        }

        @Test
        @DisplayName("Get origin")
        void getOrigin() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertSame(fVector, fLine.getOrigin(), "The IFVector reference is erroneous");
        }

        @Test
        @DisplayName("Get origin (validate positions)")
        void getOriginValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector.copy());

            assertEquals(fVector, fLine.getOrigin(), "The IFVector positions are erroneous");
        }

    }

    @Nested
    @DisplayName("Core features")
    class ICoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = FactoryGeometry.getIFLine().importFromJSON(fLineA.exportToJSON());

            assertAll("Validate JSON parser",
                    () -> assertNotSame(fLineA, fLineB,
                            "IFLine references should point at different objects"),
                    () -> assertEquals(fLineA.getOrigin(), fLineB.getOrigin(),
                            "The origin of IFLines should be exact")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isExact(fLineB), "IFLines should be equal"),
                    () -> assertTrue(fLineB.isExact(fLineB), "IFLines should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isExact(fLineB), "IFLines should not be equal"),
                    () -> assertFalse(fLineB.isExact(fLineA), "IFLines should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (throw NullPointerException)")
        void isExactThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLine = FactoryGeometry.getIFLine(fVector);

            assertThrows(NullPointerException.class,
                    () -> fLine.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(0.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertTrue(fLineA.isSimilar(fLineB), "IFLines should be similar"),
                    () -> assertTrue(fLineB.isSimilar(fLineA), "IFLines should be similar")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy().add(1.5 * jitter));

            assertAll("Validate exactness",
                    () -> assertFalse(fLineA.isSimilar(fLineB), "IFLines should not be similar"),
                    () -> assertFalse(fLineB.isSimilar(fLineA), "IFLines should not be similar")
            );
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector.copy());
            IFLine fLineB = FactoryGeometry.getIFLine(fVector.copy());

            assertEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two identical IFPoints should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = FactoryGeometry.getIFLine(HelperRandom.getTestVector(fVector));

            assertNotEquals(fLineA.hashCode(), fLineB.hashCode(),
                    "Two different IFLines should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVector = HelperRandom.getTestVector();
            IFLine fLineA = FactoryGeometry.getIFLine(fVector);
            IFLine fLineB = fLineA.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fLineA, fLineB,
                            "IFLines represent different objects"),
                    () -> assertEquals(fLineA, fLineB,
                            "IFLines should have the same values"),
                    () -> assertNotEquals(fLineA, fLineB.getOrigin().add(jitter),
                            "FIFLines should have different values")
            );
        }

    }

    @Nested
    @DisplayName("Functionality - Advanced")
    class IFLineAdvanced {

    }
}

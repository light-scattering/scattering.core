package eu.scattering.core.geometry;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import static eu.scattering.core.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFVector")
public class IFVectorTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFVectorBase {

        @Test
        @DisplayName("Constructor")
        void construct() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertNotNull(fVector, "The generated IFVector instance is null");
            assertNotSame(fVector.getBase(), fVector.getHead(), "IFPoints should have different references");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint head")
        void constructWithHead() {
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointHead);

            assertNotNull(fVector, "The generated IFVector instance is null");
            assertNotSame(fVector.getBase(), fVector.getHead(), "IFPoints should have different references");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint head (validate reference)")
        void constructWithHeadValidateReference() {
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointHead);

            fPointHead.set(0, 0, 0);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint base/head")
        void constructWithBaseHead() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertNotNull(fVector, "The generated IFVector instance is null");
            assertNotSame(fVector.getBase(), fVector.getHead(), "IFPoints should have different references");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointBase.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint base/head (validate reference)")
        void constructWithBaseHeadValidateReference() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fPointBase.set(0, 0, 0);
            fPointHead.set(0, 0, 0);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint base/head (throw IllegalArgumentException)")
        void constructWithBaseHeadThrowIllegalArgumentException() {
            IFPoint fPoint = HelperRandom.getTestPoint();

            assertThrows(IllegalArgumentException.class, () -> FactoryGeometry.getIFVector(fPoint, fPoint),
                    "IFPoints must not be the same object" );
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            IFVector fVectorRef = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(fVectorRef);

            assertNotNull(fVector, "The generated IFVector instance is null");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVectorRef.getBase().getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVectorRef.getBase().getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVectorRef.getBase().getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fVectorRef.getHead().getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fVectorRef.getHead().getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fVectorRef.getHead().getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFVector (validate reference)")
        void constructWithIFVectorValidateReference() {
            IFVector fVectorRef = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(fVectorRef);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The IFPoint base is the same object"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The IFPoint head is the same object")
            );
        }

        @Test
        @DisplayName("Get base")
        void getBase() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base")
        void setBase() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setBase(FactoryGeometry.getIFPoint());

            assertSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set base (throw NullPointerException)")
        void setBaseThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setBase(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set base ref")
        void setBaseRef() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setBaseRef(FactoryGeometry.getIFPoint());

            assertNotSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set base ref (throw NullPointerException)")
        void setBaseRefThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setBaseRef(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set base ref (throw IllegalArgumentException)")
        void setBaseRefThrowIllegalArgumentException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setBaseRef(fPointHead),
                    "IFPoints must not be the same object");
        }

        @Test
        @DisplayName("Get head")
        void getHead() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertSame(fVector.getHead(), fPointHead, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head")
        void setHead() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setHead(FactoryGeometry.getIFPoint());

            assertSame(fVector.getHead(), fPointHead, "The IFPoint reference is erroneous");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointBase.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set head (throw NullPointerException)")
        void setHeadThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setHead(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set head ref")
        void setHeadRef() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setHeadRef(FactoryGeometry.getIFPoint());

            assertNotSame(fVector.getHead(), fPointHead, "The IFPoint reference is incorrect");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointBase.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set head ref (throw NullPointerException)")
        void setHeadRefThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setHeadRef(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set head ref (throw IllegalArgumentException)")
        void setHeadRefThrowIllegalArgumentException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setHeadRef(fPointBase),
                    "IFPoints must not be an instance of the same object");
        }

        @Test
        @DisplayName("Set IFPoints")
        void setBaseHead() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.set(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

            assertSame(fVector.getBase(), fPointBase, "The IFPoint reference is incorrect");
            assertSame(fVector.getHead(), fPointHead, "The IFPoint reference is incorrect");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set IFPoints (throw NullPointerException)")
        void setBaseHeadThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertAll("Validate NullPointerExceptions",
                    () -> assertThrows(NullPointerException.class, () -> fVector.set(null, fPointHead),
                            "The base IFPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.set(fPointBase, null),
                            "The head IFPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.set(null, null),
                            "The reference IFPoints must not be null")
            );
        }

        @Test
        @DisplayName("Set IFPoints ref")
        void setBaseHeadRef() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setRef(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

            assertNotSame(fVector.getBase(), fPointBase, "The base IFPoint is incorrect");
            assertNotSame(fVector.getHead(), fPointHead, "The head IFPoint is incorrect");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set IFPoints ref (throw NullPointerException)")
        void setBaseHeadRefThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertAll("Validate NullPointerExceptions",
                    () -> assertThrows(NullPointerException.class, () -> fVector.setRef(null, fPointHead),
                            "The base IFPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.setRef(fPointBase, null),
                            "The head IFPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.setRef(null, null),
                            "The reference IFPoints must not be null")
            );
        }

        @Test
        @DisplayName("Set IFPoints ref (throw IllegalArgumentException)")
        void setBaseHeadRefThrowIllegalArgumentException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setHeadRef(fPointBase),
                    "IFPoints must not be an instance of the same object");
        }

        @Test
        @DisplayName("Set IFVector")
        void setIFVector() {
            IFVector fVectorRef = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector();

            fVector.set(fVectorRef);

            assertNotSame(fVector.getBase(), fVectorRef.getBase(), "The base IFPoint is incorrect");
            assertNotSame(fVector.getHead(), fVectorRef.getHead(), "The head IFPoint is incorrect");

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVectorRef.getBase().getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVectorRef.getBase().getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVectorRef.getBase().getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fVectorRef.getHead().getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fVectorRef.getHead().getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fVectorRef.getHead().getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set IFVector (throw NullPointerException)")
        void setIFVectorThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertThrows(NullPointerException.class, () -> fVector.set(null),
                    "The base IFPoint must not be null");
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class IFVectorAdvanced {

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Random position")
        void setRandomPosition() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setRandom(fPointHead);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );

            assertAll("Validate IFPoint values",
                    () -> assertTrue(FactoryGeometry.getIFPoint(1, 1, 0).isExact(fVector.getBase()),
                    "The base IFPoint is erroneous"),
                    () -> assertFalse(FactoryGeometry.getIFPoint(2, 1, 0).isExact(fVector.getHead()),
                    "The head IFPoint has not been randomized")
            );
        }

        @Test
        @DisplayName("Relocate base")
        void relocateBase() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFPoint fPointRel = HelperRandom.getTestPoint(fPointBase,fPointHead);
            IFPoint fPointRef = fPointHead.copy().add(fPointRel.copy().sub(fPointBase));

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateBase(fPointRel);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRel.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRel.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRel.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Relocate base (throw NullPointerException")
        void relocateBaseThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.relocateBase(null),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Relocate head")
        void relocateHead() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFPoint fPointRel = HelperRandom.getTestPoint(fPointBase, fPointHead);
            IFPoint fPointRef = fPointBase.copy().add(fPointRel.copy().sub(fPointHead));

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateHead(fPointRel);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRef.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRel.getX(), fVector.getHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRel.getY(), fVector.getHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRel.getZ(), fVector.getHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Relocate head (throw NullPointerException")
        void relocateHeadThrowNullPointerException() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.relocateHead(null),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Add IFVector")
        void addIFVector() {
            IFVector fVectorSum = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            IFPoint fPointRef = fVector.getHead().copy().add(fVectorSum.getHead().copy().sub(fVectorSum.getBase()));

            fVector.add(fVectorSum);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVector.getBase().getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVector.getBase().getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVector.getBase().getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFVector")
        void subIFVector() {
            IFVector fVectorSub = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            IFPoint fPointRef = fVector.getHead().copy().sub(fVectorSub.getHead().copy().sub(fVectorSub.getBase()));

            fVector.sub(fVectorSub);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVector.getBase().getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVector.getBase().getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVector.getBase().getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get dimension X")
        void getDimX() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getX() - fVector.getBase().getX()), fVector.getDimX(),
                    "The X dimension is incorrect");
            assertEquals(Math.abs(fVector.getBase().getX() - fVector.getHead().getX()), fVector.getDimX(),
                    "The X dimension is incorrect");
        }

        @Test
        @DisplayName("Get dimension Y")
        void getDimY() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getY() - fVector.getBase().getY()), fVector.getDimY(),
                    "The Y dimension is incorrect");
            assertEquals(Math.abs(fVector.getBase().getY() - fVector.getHead().getY()), fVector.getDimY(),
                    "The Y dimension is incorrect");
        }

        @Test
        @DisplayName("Get dimension Z")
        void getDimZ() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getZ() - fVector.getBase().getZ()), fVector.getDimZ(),
                    "The Z dimension is incorrect");
            assertEquals(Math.abs(fVector.getBase().getZ() - fVector.getHead().getZ()), fVector.getDimZ(),
                    "The Z dimension is incorrect");
        }

    }
}

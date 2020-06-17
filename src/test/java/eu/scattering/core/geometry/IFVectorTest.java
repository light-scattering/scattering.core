package eu.scattering.core.geometry;

import eu.scattering.core.exception.SamePositionException;
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
        @DisplayName("Constructor (validate reference)")
        void constructValidateReference() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertAll("Validate IFPoint references",
                    () -> assertNotNull(fVector,
                            "The generated IFVector instance is null"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint head")
        void constructWithHead() {
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointHead);

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

            assertAll("Validate IFPoint references",
                    () -> assertNotNull(fVector,
                            "The generated IFVector instance is null"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint head (validate reference change)")
        void constructWithHeadValidateReferenceChange() {
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

            assertAll("Validate IFPoint references",
                    () -> assertNotNull(fVector,
                            "The generated IFVector instance is null"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint reference is erroneous"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint base/head (validate reference change)")
        void constructWithBaseHeadValidateReferenceChange() {
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
                    () -> assertNotNull(fVector,
                            "The generated IFVector instance is null"),
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base IFPoint reference is erroneous"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head IFPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should have different references")
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
        @DisplayName("Set base (validate reference)")
        void setBaseValidateReference() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setBase(FactoryGeometry.getIFPoint());

            assertSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");
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
        @DisplayName("Set base ref (validate reference)")
        void setBaseRefValidateReference() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setBaseRef(FactoryGeometry.getIFPoint());

            assertNotSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");
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
        @DisplayName("Set head (validate reference)")
        void setHeadValidateReference() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setHead(FactoryGeometry.getIFPoint());

            assertSame(fVector.getHead(), fPointHead, "The IFPoint reference is erroneous");
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
        @DisplayName("Set head ref (validate reference)")
        void setHeadRefValidateReference() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setHeadRef(FactoryGeometry.getIFPoint());

            assertNotSame(fVector.getHead(), fPointHead, "The IFPoint reference is incorrect");
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
        @DisplayName("Set IFPoints (validate reference)")
        void setBaseHeadValidateReference() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.set(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

            assertAll("Validate IFPoint references",
                    () -> assertSame(fPointBase, fVector.getBase(), "The base IFPoint is incorrect"),
                    () -> assertSame(fPointHead, fVector.getHead(), "The head IFPoint is incorrect")
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
        @DisplayName("Set IFPoints ref (validate references)")
        void setBaseHeadRefValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setRef(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fPointBase, "The base IFPoint is incorrect"),
                    () -> assertNotSame(fVector.getHead(), fPointHead, "The head IFPoint is incorrect")
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
        @DisplayName("Set IFVector (validate references)")
        void setIFVectorValidateReferences() {
            IFVector fVectorRef = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector();

            fVector.set(fVectorRef);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base IFPoint is incorrect"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head IFPoint is incorrect")
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
        @DisplayName("Set spherical coordinates (validate references)")
        void setSphericalCoordinatesValidateReferences() {
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
        }

        @Test
        @DisplayName("Random position")
        void setRandomPosition() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setRandom(fPointHead);

            assertAll("Validate IFPoint values",
                    () -> assertTrue(FactoryGeometry.getIFPoint(1, 1, 0).isExact(fVector.getBase()),
                    "The base IFPoint is erroneous"),
                    () -> assertFalse(FactoryGeometry.getIFPoint(2, 1, 0).isExact(fVector.getHead()),
                    "The head IFPoint has not been randomized")
            );
        }

        @Test
        @DisplayName("Random position (validate references)")
        void setRandomPositionValidateReferences() {
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
        @DisplayName("Relocate base (validate references)")
        void relocateBaseValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateBase(HelperRandom.getTestPoint(fPointBase,fPointHead));

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
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
        @DisplayName("Relocate head (validate references)")
        void relocateHeadValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateHead(HelperRandom.getTestPoint(fPointBase, fPointHead));

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
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
        @DisplayName("Add IFVector (validate references)")
        void addIFVectorValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFVector fVectorSum = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            fVector.add(fVectorSum);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
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
        @DisplayName("Sub IFVector (validate references)")
        void subIFVectorValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFVector fVectorSub = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());
            fVector.sub(fVectorSub);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
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

        @Test
        @DisplayName("Get magnitude")
        void getMagnitude() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 1);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 2, 2);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(Math.sqrt(3), fVector.getMagnitude(), jitter, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude (zero)")
        void getMagnitudeZero() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint();
            IFPoint fPointHead = FactoryGeometry.getIFPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(0, fVector.getMagnitude(), jitter, "The magnitude should be zero");
        }

        @Test
        @DisplayName("Get magnitude (random)")
        void getMagnitudeRandom() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFPoint fPointHead = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            double dimX = fVector.getDimX() * fVector.getDimX();
            double dimY = fVector.getDimY() * fVector.getDimY();
            double dimZ = fVector.getDimZ() * fVector.getDimZ();
            double magnitude = Math.sqrt(dimX + dimY + dimZ);

            assertEquals(magnitude, fVector.getMagnitude(), jitter, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(3, 3, 3);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(5, 5, 5);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setMagnitude(Math.sqrt(3));

            assertAll("Validate IFPoint values",
                    () -> assertEquals(3, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(4, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(4, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set magnitude (random)")
        void setMagnitudeRandom() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFPoint fPointHead = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setMagnitude(1);

            assertEquals(1, fVector.getMagnitude(), jitter, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (throw IllegalArgumentException)")
        void setMagnitudeThrowIllegalArgumentException() {
            IFVector fVector = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

            assertThrows(IllegalArgumentException.class, () -> fVector.setMagnitude(-1),
                    "The magnitude must be a positive value");
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.normalize();

            assertEquals(1, fVector.getMagnitude(), jitter, "The magnitude is incorrect");
        }

        @Test
        @DisplayName("Normalize (throw SamePositionException)")
        void normalizeThrowSamePositionException() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertThrows(SamePositionException.class, fVector::normalize,
                    "The IFPoints must not be on the same position");
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 2, 3);
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFPoint fPointRef = fPointHead.copy().sub(fPointBase).reflect().add(fPointBase);

            fVector.reflect();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect (validate references)")
        void reflectValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.reflect();

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Invert")
        void invert() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 2, 3);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(4, 5, 6);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.invert();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(4, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(5, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(6, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Invert (validate references)")
        void invertValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.invert();

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get inclination")
        void getInclination() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 2, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getInclination(), jitter,
                    "The IFVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get inclination (validate positions")
        void getInclinationValidatePositions() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 2, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.getInclination();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(1, 2, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setInclination(Math.PI * 0.5);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set inclination (validate references)")
        void setInclinationValidateReferences() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(1, 2, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setInclination(Math.PI * 0.5);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get azimuth")
        void getAzimuth() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 1);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getAzimuth(), jitter,
                    "The IFVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get azimuth (validate positions)")
        void getAzimuthValidatePositions() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 1);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.getAzimuth();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setAzimuth(Math.PI * 0.5);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set azimuth (validate references)")
        void setAzimuthValidateReferences() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 0);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setAzimuth(Math.PI * 0.5);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            IFPoint fPointBaseA = FactoryGeometry.getIFPoint(1, 1, 1);
            IFPoint fPointHeadA = FactoryGeometry.getIFPoint(2, 2, 2);
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = FactoryGeometry.getIFPoint(1, -1, 1);
            IFPoint fPointHeadB = FactoryGeometry.getIFPoint(2, -2, 2);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                            jitter, "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fVectorB.getAngle(fVectorA),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (validate positions)")
        void getAngleValidatePositions() {
            IFPoint fPointBaseA = FactoryGeometry.getIFPoint(1, 1, 1);
            IFPoint fPointHeadA = FactoryGeometry.getIFPoint(2, 2, 2);
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = FactoryGeometry.getIFPoint(1, -1, 1);
            IFPoint fPointHeadB = FactoryGeometry.getIFPoint(2, -2, 2);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVectorA.getBase().getX(),
                            "Base A - The X value is incorrect"),
                    () -> assertEquals(1, fVectorA.getBase().getY(),
                            "Base A - The Y value is incorrect"),
                    () -> assertEquals(1, fVectorA.getBase().getZ(),
                            "Base A - The Z value is incorrect"),
                    () -> assertEquals(2, fVectorA.getHead().getX(),
                            "Head A - The X value is incorrect"),
                    () -> assertEquals(2, fVectorA.getHead().getY(),
                            "Head A - The Y value is incorrect"),
                    () -> assertEquals(2, fVectorA.getHead().getZ(),
                            "Head A - The Z value is incorrect"),
                    () -> assertEquals(1, fVectorB.getBase().getX(),
                            "Base B - The X value is incorrect"),
                    () -> assertEquals(-1, fVectorB.getBase().getY(),
                            "Base B - The Y value is incorrect"),
                    () -> assertEquals(1, fVectorB.getBase().getZ(),
                            "Base B - The Z value is incorrect"),
                    () -> assertEquals(2, fVectorB.getHead().getX(),
                            "Head B - The X value is incorrect"),
                    () -> assertEquals(-2, fVectorB.getHead().getY(),
                            "Head B - The Y value is incorrect"),
                    () -> assertEquals(2, fVectorB.getHead().getZ(),
                            "Head B - The Z value is incorrect")
            );
        }

    }
}

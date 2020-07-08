package eu.scattering.core.geometry.main.base;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.helper.HelperNull;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.*;

import java.util.List;

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
        @DisplayName("Construct with parameters")
        void constructWithParameters() {
            double valAX = HelperRandom.getTestValue();
            double valAY = HelperRandom.getTestValue();
            double valAZ = HelperRandom.getTestValue();
            double valBX = HelperRandom.getTestValue();
            double valBY = HelperRandom.getTestValue();
            double valBZ = HelperRandom.getTestValue();
            IFVector fVector = FactoryGeometry.getIFVector(valAX, valAY, valAZ, valBX, valBY, valBZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(valAX, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(valAY, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(valAZ, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(valBX, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(valBY, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(valBZ, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with head parameters")
        void constructWithHeadParameters() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFVector fVector = FactoryGeometry.getIFVector(valX, valY, valZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(valX, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(valY, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(valZ, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
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
        @DisplayName("Construct with IFPoint head and parameters")
        void constructWithHeadAndParameters() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(valX, valY, valZ, fPointHead);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(valX, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(valY, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(valZ, fVector.getBase().getZ(),
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
        @DisplayName("Construct with IFPoint head and parameters (validate reference)")
        void constructWithHeadAndParametersValidateReference() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(valX, valY, valZ, fPointHead);

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
        @DisplayName("Construct with IFPoint head and parameters (validate reference change)")
        void constructWithHeadAndParametersValidateReferenceChange() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(valX, valY, valZ, fPointHead);

            fPointHead.set(0, 0, 0);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(valX, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(valY, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(valZ, fVector.getBase().getZ(),
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
        @DisplayName("Construct with IFPoint base and parameters")
        void constructWithBaseAndParameters() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, valX, valY, valZ);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointBase.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(valX, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(valY, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(valZ, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint base and parameters (validate reference)")
        void constructWithBaseAndParametersValidateReference() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, valX, valY, valZ);

            assertAll("Validate IFPoint references",
                    () -> assertNotNull(fVector,
                            "The generated IFVector instance is null"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The head IFPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with IFPoint base and parameters (validate reference change)")
        void constructWithBaseAndParametersValidateReferenceChange() {
            double valX = HelperRandom.getTestValue();
            double valY = HelperRandom.getTestValue();
            double valZ = HelperRandom.getTestValue();
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, valX, valY, valZ);

            fPointBase.set(0, 0, 0);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(valX, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(valY, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(valZ, fVector.getHead().getZ(),
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
        @DisplayName("Relocate base with parameters")
        void relocateBaseWithParameters() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFPoint fPointRel = HelperRandom.getTestPoint(fPointBase,fPointHead);
            IFPoint fPointRef = fPointHead.copy().add(fPointRel.copy().sub(fPointBase));

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateBase(fPointRel.getX(), fPointRel.getY(), fPointRel.getZ());

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
        @DisplayName("Relocate base to OX")
        void relocateBaseToOX() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFPoint fPointRef = fPointHead.copy().sub(fPointBase);

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateBase();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
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
        @DisplayName("Relocate head with parameters")
        void relocateHeadWithParameters() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFPoint fPointRel = HelperRandom.getTestPoint(fPointBase, fPointHead);
            IFPoint fPointRef = fPointBase.copy().add(fPointRel.copy().sub(fPointHead));

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateHead(fPointRel.getX(), fPointRel.getY(), fPointRel.getZ());

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
        @DisplayName("Relocate head to OX")
        void relocateHeadToOX() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);

            IFPoint fPointRef = fPointBase.copy().sub(fPointHead);

            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.relocateHead();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRef.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
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
        @DisplayName("Move forward")
        void moveForward() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFVector fVector = HelperRandom.getTestVector();

            IFVector fVectorRef = fVector.copy()
                    .relocateBase(fVector.copy()
                            .setLength(distance)
                            .getHead());

            fVector.moveForward(distance);

            assertTrue(fVector.isSimilar(fVectorRef),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move forward (simple)")
        void moveForwardSimple() {
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, 2, 2, 2);

            fVector.moveForward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(FactoryGeometry.getIFVector(2, 2, 2, 3, 3, 3)),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move forward (validate references)")
        void moveForwardValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFVector fVectorRef = fVector.moveForward(1);

            assertAll("Validate IFPoint references",
                    () -> assertSame(fVectorRef, fVector,
                            "The IFVector reference should not change"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Move forward (negative)")
        void moveForwardNegative() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = fVectorA.copy();

            fVectorA.moveForward(-distance);
            fVectorB.moveBackward(distance);

            assertTrue(fVectorA.isSimilar(fVectorB), "The two operations should have the same effect");
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFVector fVector = HelperRandom.getTestVector();

            IFVector fVectorRef = fVector.copy()
                    .relocateBase(fVector.copy()
                            .setLength(distance)
                            .reflectHead()
                            .getHead());

            fVector.moveBackward(distance);

            assertTrue(fVector.isSimilar(fVectorRef),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move backward (simple)")
        void moveBackwardSimple() {
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, 2, 2, 2);

            fVector.moveBackward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(FactoryGeometry.getIFVector(0, 0, 0, 1, 1, 1)),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move backward (validate references)")
        void moveBackwardValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFVector fVectorRef = fVector.moveBackward(1);

            assertAll("Validate IFPoint references",
                    () -> assertSame(fVectorRef, fVector,
                            "The IFVector reference should not change"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Move backward (negative)")
        void moveBackwardNegative() {
            double distance = Math.abs(HelperRandom.getTestValue());
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = fVectorA.copy();

            fVectorA.moveBackward(-distance);
            fVectorB.moveForward(distance);

            assertTrue(fVectorA.isSimilar(fVectorB), "The two operations should have the same effect");
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
        @DisplayName("Get length X")
        void getLengthX() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getX() - fVector.getBase().getX()), fVector.getLengthX(),
                    "The X length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getX() - fVector.getHead().getX()), fVector.getLengthX(),
                    "The X length is incorrect");
        }

        @Test
        @DisplayName("Get length Y")
        void getLengthY() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getY() - fVector.getBase().getY()), fVector.getLengthY(),
                    "The Y length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getY() - fVector.getHead().getY()), fVector.getLengthY(),
                    "The Y length is incorrect");
        }

        @Test
        @DisplayName("Get length Z")
        void getLengthZ() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(), HelperRandom.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getZ() - fVector.getBase().getZ()), fVector.getLengthZ(),
                    "The Z length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getZ() - fVector.getHead().getZ()), fVector.getLengthZ(),
                    "The Z length is incorrect");
        }

        @Test
        @DisplayName("Get center")
        void getCenter() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPoint fPointRef = FactoryGeometry.getIFPoint();

            fPointRef.setX(0.5 * (fVector.getHead().getX() + fVector.getBase().getX()));
            fPointRef.setY(0.5 * (fVector.getHead().getY() + fVector.getBase().getY()));
            fPointRef.setZ(0.5 * (fVector.getHead().getZ() + fVector.getBase().getZ()));

            assertEquals(fPointRef, fVector.getCenter(), "The IFPoint position is erroneous");
        }

        @Test
        @DisplayName("Get center (simple)")
        void getCenterSimple() {
            IFVector fVector = FactoryGeometry.getIFVector(-3, -3, -3, 3, 3, 3);
            IFPoint fPointRef = FactoryGeometry.getIFPoint();

            assertEquals(fPointRef, fVector.getCenter(), "The IFPoint position is erroneous");
        }

        @Test
        @DisplayName("Get center (validate positions)")
        void getCenterValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(-3, -3, -3, 3, 3, 3);
            IFVector fVectorRef = fVector.copy();

            fVector.getCenter();

            assertEquals(fVectorRef, fVector, "The IFVector should not change");
        }

        @Test
        @DisplayName("Get center (validate references)")
        void getCenterValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.getCenter();

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
        @DisplayName("Get length")
        void getLength() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 1);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 2, 2);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(Math.sqrt(3), fVector.getLength(), jitter, "The length is erroneous");
        }

        @Test
        @DisplayName("Get length (zero)")
        void getLengthZero() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint();
            IFPoint fPointHead = FactoryGeometry.getIFPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(0, fVector.getLength(), jitter, "The length should be zero");
        }

        @Test
        @DisplayName("Get length (random)")
        void getLengthRandom() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFPoint fPointHead = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            double dimX = fVector.getLengthX() * fVector.getLengthX();
            double dimY = fVector.getLengthY() * fVector.getLengthY();
            double dimZ = fVector.getLengthZ() * fVector.getLengthZ();
            double radius = Math.sqrt(dimX + dimY + dimZ);

            assertEquals(radius, fVector.getLength(), jitter, "The radius is erroneous");
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(3, 3, 3);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(5, 5, 5);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setLength(Math.sqrt(3));

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
        @DisplayName("Set length (random)")
        void setLengthRandom() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFPoint fPointHead = FactoryGeometry.getIFPoint(HelperRandom.getTestPoint());
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.setLength(1);

            assertEquals(1, fVector.getLength(), jitter, "The length is erroneous");
        }

        @Test
        @DisplayName("Set length (throw IllegalArgumentException)")
        void setLengthThrowIllegalArgumentException() {
            IFVector fVector = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

            assertThrows(IllegalArgumentException.class, () -> fVector.setLength(-1),
                    "The length must be a positive value");
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.normalize();

            assertEquals(1, fVector.getLength(), jitter, "The length is incorrect");
        }

        @Test
        @DisplayName("Normalize (throw SamePositionException)")
        void normalizeThrowSamePositionException() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertThrows(SamePositionException.class, fVector::normalize,
                    "The IFPoints must not be on the same position");
        }

        @Test
        @DisplayName("Reflect head")
        void reflectHead() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 2, 3);
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFPoint fPointRef = fPointHead.copy().sub(fPointBase).reflect().add(fPointBase);

            fVector.reflectHead();

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
        @DisplayName("Reflect head (validate references)")
        void reflectHeadValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.reflectHead();

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
        @DisplayName("Reflect base")
        void reflectBase() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = FactoryGeometry.getIFPoint(1, 2, 3);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            IFPoint fPointRef = fPointBase.copy().sub(fPointHead).reflect().add(fPointHead);

            fVector.reflectBase();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRef.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getBase().getZ(),
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
        @DisplayName("Reflect base (simple)")
        void reflectBaseSimple() {
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 1, 2, 2, 2);

            fVector.reflectBase();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(3, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(2, fVector.getHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect base (validate references)")
        void reflectBaseValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.reflectBase();

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
        @DisplayName("Reflect")
        void reflect() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFVector fVectorRef = FactoryGeometry.getIFVector(fVector.getBase().copy().reflect(fPoint),
                    fVector.getHead().copy().reflect(fPoint));

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(fVectorRef),"The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (simple)")
        void reflectSimple() {
            IFVector fVector = FactoryGeometry.getIFVector(1, 1, 0, 1, 3, 0);
            IFPoint fPoint = FactoryGeometry.getIFPoint(2, 2, 0);

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(3, 3, 0, 3, 1, 0),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate positions)")
        void reflectValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPoint fPoint = HelperRandom.getTestPoint();
            IFPoint fPointRef = fPoint.copy();

            fVector.reflect(fPoint);

            assertTrue(fPoint.isExact(fPointRef),"Values of the reference IFPoint should not change");
        }

        @Test
        @DisplayName("Reflect (validate references)")
        void reflectValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.reflect(fPoint);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Invert direction")
        void invertDirection() {
            IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 2, 3);
            IFPoint fPointHead = FactoryGeometry.getIFPoint(4, 5, 6);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.invertDirection();

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
        @DisplayName("Invert direction (validate references)")
        void invertDirectionValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            fVector.invertDirection();

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
            IFPoint fPointBaseA = FactoryGeometry.getIFPoint();
            IFPoint fPointHeadA = FactoryGeometry.getIFPoint(2, 2, 0);
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = FactoryGeometry.getIFPoint();
            IFPoint fPointHeadB = FactoryGeometry.getIFPoint(4, -4, 0);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                            jitter, "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fVectorB.getAngle(fVectorA),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(2, 2, 2));
            IFVector fVectorB = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(4, 4, 4));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(2, 2, 2));
            IFVector fVectorB = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(-4, -4, -4));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB),
                    jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(0, 1, 0));
            IFVector fVectorB = FactoryGeometry.getIFVector(HelperRandom.getTestPoint().setY(0));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                    jitter, "The angle is incorrect");
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

            fVectorA.getAngle(fVectorB);

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

        @Test
        @DisplayName("Get angle (validate references)")
        void getAngleValidateReferences() {
            IFPoint fPointBaseA = HelperRandom.getTestPoint();
            IFPoint fPointHeadA = HelperRandom.getTestPoint(fPointBaseA);
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = HelperRandom.getTestPoint(fPointBaseA, fPointHeadA);
            IFPoint fPointHeadB = HelperRandom.getTestPoint(fPointBaseA, fPointHeadA, fPointBaseB);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            fVectorA.getAngle(fVectorB);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVectorA.getBase(), fVectorA.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBaseA, fVectorA.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHeadA, fVectorA.getHead(),
                            "The head IFPoint should not change"),
                    () -> assertNotSame(fVectorB.getBase(), fVectorB.getHead(),
                            "IFPoints should be different objects"),
                    () -> assertSame(fPointBaseB, fVectorB.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHeadB, fVectorB.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get angle (throw NullPointerException)")
        void getAngleThrowNullPointerException() {
            IFVector fVectorA = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());
            IFVector fVectorB = HelperNull.getIFVector();

            assertThrows(NullPointerException.class, () -> fVectorA.getAngle(fVectorB),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Get angle with IFPoint")
        void getAngleWithIFPoint() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 0);
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, -4, 0);
            IFPoint fPointRel = HelperRandom.getTestPoint();

            fVector.relocateBase(fPointRel);
            fPoint.add(fPointRel);

            assertEquals(Math.PI * 0.5, fVector.getAngle(fPoint), jitter,
                    "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle with IFPoint (validate references)")
        void getAngleWithIFPointValidateReferences() {
            IFPoint base = FactoryGeometry.getIFPoint();
            IFPoint head = FactoryGeometry.getIFPoint(2, 2, 0);
            IFVector fVector = FactoryGeometry.getIFVector(base, head);
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, -4, 0);

            fVector.getAngle(fPoint);

            assertSame(base, fVector.getBase(),"The base IFPoint reference is incorrect");
        }

        @Test
        @DisplayName("Get angle with IFPoint (validate positions)")
        void getAngleWithIFPointValidatePositions() {
            IFVector fVector = FactoryGeometry.getIFVector(2, 2, 0);
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, -4, 0);
            IFPoint fPointRel = HelperRandom.getTestPoint();

            fVector.relocateBase(fPointRel);
            fPoint.add(fPointRel);

            fVector.getAngle(fPoint);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(FactoryGeometry.getIFPoint(4, -4, 0).add(fPointRel), fPoint,
                            "The IFPoint value is incorrect"),
                    () -> assertEquals(fPointRel, fVector.getBase(),
                            "The IFVector value is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle with IFPoint (throw NullPointerException)")
        void getAngleWithIFPointThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());
            IFPoint fPoint = HelperNull.getIFPoint();

            assertThrows(NullPointerException.class, () -> fVector.getAngle(fPoint),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            IFPoint fPointBaseA = HelperRandom.getTestPoint();
            IFPoint fPointHeadA = HelperRandom.getTestPoint();
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = HelperRandom.getTestPoint();
            IFPoint fPointHeadB = HelperRandom.getTestPoint();
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            double result = fVectorA.getDotProduct(fVectorB);

            fVectorA.relocateBase(FactoryGeometry.getIFPoint());
            fVectorB.relocateBase(FactoryGeometry.getIFPoint());

            IFPoint fPointA = fVectorA.getHead();
            IFPoint fPointB = fVectorB.getHead();

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            assertEquals(dimX + dimY + dimZ, result, jitter, "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (simple)")
        void getDotProductSimple() {
            IFVector fVectorA = FactoryGeometry.getIFVector(0, 0, 0, 1, 2, 3);
            IFVector fVectorB = FactoryGeometry.getIFVector(0, 0, 0, 4, 5, 6);

            assertEquals(32, fVectorA.getDotProduct(fVectorB),
                    "The resulting IFVector is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate references)")
        void getDotProductValidateReferences() {
            IFPoint fPointBaseA = HelperRandom.getTestPoint();
            IFPoint fPointHeadA = HelperRandom.getTestPoint();
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = HelperRandom.getTestPoint();
            IFPoint fPointHeadB = HelperRandom.getTestPoint();
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            fVectorA.getDotProduct(fVectorB);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVectorA.getBase(), fVectorA.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(fPointBaseA, fVectorA.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHeadA, fVectorA.getHead(),
                            "The head IFPoint should not change"),
                    () -> assertNotSame(fVectorB.getBase(), fVectorB.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(fPointBaseB, fVectorB.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHeadB, fVectorB.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get dot product (validate positions)")
        void getDotProductValidatePositions() {
            IFPoint fPointBaseA = FactoryGeometry.getIFPoint(1, 2, 3);
            IFPoint fPointHeadA = FactoryGeometry.getIFPoint(4, 5, 6);
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = FactoryGeometry.getIFPoint(-1, -2, -3);
            IFPoint fPointHeadB = FactoryGeometry.getIFPoint(-4, -5, -6);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            fVectorA.getDotProduct(fVectorB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVectorA.getBase().getX(),
                            "Base A - The X value is incorrect"),
                    () -> assertEquals(2, fVectorA.getBase().getY(),
                            "Base A - The Y value is incorrect"),
                    () -> assertEquals(3, fVectorA.getBase().getZ(),
                            "Base A - The Z value is incorrect"),
                    () -> assertEquals(4, fVectorA.getHead().getX(),
                            "Head A - The X value is incorrect"),
                    () -> assertEquals(5, fVectorA.getHead().getY(),
                            "Head A - The Y value is incorrect"),
                    () -> assertEquals(6, fVectorA.getHead().getZ(),
                            "Head A - The Z value is incorrect"),
                    () -> assertEquals(-1, fVectorB.getBase().getX(),
                            "Base B - The X value is incorrect"),
                    () -> assertEquals(-2, fVectorB.getBase().getY(),
                            "Base B - The Y value is incorrect"),
                    () -> assertEquals(-3, fVectorB.getBase().getZ(),
                            "Base B - The Z value is incorrect"),
                    () -> assertEquals(-4, fVectorB.getHead().getX(),
                            "Head B - The X value is incorrect"),
                    () -> assertEquals(-5, fVectorB.getHead().getY(),
                            "Head B - The Y value is incorrect"),
                    () -> assertEquals(-6, fVectorB.getHead().getZ(),
                            "Head B - The Z value is incorrect")
            );

        }

        @Test
        @DisplayName("Get dot product (throw NullPointerException)")
        void getDotProductThrowNullPointerException() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperNull.getIFVector();

            assertThrows(NullPointerException.class, () -> fVectorA.getDotProduct(fVectorB),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Get dot product with IFPoint")
        void getDotProductWithIFPoint() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPoint fPoint = HelperRandom.getTestPoint();

            double result = fVector.getDotProduct(fPoint);

            fPoint.sub(fVector.getBase());
            fVector.relocateBase();

            double dimX = fPoint.getX() * fVector.getHead().getX();
            double dimY = fPoint.getY() * fVector.getHead().getY();
            double dimZ = fPoint.getZ() * fVector.getHead().getZ();

            assertEquals(dimX + dimY + dimZ, result, jitter, "The value is not correct");
        }

        @Test
        @DisplayName("Get dot product with IFPoint (validate references)")
        void getDotProducttWithIFPointValidateReferences() {
            IFPoint base = FactoryGeometry.getIFPoint();
            IFPoint head = FactoryGeometry.getIFPoint(2, 2, 0);
            IFVector fVector = FactoryGeometry.getIFVector(base, head);
            IFPoint fPoint = FactoryGeometry.getIFPoint(4, -4, 0);

            fVector.getDotProduct(fPoint);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(base, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(head, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get dot product with IFPoint (validate positions)")
        void getDotProductWithIFPointValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPoint fPoint = HelperRandom.getTestPoint();
            IFPoint fPointRef = fPoint.copy();

            fVector.getDotProduct(fPoint);

            assertEquals(fPoint, fPointRef, "The IFVector value should not change");
        }

        @Test
        @DisplayName("Get dot product with IFPoint (throw NullPointerException)")
        void getDotProductWithIFPointThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());
            IFPoint fPoint = HelperNull.getIFPoint();

            assertThrows(NullPointerException.class, () -> fVector.getDotProduct(fPoint),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Get cross product")
        void getCrossProduct() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();

            IFVector fVectorRes = fVectorA.copy().getCrossProduct(fVectorB);

            IFPoint fPointRel = fVectorA.getBase().copy();

            fVectorA.relocateBase(FactoryGeometry.getIFPoint());
            fVectorB.relocateBase(FactoryGeometry.getIFPoint());

            IFPoint fPointA = fVectorA.getHead();
            IFPoint fPointB = fVectorB.getHead();

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            IFVector fVectorRef = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(dimX, dimY, dimZ));
            fVectorRef.relocateBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The resulting IFVector is erroneous");
        }

        @Test
        @DisplayName("Get cross product (simple)")
        void getCrossProductSimple() {
            IFVector fVectorA = FactoryGeometry.getIFVector(0, 0, 0, 0, 0, 1);
            IFVector fVectorB = FactoryGeometry.getIFVector(0, 0, 0, 1, 0, 0);

            fVectorA.relocateBase(1, 1, 1);
            fVectorB.relocateBase(-1, -1, -1);

            fVectorA.getCrossProduct(fVectorB);

            assertEquals(fVectorA, FactoryGeometry.getIFVector(1, 1, 1, 1, 2, 1),
                    "The resulting IFVector is erroneous");
        }

        @Test
        @DisplayName("Get cross product (validate references)")
        void getCrossProductValidateReferences() {
            IFPoint fPointBaseA = HelperRandom.getTestPoint();
            IFPoint fPointHeadA = HelperRandom.getTestPoint();
            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

            IFPoint fPointBaseB = HelperRandom.getTestPoint();
            IFPoint fPointHeadB = HelperRandom.getTestPoint();
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

            IFVector fVectorRef = fVectorA.getCrossProduct(fVectorB);

            assertAll("Validate IFPoint references",
                    () -> assertSame(fVectorRef, fVectorA,
                            "The IFVector reference should not change"),
                    () -> assertNotSame(fVectorA.getBase(), fVectorA.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(fPointBaseA, fVectorA.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHeadA, fVectorA.getHead(),
                            "The head IFPoint should not change"),
                    () -> assertNotSame(fVectorB.getBase(), fVectorB.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(fPointBaseB, fVectorB.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(fPointHeadB, fVectorB.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get cross product (validate positions)")
        void getCrossProductValidatePositions() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();
            IFVector fVectorRef = fVectorB.copy();

            fVectorA.getCrossProduct(fVectorB);

            assertEquals(fVectorB, fVectorRef, "The IFVector value should not change");
        }

        @Test
        @DisplayName("Get cross product (throw NullPointerException")
        void getCrossProductThrowNullPointerException() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperNull.getIFVector();

            assertThrows(NullPointerException.class, () -> fVectorA.getCrossProduct(fVectorB),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Get cross product with IFPoint")
        void getCrossProductWithIFPoint() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPoint fPoint = HelperRandom.getTestPoint();

            IFVector fVectorRes = fVector.copy().getCrossProduct(fPoint);
            IFPoint fPointRel = fVector.getBase().copy();

            fPoint.sub(fPointRel);
            fVector.relocateBase(0, 0, 0);

            double dimX = (fVector.getHead().getY() * fPoint.getZ()) - (fVector.getHead().getZ() * fPoint.getY());
            double dimY = (fVector.getHead().getZ() * fPoint.getX()) - (fVector.getHead().getX() * fPoint.getZ());
            double dimZ = (fVector.getHead().getX() * fPoint.getY()) - (fVector.getHead().getY() * fPoint.getX());

            IFVector fVectorRef = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(dimX, dimY, dimZ));
            fVectorRef.relocateBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The value is not correct");
        }

        @Test
        @DisplayName("Get cross product with IFPoint (validate references)")
        void getCrossProductWithIFPointValidateReferences() {
            IFPoint base = FactoryGeometry.getIFPoint();
            IFPoint head = HelperRandom.getTestPoint();
            IFVector fVector = FactoryGeometry.getIFVector(base, head);

            IFVector fVectorRef = fVector.getCrossProduct(HelperRandom.getTestPoint());

            assertAll("Validate IFPoint references",
                    () -> assertSame(fVectorRef, fVector,
                            "The IFVector reference should not change"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "IFPoints should point at different objects"),
                    () -> assertSame(base, fVector.getBase(),
                            "The base IFPoint should not change"),
                    () -> assertSame(head, fVector.getHead(),
                            "The head IFPoint should not change")
            );
        }

        @Test
        @DisplayName("Get cross product with IFPoint (validate positions)")
        void getCrossProductWithIFPointValidatePositions() {
            IFVector fVector = HelperRandom.getTestVector();
            IFPoint fPoint = HelperRandom.getTestPoint();
            IFPoint fPointRef = fPoint.copy();

            fVector.getCrossProduct(fPoint);

            assertEquals(fPoint, fPointRef, "The IFVector value should not change");
        }

        @Test
        @DisplayName("Get cross product with IFPoint (throw NullPointerException)")
        void getCrossProductWithIFPointThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());
            IFPoint fPoint = HelperNull.getIFPoint();

            assertThrows(NullPointerException.class, () -> fVector.getCrossProduct(fPoint),
                    "The reference IFPoint must not be null");
        }

        @Test
        @DisplayName("Is parallel A")
        void isParallelA() {
            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(2, 2, 2));
            IFVector fVectorB = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(4, 4, 4));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertTrue(fVectorA.isParallel(fVectorB), "The two IFVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel B")
        void isParallelB() {
            IFVector fVectorA = FactoryGeometry.getIFVector(-1, 0, 0, 1, 0, 0);
            IFVector fVectorB = FactoryGeometry.getIFVector(-1, 1, 0, 1, 1, 0);

            assertTrue(fVectorA.isParallel(fVectorB), "The two IFVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel (opposite direction")
        void isParallelOppositeDirection() {
            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(2, 2, 2));
            IFVector fVectorB = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(-4, -4, -4));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertTrue(fVectorA.isParallel(fVectorB), "The IFVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail)")
        void isParallelFail() {
            IFVector fVectorA = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());
            IFVector fVectorB = FactoryGeometry.getIFVector(HelperRandom.getTestPoint(fVectorA.getHead()));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The IFVectors should not be parallel");
        }

        @Test
        @DisplayName("Is parallel (throw NullPointerException)")
        void isParallelThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());

            assertThrows(NullPointerException.class, () -> fVector.isParallel(null),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Set parallel with IFVector")
        void setParallelWithIFVector() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();

            fVectorA.setParallel(fVectorB);

            assertTrue(fVectorA.isParallel(fVectorB), "The two IFVectors should be parallel");
        }

        @Test
        @DisplayName("Set parallel with IFVector (validate references)")
        void setParallelWithIFVectorValidateReferences() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();

            IFVector fVectorRef = fVectorA.setParallel(fVectorB);

            assertSame(fVectorRef, fVectorA, "The references should not change");
        }

        @Test
        @DisplayName("Set parallel with IFVector (validate positions)")
        void setParallelWithIFVectorValidatePositions() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();
            IFVector fVectorRef = fVectorB.copy();

            fVectorA.setParallel(fVectorB);

            assertEquals(fVectorRef, fVectorB, "The positions should not change");
        }

        @Test
        @DisplayName("Is orthogonal")
        void isOrthogonal() {
            IFVector fVectorA = FactoryGeometry.getIFVector(0, 1, 0);
            IFVector fVectorB = FactoryGeometry.getIFVector(HelperRandom.getTestPoint().setY(0));

            fVectorA.relocateBase(HelperRandom.getTestPoint());
            fVectorB.relocateBase(HelperRandom.getTestPoint());

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal (fail)")
        void isOrthogonalFail() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();

            while (Math.abs((Math.PI * 0.5) - fVectorA.getAngle(fVectorB))  <  jitter) {
                fVectorB = HelperRandom.getTestVector();
            }

            assertFalse(fVectorA.isOrthogonal(fVectorB), "IFVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal (throw NullPointerException)")
        void isOrthogonalThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector(HelperRandom.getTestPoint());

            assertThrows(NullPointerException.class, () -> fVector.isOrthogonal(null),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Set orthogonal with IFVector")
        void setOrthogonalWithIFVector() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector(fVectorA);
            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal with IFVector (simple)")
        void setOrthogonalWithIFVectorSimple() {
            IFVector fVectorA = FactoryGeometry.getIFVector(-1, 0, 0);
            IFVector fVectorB = FactoryGeometry.getIFVector(0, 0, 1, 1, 0, 0);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal with IFVector (same base)")
        void setOrthogonalWithIFVectorSameBase() {
            IFVector fVectorA = HelperRandom.getTestVector();

            IFPoint fVectorBHead = HelperRandom.getTestPoint(fVectorA.getHead());
            IFVector fVectorB = FactoryGeometry.getIFVector(fVectorA.getBase().copy(), fVectorBHead);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal with IFVector (same head)")
        void setOrthogonalWithIFVectorSameHead() {
            IFVector fVectorA = HelperRandom.getTestVector();

            IFPoint fVectorBBase = HelperRandom.getTestPoint(fVectorA.getBase());
            IFVector fVectorB = FactoryGeometry.getIFVector(fVectorBBase, fVectorA.getHead().copy());

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal with IFVector (validate references)")
        void setOrthogonalWithIFVectorValidateReferences() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();

            IFVector fVectorRef = fVectorA.setOrthogonal(fVectorB);

            assertSame(fVectorRef, fVectorA, "The references should not change");
        }

        @Test
        @DisplayName("Set orthogonal with IFVector (validate positions)")
        void setOrthogonalWithIFVectorValidatePositions() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = HelperRandom.getTestVector();

            IFVector fVectorRef = fVectorB.copy();

            fVectorA.setOrthogonal(fVectorB);

            assertEquals(fVectorRef, fVectorB, "The positions should not change");
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = fPointBase.copy();
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertTrue(fVector.isZero(), "The two IFPoints should be at the same position");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint(fPointBase);
            IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertFalse(fVector.isZero(), "The two IFPoints should not be at the same position");
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class IFCoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            IFVector fVectorRef = HelperRandom.getTestVector();
            IFVector fVector = FactoryGeometry.getIFVector().importFromJSON(fVectorRef.exportToJSON());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVectorRef.getBase(), fVector.getBase(),
                            "The base IFPoint is incorrect"),
                    () -> assertEquals(fVectorRef.getHead(), fVector.getHead(),
                            "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Is exact")
        void isExact() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBase.copy(), fPointHead.copy());
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertTrue(fVectorA.isExact(fVectorB), "IFVectors should be equal");
        }

        @Test
        @DisplayName("Is exact (fail)")
        void isExactFail() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(), fPointHead);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBase, FactoryGeometry.getIFPoint());

            assertFalse(fVectorA.isExact(fVectorB), "IFVectors should not be equal");
        }

        @Test
        @DisplayName("Is exact (throw NullPointerException)")
        void isExactThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertThrows(NullPointerException.class,
                    () -> fVector.isExact(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Is exact with parameters")
        void isExactWithParameters() {
            double bX = HelperRandom.getTestValue();
            double bY = HelperRandom.getTestValue();
            double bZ = HelperRandom.getTestValue();
            double hX = HelperRandom.getTestValue();
            double hY = HelperRandom.getTestValue();
            double hZ = HelperRandom.getTestValue();

            IFVector fVector = FactoryGeometry.getIFVector(bX, bY, bZ, hX, hY, hZ);

            assertTrue(fVector.isExact(bX, bY, bZ, hX, hY, hZ),
                    "IFVector values should be equal");
        }

        @Test
        @DisplayName("Is exact with parameters (fail)")
        void isExactWithParametersFail() {
            double bX = HelperRandom.getTestValue();
            double bY = HelperRandom.getTestValue();
            double bZ = HelperRandom.getTestValue();
            double hX = HelperRandom.getTestValue();
            double hY = HelperRandom.getTestValue();
            double hZ = HelperRandom.getTestValue();

            IFVector fVector = FactoryGeometry.getIFVector(bX, bY, bZ, hX, hY, hZ);

            assertFalse(fVector.isExact(0, 0, 0, 0, 0, 0),
                    "IFVector values should not be equal");
        }

        @Test
        @DisplayName("Is similar")
        void isSimilar() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBase.copy(), fPointHead.copy());
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBase.addX(jitter * 0.5), fPointHead);

            assertTrue(fVectorA.isSimilar(fVectorB), "IFVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (fail)")
        void isSimilarFail() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBase.copy(), fPointHead.copy());
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBase.addX(jitter * 1.5), fPointHead);

            assertFalse(fVectorA.isSimilar(fVectorB), "IFVectors should not be similar");
        }

        @Test
        @DisplayName("Is similar (throw NullPointerException)")
        void isSimilarThrowNullPointerException() {
            IFVector fVector = FactoryGeometry.getIFVector();

            assertThrows(NullPointerException.class,
                    () -> fVector.isSimilar(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Is similar with parameters")
        void isSimilarWithParameters() {
            double bX = HelperRandom.getTestValue();
            double bY = HelperRandom.getTestValue();
            double bZ = HelperRandom.getTestValue();
            double hX = HelperRandom.getTestValue();
            double hY = HelperRandom.getTestValue();
            double hZ = HelperRandom.getTestValue();

            IFVector fVector = FactoryGeometry.getIFVector(
                    bX + (0.5 * jitter), bY + (0.5 * jitter), bZ + (0.5 * jitter),
                    hX + (0.5 * jitter), hY + (0.5 * jitter), hZ + (0.5 * jitter));

            assertTrue(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "IFVector values should be equal");
        }

        @Test
        @DisplayName("Is similar with parameters (fail)")
        void isSimilarWithParametersFail() {
            double bX = HelperRandom.getTestValue();
            double bY = HelperRandom.getTestValue();
            double bZ = HelperRandom.getTestValue();
            double hX = HelperRandom.getTestValue();
            double hY = HelperRandom.getTestValue();
            double hZ = HelperRandom.getTestValue();

            IFVector fVector = FactoryGeometry.getIFVector(
                    bX + (1.5 * jitter), bY + (1.5 * jitter), bZ + (1.5 * jitter),
                    hX + (1.5 * jitter), hY + (1.5 * jitter), hZ + (1.5 * jitter));

            assertFalse(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "IFVector values should not be equal");
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBase.copy(), fPointHead.copy());
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBase, fPointHead);

            assertEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two identical IFVectors should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(), fPointHead);
            IFVector fVectorB = FactoryGeometry.getIFVector(fPointBase, FactoryGeometry.getIFPoint());

            assertNotEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two different IFVectors should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorB = fVectorA.copy();

            assertAll("Validate similarity",
                    () -> assertNotSame(fVectorA, fVectorB,
                            "IFVectors represent different objects"),
                    () -> assertEquals(fVectorA, fVectorB,
                            "IFVectors should have the same values"),
                    () -> assertNotSame(fVectorA.getBase(), fVectorB.getBase(),
                            "The base IFPoints should be different"),
                    () -> assertNotSame(fVectorA.getHead(), fVectorB.getHead(),
                            "The head IFPoints should be different")
            );
        }
    }

    @Nested
    @Tag("Algebra")
    @DisplayName("Base algebra")
    class IBaseAlgebra {

        @Test
        @DisplayName("Add IFPoint")
        void addIFPoint() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.add(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.add(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.add(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.addX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Y")
        void addY() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.addY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.addZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub IFPoint")
        void subIFPoint() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.sub(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.sub(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X")
        void subX() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.subX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.subY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.subZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul IFPoint")
        void mulIFPoint() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.mul(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul IFPoint (throw NullPointerException)")
        void mulIFPointThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(NullPointerException.class,
                    () -> fVector.mul(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.mul(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.mulX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.mulY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.mulZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div IFPoint")
        void divIFPoint() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.div(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().div(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().div(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div IFPoint (throw ArithmeticException)")
        void divIFPointThrowArithmeticException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(FactoryGeometry.getIFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(FactoryGeometry.getIFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(FactoryGeometry.getIFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div IFPoint (throw NullPointerException)")
        void divIFPointThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(NullPointerException.class,
                    () -> fVector.div(null), "The operand cannot be null");
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            IFPoint fPoint = HelperRandom.getTestPoint();

            fVector.div(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().div(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().div(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(0, 1, 1), "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(1, 0, 1), "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(0, 1, 1), "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.div(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().div(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().div(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.div(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div X")
        void divX() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();

            double value = HelperRandom.getTestValue();

            fVector.divX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().divX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().divX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div X (throw ArithmeticException)")
        void divXThrowArithmeticException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div Y")
        void divY() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.divY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().divY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().divY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div Y (throw ArithmeticException)")
        void divYThrowArithmeticException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {
            IFVector fVector = HelperRandom.getTestVector();
            IFVector fVectorRef = fVector.copy();
            double value = HelperRandom.getTestValue();

            fVector.divZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().divZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().divZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div Z (ArithmeticException)")
        void divZThrowArithmeticException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Get IFPoint list")
        void getIFPoints() {
            IFVector fVector = HelperRandom.getTestVector();

            List<IFPoint> list = fVector.disassemble();

            assertAll("Validate IFPoint list",
                    () -> assertEquals(2, list.size(), "The size of the list is incorrect"),
                    () -> assertSame(fVector.getBase(), list.get(0), "The base IFPoint is incorrect"),
                    () -> assertSame(fVector.getHead(), list.get(1), "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Swap")
        void swap() {
            IFVector fVectorA = HelperRandom.getTestVector();
            IFVector fVectorRefA = fVectorA.copy();
            IFVector fVectorB = HelperRandom.getTestVector();
            IFVector fVectorRefB = fVectorB.copy();

            fVectorA.swap(fVectorB);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVectorRefA, fVectorB, "The reference X value is incorrect"),
                    () -> assertEquals(fVectorRefB, fVectorA, "The reference Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Swap (validate references)")
        void swapValidateReferences() {
            IFPoint fPointBase = HelperRandom.getTestPoint();
            IFPoint fPointHead = HelperRandom.getTestPoint();

            IFVector fVectorA = FactoryGeometry.getIFVector(fPointBase, fPointHead);
            IFVector fVectorB = HelperRandom.getTestVector();

            fVectorA.swap(fVectorB);

            fPointBase.set(1, 2, 3);

            assertEquals(FactoryGeometry.getIFPoint(1, 2, 3), fVectorA.getBase(),
                    "The reference X value is incorrect");
        }

        @Test
        @DisplayName("Swap (throw NullPointerException)")
        void swapThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(NullPointerException.class, () -> fVector.swap(null),
                    "The reference IFVector must not be null");
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            IFVector fVectorRef = HelperRandom.getTestVector();
            IFVector fVector = FactoryGeometry.getIFVector();

            fVectorRef.imprint(fVector);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVector.getBase(), fVector.getBase(), "The base IFPoint is incorrect"),
                    () -> assertEquals(fVector.getHead(), fVector.getHead(), "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate references")
        void imprintValidateReferences() {
            IFVector fVectorRef = HelperRandom.getTestVector();
            IFVector fVector = HelperRandom.getTestVector();

            fVectorRef.imprint(fVector);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVectorRef, fVector,
                            "IFVectors should point to different objects"),
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base IFPoint reference is incorrect"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head IFPoint reference is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (throw NullPointerException)")
        void imprintThrowNullPointerException() {
            IFVector fVector = HelperRandom.getTestVector();

            assertThrows(NullPointerException.class, () -> fVector.imprint(null),
                    "The reference IFVector must not be null");
        }

    }
}

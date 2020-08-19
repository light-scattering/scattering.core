package eu.scattering.core.design.main.engine.base;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.engine.base.vector.FVectorAdvanced;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.support.exception.PositionException;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;
import eu.scattering.core.design.main.engine.base.helper.FVectorHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.util.List;

import static eu.scattering.core.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("IFVector")
public class FVectorTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class IFVectorBase {

        @Test
        @DisplayName("Constructor")
        void construct() {
            FVector fVector = MainFactory.getFVector();

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
            FVector fVector = MainFactory.getFVector();

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
            double valAX = RandomHelper.getTestValue();
            double valAY = RandomHelper.getTestValue();
            double valAZ = RandomHelper.getTestValue();
            double valBX = RandomHelper.getTestValue();
            double valBY = RandomHelper.getTestValue();
            double valBZ = RandomHelper.getTestValue();
            FVector fVector = MainFactory.getFVector(valAX, valAY, valAZ, valBX, valBY, valBZ);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FVector fVector = MainFactory.getFVector(valX, valY, valZ);

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
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointHead);

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
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointHead);

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
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointHead);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(valX, valY, valZ, fPointHead);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(valX, valY, valZ, fPointHead);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(valX, valY, valZ, fPointHead);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointBase = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, valX, valY, valZ);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointBase = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, valX, valY, valZ);

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
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointBase = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, valX, valY, valZ);

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
            FPoint fPoint = RandomHelper.getTestPoint();

            assertThrows(IllegalArgumentException.class, () -> MainFactory.getFVector(fPoint, fPoint),
                    "IFPoints must not be the same object" );
        }

        @Test
        @DisplayName("Construct with IFVector")
        void constructWithIFVector() {
            FVector fVectorRef = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector(fVectorRef);

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
            FVector fVectorRef = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector(fVectorRef);

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base")
        void setBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBase(MainFactory.getFPoint());

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBase(MainFactory.getFPoint());

            assertSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base (throw NullPointerException)")
        void setBaseThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setBase(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set base ref")
        void setBaseRef() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBaseRef(MainFactory.getFPoint());

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBaseRef(MainFactory.getFPoint());

            assertNotSame(fVector.getBase(), fPointBase, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base ref (throw NullPointerException)")
        void setBaseRefThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setBaseRef(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set base ref (throw IllegalArgumentException)")
        void setBaseRefThrowIllegalArgumentException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setBaseRef(fPointHead),
                    "IFPoints must not be the same object");
        }

        @Test
        @DisplayName("Get head")
        void getHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertSame(fVector.getHead(), fPointHead, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head")
        void setHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHead(MainFactory.getFPoint());

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHead(MainFactory.getFPoint());

            assertSame(fVector.getHead(), fPointHead, "The IFPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head (throw NullPointerException)")
        void setHeadThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setHead(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set head ref")
        void setHeadRef() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHeadRef(MainFactory.getFPoint());

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHeadRef(MainFactory.getFPoint());

            assertNotSame(fVector.getHead(), fPointHead, "The IFPoint reference is incorrect");
        }

        @Test
        @DisplayName("Set head ref (throw NullPointerException)")
        void setHeadRefThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setHeadRef(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set head ref (throw IllegalArgumentException)")
        void setHeadRefThrowIllegalArgumentException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setHeadRef(fPointBase),
                    "IFPoints must not be an instance of the same object");
        }

        @Test
        @DisplayName("Set IFPoints")
        void setBaseHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.set(MainFactory.getFPoint(), MainFactory.getFPoint());

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.set(MainFactory.getFPoint(), MainFactory.getFPoint());

            assertAll("Validate IFPoint references",
                    () -> assertSame(fPointBase, fVector.getBase(), "The base IFPoint is incorrect"),
                    () -> assertSame(fPointHead, fVector.getHead(), "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Set IFPoints (throw NullPointerException)")
        void setBaseHeadThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setRef(MainFactory.getFPoint(), MainFactory.getFPoint());

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setRef(MainFactory.getFPoint(), MainFactory.getFPoint());

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVector.getBase(), fPointBase, "The base IFPoint is incorrect"),
                    () -> assertNotSame(fVector.getHead(), fPointHead, "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Set IFPoints ref (throw NullPointerException)")
        void setBaseHeadRefThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setHeadRef(fPointBase),
                    "IFPoints must not be an instance of the same object");
        }

        @Test
        @DisplayName("Set IFVector")
        void setIFVector() {
            FVector fVectorRef = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector();

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
            FVector fVectorRef = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector();

            fVector.set(fVectorRef);

            assertAll("Validate IFPoint references",
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base IFPoint is incorrect"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head IFPoint is incorrect")
            );
        }

    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class IFVectorAdvanced {

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = MainFactory.getFPoint(2, 1, 0);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(1, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set spherical coordinates (validate)")
        void setSphericalCoordinatesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(e -> e.setSphericalCoordinates(0, 0), fVector);
        }

        @Test
        @DisplayName("Set random angle")
        void setRandomAngle() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = MainFactory.getFPoint(2, 1, 0);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setRandomAngle(fPointHead);

            assertAll("Validate IFPoint values",
                    () -> assertTrue(MainFactory.getFPoint(1, 1, 0).isExact(fVector.getBase()),
                    "The base IFPoint is erroneous"),
                    () -> assertFalse(MainFactory.getFPoint(2, 1, 0).isExact(fVector.getHead()),
                    "The head IFPoint has not been randomized")
            );
        }

        @Test
        @DisplayName("Set random angle (validate)")
        void setRandomAngleValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVectorAdvanced::setRandomAngle, fVector);
        }

        @Test
        @DisplayName("Move base")
        void moveBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase,fPointHead);
            FPoint fPointRef = fPointHead.copy().add(fPointRel.copy().sub(fPointBase));

            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveBase(fPointRel);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRel.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRel.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRel.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move base (validate)")
        void moveBaseValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateRef(FVector::moveBase, fVector, fPoint);
        }

        @Test
        @DisplayName("Move base with parameters")
        void moveBaseWithParameters() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase,fPointHead);
            FPoint fPointRef = fPointHead.copy().add(fPointRel.copy().sub(fPointBase));

            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveBase(fPointRel.getX(), fPointRel.getY(), fPointRel.getZ());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRel.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRel.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRel.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move base with parameters (validate)")
        void moveBaseWithParametersValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.moveBase(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Move base to OX")
        void moveBaseToOX() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRef = fPointHead.copy().sub(fPointBase);

            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveBase();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(0, fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move base to OX (validate)")
        void moveBaseToOXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVector::moveBase, fVector);
        }

        @Test
        @DisplayName("Move head")
        void moveHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase, fPointHead);
            FPoint fPointRef = fPointBase.copy().add(fPointRel.copy().sub(fPointHead));

            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveHead(fPointRel);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRef.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRel.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRel.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRel.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move head (validate)")
        void moveHeadValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateRef(FVector::moveHead, fVector, fPoint);
        }

        @Test
        @DisplayName("Move head with parameters")
        void moveHeadWithParameters() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase, fPointHead);
            FPoint fPointRef = fPointBase.copy().add(fPointRel.copy().sub(fPointHead));

            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveHead(fPointRel.getX(), fPointRel.getY(), fPointRel.getZ());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRef.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRel.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRel.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRel.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move head with parameters (validate)")
        void moveHeadWithParametersValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.moveHead(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Move head to OX")
        void moveHeadToOX() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRef = fPointBase.copy().sub(fPointHead);

            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveHead();

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fPointRef.getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move head to OX (validate)")
        void moveHeadToOXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVector::moveHead, fVector);
        }

        @Test
        @DisplayName("Move forward")
        void moveForward() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FVector fVector = RandomHelper.getTestVector();

            FVector fVectorRef = fVector.copy()
                    .moveBase(fVector.copy()
                            .setLength(distance)
                            .getHead());

            fVector.moveForward(distance);

            assertTrue(fVector.isSimilar(fVectorRef),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move forward (simple)")
        void moveForwardSimple() {
            FVector fVector = MainFactory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.moveForward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(MainFactory.getFVector(2, 2, 2, 3, 3, 3)),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move forward (opposite direction)")
        void moveForwardOppositeDirection() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = fVectorA.copy();

            fVectorA.moveForward(-distance);
            fVectorB.moveBackward(distance);

            assertTrue(fVectorA.isSimilar(fVectorB), "The two operations should have the same effect");
        }

        @Test
        @DisplayName("Move forward (throw DirectionException)")
        void moveForwardThrowDirectionException() {
            FVector fVector = MainFactory.getFVector();

            assertThrows(DirectionException.class, () -> fVector.moveForward(1),
                    "The direction of the IFVector is unknown");
        }

        @Test
        @DisplayName("Move forward (validate)")
        void moveForwardValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.moveForward(1), fVector);
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FVector fVector = RandomHelper.getTestVector();

            FVector fVectorRef = fVector.copy()
                    .moveBase(fVector.copy()
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
            FVector fVector = MainFactory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.moveBackward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(MainFactory.getFVector(0, 0, 0, 1, 1, 1)),
                    "The IFVector is erroneous");
        }

        @Test
        @DisplayName("Move backward (opposite direction)")
        void moveBackwardOppositeDirection() {
            double distance = Math.abs(RandomHelper.getTestValue());
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = fVectorA.copy();

            fVectorA.moveBackward(-distance);
            fVectorB.moveForward(distance);

            assertTrue(fVectorA.isSimilar(fVectorB), "The two operations should have the same effect");
        }

        @Test
        @DisplayName("Move backward (throw DirectionException)")
        void moveBackwardThrowDirectionException() {
            FVector fVector = MainFactory.getFVector();

            assertThrows(DirectionException.class, () -> fVector.moveBackward(1),
                    "The direction of the IFVector is unknown");
        }

        @Test
        @DisplayName("Move backward (validate)")
        void moveBackwardValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.moveBackward(1), fVector);
        }

        @Test
        @DisplayName("Add IFVector")
        void addIFVector() {
            FVector fVectorSum = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            FPoint fPointRef = fVector.getHead().copy().add(fVectorSum.getHead().copy().sub(fVectorSum.getBase()));

            fVector.add(fVectorSum);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVector.getBase().getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVector.getBase().getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVector.getBase().getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add IFVector (validate)")
        void addIFVectorValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::add, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Sub IFVector")
        void subIFVector() {
            FVector fVectorSub = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            FPoint fPointRef = fVector.getHead().copy().sub(fVectorSub.getHead().copy().sub(fVectorSub.getBase()));

            fVector.sub(fVectorSub);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVector.getBase().getX(), fVector.getBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVector.getBase().getY(), fVector.getBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVector.getBase().getZ(), fVector.getBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                            Config.getJitter(), "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                            Config.getJitter(), "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                            Config.getJitter(), "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub IFVector (validate)")
        void subIFVectorValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::sub, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get length X")
        void getLengthX() {
            FVector fVector = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getX() - fVector.getBase().getX()), fVector.getLengthX(),
                    "The X length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getX() - fVector.getHead().getX()), fVector.getLengthX(),
                    "The X length is incorrect");
        }

        @Test
        @DisplayName("Get length X (validate)")
        void getLengthXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::getLengthX, fVector);
        }

        @Test
        @DisplayName("Get length Y")
        void getLengthY() {
            FVector fVector = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getY() - fVector.getBase().getY()), fVector.getLengthY(),
                    "The Y length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getY() - fVector.getHead().getY()), fVector.getLengthY(),
                    "The Y length is incorrect");
        }

        @Test
        @DisplayName("Get length Y (validate)")
        void getLengthYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::getLengthY, fVector);
        }

        @Test
        @DisplayName("Get length Z")
        void getLengthZ() {
            FVector fVector = MainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getZ() - fVector.getBase().getZ()), fVector.getLengthZ(),
                    "The Z length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getZ() - fVector.getHead().getZ()), fVector.getLengthZ(),
                    "The Z length is incorrect");
        }

        @Test
        @DisplayName("Get length Z (validate)")
        void getLengthZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::getLengthZ, fVector);
        }

        @Test
        @DisplayName("Get length")
        void getLength() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 1);
            FPoint fPointHead = MainFactory.getFPoint(2, 2, 2);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.sqrt(3), fVector.getLength(), Config.getJitter(), "The length is erroneous");
        }

        @Test
        @DisplayName("Get length (zero)")
        void getLengthZero() {
            FPoint fPointBase = MainFactory.getFPoint();
            FPoint fPointHead = MainFactory.getFPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(0, fVector.getLength(), Config.getJitter(), "The length should be zero");
        }

        @Test
        @DisplayName("Get length (random)")
        void getLengthRandom() {
            FPoint fPointBase = MainFactory.getFPoint(RandomHelper.getTestPoint());
            FPoint fPointHead = MainFactory.getFPoint(RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            double dimX = fVector.getLengthX() * fVector.getLengthX();
            double dimY = fVector.getLengthY() * fVector.getLengthY();
            double dimZ = fVector.getLengthZ() * fVector.getLengthZ();
            double radius = Math.sqrt(dimX + dimY + dimZ);

            assertEquals(radius, fVector.getLength(), Config.getJitter(), "The radius is erroneous");
        }

        @Test
        @DisplayName("Get length (validate)")
        void getLengthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::getLength, fVector);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPointBase = MainFactory.getFPoint(3, 3, 3);
            FPoint fPointHead = MainFactory.getFPoint(5, 5, 5);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
        @DisplayName("Set length (opposite direction)")
        void setLengthOppositeDirection() {
            FVector fVector = MainFactory.getFVector(1, 1, 1);

            fVector.setLength(-2 * Math.sqrt(3));

            assertTrue(MainFactory.getFVector(-2, -2, -2).isSimilar(fVector), "" +
                    "The resulting IFVector position is incorrect");
        }

        @Test
        @DisplayName("Set length (random)")
        void setLengthRandom() {
            FPoint fPointBase = MainFactory.getFPoint(RandomHelper.getTestPoint());
            FPoint fPointHead = MainFactory.getFPoint(RandomHelper.getTestPoint());
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.setLength(1);

            assertEquals(1, fVector.getLength(), Config.getJitter(), "The length is erroneous");
        }

        @Test
        @DisplayName("Set length (throw DirectionException)")
        void setLengthThrowDirectionException() {
            FVector fVector = MainFactory.getFVector();

            assertThrows(DirectionException.class, () -> fVector.setLength(1),
                    "The direction of the IFVector is not defined");
        }

        @Test
        @DisplayName("Set length (validate)")
        void setLengthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.setLength(1), fVector);
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            fVector.normalize();

            assertEquals(1, fVector.getLength(), Config.getJitter(), "The length is incorrect");
        }

        @Test
        @DisplayName("Normalize (throw DirectionException)")
        void normalizeThrowDirectionException() {
            FVector fVector = MainFactory.getFVector();

            assertThrows(DirectionException.class, fVector::normalize,
                    "The direction of the IFVector is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVector::normalize, fVector);
        }

        @Test
        @DisplayName("Reflect head")
        void reflectHead() {
            FPoint fPointBase = MainFactory.getFPoint(1, 2, 3);
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            FPoint fPointRef = fPointHead.copy().sub(fPointBase).reflect().add(fPointBase);

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
        @DisplayName("Reflect head (validate)")
        void reflectHeadValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVector::reflectHead, fVector);
        }

        @Test
        @DisplayName("Reflect base")
        void reflectBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = MainFactory.getFPoint(1, 2, 3);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            FPoint fPointRef = fPointBase.copy().sub(fPointHead).reflect().add(fPointHead);

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
            FVector fVector = MainFactory.getFVector(1, 1, 1, 2, 2, 2);

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
        @DisplayName("Reflect base (validate)")
        void reflectBaseValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVector::reflectBase, fVector);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);
            FPoint fPoint = RandomHelper.getTestPoint();

            FVector fVectorRef = MainFactory.getFVector(fVector.getBase().copy().reflect(fPoint),
                    fVector.getHead().copy().reflect(fPoint));

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(fVectorRef),"The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (simple)")
        void reflectSimple() {
            FVector fVector = MainFactory.getFVector(1, 1, 0, 1, 3, 0);
            FPoint fPoint = MainFactory.getFPoint(2, 2, 0);

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(3, 3, 0, 3, 1, 0),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateRef(FVector::reflect, fVector, fPoint);
        }

        @Test
        @DisplayName("Invert direction")
        void invertDirection() {
            FPoint fPointBase = MainFactory.getFPoint(1, 2, 3);
            FPoint fPointHead = MainFactory.getFPoint(4, 5, 6);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
        @DisplayName("Invert direction (validate)")
        void invertDirectionValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(FVector::reflectHead, fVector);
        }

        @Test
        @DisplayName("Get inclination")
        void getInclination() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = MainFactory.getFPoint(2, 2, 0);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getInclination(), Config.getJitter(),
                    "The IFVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get inclination (validate)")
        void getInclinationValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::getInclination, fVector);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = MainFactory.getFPoint(1, 2, 0);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
        @DisplayName("Set inclination (validate)")
        void setInclinationValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.setInclination(Math.PI * 0.5), fVector);
        }

        @Test
        @DisplayName("Get azimuth")
        void getAzimuth() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = MainFactory.getFPoint(2, 1, 1);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getAzimuth(), Config.getJitter(),
                    "The IFVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get azimuth (validate)")
        void getAzimuthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::getAzimuth, fVector);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            FPoint fPointBase = MainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = MainFactory.getFPoint(2, 1, 0);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

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
        @DisplayName("Set azimuth (validate)")
        void setAzimuthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateRef(a -> a.setAzimuth(Math.PI * 0.5), fVector);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointBaseA = MainFactory.getFPoint();
            FPoint fPointHeadA = MainFactory.getFPoint(2, 2, 0);
            FVector fVectorA = MainFactory.getFVector(fPointBaseA, fPointHeadA);

            FPoint fPointBaseB = MainFactory.getFPoint();
            FPoint fPointHeadB = MainFactory.getFPoint(4, -4, 0);
            FVector fVectorB = MainFactory.getFVector(fPointBaseB, fPointHeadB);

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                            Config.getJitter(), "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fVectorB.getAngle(fVectorA),
                            Config.getJitter(), "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = MainFactory.getFVector(MainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = MainFactory.getFVector(MainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(0, 1, 0));
            FVector fVectorB = MainFactory.getFVector(RandomHelper.getTestPoint().setY(0));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw DirectionException, input)")
        void getAngleThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector();
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.getAngle(fVectorB),
                    "The direction of the input IFVector is not defined");
        }

        @Test
        @DisplayName("Get angle (throw DirectionException, argument)")
        void getAngleThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector();

            assertThrows(DirectionException.class, () -> fVectorA.getAngle(fVectorB),
                    "The direction of the argument IFVector is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::getAngle, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get angle with IFPoint")
        void getAngleWithIFPoint() {
            FVector fVector = MainFactory.getFVector(2, 2, 0);
            FPoint fPoint = MainFactory.getFPoint(4, -4, 0);
            FPoint fPointRel = RandomHelper.getTestPoint();

            fVector.moveBase(fPointRel);
            fPoint.add(fPointRel);

            assertEquals(Math.PI * 0.5, fVector.getAngle(fPoint), Config.getJitter(),
                    "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle with IFPoint (throw DirectionException)")
        void getAngleWithIFPointThrowDirectionException() {
            FVector fVector = MainFactory.getFVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            assertThrows(DirectionException.class, () -> fVector.getAngle(fPoint),
                    "The direction of the input IFVector is not defined");
        }

        @Test
        @DisplayName("Get angle with IFPoint (throw PositionException)")
        void getAngleWithIFPointThrowPositionException() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = fVector.getBase().copy();

            assertThrows(PositionException.class, () -> fVector.getAngle(fPoint),
                    "The argument IFPoint is at the same position as the base IFPoint");
        }

        @Test
        @DisplayName("Get angle with IFPoint (validate)")
        void getAngleWithIFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateVal(FVector::getAngle, fVector, fPoint);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            FPoint fPointBaseA = RandomHelper.getTestPoint();
            FPoint fPointHeadA = RandomHelper.getTestPoint();
            FVector fVectorA = MainFactory.getFVector(fPointBaseA, fPointHeadA);

            FPoint fPointBaseB = RandomHelper.getTestPoint();
            FPoint fPointHeadB = RandomHelper.getTestPoint();
            FVector fVectorB = MainFactory.getFVector(fPointBaseB, fPointHeadB);

            double result = fVectorA.getDotProduct(fVectorB);

            fVectorA.moveBase(MainFactory.getFPoint());
            fVectorB.moveBase(MainFactory.getFPoint());

            FPoint fPointA = fVectorA.getHead();
            FPoint fPointB = fVectorB.getHead();

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            assertEquals(dimX + dimY + dimZ, result, Config.getJitter(), "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (simple)")
        void getDotProductSimple() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0, 1, 2, 3);
            FVector fVectorB = MainFactory.getFVector(0, 0, 0, 4, 5, 6);

            assertEquals(32, fVectorA.getDotProduct(fVectorB),
                    "The resulting IFVector is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::getDotProduct, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get dot product with IFPoint")
        void getDotProductWithIFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            double result = fVector.getDotProduct(fPoint);

            fPoint.sub(fVector.getBase());
            fVector.moveBase();

            double dimX = fPoint.getX() * fVector.getHead().getX();
            double dimY = fPoint.getY() * fVector.getHead().getY();
            double dimZ = fPoint.getZ() * fVector.getHead().getZ();

            assertEquals(dimX + dimY + dimZ, result, Config.getJitter(), "The value is not correct");
        }

        @Test
        @DisplayName("Get dot product with IFPoint (validate)")
        void getDotProductWithIFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateVal(FVector::getDotProduct, fVector, fPoint);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            FVector fVectorRes = fVectorA.copy().setCrossProduct(fVectorB);

            FPoint fPointRel = fVectorA.getBase().copy();

            fVectorA.moveBase(MainFactory.getFPoint());
            fVectorB.moveBase(MainFactory.getFPoint());

            FPoint fPointA = fVectorA.getHead();
            FPoint fPointB = fVectorB.getHead();

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            FVector fVectorRef = MainFactory.getFVector(MainFactory.getFPoint(dimX, dimY, dimZ));
            fVectorRef.moveBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The resulting IFVector is erroneous");
        }

        @Test
        @DisplayName("Set cross product (simple)")
        void setCrossProductSimple() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0, 0, 0, 1);
            FVector fVectorB = MainFactory.getFVector(0, 0, 0, 1, 0, 0);

            fVectorA.moveBase(1, 1, 1);
            fVectorB.moveBase(-1, -1, -1);

            fVectorA.setCrossProduct(fVectorB);

            assertEquals(fVectorA, MainFactory.getFVector(1, 1, 1, 1, 2, 1),
                    "The resulting IFVector is erroneous");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::setCrossProduct, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set cross product with IFPoint")
        void setCrossProductWithIFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVector fVectorRes = fVector.copy().setCrossProduct(fPoint);
            FPoint fPointRel = fVector.getBase().copy();

            fPoint.sub(fPointRel);
            fVector.moveBase(0, 0, 0);

            double dimX = (fVector.getHead().getY() * fPoint.getZ()) - (fVector.getHead().getZ() * fPoint.getY());
            double dimY = (fVector.getHead().getZ() * fPoint.getX()) - (fVector.getHead().getX() * fPoint.getZ());
            double dimZ = (fVector.getHead().getX() * fPoint.getY()) - (fVector.getHead().getY() * fPoint.getX());

            FVector fVectorRef = MainFactory.getFVector(MainFactory.getFPoint(dimX, dimY, dimZ));
            fVectorRef.moveBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product with IFPoint (validate)")
        void setCrossProductWithIFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateRef(FVector::setCrossProduct, fVector, fPoint);
        }

        @Test
        @DisplayName("Is parallel A")
        void isParallelA() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = MainFactory.getFVector(MainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isParallel(fVectorB), "The two IFVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel B")
        void isParallelB() {
            FVector fVectorA = MainFactory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = MainFactory.getFVector(-1, 1, 0, 1, 1, 0);

            assertTrue(fVectorA.isParallel(fVectorB), "The two IFVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail)")
        void isParallelFail() {
            FVector fVectorA = MainFactory.getFVector(RandomHelper.getTestPoint());
            FVector fVectorB = MainFactory.getFVector(RandomHelper.getTestPoint(fVectorA.getHead()));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The IFVectors should not be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail, opposite direction")
        void isParallelOppositeDirection() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = MainFactory.getFVector(MainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The IFVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel (throw DirectionException, input)")
        void isParallelThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.isParallel(fVectorB),
                    "The input IFVector direction is not defined");
        }

        @Test
        @DisplayName("Is parallel (throw DirectionException, argument)")
        void isParallelThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector(0, 0, 0);

            assertThrows(DirectionException.class, () -> fVectorA.isParallel(fVectorB),
                    "The argument IFVector direction is not defined");
        }

        @Test
        @DisplayName("Is parallel (validate)")
        void isParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::isParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set parallel")
        void setParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setParallel(fVectorB);

            assertTrue(fVectorA.isParallel(fVectorB), "The two IFVectors should be parallel");
        }

        @Test
        @DisplayName("Set parallel (throw DirectionException, input)")
        void setParallelThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.setParallel(fVectorB),
                    "The input IFVector direction is not defined");
        }

        @Test
        @DisplayName("Set parallel (throw DirectionException, argument)")
        void setParallelThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector(0, 0, 0);

            assertThrows(DirectionException.class, () -> fVectorA.setParallel(fVectorB),
                    "The argument IFVector direction is not defined");
        }

        @Test
        @DisplayName("Set parallel (validate)")
        void setParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::setParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is anti-parallel A")
        void isAntiParallelA() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = MainFactory.getFVector(MainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two IFVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel B")
        void isAntiParallelB() {
            FVector fVectorA = MainFactory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = MainFactory.getFVector(1, 1, 0, -1, 1, 0);

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two IFVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (fail)")
        void isAntiParallelFail() {
            FVector fVectorA = MainFactory.getFVector(RandomHelper.getTestPoint());
            FVector fVectorB = MainFactory.getFVector(RandomHelper.getTestPoint(fVectorA.getHead()));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The IFVectors should not not be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (fail, opposite direction")
        void isAntiParallelOppositeDirection() {
            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = MainFactory.getFVector(MainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isAntiParallel(fVectorB), "The IFVectors should not be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (throw DirectionException, input)")
        void isAntiParallelThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.isAntiParallel(fVectorB),
                    "The input IFVector direction is not defined");
        }

        @Test
        @DisplayName("Is anti-parallel (throw DirectionException, argument)")
        void isAntiParallelThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector(0, 0, 0);

            assertThrows(DirectionException.class, () -> fVectorA.isAntiParallel(fVectorB),
                    "The argument IFVector direction is not defined");
        }

        @Test
        @DisplayName("Is anti-parallel (validate)")
        void isAntiParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::isAntiParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set anti-parallel")
        void setAntiParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setAntiParallel(fVectorB);

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two IFVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Set anti-parallel (throw DirectionException, input)")
        void setAntiParallelThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.setAntiParallel(fVectorB),
                    "The input IFVector direction is not defined");
        }

        @Test
        @DisplayName("Set anti-parallel (throw DirectionException, argument)")
        void setAntiParallelThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector(0, 0, 0);

            assertThrows(DirectionException.class, () -> fVectorA.setAntiParallel(fVectorB),
                    "The argument IFVector direction is not defined");
        }

        @Test
        @DisplayName("Set anti-parallel (validate)")
        void setAntiParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::setAntiParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is orthogonal")
        void isOrthogonal() {
            FVector fVectorA = MainFactory.getFVector(0, 1, 0);
            FVector fVectorB = MainFactory.getFVector(RandomHelper.getTestPoint().setY(0));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal A (fail)")
        void isOrthogonalAFail() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            while (fVectorA.getDotProduct(fVectorB) < Config.getJitter()) {
                fVectorB = RandomHelper.getTestVector();
            }

            assertFalse(fVectorA.isOrthogonal(fVectorB), "IFVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal B (fail)")
        void isOrthogonalBFail() {
            FVector fVectorA = MainFactory.getFVector(0, 1, 0);
            FVector fVectorB = MainFactory.getFVector(-1, 1, 0, 1, -1, 0);

            assertFalse(fVectorA.isOrthogonal(fVectorB), "IFVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal (throw DirectionException, input)")
        void isOrthogonalThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.isOrthogonal(fVectorB),
                    "The input IFVector direction is not defined");
        }

        @Test
        @DisplayName("Is orthogonal (throw DirectionException, argument)")
        void isOrthogonalThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector(0, 0, 0);

            assertThrows(DirectionException.class, () -> fVectorA.isOrthogonal(fVectorB),
                    "The argument IFVector direction is not defined");
        }

        @Test
        @DisplayName("Is orthogonal (validate)")
        void isOrthogonalValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::isOrthogonal, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set orthogonal")
        void setOrthogonal() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (simple)")
        void setOrthogonalSimple() {
            FVector fVectorA = MainFactory.getFVector(-1, 0, 0);
            FVector fVectorB = MainFactory.getFVector(0, 0, 1, 1, 0, 0);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (same base)")
        void setOrthogonalSameBase() {
            FVector fVectorA = RandomHelper.getTestVector();

            FPoint fVectorBHead = RandomHelper.getTestPoint(fVectorA.getHead());
            FVector fVectorB = MainFactory.getFVector(fVectorA.getBase().copy(), fVectorBHead);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (throw PositionException, parallel)")
        void setOrthogonalThrowPositionExceptionParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setParallel(fVectorB);

            assertThrows(PositionException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "IFVectors cannot be parallel");
        }

        @Test
        @DisplayName("Set orthogonal (throw PositionException, anti-parallel)")
        void setOrthogonalThrowPositionExceptionAntiParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setAntiParallel(fVectorB);

            assertThrows(PositionException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "IFVectors cannot be anti-parallel");
        }

        @Test
        @DisplayName("Set orthogonal (same head)")
        void setOrthogonalSameHead() {
            FVector fVectorA = RandomHelper.getTestVector();

            FPoint fVectorBBase = RandomHelper.getTestPoint(fVectorA.getBase());
            FVector fVectorB = MainFactory.getFVector(fVectorBBase, fVectorA.getHead().copy());

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two IFVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (throw DirectionException, input)")
        void setOrthogonalThrowDirectionExceptionInput() {
            FVector fVectorA = MainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(DirectionException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "The input IFVector direction is not defined");
        }

        @Test
        @DisplayName("Set orthogonal (throw DirectionException, argument)")
        void setOrthogonalThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = MainFactory.getFVector(0, 0, 0);

            assertThrows(DirectionException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "The argument IFVector direction is not defined");
        }

        @Test
        @DisplayName("Set orthogonal (validate)")
        void setOrthogonalValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::setOrthogonal, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is non-directional")
        void isNonDirectional() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = fPointBase.copy();
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertTrue(fVector.isNonDirectional(), "The two IFPoints should be at the same position");
        }

        @Test
        @DisplayName("Is non-directional (fail)")
        void isNonDirectionalFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);
            FVector fVector = MainFactory.getFVector(fPointBase, fPointHead);

            assertFalse(fVector.isNonDirectional(), "The two IFPoints should not be at the same position");
        }

        @Test
        @DisplayName("Is non-directional (validate)")
        void isNonDirectionalValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::isNonDirectional, fVector);
        }

    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class IFCoreFeatures {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVectorRef = RandomHelper.getTestVector();
            FVector fVector = MainFactory.getFVector().importFromJSON(fVectorRef.exportToJSON());

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVectorRef.getBase(), fVector.getBase(),
                            "The base IFPoint is incorrect"),
                    () -> assertEquals(fVectorRef.getHead(), fVector.getHead(),
                            "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::exportToJSON, fVector);
        }

        @Test
        @DisplayName("Is exact")
        void isExact() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = MainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = MainFactory.getFVector(fPointBase, fPointHead);

            assertTrue(fVectorA.isExact(fVectorB), "IFVectors should be equal");
        }

        @Test
        @DisplayName("Is exact (fail)")
        void isExactFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(), fPointHead);
            FVector fVectorB = MainFactory.getFVector(fPointBase, MainFactory.getFPoint());

            assertFalse(fVectorA.isExact(fVectorB), "IFVectors should not be equal");
        }

        @Test
        @DisplayName("Is exact (validate)")
        void isExactValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::isExact, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is exact with parameters")
        void isExactWithParameters() {
            double bX = RandomHelper.getTestValue();
            double bY = RandomHelper.getTestValue();
            double bZ = RandomHelper.getTestValue();
            double hX = RandomHelper.getTestValue();
            double hY = RandomHelper.getTestValue();
            double hZ = RandomHelper.getTestValue();

            FVector fVector = MainFactory.getFVector(bX, bY, bZ, hX, hY, hZ);

            assertTrue(fVector.isExact(bX, bY, bZ, hX, hY, hZ),
                    "IFVector values should be equal");
        }

        @Test
        @DisplayName("Is exact with parameters (fail)")
        void isExactWithParametersFail() {
            double bX = RandomHelper.getTestValue();
            double bY = RandomHelper.getTestValue();
            double bZ = RandomHelper.getTestValue();
            double hX = RandomHelper.getTestValue();
            double hY = RandomHelper.getTestValue();
            double hZ = RandomHelper.getTestValue();

            FVector fVector = MainFactory.getFVector(bX, bY, bZ, hX, hY, hZ);

            assertFalse(fVector.isExact(0, 0, 0, 0, 0, 0),
                    "IFVector values should not be equal");
        }

        @Test
        @DisplayName("Is exact with parameters (validate)")
        void isExactWithParametersValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.isExact(0, 0, 0, 0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Is similar")
        void isSimilar() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = MainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = MainFactory.getFVector(fPointBase.addX(Config.getJitter() * 0.5), fPointHead);

            assertTrue(fVectorA.isSimilar(fVectorB), "IFVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (zero)")
        void isSimilarZero() {
            FVector fVectorA = MainFactory.getFVector();
            FVector fVectorB = MainFactory.getFVector();

            assertTrue(fVectorA.isSimilar(fVectorB), "IFVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (fail)")
        void isSimilarFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = MainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = MainFactory.getFVector(fPointBase.addX(Config.getJitter() * 1.5), fPointHead);

            assertFalse(fVectorA.isSimilar(fVectorB), "IFVectors should not be similar");
        }

        @Test
        @DisplayName("Is similar (validate)")
        void isSimilarValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateVal(FVector::isSimilar, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is similar with parameters")
        void isSimilarWithParameters() {
            double bX = RandomHelper.getTestValue();
            double bY = RandomHelper.getTestValue();
            double bZ = RandomHelper.getTestValue();
            double hX = RandomHelper.getTestValue();
            double hY = RandomHelper.getTestValue();
            double hZ = RandomHelper.getTestValue();

            FVector fVector = MainFactory.getFVector(
                    bX + (0.5 * Config.getJitter()), bY + (0.5 * Config.getJitter()), bZ + (0.5 * Config.getJitter()),
                    hX + (0.5 * Config.getJitter()), hY + (0.5 * Config.getJitter()), hZ + (0.5 * Config.getJitter()));

            assertTrue(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "IFVector values should be equal");
        }

        @Test
        @DisplayName("Is similar with parameters (fail)")
        void isSimilarWithParametersFail() {
            double bX = RandomHelper.getTestValue();
            double bY = RandomHelper.getTestValue();
            double bZ = RandomHelper.getTestValue();
            double hX = RandomHelper.getTestValue();
            double hY = RandomHelper.getTestValue();
            double hZ = RandomHelper.getTestValue();

            FVector fVector = MainFactory.getFVector(
                    bX + (1.5 * Config.getJitter()), bY + (1.5 * Config.getJitter()), bZ + (1.5 * Config.getJitter()),
                    hX + (1.5 * Config.getJitter()), hY + (1.5 * Config.getJitter()), hZ + (1.5 * Config.getJitter()));

            assertFalse(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "IFVector values should not be equal");
        }

        @Test
        @DisplayName("Is similar with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.isSimilar(0, 0, 0, 0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = MainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = MainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two identical IFVectors should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = MainFactory.getFVector(MainFactory.getFPoint(), fPointHead);
            FVector fVectorB = MainFactory.getFVector(fPointBase, MainFactory.getFPoint());

            assertNotEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two different IFVectors should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::hashCode, fVector);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = fVectorA.copy();

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

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::copy, fVector);
        }

    }

    @Nested
    @Tag("Algebra")
    @DisplayName("Base algebra")
    class IBaseAlgebra {

        @Test
        @DisplayName("Add IFPoint")
        void addIFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.add(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add IFPoint (validate)")
        void addIFPointValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::add, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.add(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.add(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.add(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.add(0), fVector);
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.addX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add X (validate)")
        void addXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.addX(0), fVector);
        }

        @Test
        @DisplayName("Add Y")
        void addY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.addY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.addY(0), fVector);
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.addZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.addZ(0), fVector);
        }

        @Test
        @DisplayName("Sub IFPoint")
        void subIFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.sub(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub IFPoint (validate)")
        void subIFPointValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorHelper.validateRef(FVector::sub, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.sub(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.sub(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.sub(0), fVector);
        }

        @Test
        @DisplayName("Sub X")
        void subX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.subX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.subX(0), fVector);
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.subY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.subY(0), fVector);
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.subZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.subZ(0), fVector);
        }

        @Test
        @DisplayName("Mul IFPoint")
        void mulIFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.mul(fPoint);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul IFPoint (validate)")
        void mulIFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateRef(FVector::mul, fVector, fPoint);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(fPoint),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(fPoint),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.mul(1, 1, 1), fVector);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mul(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.mul(1), fVector);
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mulX(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulX(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulX(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.mulX(1), fVector);
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mulY(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulY(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulY(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.mulY(1), fVector);
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mulZ(value);

            assertAll("Validate IFPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulZ(value),
                            "The base IFPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulZ(value),
                            "The head IFPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.mulZ(1), fVector);
        }

        @Test
        @DisplayName("Div IFPoint")
        void divIFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

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
            FVector fVector = RandomHelper.getTestVector();

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(MainFactory.getFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(MainFactory.getFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(MainFactory.getFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div IFPoint (validate)")
        void divIFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorHelper.validateRef(FVector::div, fVector, fPoint);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

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
            FVector fVector = RandomHelper.getTestVector();

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
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.div(1, 1, 1), fVector);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

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
            FVector fVector = RandomHelper.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.div(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.div(1), fVector);
        }

        @Test
        @DisplayName("Div X")
        void divX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();

            double value = RandomHelper.getTestValue();

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
            FVector fVector = RandomHelper.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div X (validate)")
        void divXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.divX(1), fVector);
        }

        @Test
        @DisplayName("Div Y")
        void divY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

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
            FVector fVector = RandomHelper.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Y (validate)")
        void divYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.divY(1), fVector);
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

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
            FVector fVector = RandomHelper.getTestVector();

            assertThrows(ArithmeticException.class, () -> fVector.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Div Z (validate)")
        void divZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(e -> e.divZ(1), fVector);
        }

        @Test
        @DisplayName("Get IFPoint list")
        void getIFPoints() {
            FVector fVector = RandomHelper.getTestVector();

            List<FPoint> list = fVector.disassemble();

            assertAll("Validate IFPoint list",
                    () -> assertEquals(2, list.size(), "The size of the list is incorrect"),
                    () -> assertSame(fVector.getBase(), list.get(0), "The base IFPoint is incorrect"),
                    () -> assertSame(fVector.getHead(), list.get(1), "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Get IFPoint list (validate)")
        void getIFPointsValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorHelper.validateVal(FVector::disassemble, fVector);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FVector fVectorRef = RandomHelper.getTestVector();
            FVector fVector = MainFactory.getFVector();

            fVectorRef.imprint(fVector);

            assertAll("Validate IFPoint values",
                    () -> assertEquals(fVector.getBase(), fVector.getBase(), "The base IFPoint is incorrect"),
                    () -> assertEquals(fVector.getHead(), fVector.getHead(), "The head IFPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FVector fVectorRef = RandomHelper.getTestVector();
            FVector fVector = RandomHelper.getTestVector();

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

    }
}

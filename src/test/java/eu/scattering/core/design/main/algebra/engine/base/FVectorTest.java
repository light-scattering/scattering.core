package eu.scattering.core.design.main.algebra.engine.base;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.base.support.FVectorTestHelper;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import java.util.List;

import static eu.scattering.core.Config.mainFactory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVector")
public class FVectorTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FVectorBase {

        @Test
        @DisplayName("Constructor")
        void construct() {
            FVector fVector = mainFactory.getFVector();

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector();

            assertAll("Validate FPoint references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "FPoints should have different references")
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
            FVector fVector = mainFactory.getFVector(valAX, valAY, valAZ, valBX, valBY, valBZ);

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(valX, valY, valZ);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint head")
        void constructWithHead() {
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointHead);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint head (validate reference)")
        void constructWithHeadValidateReference() {
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointHead);

            assertAll("Validate FPoint references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with FPoint head (validate reference change)")
        void constructWithHeadValidateReferenceChange() {
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointHead);

            fPointHead.set(0, 0, 0);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint head and parameters")
        void constructWithHeadAndParameters() {
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(valX, valY, valZ, fPointHead);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint head and parameters (validate reference)")
        void constructWithHeadAndParametersValidateReference() {
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(valX, valY, valZ, fPointHead);

            assertAll("Validate FPoint references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with FPoint head and parameters (validate reference change)")
        void constructWithHeadAndParametersValidateReferenceChange() {
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(valX, valY, valZ, fPointHead);

            fPointHead.set(0, 0, 0);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint base and parameters")
        void constructWithBaseAndParameters() {
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointBase = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, valX, valY, valZ);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint base and parameters (validate reference)")
        void constructWithBaseAndParametersValidateReference() {
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointBase = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, valX, valY, valZ);

            assertAll("Validate FPoint references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with FPoint base and parameters (validate reference change)")
        void constructWithBaseAndParametersValidateReferenceChange() {
            double valX = RandomHelper.getTestValue();
            double valY = RandomHelper.getTestValue();
            double valZ = RandomHelper.getTestValue();
            FPoint fPointBase = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, valX, valY, valZ);

            fPointBase.set(0, 0, 0);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint base/head")
        void constructWithBaseHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint base/head (validate reference)")
        void constructWithBaseHeadValidateReference() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertAll("Validate FPoint references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertSame(fPointBase, fVector.getBase(),
                            "The base FPoint reference is erroneous"),
                    () -> assertSame(fPointHead, fVector.getHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with FPoint base/head (validate reference change)")
        void constructWithBaseHeadValidateReferenceChange() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fPointBase.set(0, 0, 0);
            fPointHead.set(0, 0, 0);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FPoint base/head (throw IllegalArgumentException)")
        void constructWithBaseHeadThrowIllegalArgumentException() {
            FPoint fPoint = RandomHelper.getTestPoint();

            assertThrows(IllegalArgumentException.class, () -> mainFactory.getFVector(fPoint, fPoint),
                    "FPoints must not be the same object" );
        }

        @Test
        @DisplayName("Construct with FVector")
        void constructWithFVector() {
            FVector fVectorRef = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector(fVectorRef);

            assertAll("Validate FPoint values",
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
        @DisplayName("Construct with FVector (validate reference)")
        void constructWithFVectorValidateReference() {
            FVector fVectorRef = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector(fVectorRef);

            assertAll("Validate FPoint references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base FPoint reference is erroneous"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getBase(), fVector.getHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Get base")
        void getBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertSame(fVector.getBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base")
        void setBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBase(mainFactory.getFPoint());

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBase(mainFactory.getFPoint());

            assertSame(fVector.getBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base (throw NullPointerException)")
        void setBaseThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setBase(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set base ref")
        void setBaseRef() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBaseRef(mainFactory.getFPoint());

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setBaseRef(mainFactory.getFPoint());

            assertNotSame(fVector.getBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base ref (throw NullPointerException)")
        void setBaseRefThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setBaseRef(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set base ref (throw IllegalArgumentException)")
        void setBaseRefThrowIllegalArgumentException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setBaseRef(fPointHead),
                    "FPoints must not be the same object");
        }

        @Test
        @DisplayName("Get head")
        void getHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertSame(fVector.getHead(), fPointHead, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head")
        void setHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHead(mainFactory.getFPoint());

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHead(mainFactory.getFPoint());

            assertSame(fVector.getHead(), fPointHead, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head (throw NullPointerException)")
        void setHeadThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setHead(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set head ref")
        void setHeadRef() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHeadRef(mainFactory.getFPoint());

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setHeadRef(mainFactory.getFPoint());

            assertNotSame(fVector.getHead(), fPointHead, "The FPoint reference is incorrect");
        }

        @Test
        @DisplayName("Set head ref (throw NullPointerException)")
        void setHeadRefThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(NullPointerException.class, () -> fVector.setHeadRef(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set head ref (throw IllegalArgumentException)")
        void setHeadRefThrowIllegalArgumentException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setHeadRef(fPointBase),
                    "FPoints must not be an instance of the same object");
        }

        @Test
        @DisplayName("Set FPoints")
        void setBaseHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.set(mainFactory.getFPoint(), mainFactory.getFPoint());

            assertAll("Validate FPoint values",
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
        @DisplayName("Set FPoints (validate reference)")
        void setBaseHeadValidateReference() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.set(mainFactory.getFPoint(), mainFactory.getFPoint());

            assertAll("Validate FPoint references",
                    () -> assertSame(fPointBase, fVector.getBase(), "The base FPoint is incorrect"),
                    () -> assertSame(fPointHead, fVector.getHead(), "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Set FPoints (throw NullPointerException)")
        void setBaseHeadThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertAll("Validate NullPointerExceptions",
                    () -> assertThrows(NullPointerException.class, () -> fVector.set(null, fPointHead),
                            "The base FPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.set(fPointBase, null),
                            "The head FPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.set(null, null),
                            "The reference FPoints must not be null")
            );
        }

        @Test
        @DisplayName("Set FPoints ref")
        void setBaseHeadRef() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setRef(mainFactory.getFPoint(), mainFactory.getFPoint());

            assertAll("Validate FPoint values",
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
        @DisplayName("Set FPoints ref (validate references)")
        void setBaseHeadRefValidateReferences() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setRef(mainFactory.getFPoint(), mainFactory.getFPoint());

            assertAll("Validate FPoint references",
                    () -> assertNotSame(fVector.getBase(), fPointBase, "The base FPoint is incorrect"),
                    () -> assertNotSame(fVector.getHead(), fPointHead, "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Set FPoints ref (throw NullPointerException)")
        void setBaseHeadRefThrowNullPointerException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertAll("Validate NullPointerExceptions",
                    () -> assertThrows(NullPointerException.class, () -> fVector.setRef(null, fPointHead),
                            "The base FPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.setRef(fPointBase, null),
                            "The head FPoint must not be null"),
                    () -> assertThrows(NullPointerException.class, () -> fVector.setRef(null, null),
                            "The reference FPoints must not be null")
            );
        }

        @Test
        @DisplayName("Set FPoints ref (throw IllegalArgumentException)")
        void setBaseHeadRefThrowIllegalArgumentException() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertThrows(IllegalArgumentException.class, () -> fVector.setHeadRef(fPointBase),
                    "FPoints must not be an instance of the same object");
        }

        @Test
        @DisplayName("Set FVector")
        void setFVector() {
            FVector fVectorRef = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector();

            fVector.set(fVectorRef);

            assertAll("Validate FPoint values",
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
        @DisplayName("Set FVector (validate references)")
        void setFVectorValidateReferences() {
            FVector fVectorRef = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector();

            fVector.set(fVectorRef);

            assertAll("Validate FPoint references",
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base FPoint is incorrect"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head FPoint is incorrect")
            );
        }

    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FVectorAdvanced {

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = mainFactory.getFPoint(2, 1, 0);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5);

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(e -> e.setSphericalCoordinates(0, 0), fVector);
        }

        @Test
        @DisplayName("Set random angle")
        void setRandomAngle() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = mainFactory.getFPoint(2, 1, 0);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setRandomAngle(fPointHead);

            assertAll("Validate FPoint values",
                    () -> assertTrue(mainFactory.getFPoint(1, 1, 0).isExact(fVector.getBase()),
                    "The base FPoint is erroneous"),
                    () -> assertFalse(mainFactory.getFPoint(2, 1, 0).isExact(fVector.getHead()),
                    "The head FPoint has not been randomized")
            );
        }

        @Test
        @DisplayName("Set random angle (validate)")
        void setRandomAngleValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testReference(e -> e.setRandomAngle(), fVector);
        }

        @Test
        @DisplayName("Move base")
        void moveBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase,fPointHead);
            FPoint fPointRef = fPointHead.copy().add(fPointRel.copy().sub(fPointBase));

            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveBase(fPointRel);

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::moveBase, fVector, fPoint);
        }

        @Test
        @DisplayName("Move base with parameters")
        void moveBaseWithParameters() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase,fPointHead);
            FPoint fPointRef = fPointHead.copy().add(fPointRel.copy().sub(fPointBase));

            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveBase(fPointRel.getX(), fPointRel.getY(), fPointRel.getZ());

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(a -> a.moveBase(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Move base to OX")
        void moveBaseToOX() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRef = fPointHead.copy().sub(fPointBase);

            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveBase();

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::moveBase, fVector);
        }

        @Test
        @DisplayName("Move head")
        void moveHead() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase, fPointHead);
            FPoint fPointRef = fPointBase.copy().add(fPointRel.copy().sub(fPointHead));

            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveHead(fPointRel);

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::moveHead, fVector, fPoint);
        }

        @Test
        @DisplayName("Move head with parameters")
        void moveHeadWithParameters() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRel = RandomHelper.getTestPoint(fPointBase, fPointHead);
            FPoint fPointRef = fPointBase.copy().add(fPointRel.copy().sub(fPointHead));

            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveHead(fPointRel.getX(), fPointRel.getY(), fPointRel.getZ());

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(a -> a.moveHead(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Move head to OX")
        void moveHeadToOX() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);

            FPoint fPointRef = fPointBase.copy().sub(fPointHead);

            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.moveHead();

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::moveHead, fVector);
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
                    "The FVector is erroneous");
        }

        @Test
        @DisplayName("Move forward (simple)")
        void moveForwardSimple() {
            FVector fVector = mainFactory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.moveForward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(mainFactory.getFVector(2, 2, 2, 3, 3, 3)),
                    "The FVector is erroneous");
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
        @DisplayName("Move forward (throw IllegalStateException)")
        void moveForwardThrowIllegalStateException() {
            FVector fVector = mainFactory.getFVector();

            assertThrows(IllegalStateException.class, () -> fVector.moveForward(1),
                    "The direction of the FVector is unknown");
        }

        @Test
        @DisplayName("Move forward (validate)")
        void moveForwardValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testReference(a -> a.moveForward(1), fVector);
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
                    "The FVector is erroneous");
        }

        @Test
        @DisplayName("Move backward (simple)")
        void moveBackwardSimple() {
            FVector fVector = mainFactory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.moveBackward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(mainFactory.getFVector(0, 0, 0, 1, 1, 1)),
                    "The FVector is erroneous");
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
        @DisplayName("Move backward (throw IllegalStateException)")
        void moveBackwardThrowIllegalStateException() {
            FVector fVector = mainFactory.getFVector();

            assertThrows(IllegalStateException.class, () -> fVector.moveBackward(1),
                    "The direction of the FVector is unknown");
        }

        @Test
        @DisplayName("Move backward (validate)")
        void moveBackwardValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testReference(a -> a.moveBackward(1), fVector);
        }

        @Test
        @DisplayName("Add FVector")
        void addFVector() {
            FVector fVectorSum = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            FPoint fPointRef = fVector.getHead().copy().add(fVectorSum.getHead().copy().sub(fVectorSum.getBase()));

            fVector.add(fVectorSum);

            assertAll("Validate FPoint values",
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
        @DisplayName("Add FVector (validate)")
        void addFVectorValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::add, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Sub FVector")
        void subFVector() {
            FVector fVectorSub = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            FPoint fPointRef = fVector.getHead().copy().sub(fVectorSub.getHead().copy().sub(fVectorSub.getBase()));

            fVector.sub(fVectorSub);

            assertAll("Validate FPoint values",
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
        @DisplayName("Sub FVector (validate)")
        void subFVectorValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::sub, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get length X")
        void getLengthX() {
            FVector fVector = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getX() - fVector.getBase().getX()), fVector.getLengthX(),
                    "The X length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getX() - fVector.getHead().getX()), fVector.getLengthX(),
                    "The X length is incorrect");
        }

        @Test
        @DisplayName("Get length X (validate)")
        void getLengthXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getLengthX, fVector);
        }

        @Test
        @DisplayName("Get length Y")
        void getLengthY() {
            FVector fVector = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getY() - fVector.getBase().getY()), fVector.getLengthY(),
                    "The Y length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getY() - fVector.getHead().getY()), fVector.getLengthY(),
                    "The Y length is incorrect");
        }

        @Test
        @DisplayName("Get length Y (validate)")
        void getLengthYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getLengthY, fVector);
        }

        @Test
        @DisplayName("Get length Z")
        void getLengthZ() {
            FVector fVector = mainFactory.getFVector(RandomHelper.getTestPoint(), RandomHelper.getTestPoint());

            assertEquals(Math.abs(fVector.getHead().getZ() - fVector.getBase().getZ()), fVector.getLengthZ(),
                    "The Z length is incorrect");
            assertEquals(Math.abs(fVector.getBase().getZ() - fVector.getHead().getZ()), fVector.getLengthZ(),
                    "The Z length is incorrect");
        }

        @Test
        @DisplayName("Get length Z (validate)")
        void getLengthZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getLengthZ, fVector);
        }

        @Test
        @DisplayName("Get length")
        void getLength() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 1);
            FPoint fPointHead = mainFactory.getFPoint(2, 2, 2);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.sqrt(3), fVector.getLength(), Config.getJitter(), "The length is erroneous");
        }

        @Test
        @DisplayName("Get length (zero)")
        void getLengthZero() {
            FPoint fPointBase = mainFactory.getFPoint();
            FPoint fPointHead = mainFactory.getFPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(0, fVector.getLength(), Config.getJitter(), "The length should be zero");
        }

        @Test
        @DisplayName("Get length (random)")
        void getLengthRandom() {
            FPoint fPointBase = mainFactory.getFPoint(RandomHelper.getTestPoint());
            FPoint fPointHead = mainFactory.getFPoint(RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            double dimX = fVector.getLengthX() * fVector.getLengthX();
            double dimY = fVector.getLengthY() * fVector.getLengthY();
            double dimZ = fVector.getLengthZ() * fVector.getLengthZ();
            double radius = Math.sqrt(dimX + dimY + dimZ);

            assertEquals(radius, fVector.getLength(),
                    Config.getJitter(), "The radius is erroneous");
        }

        @Test
        @DisplayName("Get length (validate)")
        void getLengthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getLength, fVector);
        }

        @Test
        @DisplayName("Get length P2")
        void getLengthP2() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 1);
            FPoint fPointHead = mainFactory.getFPoint(2, 2, 2);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(3, fVector.getLengthP2(),
                    Config.getJitter(), "The P2 length is erroneous");
        }

        @Test
        @DisplayName("Get length P2 (validate)")
        void getLengthP2Validate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getLengthP2, fVector);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPointBase = mainFactory.getFPoint(3, 3, 3);
            FPoint fPointHead = mainFactory.getFPoint(5, 5, 5);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setLength(Math.sqrt(3));

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(1, 1, 1);

            fVector.setLength(-2 * Math.sqrt(3));

            assertTrue(mainFactory.getFVector(-2, -2, -2).isSimilar(fVector), "" +
                    "The resulting FVector position is incorrect");
        }

        @Test
        @DisplayName("Set length (random)")
        void setLengthRandom() {
            FPoint fPointBase = mainFactory.getFPoint(RandomHelper.getTestPoint());
            FPoint fPointHead = mainFactory.getFPoint(RandomHelper.getTestPoint());
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setLength(1);

            assertEquals(1, fVector.getLength(), Config.getJitter(), "The length is erroneous");
        }

        @Test
        @DisplayName("Set length (throw IllegalStateException)")
        void setLengthThrowIllegalStateException() {
            FVector fVector = mainFactory.getFVector();

            assertThrows(IllegalStateException.class, () -> fVector.setLength(1),
                    "The direction of the FVector is not defined");
        }

        @Test
        @DisplayName("Set length (validate)")
        void setLengthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testReference(a -> a.setLength(1), fVector);
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.normalize();

            assertEquals(1, fVector.getLength(), Config.getJitter(), "The length is incorrect");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {
            FVector fVector = mainFactory.getFVector();

            assertThrows(IllegalStateException.class, fVector::normalize,
                    "The direction of the FVector is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testReference(FVector::normalize, fVector);
        }

        @Test
        @DisplayName("Reflect head")
        void reflectHead() {
            FPoint fPointBase = mainFactory.getFPoint(1, 2, 3);
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            FPoint fPointRef = fPointHead.copy().sub(fPointBase).reflect().add(fPointBase);

            fVector.reflectHead();

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::reflectHead, fVector);
        }

        @Test
        @DisplayName("Reflect base")
        void reflectBase() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = mainFactory.getFPoint(1, 2, 3);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            FPoint fPointRef = fPointBase.copy().sub(fPointHead).reflect().add(fPointHead);

            fVector.reflectBase();

            assertAll("Validate FPoint values",
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
            FVector fVector = mainFactory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.reflectBase();

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::reflectBase, fVector);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);
            FPoint fPoint = RandomHelper.getTestPoint();

            FVector fVectorRef = mainFactory.getFVector(fVector.getBase().copy().reflect(fPoint),
                    fVector.getHead().copy().reflect(fPoint));

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(fVectorRef),"The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (simple)")
        void reflectSimple() {
            FVector fVector = mainFactory.getFVector(1, 1, 0, 1, 3, 0);
            FPoint fPoint = mainFactory.getFPoint(2, 2, 0);

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(3, 3, 0, 3, 1, 0),
                    "The reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testReference(FVector::reflect, fVector, fPoint);
        }

        @Test
        @DisplayName("Invert direction")
        void invertDirection() {
            FPoint fPointBase = mainFactory.getFPoint(1, 2, 3);
            FPoint fPointHead = mainFactory.getFPoint(4, 5, 6);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.invertDirection();

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(FVector::reflectHead, fVector);
        }

        @Test
        @DisplayName("Get inclination")
        void getInclination() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = mainFactory.getFPoint(2, 2, 0);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getInclination(), Config.getJitter(),
                    "The FVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get inclination (validate)")
        void getInclinationValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getInclination, fVector);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = mainFactory.getFPoint(1, 2, 0);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setInclination(Math.PI * 0.5);

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(a -> a.setInclination(Math.PI * 0.5), fVector);
        }

        @Test
        @DisplayName("Get azimuth")
        void getAzimuth() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = mainFactory.getFPoint(2, 1, 1);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getAzimuth(), Config.getJitter(),
                    "The FVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get azimuth (validate)")
        void getAzimuthValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::getAzimuth, fVector);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            FPoint fPointBase = mainFactory.getFPoint(1, 1, 0);
            FPoint fPointHead = mainFactory.getFPoint(2, 1, 0);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            fVector.setAzimuth(Math.PI * 0.5);

            assertAll("Validate FPoint values",
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

            FVectorTestHelper.testReference(a -> a.setAzimuth(Math.PI * 0.5), fVector);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointBaseA = mainFactory.getFPoint();
            FPoint fPointHeadA = mainFactory.getFPoint(2, 2, 0);
            FVector fVectorA = mainFactory.getFVector(fPointBaseA, fPointHeadA);

            FPoint fPointBaseB = mainFactory.getFPoint();
            FPoint fPointHeadB = mainFactory.getFPoint(4, -4, 0);
            FVector fVectorB = mainFactory.getFVector(fPointBaseB, fPointHeadB);

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
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(0, 1, 0));
            FVector fVectorB = mainFactory.getFVector(RandomHelper.getTestPoint().setY(0));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, input)")
        void getAngleThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector();
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.getAngle(fVectorB),
                    "The direction of the input FVector is not defined");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, argument)")
        void getAngleThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.getAngle(fVectorB),
                    "The direction of the argument FVector is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::getAngle, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get angle with FPoint")
        void getAngleWithFPoint() {
            FVector fVector = mainFactory.getFVector(2, 2, 0);
            FPoint fPoint = mainFactory.getFPoint(4, -4, 0);
            FPoint fPointRel = RandomHelper.getTestPoint();

            fVector.moveBase(fPointRel);
            fPoint.add(fPointRel);

            assertEquals(Math.PI * 0.5, fVector.getAngle(fPoint), Config.getJitter(),
                    "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle with FPoint (throw IllegalStateException, direction)")
        void getAngleWithFPointThrowIllegalStateExceptionDirection() {
            FVector fVector = mainFactory.getFVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            assertThrows(IllegalStateException.class, () -> fVector.getAngle(fPoint),
                    "The direction of the input FVector is not defined");
        }

        @Test
        @DisplayName("Get angle with FPoint (throw IllegalStateException, position)")
        void getAngleWithFPointThrowIllegalStateExceptionPosition() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = fVector.getBase().copy();

            assertThrows(IllegalStateException.class, () -> fVector.getAngle(fPoint),
                    "The argument FPoint is at the same position as the base FPoint");
        }

        @Test
        @DisplayName("Get angle with FPoint (validate)")
        void getAngleWithFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testValue(FVector::getAngle, fVector, fPoint);
        }

        @Test
        @DisplayName("Set angle with FPoint")
        void setAngleWithFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();
            double angle = Math.abs(RandomHelper.getTestValue() % Math.PI);

            fVector.setAngle(fPoint, angle);

            assertEquals(angle, fVector.getAngle(fPoint),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Set angle with FPoint (negative)")
        void setAngleWithFPointNegative() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();
            double angle = -Math.abs(RandomHelper.getTestValue() % Math.PI);

            fVector.setAngle(fPoint, angle);

            assertEquals(angle, -fVector.getAngle(fPoint),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Set angle with FPoint (throw IllegalStateException, base)")
        void setAngleWithFPointThrowIllegalStateExceptionBase() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = fVector.getBase().copy();
            double angle = Math.abs(RandomHelper.getTestValue() % Math.PI);

            assertThrows(IllegalStateException.class, () -> fVector.setAngle(fPoint, angle),
                    "The argument FPoint is at the same position as the base FPoint");
        }

        @Test
        @DisplayName("Set angle with FPoint (throw IllegalStateException, head)")
        void setAngleWithFPointThrowIllegalStateExceptionHead() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = fVector.getHead().copy();
            double angle = Math.abs(RandomHelper.getTestValue() % Math.PI);

            assertThrows(IllegalStateException.class, () -> fVector.setAngle(fPoint, angle),
                    "The argument FPoint is at the same position as the head FPoint");
        }

        @Test
        @DisplayName("Set angle with FPoint (validate)")
        void setAngleWithFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testReference((a, b) -> a.setAngle(b, Math.PI), fVector, fPoint);
        }

        @Test
        @DisplayName("Set angle with FVector")
        void setAngleWithFVector() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();
            double angle = Math.abs(RandomHelper.getTestValue() % Math.PI);

            fVectorA.setAngle(fVectorB, angle);

            assertEquals(angle, fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Set angle with FVector (negative)")
        void setAngleWithFVectorNegative() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();
            double angle = -Math.abs(RandomHelper.getTestValue() % Math.PI);

            fVectorA.setAngle(fVectorB, angle);

            assertEquals(angle, -fVectorA.getAngle(fVectorB),
                    Config.getJitter(), "The angle is incorrect");
        }

        @Test
        @DisplayName("Set angle with FVector (throw IllegalStateException, position)")
        void setAngleWithFVectorThrowIllegalStateExceptionPosition() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = fVectorA.copy();
            double angle = Math.abs(RandomHelper.getTestValue() % Math.PI);

            assertThrows(IllegalStateException.class, () -> fVectorA.setAngle(fVectorB, angle),
                    "Both FVectors are at the same position");
        }

        @Test
        @DisplayName("Set angle with FVector (throw IllegalArgumentException, direction)")
        void setAngleWithFVectorThrowIllegalArgumentExceptionDirection() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector();
            double angle = Math.abs(RandomHelper.getTestValue() % Math.PI);

            assertThrows(IllegalArgumentException.class, () -> fVectorA.setAngle(fVectorB, angle),
                    "The direction of the provided FVector is not defined");
        }

        @Test
        @DisplayName("Set angle with FVector (validate)")
        void setAngleWithFVectorValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            FVectorTestHelper.testReference((a, b) -> a.setAngle(b, Math.PI), fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            FPoint fPointBaseA = RandomHelper.getTestPoint();
            FPoint fPointHeadA = RandomHelper.getTestPoint();
            FVector fVectorA = mainFactory.getFVector(fPointBaseA, fPointHeadA);

            FPoint fPointBaseB = RandomHelper.getTestPoint();
            FPoint fPointHeadB = RandomHelper.getTestPoint();
            FVector fVectorB = mainFactory.getFVector(fPointBaseB, fPointHeadB);

            double result = fVectorA.getDotProduct(fVectorB);

            fVectorA.moveBase(mainFactory.getFPoint());
            fVectorB.moveBase(mainFactory.getFPoint());

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
            FVector fVectorA = mainFactory.getFVector(0, 0, 0, 1, 2, 3);
            FVector fVectorB = mainFactory.getFVector(0, 0, 0, 4, 5, 6);

            assertEquals(32, fVectorA.getDotProduct(fVectorB),
                    "The resulting FVector is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::getDotProduct, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get dot product with FPoint")
        void getDotProductWithFPoint() {
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
        @DisplayName("Get dot product with FPoint (validate)")
        void getDotProductWithFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testValue(FVector::getDotProduct, fVector, fPoint);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            FVector fVectorRes = fVectorA.copy().setCrossProduct(fVectorB);

            FPoint fPointRel = fVectorA.getBase().copy();

            fVectorA.moveBase(mainFactory.getFPoint());
            fVectorB.moveBase(mainFactory.getFPoint());

            FPoint fPointA = fVectorA.getHead();
            FPoint fPointB = fVectorB.getHead();

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            FVector fVectorRef = mainFactory.getFVector(mainFactory.getFPoint(dimX, dimY, dimZ));
            fVectorRef.moveBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The resulting FVector is erroneous");
        }

        @Test
        @DisplayName("Set cross product (simple)")
        void setCrossProductSimple() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0, 0, 0, 1);
            FVector fVectorB = mainFactory.getFVector(0, 0, 0, 1, 0, 0);

            fVectorA.moveBase(1, 1, 1);
            fVectorB.moveBase(-1, -1, -1);

            fVectorA.setCrossProduct(fVectorB);

            assertEquals(fVectorA, mainFactory.getFVector(1, 1, 1, 1, 2, 1),
                    "The resulting FVector is erroneous");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::setCrossProduct, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set cross product with FPoint")
        void setCrossProductWithFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVector fVectorRes = fVector.copy().setCrossProduct(fPoint);
            FPoint fPointRel = fVector.getBase().copy();

            fPoint.sub(fPointRel);
            fVector.moveBase(0, 0, 0);

            double dimX = (fVector.getHead().getY() * fPoint.getZ()) - (fVector.getHead().getZ() * fPoint.getY());
            double dimY = (fVector.getHead().getZ() * fPoint.getX()) - (fVector.getHead().getX() * fPoint.getZ());
            double dimZ = (fVector.getHead().getX() * fPoint.getY()) - (fVector.getHead().getY() * fPoint.getX());

            FVector fVectorRef = mainFactory.getFVector(mainFactory.getFPoint(dimX, dimY, dimZ));
            fVectorRef.moveBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The value is not correct");
        }

        @Test
        @DisplayName("Set cross product with FPoint (validate)")
        void setCrossProductWithFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testReference(FVector::setCrossProduct, fVector, fPoint);
        }

        @Test
        @DisplayName("Is collinear A")
        void isCollinearA() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isCollinear(fVectorB), "The two FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear B")
        void isCollinearB() {
            FVector fVectorA = mainFactory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = mainFactory.getFVector(-1, 1, 0, 1, 1, 0);

            assertTrue(fVectorA.isCollinear(fVectorB), "The two FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear A (opposite direction")
        void isCollinearAOppositeDirection() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isCollinear(fVectorB), "The FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear B (opposite direction")
        void isCollinearBOppositeDirection() {
            FVector fVectorA = mainFactory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = mainFactory.getFVector(1, 1, 0, -1, 1, 0);

            assertTrue(fVectorA.isCollinear(fVectorB), "The two FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear (fail)")
        void isCollinearFail() {
            FVector fVectorA = mainFactory.getFVector(RandomHelper.getTestPoint());
            FVector fVectorB = mainFactory.getFVector(RandomHelper.getTestPoint(fVectorA.getHead()));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isCollinear(fVectorB), "The FVectors should not be collinear");
        }

        @Test
        @DisplayName("Is collinear (throw IllegalStateException, input)")
        void isCollinearThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.isCollinear(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is collinear (throw IllegalStateException, argument)")
        void isCollinearThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.isCollinear(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is collinear (validate)")
        void isCollinearValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isCollinear, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is parallel A")
        void isParallelA() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isParallel(fVectorB), "The two FVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel B")
        void isParallelB() {
            FVector fVectorA = mainFactory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = mainFactory.getFVector(-1, 1, 0, 1, 1, 0);

            assertTrue(fVectorA.isParallel(fVectorB), "The two FVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail)")
        void isParallelFail() {
            FVector fVectorA = mainFactory.getFVector(RandomHelper.getTestPoint());
            FVector fVectorB = mainFactory.getFVector(RandomHelper.getTestPoint(fVectorA.getHead()));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The FVectors should not be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail, opposite direction")
        void isParallelOppositeDirection() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The FVectors should not be parallel");
        }

        @Test
        @DisplayName("Is parallel (throw IllegalStateException, input)")
        void isParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.isParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is parallel (throw IllegalStateException, argument)")
        void isParallelThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.isParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is parallel (validate)")
        void isParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set parallel")
        void setParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setParallel(fVectorB);

            assertTrue(fVectorA.isParallel(fVectorB), "The two FVectors should be parallel");
        }

        @Test
        @DisplayName("Set parallel (throw IllegalStateException, input)")
        void setParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.setParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Set parallel (throw IllegalStateException, argument)")
        void setParallelThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.setParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Set parallel (validate)")
        void setParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::setParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is anti-parallel A")
        void isAntiParallelA() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two FVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel B")
        void isAntiParallelB() {
            FVector fVectorA = mainFactory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = mainFactory.getFVector(1, 1, 0, -1, 1, 0);

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two FVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (fail)")
        void isAntiParallelFail() {
            FVector fVectorA = mainFactory.getFVector(RandomHelper.getTestPoint());
            FVector fVectorB = mainFactory.getFVector(RandomHelper.getTestPoint(fVectorA.getHead()));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The FVectors should not not be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (fail, opposite direction")
        void isAntiParallelOppositeDirection() {
            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(2, 2, 2));
            FVector fVectorB = mainFactory.getFVector(mainFactory.getFPoint(4, 4, 4));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertFalse(fVectorA.isAntiParallel(fVectorB), "The FVectors should not be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (throw IllegalStateException, input)")
        void isAntiParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.isAntiParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is anti-parallel (throw IllegalStateException, argument)")
        void isAntiParallelThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.isAntiParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is anti-parallel (validate)")
        void isAntiParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isAntiParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set anti-parallel")
        void setAntiParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setAntiParallel(fVectorB);

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two FVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Set anti-parallel (throw IllegalStateException, input)")
        void setAntiParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.setAntiParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Set anti-parallel (throw DirectionException, argument)")
        void setAntiParallelThrowDirectionExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.setAntiParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Set anti-parallel (validate)")
        void setAntiParallelValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::setAntiParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is orthogonal")
        void isOrthogonal() {
            FVector fVectorA = mainFactory.getFVector(0, 1, 0);
            FVector fVectorB = mainFactory.getFVector(RandomHelper.getTestPoint().setY(0));

            fVectorA.moveBase(RandomHelper.getTestPoint());
            fVectorB.moveBase(RandomHelper.getTestPoint());

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal A (fail)")
        void isOrthogonalAFail() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            while (fVectorA.getDotProduct(fVectorB) < Config.getJitter()) {
                fVectorB = RandomHelper.getTestVector();
            }

            assertFalse(fVectorA.isOrthogonal(fVectorB), "FVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal B (fail)")
        void isOrthogonalBFail() {
            FVector fVectorA = mainFactory.getFVector(0, 1, 0);
            FVector fVectorB = mainFactory.getFVector(-1, 1, 0, 1, -1, 0);

            assertFalse(fVectorA.isOrthogonal(fVectorB), "FVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal (throw IllegalStateException, input)")
        void isOrthogonalThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.isOrthogonal(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is orthogonal (throw IllegalStateException, argument)")
        void isOrthogonalThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.isOrthogonal(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is orthogonal (validate)")
        void isOrthogonalValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isOrthogonal, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set orthogonal")
        void setOrthogonal() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);
            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (simple)")
        void setOrthogonalSimple() {
            FVector fVectorA = mainFactory.getFVector(-1, 0, 0);
            FVector fVectorB = mainFactory.getFVector(0, 0, 1, 1, 0, 0);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (same base)")
        void setOrthogonalSameBase() {
            FVector fVectorA = RandomHelper.getTestVector();

            FPoint fVectorBHead = RandomHelper.getTestPoint(fVectorA.getHead());
            FVector fVectorB = mainFactory.getFVector(fVectorA.getBase().copy(), fVectorBHead);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, parallel)")
        void setOrthogonalThrowIllegalStateExceptionParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setParallel(fVectorB);

            assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "FVectors cannot be parallel");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, anti-parallel)")
        void setOrthogonalThrowIllegalStateExceptionAntiParallel() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector();

            fVectorA.setAntiParallel(fVectorB);

            assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "FVectors cannot be anti-parallel");
        }

        @Test
        @DisplayName("Set orthogonal (same head)")
        void setOrthogonalSameHead() {
            FVector fVectorA = RandomHelper.getTestVector();

            FPoint fVectorBBase = RandomHelper.getTestPoint(fVectorA.getBase());
            FVector fVectorB = mainFactory.getFVector(fVectorBBase, fVectorA.getHead().copy());

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, input)")
        void setOrthogonalThrowIllegalStateExceptionInput() {
            FVector fVectorA = mainFactory.getFVector(0, 0, 0);
            FVector fVectorB = RandomHelper.getTestVector();

            assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, argument)")
        void setOrthogonalThrowIllegalStateExceptionArgument() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = mainFactory.getFVector(0, 0, 0);

            assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Set orthogonal (validate)")
        void setOrthogonalValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::setOrthogonal, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is non-directional")
        void isNonDirectional() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = fPointBase.copy();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertTrue(fVector.isNonDirectional(), "The two FPoints should be at the same position");
        }

        @Test
        @DisplayName("Is non-directional (fail)")
        void isNonDirectionalFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint(fPointBase);
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertFalse(fVector.isNonDirectional(), "The two FPoints should not be at the same position");
        }

        @Test
        @DisplayName("Is non-directional (validate)")
        void isNonDirectionalValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::isNonDirectional, fVector);
        }

        @Test
        @DisplayName("Is zero")
        void isZero() {
            FVector fVector = mainFactory.getFVector();

            assertTrue(fVector.isZero(), "The two FPoints should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        void isZeroFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = fPointBase.copy();
            FVector fVector = mainFactory.getFVector(fPointBase, fPointHead);

            assertFalse(fVector.isZero(), "The two FPoints should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::isZero, fVector);
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
            FVector fVector = mainFactory.getFVector().importFromJSON(fVectorRef.exportToJSON());

            assertAll("Validate FPoint values",
                    () -> assertEquals(fVectorRef.getBase(), fVector.getBase(),
                            "The base FPoint is incorrect"),
                    () -> assertEquals(fVectorRef.getHead(), fVector.getHead(),
                            "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::exportToJSON, fVector);
        }

        @Test
        @DisplayName("Is exact")
        void isExact() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = mainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = mainFactory.getFVector(fPointBase, fPointHead);

            assertTrue(fVectorA.isExact(fVectorB), "FVectors should be equal");
        }

        @Test
        @DisplayName("Is exact (fail)")
        void isExactFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(), fPointHead);
            FVector fVectorB = mainFactory.getFVector(fPointBase, mainFactory.getFPoint());

            assertFalse(fVectorA.isExact(fVectorB), "FVectors should not be equal");
        }

        @Test
        @DisplayName("Is exact (validate)")
        void isExactValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isExact, fVectorA, fVectorB);
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

            FVector fVector = mainFactory.getFVector(bX, bY, bZ, hX, hY, hZ);

            assertTrue(fVector.isExact(bX, bY, bZ, hX, hY, hZ),
                    "FVector values should be equal");
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

            FVector fVector = mainFactory.getFVector(bX, bY, bZ, hX, hY, hZ);

            assertFalse(fVector.isExact(0, 0, 0, 0, 0, 0),
                    "FVector values should not be equal");
        }

        @Test
        @DisplayName("Is exact with parameters (validate)")
        void isExactWithParametersValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.isExact(0, 0, 0, 0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Is similar")
        void isSimilar() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = mainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = mainFactory.getFVector(fPointBase.addX(Config.getJitter() * 0.5), fPointHead);

            assertTrue(fVectorA.isSimilar(fVectorB), "FVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (zero)")
        void isSimilarZero() {
            FVector fVectorA = mainFactory.getFVector();
            FVector fVectorB = mainFactory.getFVector();

            assertTrue(fVectorA.isSimilar(fVectorB), "FVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (fail)")
        void isSimilarFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = mainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = mainFactory.getFVector(fPointBase.addX(Config.getJitter() * 1.5), fPointHead);

            assertFalse(fVectorA.isSimilar(fVectorB), "FVectors should not be similar");
        }

        @Test
        @DisplayName("Is similar (validate)")
        void isSimilarValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isSimilar, fVectorA, fVectorB);
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

            FVector fVector = mainFactory.getFVector(
                    bX + (0.5 * Config.getJitter()), bY + (0.5 * Config.getJitter()), bZ + (0.5 * Config.getJitter()),
                    hX + (0.5 * Config.getJitter()), hY + (0.5 * Config.getJitter()), hZ + (0.5 * Config.getJitter()));

            assertTrue(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "FVector values should be equal");
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

            FVector fVector = mainFactory.getFVector(
                    bX + (1.5 * Config.getJitter()), bY + (1.5 * Config.getJitter()), bZ + (1.5 * Config.getJitter()),
                    hX + (1.5 * Config.getJitter()), hY + (1.5 * Config.getJitter()), hZ + (1.5 * Config.getJitter()));

            assertFalse(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "FVector values should not be equal");
        }

        @Test
        @DisplayName("Is similar with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.isSimilar(0, 0, 0, 0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = mainFactory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = mainFactory.getFVector(fPointBase, fPointHead);

            assertEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two identical FVectors should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FPoint fPointBase = RandomHelper.getTestPoint();
            FPoint fPointHead = RandomHelper.getTestPoint();

            FVector fVectorA = mainFactory.getFVector(mainFactory.getFPoint(), fPointHead);
            FVector fVectorB = mainFactory.getFVector(fPointBase, mainFactory.getFPoint());

            assertNotEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two different FVectors should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::hashCode, fVector);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = fVectorA.copy();

            assertAll("Validate similarity",
                    () -> assertNotSame(fVectorA, fVectorB,
                            "FVectors represent different objects"),
                    () -> assertEquals(fVectorA, fVectorB,
                            "FVectors should have the same values"),
                    () -> assertNotSame(fVectorA.getBase(), fVectorB.getBase(),
                            "The base FPoints should be different"),
                    () -> assertNotSame(fVectorA.getHead(), fVectorB.getHead(),
                            "The head FPoints should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::copy, fVector);
        }

    }

    @Nested
    @Tag("Algebra")
    @DisplayName("Base algebra")
    class IBaseAlgebra {

        @Test
        @DisplayName("Add FPoint")
        void addFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.add(fPoint);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add FPoint (validate)")
        void addFPointValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::add, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.add(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.add(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.add(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().add(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().add(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.add(0), fVector);
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.addX(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addX(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addX(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add X (validate)")
        void addXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.addX(0), fVector);
        }

        @Test
        @DisplayName("Add Y")
        void addY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.addY(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addY(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addY(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.addY(0), fVector);
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.addZ(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().addZ(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().addZ(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.addZ(0), fVector);
        }

        @Test
        @DisplayName("Sub FPoint")
        void subFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.sub(fPoint);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub FPoint (validate)")
        void subFPointValidate() {
            FVector fVectorA = RandomHelper.getTestVector();
            FVector fVectorB = RandomHelper.getTestVector(fVectorA);

            FVectorTestHelper.testReference(FVector::sub, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.sub(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.sub(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().sub(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().sub(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.sub(0), fVector);
        }

        @Test
        @DisplayName("Sub X")
        void subX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.subX(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subX(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subX(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.subX(0), fVector);
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.subY(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subY(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subY(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.subY(0), fVector);
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.subZ(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().subZ(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().subZ(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.subZ(0), fVector);
        }

        @Test
        @DisplayName("Mul FPoint")
        void mulFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.mul(fPoint);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul FPoint (validate)")
        void mulFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testReference(FVector::mul, fVector, fPoint);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.mul(1, 1, 1), fVector);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mul(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mul(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mul(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.mul(1), fVector);
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mulX(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulX(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulX(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.mulX(1), fVector);
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mulY(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulY(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulY(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.mulY(1), fVector);
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.mulZ(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().mulZ(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().mulZ(value),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(e -> e.mulZ(1), fVector);
        }

        @Test
        @DisplayName("Div FPoint")
        void divFPoint() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.div(fPoint);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().div(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().div(fPoint),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div FPoint (throw ArithmeticException)")
        void divFPointThrowArithmeticException() {
            FVector fVector = RandomHelper.getTestVector();

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(mainFactory.getFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(mainFactory.getFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fVector.div(mainFactory.getFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div FPoint (validate)")
        void divFPointValidate() {
            FVector fVector = RandomHelper.getTestVector();
            FPoint fPoint = RandomHelper.getTestPoint();

            FVectorTestHelper.testReference(FVector::div, fVector, fPoint);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = RandomHelper.getTestPoint();

            fVector.div(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().div(fPoint),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().div(fPoint),
                            "The head FPoint is erroneous")
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

            FVectorTestHelper.testValue(e -> e.div(1, 1, 1), fVector);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.div(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().div(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().div(value),
                            "The head FPoint is erroneous")
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

            FVectorTestHelper.testValue(e -> e.div(1), fVector);
        }

        @Test
        @DisplayName("Div X")
        void divX() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();

            double value = RandomHelper.getTestValue();

            fVector.divX(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().divX(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().divX(value),
                            "The head FPoint is erroneous")
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

            FVectorTestHelper.testValue(e -> e.divX(1), fVector);
        }

        @Test
        @DisplayName("Div Y")
        void divY() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.divY(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().divY(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().divY(value),
                            "The head FPoint is erroneous")
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

            FVectorTestHelper.testValue(e -> e.divY(1), fVector);
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {
            FVector fVector = RandomHelper.getTestVector();
            FVector fVectorRef = fVector.copy();
            double value = RandomHelper.getTestValue();

            fVector.divZ(value);

            assertAll("Validate FPoints",
                    () -> assertEquals(fVector.getBase(), fVectorRef.getBase().copy().divZ(value),
                            "The base FPoint is erroneous"),
                    () -> assertEquals(fVector.getHead(), fVectorRef.getHead().copy().divZ(value),
                            "The head FPoint is erroneous")
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

            FVectorTestHelper.testValue(e -> e.divZ(1), fVector);
        }

        @Test
        @DisplayName("Get FPoint list")
        void getFPoints() {
            FVector fVector = RandomHelper.getTestVector();

            List<FPoint> list = fVector.disassemble();

            assertAll("Validate FPoint list",
                    () -> assertEquals(2, list.size(), "The size of the list is incorrect"),
                    () -> assertSame(fVector.getBase(), list.get(0), "The base FPoint is incorrect"),
                    () -> assertSame(fVector.getHead(), list.get(1), "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint list (validate)")
        void getFPointsValidate() {
            FVector fVector = RandomHelper.getTestVector();

            FVectorTestHelper.testValue(FVector::disassemble, fVector);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FVector fVectorRef = RandomHelper.getTestVector();
            FVector fVector = mainFactory.getFVector();

            fVectorRef.imprint(fVector);

            assertAll("Validate FPoint values",
                    () -> assertEquals(fVector.getBase(), fVector.getBase(), "The base FPoint is incorrect"),
                    () -> assertEquals(fVector.getHead(), fVector.getHead(), "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FVector fVectorRef = RandomHelper.getTestVector();
            FVector fVector = RandomHelper.getTestVector();

            fVectorRef.imprint(fVector);

            assertAll("Validate FPoint references",
                    () -> assertNotSame(fVectorRef, fVector,
                            "FVectors should point to different objects"),
                    () -> assertNotSame(fVectorRef.getBase(), fVector.getBase(),
                            "The base FPoint reference is incorrect"),
                    () -> assertNotSame(fVectorRef.getHead(), fVector.getHead(),
                            "The head FPoint reference is incorrect")
            );
        }

    }
}

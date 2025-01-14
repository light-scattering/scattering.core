package eu.scattering.core.test.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.geometry.primitive.support.FVectorTestHelper;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVector")
public class FVectorTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FVectorBasicTest {

        @Test
        @DisplayName("Constructor")
        void construct() {
            FVector fVector = factory.getFVector();

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Constructor (validate reference)")
        void constructValidateReference() {
            FVector fVector = factory.getFVector();

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertNotSame(fVector.getRefBase(), fVector.getRefHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with parameters")
        void constructWithParameters() {
            double valAX = random.nextDouble();
            double valAY = random.nextDouble();
            double valAZ = random.nextDouble();
            double valBX = random.nextDouble();
            double valBY = random.nextDouble();
            double valBZ = random.nextDouble();

            FVector fVector = factory.getFVector(valAX, valAY, valAZ, valBX, valBY, valBZ);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(valAX, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(valAY, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(valAZ, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(valBX, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(valBY, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(valBZ, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with head parameters")
        void constructWithHeadParameters() {
            double valX = random.nextDouble();
            double valY = random.nextDouble();
            double valZ = random.nextDouble();

            FVector fVector = factory.getFVector(valX, valY, valZ);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(valX, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(valY, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(valZ, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint base/head")
        void constructWithBaseHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(fPointBase.getX(), fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint base/head (validate reference)")
        void constructWithBaseHeadValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertNotSame(fPointBase, fVector.getRefBase(),
                            "The base FPoint reference is erroneous"),
                    () -> assertNotSame(fPointHead, fVector.getRefHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getRefBase(), fVector.getRefHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with FPoint base/head (validate reference change)")
        void constructWithBaseHeadValidateReferenceChange() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fPointBase.set(0, 0, 0);
            fPointHead.set(0, 0, 0);

            Assertions.assertAll("Validate FVector values",
                    () -> assertNotEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with reference FPoint base/head")
        void constructWithRefBaseHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(fPointBase.getX(), fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with reference FPoint base/head (validate reference)")
        void constructWithRefBaseHeadValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertSame(fPointBase, fVector.getRefBase(),
                            "The base FPoint reference is erroneous"),
                    () -> assertSame(fPointHead, fVector.getRefHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getRefBase(), fVector.getRefHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with reference FPoint base/head (validate reference change)")
        void constructWithRefBaseHeadValidateReferenceChange() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fPointBase.set(0, 0, 0);
            fPointHead.set(0, 0, 0);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint head")
        void constructWithHead() {
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointHead);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint head (validate reference)")
        void constructWithHeadValidateReference() {
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointHead);

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertNotSame(fPointHead, fVector.getRefHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getRefBase(), fVector.getRefHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with FPoint head (validate reference change)")
        void constructWithHeadValidateReferenceChange() {
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointHead);

            fPointHead.set(0, 0, 0);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertNotEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with reference FPoint head")
        void constructWithRefHead() {
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointHead);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with reference FPoint head (validate reference)")
        void constructWithRefHeadValidateReference() {
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointHead);

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotNull(fVector,
                            "The generated FVector instance is null"),
                    () -> assertSame(fPointHead, fVector.getRefHead(),
                            "The head FPoint reference is erroneous"),
                    () -> assertNotSame(fVector.getRefBase(), fVector.getRefHead(),
                            "FPoints should have different references")
            );
        }

        @Test
        @DisplayName("Construct with reference FPoint head (validate reference change)")
        void constructWithRefHeadValidateReferenceChange() {
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointHead);

            fPointHead.set(0, 0, 0);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Get base")
        void getBase() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            assertNotSame(fVector.getRefBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base")
        void setBase() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            FVector fVectorRef = fVector.setBase(0, 0, 0);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set base (validate reference)")
        void setBaseValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.setBase(0, 0, 0);

            assertNotSame(fVector.getRefBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set base (throw NullPointerException)")
        void setBaseThrowNullPointerException() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            Assertions.assertThrows(NullPointerException.class, () -> fVector.setBase((FPoint) null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Get reference base")
        void getRefBase() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            assertSame(fVector.getRefBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set reference base")
        void setRefBase() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            FVector fVectorRef = fVector.setRefBase(factory.getFPoint());

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointHead.getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fPointHead.getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointHead.getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference base (validate reference)")
        void setRefBaseValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setRefBase(factory.getFPoint());

            assertNotSame(fVector.getRefBase(), fPointBase, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set reference base (throw NullPointerException)")
        void setRefBaseThrowNullPointerException() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            Assertions.assertThrows(NullPointerException.class, () -> fVector.setRefBase(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Get head")
        void getHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            assertNotSame(fVector.getRefHead(), fPointHead, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head")
        void setHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            FVector fVectorRef = fVector.setHead(factory.getFPoint());

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(fPointBase.getX(), fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set head (validate reference)")
        void setHeadValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.setHead(0, 0, 0);

            assertNotSame(fVector.getRefHead(), fPointHead, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set head (throw NullPointerException)")
        void setHeadThrowNullPointerException() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            Assertions.assertThrows(NullPointerException.class, () -> fVector.setHead((FPoint) null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Get reference head")
        void getRefHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            assertSame(fVector.getRefHead(), fPointHead, "The FPoint reference is erroneous");
        }

        @Test
        @DisplayName("Set reference head")
        void setRefHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setRefHead(factory.getFPoint());

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(fPointBase.getX(), fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fPointBase.getY(), fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fPointBase.getZ(), fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference head (validate reference)")
        void setRefHeadValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setRefHead(factory.getFPoint());

            assertNotSame(fVector.getRefHead(), fPointHead, "The FPoint reference is incorrect");
        }

        @Test
        @DisplayName("Set reference head (throw NullPointerException)")
        void setRefHeadThrowNullPointerException() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            Assertions.assertThrows(NullPointerException.class, () -> fVector.setRefHead(null),
                    "The reference must not be null");
        }

        @Test
        @DisplayName("Set FPoints")
        void setBaseHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.set(factory.getFPoint(), factory.getFPoint());

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set FPoints (validate reference)")
        void setBaseHeadValidateReference() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.set(factory.getFPoint(), factory.getFPoint());

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotSame(fPointBase, fVector.getRefBase(), "The base FPoint is incorrect"),
                    () -> assertNotSame(fPointHead, fVector.getRefHead(), "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Set FPoints (throw NullPointerException)")
        void setBaseHeadThrowNullPointerException() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            Assertions.assertAll("Validate NullPointerExceptions",
                    () -> Assertions.assertThrows(NullPointerException.class, () -> fVector.set(null, fPointHead),
                            "The base FPoint must not be null"),
                    () -> Assertions.assertThrows(NullPointerException.class, () -> fVector.set(fPointBase, null),
                            "The head FPoint must not be null"),
                    () -> Assertions.assertThrows(NullPointerException.class, () -> fVector.set(null, null),
                            "The reference FPoints must not be null")
            );
        }

        @Test
        @DisplayName("Set reference FPoints")
        void setRefBaseHead() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.setRef(factory.getFPoint(), factory.getFPoint());

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference FPoints (validate references)")
        void setRefBaseHeadValidateReferences() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.setRef(factory.getFPoint(), factory.getFPoint());

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotSame(fVector.getRefBase(), fPointBase, "The base FPoint is incorrect"),
                    () -> assertNotSame(fVector.getRefHead(), fPointHead, "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Set reference FPoints (throw NullPointerException)")
        void setRefBaseHeadThrowNullPointerException() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            Assertions.assertAll("Validate NullPointerExceptions",
                    () -> Assertions.assertThrows(NullPointerException.class, () -> fVector.setRef(null, fPointHead),
                            "The base FPoint must not be null"),
                    () -> Assertions.assertThrows(NullPointerException.class, () -> fVector.setRef(fPointBase, null),
                            "The head FPoint must not be null"),
                    () -> Assertions.assertThrows(NullPointerException.class, () -> fVector.setRef(null, null),
                            "The reference FPoints must not be null")
            );
        }

        @Test
        @DisplayName("Set FVector")
        void setFVector() {
            FVector fVectorRef = factory.getFVector(TestHelper.getRandomFPoint(), TestHelper.getRandomFPoint());
            FVector fVector = factory.getFVector();

            fVector.applyStateFrom(fVectorRef);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(fVectorRef.getRefBase().getX(), fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVectorRef.getRefBase().getY(), fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVectorRef.getRefBase().getZ(), fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fVectorRef.getRefHead().getX(), fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(fVectorRef.getRefHead().getY(), fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(fVectorRef.getRefHead().getZ(), fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set FVector (validate references)")
        void setFVectorValidateReferences() {
            FVector fVectorRef = factory.getFVector(TestHelper.getRandomFPoint(), TestHelper.getRandomFPoint());
            FVector fVector = factory.getFVector();

            fVector.applyStateFrom(fVectorRef);

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotSame(fVectorRef.getRefBase(), fVector.getRefBase(),
                            "The base FPoint is incorrect"),
                    () -> assertNotSame(fVectorRef.getRefHead(), fVector.getRefHead(),
                            "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Access base X")
        void accessBaseX() {
            FVector fVector = factory.getFVector();

            FVector fVectorRef = fVector.setBaseX(1);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Access base Y")
        void accessBaseY() {
            FVector fVector = factory.getFVector();

            FVector fVectorRef = fVector.setBaseY(1);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Access base Z")
        void accessBaseZ() {
            FVector fVector = factory.getFVector();

            FVector fVectorRef = fVector.setBaseZ(1);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Access head X")
        void accessHeadX() {
            FVector fVector = factory.getFVector();

            FVector fVectorRef = fVector.setHeadX(1);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Access head Y")
        void accessHeadY() {
            FVector fVector = factory.getFVector();

            FVector fVectorRef = fVector.setHeadY(1);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Access head Z")
        void accessHeadZ() {
            FVector fVector = factory.getFVector();

            FVector fVectorRef = fVector.setHeadZ(1);

            Assertions.assertSame(fVector, fVectorRef, "The FVector reference should not change");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPairPos3D")
        void constructWithFPairPos3D() {
            FPairPos3D fPairPos3D = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            FVector fVector = factory.getFVector(fPairPos3D);

            assertNotNull(fVector, "The instance is null");

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(2, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(5, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fVector.getRefHead().getZ(),
                            "Head - The Y value is incorrect")
            );
        }

        @Test
        @DisplayName("Export to FPairPos3D")
        void toPairPos3D() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
            FPairPos3D fPairPos3D = fVector.toFPairPos3D();

            Assertions.assertAll("Validate FVector values",
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
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FVectorAdvancedTest {

        @Test
        @DisplayName("Set spherical coordinates")
        void setSphericalCoordinates() {
            FPoint fPointBase = factory.getFPoint(1, 1, 0);
            FPoint fPointHead = factory.getFPoint(2, 1, 0);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setSphericalCoordinates(Math.PI * 0.5, Math.PI * 0.5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set spherical coordinates (validate)")
        void setSphericalCoordinatesValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            FVectorTestHelper.testReference(e -> e.setSphericalCoordinates(0, 0), fVector);
        }

        @Test
        @DisplayName("Move base")
        void moveBase() {
            FPoint fPointBase = factory.getFPoint(0, 0, 0);
            FPoint fPointHead = factory.getFPoint(1, 2, 3);

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            FPoint fPointOffset = factory.getFPoint(4, 5, 6);

            fVector.moveBase(fPointOffset);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(4, fVector.getRefBase().getX(), jitter,
                            "Base - The X value is incorrect"),
                    () -> assertEquals(5, fVector.getRefBase().getY(), jitter,
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(6, fVector.getRefBase().getZ(), jitter,
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(5, fVector.getRefHead().getX(), jitter,
                            "Head - The X value is incorrect"),
                    () -> assertEquals(7, fVector.getRefHead().getY(), jitter,
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(9, fVector.getRefHead().getZ(), jitter,
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move base (validate)")
        void moveBaseValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);
            FPoint fPointOffset = factory.getFPoint(4, 5, 6);

            FVectorTestHelper.testReference(FVector::moveBase, fVector, fPointOffset);
        }

        @Test
        @DisplayName("Move base with parameters")
        void moveBaseWithParameters() {
            FPoint fPointBase = factory.getFPoint(3, -2, -3);
            FPoint fPointHead = factory.getFPoint(2, 4, 6);

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.moveBase(-1, 3, -2);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(-1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(9, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(7, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move base with parameters (validate)")
        void moveBaseWithParametersValidate() {
            FVector fVector = factory.getFVector(0, 0, 0, 1, 2, 3);

            FVectorTestHelper.testReference(a -> a.moveBase(4, 5, 6), fVector);
        }

        @Test
        @DisplayName("Move base to OX")
        void moveBaseToOX() {
            FPoint fPointBase = factory.getFPoint(1, 2, 3);
            FPoint fPointHead = factory.getFPoint(4, 5, 6);

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.moveBaseToCenter();

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(3, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move base to OX (validate)")
        void moveBaseToOXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            FVectorTestHelper.testReference(FVector::moveBaseToCenter, fVector);
        }

        @Test
        @DisplayName("Move head")
        void moveHead() {
            FPoint fPointBase = factory.getFPoint(0, 0, 0);
            FPoint fPointHead = factory.getFPoint(1, 2, 3);

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            FPoint fPointOffset = factory.getFPoint(4, 5, 6);

            fVector.moveHead(fPointOffset);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(3, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(5, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(6, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move head (validate)")
        void moveHeadValidate() {
            FVector fVector = factory.getFVector(0, 0, 0, 1, 2, 3);
            FPoint fPointOffset = factory.getFPoint(4, 5, 6);

            FVectorTestHelper.testReference(FVector::moveHead, fVector, fPointOffset);
        }

        @Test
        @DisplayName("Move head with parameters")
        void moveHeadWithParameters() {
            FPoint fPointBase = factory.getFPoint(3, -2, -3);
            FPoint fPointHead = factory.getFPoint(2, 4, 6);

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.moveHead(-1, 3, -2);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(-3, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(-11, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(-1, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move head with parameters (validate)")
        void moveHeadWithParametersValidate() {
            FVector fVector = factory.getFVector(0, 0, 0, 1, 2, 3);

            FVectorTestHelper.testReference(a -> a.moveHead(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Move head to OX")
        void moveHeadToOX() {
            FPoint fPointBase = factory.getFPoint(1, 2, 3);
            FPoint fPointHead = factory.getFPoint(4, 5, 6);

            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.moveHeadToCenter();

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(-3, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(-3, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(-3, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move head to OX (validate)")
        void moveHeadToOXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            FVectorTestHelper.testReference(FVector::moveHeadToCenter, fVector);
        }

        @Test
        @DisplayName("Move forward - X")
        void moveForwardX() {
            FVector fVector = factory.getFVector(3, 0, 0);

            fVector.shiftForward(5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(5, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(8, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move forward - Y")
        void moveForwardY() {
            FVector fVector = factory.getFVector(0, 3, 0);

            fVector.shiftForward(5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(5, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(8, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move forward - Z")
        void moveForwardZ() {
            FVector fVector = factory.getFVector(0, 0, 3);

            fVector.shiftForward(5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(5, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(8, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move forward")
        void moveForward() {
            FVector fVector = factory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.shiftForward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(factory.getFVector(2, 2, 2, 3, 3, 3)),
                    "The FVector values are erroneous");
        }

        @Test
        @DisplayName("Move forward (opposite direction)")
        void moveForwardOppositeDirection() {
            FVector fVectorA = factory.getFVector(1, 2, 3, 4, 5, 6);
            FVector fVectorB = fVectorA.copy();

            fVectorA.shiftForward(-5);
            fVectorB.shiftBackward(5);

            assertTrue(fVectorA.isSimilar(fVectorB),
                    "The two vector operations should have the same effect");
        }

        @Test
        @DisplayName("Move forward (throw IllegalStateException)")
        void moveForwardThrowIllegalStateException() {
            FVector fVector = factory.getFVector(0, 0, 0, 0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVector.shiftForward(1),
                    "The direction of the FVector cannot be determined");
        }

        @Test
        @DisplayName("Move forward (validate)")
        void moveForwardValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            FVectorTestHelper.testReference(a -> a.shiftForward(1), fVector);
        }

        @Test
        @DisplayName("Move backward - X")
        void moveBackwardX() {
            FVector fVector = factory.getFVector(3, 0, 0);

            fVector.shiftBackward(5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(-5, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move backward - Y")
        void moveBackwardY() {
            FVector fVector = factory.getFVector(0, 3, 0);

            fVector.shiftBackward(5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(-5, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move backward - Z")
        void moveBackwardZ() {
            FVector fVector = factory.getFVector(0, 0, 3);

            fVector.shiftBackward(5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(0, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(-5, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Move backward")
        void moveBackward() {
            FVector fVector = factory.getFVector(1, 1, 1, 2, 2, 2);

            fVector.shiftBackward(Math.sqrt(3));

            assertTrue(fVector.isSimilar(factory.getFVector(0, 0, 0, 1, 1, 1)),
                    "The FVector values are erroneous");
        }

        @Test
        @DisplayName("Move backward (opposite direction)")
        void moveBackwardOppositeDirection() {
            FVector fVectorA = factory.getFVector(1, 2, 3, 4, 5, 6);
            FVector fVectorB = fVectorA.copy();

            fVectorA.shiftBackward(-5);
            fVectorB.shiftForward(5);

            assertTrue(fVectorA.isSimilar(fVectorB),
                    "The two vector operations should have the same effect");
        }

        @Test
        @DisplayName("Move backward (throw IllegalStateException)")
        void moveBackwardThrowIllegalStateException() {
            FVector fVector = factory.getFVector(0, 0, 0, 0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVector.shiftBackward(1),
                    "The direction of the FVector cannot be determined");
        }

        @Test
        @DisplayName("Move backward (validate)")
        void moveBackwardValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            FVectorTestHelper.testReference(a -> a.shiftBackward(1), fVector);
        }

        @Test
        @DisplayName("Add FVector")
        void addFVector() {
            FVector fVectorA = factory.getFVector(-1, 1, -2, 2, -5, 1);
            FVector fVectorB = factory.getFVector(6, -2, 4, -3, -2, 7);

            fVectorA.add(fVectorB);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(-1, fVectorA.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVectorA.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(-2, fVectorA.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(-7, fVectorA.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(-5, fVectorA.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(4, fVectorA.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Add FVector (validate)")
        void addFVectorValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testReference(FVector::add, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Sub FVector")
        void subFVector() {
            FVector fVectorA = factory.getFVector(1, 5, -4, 2, -5, 1);
            FVector fVectorB = factory.getFVector(-6, 2, 1, -3, 2, 6);

            FPoint fPointRef = fVectorA.getRefHead().copy().sub(fVectorB.getRefHead().copy().sub(fVectorB.getRefBase()));

            fVectorA.sub(fVectorB);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(1, fVectorA.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(fVectorA.getRefBase().getY(), fVectorA.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(fVectorA.getRefBase().getZ(), fVectorA.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(fPointRef.getX(), fVectorA.getRefHead().getX(),
                            jitter, "Head - The X value is incorrect"),
                    () -> assertEquals(fPointRef.getY(), fVectorA.getRefHead().getY(),
                            jitter, "Head - The Y value is incorrect"),
                    () -> assertEquals(fPointRef.getZ(), fVectorA.getRefHead().getZ(),
                            jitter, "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FVector (validate)")
        void subFVectorValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testReference(FVector::sub, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get length X")
        void getLengthX() {
            FVector fVector = factory.getFVector(1, -2, 3, -4, 5, 6);

            assertEquals(5, fVector.getLengthX()," The X length is incorrect");
        }

        @Test
        @DisplayName("Get length X (validate)")
        void getLengthXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getLengthX, fVector);
        }

        @Test
        @DisplayName("Get length Y")
        void getLengthY() {
            FVector fVector = factory.getFVector(1, -2, 3, -4, 5, 6);

            assertEquals(7, fVector.getLengthY()," The Y length is incorrect");
        }

        @Test
        @DisplayName("Get length Y (validate)")
        void getLengthYValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getLengthY, fVector);
        }

        @Test
        @DisplayName("Get length Z")
        void getLengthZ() {
            FVector fVector = factory.getFVector(1, -2, 3, -4, 5, 6);

            assertEquals(3, fVector.getLengthZ()," The Z length is incorrect");
        }

        @Test
        @DisplayName("Get length Z (validate)")
        void getLengthZValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getLengthZ, fVector);
        }

        @Test
        @DisplayName("Get length")
        void getLength() {
            FPoint fPointBase = factory.getFPoint(1, 1, 1);
            FPoint fPointHead = factory.getFPoint(2, 2, 2);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            assertEquals(Math.sqrt(3), fVector.getLength(), jitter, "The FVector length is erroneous");
        }

        @Test
        @DisplayName("Get length (zero)")
        void getLengthZero() {
            FPoint fPointBase = factory.getFPoint();
            FPoint fPointHead = factory.getFPoint();
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            assertEquals(0, fVector.getLength(), jitter, "The FVector length should be zero");
        }

        @Test
        @DisplayName("Get length (random)")
        void getLengthRandom() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            double dimX = fVector.getLengthX() * fVector.getLengthX();
            double dimY = fVector.getLengthY() * fVector.getLengthY();
            double dimZ = fVector.getLengthZ() * fVector.getLengthZ();
            double radius = Math.sqrt(dimX + dimY + dimZ);

            assertEquals(radius, fVector.getLength(), jitter, "The FVector length is erroneous");
        }

        @Test
        @DisplayName("Get length (validate)")
        void getLengthValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getLength, fVector);
        }

        @Test
        @DisplayName("Get length P2")
        void getLengthP2() {
            FPoint fPointBase = factory.getFPoint(1, 1, 1);
            FPoint fPointHead = factory.getFPoint(2, 2, 2);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            assertEquals(3, fVector.getLengthP2(), jitter, "The FVector P2 length is erroneous");
        }

        @Test
        @DisplayName("Get length P2 (validate)")
        void getLengthP2Validate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getLengthP2, fVector);
        }

        @Test
        @DisplayName("Set length")
        void setLength() {
            FPoint fPointBase = factory.getFPoint(3, 3, 3);
            FPoint fPointHead = factory.getFPoint(5, 5, 5);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setLength(Math.sqrt(3));

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(3, fVector.getRefBase().getX(), jitter,
                            "Base - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getY(), jitter,
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getZ(), jitter,
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(4, fVector.getRefHead().getX(), jitter,
                            "Head - The X value is incorrect"),
                    () -> assertEquals(4, fVector.getRefHead().getY(), jitter,
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(4, fVector.getRefHead().getZ(), jitter,
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set length (opposite direction)")
        void setLengthOppositeDirection() {
            FVector fVector = factory.getFVector(1, 1, 1);

            fVector.setLength(-2 * Math.sqrt(3));

            assertTrue(fVector.isSimilar(0, 0, 0, -2, -2, -2),
                    "The resulting FVector position is incorrect");
        }

        @Test
        @DisplayName("Set length (random)")
        void setLengthRandom() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setLength(5);

            assertEquals(5, fVector.getLength(), jitter, "The FVector length is erroneous");
        }

        @Test
        @DisplayName("Set length (throw IllegalStateException)")
        void setLengthThrowIllegalStateException() {
            FVector fVector = factory.getFVector();

            Assertions.assertThrows(IllegalStateException.class, () -> fVector.setLength(1),
                    "The direction of the FVector is not defined");
        }

        @Test
        @DisplayName("Set length (validate)")
        void setLengthValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(a -> a.setLength(1), fVector);
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.normalize();

            assertEquals(1, fVector.getLength(), jitter, "The FVector length is incorrect");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {
            FVector fVector = factory.getFVector();

            assertThrows(IllegalStateException.class, fVector::normalize,
                    "The direction of the FVector is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(FVector::normalize, fVector);
        }

        @Test
        @DisplayName("Reflect head")
        void reflectHead() {
            FPoint fPointBase = factory.getFPoint(-1, 6, 3);
            FPoint fPointHead = factory.getFPoint(6, 3, -2);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.reflectHead();

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(-1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(6, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(-8, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(9, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(8, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect head (validate)")
        void reflectHeadValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(FVector::reflectHead, fVector);
        }

        @Test
        @DisplayName("Reflect base")
        void reflectBase() {
            FPoint fPointBase = factory.getFPoint(-1, 6, 3);
            FPoint fPointHead = factory.getFPoint(6, 3, -2);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.reflectBase();

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(13, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(-7, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(6, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(3, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(-2, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Reflect base (validate)")
        void reflectBaseValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(FVector::reflectBase, fVector);
        }

        @Test
        @DisplayName("Reflect")
        void reflect() {
            FVector fVector = factory.getFVector(1, 1, 0, 1, 3, 0);
            FPoint fPoint = factory.getFPoint(2, 2, 0);

            fVector.reflect(fPoint);

            assertTrue(fVector.isSimilar(3, 3, 0, 3, 1, 0),
                    "The FVector reflection is erroneous");
        }

        @Test
        @DisplayName("Reflect (validate)")
        void reflectValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);
            FPoint fPoint = factory.getFPoint(4, 5, 6);

            FVectorTestHelper.testReference(FVector::reflect, fVector, fPoint);
        }

        @Test
        @DisplayName("Invert direction")
        void invertDirection() {
            FPoint fPointBase = factory.getFPoint(1, 2, 3);
            FPoint fPointHead = factory.getFPoint(4, 5, 6);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.swapBaseWithHead();

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(4, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(5, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(6, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(2, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(3, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Invert direction (validate)")
        void invertDirectionValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(FVector::swapBaseWithHead, fVector);
        }

        @Test
        @DisplayName("Get inclination")
        void getInclination() {
            FPoint fPointBase = factory.getFPoint(1, 1, 0);
            FPoint fPointHead = factory.getFPoint(2, 2, 0);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getInclination(), jitter,
                    "The FVector inclination is incorrect");
        }

        @Test
        @DisplayName("Get inclination (validate)")
        void getInclinationValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getInclination, fVector);
        }

        @Test
        @DisplayName("Set inclination")
        void setInclination() {
            FPoint fPointBase = factory.getFPoint(1, 1, 0);
            FPoint fPointHead = factory.getFPoint(1, 2, 0);
            FVector fVector = factory.getRefFVector(fPointBase, fPointHead);

            fVector.setInclination(Math.PI * 0.5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(2, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set inclination (validate)")
        void setInclinationValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(a -> a.setInclination(Math.PI * 0.5), fVector);
        }

        @Test
        @DisplayName("Get azimuth")
        void getAzimuth() {
            FPoint fPointBase = factory.getFPoint(1, 1, 0);
            FPoint fPointHead = factory.getFPoint(2, 1, 1);
            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            assertEquals(Math.PI * 0.25, fVector.getAzimuth(), jitter,
                    "The FVector azimuth is incorrect");
        }

        @Test
        @DisplayName("Get azimuth (validate)")
        void getAzimuthValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::getAzimuth, fVector);
        }

        @Test
        @DisplayName("Set azimuth")
        void setAzimuth() {
            FPoint fPointBase = factory.getFPoint(1, 1, 0);
            FPoint fPointHead = factory.getFPoint(2, 1, 0);
            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            fVector.setAzimuth(Math.PI * 0.5);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(1, fVector.getRefBase().getX(),
                            "Base - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefBase().getY(),
                            "Base - The Y value is incorrect"),
                    () -> assertEquals(0, fVector.getRefBase().getZ(),
                            "Base - The Z value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getX(),
                            "Head - The X value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getY(),
                            "Head - The Y value is incorrect"),
                    () -> assertEquals(1, fVector.getRefHead().getZ(),
                            "Head - The Z value is incorrect")
            );
        }

        @Test
        @DisplayName("Set azimuth (validate)")
        void setAzimuthValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testReference(a -> a.setAzimuth(Math.PI * 0.5), fVector);
        }

        @Test
        @DisplayName("Get angle")
        void getAngle() {
            FPoint fPointBaseA = factory.getFPoint();
            FPoint fPointHeadA = factory.getFPoint(2, 2, 0);
            FVector fVectorA = factory.getFVector(fPointBaseA, fPointHeadA);

            FPoint fPointBaseB = factory.getFPoint();
            FPoint fPointHeadB = factory.getFPoint(4, -4, 0);
            FVector fVectorB = factory.getFVector(fPointBaseB, fPointHeadB);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            Assertions.assertAll("Validate results",
                    () -> assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB),
                            jitter, "The angle is incorrect"),
                    () -> assertEquals(Math.PI * 0.5, fVectorB.getAngle(fVectorA),
                            jitter, "The angle is incorrect")
            );
        }

        @Test
        @DisplayName("Get angle (parallel)")
        void getAngleParallel() {
            FVector fVectorA = factory.getFVector(factory.getFPoint(2, 2, 2));
            FVector fVectorB = factory.getFVector(factory.getFPoint(4, 4, 4));

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB), jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (antiparallel)")
        void getAngleAntiparallel() {
            FVector fVectorA = factory.getFVector(factory.getFPoint(2, 2, 2));
            FVector fVectorB = factory.getFVector(factory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertEquals(0, fVectorA.getAngle(fVectorB), jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (orthogonal)")
        void getAngleOrthogonal() {
            FVector fVectorA = factory.getFVector(factory.getFPoint(0, 1, 0));
            FVector fVectorB = factory.getFVector(TestHelper.getRandomFPoint().setY(0));

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB), jitter, "The angle is incorrect");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, input)")
        void getAngleThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector();
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.getAngle(fVectorB),
                    "The direction of the input FVector is not defined");
        }

        @Test
        @DisplayName("Get angle (throw IllegalStateException, argument)")
        void getAngleThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector();

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.getAngle(fVectorB),
                    "The direction of the FVector is not defined");
        }

        @Test
        @DisplayName("Get angle (validate)")
        void getAngleValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testValue(FVector::getAngle, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Get dot product")
        void getDotProduct() {
            FPoint fPointBaseA = TestHelper.getRandomFPoint();
            FPoint fPointHeadA = TestHelper.getRandomFPoint();
            FVector fVectorA = factory.getRefFVector(fPointBaseA, fPointHeadA);

            FPoint fPointBaseB = TestHelper.getRandomFPoint();
            FPoint fPointHeadB = TestHelper.getRandomFPoint();
            FVector fVectorB = factory.getRefFVector(fPointBaseB, fPointHeadB);

            double result = fVectorA.getDotProduct(fVectorB);

            fVectorA.moveBase(factory.getFPoint());
            fVectorB.moveBase(factory.getFPoint());

            FPoint fPointA = fVectorA.getRefHead();
            FPoint fPointB = fVectorB.getRefHead();

            double dimX = fPointA.getX() * fPointB.getX();
            double dimY = fPointA.getY() * fPointB.getY();
            double dimZ = fPointA.getZ() * fPointB.getZ();

            Assertions.assertEquals(dimX + dimY + dimZ, result, jitter, "The value is erroneous");
        }

        @Test
        @DisplayName("Get dot product (simple)")
        void getDotProductSimple() {
            FVector fVectorA = factory.getFVector(0, 0, 0, 1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0, 4, 5, 6);

            assertEquals(32, fVectorA.getDotProduct(fVectorB),
                    "The resulting FVector is erroneous");
        }

        @Test
        @DisplayName("Get dot product (validate)")
        void getDotProductValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testValue(FVector::getDotProduct, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set cross product")
        void setCrossProduct() {
            FVector fVectorA = TestHelper.getRandomFVector();
            FVector fVectorB = TestHelper.getRandomFVector();

            FVector fVectorRes = fVectorA.copy().setCrossProduct(fVectorB);

            FPoint fPointRel = fVectorA.getRefBase().copy();

            fVectorA.moveBaseToCenter();
            fVectorB.moveBaseToCenter();

            FPoint fPointA = fVectorA.getRefHead();
            FPoint fPointB = fVectorB.getRefHead();

            double dimX = (fPointA.getY() * fPointB.getZ()) - (fPointA.getZ() * fPointB.getY());
            double dimY = (fPointA.getZ() * fPointB.getX()) - (fPointA.getX() * fPointB.getZ());
            double dimZ = (fPointA.getX() * fPointB.getY()) - (fPointA.getY() * fPointB.getX());

            FVector fVectorRef = factory.getFVector(dimX, dimY, dimZ);

            fVectorRef.moveBase(fPointRel);

            assertTrue(fVectorRes.isSimilar(fVectorRef),"The resulting FVector is erroneous");
        }

        @Test
        @DisplayName("Set cross product (simple)")
        void setCrossProductSimple() {
            FVector fVectorA = factory.getFVector(1, 1, 1, 1, 1, 2);
            FVector fVectorB = factory.getFVector(-1, -1, -1, 0, -1, -1);

            fVectorA.setCrossProduct(fVectorB);

            assertTrue(fVectorA.isExact(factory.getFVector(1, 1, 1, 1, 2, 1)),
                    "The resulting FVector is erroneous");
        }

        @Test
        @DisplayName("Set cross product (validate)")
        void setCrossProductValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testReference(FVector::setCrossProduct, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is collinear A")
        void isCollinearA() {
            FVector fVectorA = factory.getFVector(2, 2, 2);
            FVector fVectorB = factory.getFVector(4, 4, 4);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isCollinear(fVectorB),
                    "The two FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear B")
        void isCollinearB() {
            FVector fVectorA = factory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = factory.getFVector(-1, 1, 0, 1, 1, 0);

            assertTrue(fVectorA.isCollinear(fVectorB),
                    "The two FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear A (opposite direction")
        void isCollinearOppositeDirectionA() {
            FVector fVectorA = factory.getFVector(factory.getFPoint(2, 2, 2));
            FVector fVectorB = factory.getFVector(factory.getFPoint(-4, -4, -4));

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isCollinear(fVectorB),
                    "The FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear B (opposite direction")
        void isCollinearOppositeDirectionB() {
            FVector fVectorA = factory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = factory.getFVector(1, 1, 0, -1, 1, 0);

            assertTrue(fVectorA.isCollinear(fVectorB),
                    "The two FVectors should be collinear");
        }

        @Test
        @DisplayName("Is collinear (fail)")
        void isCollinearFail() {
            FVector fVectorA = factory.getFVector(1, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 0);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertFalse(fVectorA.isCollinear(fVectorB),
                    "The FVectors should not be collinear");
        }

        @Test
        @DisplayName("Is collinear (throw IllegalStateException, input)")
        void isCollinearThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isCollinear(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is collinear (throw IllegalStateException, argument)")
        void isCollinearThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isCollinear(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is collinear (validate)")
        void isCollinearValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testValue(FVector::isCollinear, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is parallel A")
        void isParallelA() {
            FVector fVectorA = factory.getFVector(2, 2, 2);
            FVector fVectorB = factory.getFVector(4, 4, 4);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isParallel(fVectorB), "The two FVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel B")
        void isParallelB() {
            FVector fVectorA = factory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = factory.getFVector(-1, 1, 0, 1, 1, 0);

            assertTrue(fVectorA.isParallel(fVectorB), "The two FVectors should be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail)")
        void isParallelFail() {
            FVector fVectorA = factory.getFVector(1, 0, 0);
            FVector fVectorB = factory.getFVector(1, 1, 0);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The FVectors should not be parallel");
        }

        @Test
        @DisplayName("Is parallel (fail, opposite direction")
        void isParallelOppositeDirection() {
            FVector fVectorA = factory.getFVector(2, 2, 2);
            FVector fVectorB = factory.getFVector(-4, -4, -4);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The FVectors should not be parallel");
        }

        @Test
        @DisplayName("Is parallel (throw IllegalStateException, input)")
        void isParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is parallel (throw IllegalStateException, argument)")
        void isParallelThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is parallel (validate)")
        void isParallelValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testValue(FVector::isParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set parallel")
        void setParallel() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            fVectorA.setParallel(fVectorB);

            assertTrue(fVectorA.isParallel(fVectorB), "The two FVectors should be parallel");
        }

        @Test
        @DisplayName("Set parallel (throw IllegalStateException, input)")
        void setParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Set parallel (throw IllegalStateException, argument)")
        void setParallelThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Set parallel (validate)")
        void setParallelValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testReference(FVector::setParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is anti-parallel A")
        void isAntiParallelA() {
            FVector fVectorA = factory.getFVector(2, 2, 2);
            FVector fVectorB = factory.getFVector(-4, -4, -4);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two FVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel B")
        void isAntiParallelB() {
            FVector fVectorA = factory.getFVector(-1, 0, 0, 1, 0, 0);
            FVector fVectorB = factory.getFVector(1, 1, 0, -1, 1, 0);

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two FVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (fail)")
        void isAntiParallelFail() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertFalse(fVectorA.isParallel(fVectorB), "The FVectors should not not be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (fail, opposite direction")
        void isAntiParallelOppositeDirection() {
            FVector fVectorA = factory.getFVector(2, 2, 2);
            FVector fVectorB = factory.getFVector(4, 4, 4);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertFalse(fVectorA.isAntiParallel(fVectorB), "The FVectors should not be anti-parallel");
        }

        @Test
        @DisplayName("Is anti-parallel (throw IllegalStateException, input)")
        void isAntiParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isAntiParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is anti-parallel (throw IllegalStateException, argument)")
        void isAntiParallelThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isAntiParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is anti-parallel (validate)")
        void isAntiParallelValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testValue(FVector::isAntiParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set anti-parallel")
        void setAntiParallel() {
            FVector fVectorA = factory.getFVector(4, 5, -3, -3, 6, 1);
            FVector fVectorB = factory.getFVector(7, 3, -4, 6, 2, -3);

            fVectorA.setAntiParallel(fVectorB);

            assertTrue(fVectorA.isAntiParallel(fVectorB), "The two FVectors should be anti-parallel");
        }

        @Test
        @DisplayName("Set anti-parallel (throw IllegalStateException, input)")
        void setAntiParallelThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setAntiParallel(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Set anti-parallel (throw DirectionException, argument)")
        void setAntiParallelThrowDirectionExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setAntiParallel(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Set anti-parallel (validate)")
        void setAntiParallelValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testReference(FVector::setAntiParallel, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is orthogonal - X")
        void isOrthogonalX() {
            FVector fVectorA = factory.getFVector(1, 0, 0);
            FVector fVectorB = factory.getFVector(0, 5, 5);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal - Y")
        void isOrthogonalY() {
            FVector fVectorA = factory.getFVector(0, 1, 0);
            FVector fVectorB = factory.getFVector(5, 0, 5);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal - Z")
        void isOrthogonalZ() {
            FVector fVectorA = factory.getFVector(0, 0, 1);
            FVector fVectorB = factory.getFVector(5, 5, 0);

            fVectorA.moveBase(TestHelper.getRandomFPoint());
            fVectorB.moveBase(TestHelper.getRandomFPoint());

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal A (fail)")
        void isOrthogonalAFailA() {
            FVector fVectorA = factory.getFVector(1, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            assertFalse(fVectorA.isOrthogonal(fVectorB), "FVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal B (fail)")
        void isOrthogonalFailB() {
            FVector fVectorA = factory.getFVector(0, 1, 0);
            FVector fVectorB = factory.getFVector(-1, 1, 0, 1, -1, 0);

            assertFalse(fVectorA.isOrthogonal(fVectorB), "FVectors should not be orthogonal");
        }

        @Test
        @DisplayName("Is orthogonal (throw IllegalStateException, input)")
        void isOrthogonalThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isOrthogonal(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Is orthogonal (throw IllegalStateException, argument)")
        void isOrthogonalThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.isOrthogonal(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Is orthogonal (validate)")
        void isOrthogonalValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testValue(FVector::isOrthogonal, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Set orthogonal")
        void setOrthogonal() {
            FVector fVectorA = factory.getFVector(-1, 0, 0);
            FVector fVectorB = factory.getFVector(0, 0, 1, 1, 0, 0);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (length)")
        void setOrthogonalLength() {
            double magnitude = random.nextDouble(1, 100);

            FVector fVectorA = factory.getFVector(-magnitude, 0, 0);
            FVector fVectorB = factory.getFVector(0, 0, 1, 1, 0, 0);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
            assertEquals(magnitude, fVectorA.getLength(), jitter, "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (same base)")
        void setOrthogonalSameBase() {
            FVector fVectorA = TestHelper.getRandomFVector();

            FPoint fVectorBHead = TestHelper.getRandomFPoint(fVectorA.getRefHead());
            FVector fVectorB = factory.getFVector(fVectorA.getRefBase().copy(), fVectorBHead);

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, parallel)")
        void setOrthogonalThrowIllegalStateExceptionParallel() {
            FVector fVectorA = factory.getFVector(1, 2, 3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(6, 5, 4, 3, 2, 1);

            fVectorA.setParallel(fVectorB);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "FVectors cannot be parallel");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, anti-parallel)")
        void setOrthogonalThrowIllegalStateExceptionAntiParallel() {
            FVector fVectorA = factory.getFVector(1, 2, 3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(6, 5, 4, 3, 2, 1);

            fVectorA.setAntiParallel(fVectorB);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "FVectors cannot be anti-parallel");
        }

        @Test
        @DisplayName("Set orthogonal (same head)")
        void setOrthogonalSameHead() {
            FVector fVectorA = TestHelper.getRandomFVector();

            FPoint fVectorBBase = TestHelper.getRandomFPoint(fVectorA.getRefBase());
            FVector fVectorB = factory.getFVector(fVectorBBase, fVectorA.getRefHead().copy());

            fVectorA.setOrthogonal(fVectorB);

            assertTrue(fVectorA.isOrthogonal(fVectorB), "The two FVectors should be orthogonal");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, input)")
        void setOrthogonalThrowIllegalStateExceptionInput() {
            FVector fVectorA = factory.getFVector(0, 0, 0);
            FVector fVectorB = factory.getFVector(1, 2, 3);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "The input FVector direction is not defined");
        }

        @Test
        @DisplayName("Set orthogonal (throw IllegalStateException, argument)")
        void setOrthogonalThrowIllegalStateExceptionArgument() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(0, 0, 0);

            Assertions.assertThrows(IllegalStateException.class, () -> fVectorA.setOrthogonal(fVectorB),
                    "The argument FVector direction is not defined");
        }

        @Test
        @DisplayName("Set orthogonal (validate)")
        void setOrthogonalValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4, 5, 6);

            FVectorTestHelper.testReference(FVector::setOrthogonal, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is non-directional")
        void isNonDirectional() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = fPointBase.copy();
            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            assertTrue(fVector.isNearZeroLength(), "The two FPoints should be at the same position");
        }

        @Test
        @DisplayName("Is non-directional (fail)")
        void isNonDirectionalFail() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint(fPointBase);
            FVector fVector = factory.getFVector(fPointBase, fPointHead);

            assertFalse(fVector.isNearZeroLength(), "The two FPoints should not be at the same position");
        }

        @Test
        @DisplayName("Is non-directional (validate)")
        void isNonDirectionalValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            FVectorTestHelper.testValue(FVector::isNearZeroLength, fVector);
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FVectorCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FVector fVector = TestHelper.getRandomFVector();

            JSONObject json = fVector.toJSON();

            FVector fVectorRef = factory.getFVector().applyStateFrom(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fVector, fVectorRef,
                            "FVector references should point at different objects"),
                    () -> assertTrue(fVector.isExact(fVectorRef),
                            "The FVectors should be exact")
            );
        }

        @Test
        @DisplayName("Parse JSON export (validate)")
        void parseJSONExportValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::toJSON, fVector);
        }

        @Test
        @DisplayName("Is exact")
        void isExact() {
            FVector fVectorA = factory.getFVector(1, 2, 3, 4, 5, 6);
            FVector fVectorB = factory.getFVector(1, 2, 3, 4, 5, 6);

            assertTrue(fVectorA.isExact(fVectorB), "FVectors should be equal");
        }

        @Test
        @DisplayName("Is exact (fail)")
        void isExactFail() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVectorA = factory.getFVector(factory.getFPoint(), fPointHead);
            FVector fVectorB = factory.getFVector(fPointBase, factory.getFPoint());

            assertFalse(fVectorA.isExact(fVectorB), "FVectors should not be equal");
        }

        @Test
        @DisplayName("Is exact (validate)")
        void isExactValidate() {
            FVector fVectorA = factory.getFVector(1, 2, 3);
            FVector fVectorB = factory.getFVector(4,5, 6);

            FVectorTestHelper.testValue(FVector::isExact, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is exact with parameters")
        void isExactWithParameters() {
            double bX = random.nextDouble();
            double bY = random.nextDouble();
            double bZ = random.nextDouble();
            double hX = random.nextDouble();
            double hY = random.nextDouble();
            double hZ = random.nextDouble();

            FVector fVector = factory.getFVector(bX, bY, bZ, hX, hY, hZ);

            assertTrue(fVector.isExact(bX, bY, bZ, hX, hY, hZ),
                    "FVector values should be equal");
        }

        @Test
        @DisplayName("Is exact with parameters (fail)")
        void isExactWithParametersFail() {
            double bX = random.nextDouble();
            double bY = random.nextDouble();
            double bZ = random.nextDouble();
            double hX = random.nextDouble();
            double hY = random.nextDouble();
            double hZ = random.nextDouble();

            FVector fVector = factory.getFVector(bX, bY, bZ, hX, hY, hZ);

            assertFalse(fVector.isExact(0, 0, 0, 0, 0, 0),
                    "FVector values should not be equal");
        }

        @Test
        @DisplayName("Is exact with parameters (validate)")
        void isExactWithParametersValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(e -> e.isExact(0, 0, 0, 0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Is similar")
        void isSimilar() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVectorA = factory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = factory.getFVector(fPointBase.addX(jitter * 0.5), fPointHead);

            assertTrue(fVectorA.isSimilar(fVectorB), "FVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (zero)")
        void isSimilarZero() {
            FVector fVectorA = factory.getFVector();
            FVector fVectorB = factory.getFVector();

            assertTrue(fVectorA.isSimilar(fVectorB), "FVectors should be similar");
        }

        @Test
        @DisplayName("Is similar (fail)")
        void isSimilarFail() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVectorA = factory.getFVector(fPointBase.copy(), fPointHead.copy());
            FVector fVectorB = factory.getFVector(fPointBase.addX(jitter * 1.5), fPointHead);

            assertFalse(fVectorA.isSimilar(fVectorB), "FVectors should not be similar");
        }

        @Test
        @DisplayName("Is similar (validate)")
        void isSimilarValidate() {
            FVector fVectorA = TestHelper.getRandomFVector();
            FVector fVectorB = TestHelper.getRandomFVector(fVectorA);

            FVectorTestHelper.testValue(FVector::isSimilar, fVectorA, fVectorB);
        }

        @Test
        @DisplayName("Is similar with parameters")
        void isSimilarWithParameters() {
            double bX = random.nextDouble();
            double bY = random.nextDouble();
            double bZ = random.nextDouble();
            double hX = random.nextDouble();
            double hY = random.nextDouble();
            double hZ = random.nextDouble();

            FVector fVector = factory.getFVector(
                    bX + (0.5 * jitter), bY + (0.5 * jitter), bZ + (0.5 * jitter),
                    hX + (0.5 * jitter), hY + (0.5 * jitter), hZ + (0.5 * jitter));

            assertTrue(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "FVector values should be equal");
        }

        @Test
        @DisplayName("Is similar with parameters (fail)")
        void isSimilarWithParametersFail() {
            double bX = random.nextDouble();
            double bY = random.nextDouble();
            double bZ = random.nextDouble();
            double hX = random.nextDouble();
            double hY = random.nextDouble();
            double hZ = random.nextDouble();

            FVector fVector = factory.getFVector(
                    bX + (1.5 * jitter), bY + (1.5 * jitter), bZ + (1.5 * jitter),
                    hX + (1.5 * jitter), hY + (1.5 * jitter), hZ + (1.5 * jitter));

            assertFalse(fVector.isSimilar(bX, bY, bZ, hX, hY, hZ),
                    "FVector values should not be equal");
        }

        @Test
        @DisplayName("Is similar with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(e -> e.isSimilar(0, 0, 0, 0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVectorA = factory.getFVector(fPointBase, fPointHead);
            FVector fVectorB = factory.getFVector(fPointBase, fPointHead);

            assertEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two identical FVectors should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FPoint fPointBase = TestHelper.getRandomFPoint();
            FPoint fPointHead = TestHelper.getRandomFPoint();

            FVector fVectorA = factory.getFVector(factory.getFPoint(), fPointHead);
            FVector fVectorB = factory.getFVector(fPointBase, factory.getFPoint());

            assertNotEquals(fVectorA.hashCode(), fVectorB.hashCode(),
                    "Two different FVectors should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::hashCode, fVector);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FVector fVectorA = TestHelper.getRandomFVector();
            FVector fVectorB = fVectorA.copy();

            Assertions.assertAll("Validate similarity",
                    () -> assertNotSame(fVectorA, fVectorB,
                            "FVectors represent different objects"),
                    () -> assertTrue(fVectorA.isExact(fVectorB),
                            "FVectors should have the same values"),
                    () -> assertNotSame(fVectorA.getRefBase(), fVectorB.getRefBase(),
                            "The base FPoints should be different"),
                    () -> assertNotSame(fVectorA.getRefHead(), fVectorB.getRefHead(),
                            "The head FPoints should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::copy, fVector);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FVector fVectorA = TestHelper.getRandomFVector();
            FVector fVectorB = fVectorA.copyZero();

            Assertions.assertAll("Validate similarity",
                    () -> assertNotSame(fVectorA, fVectorB,
                            "FVectors represent different objects"),
                    () -> assertFalse(fVectorA.isExact(fVectorB),
                            "FVectors should have the same values"),
                    () -> assertNotSame(fVectorA.getRefBase(), fVectorB.getRefBase(),
                            "The base FPoints should be different"),
                    () -> assertNotSame(fVectorA.getRefHead(), fVectorB.getRefHead(),
                            "The head FPoints should be different")
            );
        }

        @Test
        @DisplayName("Copy zero (validate)")
        void copyZeroValidate() {
            FVector fVector = factory.getFVector(1, 2, 3);

            FVectorTestHelper.testValue(FVector::copyZero, fVector);
        }
    }

    @Nested
    @Tag("Mutable")
    @DisplayName("Base mutable")
    class FVectorMutableTest {

        @Test
        @DisplayName("Add FPoint")
        void addFPoint() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.add(fPoint);

            Assertions.assertAll("Validate FVectors",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().add(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().add(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add FPoint (validate)")
        void addFPointValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);
            FPoint fPoint = factory.getFPoint(3, 2, 1);

            FVectorTestHelper.testReference(FVector::add, fVector, fPoint);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.add(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().add(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().add(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.add(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.add(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().add(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().add(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.add(0), fVector);
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.addX(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().addX(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().addX(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add X (validate)")
        void addXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.addX(0), fVector);
        }

        @Test
        @DisplayName("Add Y")
        void addY() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.addY(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().addY(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().addY(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Y (validate)")
        void addYValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.addY(0), fVector);
        }

        @Test
        @DisplayName("Add Z")
        void addZ() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.addZ(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().addZ(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().addZ(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Add Z (validate)")
        void addZValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.addZ(0), fVector);
        }

        @Test
        @DisplayName("Sub FPoint")
        void subFPoint() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.sub(fPoint);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().sub(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().sub(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub FPoint (validate)")
        void subFPointValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);
            FPoint fPoint = factory.getFPoint(3, 2, 1);

            FVectorTestHelper.testReference(FVector::sub, fVector, fPoint);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.sub(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().sub(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().sub(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.sub(0, 0, 0), fVector);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.sub(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().sub(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().sub(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.sub(0), fVector);
        }

        @Test
        @DisplayName("Sub X")
        void subX() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.subX(value);

            Assertions.assertAll("Validate FValue",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().subX(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().subX(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.subX(0), fVector);
        }

        @Test
        @DisplayName("Sub Y")
        void subY() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.subY(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().subY(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().subY(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub Y (validate)")
        void subYValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.subY(0), fVector);
        }

        @Test
        @DisplayName("Sub Z")
        void subZ() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.subZ(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().subZ(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().subZ(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Sub X (validate)")
        void subZValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.subZ(0), fVector);
        }

        @Test
        @DisplayName("Mul FPoint")
        void mulFPoint() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.mul(fPoint);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().mul(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().mul(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul FPoint (validate)")
        void mulFPointValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);
            FPoint fPoint = factory.getFPoint(3, 2, 1);

            FVectorTestHelper.testReference(FVector::mul, fVector, fPoint);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.mul(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().mul(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().mul(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.mul(1, 1, 1), fVector);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.mul(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().mul(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().mul(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.mul(1), fVector);
        }

        @Test
        @DisplayName("Mul X")
        void mulX() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.mulX(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().mulX(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().mulX(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul X (validate)")
        void mulXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.mulX(1), fVector);
        }

        @Test
        @DisplayName("Mul Y")
        void mulY() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.mulY(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().mulY(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().mulY(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Y (validate)")
        void mulYValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.mulY(1), fVector);
        }

        @Test
        @DisplayName("Mul Z")
        void mulZ() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.mulZ(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().mulZ(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().mulZ(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Mul Z (validate)")
        void mulZValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.mulZ(1), fVector);
        }

        @Test
        @DisplayName("Div FPoint")
        void divFPoint() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.div(fPoint);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().div(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().div(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div FPoint (throw ArithmeticException)")
        void divFPointThrowArithmeticException() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fVector.div(factory.getFPoint(0, 1, 1)),
                            "The X value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fVector.div(factory.getFPoint(1, 0, 1)),
                            "The Y value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fVector.div(factory.getFPoint(0, 1, 1)),
                            "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div FPoint (validate)")
        void divFPointValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);
            FPoint fPoint = factory.getFPoint(3, 2, 1);

            FVectorTestHelper.testReference(FVector::div, fVector, fPoint);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            FPoint fPoint = TestHelper.getRandomFPoint();

            fVector.div(fPoint.getX(), fPoint.getY(), fPoint.getZ());

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().div(fPoint)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().div(fPoint)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fVector.div(0, 1, 1), "The X value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fVector.div(1, 0, 1), "The Y value is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fVector.div(0, 1, 1), "The Z value is zero")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.div(1, 1, 1), fVector);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.div(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().div(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().div(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            Assertions.assertThrows(ArithmeticException.class, () -> fVector.div(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.div(1), fVector);
        }

        @Test
        @DisplayName("Div X")
        void divX() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.divX(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().divX(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().divX(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div X (throw ArithmeticException)")
        void divXThrowArithmeticException() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            Assertions.assertThrows(ArithmeticException.class, () -> fVector.divX(0), "The X value is zero");
        }

        @Test
        @DisplayName("Div X (validate)")
        void divXValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.divX(1), fVector);
        }

        @Test
        @DisplayName("Div Y")
        void divY() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.divY(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().divY(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().divY(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div Y (throw ArithmeticException)")
        void divYThrowArithmeticException() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            Assertions.assertThrows(ArithmeticException.class, () -> fVector.divY(0), "The Y value is zero");
        }

        @Test
        @DisplayName("Div Y (validate)")
        void divYValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.divY(1), fVector);
        }

        @Test
        @DisplayName("Div Z")
        void divZ() {
            FVector fVector = TestHelper.getRandomFVector();
            FVector fVectorRef = fVector.copy();
            double value = random.nextDouble();

            fVector.divZ(value);

            Assertions.assertAll("Validate FVector",
                    () -> assertTrue(fVector.getRefBase().isExact(fVectorRef.getRefBase().copy().divZ(value)),
                            "The base FPoint is erroneous"),
                    () -> assertTrue(fVector.getRefHead().isExact(fVectorRef.getRefHead().copy().divZ(value)),
                            "The head FPoint is erroneous")
            );
        }

        @Test
        @DisplayName("Div Z (ArithmeticException)")
        void divZThrowArithmeticException() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            Assertions.assertThrows(ArithmeticException.class, () -> fVector.divZ(0), "The Z value is zero");
        }

        @Test
        @DisplayName("Div Z (validate)")
        void divZValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(e -> e.divZ(1), fVector);
        }

        @Test
        @DisplayName("Get FPoint list")
        void getFPoints() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            List<FPoint> list = fVector.disassemble();

            Assertions.assertAll("Validate FVector list",
                    () -> Assertions.assertEquals(2, list.size(), "The size of the list is incorrect"),
                    () -> assertSame(fVector.getRefBase(), list.get(0), "The base FPoint is incorrect"),
                    () -> assertSame(fVector.getRefHead(), list.get(1), "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Get FPoint list (validate)")
        void getFPointsValidate() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5,6);

            FVectorTestHelper.testValue(FVector::disassemble, fVector);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FVector fVectorRef = TestHelper.getRandomFVector();
            FVector fVector = factory.getFVector();

            fVectorRef.applyStateTo(fVector);

            Assertions.assertAll("Validate FVector values",
                    () -> assertEquals(fVector.getRefBase(), fVector.getRefBase(), "The base FPoint is incorrect"),
                    () -> assertEquals(fVector.getRefHead(), fVector.getRefHead(), "The head FPoint is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FVector fVectorRef = factory.getFVector(1, 2, 3, 4, 5,6);
            FVector fVector = factory.getFVector(6, 5, 4, 3, 2, 1);

            fVectorRef.applyStateTo(fVector);

            Assertions.assertAll("Validate FVector references",
                    () -> assertNotSame(fVectorRef, fVector,
                            "FVectors should point to different objects"),
                    () -> assertNotSame(fVectorRef.getRefBase(), fVector.getRefBase(),
                            "The base FPoint reference is incorrect"),
                    () -> assertNotSame(fVectorRef.getRefHead(), fVector.getRefHead(),
                            "The head FPoint reference is incorrect")
            );
        }
    }

    @Nested
    @Tag("Extension")
    class FVectorExtensionTest {

        @Test
        @DisplayName("Apply")
        void apply() {
            FVector fVector = factory.getFVector();

            var fVectorRes = fVector.apply(p -> p.set(1, 2, 3, 4, 5, 6));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(2, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(3, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(4, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(5, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(6, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Apply with fixed state")
        void applyWithFixedState() {
            FVector fVector = factory.getFVector();

            List<Double> intermediate = new ArrayList<>();

            var fVectorRes = fVector.applyWithFixedState(p ->
                    intermediate.add(p.set(1, 2, 3, 4, 5, 6).getLengthP2()));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(0, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(0, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                    () -> assertEquals(27, intermediate.get(0), jitter, "The value is incorrect"),
                    () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Apply with fixed length")
        void applyWithFixedLength() {
            FVector fVector = factory.getFVector(1, 0, 0);

            var fVectorRes = fVector.applyWithFixedLength(p ->
                    p.setHead(-10, 0, 0));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(0, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(0, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(-1, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Apply with centered position")
        void applyWithCenteredPosition() {
            FVector fVector = factory.getFVector(5, 0, 0, 9, 0, 0);

            var fVectorRes = fVector.applyWithCenteredPosition(p ->
                    p.getRefHead().reflect(factory.getFPoint(9, 0, 0)));

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(5, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(0, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(0, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(19, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(0, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with double")
        void terminateWithDouble() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            var res = fVector.toDouble(p -> {
                p.reflect(factory.getFPoint());
                return p.getLengthP2();
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-1, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(-2, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(-3, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(-4, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(-5, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(-6, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertEquals(27, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean")
        void terminateWithBoolean() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            var res = fVector.toBoolean(p -> {
                p.reflect(factory.getFPoint());
                return p.isNearZeroLength();
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(-1, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(-2, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(-3, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(-4, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(-5, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(-6, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertFalse(res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with double (fixed state)")
        void terminateWithDoubleFixedState() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            var res = fVector.toDoubleWithFixedState(p -> {
                p.reflect(factory.getFPoint());
                return p.getLengthP2();
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(2, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(3, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(4, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(5, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(6, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertEquals(27, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean (fixed state)")
        void terminateWithBooleanFixedState() {
            FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

            var res = fVector.toBooleanWithFixedState(p -> {
                p.reflect(factory.getFPoint());
                return p.isNearZeroLength();
            });

            Assertions.assertAll("Validate FPoint values",
                    () -> assertEquals(1, fVector.getBaseX(), "The base X value is incorrect"),
                    () -> assertEquals(2, fVector.getBaseY(), "The base Y value is incorrect"),
                    () -> assertEquals(3, fVector.getBaseZ(), "The base Z value is incorrect"),
                    () -> assertEquals(4, fVector.getHeadX(), "The head X value is incorrect"),
                    () -> assertEquals(5, fVector.getHeadY(), "The head Y value is incorrect"),
                    () -> assertEquals(6, fVector.getHeadZ(), "The head Z value is incorrect"),
                    () -> assertFalse(res, "The value is incorrect")
            );
        }
    }
}

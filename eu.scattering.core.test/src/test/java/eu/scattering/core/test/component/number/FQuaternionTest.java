package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.component.number.support.FQuaternionTestHelper;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FQuaternion")
public class FQuaternionTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FQuaternionBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            assertNotNull(fQuaternion, "The instance is null");

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FPos4D")
        void constructWithFPos4D() {
            FQuaternion fQuaternion = factory.getFQuaternion(factory.getFPos4D(1, 2, 3, 4));

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(3, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(4, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with parameters")
        void constructWithParameters() {
            FQuaternion fQuaternion = factory.getFQuaternion(1, 2, 3, 4);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(3, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(4, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with real part")
        void constructWithRealPart() {
            FQuaternion fQuaternion = factory.getFQuaternion(1);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.set(1, 2, 3, 4);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(3, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(4, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FQuaternion")
        void setWithFQuaternion() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.applyStateFrom(factory.getFQuaternion(1, 2, 3, 4));

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(3, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(4, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FPos4D")
        void setWithFPos4D() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.applyStateFrom(factory.getFPos4D(1, 2, 3, 4));

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(3, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(4, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set Re")
        void setRe() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.setRe(1);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(1, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set I")
        void setI() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.setI(1);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(1, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set J")
        void setJ() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.setJ(1);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(1, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Set K")
        void setK() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.setK(1);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(1, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Export to FPos4D")
        void toFPos4D() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            fQuaternion.set(1, 2, 3, 4);

            FPos4D fPos4D = fQuaternion.toFPos4D();

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fPos4D.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, fPos4D.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, fPos4D.getD2(), "The D1 value is incorrect"),
                    () -> assertEquals(4, fPos4D.getD3(), "The D1 value is incorrect")
            );
        }
    }

    @Nested
    @Tag("Mutable")
    @DisplayName("Base mutable")
    class FQuaternionMutableTest {

        private double refRe, refI, refJ, refK;
        private double argRe, argI, argJ, argK;
        private FQuaternion refFQuaternion, argFQuaternion;

        @BeforeEach
        void beforeEach() {
            refRe = rand.nextDouble();
            refI = rand.nextDouble();
            refJ = rand.nextDouble();
            refK = rand.nextDouble();

            argRe = rand.nextDouble();
            argI = rand.nextDouble();
            argJ = rand.nextDouble();
            argK = rand.nextDouble();

            refFQuaternion = factory.getFQuaternion(refRe, refI, refJ, refK);
            argFQuaternion = factory.getFQuaternion(argRe, argI, argJ, argK);
        }

        @Test
        @DisplayName("Add FQuaternion")
        void addFQuaternion() {

            FQuaternion results = refFQuaternion.add(argFQuaternion);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect"),
                    () -> assertSame(refFQuaternion, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Add FQuaternion (validate)")
        void addFQuaternionValidate() {

            FQuaternionTestHelper.restReference(FQuaternion::add, refFQuaternion, argFQuaternion);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            refFQuaternion.add(argRe, argI, argJ, argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {

            FQuaternionTestHelper.restReference(e -> e.add(0, 0, 0, 0), refFQuaternion);
        }

        @Test
        @DisplayName("Add FPos4D")
        void addFPos4D() {

            FQuaternion results = refFQuaternion.add(factory.getFPos4D(argRe, argI, argJ, argK));

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect"),
                    () -> assertSame(refFQuaternion, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = argRe * argI * argJ * argK;

            refFQuaternion.addFactor(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + op, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + op, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + op, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + op, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {

            FQuaternionTestHelper.restReference(e -> e.addFactor(0), refFQuaternion);
        }

        @Test
        @DisplayName("Add Re")
        void addRe() {

            refFQuaternion.addRe(argRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add Re (validate)")
        void addReValidate() {

            FQuaternionTestHelper.restReference(e -> e.addRe(0), refFQuaternion);
        }

        @Test
        @DisplayName("Add Im")
        void addIm() {

            refFQuaternion.addIm(argI, argJ, argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add Im (validate)")
        void addImValidate() {

            FQuaternionTestHelper.restReference(e -> e.addIm(0, 0, 0), refFQuaternion);
        }

        @Test
        @DisplayName("Add I")
        void addI() {

            refFQuaternion.addI(argI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add I (validate)")
        void addIValidate() {

            FQuaternionTestHelper.restReference(e -> e.addI(0), refFQuaternion);
        }

        @Test
        @DisplayName("Add J")
        void addJ() {

            refFQuaternion.addJ(argJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add J (validate)")
        void addJValidate() {

            FQuaternionTestHelper.restReference(e -> e.addJ(0), refFQuaternion);
        }

        @Test
        @DisplayName("Add K")
        void addK() {

            refFQuaternion.addK(argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add K (validate)")
        void addKValidate() {

            FQuaternionTestHelper.restReference(e -> e.addK(0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub FQuaternion")
        void subFQuaternion() {

            refFQuaternion.sub(argFQuaternion);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FQuaternion (validate)")
        void subFQuaternionValidate() {

            FQuaternionTestHelper.restReference(FQuaternion::sub, refFQuaternion, argFQuaternion);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            refFQuaternion.sub(argRe, argI, argJ, argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {

            FQuaternionTestHelper.restReference(e -> e.sub(0, 0, 0, 0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub FPos4D")
        void subFPos4D() {

            refFQuaternion.sub(factory.getFPos4D(argRe, argI, argJ, argK));

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = argRe * argI * argJ * argK;

            refFQuaternion.subFactor(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - op, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - op, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - op, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - op, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {

            FQuaternionTestHelper.restReference(e -> e.subFactor(0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub Re")
        void subRe() {

            refFQuaternion.subRe(argRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Re (validate)")
        void subReValidate() {

            FQuaternionTestHelper.restReference(e -> e.subRe(0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub Im")
        void subIm() {

            refFQuaternion.subIm(argI, argJ, argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Im (validate)")
        void subImValidate() {

            FQuaternionTestHelper.restReference(e -> e.subIm(0, 0, 0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub I")
        void subI() {

            refFQuaternion.subI(argI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub I (validate)")
        void subIValidate() {

            FQuaternionTestHelper.restReference(e -> e.subI(0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub J")
        void subJ() {

            refFQuaternion.subJ(argJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub J (validate)")
        void subJValidate() {

            FQuaternionTestHelper.restReference(e -> e.subJ(0), refFQuaternion);
        }

        @Test
        @DisplayName("Sub K")
        void subK() {

            refFQuaternion.subK(argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub K (validate)")
        void subKValidate() {

            FQuaternionTestHelper.restReference(e -> e.subK(0), refFQuaternion);
        }

        @Test
        @DisplayName("Mul FQuaternion (simple)")
        void mulFQuaternionSimple() {
            double refRe = 2, refI = 3, refJ = 4, refK = 5;
            double argRe = 6, argI = 7, argJ = 8, argK = 9;

            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionArg = factory.getFQuaternion(argRe, argI, argJ, argK);

            fQuaternionRef.mul(fQuaternionArg);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(-86, fQuaternionRef.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(28, fQuaternionRef.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(48, fQuaternionRef.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(44, fQuaternionRef.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FQuaternion (validate)")
        void mulFQuaternionValidate() {

            FQuaternionTestHelper.restReference(FQuaternion::mul, refFQuaternion, argFQuaternion);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {
            double refRe = 2, refI = 3, refJ = 4, refK = 5;

            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);

            fQuaternionRef.mul(6, 7, 8, 9);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(-86, fQuaternionRef.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(28, fQuaternionRef.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(48, fQuaternionRef.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(44, fQuaternionRef.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {

            FQuaternionTestHelper.restReference(e -> e.mul(0, 0, 0, 0), refFQuaternion);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = argRe * argI * argJ * argK;

            refFQuaternion.mulFactor(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe * op, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI * op, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ * op, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK * op, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {

            FQuaternionTestHelper.restReference(e -> e.mulFactor(0), refFQuaternion);
        }

        @Test
        @DisplayName("Mul Re")
        void mulRe() {

            refFQuaternion.mulRe(argRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe * argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Re (validate)")
        void mulReValidate() {

            FQuaternionTestHelper.restReference(e -> e.mulRe(0), refFQuaternion);
        }

        @Test
        @DisplayName("Mul I")
        void mulI() {

            refFQuaternion.mulI(argI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI * argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul I (validate)")
        void mulIValidate() {

            FQuaternionTestHelper.restReference(e -> e.mulI(0), refFQuaternion);
        }

        @Test
        @DisplayName("Mul J")
        void mulJ() {

            refFQuaternion.mulJ(argJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ * argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul J (validate)")
        void mulJValidate() {

            FQuaternionTestHelper.restReference(e -> e.mulJ(0), refFQuaternion);
        }

        @Test
        @DisplayName("Mul K")
        void mulK() {

            refFQuaternion.mulK(argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK * argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul K (validate)")
        void mulKValidate() {

            FQuaternionTestHelper.restReference(e -> e.mulK(0), refFQuaternion);
        }

        @Test
        @DisplayName("Div FQuaternion (simple)")
        void divFQuaternionSimple() {
            double refRe = 2, refI = 3, refJ = 4, refK = 5;
            double argRe = 6, argI = 7, argJ = 8, argK = 9;

            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionArg = factory.getFQuaternion(argRe, argI, argJ, argK);

            fQuaternionRef.div(fQuaternionArg);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0.478260869565, fQuaternionRef.getRe(),
                            epsilon, "The real part is incorrect"),
                    () -> assertEquals(0.034782608696, fQuaternionRef.getI(),
                            epsilon, "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0.000000000000, fQuaternionRef.getJ(),
                            epsilon, "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0.069565217391, fQuaternionRef.getK(),
                            epsilon, "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div FQuaternion (throw ArithmeticException)")
        void divFQuaternionThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFQuaternion.div(factory.getFQuaternion(0, 0, 0, 0)),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div FQuaternion (validate)")
        void divFQuaternionValidate() {

            FQuaternionTestHelper.restReference(FQuaternion::div, refFQuaternion, argFQuaternion);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {
            double refRe = 2, refI = 3, refJ = 4, refK = 5;

            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);

            fQuaternionRef.div(6, 7, 8, 9);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0.478260869565, fQuaternionRef.getRe(),
                            epsilon, "The real part is incorrect"),
                    () -> assertEquals(0.034782608696, fQuaternionRef.getI(),
                            epsilon, "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0.000000000000, fQuaternionRef.getJ(),
                            epsilon, "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0.069565217391, fQuaternionRef.getK(),
                            epsilon, "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {

            FQuaternionTestHelper.restReference(e -> e.div(1, 1, 1, 1), refFQuaternion);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = argRe * argI * argJ * argK;

            refFQuaternion.divFactor(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe / op, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI / op, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ / op, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK / op, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {

            FQuaternionTestHelper.restReference(e -> e.divFactor(1), refFQuaternion);
        }

        @Test
        @DisplayName("Div Re")
        void divRe() {

            refFQuaternion.divRe(argRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe / argRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div Re (throw ArithmeticException)")
        void divReThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFQuaternion.divRe(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Re (validate)")
        void divReValidate() {

            FQuaternionTestHelper.restReference(e -> e.divRe(1), refFQuaternion);
        }

        @Test
        @DisplayName("Div I")
        void divI() {

            refFQuaternion.divI(argI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI / argI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div I (throw ArithmeticException)")
        void divIThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFQuaternion.divI(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div I (validate)")
        void divIValidate() {

            FQuaternionTestHelper.restReference(e -> e.divI(1), refFQuaternion);
        }

        @Test
        @DisplayName("Div J")
        void divJ() {

            refFQuaternion.divJ(argJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ / argJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div J (throw ArithmeticException)")
        void divJThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFQuaternion.divJ(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div J (validate)")
        void divJValidate() {

            FQuaternionTestHelper.restReference(e -> e.divJ(1), refFQuaternion);
        }

        @Test
        @DisplayName("Div K")
        void divK() {

            refFQuaternion.divK(argK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK / argK, refFQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div K (throw ArithmeticException)")
        void divKThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFQuaternion.divK(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div K (validate)")
        void divKValidate() {

            FQuaternionTestHelper.restReference(e -> e.subK(0), refFQuaternion);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FQuaternion fComplexRef = factory.getFQuaternion();

            refFQuaternion.applyStateTo(fComplexRef);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, refFQuaternion.getRe(),
                            "The reference Re value is incorrect"),
                    () -> assertEquals(refI, refFQuaternion.getI(),
                            "The reference I value is incorrect"),
                    () -> assertEquals(refJ, refFQuaternion.getJ(),
                            "The reference J value is incorrect"),
                    () -> assertEquals(refK, refFQuaternion.getK(),
                            "The reference K value is incorrect"),
                    () -> assertEquals(refRe, fComplexRef.getRe(),
                            "The Re value is incorrect"),
                    () -> assertEquals(refI, fComplexRef.getI(),
                            "The I value is incorrect"),
                    () -> assertEquals(refJ, fComplexRef.getJ(),
                            "The J value is incorrect"),
                    () -> assertEquals(refK, fComplexRef.getK(),
                            "The K value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FQuaternion fQuaternionOp = factory.getFQuaternion();

            FQuaternion fQuaternionRef = fQuaternionOp.applyStateTo(refFQuaternion);

            Assertions.assertAll("Validate references",
                    () -> assertNotSame(refFQuaternion, fQuaternionOp,
                            "FComplex references should change"),
                    () -> assertSame(fQuaternionOp, fQuaternionRef,
                            "The FComplex reference should not change")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FQuaternionCoreTest {

        private double refRe, refI, refJ, refK;
        private FQuaternion refFQuaternion;

        @BeforeEach
        void beforeEach() {

            refRe = rand.nextDouble();
            refI = rand.nextDouble();
            refJ = rand.nextDouble();
            refK = rand.nextDouble();
            refFQuaternion = factory.getFQuaternion(refRe, refI, refJ, refK);
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            JSONObject json = refFQuaternion.toJSON();

            FQuaternion fQuaternionOp = factory.getFQuaternion().applyStateFrom(json);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternionOp.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternionOp.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternionOp.getJ(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refK, fQuaternionOp.getK(),
                            "The imaginary part (I) is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FQuaternion fQuaternionArg = factory.getFQuaternion(refRe, refI, refJ, refK);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(refFQuaternion.isExact(fQuaternionArg),
                            "FQuaternion values should be equal"),
                    () -> assertTrue(fQuaternionArg.isExact(refFQuaternion),
                            "FQuaternion values should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FQuaternion fQuaternionArg = refFQuaternion.copy().addFactor(0.5 * epsilon);

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(refFQuaternion.isExact(fQuaternionArg),
                            "FQuaternion values should not be equal"),
                    () -> assertFalse(fQuaternionArg.isExact(refFQuaternion),
                            "FQuaternion values should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FQuaternion fQuaternionArg = refFQuaternion.copy().addFactor(0.5 * epsilon);

            FQuaternionTestHelper.testValue(FQuaternion::isExact, refFQuaternion, fQuaternionArg);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {

            assertTrue(refFQuaternion.isExact(refRe, refI, refJ, refK),
                    "FQuaternion values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {

            assertFalse(refFQuaternion.isExact(0, 0, 0, 0),
                    "FQuaternion values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(e -> e.isExact(0, 0, 0, 0), fQuaternionRef);
        }

        @Test
        @DisplayName("Exactness with FPos4D")
        void isExactWithFPos4D() {
            FPos4D fPos4D = factory.getFPos4D(refRe, refI, refJ, refK);

            assertTrue(refFQuaternion.isExact(fPos4D),
                            "Values should be equal");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            double ref = epsilon * 0.5;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion)),
                            "FQuaternion values should be similar (same position)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addRe(ref)),
                            "FQuaternion values should be similar (positive Re)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subRe(ref)),
                            "FQuaternion values should be similar (negative Re)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addI(ref)),
                            "FQuaternion values should be similar (positive I)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subI(ref)),
                            "FQuaternion values should be similar (negative I)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addJ(ref)),
                            "FQuaternion values should be similar (positive J)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subJ(ref)),
                            "FQuaternion values should be similar (negative J)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addK(ref)),
                            "FQuaternion values should be similar (positive K)"),
                    () -> assertTrue(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subK(ref)),
                            "FQuaternion values should be similar (negative K)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            double ref = epsilon * 2;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addRe(ref)),
                            "FQuaternion values should not be similar (positive Re)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subRe(ref)),
                            "FQuaternion values should not be similar (negative Re)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addI(ref)),
                            "FQuaternion values should not be similar (positive I)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subI(ref)),
                            "FQuaternion values should not be similar (negative I)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addJ(ref)),
                            "FQuaternion values should not be similar (positive J)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subJ(ref)),
                            "FQuaternion values should not be similar (negative J)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).addK(ref)),
                            "FQuaternion values should not be similar (positive K)"),
                    () -> assertFalse(refFQuaternion
                                    .isSimilar(factory.getFQuaternion().add(refFQuaternion).subK(ref)),
                            "FQuaternion values should not be similar (negative K)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FQuaternion fQuaternionArg = refFQuaternion.copy().addFactor(0.5 * epsilon);

            FQuaternionTestHelper.testValue(FQuaternion::isSimilar, refFQuaternion, fQuaternionArg);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            double error = 0.5 * epsilon;

            assertTrue(refFQuaternion.isSimilar(
                    refRe + error,
                    refI + error, refJ + error, refK + error),
                    "FQuaternion values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            double error = 0.5 * epsilon;

            assertTrue(refFQuaternion.isSimilar(
                    refRe + error,
                    refI + error, refJ + error, refK + error),
                    "FQuaternion values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(e -> e.isSimilar(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Similarity with FPos4D")
        void isSimilarWithFPos4D() {
            double error = 0.5 * epsilon;
            FPos4D fPos4D = factory.getFPos4D(refRe + error, refI + error, refJ + error, refK + error);

            assertTrue(refFQuaternion.isSimilar(fPos4D),
                    "FComplex values should be similar");
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FQuaternion fQuaternionRefA = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionRefB = factory.getFQuaternion(refRe, refI, refJ, refK);

            assertEquals(fQuaternionRefA.hashCode(), fQuaternionRefB.hashCode(),
                    "Two identical FQuaternion values should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);

            assertNotEquals(fQuaternionRef.hashCode(), factory.getFQuaternion().hashCode(),
                    "Two different FQuaternion values should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::hashCode, fQuaternion);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternion = fQuaternionRef.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fQuaternionRef, fQuaternion,
                            "FQuaternion objects contain different values"),
                    () -> assertTrue(fQuaternionRef.isExact(fQuaternion),
                            "FQuaternion values should be the same"),
                    () -> assertFalse(fQuaternionRef.isExact(fQuaternion.add(fQuaternionRef)),
                            "FQuaternion values should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::copy, fQuaternion);
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FQuaternionAdvancedTest {

        @Test
        @DisplayName("Get magnitude")
        void getMagnitude() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            double resRe = fQuaternion.getRe() * fQuaternion.getRe();
            double resI = fQuaternion.getI() * fQuaternion.getI();
            double resJ = fQuaternion.getJ() * fQuaternion.getJ();
            double resK = fQuaternion.getK() * fQuaternion.getK();

            double res = Math.sqrt(resRe + resI + resJ + resK);

            assertEquals(res, fQuaternion.getMagnitude(), epsilon, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude (validate)")
        void getMagnitudeValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::getMagnitude, fQuaternion);
        }

        @Test
        @DisplayName("Get magnitude P2")
        void getMagnitudeP2() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            double resRe = fQuaternion.getRe() * fQuaternion.getRe();
            double resI = fQuaternion.getI() * fQuaternion.getI();
            double resJ = fQuaternion.getJ() * fQuaternion.getJ();
            double resK = fQuaternion.getK() * fQuaternion.getK();

            double res = resRe + resI + resJ + resK;

            assertEquals(res, fQuaternion.getMagnitudeP2(), epsilon, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude P2 (validate)")
        void getMagnitudeP2Validate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::getMagnitudeP2, fQuaternion);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FQuaternion fQuaternionRef = TestHelper.getRandFQuaternion();
            FQuaternion fQuaternionArg = TestHelper.getRandFQuaternion(fQuaternionRef);

            double distanceRe = Math.pow(Math.abs(fQuaternionRef.getRe() - fQuaternionArg.getRe()), 2);
            double distanceI = Math.pow(Math.abs(fQuaternionRef.getI() - fQuaternionArg.getI()), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternionRef.getJ() - fQuaternionArg.getJ()), 2);
            double distanceK = Math.pow(Math.abs(fQuaternionRef.getK() - fQuaternionArg.getK()), 2);

            double res = Math.sqrt(distanceRe + distanceI + distanceJ + distanceK);

            assertEquals(res, fQuaternionRef.getDistance(fQuaternionArg),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion();
            FQuaternion fQuaternionArg = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::getDistance, fQuaternionRef, fQuaternionArg);
        }

        @Test
        @DisplayName("Get distance with primitives")
        void getDistanceWithPrimitives() {
            double re = rand.nextDouble();
            double i = rand.nextDouble();
            double j = rand.nextDouble();
            double k = rand.nextDouble();

            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            double distanceRe = Math.pow(Math.abs(fQuaternion.getRe() - re), 2);
            double distanceI = Math.pow(Math.abs(fQuaternion.getI() - i), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternion.getJ() - j), 2);
            double distanceK = Math.pow(Math.abs(fQuaternion.getK() - k), 2);

            double res = Math.sqrt(distanceRe + distanceI + distanceJ + distanceK);

            assertEquals(res, fQuaternion.getDistance(re, i, j, k),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance with FPos4D")
        void getDistanceWithFPos4D() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();
            FPos4D fPos4D = TestHelper.getRandFQuaternion(fQuaternion).toFPos4D();

            double distanceRe = Math.pow(Math.abs(fQuaternion.getRe() - fPos4D.getD0()), 2);
            double distanceI = Math.pow(Math.abs(fQuaternion.getI() - fPos4D.getD1()), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternion.getJ() - fPos4D.getD2()), 2);
            double distanceK = Math.pow(Math.abs(fQuaternion.getK() - fPos4D.getD3()), 2);

            double res = Math.sqrt(distanceRe + distanceI + distanceJ + distanceK);

            assertEquals(res, fQuaternion.getDistance(fPos4D), epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FQuaternion fQuaternionRef = TestHelper.getRandFQuaternion();
            FQuaternion fQuaternionArg = TestHelper.getRandFQuaternion(fQuaternionRef);

            double distanceRe = Math.pow(Math.abs(fQuaternionRef.getRe() - fQuaternionArg.getRe()), 2);
            double distanceI = Math.pow(Math.abs(fQuaternionRef.getI() - fQuaternionArg.getI()), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternionRef.getJ() - fQuaternionArg.getJ()), 2);
            double distanceK = Math.pow(Math.abs(fQuaternionRef.getK() - fQuaternionArg.getK()), 2);

            double res = distanceRe + distanceI + distanceJ + distanceK;

            assertEquals(res, fQuaternionRef.getDistanceP2(fQuaternionArg),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion();
            FQuaternion fQuaternionArg = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::getDistanceP2, fQuaternionRef, fQuaternionArg);
        }

        @Test
        @DisplayName("Get distance P2 with primitives")
        void getDistanceP2WithPrimitives() {
            double re = rand.nextDouble();
            double i = rand.nextDouble();
            double j = rand.nextDouble();
            double k = rand.nextDouble();

            FQuaternion fQuaternionRef = TestHelper.getRandFQuaternion();

            double distanceRe = Math.pow(Math.abs(fQuaternionRef.getRe() - re), 2);
            double distanceI = Math.pow(Math.abs(fQuaternionRef.getI() - i), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternionRef.getJ() - j), 2);
            double distanceK = Math.pow(Math.abs(fQuaternionRef.getK() - k), 2);

            double res = distanceRe + distanceI + distanceJ + distanceK;

            assertEquals(res, fQuaternionRef.getDistanceP2(re, i, j, k),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 with FPos4D")
        void getDistanceP2WithFPos4D() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();
            FPos4D fPos4D = TestHelper.getRandFQuaternion(fQuaternion).toFPos4D();

            double distanceRe = Math.pow(Math.abs(fQuaternion.getRe() - fPos4D.getD0()), 2);
            double distanceI = Math.pow(Math.abs(fQuaternion.getI() - fPos4D.getD1()), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternion.getJ() - fPos4D.getD2()), 2);
            double distanceK = Math.pow(Math.abs(fQuaternion.getK() - fPos4D.getD3()), 2);

            double res = distanceRe + distanceI + distanceJ + distanceK;

            assertEquals(res, fQuaternion.getDistanceP2(fPos4D),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();
            double magnitude = Math.abs(rand.nextDouble());

            fQuaternion.setMagnitude(magnitude);

            assertEquals(magnitude, fQuaternion.getMagnitude(),
                    epsilon, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (negative)")
        void setMagnitudeNegative() {
            FQuaternion fQuaternionRef = TestHelper.getRandFQuaternion();
            FQuaternion fQuaternionArg = fQuaternionRef.copy().negate();
            double magnitude = Math.abs(rand.nextDouble());

            fQuaternionRef.setMagnitude(-magnitude);
            fQuaternionArg.setMagnitude(magnitude);

            assertTrue(fQuaternionRef.isSimilar(fQuaternionArg), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (throw IllegalStateException)")
        void setMagnitudeThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class, () -> factory.getFQuaternion().setMagnitude(1),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Set magnitude (validate)")
        void setMagnitudeValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion(1);

            FQuaternionTestHelper.restReference(e -> e.setMagnitude(1), fQuaternion);
        }

        @Test
        @DisplayName("Inverse (simple)")
        public void inverseSimple() {
            double re = 2, i = 3, j = 4, k = 5;
            FQuaternion fComplex = factory.getFQuaternion(re, i, j, k);

            fComplex.inverse();

            Assertions.assertAll("Validate FQuaternion",
                    () -> assertEquals(0.037037037037, fComplex.getRe(),
                            epsilon, "The Re value is erroneous"),
                    () -> assertEquals(-0.055555555556, fComplex.getI(),
                            epsilon, "The I value is erroneous"),
                    () -> assertEquals(-0.074074074074, fComplex.getJ(),
                            epsilon, "The J value is erroneous"),
                    () -> assertEquals(-0.092592592593, fComplex.getK(),
                            epsilon, "The K value is erroneous")
            );
        }

        @Test
        @DisplayName("Inverse (validate)")
        void inverseValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion(1);

            FQuaternionTestHelper.restReference(FQuaternion::inverse, fQuaternion);
        }

        @Test
        @DisplayName("Conjugate")
        public void conjugate() {
            double re = rand.nextDouble();
            double i = rand.nextDouble();
            double j = rand.nextDouble();
            double k = rand.nextDouble();
            FQuaternion fComplex = factory.getFQuaternion(re, i, j, k);

            fComplex.conjugate();

            Assertions.assertAll("Validate FQuaternion",
                    () -> assertEquals(re, fComplex.getRe(),
                            epsilon, "The Re value is erroneous"),
                    () -> assertEquals(-i, fComplex.getI(),
                            epsilon, "The I value is erroneous"),
                    () -> assertEquals(-j, fComplex.getJ(),
                            epsilon, "The J value is erroneous"),
                    () -> assertEquals(-k, fComplex.getK(),
                            epsilon, "The K value is erroneous")
            );
        }

        @Test
        @DisplayName("Conjugate (validate)")
        void conjugateValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(FQuaternion::conjugate, fQuaternion);
        }

        @Test
        @DisplayName("Normalize")
        public void normalize() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            fQuaternion.normalize();

            assertEquals(1, fQuaternion.getMagnitude(),
                    epsilon, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class, () -> factory.getFQuaternion().normalize(),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion(1, 1, 1, 1);

            FQuaternionTestHelper.restReference(FQuaternion::normalize, fQuaternion);
        }

        @Test
        @DisplayName("Is zero")
        public void isZero() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            assertTrue(fQuaternion.isZero(), "The FQuaternion value should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        public void isZeroFail() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            assertFalse(fQuaternion.isZero(), "The FQuaternion value should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::isZero, fQuaternion);
        }

        @Test
        @DisplayName("Power")
        public void pow() {
            FQuaternion fQuaternion = factory.getFQuaternion(3, 4, 5, 6);
            int n = 3;

            FQuaternion res = fQuaternion.copy().mul(fQuaternion).mul(fQuaternion);

            assertTrue(fQuaternion.power(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (negative)")
        public void powNegative() {
            FQuaternion fQuaternion = factory.getFQuaternion(3, 4, 5, 6);
            int n = -3;

            FQuaternion res = factory.getFQuaternion(1)
                    .div(fQuaternion.copy().mul(fQuaternion).mul(fQuaternion));

            assertTrue(fQuaternion.power(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (zero)")
        public void powZero() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            assertTrue(fQuaternion.power(0).isExact(1, 0, 0, 0), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (validate)")
        public void powValidate() {
            FQuaternion fQuaternion = TestHelper.getRandFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.power(3), fQuaternion);
        }
    }

    @Nested
    @Tag("Extension")
    @DisplayName("Extension")
    class FQuaternionExtensionTest {

        @Test
        @DisplayName("Apply")
        void apply() {
            FQuaternion fQuaternion = factory.getFQuaternion(0, 0, 0, 0);

            var fQuaternionRes = fQuaternion.apply(p -> p.setRe(1).setI(2).setJ(3).setK(4));

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(1, fQuaternion.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(2, fQuaternion.getI(), "The 'i' value is incorrect"),
                    () -> assertEquals(3, fQuaternion.getJ(), "The 'j' value is incorrect"),
                    () -> assertEquals(4, fQuaternion.getK(), "The 'k' value is incorrect"),
                    () -> assertSame(fQuaternionRes, fQuaternion, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with double")
        void terminateWithDouble() {
            FQuaternion fQuaternion = factory.getFQuaternion(1, 2, 3, 4);

            var res = fQuaternion.toDouble(p -> {
                p.add(3, 4, 5, 6);
                return p.getRe() + p.getI() + p.getJ() + p.getK();
            });

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(4, fQuaternion.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(6, fQuaternion.getI(), "The 'i' value is incorrect"),
                    () -> assertEquals(8, fQuaternion.getJ(), "The 'j' value is incorrect"),
                    () -> assertEquals(10, fQuaternion.getK(), "The 'k' value is incorrect"),
                    () -> assertEquals(28, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean")
        void terminateWithBoolean() {
            FQuaternion fQuaternion = factory.getFQuaternion(1, 2, 3, 4);

            var res = fQuaternion.toBoolean(p -> {
                p.add(3, 4, 5, 6);
                return p.getRe() + p.getI() + p.getJ() + p.getK() == 28;
            });

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(4, fQuaternion.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(6, fQuaternion.getI(), "The 'i' value is incorrect"),
                    () -> assertEquals(8, fQuaternion.getJ(), "The 'j' value is incorrect"),
                    () -> assertEquals(10, fQuaternion.getK(), "The 'k' value is incorrect"),
                    () -> assertTrue(res, "The value is incorrect")
            );
        }
    }
}

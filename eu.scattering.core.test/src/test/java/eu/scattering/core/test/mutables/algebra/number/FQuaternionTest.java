package eu.scattering.core.test.mutables.algebra.number;

import eu.scattering.core.design.mutables.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.algebra.number.support.FQuaternionTestHelper;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FQuaternion")
public class FQuaternionTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FQuaternionBase {

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
    }

    @Nested
    @Tag("Mutable")
    @DisplayName("Base mutable")
    class BaseMutable {

        private double refRe, refI, refJ, refK;
        private double opRe, opI, opJ, opK;
        private FQuaternion fQuaternion;

        @BeforeEach
        void beforeEach() {
            refRe = random.nextDouble();
            refI = random.nextDouble();
            refJ = random.nextDouble();
            refK = random.nextDouble();

            opRe = random.nextDouble();
            opI = random.nextDouble();
            opJ = random.nextDouble();
            opK = random.nextDouble();

            fQuaternion = factory.getFQuaternion(refRe, refI, refJ, refK);
        }

        @Test
        @DisplayName("Add FQuaternion")
        void addFQuaternion() {
            FQuaternion fQuaternionOp = factory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternion.add(fQuaternionOp);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add FQuaternion (validate)")
        void addFQuaternionValidate() {
            FQuaternion fQuaternionA = factory.getFQuaternion();
            FQuaternion fQuaternionB = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(FQuaternion::add, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fQuaternion.add(opRe, opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.add(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.add(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + op, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + op, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + op, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + op, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.add(0), fQuaternion);
        }

        @Test
        @DisplayName("Add Re")
        void addRe() {

            fQuaternion.addRe(opRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe + opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add Re (validate)")
        void addReValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.addRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Add Im")
        void addIm() {

            fQuaternion.addIm(opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add Im (validate)")
        void addImValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.addIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Add I")
        void addI() {

            fQuaternion.addI(opI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI + opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add I (validate)")
        void addIValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.addI(0), fQuaternion);
        }

        @Test
        @DisplayName("Add J")
        void addJ() {

            fQuaternion.addJ(opJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ + opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add J (validate)")
        void addJValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.addJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Add K")
        void addK() {

            fQuaternion.addK(opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK + opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Add K (validate)")
        void addKValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.addK(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub FQuaternion")
        void subFQuaternion() {
            FQuaternion fQuaternionOp = factory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternion.sub(fQuaternionOp);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FQuaternion (validate)")
        void subFQuaternionValidate() {
            FQuaternion fQuaternionA = factory.getFQuaternion();
            FQuaternion fQuaternionB = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(FQuaternion::sub, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fQuaternion.sub(opRe, opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.sub(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.sub(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - op, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - op, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - op, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - op, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.sub(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub Re")
        void subRe() {

            fQuaternion.subRe(opRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe - opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Re (validate)")
        void subReValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.subRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub Im")
        void subIm() {

            fQuaternion.subIm(opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Im (validate)")
        void subImValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.subIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Sub I")
        void subI() {

            fQuaternion.subI(opI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI - opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub I (validate)")
        void subIValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.subI(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub J")
        void subJ() {

            fQuaternion.subJ(opJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ - opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub J (validate)")
        void subJValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.subJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub K")
        void subK() {

            fQuaternion.subK(opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK - opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Sub K (validate)")
        void subKValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.subK(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul FQuaternion (simple)")
        void mulFQuaternionSimple() {
            double refRe = 2, refI = 3, refJ = 4, refK = 5;
            double opRe = 6, opI = 7, opJ = 8, opK = 9;

            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = factory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternionRef.mul(fQuaternionOp);

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
            FQuaternion fQuaternionA = factory.getFQuaternion();
            FQuaternion fQuaternionB = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(FQuaternion::mul, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fQuaternion.mul(opRe, opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe * opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI * opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ * opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK * opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mul(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.mul(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe * op, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI * op, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ * op, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK * op, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mul(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul Re")
        void mulRe() {

            fQuaternion.mulRe(opRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe * opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Re (validate)")
        void mulReValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mulRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul Im")
        void mulIm() {

            fQuaternion.mulIm(opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI * opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ * opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK * opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Im (validate)")
        void mulImValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mulIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Mul I")
        void mulI() {

            fQuaternion.mulI(opI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI * opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul I (validate)")
        void mulIValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mulI(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul J")
        void mulJ() {

            fQuaternion.mulJ(opJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ * opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul J (validate)")
        void mulJValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mulJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul K")
        void mulK() {

            fQuaternion.mulK(opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK * opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Mul K (validate)")
        void mulKValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.mulK(0), fQuaternion);
        }

        @Test
        @DisplayName("Div FQuaternion (simple)")
        void divFQuaternionSimple() {
            double refRe = 2, refI = 3, refJ = 4, refK = 5;
            double opRe = 6, opI = 7, opJ = 8, opK = 9;

            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = factory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternionRef.div(fQuaternionOp);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(0.478260869565, fQuaternionRef.getRe(),
                            jitter, "The real part is incorrect"),
                    () -> assertEquals(0.034782608696, fQuaternionRef.getI(),
                            jitter, "The imaginary part (I) is incorrect"),
                    () -> assertEquals(0.000000000000, fQuaternionRef.getJ(),
                            jitter, "The imaginary part (J) is incorrect"),
                    () -> assertEquals(0.069565217391, fQuaternionRef.getK(),
                            jitter, "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div FQuaternion (throw ArithmeticException)")
        void divFQuaternionThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fQuaternion.div(factory.getFQuaternion(0, 0, 0, 0)),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div FQuaternion (validate)")
        void divFQuaternionValidate() {
            FQuaternion fQuaternionA = factory.getFQuaternion(1, 2, 3, 4);
            FQuaternion fQuaternionB = factory.getFQuaternion(5, 6, 7, 8);

            FQuaternionTestHelper.restReference(FQuaternion::div, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fQuaternion.div(opRe, opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe / opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI / opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ / opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK / opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(0, 1, 1, 1),
                            "The value of the real part is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(1, 0, 1, 1),
                            "The value of the imaginary part (I) is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(1, 1, 0, 1),
                            "The value of the imaginary part (J) is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(1, 1, 1, 0),
                            "The value of the imaginary part (K) is zero")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.div(1, 1, 1, 1), fQuaternion);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.div(op);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe / op, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI / op, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ / op, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK / op, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fQuaternion.div(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.div(1), fQuaternion);
        }

        @Test
        @DisplayName("Div Re")
        void divRe() {

            fQuaternion.divRe(opRe);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe / opRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div Re (throw ArithmeticException)")
        void divReThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divRe(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Re (validate)")
        void divReValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.divRe(1), fQuaternion);
        }

        @Test
        @DisplayName("Div Im")
        void divIm() {

            fQuaternion.divIm(opI, opJ, opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI / opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ / opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK / opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div Im (throw ArithmeticException)")
        void divImsThrowArithmeticException() {

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.divIm(0, 1, 1),
                            "The value of the imaginary part (I) is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.divIm(1, 0, 1),
                            "The value of the imaginary part (J) is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fQuaternion.divIm(1, 1, 0),
                            "The value of the imaginary part (K) is zero")
            );
        }

        @Test
        @DisplayName("Div Im (validate)")
        void divImValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.divIm(1, 1, 1), fQuaternion);
        }

        @Test
        @DisplayName("Div I")
        void divI() {

            fQuaternion.divI(opI);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI / opI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div I (throw ArithmeticException)")
        void divIThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divI(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div I (validate)")
        void divIValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.divI(1), fQuaternion);
        }

        @Test
        @DisplayName("Div J")
        void divJ() {

            fQuaternion.divJ(opJ);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ / opJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div J (throw ArithmeticException)")
        void divJThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divJ(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div J (validate)")
        void divJValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.divJ(1), fQuaternion);
        }

        @Test
        @DisplayName("Div K")
        void divK() {

            fQuaternion.divK(opK);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The imaginary part (I) is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The imaginary part (J) is incorrect"),
                    () -> assertEquals(refK / opK, fQuaternion.getK(),
                            "The imaginary part (K) is incorrect")
            );
        }

        @Test
        @DisplayName("Div K (throw ArithmeticException)")
        void divKThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divK(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div K (validate)")
        void divKValidate() {
            FQuaternion fQuaternion = factory.getFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.subK(0), fQuaternion);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FQuaternion fComplexRef = factory.getFQuaternion();

            fQuaternion.applyStateTo(fComplexRef);

            Assertions.assertAll("Validate FQuaternion values",
                    () -> assertEquals(refRe, fQuaternion.getRe(),
                            "The reference Re value is incorrect"),
                    () -> assertEquals(refI, fQuaternion.getI(),
                            "The reference I value is incorrect"),
                    () -> assertEquals(refJ, fQuaternion.getJ(),
                            "The reference J value is incorrect"),
                    () -> assertEquals(refK, fQuaternion.getK(),
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

            FQuaternion fQuaternionRef = fQuaternionOp.applyStateTo(fQuaternion);

            Assertions.assertAll("Validate references",
                    () -> assertNotSame(fQuaternion, fQuaternionOp,
                            "FComplex references should change"),
                    () -> assertSame(fQuaternionOp, fQuaternionRef,
                            "The FComplex reference should not change")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class CoreFeatures {

        private double refRe, refI, refJ, refK;

        @BeforeEach
        void beforeEach() {

            refRe = random.nextDouble();
            refI = random.nextDouble();
            refJ = random.nextDouble();
            refK = random.nextDouble();
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = factory.getFQuaternion().applyStateFrom(fQuaternionRef.toJSON());

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
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = factory.getFQuaternion(refRe, refI, refJ, refK);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fQuaternionRef.isExact(fQuaternionOp),
                            "FQuaternion values should be equal"),
                    () -> assertTrue(fQuaternionOp.isExact(fQuaternionRef),
                            "FQuaternion values should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = fQuaternionRef.copy().add(0.5 * jitter);

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fQuaternionRef.isExact(fQuaternionOp),
                            "FQuaternion values should not be equal"),
                    () -> assertFalse(fQuaternionOp.isExact(fQuaternionRef),
                            "FQuaternion values should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = fQuaternionRef.copy().add(0.5 * jitter);

            FQuaternionTestHelper.testValue(FQuaternion::isExact, fQuaternionRef, fQuaternionOp);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);

            assertTrue(fQuaternionRef.isExact(refRe, refI, refJ, refK),
                    "FQuaternion values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);

            assertFalse(fQuaternionRef.isExact(0, 0, 0, 0),
                    "FQuaternion values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(e -> e.isExact(0, 0, 0, 0), fQuaternionRef);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            double ref = jitter * 0.5;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef)),
                            "FQuaternion values should be similar (same position)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addRe(ref)),
                            "FQuaternion values should be similar (positive Re)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subRe(ref)),
                            "FQuaternion values should be similar (negative Re)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addI(ref)),
                            "FQuaternion values should be similar (positive I)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subI(ref)),
                            "FQuaternion values should be similar (negative I)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addJ(ref)),
                            "FQuaternion values should be similar (positive J)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subJ(ref)),
                            "FQuaternion values should be similar (negative J)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addK(ref)),
                            "FQuaternion values should be similar (positive K)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subK(ref)),
                            "FQuaternion values should be similar (negative K)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            double ref = jitter * 2;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addRe(ref)),
                            "FQuaternion values should not be similar (positive Re)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subRe(ref)),
                            "FQuaternion values should not be similar (negative Re)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addI(ref)),
                            "FQuaternion values should not be similar (positive I)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subI(ref)),
                            "FQuaternion values should not be similar (negative I)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addJ(ref)),
                            "FQuaternion values should not be similar (positive J)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subJ(ref)),
                            "FQuaternion values should not be similar (negative J)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).addK(ref)),
                            "FQuaternion values should not be similar (positive K)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(factory.getFQuaternion().add(fQuaternionRef).subK(ref)),
                            "FQuaternion values should not be similar (negative K)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = fQuaternionRef.copy().add(0.5 * jitter);

            FQuaternionTestHelper.testValue(FQuaternion::isSimilar, fQuaternionRef, fQuaternionOp);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            double error = 0.5 * jitter;

            assertTrue(fQuaternionRef.isSimilar(
                    refRe + error,
                    refI + error, refJ + error, refK + error),
                    "FQuaternion values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FQuaternion fQuaternionRef = factory.getFQuaternion(refRe, refI, refJ, refK);
            double error = 0.5 * jitter;

            assertTrue(fQuaternionRef.isSimilar(
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
            FQuaternion fQuaternionRefA = factory.getFQuaternion(refRe, refI, refJ, refK);

            assertNotEquals(fQuaternionRefA.hashCode(), factory.getFQuaternion().hashCode(),
                    "Two different FQuaternion values should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FQuaternion fQuaternionRef = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::hashCode, fQuaternionRef);
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
    class FQuaternionAdvanced {

        @Test
        @DisplayName("Get magnitude")
        void getMagnitude() {
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();

            double resRe = fQuaternion.getRe() * fQuaternion.getRe();
            double resI = fQuaternion.getI() * fQuaternion.getI();
            double resJ = fQuaternion.getJ() * fQuaternion.getJ();
            double resK = fQuaternion.getK() * fQuaternion.getK();

            double res = Math.sqrt(resRe + resI + resJ + resK);

            assertEquals(res, fQuaternion.getMagnitude(), jitter, "The magnitude is erroneous");
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
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();

            double resRe = fQuaternion.getRe() * fQuaternion.getRe();
            double resI = fQuaternion.getI() * fQuaternion.getI();
            double resJ = fQuaternion.getJ() * fQuaternion.getJ();
            double resK = fQuaternion.getK() * fQuaternion.getK();

            double res = resRe + resI + resJ + resK;

            assertEquals(res, fQuaternion.getMagnitudeP2(), jitter, "The magnitude is erroneous");
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
            FQuaternion fQuaternionA = TestHelper.getRandomFQuaternion();
            FQuaternion fQuaternionB = TestHelper.getRandomFQuaternion(fQuaternionA);

            double distanceRe = Math.pow(Math.abs(fQuaternionA.getRe() - fQuaternionB.getRe()), 2);
            double distanceI = Math.pow(Math.abs(fQuaternionA.getI() - fQuaternionB.getI()), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternionA.getJ() - fQuaternionB.getI()), 2);
            double distanceK = Math.pow(Math.abs(fQuaternionA.getK() - fQuaternionB.getK()), 2);

            double res = Math.sqrt(distanceRe + distanceI + distanceJ + distanceK);

            assertEquals(res, fQuaternionA.getDistance(fQuaternionB), jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FQuaternion fQuaternionA = factory.getFQuaternion();
            FQuaternion fQuaternionB = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::getDistance, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FQuaternion fQuaternionA = TestHelper.getRandomFQuaternion();
            FQuaternion fQuaternionB = TestHelper.getRandomFQuaternion(fQuaternionA);

            double distanceRe = Math.pow(Math.abs(fQuaternionA.getRe() - fQuaternionB.getRe()), 2);
            double distanceI = Math.pow(Math.abs(fQuaternionA.getI() - fQuaternionB.getI()), 2);
            double distanceJ = Math.pow(Math.abs(fQuaternionA.getJ() - fQuaternionB.getI()), 2);
            double distanceK = Math.pow(Math.abs(fQuaternionA.getK() - fQuaternionB.getK()), 2);

            double res = distanceRe + distanceI + distanceJ + distanceK;

            assertEquals(res, fQuaternionA.getDistanceP2(fQuaternionB), jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FQuaternion fQuaternionA = factory.getFQuaternion();
            FQuaternion fQuaternionB = factory.getFQuaternion();

            FQuaternionTestHelper.testValue(FQuaternion::getDistanceP2, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();
            double magnitude = Math.abs(random.nextDouble());

            fQuaternion.setMagnitude(magnitude);

            assertEquals(magnitude, fQuaternion.getMagnitude(),
                    jitter, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (negative)")
        void setMagnitudeNegative() {
            FQuaternion fQuaternionA = TestHelper.getRandomFQuaternion();
            FQuaternion fQuaternionB = fQuaternionA.copy().negate();
            double magnitude = Math.abs(random.nextDouble());

            fQuaternionA.setMagnitude(-magnitude);
            fQuaternionB.setMagnitude(magnitude);

            assertTrue(fQuaternionA.isSimilar(fQuaternionB), "The magnitude is erroneous");
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
                            jitter, "The Re value is erroneous"),
                    () -> assertEquals(-0.055555555556, fComplex.getI(),
                            jitter, "The I value is erroneous"),
                    () -> assertEquals(-0.074074074074, fComplex.getJ(),
                            jitter, "The J value is erroneous"),
                    () -> assertEquals(-0.092592592593, fComplex.getK(),
                            jitter, "The K value is erroneous")
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
            double re = random.nextDouble();
            double i = random.nextDouble();
            double j = random.nextDouble();
            double k = random.nextDouble();
            FQuaternion fComplex = factory.getFQuaternion(re, i, j, k);

            fComplex.conjugate();

            Assertions.assertAll("Validate FQuaternion",
                    () -> assertEquals(re, fComplex.getRe(),
                            jitter, "The Re value is erroneous"),
                    () -> assertEquals(-i, fComplex.getI(),
                            jitter, "The I value is erroneous"),
                    () -> assertEquals(-j, fComplex.getJ(),
                            jitter, "The J value is erroneous"),
                    () -> assertEquals(-k, fComplex.getK(),
                            jitter, "The K value is erroneous")
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
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();

            fQuaternion.normalize();

            assertEquals(1, fQuaternion.getMagnitude(),
                    jitter, "The magnitude is erroneous");
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
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();

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
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();

            assertTrue(fQuaternion.power(0).isExact(1, 0, 0, 0), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (validate)")
        public void powValidate() {
            FQuaternion fQuaternion = TestHelper.getRandomFQuaternion();

            FQuaternionTestHelper.restReference(e -> e.power(3), fQuaternion);
        }
    }
}

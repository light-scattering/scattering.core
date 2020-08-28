package eu.scattering.core.design.main.algebra.type;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.type.support.FQuaternionHelper;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            assertNotNull(fQuaternion, "The instance is null");

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion(1, 2, 3, 4);

            assertAll("Validate FQuaternion values",
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
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            fQuaternion.set(1, 2, 3, 4);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            fQuaternion.set(MainFactory.getFQuaternion(1, 2, 3, 4));

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            fQuaternion.setRe(1);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            fQuaternion.setI(1);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            fQuaternion.setJ(1);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            fQuaternion.setK(1);

            assertAll("Validate FQuaternion values",
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
    @Tag("Algebra")
    @DisplayName("Base algebra")
    class BaseAlgebra {

        private double refRe, refI, refJ, refK;
        private double opRe, opI, opJ, opK;
        private FQuaternion fQuaternion;

        @BeforeEach
        void beforeEach() {
            refRe = RandomHelper.getTestValue();
            refI = RandomHelper.getTestValue();
            refJ = RandomHelper.getTestValue();
            refK = RandomHelper.getTestValue();

            opRe = RandomHelper.getTestValue();
            opI = RandomHelper.getTestValue();
            opJ = RandomHelper.getTestValue();
            opK = RandomHelper.getTestValue();

            fQuaternion = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
        }

        @Test
        @DisplayName("Add FQuaternion")
        void addFQuaternion() {
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternion.add(fQuaternionOp);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternionA = MainFactory.getFQuaternion();
            FQuaternion fQuaternionB = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::add, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fQuaternion.add(opRe, opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.add(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.add(op);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.add(0), fQuaternion);
        }

        @Test
        @DisplayName("Add Re")
        void addRe() {

            fQuaternion.addRe(opRe);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.addRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Add Im")
        void addIm() {

            fQuaternion.addIm(opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.addIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Add I")
        void addI() {

            fQuaternion.addI(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.addI(0), fQuaternion);
        }

        @Test
        @DisplayName("Add J")
        void addJ() {

            fQuaternion.addJ(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.addJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Add K")
        void addK() {

            fQuaternion.addK(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.addK(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub FQuaternion")
        void subFQuaternion() {
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternion.sub(fQuaternionOp);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternionA = MainFactory.getFQuaternion();
            FQuaternion fQuaternionB = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::sub, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fQuaternion.sub(opRe, opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.sub(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.sub(op);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.sub(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub Re")
        void subRe() {

            fQuaternion.subRe(opRe);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.subRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub Im")
        void subIm() {

            fQuaternion.subIm(opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.subIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Sub I")
        void subI() {

            fQuaternion.subI(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.subI(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub J")
        void subJ() {

            fQuaternion.subJ(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.subJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Sub K")
        void subK() {

            fQuaternion.addK(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.subK(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul FQuaternion")
        void mulFQuaternion() {
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternion.mul(fQuaternionOp);

            assertAll("Validate FQuaternion values",
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
        @DisplayName("Mul FQuaternion (validate)")
        void mulFQuaternionValidate() {
            FQuaternion fQuaternionA = MainFactory.getFQuaternion();
            FQuaternion fQuaternionB = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::mul, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fQuaternion.mul(opRe, opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mul(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.mul(op);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mul(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul Re")
        void mulRe() {

            fQuaternion.mulRe(opRe);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mulRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul Im")
        void mulIm() {

            fQuaternion.mulIm(opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mulIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Mul I")
        void mulI() {

            fQuaternion.mulI(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mulI(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul J")
        void mulJ() {

            fQuaternion.mulJ(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mulJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Mul K")
        void mulK() {

            fQuaternion.addK(opI);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.mulK(0), fQuaternion);
        }

        @Test
        @DisplayName("Div FQuaternion")
        void divFQuaternion() {
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion(opRe, opI, opJ, opK);

            fQuaternion.div(fQuaternionOp);

            assertAll("Validate FQuaternion values",
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
        @DisplayName("Div FQuaternion (throw ArithmeticException)")
        void divFQuaternionThrowArithmeticException() {

            assertThrows(ArithmeticException.class,
                    () -> fQuaternion.div(MainFactory.getFQuaternion(0, 0, 0, 0)),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div FQuaternion (validate)")
        void divFQuaternionValidate() {
            FQuaternion fQuaternionA = MainFactory.getFQuaternion();
            FQuaternion fQuaternionB = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::div, fQuaternionA, fQuaternionB);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fQuaternion.div(opRe, opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(0, 1, 1, 1),
                            "The value of the real part is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(1, 0, 1, 1),
                            "The value of the imaginary part (I) is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(1, 1, 0, 1),
                            "The value of the imaginary part (J) is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.div(1, 1, 1, 0),
                            "The value of the imaginary part (K) is zero")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.div(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opRe * opI * opJ * opK;

            fQuaternion.div(op);

            assertAll("Validate FQuaternion values",
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

            assertThrows(ArithmeticException.class,
                    () -> fQuaternion.div(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.div(0), fQuaternion);
        }

        @Test
        @DisplayName("Div Re")
        void divRe() {

            fQuaternion.divRe(opRe);

            assertAll("Validate FQuaternion values",
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

            assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divRe(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Re (validate)")
        void divReValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.divRe(0), fQuaternion);
        }

        @Test
        @DisplayName("Div Im")
        void divIm() {

            fQuaternion.divIm(opI, opJ, opK);

            assertAll("Validate FQuaternion values",
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

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.divIm(0, 1, 1),
                            "The value of the imaginary part (I) is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.divIm(1, 0, 1),
                            "The value of the imaginary part (J) is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fQuaternion.divIm(1, 1, 0),
                            "The value of the imaginary part (K) is zero")
            );
        }

        @Test
        @DisplayName("Div Im (validate)")
        void divImValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.divIm(0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Div I")
        void divI() {

            fQuaternion.divI(opI);

            assertAll("Validate FQuaternion values",
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

            assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divI(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div I (validate)")
        void divIValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.divI(0), fQuaternion);
        }

        @Test
        @DisplayName("Div J")
        void divJ() {

            fQuaternion.divJ(opI);

            assertAll("Validate FQuaternion values",
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

            assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divJ(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div J (validate)")
        void divJValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.divJ(0), fQuaternion);
        }

        @Test
        @DisplayName("Div K")
        void divK() {

            fQuaternion.divK(opI);

            assertAll("Validate FQuaternion values",
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

            assertThrows(ArithmeticException.class,
                    () -> fQuaternion.divK(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div K (validate)")
        void divKValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.subK(0), fQuaternion);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FQuaternion fComplexRef = MainFactory.getFQuaternion();

            fQuaternion.imprint(fComplexRef);

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion();

            FQuaternion fQuaternionRef = fQuaternionOp.imprint(fQuaternion);

            assertAll("Validate references",
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

            refRe = RandomHelper.getTestValue();
            refI = RandomHelper.getTestValue();
            refJ = RandomHelper.getTestValue();
            refK = RandomHelper.getTestValue();
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion().importFromJSON(fQuaternionRef.exportToJSON());

            assertAll("Validate FQuaternion values",
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
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = MainFactory.getFQuaternion(refRe, refI, refJ, refK);

            assertAll("Validate exactness",
                    () -> assertTrue(fQuaternionRef.isExact(fQuaternionOp),
                            "FQuaternion values should be equal"),
                    () -> assertTrue(fQuaternionOp.isExact(fQuaternionRef),
                            "FQuaternion values should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = fQuaternionRef.copy().add(0.5 * Config.getJitter());

            assertAll("Validate exactness",
                    () -> assertFalse(fQuaternionRef.isExact(fQuaternionOp),
                            "FQuaternion values should not be equal"),
                    () -> assertFalse(fQuaternionOp.isExact(fQuaternionRef),
                            "FQuaternion values should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = fQuaternionRef.copy().add(0.5 * Config.getJitter());

            FQuaternionHelper.validateVal(FQuaternion::isExact, fQuaternionRef, fQuaternionOp);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);

            assertTrue(fQuaternionRef.isExact(refRe, refI, refJ, refK),
                    "FQuaternion values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);

            assertTrue(fQuaternionRef.isExact(0, 0, 0, 0),
                    "FQuaternion values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion();

            FQuaternionHelper.validateVal(e -> e.isExact(0, 0, 0, 0), fQuaternionRef);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            double ref = Config.getJitter() * 0.5;

            assertAll("Check combinations (true)",
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef)),
                            "FQuaternion values should be similar (same position)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addRe(ref)),
                            "FQuaternion values should be similar (positive Re)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subRe(ref)),
                            "FQuaternion values should be similar (negative Re)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addI(ref)),
                            "FQuaternion values should be similar (positive I)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subI(ref)),
                            "FQuaternion values should be similar (negative I)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addJ(ref)),
                            "FQuaternion values should be similar (positive J)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subJ(ref)),
                            "FQuaternion values should be similar (negative J)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addK(ref)),
                            "FQuaternion values should be similar (positive K)"),
                    () -> assertTrue(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subK(ref)),
                            "FQuaternion values should be similar (negative K)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            double ref = Config.getJitter() * 2;

            assertAll("Check combinations (true)",
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addRe(ref)),
                            "FQuaternion values should not be similar (positive Re)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subRe(ref)),
                            "FQuaternion values should not be similar (negative Re)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addI(ref)),
                            "FQuaternion values should not be similar (positive I)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subI(ref)),
                            "FQuaternion values should not be similar (negative I)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addJ(ref)),
                            "FQuaternion values should not be similar (positive J)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subJ(ref)),
                            "FQuaternion values should not be similar (negative J)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).addK(ref)),
                            "FQuaternion values should not be similar (positive K)"),
                    () -> assertFalse(fQuaternionRef
                                    .isSimilar(MainFactory.getFQuaternion().add(fQuaternionRef).subK(ref)),
                            "FQuaternion values should not be similar (negative K)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionOp = fQuaternionRef.copy().add(0.5 * Config.getJitter());

            FQuaternionHelper.validateVal(FQuaternion::isSimilar, fQuaternionRef, fQuaternionOp);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            double jitter = 0.5 * Config.getJitter();

            assertTrue(fQuaternionRef.isSimilar(
                    refRe + jitter,
                    refI + jitter, refJ + jitter, refK + jitter),
                    "FQuaternion values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            double jitter = 1.5 * Config.getJitter();

            assertTrue(fQuaternionRef.isSimilar(
                    refRe + jitter,
                    refI + jitter, refJ + jitter, refK + jitter),
                    "FQuaternion values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateVal(e -> e.isSimilar(0, 0, 0, 0), fQuaternion);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FQuaternion fQuaternionRefA = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternionRefB = MainFactory.getFQuaternion(refRe, refI, refJ, refK);

            assertEquals(fQuaternionRefA.hashCode(), fQuaternionRefB.hashCode(),
                    "Two identical FQuaternion values should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FQuaternion fQuaternionRefA = MainFactory.getFQuaternion(refRe, refI, refJ, refK);

            assertNotEquals(fQuaternionRefA.hashCode(), MainFactory.getFQuaternion().hashCode(),
                    "Two different FQuaternion values should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion();

            FQuaternionHelper.validateVal(FQuaternion::hashCode, fQuaternionRef);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FQuaternion fQuaternionRef = MainFactory.getFQuaternion(refRe, refI, refJ, refK);
            FQuaternion fQuaternion = fQuaternionRef.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fQuaternionRef, fQuaternion,
                            "FQuaternion objects contain different values"),
                    () -> assertEquals(fQuaternionRef, fQuaternion,
                            "FQuaternion values should be the same"),
                    () -> assertNotEquals(fQuaternionRef, fQuaternion.add(fQuaternionRef),
                            "FQuaternion values should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateVal(FQuaternion::copy, fQuaternion);
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FQuaternionAdvanced {

        @Test
        @DisplayName("Get magnitude")
        void getMagnitude() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            double resRe = fQuaternion.getRe() * fQuaternion.getRe();
            double resI = fQuaternion.getI() * fQuaternion.getI();
            double resJ = fQuaternion.getJ() * fQuaternion.getJ();
            double resK = fQuaternion.getK() * fQuaternion.getK();

            double res = Math.sqrt(resRe + resI + resJ + resK);

            assertEquals(res, fQuaternion.getMagnitude(), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude (validate)")
        void getMagnitudeValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateVal(FQuaternion::getMagnitude, fQuaternion);
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();
            double magnitude = Math.abs(RandomHelper.getTestValue());

            fQuaternion.setMagnitude(magnitude);

            assertEquals(magnitude, fQuaternion.getMagnitude(),
                    Config.getJitter(), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (negative)")
        void setMagnitudeNegative() {
            FQuaternion fQuaternionA = RandomHelper.getTestQuaternion();
            FQuaternion fQuaternionB = fQuaternionA.copy().inverse();
            double magnitude = Math.abs(RandomHelper.getTestValue());

            fQuaternionA.setMagnitude(-magnitude);
            fQuaternionB.setMagnitude(magnitude);

            assertTrue(fQuaternionA.isSimilar(fQuaternionB), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (throw IllegalStateException)")
        void setMagnitudeThrowIllegalStateException() {

            assertThrows(IllegalStateException.class, () -> MainFactory.getFQuaternion().setMagnitude(1),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Set magnitude (validate)")
        void setMagnitudeValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(e -> e.setMagnitude(1), fQuaternion);
        }

        @Test
        @DisplayName("Inverse")
        public void inverse() {
            double re = RandomHelper.getTestValue();
            double i = RandomHelper.getTestValue();
            double j = RandomHelper.getTestValue();
            double k = RandomHelper.getTestValue();
            FQuaternion fComplex = MainFactory.getFQuaternion(re, i, j, k);

            fComplex.inverse();

            assertAll("Validate FQuaternion",
                    () -> assertEquals(-re, fComplex.getRe(),
                            Config.getJitter(), "The Re value is erroneous"),
                    () -> assertEquals(-i, fComplex.getI(),
                            Config.getJitter(), "The I value is erroneous"),
                    () -> assertEquals(-j, fComplex.getJ(),
                            Config.getJitter(), "The J value is erroneous"),
                    () -> assertEquals(-k, fComplex.getK(),
                            Config.getJitter(), "The K value is erroneous")
            );
        }

        @Test
        @DisplayName("Inverse (validate)")
        void inverseValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::inverse, fQuaternion);
        }

        @Test
        @DisplayName("Conjugate")
        public void conjugate() {
            double re = RandomHelper.getTestValue();
            double i = RandomHelper.getTestValue();
            double j = RandomHelper.getTestValue();
            double k = RandomHelper.getTestValue();
            FQuaternion fComplex = MainFactory.getFQuaternion(re, i, j, k);

            fComplex.conjugate();

            assertAll("Validate FQuaternion",
                    () -> assertEquals(re, fComplex.getRe(),
                            Config.getJitter(), "The Re value is erroneous"),
                    () -> assertEquals(-i, fComplex.getI(),
                            Config.getJitter(), "The I value is erroneous"),
                    () -> assertEquals(-j, fComplex.getJ(),
                            Config.getJitter(), "The J value is erroneous"),
                    () -> assertEquals(-k, fComplex.getK(),
                            Config.getJitter(), "The K value is erroneous")
            );
        }

        @Test
        @DisplayName("Conjugate (validate)")
        void conjugateValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::conjugate, fQuaternion);
        }

        @Test
        @DisplayName("Normalize")
        public void normalize() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            fQuaternion.normalize();

            assertEquals(1, fQuaternion.getMagnitude(),
                    Config.getJitter(), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {

            assertThrows(IllegalStateException.class, () -> MainFactory.getFQuaternion().normalize(),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        void normalizeValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateRef(FQuaternion::normalize, fQuaternion);
        }

        @Test
        @DisplayName("Is zero")
        public void isZero() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            assertTrue(fQuaternion.isZero(), "The FQuaternion value should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        public void isZeroFail() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            assertFalse(fQuaternion.isZero(), "The FQuaternion value should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        void isZeroValidate() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion();

            FQuaternionHelper.validateVal(FQuaternion::isZero, fQuaternion);
        }

        @Test
        @DisplayName("Power")
        public void pow() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion(3, 4, 5, 6);
            int n = 3;

            FQuaternion res = fQuaternion.copy().mul(fQuaternion).mul(fQuaternion);

            assertTrue(fQuaternion.pow(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (negative)")
        public void powNegative() {
            FQuaternion fQuaternion = MainFactory.getFQuaternion(3, 4, 5, 6);
            int n = -3;

            FQuaternion res = MainFactory.getFQuaternion(1, 0, 0, 0)
                    .div(fQuaternion.copy().mul(fQuaternion).mul(fQuaternion));

            assertTrue(fQuaternion.pow(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (zero)")
        public void powZero() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            assertTrue(fQuaternion.pow(0).isExact(1, 0, 0, 0), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (validate)")
        public void powValidate() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            FQuaternionHelper.validateRef(e -> e.pow(3), fQuaternion);
        }

        @Test
        @DisplayName("Root")
        public void root() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            FQuaternion[] fQuaternionRes = fQuaternion.root(3);

            assertAll("Validate root values",
                    () -> assertTrue(fQuaternion.isSimilar(fQuaternionRes[0].pow(3)),
                            "The root value 0 is erroneous"),
                    () -> assertTrue(fQuaternion.isSimilar(fQuaternionRes[1].pow(3)),
                            "The root value 1 is erroneous"),
                    () -> assertTrue(fQuaternion.isSimilar(fQuaternionRes[2].pow(3)),
                            "The root value 2 is erroneous")
            );
        }

        @Test
        @DisplayName("Root (size)")
        public void rootSize() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            FQuaternion[] fComplexRes = fQuaternion.root(3);

            assertEquals(3, fComplexRes.length,
                    "The number of root values is erroneous");
        }

        @Test
        @DisplayName("Root (throw IllegalArgumentException)")
        public void rootThrowIllegalArgumentException() {

            assertThrows(IllegalArgumentException.class, () -> RandomHelper.getTestQuaternion().root(-1),
                    "The root value must be greater than zero");
        }

        @Test
        @DisplayName("Root (validate)")
        public void rootValidate() {
            FQuaternion fQuaternion = RandomHelper.getTestQuaternion();

            FQuaternionHelper.validateVal(e -> e.root(3), fQuaternion);
        }
    }
}

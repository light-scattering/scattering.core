package eu.scattering.core.design.main.algebra.type;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.support.FComplexHelper;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.support.helper.RandomHelper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FComplex")
public class FComplexTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FComplexBase {

        @Test
        @DisplayName("Construct")
        void construct() {
            FComplex fComplex = MainFactory.getFComplex();

            assertNotNull(fComplex, "The instance is null");

            assertAll("Validate FComplex values",
                    () -> assertEquals(0, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with parameters")
        void constructWithParameters() {
            FComplex fComplex = MainFactory.getFComplex(1, 2);

            assertAll("Validate FComplex values",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            FComplex fComplex = MainFactory.getFComplex();

            fComplex.set(1, 2);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FComplex")
        void setWithFComplex() {
            FComplex fComplex = MainFactory.getFComplex();

            fComplex.set(MainFactory.getFComplex(1, 2));

            assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set Re")
        void setRe() {
            FComplex fComplex = MainFactory.getFComplex();

            fComplex.setRe(1);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set Im")
        void setIm() {
            FComplex fComplex = MainFactory.getFComplex();

            fComplex.setIm(1);

            assertAll("Updated values are incorrect",
                    () -> assertEquals(0, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(1, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }
    }

    @Nested
    @Tag("Algebra")
    @DisplayName("Base algebra")
    class BaseAlgebra {

        private double refRe, refIm;
        private double opRe, opIm;
        private FComplex fComplex;

        @BeforeEach
        void beforeEach() {
            refRe = RandomHelper.getTestValue();
            refIm = RandomHelper.getTestValue();

            opRe = RandomHelper.getTestValue();
            opIm = RandomHelper.getTestValue();

            fComplex = MainFactory.getFComplex(refRe, refIm);
        }

        @Test
        @DisplayName("Add FComplex")
        void addFComplex() {
            FComplex fComplexOp = MainFactory.getFComplex(opRe, opIm);

            fComplex.add(fComplexOp);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add FComplex (validate)")
        void addFComplexValidate() {
            FComplex fComplexA = MainFactory.getFComplex();
            FComplex fComplexB = MainFactory.getFComplex();

            FComplexHelper.validateRef(FComplex::add, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fComplex.add(opRe, opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.add(0, 0), fComplex);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opRe * opIm;

            fComplex.add(op);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + op, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.add(1), fComplex);
        }

        @Test
        @DisplayName("Add Re")
        void addRe() {

            fComplex.addRe(opRe);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add Re (validate)")
        void addReValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.addRe(1), fComplex);
        }

        @Test
        @DisplayName("Add Im")
        void addIm() {

            fComplex.addIm(opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add Im (validate)")
        void addImValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.addIm(1), fComplex);
        }

        @Test
        @DisplayName("Sub FComplex")
        void subFComplex() {
            FComplex fComplexOp = MainFactory.getFComplex(opRe, opIm);

            fComplex.sub(fComplexOp);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FComplex (validate)")
        void subFComplexValidate() {
            FComplex fComplexA = MainFactory.getFComplex();
            FComplex fComplexB = MainFactory.getFComplex();

            FComplexHelper.validateRef(FComplex::sub, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fComplex.sub(opRe, opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.sub(0, 0), fComplex);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opRe * opIm;

            fComplex.sub(op);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - op, fComplex.getIm(),
                            "The imaginary part  is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.sub(1), fComplex);
        }

        @Test
        @DisplayName("Sub Re")
        void subRe() {

            fComplex.subRe(opRe);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Re (validate)")
        void subReValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.subRe(1), fComplex);
        }

        @Test
        @DisplayName("Sub Im")
        void subIm() {

            fComplex.subIm(opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Im (validate)")
        void subImValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.subIm(1), fComplex);
        }

        @Test
        @DisplayName("Mul FComplex")
        void mulFComplex() {
            FComplex fComplexOp = MainFactory.getFComplex(opRe, opIm);

            fComplex.mul(fComplexOp);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FComplex (validate)")
        void mulFComplexValidate() {
            FComplex fComplexA = MainFactory.getFComplex(1, 1);
            FComplex fComplexB = MainFactory.getFComplex(1, 1);

            FComplexHelper.validateRef(FComplex::mul, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fComplex.add(opRe, opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.mul(1, 1), fComplex);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opRe * opIm;

            fComplex.mul(op);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * op, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.mul(1), fComplex);
        }

        @Test
        @DisplayName("Mul Re")
        void mulRe() {

            fComplex.mulRe(opRe);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Re (validate)")
        void mulReValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.mulRe(1), fComplex);
        }

        @Test
        @DisplayName("Mul Im")
        void mulIm() {

            fComplex.mulIm(opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Im (validate)")
        void mulImValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.mulIm(1), fComplex);
        }

        @Test
        @DisplayName("Div FComplex")
        void divFComplex() {
            FComplex fComplexOp = MainFactory.getFComplex(opRe, opIm);

            fComplex.div(fComplexOp);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div FComplex (throw ArithmeticException)")
        void divFComplexThrowArithmeticException() {

            assertThrows(ArithmeticException.class,
                    () -> fComplex.div(MainFactory.getFComplex(0, 0)),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div FComplex (validate)")
        void divFComplexValidate() {
            FComplex fComplexA = MainFactory.getFComplex(1, 1);
            FComplex fComplexB = MainFactory.getFComplex(1, 1);

            FComplexHelper.validateRef(FComplex::div, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fComplex.div(opRe, opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {

            assertAll("Division by zero",
                    () -> assertThrows(ArithmeticException.class,
                            () -> fComplex.div(0, 1),
                            "The value of the real part is zero"),
                    () -> assertThrows(ArithmeticException.class,
                            () -> fComplex.div(1, 0),
                            "The value of the imaginary part is zero")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.div(1, 1), fComplex);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opRe * opIm;

            fComplex.div(op);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / op, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {

            assertThrows(ArithmeticException.class,
                    () -> fComplex.div(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.div(1), fComplex);
        }

        @Test
        @DisplayName("Div Re")
        void divRe() {

            fComplex.divRe(opRe);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div Re (throw ArithmeticException)")
        void divReThrowArithmeticException() {

            assertThrows(ArithmeticException.class,
                    () -> fComplex.divRe(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Re (validate)")
        void divReValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.divRe(1), fComplex);
        }

        @Test
        @DisplayName("Div Im")
        void divIm() {

            fComplex.divIm(opIm);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div Im (throw ArithmeticException)")
        void divImThrowArithmeticException() {

            assertThrows(ArithmeticException.class,
                    () -> fComplex.divIm(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Im (validate)")
        void divImValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.divIm(1), fComplex);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FComplex fComplexRef = MainFactory.getFComplex();

            fComplex.imprint(fComplexRef);

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The reference Re value is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The reference Im value is incorrect"),
                    () -> assertEquals(refRe, fComplexRef.getRe(),
                            "The Re value is incorrect"),
                    () -> assertEquals(refIm, fComplexRef.getIm(),
                            "The Im value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FComplex fComplexOp = MainFactory.getFComplex();

            FComplex fComplexRef = fComplexOp.imprint(fComplex);

            assertAll("Validate references",
                    () -> assertNotSame(fComplex, fComplexOp,
                            "FComplex references should change"),
                    () -> assertSame(fComplexOp, fComplexRef,
                            "The FComplex reference should not change")
            );
        }

    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class CoreFeatures {

        private double refRe, refIm;

        @BeforeEach
        void beforeEach() {

            refRe = RandomHelper.getTestValue();
            refIm = RandomHelper.getTestValue();
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplexOp = MainFactory.getFComplex().importFromJSON(fComplexRef.exportToJSON());

            assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplexOp.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplexOp.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplexOp = MainFactory.getFComplex(refRe, refIm);

            assertAll("Validate exactness",
                    () -> assertTrue(fComplexRef.isExact(fComplexOp),
                            "FComplex values should be equal"),
                    () -> assertTrue(fComplexOp.isExact(fComplexRef),
                            "FComplex values should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplexOp = fComplexRef.copy().add(0.5 * Config.getJitter());

            assertAll("Validate exactness",
                    () -> assertFalse(fComplexRef.isExact(fComplexOp),
                            "FComplex values should not be equal"),
                    () -> assertFalse(fComplexOp.isExact(fComplexRef),
                            "FComplex values should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplexOp = MainFactory.getFComplex(refRe, refIm);

            FComplexHelper.validateVal(FComplex::isExact, fComplexRef, fComplexOp);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);

            assertTrue(fComplexRef.isExact(refRe, refIm), "FComplex values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);

            assertFalse(fComplexRef.isExact(0, 0),
                    "FComplex values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateVal(e -> e.isExact(0, 0), fComplex);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            double ref = Config.getJitter() * 0.5;

            assertAll("Check combinations (true)",
                    () -> assertTrue(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef)),
                            "FComplex values should be similar (same position)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).addRe(ref)),
                            "FComplex values should be similar (positive Re)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).subRe(ref)),
                            "FComplex values should be similar (negative Re)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).addIm(ref)),
                            "FComplex values should be similar (positive Im)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).subIm(ref)),
                            "FComplex values should be similar (negative Im)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            double ref = Config.getJitter() * 2;

            assertAll("Check combinations (true)",
                    () -> assertFalse(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).addRe(ref)),
                            "FComplex values should not be similar (positive Re)"),
                    () -> assertFalse(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).subRe(ref)),
                            "FComplex values should not be similar (negative Re)"),
                    () -> assertFalse(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).addIm(ref)),
                            "FComplex values should not be similar (positive Im)"),
                    () -> assertFalse(fComplexRef
                                    .isSimilar(MainFactory.getFComplex().add(fComplexRef).subIm(ref)),
                            "FComplex values should not be similar (negative Im)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplexOp = MainFactory.getFComplex(refRe, refIm);

            FComplexHelper.validateVal(FComplex::isSimilar, fComplexRef, fComplexOp);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            double jitter = 0.5 * Config.getJitter();

            assertTrue(fComplexRef.isSimilar(
                    refRe + jitter,
                    refIm + jitter),
                    "FComplex values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);

            assertFalse(fComplexRef.isSimilar(
                    refRe + (1.5 * Config.getJitter()),
                    refRe + (1.5 * Config.getJitter())),
                    "FComplex values should not be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateVal(e -> e.isSimilar(0, 0), fComplex);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FComplex fComplexRefA = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplexRefB = MainFactory.getFComplex(refRe, refIm);

            assertEquals(fComplexRefA.hashCode(), fComplexRefB.hashCode(),
                    "Two identical FComplex values should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FComplex fComplexRefA = MainFactory.getFComplex(refRe, refIm);

            assertNotEquals(fComplexRefA.hashCode(), MainFactory.getFComplex().hashCode(),
                    "Two different FComplex values should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FComplex fComplexRef = MainFactory.getFComplex();

            FComplexHelper.validateVal(FComplex::hashCode, fComplexRef);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FComplex fComplexRef = MainFactory.getFComplex(refRe, refIm);
            FComplex fComplex = fComplexRef.copy();

            assertAll("Validate copy",
                    () -> assertNotSame(fComplexRef, fComplex,
                            "FComplex objects contain different values"),
                    () -> assertEquals(fComplexRef, fComplex,
                            "FComplex values should be the same"),
                    () -> assertNotEquals(fComplexRef, fComplex.add(fComplexRef),
                            "FComplex values should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateVal(FComplex::copy, fComplex);
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FComplexAdvanced {

        @Test
        @DisplayName("Get magnitude")
        void getMagnitude() {
            FComplex fComplex = RandomHelper.getTestComplex();

            double res = Math.sqrt((fComplex.getRe() * fComplex.getRe()) + (fComplex.getIm() * fComplex.getIm()));

            assertEquals(res, fComplex.getMagnitude(), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude (validate)")
        void getMagnitudeValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateVal(FComplex::getMagnitude, fComplex);
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            FComplex fComplex = RandomHelper.getTestComplex();
            double magnitude = Math.abs(RandomHelper.getTestValue());

            fComplex.setMagnitude(magnitude);

            assertEquals(magnitude, fComplex.getMagnitude(),
                    Config.getJitter(), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (negative)")
        void setMagnitudeNegative() {
            FComplex fComplexA = RandomHelper.getTestComplex();
            FComplex fComplexB = fComplexA.copy().inverse();
            double magnitude = Math.abs(RandomHelper.getTestValue());

            fComplexA.setMagnitude(-magnitude);
            fComplexB.setMagnitude(magnitude);

            assertTrue(fComplexA.isSimilar(fComplexB), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (throw IllegalStateException)")
        void setMagnitudeThrowIllegalStateException() {

            assertThrows(IllegalStateException.class, () -> MainFactory.getFComplex().setMagnitude(1),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Set magnitude (validate)")
        void setMagnitudeValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateRef(e -> e.setMagnitude(1), fComplex);
        }

        @Test
        @DisplayName("Get phase")
        void getPhase() {
            double re = RandomHelper.getTestValue();
            double im = RandomHelper.getTestValue();

            FComplex fComplex = MainFactory.getFComplex(re, im);

            assertNotEquals(Math.PI, fComplex.getPhase(),
                    Config.getJitter(), "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (zero)")
        void getPhaseZero() {
            double re = Math.abs(RandomHelper.getTestValue());
            double im = 0;

            FComplex fComplex = MainFactory.getFComplex(re, im);

            assertEquals(0, fComplex.getPhase(),
                    Config.getJitter(), "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (pi)")
        void getPhasePi() {
            double re = -Math.abs(RandomHelper.getTestValue());
            double im = 0;

            FComplex fComplex = MainFactory.getFComplex(re, im);

            assertEquals(Math.PI, fComplex.getPhase(),
                    Config.getJitter(), "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (simple A)")
        void getPhaseSimpleA () {
            double re = 1;
            double im = 1;

            FComplex fComplex = MainFactory.getFComplex(re, im);

            assertEquals(Math.PI * +0.25, fComplex.getPhase(),
                    Config.getJitter(), "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (simple B)")
        void getPhaseSimpleB () {
            double re = 1;
            double im = -1;

            FComplex fComplex = MainFactory.getFComplex(re, im);

            assertEquals(Math.PI * -0.25, fComplex.getPhase(),
                    Config.getJitter(), "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (throw IllegalStateException)")
        void getPhaseThrowIllegalStateException() {

            assertThrows(IllegalStateException.class, () -> MainFactory.getFComplex().getPhase(),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Get phase (validate)")
        void getPhaseValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateVal(FComplex::getPhase, fComplex);
        }

        @Test
        @DisplayName("Set phase")
        void setPhase() {
            FComplex fComplex = MainFactory.getFComplex(1, 1);
            double phase = RandomHelper.getTestValue() % Math.PI;

            fComplex.setPhase(phase);

            assertEquals(phase, fComplex.getPhase(),
                    Config.getJitter(), "The phase is erroneous");
        }

        @Test
        @DisplayName("Set phase (validate)")
        void setPhaseValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateRef(e -> e.setPhase(1), fComplex);
        }

        @Test
        @DisplayName("Inverse")
        public void inverse() {
            double re = RandomHelper.getTestValue();
            double im = RandomHelper.getTestValue();
            FComplex fComplex = MainFactory.getFComplex(re, im);

            fComplex.inverse();

            assertAll("Validate FComplex",
                    () -> assertEquals(-re, fComplex.getRe(),
                            Config.getJitter(), "The Re value is erroneous"),
                    () -> assertEquals(-im, fComplex.getIm(),
                            Config.getJitter(), "The Im value is erroneous")
            );
        }

        @Test
        @DisplayName("Inverse (validate)")
        public void inverseValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateRef(FComplex::inverse, fComplex);
        }

        @Test
        @DisplayName("Conjugate")
        public void conjugate() {
            double re = RandomHelper.getTestValue();
            double im = RandomHelper.getTestValue();
            FComplex fComplex = MainFactory.getFComplex(re, im);

            fComplex.conjugate();

            assertAll("Validate FComplex",
                    () -> assertEquals(re, fComplex.getRe(),
                            Config.getJitter(), "The Re value is erroneous"),
                    () -> assertEquals(-im, fComplex.getIm(),
                            Config.getJitter(), "The Im value is erroneous"));
        }

        @Test
        @DisplayName("Conjugate (validate)")
        public void conjugateValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateRef(FComplex::conjugate, fComplex);
        }

        @Test
        @DisplayName("Normalize")
        public void normalize() {
            FComplex fComplex = RandomHelper.getTestComplex();

            fComplex.normalize();

            assertEquals(1, fComplex.getMagnitude(),
                    Config.getJitter(), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {

            assertThrows(IllegalStateException.class, () -> MainFactory.getFComplex().normalize(),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        public void normalizeValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateRef(FComplex::normalize, fComplex);
        }

        @Test
        @DisplayName("Is zero")
        public void isZero() {
            FComplex fComplex = MainFactory.getFComplex();

            assertTrue(fComplex.isZero(), "The FComplex value should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        public void isZeroFail() {
            FComplex fComplex = RandomHelper.getTestComplex();

            assertFalse(fComplex.isZero(), "The FComplex value should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        public void isZeroValidate() {
            FComplex fComplex = MainFactory.getFComplex();

            FComplexHelper.validateVal(FComplex::isZero, fComplex);
        }

        @Test
        @DisplayName("Power")
        public void pow() {
            FComplex fComplex = MainFactory.getFComplex(3, 4);
            int n = 3;

            FComplex res = fComplex.copy().mul(fComplex).mul(fComplex);

            assertTrue(fComplex.pow(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (negative)")
        public void powNegative() {
            FComplex fComplex = MainFactory.getFComplex(3, 4);
            int n = -3;

            FComplex res = MainFactory.getFComplex(1, 0)
                    .div(fComplex.copy().mul(fComplex).mul(fComplex));

            assertTrue(fComplex.pow(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (zero)")
        public void powZero() {
            FComplex fComplex = RandomHelper.getTestComplex();

            assertTrue(fComplex.pow(0).isExact(1, 0), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (validate)")
        public void powValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateRef(e -> e.pow(3), fComplex);
        }

        @Test
        @DisplayName("Root")
        public void root() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplex[] fComplexRes = fComplex.root(3);

            assertAll("Validate root values",
                    () -> assertTrue(fComplex.isSimilar(fComplexRes[0].pow(3)),
                            "The root value 0 is erroneous"),
                    () -> assertTrue(fComplex.isSimilar(fComplexRes[1].pow(3)),
                            "The root value 1 is erroneous"),
                    () -> assertTrue(fComplex.isSimilar(fComplexRes[2].pow(3)),
                            "The root value 2 is erroneous")
            );
        }

        @Test
        @DisplayName("Root (size)")
        public void rootSize() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplex[] fComplexRes = fComplex.root(3);

            assertEquals(3, fComplexRes.length,
                    "The number of root values is erroneous");
        }

        @Test
        @DisplayName("Root (throw IllegalArgumentException)")
        public void rootThrowIllegalArgumentException() {

            assertThrows(IllegalArgumentException.class, () -> RandomHelper.getTestComplex().root(-1),
                    "The root value must be greater than zero");
        }

        @Test
        @DisplayName("Root (validate)")
        public void rootValidate() {
            FComplex fComplex = RandomHelper.getTestComplex();

            FComplexHelper.validateVal(e -> e.root(3), fComplex);
        }
    }
}

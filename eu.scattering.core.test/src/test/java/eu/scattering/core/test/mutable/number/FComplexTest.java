package eu.scattering.core.test.mutable.number;

import eu.scattering.core.design.mutable.number.complex.FComplex;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutable.number.support.FComplexTestHelper;
import eu.scattering.core.transfer.container.position.FPos2D.FPos2D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FComplex")
public class FComplexTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FComplexBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FComplex fComplex = factory.getFComplex();

            assertNotNull(fComplex, "The instance is null");

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(0, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with parameters")
        void constructWithParameters() {
            FComplex fComplex = factory.getFComplex(1, 2);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with real part")
        void constructWithRe() {
            FComplex fComplex = factory.getFComplex(1);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FPos2D")
        void constructWithFPos2D() {
            FComplex fComplex = factory.getFComplex(factory.getFPos2D(1, 2));

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with primitives")
        void setWithPrimitives() {
            FComplex fComplex = factory.getFComplex();

            fComplex.set(1, 2);

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FComplex")
        void setWithFComplex() {
            FComplex fComplex = factory.getFComplex();

            fComplex.applyStateFrom(factory.getFComplex(1, 2));

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set values with FPos2D")
        void setWithFPos2D() {
            FComplex fComplex = factory.getFComplex();

            fComplex.applyStateFrom(factory.getFPos2D(1, 2));

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set Re")
        void setRe() {
            FComplex fComplex = factory.getFComplex();

            fComplex.setRe(1);

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(0, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Set Im")
        void setIm() {
            FComplex fComplex = factory.getFComplex();

            fComplex.setIm(1);

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(0, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(1, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Export to FPos2D")
        void toFPos2D() {
            FComplex fComplex = factory.getFComplex();

            fComplex.set(1, 2);

            FPos2D fPos2D = fComplex.toFPos2D();

            Assertions.assertAll("Updated values are incorrect",
                    () -> assertEquals(1, fPos2D.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, fPos2D.getD1(), "The D1 value is incorrect")
            );
        }
    }

    @Nested
    @Tag("Mutable")
    @DisplayName("Base mutable")
    class FComplexMutableTest {

        private double refRe, refIm;
        private double argRe, argIm;
        private FComplex refFComplex, argFComplex;

        @BeforeEach
        void beforeEach() {
            refRe = rand.nextDouble();
            refIm = rand.nextDouble();

            argRe = rand.nextDouble();
            argIm = rand.nextDouble();

            refFComplex = factory.getFComplex(refRe, refIm);
            argFComplex = factory.getFComplex(argRe, argIm);
        }

        @Test
        @DisplayName("Add FComplex")
        void addFComplex() {

            refFComplex.add(argFComplex);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add FComplex (validate)")
        void addFComplexValidate() {

            FComplexTestHelper.testReference(FComplex::add, refFComplex, argFComplex);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            refFComplex.add(argRe, argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {

            FComplexTestHelper.testReference(e -> e.add(0, 0), refFComplex);
        }

        @Test
        @DisplayName("Add FPos2D")
        void addFPos2D() {

            refFComplex.add(factory.getFPos2D(argRe, argIm));

            Assertions.assertAll("Validate values",
                    () -> assertEquals(refRe + argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = argRe * argIm;

            refFComplex.addFactor(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + op, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + op, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {

            FComplexTestHelper.testReference(e -> e.addFactor(1), refFComplex);
        }

        @Test
        @DisplayName("Add Re")
        void addRe() {

            refFComplex.addRe(argRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add Re (validate)")
        void addReValidate() {

            FComplexTestHelper.testReference(e -> e.addRe(1), refFComplex);
        }

        @Test
        @DisplayName("Add Im")
        void addIm() {

            refFComplex.addIm(argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add Im (validate)")
        void addImValidate() {

            FComplexTestHelper.testReference(e -> e.addIm(1), refFComplex);
        }

        @Test
        @DisplayName("Sub FComplex")
        void subFComplex() {

            refFComplex.sub(argFComplex);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FComplex (validate)")
        void subFComplexValidate() {

            FComplexTestHelper.testReference(FComplex::sub, refFComplex, argFComplex);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            refFComplex.sub(argRe, argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {

            FComplexTestHelper.testReference(e -> e.sub(0, 0), refFComplex);
        }

        @Test
        @DisplayName("Sub FPos2D")
        void subFPos2D() {

            refFComplex.sub(factory.getFPos2D(argRe, argIm));

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = argRe * argIm;

            refFComplex.subFactor(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - op, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - op, refFComplex.getIm(),
                            "The imaginary part  is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {

            FComplexTestHelper.testReference(e -> e.subFactor(1), refFComplex);
        }

        @Test
        @DisplayName("Sub Re")
        void subRe() {

            refFComplex.subRe(argRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Re (validate)")
        void subReValidate() {

            FComplexTestHelper.testReference(e -> e.subRe(1), refFComplex);
        }

        @Test
        @DisplayName("Sub Im")
        void subIm() {

            refFComplex.subIm(argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Im (validate)")
        void subImValidate() {

            FComplexTestHelper.testReference(e -> e.subIm(1), refFComplex);
        }

        @Test
        @DisplayName("Mul FComplex (simple)")
        void mulFComplexSimple() {
            double refRe = 2, refIm = 3;
            double argRe = 4, argIm = 5;

            FComplex fComplex = factory.getFComplex(refRe, refIm);
            FComplex fComplexArg = factory.getFComplex(argRe, argIm);

            fComplex.mul(fComplexArg);

            double valueRe = (refRe * argRe) - (refIm * argIm);
            double valueIm = ((refRe + refIm) * (argRe + argIm)) - (refRe * argRe) - (refIm * argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(valueRe, fComplex.getRe(),
                            epsilon, "The real part is incorrect"),
                    () -> assertEquals(valueIm, fComplex.getIm(),
                            epsilon, "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FComplex (validate)")
        void mulFComplexValidate() {

            FComplexTestHelper.testReference(FComplex::mul, refFComplex, argFComplex);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {
            double refRe = 2, refIm = 3;

            FComplex fComplex = factory.getFComplex(refRe, refIm);

            fComplex.mul(4, 5);

            double valueRe = (refRe * 4) - (refIm * 5);
            double valueIm = ((refRe + refIm) * (4 + 5)) - (refRe * 4) - (refIm * 5);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(valueRe, fComplex.getRe(),
                            epsilon, "The real part is incorrect"),
                    () -> assertEquals(valueIm, fComplex.getIm(),
                            epsilon, "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {

            FComplexTestHelper.testReference(e -> e.mul(1, 1), refFComplex);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = argRe * argIm;

            refFComplex.mulFactor(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * op, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * op, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {

            FComplexTestHelper.testReference(e -> e.mulFactor(1), refFComplex);
        }

        @Test
        @DisplayName("Mul Re")
        void mulRe() {

            refFComplex.mulRe(argRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Re (validate)")
        void mulReValidate() {

            FComplexTestHelper.testReference(e -> e.mulRe(1), refFComplex);
        }

        @Test
        @DisplayName("Mul Im")
        void mulIm() {

            refFComplex.mulIm(argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Im (validate)")
        void mulImValidate() {

            FComplexTestHelper.testReference(e -> e.mulIm(1), refFComplex);
        }

        @Test
        @DisplayName("Div FComplex (simple)")
        void divFComplexSimple() {
            double refRe = 2, refIm = 3;
            double argRe = 4, argIm = 5;

            FComplex fComplex = factory.getFComplex(refRe, refIm);
            FComplex fComplexArg = factory.getFComplex(argRe, argIm);

            fComplex.div(fComplexArg);

            double divisor = (argRe * argRe) + (argIm * argIm);
            double valueRe = ((refRe * argRe) + (refIm * argIm)) / (divisor);
            double valueIm = ((refIm * argRe) - (refRe * argIm)) / (divisor);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(valueRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(valueIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div FComplex (throw ArithmeticException)")
        void divFComplexThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFComplex.div(factory.getFComplex(0, 0)),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div FComplex (validate)")
        void divFComplexValidate() {

            FComplexTestHelper.testReference(FComplex::div, refFComplex, argFComplex);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {
            double refRe = 2, refIm = 3;

            FComplex fComplex = factory.getFComplex(refRe, refIm);

            fComplex.div(4, 5);

            double divisor = (4 * 4) + (5 * 5);
            double valueRe = ((refRe * 4) + (refIm * 5)) / (divisor);
            double valueIm = ((refIm * 4) - (refRe * 5)) / (divisor);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(valueRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(valueIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {

            FComplexTestHelper.testReference(e -> e.div(1, 1), refFComplex);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = argRe * argIm;

            refFComplex.divFactor(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / op, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / op, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFComplex.divFactor(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {

            FComplexTestHelper.testReference(e -> e.divFactor(1), refFComplex);
        }

        @Test
        @DisplayName("Div Re")
        void divRe() {

            refFComplex.divRe(argRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / argRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div Re (throw ArithmeticException)")
        void divReThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFComplex.divRe(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Re (validate)")
        void divReValidate() {

            FComplexTestHelper.testReference(e -> e.divRe(1), refFComplex);
        }

        @Test
        @DisplayName("Div Im")
        void divIm() {

            refFComplex.divIm(argIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, refFComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / argIm, refFComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div Im (throw ArithmeticException)")
        void divImThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> refFComplex.divIm(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Im (validate)")
        void divImValidate() {

            FComplexTestHelper.testReference(e -> e.divIm(1), refFComplex);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {

            refFComplex.applyStateTo(refFComplex);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, refFComplex.getRe(),
                            "The reference Re value is incorrect"),
                    () -> assertEquals(refIm, refFComplex.getIm(),
                            "The reference Im value is incorrect"),
                    () -> assertEquals(refRe, refFComplex.getRe(),
                            "The Re value is incorrect"),
                    () -> assertEquals(refIm, refFComplex.getIm(),
                            "The Im value is incorrect")
            );
        }

        @Test
        @DisplayName("Imprint (validate)")
        void imprintValidate() {
            FComplex fComplexRef = factory.getFComplex();

            FComplex fComplexArg = fComplexRef.applyStateTo(refFComplex);

            Assertions.assertAll("Validate references",
                    () -> assertNotSame(refFComplex, fComplexRef,
                            "FComplex references should change"),
                    () -> assertSame(fComplexRef, fComplexArg,
                            "The FComplex reference should not change")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FComplexCoreTest {
        private double refRe, refIm;
        private FComplex refFComplex;

        @BeforeEach
        void beforeEach() {

            refRe = rand.nextDouble();
            refIm = rand.nextDouble();
            refFComplex = factory.getFComplex(refRe, refIm);
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            JSONObject json = refFComplex.toJSON();

            FComplex fComplexOp = factory.getFComplex().applyStateFrom(json);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplexOp.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplexOp.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(refFComplex.isExact(fComplexOp),
                            "FComplex values should be equal"),
                    () -> assertTrue(fComplexOp.isExact(refFComplex),
                            "FComplex values should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FComplex fComplexOp = refFComplex.copy().addFactor(0.5 * epsilon);

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(refFComplex.isExact(fComplexOp),
                            "FComplex values should not be equal"),
                    () -> assertFalse(fComplexOp.isExact(refFComplex),
                            "FComplex values should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            FComplexTestHelper.testValue(FComplex::isExact, refFComplex, fComplexOp);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {

            assertTrue(refFComplex.isExact(refRe, refIm), "FComplex values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {

            assertFalse(refFComplex.isExact(0, 0),
                    "FComplex values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testValue(e -> e.isExact(0, 0), fComplex);
        }

        @Test
        @DisplayName("Exactness with Pos2D")
        void isExactWithPos2D() {
            FPos2D fPos2D = factory.getFPos2D(refRe, refIm);

            assertTrue(refFComplex.isExact(fPos2D),
                    "Values should be equal");
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            double ref = epsilon * 0.5;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertTrue(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex)),
                            "FComplex values should be similar (same position)"),
                    () -> assertTrue(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).addRe(ref)),
                            "FComplex values should be similar (positive Re)"),
                    () -> assertTrue(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).subRe(ref)),
                            "FComplex values should be similar (negative Re)"),
                    () -> assertTrue(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).addIm(ref)),
                            "FComplex values should be similar (positive Im)"),
                    () -> assertTrue(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).subIm(ref)),
                            "FComplex values should be similar (negative Im)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            double ref = epsilon * 2;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertFalse(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).addRe(ref)),
                            "FComplex values should not be similar (positive Re)"),
                    () -> assertFalse(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).subRe(ref)),
                            "FComplex values should not be similar (negative Re)"),
                    () -> assertFalse(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).addIm(ref)),
                            "FComplex values should not be similar (positive Im)"),
                    () -> assertFalse(refFComplex
                                    .isSimilar(factory.getFComplex().add(refFComplex).subIm(ref)),
                            "FComplex values should not be similar (negative Im)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            FComplexTestHelper.testValue(FComplex::isSimilar, refFComplex, fComplexOp);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            double error = 0.5 * epsilon;

            assertTrue(refFComplex.isSimilar(
                    refRe + error,
                    refIm + error),
                    "FComplex values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {

            assertFalse(refFComplex.isSimilar(
                    refRe + (1.5 * epsilon),
                    refRe + (1.5 * epsilon)),
                    "FComplex values should not be similar");
        }

        @Test
        @DisplayName("Similarity with FPos2D")
        void isSimilarWithFPos2D() {
            double error = 0.5 * epsilon;
            FPos2D fPos2D = factory.getFPos2D(refRe + error, refIm + error);

            assertTrue(refFComplex.isSimilar(fPos2D),
                    "FComplex values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (validate)")
        void isSimilarWithParametersValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testValue(e -> e.isSimilar(0, 0), fComplex);
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            assertEquals(refFComplex.hashCode(), fComplexOp.hashCode(),
                    "Two identical FComplex values should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {

            assertNotEquals(refFComplex.hashCode(), factory.getFComplex().hashCode(),
                    "Two different FComplex values should not have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (validate)")
        void getHashCodeValidate() {
            FComplex fComplexRef = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::hashCode, fComplexRef);
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FComplex fComplex = refFComplex.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(refFComplex, fComplex,
                            "FComplex objects contain different values"),
                    () -> assertTrue(refFComplex.isExact(fComplex),
                            "FComplex values should be the same"),
                    () -> assertFalse(refFComplex.isExact(fComplex.add(refFComplex)),
                            "FComplex values should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FComplex fComplex = TestHelper.getRandFComplex();

            FComplexTestHelper.testValue(FComplex::copy, fComplex);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FComplex fComplex = refFComplex.copyZero();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(refFComplex, fComplex,
                            "FComplex objects contain different values"),
                    () -> assertEquals(0, fComplex.getRe(),
                            "FComplex Re values are incorrect"),
                    () -> assertEquals(0, fComplex.getIm(),
                            "FComplex Im values are incorrect")
            );
        }

        @Test
        @DisplayName("Copy zero (validate)")
        void copyZeroValidate() {
            FComplex fComplex = TestHelper.getRandFComplex();

            FComplexTestHelper.testValue(FComplex::copyZero, fComplex);
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FComplexAdvancedTest {

        @Test
        @DisplayName("Get magnitude")
        void getMagnitude() {
            FComplex fComplex = TestHelper.getRandFComplex();

            double res = Math.sqrt((fComplex.getRe() * fComplex.getRe()) + (fComplex.getIm() * fComplex.getIm()));

            assertEquals(res, fComplex.getMagnitude(),
                    "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude (validate)")
        void getMagnitudeValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::getMagnitude, fComplex);
        }

        @Test
        @DisplayName("Get magnitude P2")
        void getMagnitudeP2() {
            FComplex fComplex = TestHelper.getRandFComplex();

            double res = (fComplex.getRe() * fComplex.getRe()) + (fComplex.getIm() * fComplex.getIm());

            assertEquals(res, fComplex.getMagnitudeP2(),
                    "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Get magnitude P2 (validate)")
        void getMagnitudeP2Validate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::getMagnitudeP2, fComplex);
        }

        @Test
        @DisplayName("Get distance")
        void getDistance() {
            FComplex fComplexRef = TestHelper.getRandFComplex();
            FComplex fComplexArg = TestHelper.getRandFComplex(fComplexRef);

            double distanceRe = Math.pow(Math.abs(fComplexRef.getRe() - fComplexArg.getRe()), 2);
            double distanceIm = Math.pow(Math.abs(fComplexRef.getIm() - fComplexArg.getIm()), 2);
            double res = Math.sqrt(distanceRe + distanceIm);

            assertEquals(res, fComplexRef.getDistance(fComplexArg),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FComplex fComplexRef = factory.getFComplex();
            FComplex fComplexArg = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::getDistance, fComplexRef, fComplexArg);
        }

        @Test
        @DisplayName("Get distance with primitives")
        void getDistanceWithPrimitives() {
            double argRe = rand.nextDouble();
            double argIm = rand.nextDouble();

            FComplex fComplexRef = TestHelper.getRandFComplex();

            double distanceRe = Math.pow(Math.abs(fComplexRef.getRe() - argRe), 2);
            double distanceIm = Math.pow(Math.abs(fComplexRef.getIm() - argIm), 2);
            double res = Math.sqrt(distanceRe + distanceIm);

            assertEquals(res, fComplexRef.getDistance(argRe, argIm),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance with FPos2D")
        void getDistanceWithFPos2D() {
            double argRe = rand.nextDouble();
            double argIm = rand.nextDouble();

            FComplex fComplex = TestHelper.getRandFComplex();
            FPos2D fPos2D = factory.getFPos2D(argRe, argIm);

            double distanceRe = Math.pow(Math.abs(fComplex.getRe() - fPos2D.getD0()), 2);
            double distanceIm = Math.pow(Math.abs(fComplex.getIm() - fPos2D.getD1()), 2);
            double res = Math.sqrt(distanceRe + distanceIm);

            assertEquals(res, fComplex.getDistance(fPos2D),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FComplex fComplexRef = TestHelper.getRandFComplex();
            FComplex fComplexArg = TestHelper.getRandFComplex(fComplexRef);

            double distanceRe = Math.pow(Math.abs(fComplexRef.getRe() - fComplexArg.getRe()), 2);
            double distanceIm = Math.pow(Math.abs(fComplexRef.getIm() - fComplexArg.getIm()), 2);
            double res = distanceRe + distanceIm;

            assertEquals(res, fComplexRef.getDistanceP2(fComplexArg),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FComplex fComplexRef = factory.getFComplex();
            FComplex fComplexArg = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::getDistanceP2, fComplexRef, fComplexArg);
        }

        @Test
        @DisplayName("Get distance P2 with primitives")
        void getDistanceP2WithPrimitives() {
            double re = rand.nextDouble();
            double im = rand.nextDouble();

            FComplex fComplexRef = TestHelper.getRandFComplex();

            double distanceRe = Math.pow(Math.abs(fComplexRef.getRe() - re), 2);
            double distanceIm = Math.pow(Math.abs(fComplexRef.getIm() - im), 2);
            double res = distanceRe + distanceIm;

            assertEquals(res, fComplexRef.getDistanceP2(re, im),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 with FPos2D")
        void getDistanceP2WithFPos2D() {
            FComplex fComplexRef = TestHelper.getRandFComplex();
            FPos2D fPos2D = TestHelper.getRandFComplex(fComplexRef).toFPos2D();

            double distanceRe = Math.pow(Math.abs(fComplexRef.getRe() - fPos2D.getD0()), 2);
            double distanceIm = Math.pow(Math.abs(fComplexRef.getIm() - fPos2D.getD1()), 2);
            double res = distanceRe + distanceIm;

            assertEquals(res, fComplexRef.getDistanceP2(fPos2D),
                    epsilon, "The distance is erroneous");
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            FComplex fComplex = TestHelper.getRandFComplex();
            double magnitude = Math.abs(rand.nextDouble());

            fComplex.setMagnitude(magnitude);

            assertEquals(magnitude, fComplex.getMagnitude(),
                    epsilon, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (negative)")
        void setMagnitudeNegative() {
            FComplex fComplexA = TestHelper.getRandFComplex();
            FComplex fComplexB = fComplexA.copy().negate();
            double magnitude = Math.abs(rand.nextDouble());

            fComplexA.setMagnitude(-magnitude);
            fComplexB.setMagnitude(magnitude);

            assertTrue(fComplexA.isSimilar(fComplexB),
                    "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (throw IllegalStateException)")
        void setMagnitudeThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class,
                    () -> factory.getFComplex().setMagnitude(1),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Set magnitude (validate)")
        void setMagnitudeValidate() {
            FComplex fComplex = factory.getFComplex(1, 1);

            FComplexTestHelper.testReference(e -> e.setMagnitude(1), fComplex);
        }

        @Test
        @DisplayName("Get phase")
        void getPhase() {
            double re = rand.nextDouble();
            double im = rand.nextDouble();

            FComplex fComplex = factory.getFComplex(re, im);

            assertNotEquals(Math.PI, fComplex.getPhase(),
                    epsilon, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (zero)")
        void getPhaseZero() {
            double re = Math.abs(rand.nextDouble());
            double im = 0;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(0, fComplex.getPhase(),
                    epsilon, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (pi)")
        void getPhasePi() {
            double re = -Math.abs(rand.nextDouble());
            double im = 0;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(Math.PI, fComplex.getPhase(),
                    epsilon, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (simple A)")
        void getPhaseSimpleA () {
            double re = 1;
            double im = 1;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(Math.PI * 0.25, fComplex.getPhase(),
                    epsilon, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (simple B)")
        void getPhaseSimpleB () {
            double re = 1;
            double im = -1;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(Math.PI * -0.25, fComplex.getPhase(),
                    epsilon, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (throw IllegalStateException)")
        void getPhaseThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class,
                    () -> factory.getFComplex().getPhase(),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Get phase (validate)")
        void getPhaseValidate() {
            FComplex fComplex = factory.getFComplex(1, 1);

            FComplexTestHelper.testValue(FComplex::getPhase, fComplex);
        }

        @Test
        @DisplayName("Set phase")
        void setPhase() {
            FComplex fComplex = factory.getFComplex(1, 1);
            double phase = rand.nextDouble() % Math.PI;

            fComplex.setPhase(phase);

            assertEquals(phase, fComplex.getPhase(),
                    epsilon, "The phase is erroneous");
        }

        @Test
        @DisplayName("Set phase (validate)")
        void setPhaseValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testReference(e -> e.setPhase(1), fComplex);
        }

        @Test
        @DisplayName("Negate")
        public void negate() {
            double re = rand.nextDouble();
            double im = rand.nextDouble();
            FComplex fComplex = factory.getFComplex(re, im);

            fComplex.negate();

            Assertions.assertAll("Validate FComplex",
                    () -> assertEquals(-re, fComplex.getRe(),
                            epsilon, "The Re value is erroneous"),
                    () -> assertEquals(-im, fComplex.getIm(),
                            epsilon, "The Im value is erroneous")
            );
        }

        @Test
        @DisplayName("Negate (validate)")
        public void negateValidate() {
            FComplex fComplex = factory.getFComplex(1, 1);

            FComplexTestHelper.testReference(FComplex::negate, fComplex);
        }

        @Test
        @DisplayName("Inverse")
        public void inverse() {
            double re = rand.nextDouble();
            double im = rand.nextDouble();
            FComplex fComplex = factory.getFComplex(re, im);

            fComplex.inverse();
            fComplex.mul(factory.getFComplex(re, im));

            Assertions.assertAll("Validate FComplex",
                    () -> assertEquals(1, fComplex.getRe(),
                            epsilon, "The Re value is erroneous"),
                    () -> assertEquals(0, fComplex.getIm(),
                            epsilon, "The Im value is erroneous")
            );
        }

        @Test
        @DisplayName("Inverse (validate)")
        public void inverseValidate() {
            FComplex fComplex = factory.getFComplex(1, 1);

            FComplexTestHelper.testReference(FComplex::inverse, fComplex);
        }

        @Test
        @DisplayName("Conjugate")
        public void conjugate() {
            double re = rand.nextDouble();
            double im = rand.nextDouble();
            FComplex fComplex = factory.getFComplex(re, im);

            fComplex.conjugate();

            Assertions.assertAll("Validate FComplex",
                    () -> assertEquals(re, fComplex.getRe(),
                            epsilon, "The Re value is erroneous"),
                    () -> assertEquals(-im, fComplex.getIm(),
                            epsilon, "The Im value is erroneous"));
        }

        @Test
        @DisplayName("Conjugate (validate)")
        public void conjugateValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testReference(FComplex::conjugate, fComplex);
        }

        @Test
        @DisplayName("Normalize")
        public void normalize() {
            FComplex fComplex = TestHelper.getRandFComplex();

            fComplex.normalize();

            assertEquals(1, fComplex.getMagnitude(),
                    epsilon, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class,
                    () -> factory.getFComplex().normalize(),
                    "The direction is not defined");
        }

        @Test
        @DisplayName("Normalize (validate)")
        public void normalizeValidate() {
            FComplex fComplex = factory.getFComplex(1, 1);

            FComplexTestHelper.testReference(FComplex::normalize, fComplex);
        }

        @Test
        @DisplayName("Is zero")
        public void isZero() {
            FComplex fComplex = factory.getFComplex();

            assertTrue(fComplex.isZero(),
                    "The FComplex value should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        public void isZeroFail() {
            FComplex fComplex = TestHelper.getRandFComplex();

            assertFalse(fComplex.isZero(),
                    "The FComplex value should not be zero");
        }

        @Test
        @DisplayName("Is zero (validate)")
        public void isZeroValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::isZero, fComplex);
        }

        @Test
        @DisplayName("Power")
        public void pow() {
            FComplex fComplex = factory.getFComplex(3, 4);
            int n = 3;

            FComplex res = fComplex.copy().mul(fComplex).mul(fComplex);

            assertTrue(fComplex.power(n).isSimilar(res),
                    "The value is erroneous");
        }

        @Test
        @DisplayName("Power (negative)")
        public void powNegative() {
            FComplex fComplex = factory.getFComplex(3, 4);
            int n = -3;

            FComplex res = factory.getFComplex(1, 0)
                    .div(fComplex.copy().mul(fComplex).mul(fComplex));

            assertTrue(fComplex.power(n).isSimilar(res),
                    "The value is erroneous");
        }

        @Test
        @DisplayName("Power (zero)")
        public void powZero() {
            FComplex fComplex = TestHelper.getRandFComplex();

            assertTrue(fComplex.power(0).isExact(1, 0),
                    "The value is erroneous");
        }

        @Test
        @DisplayName("Power (validate)")
        public void powValidate() {
            FComplex fComplex = TestHelper.getRandFComplex();

            FComplexTestHelper.testReference(e -> e.power(3), fComplex);
        }

        @Test
        @DisplayName("Root")
        public void root() {
            FComplex fComplex = TestHelper.getRandFComplex();

            FComplex[] fComplexRes = fComplex.root(3);

            Assertions.assertAll("Validate root values",
                    () -> assertTrue(fComplex.isSimilar(fComplexRes[0].power(3)),
                            "The root value 0 is erroneous"),
                    () -> assertTrue(fComplex.isSimilar(fComplexRes[1].power(3)),
                            "The root value 1 is erroneous"),
                    () -> assertTrue(fComplex.isSimilar(fComplexRes[2].power(3)),
                            "The root value 2 is erroneous")
            );
        }

        @Test
        @DisplayName("Root (size)")
        public void rootSize() {
            FComplex fComplex = TestHelper.getRandFComplex();

            FComplex[] fComplexRes = fComplex.root(3);

            Assertions.assertEquals(3, fComplexRes.length,
                    "The number of root values is erroneous");
        }

        @Test
        @DisplayName("Root (throw IllegalArgumentException)")
        public void rootThrowIllegalArgumentException() {

            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> TestHelper.getRandFComplex().root(-1),
                    "The root value must be greater than zero");
        }

        @Test
        @DisplayName("Root (validate)")
        public void rootValidate() {
            FComplex fComplex = TestHelper.getRandFComplex();

            FComplexTestHelper.testValue(e -> e.root(3), fComplex);
        }
    }

    @Nested
    @Tag("Extension")
    @DisplayName("Extension")
    class FComplexExtensionTest {

        @Test
        @DisplayName("Apply")
        void apply() {
            FComplex fComplex = factory.getFComplex(0, 0);

            var fComplexRes = fComplex.apply(p -> p.setRe(1).setIm(2));

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(1, fComplex.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(), "The 'im' value is incorrect"),
                    () -> assertSame(fComplexRes, fComplex, "The reference is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with double")
        void terminateWithDouble() {
            FComplex fComplex = factory.getFComplex(1, 2);

            var res = fComplex.toDouble(p -> {
                p.add(3, 4);
                return p.getRe() + p.getIm();
            });

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(4, fComplex.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(6, fComplex.getIm(), "The 'im' value is incorrect"),
                    () -> assertEquals(10, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean")
        void terminateWithBoolean() {
            FComplex fComplex = factory.getFComplex(1, 2);

            var res = fComplex.toBoolean(p -> {
                p.add(3, 4);
                return p.getRe() + p.getIm() == 10;
            });

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(4, fComplex.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(6, fComplex.getIm(), "The 'im' value is incorrect"),
                    () -> assertTrue(res, "The value is incorrect")
            );
        }
    }
}

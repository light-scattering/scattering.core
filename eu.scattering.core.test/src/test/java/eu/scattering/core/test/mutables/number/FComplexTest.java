package eu.scattering.core.test.mutables.number;

import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.test.mutables.number.support.FComplexTestHelper;
import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Configuration.*;
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

            fComplex.set(factory.getFPos2D(1, 2));

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
        private double opRe, opIm;
        private FComplex fComplex;

        @BeforeEach
        void beforeEach() {
            refRe = random.nextDouble();
            refIm = random.nextDouble();

            opRe = random.nextDouble();
            opIm = random.nextDouble();

            fComplex = factory.getFComplex(refRe, refIm);
        }

        @Test
        @DisplayName("Add FComplex")
        void addFComplex() {
            FComplex fComplexOp = factory.getFComplex(opRe, opIm);

            fComplex.add(fComplexOp);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add FComplex (validate)")
        void addFComplexValidate() {
            FComplex fComplexA = factory.getFComplex();
            FComplex fComplexB = factory.getFComplex();

            FComplexTestHelper.testReference(FComplex::add, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Add primitives")
        void addPrimitives() {

            fComplex.add(opRe, opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add primitives (validate)")
        void addPrimitivesValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.add(0, 0), fComplex);
        }

        @Test
        @DisplayName("Add factor")
        void addFactor() {
            double op = opRe * opIm;

            fComplex.add(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + op, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add factor (validate)")
        void addFactorValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.add(1), fComplex);
        }

        @Test
        @DisplayName("Add Re")
        void addRe() {

            fComplex.addRe(opRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe + opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add Re (validate)")
        void addReValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.addRe(1), fComplex);
        }

        @Test
        @DisplayName("Add Im")
        void addIm() {

            fComplex.addIm(opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm + opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Add Im (validate)")
        void addImValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.addIm(1), fComplex);
        }

        @Test
        @DisplayName("Sub FComplex")
        void subFComplex() {
            FComplex fComplexOp = factory.getFComplex(opRe, opIm);

            fComplex.sub(fComplexOp);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub FComplex (validate)")
        void subFComplexValidate() {
            FComplex fComplexA = factory.getFComplex();
            FComplex fComplexB = factory.getFComplex();

            FComplexTestHelper.testReference(FComplex::sub, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Sub primitives")
        void subPrimitives() {

            fComplex.sub(opRe, opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub primitives (validate)")
        void subPrimitivesValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.sub(0, 0), fComplex);
        }

        @Test
        @DisplayName("Sub factor")
        void subFactor() {
            double op = opRe * opIm;

            fComplex.sub(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - op, fComplex.getIm(),
                            "The imaginary part  is incorrect")
            );
        }

        @Test
        @DisplayName("Sub factor (validate)")
        void subFactorValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.sub(1), fComplex);
        }

        @Test
        @DisplayName("Sub Re")
        void subRe() {

            fComplex.subRe(opRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe - opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Re (validate)")
        void subReValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.subRe(1), fComplex);
        }

        @Test
        @DisplayName("Sub Im")
        void subIm() {

            fComplex.subIm(opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm - opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Sub Im (validate)")
        void subImValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.subIm(1), fComplex);
        }

        @Test
        @DisplayName("Mul FComplex (simple)")
        void mulFComplexSimple() {
            double refRe = 2, refIm = 3;
            double opRe = 4, opIm = 5;

            FComplex fComplex = factory.getFComplex(refRe, refIm);
            FComplex fComplexOp = factory.getFComplex(opRe, opIm);

            fComplex.mul(fComplexOp);

            double valueRe = (refRe * opRe) - (refIm * opIm);
            double valueIm = ((refRe + refIm) * (opRe + opIm)) - (refRe * opRe) - (refIm * opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(valueRe, fComplex.getRe(),
                            jitter, "The real part is incorrect"),
                    () -> assertEquals(valueIm, fComplex.getIm(),
                            jitter, "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul FComplex (validate)")
        void mulFComplexValidate() {
            FComplex fComplexA = factory.getFComplex(1, 1);
            FComplex fComplexB = factory.getFComplex(1, 1);

            FComplexTestHelper.testReference(FComplex::mul, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Mul primitives")
        void mulPrimitives() {

            fComplex.mul(opRe, opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul primitives (validate)")
        void mulPrimitivesValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.mul(1, 1), fComplex);
        }

        @Test
        @DisplayName("Mul factor")
        void mulFactor() {
            double op = opRe * opIm;

            fComplex.mul(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * op, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul factor (validate)")
        void mulFactorValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.mul(1), fComplex);
        }

        @Test
        @DisplayName("Mul Re")
        void mulRe() {

            fComplex.mulRe(opRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe * opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Re (validate)")
        void mulReValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.mulRe(1), fComplex);
        }

        @Test
        @DisplayName("Mul Im")
        void mulIm() {

            fComplex.mulIm(opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm * opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Mul Im (validate)")
        void mulImValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.mulIm(1), fComplex);
        }

        @Test
        @DisplayName("Div FComplex (simple)")
        void divFComplexSimple() {
            double refRe = 2, refIm = 3;
            double opRe = 4, opIm = 5;

            FComplex fComplex = factory.getFComplex(refRe, refIm);
            FComplex fComplexOp = factory.getFComplex(opRe, opIm);

            fComplex.div(fComplexOp);

            double divisor = (opRe * opRe) + (opIm * opIm);
            double valueRe = ((refRe * opRe) + (refIm * opIm)) / (divisor);
            double valueIm = ((refIm * opRe) - (refRe * opIm)) / (divisor);

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
                    () -> fComplex.div(factory.getFComplex(0, 0)),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div FComplex (validate)")
        void divFComplexValidate() {
            FComplex fComplexA = factory.getFComplex(1, 1);
            FComplex fComplexB = factory.getFComplex(1, 1);

            FComplexTestHelper.testReference(FComplex::div, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Div primitives")
        void divPrimitives() {

            fComplex.div(opRe, opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div primitives (throw ArithmeticException)")
        void divPrimitivesThrowArithmeticException() {

            Assertions.assertAll("Division by zero",
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fComplex.div(0, 1),
                            "The value of the real part is zero"),
                    () -> Assertions.assertThrows(ArithmeticException.class,
                            () -> fComplex.div(1, 0),
                            "The value of the imaginary part is zero")
            );
        }

        @Test
        @DisplayName("Div primitives (validate)")
        void divPrimitivesValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.div(1, 1), fComplex);
        }

        @Test
        @DisplayName("Div factor")
        void divFactor() {
            double op = opRe * opIm;

            fComplex.div(op);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / op, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / op, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div factor (throw ArithmeticException)")
        void divFactorThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fComplex.div(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div factor (validate)")
        void divFactorValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.div(1), fComplex);
        }

        @Test
        @DisplayName("Div Re")
        void divRe() {

            fComplex.divRe(opRe);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe / opRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div Re (throw ArithmeticException)")
        void divReThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fComplex.divRe(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Re (validate)")
        void divReValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.divRe(1), fComplex);
        }

        @Test
        @DisplayName("Div Im")
        void divIm() {

            fComplex.divIm(opIm);

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(refRe, fComplex.getRe(),
                            "The real part is incorrect"),
                    () -> assertEquals(refIm / opIm, fComplex.getIm(),
                            "The imaginary part is incorrect")
            );
        }

        @Test
        @DisplayName("Div Im (throw ArithmeticException)")
        void divImThrowArithmeticException() {

            Assertions.assertThrows(ArithmeticException.class,
                    () -> fComplex.divIm(0),
                    "The divisor cannot be zero");
        }

        @Test
        @DisplayName("Div Im (validate)")
        void divImValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.divIm(1), fComplex);
        }

        @Test
        @DisplayName("Imprint")
        void imprint() {
            FComplex fComplexRef = factory.getFComplex();

            fComplex.applyStateTo(fComplexRef);

            Assertions.assertAll("Validate FComplex values",
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
            FComplex fComplexOp = factory.getFComplex();

            FComplex fComplexRef = fComplexOp.applyStateTo(fComplex);

            Assertions.assertAll("Validate references",
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
    class FComplexCoreTest {
        private double refRe, refIm;

        @BeforeEach
        void beforeEach() {

            refRe = random.nextDouble();
            refIm = random.nextDouble();
        }

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);

            JSONObject json = fComplexRef.toJSON();

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
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            Assertions.assertAll("Validate exactness",
                    () -> assertTrue(fComplexRef.isExact(fComplexOp),
                            "FComplex values should be equal"),
                    () -> assertTrue(fComplexOp.isExact(fComplexRef),
                            "FComplex values should be equal")
            );
        }

        @Test
        @DisplayName("Exactness (fail)")
        void isExactFail() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            FComplex fComplexOp = fComplexRef.copy().add(0.5 * jitter);

            Assertions.assertAll("Validate exactness",
                    () -> assertFalse(fComplexRef.isExact(fComplexOp),
                            "FComplex values should not be equal"),
                    () -> assertFalse(fComplexOp.isExact(fComplexRef),
                            "FComplex values should not be equal")
            );
        }

        @Test
        @DisplayName("Exactness (validate)")
        void isExactValidate() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            FComplexTestHelper.testValue(FComplex::isExact, fComplexRef, fComplexOp);
        }

        @Test
        @DisplayName("Exactness with parameters")
        void isExactWithParameters() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);

            assertTrue(fComplexRef.isExact(refRe, refIm), "FComplex values should be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (fail)")
        void isExactWithParametersFail() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);

            assertFalse(fComplexRef.isExact(0, 0),
                    "FComplex values should not be equal");
        }

        @Test
        @DisplayName("Exactness with parameters (validate)")
        void isExactWithParametersValidate() {
            FComplex fComplex = factory.getFComplex();

            FComplexTestHelper.testValue(e -> e.isExact(0, 0), fComplex);
        }

        @Test
        @DisplayName("Similarity")
        void isSimilar() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            double ref = jitter * 0.5;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertTrue(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef)),
                            "FComplex values should be similar (same position)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).addRe(ref)),
                            "FComplex values should be similar (positive Re)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).subRe(ref)),
                            "FComplex values should be similar (negative Re)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).addIm(ref)),
                            "FComplex values should be similar (positive Im)"),
                    () -> assertTrue(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).subIm(ref)),
                            "FComplex values should be similar (negative Im)")
            );
        }

        @Test
        @DisplayName("Similarity (fail)")
        void isSimilarFail() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            double ref = jitter * 2;

            Assertions.assertAll("Check combinations (true)",
                    () -> assertFalse(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).addRe(ref)),
                            "FComplex values should not be similar (positive Re)"),
                    () -> assertFalse(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).subRe(ref)),
                            "FComplex values should not be similar (negative Re)"),
                    () -> assertFalse(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).addIm(ref)),
                            "FComplex values should not be similar (positive Im)"),
                    () -> assertFalse(fComplexRef
                                    .isSimilar(factory.getFComplex().add(fComplexRef).subIm(ref)),
                            "FComplex values should not be similar (negative Im)")
            );
        }

        @Test
        @DisplayName("Similarity (validate)")
        void isSimilarValidate() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            FComplex fComplexOp = factory.getFComplex(refRe, refIm);

            FComplexTestHelper.testValue(FComplex::isSimilar, fComplexRef, fComplexOp);
        }

        @Test
        @DisplayName("Similarity with parameters")
        void isSimilarWithParameters() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            double error = 0.5 * jitter;

            assertTrue(fComplexRef.isSimilar(
                    refRe + error,
                    refIm + error),
                    "FComplex values should be similar");
        }

        @Test
        @DisplayName("Similarity with parameters (fail)")
        void isSimilarWithParametersFail() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);

            assertFalse(fComplexRef.isSimilar(
                    refRe + (1.5 * jitter),
                    refRe + (1.5 * jitter)),
                    "FComplex values should not be similar");
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
            FComplex fComplexRefA = factory.getFComplex(refRe, refIm);
            FComplex fComplexRefB = factory.getFComplex(refRe, refIm);

            assertEquals(fComplexRefA.hashCode(), fComplexRefB.hashCode(),
                    "Two identical FComplex values should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FComplex fComplexRefA = factory.getFComplex(refRe, refIm);

            assertNotEquals(fComplexRefA.hashCode(), factory.getFComplex().hashCode(),
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
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            FComplex fComplex = fComplexRef.copy();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fComplexRef, fComplex,
                            "FComplex objects contain different values"),
                    () -> assertTrue(fComplexRef.isExact(fComplex),
                            "FComplex values should be the same"),
                    () -> assertFalse(fComplexRef.isExact(fComplex.add(fComplexRef)),
                            "FComplex values should be different")
            );
        }

        @Test
        @DisplayName("Copy (validate)")
        void copyValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testValue(FComplex::copy, fComplex);
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FComplex fComplexRef = factory.getFComplex(refRe, refIm);
            FComplex fComplex = fComplexRef.copyZero();

            Assertions.assertAll("Validate copy",
                    () -> assertNotSame(fComplexRef, fComplex,
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
            FComplex fComplex = TestHelper.getRandomFComplex();

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
            FComplex fComplex = TestHelper.getRandomFComplex();

            double res = Math.sqrt((fComplex.getRe() * fComplex.getRe()) + (fComplex.getIm() * fComplex.getIm()));

            assertEquals(res, fComplex.getMagnitude(), "The magnitude is erroneous");
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
            FComplex fComplex = TestHelper.getRandomFComplex();

            double res = (fComplex.getRe() * fComplex.getRe()) + (fComplex.getIm() * fComplex.getIm());

            assertEquals(res, fComplex.getMagnitudeP2(), "The magnitude is erroneous");
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
            FComplex fComplexA = TestHelper.getRandomFComplex();
            FComplex fComplexB = TestHelper.getRandomFComplex(fComplexA);

            double distanceRe = Math.pow(Math.abs(fComplexA.getRe() - fComplexB.getRe()), 2);
            double distanceIm = Math.pow(Math.abs(fComplexA.getIm() - fComplexB.getIm()), 2);
            double res = Math.sqrt(distanceRe + distanceIm);

            assertEquals(res, fComplexA.getDistance(fComplexB), jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance (validate)")
        void getDistanceValidate() {
            FComplex fComplexA = factory.getFComplex();
            FComplex fComplexB = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::getDistance, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Get distance P2")
        void getDistanceP2() {
            FComplex fComplexA = TestHelper.getRandomFComplex();
            FComplex fComplexB = TestHelper.getRandomFComplex(fComplexA);

            double distanceRe = Math.pow(Math.abs(fComplexA.getRe() - fComplexB.getRe()), 2);
            double distanceIm = Math.pow(Math.abs(fComplexA.getIm() - fComplexB.getIm()), 2);
            double res = distanceRe + distanceIm;

            assertEquals(res, fComplexA.getDistanceP2(fComplexB), jitter, "The distance is erroneous");
        }

        @Test
        @DisplayName("Get distance P2 (validate)")
        void getDistanceP2Validate() {
            FComplex fComplexA = factory.getFComplex();
            FComplex fComplexB = factory.getFComplex();

            FComplexTestHelper.testValue(FComplex::getDistanceP2, fComplexA, fComplexB);
        }

        @Test
        @DisplayName("Set magnitude")
        void setMagnitude() {
            FComplex fComplex = TestHelper.getRandomFComplex();
            double magnitude = Math.abs(random.nextDouble());

            fComplex.setMagnitude(magnitude);

            assertEquals(magnitude, fComplex.getMagnitude(),
                    jitter, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (negative)")
        void setMagnitudeNegative() {
            FComplex fComplexA = TestHelper.getRandomFComplex();
            FComplex fComplexB = fComplexA.copy().negate();
            double magnitude = Math.abs(random.nextDouble());

            fComplexA.setMagnitude(-magnitude);
            fComplexB.setMagnitude(magnitude);

            assertTrue(fComplexA.isSimilar(fComplexB), "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Set magnitude (throw IllegalStateException)")
        void setMagnitudeThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class, () -> factory.getFComplex().setMagnitude(1),
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
            double re = random.nextDouble();
            double im = random.nextDouble();

            FComplex fComplex = factory.getFComplex(re, im);

            assertNotEquals(Math.PI, fComplex.getPhase(),
                    jitter, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (zero)")
        void getPhaseZero() {
            double re = Math.abs(random.nextDouble());
            double im = 0;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(0, fComplex.getPhase(),
                    jitter, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (pi)")
        void getPhasePi() {
            double re = -Math.abs(random.nextDouble());
            double im = 0;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(Math.PI, fComplex.getPhase(),
                    jitter, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (simple A)")
        void getPhaseSimpleA () {
            double re = 1;
            double im = 1;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(Math.PI * 0.25, fComplex.getPhase(),
                    jitter, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (simple B)")
        void getPhaseSimpleB () {
            double re = 1;
            double im = -1;

            FComplex fComplex = factory.getFComplex(re, im);

            assertEquals(Math.PI * -0.25, fComplex.getPhase(),
                    jitter, "The phase is erroneous");
        }

        @Test
        @DisplayName("Get phase (throw IllegalStateException)")
        void getPhaseThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class, () -> factory.getFComplex().getPhase(),
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
            double phase = random.nextDouble() % Math.PI;

            fComplex.setPhase(phase);

            assertEquals(phase, fComplex.getPhase(),
                    jitter, "The phase is erroneous");
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
            double re = random.nextDouble();
            double im = random.nextDouble();
            FComplex fComplex = factory.getFComplex(re, im);

            fComplex.negate();

            Assertions.assertAll("Validate FComplex",
                    () -> assertEquals(-re, fComplex.getRe(),
                            jitter, "The Re value is erroneous"),
                    () -> assertEquals(-im, fComplex.getIm(),
                            jitter, "The Im value is erroneous")
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
            double re = random.nextDouble();
            double im = random.nextDouble();
            FComplex fComplex = factory.getFComplex(re, im);

            fComplex.inverse();
            fComplex.mul(factory.getFComplex(re, im));

            Assertions.assertAll("Validate FComplex",
                    () -> assertEquals(1, fComplex.getRe(),
                            jitter, "The Re value is erroneous"),
                    () -> assertEquals(0, fComplex.getIm(),
                            jitter, "The Im value is erroneous")
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
            double re = random.nextDouble();
            double im = random.nextDouble();
            FComplex fComplex = factory.getFComplex(re, im);

            fComplex.conjugate();

            Assertions.assertAll("Validate FComplex",
                    () -> assertEquals(re, fComplex.getRe(),
                            jitter, "The Re value is erroneous"),
                    () -> assertEquals(-im, fComplex.getIm(),
                            jitter, "The Im value is erroneous"));
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
            FComplex fComplex = TestHelper.getRandomFComplex();

            fComplex.normalize();

            assertEquals(1, fComplex.getMagnitude(),
                    jitter, "The magnitude is erroneous");
        }

        @Test
        @DisplayName("Normalize (throw IllegalStateException)")
        void normalizeThrowIllegalStateException() {

            Assertions.assertThrows(IllegalStateException.class, () -> factory.getFComplex().normalize(),
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

            assertTrue(fComplex.isZero(), "The FComplex value should be zero");
        }

        @Test
        @DisplayName("Is zero (fail)")
        public void isZeroFail() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            assertFalse(fComplex.isZero(), "The FComplex value should not be zero");
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

            assertTrue(fComplex.power(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (negative)")
        public void powNegative() {
            FComplex fComplex = factory.getFComplex(3, 4);
            int n = -3;

            FComplex res = factory.getFComplex(1, 0)
                    .div(fComplex.copy().mul(fComplex).mul(fComplex));

            assertTrue(fComplex.power(n).isSimilar(res), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (zero)")
        public void powZero() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            assertTrue(fComplex.power(0).isExact(1, 0), "The value is erroneous");
        }

        @Test
        @DisplayName("Power (validate)")
        public void powValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplexTestHelper.testReference(e -> e.power(3), fComplex);
        }

        @Test
        @DisplayName("Root")
        public void root() {
            FComplex fComplex = TestHelper.getRandomFComplex();

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
            FComplex fComplex = TestHelper.getRandomFComplex();

            FComplex[] fComplexRes = fComplex.root(3);

            Assertions.assertEquals(3, fComplexRes.length,
                    "The number of root values is erroneous");
        }

        @Test
        @DisplayName("Root (throw IllegalArgumentException)")
        public void rootThrowIllegalArgumentException() {

            Assertions.assertThrows(IllegalArgumentException.class, () -> TestHelper.getRandomFComplex().root(-1),
                    "The root value must be greater than zero");
        }

        @Test
        @DisplayName("Root (validate)")
        public void rootValidate() {
            FComplex fComplex = TestHelper.getRandomFComplex();

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
        @DisplayName("Apply with fixed state")
        void applyWithFixedState() {
            FComplex fComplex = factory.getFComplex(0, 0);

            List<Double> intermediate = new ArrayList<>();

            var fComplexRes = fComplex.applyWithFixedState(p -> intermediate.add(p.setRe(2).setIm(2).getMagnitude()));

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(0, fComplex.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(0, fComplex.getIm(), "The 'im' value is incorrect"),
                    () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                    () -> assertEquals(2 * Math.sqrt(2), intermediate.get(0), jitter, "The value is incorrect"),
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

        @Test
        @DisplayName("Terminate with double (fixed state)")
        void terminateWithDoubleFixedState() {
            FComplex fComplex = factory.getFComplex(1, 2);

            var res = fComplex.toDoubleWithFixedState(p -> {
                p.add(3, 4);
                return p.getRe() + p.getIm();
            });

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(1, fComplex.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(), "The 'im' value is incorrect"),
                    () -> assertEquals(10, res, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Terminate with boolean (fixed state)")
        void terminateWithBooleanFixedState() {
            FComplex fComplex = factory.getFComplex(1, 2);

            var res = fComplex.toBooleanWithFixedState(p -> {
                p.add(3, 4);
                return p.getRe() + p.getIm() == 10;
            });

            Assertions.assertAll("Validate FComplex values",
                    () -> assertEquals(1, fComplex.getRe(), "The 're' value is incorrect"),
                    () -> assertEquals(2, fComplex.getIm(), "The 'im' value is incorrect"),
                    () -> assertTrue(res, "The value is incorrect")
            );
        }
    }
}

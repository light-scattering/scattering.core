package eu.scattering.core.design.main.algebra.type.helper;

import eu.scattering.core.design.main.algebra.type.complex.FComplex;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FComplexHelper {

    public static FComplex validateRef(BiFunction<FComplex, FComplex, FComplex> test, FComplex in, FComplex arg) {
        FComplex argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        FComplex res = test.apply(in, arg);

        assertSame(res, in, "The FComplex reference should not change");
        assertEquals(argPos, arg, "The argument position should not change");

        return res;
    }

    public static FComplex validateRef(Function<FComplex, FComplex> test, FComplex in) {

        FComplex res = test.apply(in);

        assertSame(res, in, "The FComplex reference should not change");

        return res;
    }

    public static Object validateVal(BiFunction<FComplex, FComplex, Object> test, FComplex in, FComplex arg) {
        FComplex inPos = in.copy();
        FComplex argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(in, inPos, "The input position should not change"),
                () -> assertEquals(arg, argPos, "The argument position should not change")
        );

        return res;
    }

    public static Object validateVal(Function<FComplex, Object> test, FComplex in) {
        FComplex inPos = in.copy();

        Object res = test.apply(in);

        assertEquals(in, inPos, "The input position should not change");

        return res;
    }

}

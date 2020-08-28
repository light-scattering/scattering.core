package eu.scattering.core.design.main.algebra.type.support;

import eu.scattering.core.design.main.algebra.type.complex.FComplex;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FComplexTestHelper {

    public static void testReference(BiFunction<FComplex, FComplex, FComplex> exe,
                                     FComplex ref, FComplex arg) {

        assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FComplex argSnapshot = arg.copy();

        FComplex result = exe.apply(ref, arg);

        assertAll("Validate method",
                () -> assertSame(result, ref, "The reference should not change"),
                () -> assertEquals(argSnapshot, arg, "The argument should not be modified")
        );
    }

    public static void testReference(Function<FComplex, FComplex> exe, FComplex ref) {

        FComplex result = exe.apply(ref);

        assertSame(result, ref, "The reference should not change");
    }

    public static void testValue(BiFunction<FComplex, FComplex, Object> exe, FComplex ref, FComplex arg) {

        assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FComplex refSnapshot = ref.copy();
        FComplex argSnapshot = arg.copy();

        exe.apply(ref, arg);

        assertAll("Validate method",
                () -> assertEquals(ref, refSnapshot, "The input should not be modified"),
                () -> assertEquals(arg, argSnapshot, "The argument should not be modified")
        );
    }

    public static void testValue(Function<FComplex, Object> exe, FComplex ref) {
        FComplex refSnapshot = ref.copy();

        exe.apply(ref);

        assertEquals(ref, refSnapshot, "The input should not be modified");
    }
}

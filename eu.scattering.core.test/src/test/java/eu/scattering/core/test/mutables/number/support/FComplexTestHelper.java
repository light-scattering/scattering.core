package eu.scattering.core.test.mutables.number.support;

import eu.scattering.core.design.mutables.number.complex.FComplex;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class FComplexTestHelper {

    public static void testReference(BiFunction<FComplex, FComplex, FComplex> exe, FComplex ref, FComplex arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FComplex argSnapshot = arg.copy();

        FComplex result = exe.apply(ref, arg);

        Assertions.assertAll("Validate method",
                () -> assertSame(result, ref, "The reference should not change"),
                () -> assertTrue(argSnapshot.isExact(arg), "The argument should not be modified")
        );
    }

    public static void testReference(Function<FComplex, FComplex> exe, FComplex ref) {
        FComplex result = exe.apply(ref);

        assertSame(result, ref, "The reference should not change");
    }

    public static void testValue(BiFunction<FComplex, FComplex, Object> exe, FComplex ref, FComplex arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FComplex refSnapshot = ref.copy();
        FComplex argSnapshot = arg.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate method",
                () -> assertTrue(ref.isExact(refSnapshot), "The input should not be modified"),
                () -> assertTrue(arg.isExact(argSnapshot), "The argument should not be modified")
        );
    }

    public static void testValue(Function<FComplex, Object> exe, FComplex ref) {
        FComplex refSnapshot = ref.copy();

        exe.apply(ref);

        assertTrue(ref.isExact(refSnapshot), "The input should not be modified");
    }
}

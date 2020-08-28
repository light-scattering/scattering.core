package eu.scattering.core.design.main.algebra.type.support;

import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FQuaternionTestHelper {

    public static void restReference(BiFunction<FQuaternion, FQuaternion, FQuaternion> exe,
                                     FQuaternion ref, FQuaternion arg) {

        assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FQuaternion argSnapshot = arg.copy();

        FQuaternion result = exe.apply(ref, arg);

        assertAll("Validate method",
                () -> assertSame(result, ref, "The reference should not change"),
                () -> assertEquals(argSnapshot, arg, "The argument should not be modified")
        );
    }

    public static void restReference(Function<FQuaternion, FQuaternion> exe, FQuaternion ref) {

        FQuaternion result = exe.apply(ref);

        assertSame(result, ref, "The reference should not change");
    }

    public static void testValue(BiFunction<FQuaternion, FQuaternion, Object> exe, FQuaternion ref, FQuaternion arg) {

        assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FQuaternion refSnapshot = ref.copy();
        FQuaternion argSnapshot = arg.copy();

        exe.apply(ref, arg);

        assertAll("Validate method",
                () -> assertEquals(ref, refSnapshot, "The input should not be modified"),
                () -> assertEquals(arg, argSnapshot, "The argument should not be modified")
        );
    }

    public static void testValue(Function<FQuaternion, Object> exe, FQuaternion ref) {
        FQuaternion refSnapshot = ref.copy();

        exe.apply(ref);

        assertEquals(ref, refSnapshot, "The input should not be modified");
    }
}

package eu.scattering.core.test.core.mutable.number.support;

import eu.scattering.core.design.core.mutable.number.quaternion.FQuaternion;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class FQuaternionTestHelper {

    public static void restReference(BiFunction<FQuaternion, FQuaternion, FQuaternion> exe,
                                     FQuaternion ref, FQuaternion arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FQuaternion argSnapshot = arg.copy();

        FQuaternion result = exe.apply(ref, arg);

        Assertions.assertAll("Validate method",
                () -> assertSame(result, ref, "The reference should not change"),
                () -> assertTrue(argSnapshot.isExact(arg), "The argument should not be modified")
        );
    }

    public static void restReference(Function<FQuaternion, FQuaternion> exe, FQuaternion ref) {

        FQuaternion result = exe.apply(ref);

        assertSame(result, ref, "The reference should not change");
    }

    public static void testValue(BiFunction<FQuaternion, FQuaternion, Object> exe, FQuaternion ref, FQuaternion arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FQuaternion refSnapshot = ref.copy();
        FQuaternion argSnapshot = arg.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate method",
                () -> assertTrue(ref.isExact(refSnapshot), "The input should not be modified"),
                () -> assertTrue(arg.isExact(argSnapshot), "The argument should not be modified")
        );
    }

    public static void testValue(Function<FQuaternion, Object> exe, FQuaternion ref) {
        FQuaternion refSnapshot = ref.copy();

        exe.apply(ref);

        assertTrue(ref.isExact(refSnapshot), "The input should not be modified");
    }
}

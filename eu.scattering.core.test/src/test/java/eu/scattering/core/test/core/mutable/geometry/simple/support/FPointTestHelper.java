package eu.scattering.core.test.core.mutable.geometry.simple.support;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FPointTestHelper {

    public static void testReference(BiFunction<FPoint, FPoint, FPoint> exe, FPoint ref, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FPoint argSnapshot = arg.copy();

        FPoint result = exe.apply(ref, arg);

        Assertions.assertAll("Validate method",
                () -> assertSame(result, ref, "The reference should not change"),
                () -> assertTrue(argSnapshot.isExact(arg), "The argument should not be modified")
        );
    }

    public static void testReference(Function<FPoint, FPoint> exe, FPoint ref) {

        FPoint result = exe.apply(ref);

        assertSame(result, ref, "The reference should not change");
    }

    public static void testValue(BiFunction<FPoint, FPoint, Object> exe, FPoint ref, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FPoint refSnapshot = ref.copy();
        FPoint argSnapshot = arg.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate method",
                () -> assertTrue(ref.isExact(refSnapshot), "The input should not be modified"),
                () -> assertTrue(arg.isExact(argSnapshot), "The argument should not be modified")
        );
    }

    public static void testValue(Function<FPoint, Object> exe, FPoint ref) {
        FPoint refSnapshot = ref.copy();

        exe.apply(ref);

        assertTrue(ref.isExact(refSnapshot), "The input should not be modified");
    }

}

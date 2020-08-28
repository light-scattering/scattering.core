package eu.scattering.core.design.main.algebra.type.support;

import eu.scattering.core.design.main.algebra.type.Type;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FTypeTestHelper {
// testReference
    public static Type validateRef(BiFunction<Type, Type, Type> exe, Type ref, Type arg) {

        assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        Type argSnapshot = (Type) arg.copy();
        Type result = exe.apply(ref, arg);

        assertAll("Validate method",
                () -> assertSame(result, ref, "The reference should not change"),
                () -> assertEquals(argSnapshot, arg, "The argument should not be modified")
        );

        return result;
    }
// testReference
    public static Type validateRef(Function<Type, Type> exe, Type ref) {

        Type result = exe.apply(ref);

        assertSame(result, ref, "The reference should not change");

        return result;
    }
// testValue
    public static Object validateVal(BiFunction<Type, Type, Object> exe, Type ref, Type arg) {

        assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        Type refSnapshot = (Type) ref.copy();
        Type argSnapshot = (Type) arg.copy();
        Object result = exe.apply(ref, arg);

        assertAll("Validate method",
                () -> assertEquals(ref, refSnapshot, "The input should not be modified"),
                () -> assertEquals(arg, argSnapshot, "The argument should not be modified")
        );

        return result;
    }
// testValue
    public static Object validateVal(Function<Type, Object> exe, Type ref) {
        Type refSnapshot = (Type) ref.copy();
        Object result = exe.apply(ref);

        assertEquals(ref, refSnapshot, "The input should not be modified");

        return result;
    }
}

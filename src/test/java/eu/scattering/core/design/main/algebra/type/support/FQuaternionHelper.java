package eu.scattering.core.design.main.algebra.type.support;

import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FQuaternionHelper {

    public static FQuaternion validateRef(BiFunction<FQuaternion, FQuaternion, FQuaternion> test, FQuaternion in, FQuaternion arg) {
        FQuaternion argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        FQuaternion res = test.apply(in, arg);

        assertSame(res, in, "The FQuaternion reference should not change");
        assertEquals(argPos, arg, "The argument position should not change");

        return res;
    }

    public static FQuaternion validateRef(Function<FQuaternion, FQuaternion> test, FQuaternion in) {

        FQuaternion res = test.apply(in);

        assertSame(res, in, "The FQuaternion reference should not change");

        return res;
    }

    public static Object validateVal(BiFunction<FQuaternion, FQuaternion, Object> test, FQuaternion in, FQuaternion arg) {
        FQuaternion inPos = in.copy();
        FQuaternion argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(in, inPos, "The input position should not change"),
                () -> assertEquals(arg, argPos, "The argument position should not change")
        );

        return res;
    }

    public static Object validateVal(Function<FQuaternion, Object> test, FQuaternion in) {
        FQuaternion inPos = in.copy();

        Object res = test.apply(in);

        assertEquals(in, inPos, "The input position should not change");

        return res;
    }
}

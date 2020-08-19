package eu.scattering.core.design.main.engine.base.helper;

import eu.scattering.core.design.main.engine.base.point.FPoint;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FPointHelper {

    public static FPoint validateRef(BiFunction<FPoint, FPoint, FPoint> test, FPoint in, FPoint arg) {
        FPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        FPoint res = test.apply(in, arg);

        assertSame(res, in, "The IFVector reference should not change");
        assertEquals(argPos, arg, "The argument position should not change");

        return res;
    }

    public static FPoint validateRef(Function<FPoint, FPoint> test, FPoint in) {

        FPoint res = test.apply(in);

        assertSame(res, in, "The IFVector reference should not change");

        return res;
    }

    public static Object validateVal(BiFunction<FPoint, FPoint, Object> test, FPoint in, FPoint arg) {
        FPoint inPos = in.copy();
        FPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(in, inPos, "The input position should not change"),
                () -> assertEquals(arg, argPos, "The argument position should not change")
        );

        return res;
    }

    public static Object validateVal(Function<FPoint, Object> test, FPoint in) {
        FPoint inPos = in.copy();

        Object res = test.apply(in);

        assertEquals(in, inPos, "The input position should not change");

        return res;
    }

}

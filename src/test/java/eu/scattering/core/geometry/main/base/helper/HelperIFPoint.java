package eu.scattering.core.geometry.main.base.helper;

import eu.scattering.core.geometry.main.base.point.IFPoint;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class HelperIFPoint {

    public static IFPoint validateRef(BiFunction<IFPoint, IFPoint, IFPoint> test, IFPoint in, IFPoint arg) {
        IFPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        IFPoint res = test.apply(in, arg);

        assertSame(res, in, "The IFVector reference should not change");
        assertEquals(argPos, arg, "The argument position should not change");

        return res;
    }

    public static IFPoint validateRef(Function<IFPoint, IFPoint> test, IFPoint in) {

        IFPoint res = test.apply(in);

        assertSame(res, in, "The IFVector reference should not change");

        return res;
    }

    public static Object validateVal(BiFunction<IFPoint, IFPoint, Object> test, IFPoint in, IFPoint arg) {
        IFPoint inPos = in.copy();
        IFPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(in, inPos, "The input position should not change"),
                () -> assertEquals(arg, argPos, "The argument position should not change")
        );

        return res;
    }

    public static Object validateVal(Function<IFPoint, Object> test, IFPoint in) {
        IFPoint inPos = in.copy();

        Object res = test.apply(in);

        assertEquals(in, inPos, "The input position should not change");

        return res;
    }

}

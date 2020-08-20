package eu.scattering.core.design.engine.base.helper;

import eu.scattering.core.design.engine.base.point.FPoint;
import eu.scattering.core.design.engine.base.vector.FVector;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public final class FVectorHelper {

    public static FVector validateRef(BiFunction<FVector, FVector, FVector> test, FVector in, FVector arg) {
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();
        FPoint argBaseRef = arg.getBase();
        FPoint argHeadRef = arg.getHead();
        FPoint argBasePos = argBaseRef.copy();
        FPoint argHeadPos = argHeadRef.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        FVector res = test.apply(in, arg);

        assertSame(res, in, "The FVector reference should not change");

        assertAll("Validate positions",
                () -> assertEquals(argBasePos, arg.getBase(), "The argument base position should not change"),
                () -> assertEquals(argHeadPos, arg.getHead(), "The argument head position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inBaseRef, res.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, res.getHead(), "The input head reference should not change"),
                () -> assertSame(argBaseRef, arg.getBase(), "The argument base reference should not change"),
                () -> assertSame(argHeadRef, arg.getHead(), "The argument head reference should not change")
        );

        return res;
    }

    public static FVector validateRef(BiFunction<FVector, FPoint, FVector> test, FVector in, FPoint arg) {
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();
        FPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        FVector res = test.apply(in, arg);

        assertSame(res, in, "The FVector reference should not change");

        assertAll("Validate positions",
                () -> assertEquals(argPos, arg, "The argument position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inBaseRef, res.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, res.getHead(), "The input head reference should not change")
        );

        return res;
    }

    public static FVector validateRef(Function<FVector, FVector> test, FVector in) {
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();

        FVector res = test.apply(in);

        assertSame(res, in, "The FVector reference should not change");

        assertAll("Validate references",
                () -> assertSame(inBaseRef, res.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, res.getHead(), "The input head reference should not change")
        );

        return res;
    }

    public static Object validateVal(BiFunction<FVector, FVector, Object> test, FVector in, FVector arg) {
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();
        FPoint inBasePos = inBaseRef.copy();
        FPoint inHeadPos = inHeadRef.copy();
        FPoint argBaseRef = arg.getBase();
        FPoint argHeadRef = arg.getHead();
        FPoint argBasePos = argBaseRef.copy();
        FPoint argHeadPos = argHeadRef.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(inBasePos, in.getBase(), "The argument base position should not change"),
                () -> assertEquals(inHeadPos, in.getHead(), "The argument head position should not change"),
                () -> assertEquals(argBasePos, arg.getBase(), "The argument base position should not change"),
                () -> assertEquals(argHeadPos, arg.getHead(), "The argument head position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inBaseRef, in.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, in.getHead(), "The input head reference should not change"),
                () -> assertSame(argBaseRef, arg.getBase(), "The argument base reference should not change"),
                () -> assertSame(argHeadRef, arg.getHead(), "The argument head reference should not change")
        );

        return res;
    }

    public static Object validateVal(BiFunction<FVector, FPoint, Object> test, FVector in, FPoint arg) {
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();
        FPoint inBasePos = inBaseRef.copy();
        FPoint inHeadPos = inHeadRef.copy();
        FPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(inBasePos, in.getBase(), "The argument base position should not change"),
                () -> assertEquals(inHeadPos, in.getHead(), "The argument head position should not change"),
                () -> assertEquals(argPos, arg, "The argument position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inBaseRef, in.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, in.getHead(), "The input head reference should not change")
        );

        return res;
    }

    public static Object validateVal(Function<FVector, Object> test, FVector in) {
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();
        FPoint inBasePos = inBaseRef.copy();
        FPoint inHeadPos = inHeadRef.copy();

        Object res = test.apply(in);

        assertAll("Validate positions",
                () -> assertEquals(inBasePos, in.getBase(), "The argument base position should not change"),
                () -> assertEquals(inHeadPos, in.getHead(), "The argument head position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inBaseRef, in.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, in.getHead(), "The input head reference should not change")
        );

        return res;
    }
}

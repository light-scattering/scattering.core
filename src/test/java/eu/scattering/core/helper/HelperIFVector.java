package eu.scattering.core.helper;

import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public final class HelperIFVector {

    public static void validate(BiFunction<IFVector, IFVector, IFVector> test, IFVector in, IFVector arg) {
        IFPoint inBaseRef = in.getBase();
        IFPoint inHeadRef = in.getHead();
        IFPoint argBaseRef = arg.getBase();
        IFPoint argHeadRef = arg.getHead();
        IFPoint argBasePos = argBaseRef.copy();
        IFPoint argHeadPos = argHeadRef.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        IFVector res = test.apply(in, arg);

        assertSame(res, in, "The IFVector reference should not change");

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
    }

    public static void validate(BiFunction<IFVector, IFPoint, IFVector> test, IFVector in, IFPoint arg) {
        IFPoint inBaseRef = in.getBase();
        IFPoint inHeadRef = in.getHead();
        IFPoint argPos = arg.copy();

        assertThrows(NullPointerException.class, () -> test.apply(in, null), "The reference cannot be null");

        IFVector res = test.apply(in, arg);

        assertSame(res, in, "The IFVector reference should not change");

        assertAll("Validate positions",
                () -> assertEquals(argPos, arg, "The argument position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inBaseRef, res.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, res.getHead(), "The input head reference should not change")
        );
    }

    public static void validate(Function<IFVector, IFVector> test, IFVector in) {
        IFPoint inBaseRef = in.getBase();
        IFPoint inHeadRef = in.getHead();

        IFVector res = test.apply(in);

        assertSame(res, in, "The IFVector reference should not change");

        assertAll("Validate references",
                () -> assertSame(inBaseRef, res.getBase(), "The input base reference should not change"),
                () -> assertSame(inHeadRef, res.getHead(), "The input head reference should not change")
        );
    }

}

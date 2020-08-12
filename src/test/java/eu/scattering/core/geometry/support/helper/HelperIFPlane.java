package eu.scattering.core.geometry.support.helper;

import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import eu.scattering.core.geometry.support.plane.IFPlane;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class HelperIFPlane {

    public static Object validateVal(BiFunction<IFPlane, IFPlane, Object> test, IFPlane in, IFPlane arg) {
        IFVector inRef = in.getOrigin();
        IFVector inRefPos = in.getOrigin().copy();
        IFPoint inBaseRef = in.getBase();
        IFPoint inHeadRef = in.getHead();
        IFVector argRef = arg.getOrigin();
        IFVector argRefPos = arg.getOrigin().copy();
        IFPoint argBaseRef = arg.getBase();
        IFPoint argHeadRef = arg.getHead();

        assertThrows(NullPointerException.class, () -> test.apply(in, null),
                "The reference cannot be null");

        Object res = test.apply(in, arg);

        assertAll("Validate positions",
                () -> assertEquals(inRefPos, in.getOrigin(),
                        "The input origin position should not change"),
                () -> assertEquals(argRefPos, arg.getOrigin(),
                        "The argument origin position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inRef, inRef,
                        "The input origin reference should not change"),
                () -> assertSame(inBaseRef, in.getBase(),
                        "The input origin head reference should not change"),
                () -> assertSame(inHeadRef, in.getHead(),
                        "The input origin base reference should not change"),
                () -> assertSame(argRef, arg.getOrigin(),
                        "The argument origin reference should not change"),
                () -> assertSame(argBaseRef, arg.getBase(),
                        "The argument origin base reference should not change"),
                () -> assertSame(argHeadRef, arg.getHead(),
                        "The argument origin head reference should not change")
        );

        return res;
    }

    public static Object validateVal(Function<IFPlane, Object> test, IFPlane in) {
        IFVector inRef = in.getOrigin();
        IFVector inRefPos = in.getOrigin().copy();
        IFPoint inBaseRef = in.getBase();
        IFPoint inHeadRef = in.getHead();

        Object res = test.apply(in);

        assertAll("Validate positions",
                () -> assertEquals(inRefPos, in.getOrigin(),
                        "The input origin position should not change")
        );

        assertAll("Validate references",
                () -> assertSame(inRef, inRef,
                        "The input origin reference should not change"),
                () -> assertSame(inBaseRef, in.getBase(),
                        "The input origin head reference should not change"),
                () -> assertSame(inHeadRef, in.getHead(),
                        "The input origin base reference should not change")
        );

        return res;
    }

}

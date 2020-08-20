package eu.scattering.core.design.main.engine.support.helper;

import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;
import eu.scattering.core.design.main.engine.support.plane.FPlane;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class FPlaneHelper {

    public static Object validateVal(BiFunction<FPlane, FPlane, Object> test, FPlane in, FPlane arg) {
        FVector inRef = in.getOrigin();
        FVector inRefPos = in.getOrigin().copy();
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();
        FVector argRef = arg.getOrigin();
        FVector argRefPos = arg.getOrigin().copy();
        FPoint argBaseRef = arg.getBase();
        FPoint argHeadRef = arg.getHead();

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

    public static Object validateVal(Function<FPlane, Object> test, FPlane in) {
        FVector inRef = in.getOrigin();
        FVector inRefPos = in.getOrigin().copy();
        FPoint inBaseRef = in.getBase();
        FPoint inHeadRef = in.getHead();

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

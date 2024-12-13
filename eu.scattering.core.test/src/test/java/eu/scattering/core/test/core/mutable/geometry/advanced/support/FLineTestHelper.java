package eu.scattering.core.test.core.mutable.geometry.advanced.support;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.geometry.construct.line.FLine;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FLineTestHelper {

    public static void testValue(BiFunction<FLine, FLine, Object> exe,
                                 FLine ref, FLine arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");
        
        FVector refOriginSnapshot = ref.getOrigin();
        FVector refOriginPositionSnapshot = ref.getOrigin().copy();
        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();
        FVector argOriginSnapshot = arg.getOrigin();
        FVector argOriginPositionSnapshot = arg.getOrigin().copy();
        FPoint argBaseSnapshot = arg.getBase();
        FPoint argHeadSnapshot = arg.getHead();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refOriginPositionSnapshot.isExact(ref.getOrigin()),
                        "The input origin position should not change"),
                () -> assertTrue(argOriginPositionSnapshot.isExact(arg.getOrigin()),
                        "The argument origin position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refOriginSnapshot, refOriginSnapshot,
                        "The input origin reference should not change"),
                () -> assertSame(refBaseSnapshot, ref.getBase(),
                        "The input origin head reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getHead(),
                        "The input origin base reference should not change"),
                () -> assertSame(argOriginSnapshot, arg.getOrigin(),
                        "The argument origin reference should not change"),
                () -> assertSame(argBaseSnapshot, arg.getBase(),
                        "The argument origin base reference should not change"),
                () -> assertSame(argHeadSnapshot, arg.getHead(),
                        "The argument origin head reference should not change")
        );
    }

    public static void testValue(Function<FLine, Object> exe, FLine ref) {
        FVector refOriginSnapshot = ref.getOrigin();
        FVector refOriginPositionSnapshot = ref.getOrigin().copy();
        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();

        exe.apply(ref);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refOriginPositionSnapshot.isExact(ref.getOrigin()),
                        "The input origin position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refOriginSnapshot, refOriginSnapshot,
                        "The input origin reference should not change"),
                () -> assertSame(refBaseSnapshot, ref.getBase(),
                        "The input origin head reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getHead(),
                        "The input origin base reference should not change")
        );
    }
}

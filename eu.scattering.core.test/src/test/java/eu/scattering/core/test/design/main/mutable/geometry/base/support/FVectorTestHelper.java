package eu.scattering.core.test.design.main.mutable.geometry.base.support;

import eu.scattering.core.test.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.test.design.main.mutable.geometry.base.vector.FVector;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public final class FVectorTestHelper {

    public static void testReference(BiFunction<FVector, FVector, FVector> exe,
                                     FVector ref, FVector arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();
        FPoint argBaseSnapshot = arg.getBase();
        FPoint argHeadSnapshot = arg.getHead();
        FPoint argBasePositionSnapshot = argBaseSnapshot.copy();
        FPoint argHeadPositionSnapshot = argHeadSnapshot.copy();

        FVector result = exe.apply(ref, arg);

        assertSame(result, ref, "The FVector reference should not change");

        Assertions.assertAll("Validate positions",
                () -> assertTrue(argBasePositionSnapshot.isExact(arg.getBase()),
                        "The argument base position should not change"),
                () -> assertTrue(argHeadPositionSnapshot.isExact(arg.getHead()),
                        "The argument head position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, result.getBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, result.getHead(),
                        "The input head reference should not change"),
                () -> assertSame(argBaseSnapshot, arg.getBase(),
                        "The argument base reference should not change"),
                () -> assertSame(argHeadSnapshot, arg.getHead(),
                        "The argument head reference should not change")
        );
    }

    public static void testReference(BiFunction<FVector, FPoint, FVector> exe,
                                        FVector ref, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();
        FPoint argSnapshot = arg.copy();

        FVector result = exe.apply(ref, arg);

        assertSame(result, ref, "The FVector reference should not change");

        Assertions.assertAll("Validate positions",
                () -> assertTrue(argSnapshot.isExact(arg),
                        "The argument position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, result.getBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, result.getHead(),
                        "The input head reference should not change")
        );
    }

    public static void testReference(Function<FVector, FVector> exe, FVector ref) {

        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();

        FVector result = exe.apply(ref);

        assertSame(result, ref, "The FVector reference should not change");

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, result.getBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, result.getHead(),
                        "The input head reference should not change")
        );
    }

    public static void testValue(BiFunction<FVector, FVector, Object> exe, FVector ref, FVector arg) {


        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();
        FPoint refBasePositionSnapshot = refBaseSnapshot.copy();
        FPoint refHeadPositionSnapshot = refHeadSnapshot.copy();
        FPoint argBaseSnapshot = arg.getBase();
        FPoint argHeadSnapshot = arg.getHead();
        FPoint argBasePositionSnapshot = argBaseSnapshot.copy();
        FPoint argHeadPositionSnapshot = argHeadSnapshot.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refBasePositionSnapshot.isExact(ref.getBase()),
                        "The input base position should not change"),
                () -> assertTrue(refHeadPositionSnapshot.isExact(ref.getHead()),
                        "The input head position should not change"),
                () -> assertTrue(argBasePositionSnapshot.isExact(arg.getBase()),
                        "The argument base position should not change"),
                () -> assertTrue(argHeadPositionSnapshot.isExact(arg.getHead()),
                        "The argument head position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, ref.getBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getHead(),
                        "The input head reference should not change"),
                () -> assertSame(argBaseSnapshot, arg.getBase(),
                        "The argument base reference should not change"),
                () -> assertSame(argHeadSnapshot, arg.getHead(),
                        "The argument head reference should not change")
        );
    }

    public static void testValue(BiFunction<FVector, FPoint, Object> exe, FVector ref, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();
        FPoint refBasePositionSnapshot = refBaseSnapshot.copy();
        FPoint refHeadPositionSnapshot = refHeadSnapshot.copy();
        FPoint argPositionSnapshot = arg.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refBasePositionSnapshot.isExact(ref.getBase()),
                        "The input base position should not change"),
                () -> assertTrue(refHeadPositionSnapshot.isExact(ref.getHead()),
                        "The input head position should not change"),
                () -> assertTrue(argPositionSnapshot.isExact(arg),
                        "The argument position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, ref.getBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getHead(),
                        "The input head reference should not change")
        );
    }

    public static void testValue(Function<FVector, Object> exe, FVector ref) {

        FPoint refBaseSnapshot = ref.getBase();
        FPoint refHeadSnapshot = ref.getHead();
        FPoint refBasePositionSnapshot = refBaseSnapshot.copy();
        FPoint refHeadPositionSnapshot = refHeadSnapshot.copy();

        exe.apply(ref);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refBasePositionSnapshot.isExact(ref.getBase()),
                        "The input base position should not change"),
                () -> assertTrue(refHeadPositionSnapshot.isExact(ref.getHead()),
                        "The input head position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, ref.getBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getHead(),
                        "The input head reference should not change")
        );
    }
}

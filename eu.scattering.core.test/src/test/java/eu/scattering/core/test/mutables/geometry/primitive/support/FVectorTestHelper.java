package eu.scattering.core.test.mutables.geometry.primitive.support;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public final class FVectorTestHelper {

    public static void testReference(BiFunction<FVector, FVector, FVector> exe, FVector op, FVector arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(op, null),
                "The reference cannot be null");

        FPoint opBaseSnapshot = op.getRefBase();
        FPoint opHeadSnapshot = op.getRefHead();
        FPoint argBaseSnapshot = arg.getRefBase();
        FPoint argHeadSnapshot = arg.getRefHead();
        FPoint argBasePositionSnapshot = argBaseSnapshot.copy();
        FPoint argHeadPositionSnapshot = argHeadSnapshot.copy();

        FVector result = exe.apply(op, arg);

        assertSame(result, op, "The FVector reference should not change");

        Assertions.assertAll("Validate positions",
                () -> assertTrue(argBasePositionSnapshot.isExact(arg.getRefBase()),
                        "The argument base position should not change"),
                () -> assertTrue(argHeadPositionSnapshot.isExact(arg.getRefHead()),
                        "The argument head position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(opBaseSnapshot, result.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(opHeadSnapshot, result.getRefHead(),
                        "The input head reference should not change"),
                () -> assertSame(argBaseSnapshot, arg.getRefBase(),
                        "The argument base reference should not change"),
                () -> assertSame(argHeadSnapshot, arg.getRefHead(),
                        "The argument head reference should not change")
        );
    }

    public static void testReference(BiFunction<FVector, FPoint, FVector> exe, FVector op, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(op, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = op.getRefBase();
        FPoint refHeadSnapshot = op.getRefHead();
        FPoint argSnapshot = arg.copy();

        FVector result = exe.apply(op, arg);

        assertSame(result, op, "The FVector reference should not change");

        Assertions.assertAll("Validate positions",
                () -> assertTrue(argSnapshot.isExact(arg),
                        "The argument position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, result.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, result.getRefHead(),
                        "The input head reference should not change")
        );
    }

    public static void testReference(Function<FVector, FVector> exe, FVector op) {

        FPoint refBaseSnapshot = op.getRefBase();
        FPoint refHeadSnapshot = op.getRefHead();

        FVector result = exe.apply(op);

        assertSame(result, op, "The FVector reference should not change");

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, result.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, result.getRefHead(),
                        "The input head reference should not change")
        );
    }

    public static void testValue(BiFunction<FVector, FVector, Object> exe, FVector op, FVector arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(op, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = op.getRefBase();
        FPoint refHeadSnapshot = op.getRefHead();
        FPoint refBasePositionSnapshot = refBaseSnapshot.copy();
        FPoint refHeadPositionSnapshot = refHeadSnapshot.copy();
        FPoint argBaseSnapshot = arg.getRefBase();
        FPoint argHeadSnapshot = arg.getRefHead();
        FPoint argBasePositionSnapshot = argBaseSnapshot.copy();
        FPoint argHeadPositionSnapshot = argHeadSnapshot.copy();

        exe.apply(op, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refBasePositionSnapshot.isExact(op.getRefBase()),
                        "The input base position should not change"),
                () -> assertTrue(refHeadPositionSnapshot.isExact(op.getRefHead()),
                        "The input head position should not change"),
                () -> assertTrue(argBasePositionSnapshot.isExact(arg.getRefBase()),
                        "The argument base position should not change"),
                () -> assertTrue(argHeadPositionSnapshot.isExact(arg.getRefHead()),
                        "The argument head position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, op.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, op.getRefHead(),
                        "The input head reference should not change"),
                () -> assertSame(argBaseSnapshot, arg.getRefBase(),
                        "The argument base reference should not change"),
                () -> assertSame(argHeadSnapshot, arg.getRefHead(),
                        "The argument head reference should not change")
        );
    }

    public static void testValue(BiFunction<FVector, FPoint, Object> exe, FVector op, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(op, null),
                "The reference cannot be null");

        FPoint refBaseSnapshot = op.getRefBase();
        FPoint refHeadSnapshot = op.getRefHead();
        FPoint refBasePositionSnapshot = refBaseSnapshot.copy();
        FPoint refHeadPositionSnapshot = refHeadSnapshot.copy();
        FPoint argPositionSnapshot = arg.copy();

        exe.apply(op, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refBasePositionSnapshot.isExact(op.getRefBase()),
                        "The input base position should not change"),
                () -> assertTrue(refHeadPositionSnapshot.isExact(op.getRefHead()),
                        "The input head position should not change"),
                () -> assertTrue(argPositionSnapshot.isExact(arg),
                        "The argument position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, op.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, op.getRefHead(),
                        "The input head reference should not change")
        );
    }

    public static void testValue(Function<FVector, Object> exe, FVector op) {

        FPoint refBaseSnapshot = op.getRefBase();
        FPoint refHeadSnapshot = op.getRefHead();
        FPoint refBasePositionSnapshot = refBaseSnapshot.copy();
        FPoint refHeadPositionSnapshot = refHeadSnapshot.copy();

        exe.apply(op);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refBasePositionSnapshot.isExact(op.getRefBase()),
                        "The input base position should not change"),
                () -> assertTrue(refHeadPositionSnapshot.isExact(op.getRefHead()),
                        "The input head position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseSnapshot, op.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadSnapshot, op.getRefHead(),
                        "The input head reference should not change")
        );
    }
}

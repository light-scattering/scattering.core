package eu.scattering.core.test.mutable.geometry.primitive.support;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public final class FVectorTestHelper {

    public static void testReference(Function<FVector, FVector> exe, FVector ref) {
        FPoint refBaseContainer = ref.getRefBase();
        FPoint refHeadContainer = ref.getRefHead();

        FVector refResult = exe.apply(ref);

        assertSame(ref, refResult, "The FVector reference should not change");

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, refResult.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadContainer, refResult.getRefHead(),
                        "The input head reference should not change")
        );
    }

    public static void testReference(BiFunction<FVector, FVector, FVector> exe, FVector ref, FVector arg) {

        Assertions.assertThrows(NullPointerException.class, () ->
                        exe.apply(ref, null), "The argument cannot be null");

        FPoint refBaseContainer = ref.getRefBase();
        FPoint refHeadContainer = ref.getRefHead();

        FPoint argBaseContainer = arg.getRefBase();
        FPoint argHeadContainer = arg.getRefHead();

        FVector argSnapshot = arg.copy();

        FVector refResults = exe.apply(ref, arg);

        assertSame(refResults, ref, "The FVector reference should not change");

        Assertions.assertAll("Validate positions",
                () -> assertTrue(argSnapshot.isExact(arg),
                        "The argument base position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, refResults.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadContainer, refResults.getRefHead(),
                        "The input head reference should not change"),
                () -> assertSame(argBaseContainer, arg.getRefBase(),
                        "The argument base reference should not change"),
                () -> assertSame(argHeadContainer, arg.getRefHead(),
                        "The argument head reference should not change")
        );
    }

    public static void testReference(BiFunction<FVector, FPoint, FVector> exe, FVector ref, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, () ->
                        exe.apply(ref, null), "The argument cannot be null");

        FPoint refBaseContainer = ref.getRefBase();
        FPoint refHeadContainer = ref.getRefHead();

        FPoint argSnapshot = arg.copy();

        FVector refResults = exe.apply(ref, arg);

        assertSame(refResults, ref, "The FVector reference should not change");

        Assertions.assertAll("Validate positions",
                () -> assertTrue(argSnapshot.isExact(arg),
                        "The argument position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, refResults.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadContainer, refResults.getRefHead(),
                        "The input head reference should not change")
        );
    }

    public static void testValue(Function<FVector, Object> exe, FVector ref) {
        FPoint refBaseContainer = ref.getRefBase();
        FPoint refHeadContainer = ref.getRefHead();

        FVector refSnapshot = ref.copy();

        exe.apply(ref);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refSnapshot.isExact(ref),
                        "The input base position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, ref.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadContainer, ref.getRefHead(),
                        "The input head reference should not change")
        );
    }

    public static void testValue(BiFunction<FVector, FVector, Object> exe, FVector ref, FVector arg) {

        Assertions.assertThrows(NullPointerException.class, ()
                        -> exe.apply(ref, null), "The reference cannot be null");

        FPoint refBaseContainer = ref.getRefBase();
        FPoint refHeadContainer = ref.getRefHead();

        FPoint argBaseContainer = arg.getRefBase();
        FPoint argHeadContainer = arg.getRefHead();

        FVector refSnapshot = ref.copy();
        FVector argSnapshot = arg.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refSnapshot.isExact(ref),
                        "The input reference should not change"),
                () -> assertTrue(argSnapshot.isExact(arg),
                        "The argument reference should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, ref.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadContainer, ref.getRefHead(),
                        "The input head reference should not change"),
                () -> assertSame(argBaseContainer, arg.getRefBase(),
                        "The argument base reference should not change"),
                () -> assertSame(argHeadContainer, arg.getRefHead(),
                        "The argument head reference should not change")
        );
    }

    public static void testValue(BiFunction<FVector, FPoint, Object> exe, FVector ref, FPoint arg) {

        Assertions.assertThrows(NullPointerException.class, ()
                        -> exe.apply(ref, null), "The reference cannot be null");

        FPoint refBaseContainer = ref.getRefBase();
        FPoint refHeadContainer = ref.getRefHead();

        FVector refSnapshot = ref.copy();
        FPoint argSnapshot = arg.copy();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refSnapshot.isExact(ref),
                        "The input position should not change"),
                () -> assertTrue(argSnapshot.isExact(arg),
                        "The argument position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, ref.getRefBase(),
                        "The input base reference should not change"),
                () -> assertSame(refHeadContainer, ref.getRefHead(),
                        "The input head reference should not change")
        );
    }
}

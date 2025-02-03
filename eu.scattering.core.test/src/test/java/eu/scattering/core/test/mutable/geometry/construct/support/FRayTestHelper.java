package eu.scattering.core.test.mutable.geometry.construct.support;

import eu.scattering.core.design.mutable.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import org.junit.jupiter.api.Assertions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FRayTestHelper {

    public static void testValue(BiFunction<FRay, FRay, Object> exe, FRay ref, FRay arg) {

        Assertions.assertThrows(NullPointerException.class, () -> exe.apply(ref, null),
                "The reference cannot be null");
        
        FVector refOriginSnapshot = ref.getRefOrigin();
        FVector refOriginPositionSnapshot = ref.getRefOrigin().copy();
        FPoint refBaseSnapshot = ref.getRefOrigin().getRefBase();
        FPoint refHeadSnapshot = ref.getRefOrigin().getRefHead();
        FVector argOriginSnapshot = arg.getRefOrigin();
        FVector argOriginPositionSnapshot = arg.getRefOrigin().copy();
        FPoint argBaseSnapshot = arg.getRefOrigin().getRefBase();
        FPoint argHeadSnapshot = arg.getRefOrigin().getRefHead();

        exe.apply(ref, arg);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refOriginPositionSnapshot.isExact(ref.getRefOrigin()),
                        "The input origin position should not change"),
                () -> assertTrue(argOriginPositionSnapshot.isExact(arg.getRefOrigin()),
                        "The argument origin position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refOriginSnapshot, refOriginSnapshot,
                        "The input origin reference should not change"),
                () -> assertSame(refBaseSnapshot, ref.getRefOrigin().getRefBase(),
                        "The input origin head reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getRefOrigin().getRefHead(),
                        "The input origin base reference should not change"),
                () -> assertSame(argOriginSnapshot, arg.getRefOrigin(),
                        "The argument origin reference should not change"),
                () -> assertSame(argBaseSnapshot, arg.getRefOrigin().getRefBase(),
                        "The argument origin base reference should not change"),
                () -> assertSame(argHeadSnapshot, arg.getRefOrigin().getRefHead(),
                        "The argument origin head reference should not change")
        );
    }

    public static void testValue(Function<FRay, Object> exe, FRay ref) {
        FVector refOriginSnapshot = ref.getRefOrigin();
        FVector refOriginPositionSnapshot = ref.getRefOrigin().copy();
        FPoint refBaseSnapshot = ref.getRefOrigin().getRefBase();
        FPoint refHeadSnapshot = ref.getRefOrigin().getRefHead();

        exe.apply(ref);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refOriginPositionSnapshot.isExact(ref.getRefOrigin()),
                        "The input origin position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refOriginSnapshot, refOriginSnapshot,
                        "The input origin reference should not change"),
                () -> assertSame(refBaseSnapshot, ref.getRefOrigin().getRefBase(),
                        "The input origin head reference should not change"),
                () -> assertSame(refHeadSnapshot, ref.getRefOrigin().getRefHead(),
                        "The input origin base reference should not change")
        );
    }
}

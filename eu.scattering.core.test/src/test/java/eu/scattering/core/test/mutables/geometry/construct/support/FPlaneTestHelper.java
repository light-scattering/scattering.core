package eu.scattering.core.test.mutables.geometry.construct.support;

import eu.scattering.core.design.mutables.geometry.construct.plane.FPlane;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FPlaneTestHelper {

    public static void testValue(Function<FPlane, Object> exe, FPlane ref) {
        FVector refOrigin = ref.getRefOrigin();

        FPoint refBaseContainer = refOrigin.getRefBase();
        FPoint refHeadContainer = refOrigin.getRefHead();

        FVector refOriginSnapshot = refOrigin.copy();

        exe.apply(ref);

        Assertions.assertAll("Validate positions",
                () -> assertTrue(refOriginSnapshot.isExact(ref.getRefOrigin()),
                        "The input origin position should not change")
        );

        Assertions.assertAll("Validate references",
                () -> assertSame(refBaseContainer, ref.getRefOrigin().getRefBase(),
                        "The origin head reference should not change"),
                () -> assertSame(refHeadContainer, ref.getRefOrigin().getRefHead(),
                        "The origin base reference should not change")
        );
    }
}

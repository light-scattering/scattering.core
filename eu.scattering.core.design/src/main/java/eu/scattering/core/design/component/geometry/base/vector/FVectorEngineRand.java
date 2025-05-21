package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FVectorEngineRand {

    // Alter the current state.

    FVector varyAngle(FVector in);

    // Reset the current state (the value of the input element is meaningless).

    FVector rndPos(FVector in, FPairPos3D range);

    FVector rndPosInSphere(FVector in, double radius);
    FVector rndPosOnSphere(FVector in, double radius);

    // The following methods were originally meant to be used with constructs, not base elements.
    // However, they are not marked as depreciated and will not be removed in the future.

    FPoint rndPosOnAxis(FPoint in, FVector dir);

    FPoint rndPosBaseInCircle(FPoint in, FVector dir, double radius);
    FPoint rndPosBaseOnCircle(FPoint in, FVector dir, double radius);

    FPoint rndPosHeadInCircle(FPoint in, FVector dir, double radius);
    FPoint rndPosHeadOnCircle(FPoint in, FVector dir, double radius);
}

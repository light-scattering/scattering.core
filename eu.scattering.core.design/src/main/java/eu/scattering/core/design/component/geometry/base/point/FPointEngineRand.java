package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FPointEngineRand {

    // Alter the current state.

    FPoint varyAngle(FPoint in);

    // Reset the current state (the value of the input element is meaningless).

    FPoint rndPosInRange(FPoint in, FPairPos3D range);

    FPoint rndPosInSphere(FPoint in, double radius);
    FPoint rndPosOnSphere(FPoint in, double radius);

    // The following methods were originally meant to be used with constructs, not base elements.
    // However, they are not marked as depreciated and will not be removed in the future.

    FPoint rndPosOnAxis(FPoint in, FPoint dir);

    FPoint rndPosBaseInCircle(FPoint in, FPoint dir, double radius);
    FPoint rndPosBaseOnCircle(FPoint in, FPoint dir, double radius);

    FPoint rndPosHeadInCircle(FPoint in, FPoint dir, double radius);
    FPoint rndPosHeadOnCircle(FPoint in, FPoint dir, double radius);
}

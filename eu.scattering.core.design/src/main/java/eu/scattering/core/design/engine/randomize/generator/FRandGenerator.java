package eu.scattering.core.design.engine.randomize.generator;

import eu.scattering.core.design.engine.randomize.generator.core.FRandCore;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.Optional;

public interface FRandGenerator extends FRandCore {

    Optional<Long> getSeed();

    Optional<Integer> getRetryLimit();
    void setRetryLimit(int retryLimit);
    void clearRetryLimit();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double min, double max);

    FPos2D nextDouble2D(FPairPos2D range);
    FPos3D nextDouble3D(FPairPos3D range);
    FPos4D nextDouble4D(FPairPos4D range);

    FPos2D nextDoubleOnCircle(double radius);
    FPos2D nextDoubleInCircle(double radius);

    FPos3D nextDoubleOnSphere(double radius);
    FPos3D nextDoubleInSphere(double radius);
}

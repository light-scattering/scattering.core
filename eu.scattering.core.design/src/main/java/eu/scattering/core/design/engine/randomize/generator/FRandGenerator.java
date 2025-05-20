package eu.scattering.core.design.engine.randomize.generator;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.engine.randomize.generator.core.FRandProcessorCore;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.Optional;

public interface FRandGenerator extends FRandProcessorCore {

    Optional<Long> getSeed();

    Optional<Integer> getRetryLimit();
    void setRetryLimit(int retryLimit);
    void clearRetryLimit();

    Optional<Double> getProximityLimit();
    void setProximityLimit(double proximityLimit);
    void clearProximityLimit();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double min, double max);
    double nextDouble(double min, double max, double... exclude);

    FPos2D nextDouble2D(FPairPos2D range, FPos2D... exclude);
    FPos3D nextDouble3D(FPairPos3D range, FPos3D... exclude);
    FPos4D nextDouble4D(FPairPos4D range, FPos4D... exclude);

    FPos2D nextDoubleOnCircle(double radius, FPos2D... exclude);
    FPos2D nextDoubleInCircle(double radius, FPos2D... exclude);

    FPos3D nextDoubleOnSphere(double radius, FPos3D... exclude);
    FPos3D nextDoubleInSphere(double radius, FPos3D... exclude);

    @Fragment
    boolean valExc1D(double value, double... exclude);
    @Fragment
    boolean valExc2D(FPos2D value, FPos2D... exclude);
    @Fragment
    boolean valExc3D(FPos3D value, FPos3D... exclude);
    @Fragment
    boolean valExc4D(FPos4D value, FPos4D... exclude);
}

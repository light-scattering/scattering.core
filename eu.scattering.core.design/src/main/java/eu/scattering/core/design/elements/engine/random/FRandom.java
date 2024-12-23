package eu.scattering.core.design.elements.engine.random;

import eu.scattering.core.design.annotations.IntermediateResults;
import eu.scattering.core.design.transfers.position.FPairPos2D;
import eu.scattering.core.design.transfers.position.FPairPos3D;
import eu.scattering.core.design.transfers.position.FPos2D;
import eu.scattering.core.design.transfers.position.FPos3D;
import eu.scattering.core.design.elements.engine.Engine;

import java.util.Optional;

public interface FRandom extends FRandomCore, Engine<FRandom> {

    Optional<Long> getSeed();

    Optional<Integer> getRetryLimit();
    void setRetryLimit(int retryLimit);
    void clearRetryLimit();

    Optional<Double> getProximityThreshold();
    void setProximityThreshold(double proximityThreshold);
    void clearProximityThreshold();

    boolean nextBoolean();

    double nextDouble();
    double nextDouble(double min, double max);
    double nextDouble(double min, double max, double... exclude);

    FPos2D nextDouble2D(FPairPos2D range, FPos2D... exclude);
    FPos3D nextDouble3D(FPairPos3D range, FPos3D... exclude);

    FPos2D nextDoubleOnCircle(double radius, FPos2D... exclude);
    FPos2D nextDoubleInCircle(double radius, FPos2D... exclude);

    FPos3D nextDoubleOnSphere(double radius, FPos3D... exclude);
    FPos3D nextDoubleInSphere(double radius, FPos3D... exclude);

    @IntermediateResults
    boolean valExc1D(double value, double... exclude);
    @IntermediateResults
    boolean valExc2D(FPos2D value, FPos2D... exclude);
    @IntermediateResults
    boolean valExc3D(FPos3D value, FPos3D... exclude);
}

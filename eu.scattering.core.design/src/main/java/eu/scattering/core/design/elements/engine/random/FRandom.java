package eu.scattering.core.design.elements.engine.random;

import eu.scattering.core.design.annotations.Utility;
import eu.scattering.core.design.elements.data.position.FPairPos2D;
import eu.scattering.core.design.elements.data.position.FPairPos3D;
import eu.scattering.core.design.elements.data.position.FPos2D;
import eu.scattering.core.design.elements.data.position.FPos3D;
import eu.scattering.core.design.elements.engine.Engine;

import java.util.Optional;

public interface FRandom extends FRandomCore, Engine<FRandom> {

    Optional<Long> getSeed();

    Optional<Integer> getRetryLimit();
    void setRetryLimit(int retryLimit);
    void clearRetryLimit();

    Optional<Double> getSeparationDistance();
    void setSeparationDistance(double separationDistance);
    void clearSeparationDistance();

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

    @Utility("Method for checking exclusion filters")
    boolean valExc1D(double value, double... exclude);
    @Utility("Method for checking exclusion filters")
    boolean valExc2D(FPos2D value, FPos2D... exclude);
    @Utility("Method for checking exclusion filters")
    boolean valExc3D(FPos3D value, FPos3D... exclude);
}

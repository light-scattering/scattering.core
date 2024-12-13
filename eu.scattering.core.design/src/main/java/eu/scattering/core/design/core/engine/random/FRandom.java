package eu.scattering.core.design.core.engine.random;

import eu.scattering.core.design.core.data.position.FPos3D;
import eu.scattering.core.design.core.engine.Engine;

import java.util.Optional;

public interface FRandom extends Engine<FRandom> {

    Optional<Long> getSeed();

    double getJitter();
    FRandom setJitter(double jitter);

    Optional<Double> getLimitMin();
    FRandom setLimitMin(double limitMin);
    FRandom removeLimitMin();

    Optional<Double> getLimitMax();
    FRandom setLimitMax(double limitMax);
    FRandom removeLimitMax();

//    double nextDouble();

    FPos3D getPositionOnUnitSphere();
}

package eu.scattering.core.design.core.engine.random;

import eu.scattering.core.design.core.engine.Engine;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.type.CoordinateSystemType;

public interface FRandom extends Engine<FRandom> {

    long getSeed();

    FPoint setRandomLocation(FPoint fPoint, CoordinateSystemType coordinates);
}

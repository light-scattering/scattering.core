package eu.scattering.core.design.core.engine.random;

import eu.scattering.core.design.core.data.position.FPos3D;
import eu.scattering.core.design.core.engine.Engine;
import eu.scattering.core.design.type.CoordinateSystemType;

public interface FRandom extends Engine<FRandom> {

    long getSeed();

    FPos3D getPositionOnSphere(CoordinateSystemType coordinates);
}

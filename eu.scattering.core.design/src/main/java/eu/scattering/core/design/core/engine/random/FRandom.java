package eu.scattering.core.design.core.engine.random;

import eu.scattering.core.design.core.data.position.FPos3D;
import eu.scattering.core.design.core.engine.Engine;
import eu.scattering.core.design.enums.CoordinateSystem;

public interface FRandom extends Engine<FRandom> {

    long getSeed();

    FPos3D getPositionOnSphere(CoordinateSystem coordinates);
}

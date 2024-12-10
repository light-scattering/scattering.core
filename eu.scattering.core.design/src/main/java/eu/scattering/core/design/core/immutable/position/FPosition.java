package eu.scattering.core.design.core.immutable.position;

import eu.scattering.core.design.core.immutable.Immutable;

public interface FPosition extends Immutable<FPosition> {

    int[] get();

    int getX();
    int getY();
    int getZ();
}

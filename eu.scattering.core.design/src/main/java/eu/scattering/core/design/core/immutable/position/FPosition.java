package eu.scattering.core.design.core.immutable.position;

import eu.scattering.core.design.core.Core;

public interface FPosition extends Core<FPosition> {

    int[] get();

    int getX();
    int getY();
    int getZ();
}

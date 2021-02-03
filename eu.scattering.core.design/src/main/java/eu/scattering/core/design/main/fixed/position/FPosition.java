package eu.scattering.core.design.main.fixed.position;

import eu.scattering.core.design.main.Main;

public interface FPosition extends Main<FPosition> {

    int[] get();

    int getX();
    int getY();
    int getZ();
}

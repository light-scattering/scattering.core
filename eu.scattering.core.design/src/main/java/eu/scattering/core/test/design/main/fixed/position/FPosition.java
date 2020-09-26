package eu.scattering.core.test.design.main.fixed.position;

import eu.scattering.core.test.design.main.Main;

public interface FPosition extends Main<FPosition> {

    int[] get();

    int getX();
    int getY();
    int getZ();
}

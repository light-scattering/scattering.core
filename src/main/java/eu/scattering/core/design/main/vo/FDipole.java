package eu.scattering.core.design.main.vo;

import eu.scattering.core.design.main.Main;

public interface FDipole extends Main<FDipole> {

    int[] getPosition();

    int getPositionX();
    int getPositionY();
    int getPositionZ();
}

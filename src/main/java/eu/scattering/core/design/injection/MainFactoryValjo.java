package eu.scattering.core.design.injection;

import eu.scattering.core.design.main.valjo.FDipole;

public interface MainFactoryValjo {

    FDipole getFDipole(int x, int y, int z);

    FDipole getFDipole(String position);
}

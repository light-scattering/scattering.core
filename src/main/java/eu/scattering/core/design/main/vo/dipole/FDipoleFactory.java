package eu.scattering.core.design.main.vo.dipole;

public interface FDipoleFactory {

    FDipole getFDipole(int x, int y, int z);

    FDipole getFDipole(String structure);

}

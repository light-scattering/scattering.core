package eu.scattering.core.design.physics.material.data;

import eu.scattering.core.design.physics.Physics;

public interface FMaterialData extends Physics {

    double getDensity();
    void setDensity(double density);

    double getRefIndexRe();
    void setRefIndexRe(double refIndexRe);

    double getRefIndexIm();
    void setRefIndexIm(double refIndexIm);

    FMaterialData copy();
    boolean isEqual(FMaterialData material);
}

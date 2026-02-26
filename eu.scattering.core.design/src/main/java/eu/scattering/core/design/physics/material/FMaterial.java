package eu.scattering.core.design.physics.material;

import eu.scattering.core.design.physics.Physics;
import eu.scattering.core.design.physics.material.data.FMaterialData;

public interface FMaterial extends Physics {

    int size();

    double getDensity(String tag);
    void setDensity(String tag, double density);

    double getRefIndexRe(String tag);
    void setRefIndexRe(String tag, double refIndexRe);

    double getRefIndexIm(String tag);
    void setRefIndexIm(String tag, double refIndexIm);

    void setRefIndex(String tag, double refIndexRe, double refIndexIm);

    //--------------------------------------------------

    FMaterialData getMaterial(String tag, boolean create);
    void addMaterial(String name, FMaterialData material);

    //--------------------------------------------------

    FMaterial copy();
    boolean isEqual(FMaterial material);
}

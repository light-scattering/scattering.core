package eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3D;

public interface FRandDist3DNormal extends FRandDist3D {

    FRandDist3DNormal setAvg(double d0, double d1, double d2);
    FRandDist3DNormal setStd(double d0, double d1, double d2);

    FRandDist3DNormal setCorD01(double d01);
    FRandDist3DNormal setCorD02(double d02);
    FRandDist3DNormal setCorD12(double d12);
}

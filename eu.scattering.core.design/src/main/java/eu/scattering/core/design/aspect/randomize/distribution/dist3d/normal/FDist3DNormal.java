package eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FDist3D;

public interface FDist3DNormal extends FDist3D {

    FDist3DNormal setAvg(double d0, double d1, double d2);
    FDist3DNormal setStd(double d0, double d1, double d2);

    FDist3DNormal setCorD01(double d01);
    FDist3DNormal setCorD02(double d02);
    FDist3DNormal setCorD12(double d12);
}

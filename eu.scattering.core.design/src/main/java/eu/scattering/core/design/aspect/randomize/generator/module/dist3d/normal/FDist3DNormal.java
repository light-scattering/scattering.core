package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.normal;

import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;

public interface FDist3DNormal extends FDist3D {

    FDist3DNormal setAvg(double avgX, double avgY, double avgZ);
    FDist3DNormal setStd(double stdX, double stdY, double stdZ);

    FDist3DNormal setCorXY(double corXY);
    FDist3DNormal setCorXZ(double corXZ);
    FDist3DNormal setCorYZ(double corYZ);
}

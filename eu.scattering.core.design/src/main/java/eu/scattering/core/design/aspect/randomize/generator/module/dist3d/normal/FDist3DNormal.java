package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.normal;

import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;

public interface FDist3DNormal extends FDist3D {

    FDist3DNormal setAvg(double avgD0, double avgD1, double avgD2);
    FDist3DNormal setStd(double stdD0, double stdD1, double stdD2);

    FDist3DNormal setCorD01(double corD01);
    FDist3DNormal setCorD02(double corD02);
    FDist3DNormal setCorD12(double corD12);
}

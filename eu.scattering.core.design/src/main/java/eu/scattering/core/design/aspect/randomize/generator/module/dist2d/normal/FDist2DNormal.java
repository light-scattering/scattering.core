package eu.scattering.core.design.aspect.randomize.generator.module.dist2d.normal;

import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.FDist2D;

public interface FDist2DNormal extends FDist2D {

    FDist2DNormal setAvg(double d0, double d1);
    FDist2DNormal setStd(double d0, double d1);

    FDist2DNormal setCor(double d01);
}

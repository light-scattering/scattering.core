package eu.scattering.core.design.aspect.randomize.distribution.dist2d.normal;

import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2D;

public interface FRandDist2DNormal extends FRandDist2D {

    FRandDist2DNormal setAvg(double d0, double d1);
    FRandDist2DNormal setStd(double d0, double d1);

    FRandDist2DNormal setCor(double d01);
}

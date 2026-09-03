package eu.scattering.core.design.aspect.randomize.distribution.dist2d.fixed;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FRandDist2DFixedFactory {

    FRandDist2DFixed fixed(double d0, double d1);

    FRandDist2DFixed fixed(FPos2D val);
}

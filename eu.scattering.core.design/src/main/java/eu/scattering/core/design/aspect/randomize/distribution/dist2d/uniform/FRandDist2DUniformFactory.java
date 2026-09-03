package eu.scattering.core.design.aspect.randomize.distribution.dist2d.uniform;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;

public interface FRandDist2DUniformFactory {

    FRandDist2DUniform uniform(double d0min, double d0max, double d1min, double d1max);

    FRandDist2DUniform uniform(FPairPos2D range);
}

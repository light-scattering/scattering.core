package eu.scattering.core.design.aspect.randomize.generator.module.dist2d.uniform;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;

public interface FDist2DUniformFactory {

    FDist2DUniform getFDist2DUniform(double d0min, double d0max, double d1min, double d1max);

    FDist2DUniform getFDist2DUniform(FPairPos2D range);
}

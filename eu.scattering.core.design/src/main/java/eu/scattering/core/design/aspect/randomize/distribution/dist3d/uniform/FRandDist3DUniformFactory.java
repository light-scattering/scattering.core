package eu.scattering.core.design.aspect.randomize.distribution.dist3d.uniform;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

public interface FRandDist3DUniformFactory {

    FRandDist3DUniform uniform(double d0min, double d0max, double d1min, double d1max, double d2min, double d2max);

    FRandDist3DUniform uniform(FPairPos3D range);
}

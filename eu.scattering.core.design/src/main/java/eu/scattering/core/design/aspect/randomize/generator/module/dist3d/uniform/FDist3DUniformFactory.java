package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.uniform;

import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;

public interface FDist3DUniformFactory {

    FDist3DUniform getFDist3DUniform(double d0min, double d0max, double d1min, double d1max, double d2min, double d2max);

    FDist3DUniform getFDist3DUniform(FPairPos3D range);
}

package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.uniform;

import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;

public interface FDist3DUniformFactory {

    FDist3DUniform getFDist3DUniform(double x1, double x2, double y1, double y2, double z1, double z2);

    FDist3DUniform getFDist3DUniform(FPairPos3D range);
}

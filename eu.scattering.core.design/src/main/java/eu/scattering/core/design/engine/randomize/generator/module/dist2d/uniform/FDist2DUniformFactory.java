package eu.scattering.core.design.engine.randomize.generator.module.dist2d.uniform;

import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;

public interface FDist2DUniformFactory {

    FDist2DUniform getFDist2DUniform(double x1, double x2, double y1, double y2);

    FDist2DUniform getFDist2DUniform(FPairPos2D range);
}

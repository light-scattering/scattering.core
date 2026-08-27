package eu.scattering.core.design.aspect.randomize.generator.module.dist2d.fixed;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FDist2DFixedFactory {

    FDist2DFixed getFDist2DFixed(double d0, double d1);

    FDist2DFixed getFDist2DFixed(FPos2D val);
}

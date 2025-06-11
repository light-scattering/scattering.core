package eu.scattering.core.design.engine.randomize.generator.module.dist2d.fixed;

import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;

public interface FDist2DFixedFactory {

    FDist2DFixed getFDist2DFixed(double x, double y);

    FDist2DFixed getFDist2DFixed(FPos2D val);
}

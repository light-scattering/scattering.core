package eu.scattering.core.design.engine.randomize.generator.module.dist2d.composite;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;

public interface FDist2DCompositeFactory {

    FDist2DComposite getFDist2DComposite(FDist1D dX, FDist1D dY);
}

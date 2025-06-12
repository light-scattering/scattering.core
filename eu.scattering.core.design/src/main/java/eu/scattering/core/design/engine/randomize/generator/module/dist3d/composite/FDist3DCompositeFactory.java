package eu.scattering.core.design.engine.randomize.generator.module.dist3d.composite;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;

public interface FDist3DCompositeFactory {

    FDist3DComposite getFDist3DComposite(FDist1D dX, FDist1D dY, FDist1D dZ);
}

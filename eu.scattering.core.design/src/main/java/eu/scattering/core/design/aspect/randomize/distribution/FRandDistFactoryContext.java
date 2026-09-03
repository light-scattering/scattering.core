package eu.scattering.core.design.aspect.randomize.distribution;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3DFactoryContext;

public interface FRandDistFactoryContext {

    FRandDist1DFactoryContext dist1D();
    FRandDist2DFactoryContext dist2D();
    FRandDist3DFactoryContext dist3D();
}

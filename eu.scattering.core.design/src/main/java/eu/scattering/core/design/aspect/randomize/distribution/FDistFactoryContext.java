package eu.scattering.core.design.aspect.randomize.distribution;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FDist2DFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FDist3DFactoryContext;

public interface FDistFactoryContext {

    FDist1DFactoryContext d1();
    FDist2DFactoryContext d2();
    FDist3DFactoryContext d3();
}

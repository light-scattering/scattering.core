package eu.scattering.core.design.aspect.randomize;

import eu.scattering.core.design.aspect.Aspect;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1DFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2DFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3DFactory;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngineFactory;
import eu.scattering.core.design.aspect.randomize.mutation.FRandMutation;

public interface FRandAspect extends Aspect, FRandEngineFactory {

    FRandMutation mutate();

    FRandDist1DFactory dist1D();
    FRandDist2DFactory dist2D();
    FRandDist3DFactory dist3D();
}

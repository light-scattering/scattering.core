package eu.scattering.core.design.aspect.randomize.distribution.dist2d;

import eu.scattering.core.design.aspect.randomize.distribution.dist2d.fixed.FRandDist2DFixedFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.joint.FRandDist2DJointFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.custom.FRandDist2DCustomFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.normal.FRandDist2DNormalFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.uniform.FRandDist2DUniformFactory;

public interface FRandDist2DFactoryContext extends
        FRandDist2DCustomFactory,
        FRandDist2DJointFactory,
        FRandDist2DFixedFactory,
        FRandDist2DNormalFactory,
        FRandDist2DUniformFactory {
}

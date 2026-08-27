package eu.scattering.core.design.aspect.randomize.generator.module.dist2d.joint;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;

public interface FDist2DJointFactory {

    FDist2DJoint getFDist2DJoint(FDist1D d0, FDist1D d1);
}

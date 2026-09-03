package eu.scattering.core.design.aspect.randomize.distribution.dist2d.joint;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;

public interface FRandDist2DJointFactory {

    FRandDist2DJoint joint(FRandDist1D d0, FRandDist1D d1);
}

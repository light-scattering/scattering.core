package eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;

public interface FRandDist3DJointFactory {

    FRandDist3DJoint joint(FRandDist1D d0, FRandDist1D d1, FRandDist1D d2);
}

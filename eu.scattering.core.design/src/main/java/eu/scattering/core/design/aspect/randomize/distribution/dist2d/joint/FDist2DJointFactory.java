package eu.scattering.core.design.aspect.randomize.distribution.dist2d.joint;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;

public interface FDist2DJointFactory {

    FDist2DJoint joint(FDist1D d0, FDist1D d1);
}

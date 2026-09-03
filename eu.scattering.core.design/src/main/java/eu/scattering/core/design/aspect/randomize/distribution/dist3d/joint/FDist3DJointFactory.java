package eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;

public interface FDist3DJointFactory {

    FDist3DJoint joint(FDist1D d0, FDist1D d1, FDist1D d2);
}

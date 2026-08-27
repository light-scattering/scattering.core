package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.joint;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;

public interface FDist3DJointFactory {

    FDist3DJoint getFDist3DJoint(FDist1D d0, FDist1D d1, FDist1D d2);
}

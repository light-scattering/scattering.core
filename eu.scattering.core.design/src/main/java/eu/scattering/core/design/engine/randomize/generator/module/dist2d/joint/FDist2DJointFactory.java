package eu.scattering.core.design.engine.randomize.generator.module.dist2d.joint;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;

public interface FDist2DJointFactory {

    FDist2DJoint getFDist2DJoint(FDist1D dX, FDist1D dY);
}

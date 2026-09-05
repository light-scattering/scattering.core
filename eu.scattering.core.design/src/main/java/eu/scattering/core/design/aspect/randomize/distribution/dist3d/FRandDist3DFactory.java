package eu.scattering.core.design.aspect.randomize.distribution.dist3d;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint.FRandDist3DJointFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.fixed.FRandDist3DFixedFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom.FRandDist3DCustomFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal.FRandDist3DNormalFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.uniform.FRandDist3DUniformFactory;

public interface FRandDist3DFactory extends
        FRandDist3DCustomFactory,
        FRandDist3DJointFactory,
        FRandDist3DFixedFactory,
        FRandDist3DNormalFactory,
        FRandDist3DUniformFactory {
}

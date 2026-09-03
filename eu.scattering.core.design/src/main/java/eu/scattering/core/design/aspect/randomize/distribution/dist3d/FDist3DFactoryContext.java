package eu.scattering.core.design.aspect.randomize.distribution.dist3d;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint.FDist3DJointFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.fixed.FDist3DFixedFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom.FDist3DCustomFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal.FDist3DNormalFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.uniform.FDist3DUniformFactory;

public interface FDist3DFactoryContext extends
        FDist3DCustomFactory,
        FDist3DJointFactory,
        FDist3DFixedFactory,
        FDist3DNormalFactory,
        FDist3DUniformFactory {
}

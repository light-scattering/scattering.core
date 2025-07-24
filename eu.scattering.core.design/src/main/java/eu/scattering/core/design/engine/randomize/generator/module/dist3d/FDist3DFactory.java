package eu.scattering.core.design.engine.randomize.generator.module.dist3d;

import eu.scattering.core.design.engine.randomize.generator.module.dist3d.joint.FDist3DJointFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.fixed.FDist3DFixedFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.custom.FDist3DCustomFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.uniform.FDist3DUniformFactory;

public interface FDist3DFactory extends
        FDist3DCustomFactory,
        FDist3DJointFactory,
        FDist3DFixedFactory,
        FDist3DUniformFactory {
}

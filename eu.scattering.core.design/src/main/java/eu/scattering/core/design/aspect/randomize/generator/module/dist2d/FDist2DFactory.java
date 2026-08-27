package eu.scattering.core.design.aspect.randomize.generator.module.dist2d;

import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.fixed.FDist2DFixedFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.joint.FDist2DJointFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.custom.FDist2DCustomFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.normal.FDist2DNormalFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.uniform.FDist2DUniformFactory;

public interface FDist2DFactory extends
        FDist2DCustomFactory,
        FDist2DJointFactory,
        FDist2DFixedFactory,
        FDist2DNormalFactory,
        FDist2DUniformFactory {
}

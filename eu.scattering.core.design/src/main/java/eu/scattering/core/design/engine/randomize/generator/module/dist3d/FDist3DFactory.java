package eu.scattering.core.design.engine.randomize.generator.module.dist3d;

import eu.scattering.core.design.engine.randomize.generator.module.dist3d.composite.FDist3DCompositeFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.fixed.FDist3DFixedFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.uniform.FDist3DUniformFactory;

public interface FDist3DFactory extends
        FDist3DCompositeFactory,
        FDist3DFixedFactory,
        FDist3DUniformFactory {
}

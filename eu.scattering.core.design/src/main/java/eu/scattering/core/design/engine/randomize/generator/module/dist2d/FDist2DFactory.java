package eu.scattering.core.design.engine.randomize.generator.module.dist2d;

import eu.scattering.core.design.engine.randomize.generator.module.dist2d.composite.FDist2DCompositeFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.fixed.FDist2DFixedFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.uniform.FDist2DUniformFactory;

public interface FDist2DFactory extends
        FDist2DCompositeFactory,
        FDist2DFixedFactory,
        FDist2DUniformFactory {
}

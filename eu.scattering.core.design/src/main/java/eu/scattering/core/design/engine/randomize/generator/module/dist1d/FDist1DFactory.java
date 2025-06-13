package eu.scattering.core.design.engine.randomize.generator.module.dist1d;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.manual.FDist1DManualFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.fixed.FDist1DFixedFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.uniform.FDist1DUniformFactory;

public interface FDist1DFactory extends
        FDist1DManualFactory,
        FDist1DFixedFactory,
        FDist1DUniformFactory {
}

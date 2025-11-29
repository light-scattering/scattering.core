package eu.scattering.core.design.aspect.randomize.generator.module.dist1d;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.custom.FDist1DCustomFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.fixed.FDist1DFixedFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.normal.FDist1DNormalFactory;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.uniform.FDist1DUniformFactory;

public interface FDist1DFactory extends
        FDist1DCustomFactory,
        FDist1DFixedFactory,
        FDist1DNormalFactory,
        FDist1DUniformFactory {
}

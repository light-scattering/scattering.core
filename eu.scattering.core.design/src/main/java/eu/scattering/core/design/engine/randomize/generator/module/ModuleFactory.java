package eu.scattering.core.design.engine.randomize.generator.module;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1DFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.FDist2DFactory;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3DFactory;

public interface ModuleFactory extends
        FDist1DFactory,
        FDist2DFactory,
        FDist3DFactory {
}

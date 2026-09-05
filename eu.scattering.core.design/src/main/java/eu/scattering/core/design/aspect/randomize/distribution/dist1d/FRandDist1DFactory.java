package eu.scattering.core.design.aspect.randomize.distribution.dist1d;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.custom.FRandDist1DCustomFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.fixed.FRandDist1DFixedFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal.FRandDist1DNormalFactory;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.uniform.FRandDist1DUniformFactory;

public interface FRandDist1DFactory extends
        FRandDist1DCustomFactory,
        FRandDist1DFixedFactory,
        FRandDist1DNormalFactory,
        FRandDist1DUniformFactory {
}

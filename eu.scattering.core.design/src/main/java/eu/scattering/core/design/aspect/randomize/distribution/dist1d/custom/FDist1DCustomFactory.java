package eu.scattering.core.design.aspect.randomize.distribution.dist1d.custom;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist1DCustomFactory {

    FDist1DCustom custom(BiConsumer<FRandGenerator, Double[]> consumer);
}

package eu.scattering.core.design.aspect.randomize.distribution.dist2d.custom;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist2DCustomFactory {

    FDist2DCustom custom(BiConsumer<FRandGenerator, Double[]> consumer);
}

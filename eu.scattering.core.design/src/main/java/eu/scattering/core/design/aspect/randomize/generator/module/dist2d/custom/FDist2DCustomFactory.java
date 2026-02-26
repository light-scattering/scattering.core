package eu.scattering.core.design.aspect.randomize.generator.module.dist2d.custom;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist2DCustomFactory {

    FDist2DCustom getFDist2DManual(BiConsumer<FRandGenerator, Double[]> consumer);
}

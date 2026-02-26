package eu.scattering.core.design.aspect.randomize.generator.module.dist1d.custom;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist1DCustomFactory {

    FDist1DCustom getFDist1DManual(BiConsumer<FRandGenerator, Double[]> consumer);
}

package eu.scattering.core.design.aspect.randomize.distribution.dist3d.custom;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist3DCustomFactory {

    FDist3DCustom custom(BiConsumer<FRandGenerator, Double[]> consumer);
}

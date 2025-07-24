package eu.scattering.core.design.engine.randomize.generator.module.dist3d.custom;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist3DCustomFactory {

    FDist3DCustom getFDist3DManual(BiConsumer<FRandGenerator, Double[]> consumer);
}

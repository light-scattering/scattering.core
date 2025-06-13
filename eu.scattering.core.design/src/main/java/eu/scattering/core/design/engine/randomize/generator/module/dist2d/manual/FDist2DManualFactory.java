package eu.scattering.core.design.engine.randomize.generator.module.dist2d.manual;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist2DManualFactory {

    FDist2DManual getFDist2DManual(BiConsumer<FRandGenerator, Double[]> consumer);
}

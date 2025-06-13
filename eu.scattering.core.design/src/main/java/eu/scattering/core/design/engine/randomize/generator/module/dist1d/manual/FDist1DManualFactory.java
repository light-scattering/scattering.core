package eu.scattering.core.design.engine.randomize.generator.module.dist1d.manual;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist1DManualFactory {

    FDist1DManual getFDist1DManual(BiConsumer<FRandGenerator, Double[]> consumer);
}

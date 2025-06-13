package eu.scattering.core.design.engine.randomize.generator.module.dist3d.manual;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;

import java.util.function.BiConsumer;

public interface FDist3DManualFactory {

    FDist3DManual getFDist3DManual(BiConsumer<FRandGenerator, Double[]> consumer);
}

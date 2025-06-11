package eu.scattering.core.design.engine.randomize.generator;

public interface FRandGeneratorFactory {

    FRandGenerator getFRandGenerator();

    FRandGenerator spawnFRandGenerator(long seed);
}

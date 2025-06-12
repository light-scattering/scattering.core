package eu.scattering.core.design.engine.randomize.generator;

public interface FRandGeneratorFactory {

    FRandGenerator getFRandGenShared();

    FRandGenerator getFRandGen(long seed);
}

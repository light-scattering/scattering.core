package eu.scattering.core.design.mutables.engine.random;

public interface FRandomFactory {

    FRandom getFRandom();
    FRandom getFRandom(long seed);
}

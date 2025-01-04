package eu.scattering.core.design.engines.random;

public interface FRandomFactory {

    FRandom getFRandom();
    FRandom getFRandom(long seed);
}

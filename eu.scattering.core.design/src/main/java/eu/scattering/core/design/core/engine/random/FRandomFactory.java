package eu.scattering.core.design.core.engine.random;

public interface FRandomFactory {

    FRandom getFRandom();
    FRandom getFRandom(long seed);
}

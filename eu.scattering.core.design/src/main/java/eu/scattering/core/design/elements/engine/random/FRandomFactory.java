package eu.scattering.core.design.elements.engine.random;

public interface FRandomFactory {

    FRandom getFRandom();
    FRandom getFRandom(long seed);
}

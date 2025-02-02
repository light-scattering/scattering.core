package eu.scattering.core.design.engines.rand.processor;

public interface FRandProcessorFactory {

    FRandProcessor getFRandProcessor();
    FRandProcessor getFRandProcessor(long seed);
}

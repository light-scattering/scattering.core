package eu.scattering.core.design.engine.randomize.processor;

public interface FRandProcessorFactory {

    FRandProcessor getFRandProcessor();
    FRandProcessor getFRandProcessor(long seed);
}

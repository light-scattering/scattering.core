package eu.scattering.core.design.engines.random.processor;

public interface FRandomProcessorFactory {

    FRandomProcessor getFRandomProcessor();
    FRandomProcessor getFRandomProcessor(long seed);
}

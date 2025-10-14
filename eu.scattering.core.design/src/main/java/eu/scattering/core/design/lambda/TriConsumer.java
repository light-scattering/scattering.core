package eu.scattering.core.design.lambda;

@FunctionalInterface
public interface TriConsumer<T, S, U> {

    void accept(T inA, S inB, U inC);
}

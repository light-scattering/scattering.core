package eu.scattering.core.design.lambda;

@FunctionalInterface
public interface TriFunction<T, S, U, V> {

    V accept(T inA, S inB, U inC);
}

package eu.scattering.core.design.utility.lambda;

@FunctionalInterface
public interface TriFunction<T, S, U, V> {

    V accept(T inA, S inB, U inC);
}

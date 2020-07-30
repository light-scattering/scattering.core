package eu.scattering.core;

public abstract class CoreObject {

    @Override
    public abstract Object clone();

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();
}

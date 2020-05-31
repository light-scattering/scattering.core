package eu.scattering.core;

public abstract class CoreObject {

    @Override
    abstract public boolean equals(Object object);

    @Override
    abstract public int hashCode();

    @Override
    abstract public String toString();

    @Override
    abstract public Object clone();
}

package eu.scattering.core.geometry;

public interface IBaseObject<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    int getHashCode();

    String exportToJSON();

    T importFromJSON();

    T copy();

}

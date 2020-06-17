package eu.scattering.core;

public interface ICoreObjectFeatures<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    int getHashCode();

    String exportToJSON();

    T importFromJSON(String json);

    T copy();

}

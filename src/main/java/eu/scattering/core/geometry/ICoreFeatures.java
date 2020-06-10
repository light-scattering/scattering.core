package eu.scattering.core.geometry;

public interface ICoreFeatures<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    int getHashCode();

    String exportToJSON();

    T importFromJSON(String json);

    T copy();

}

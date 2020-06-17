package eu.scattering.core.geometry;

public interface IGeometryBase<T> {

    boolean isExact(Object element);

    boolean isSimilar(Object element);

    int getHashCode();

    String exportToJSON();

    T importFromJSON(String json);

    T copy();

}

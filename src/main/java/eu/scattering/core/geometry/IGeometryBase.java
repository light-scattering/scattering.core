package eu.scattering.core.geometry;

public interface IGeometryBase<T> {

    boolean isExact(T element);

    boolean isSimilar(T element);

    String exportToJSON();

    T importFromJSON(String json);

    T copy();

}

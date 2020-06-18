package eu.scattering.core.geometry;

public interface IGeometryDebug<T> {

    T devDescribe();

    T devDescribe(String message);

    T devStore(T element);

}

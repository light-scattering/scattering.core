package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public interface Shape<T> extends Geometry {

    T setPosCenter(double x, double y, double z);
    T setPosCenter(FPoint fPoint);
    T setPosCenter(FPos3D fPos3D);

    T setPosCenterX(double x);
    T setPosCenterY(double Y);
    T setPosCenterZ(double z);

    void getPosCenter(FPoint in);

    boolean contains(double x, double y, double z);
    boolean contains(FPoint fPoint);
    boolean contains(FPos3D fPos3D);

    boolean encloses(T shape, double epsilon);

    boolean touches(T shape, double epsilon);
    boolean overlaps(T shape, double epsilon);
    boolean intersects(T shape, double epsilon);

    double getVolume();
    T setVolume(double volume);

    double getSurface();
    T setSurface(double surface);

    double getOuterRadius();
    T setOuterRadius(double radius);

    double getInnerRadius();
    T setInnerRadius(double radius);

    void getVolumeStream(FStream3DI stream, double delta);
    void getVolumeStream(FStream3D stream, double delta);

    void getSurfaceStream(FStream3DI stream, double delta);
    void getSurfaceStream(FStream3D stream, double delta);
}

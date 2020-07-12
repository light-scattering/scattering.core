package eu.scattering.core.geometry.support;

import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.base.point.IFPoint;

import java.time.LocalTime;

import static eu.scattering.core.Configuration.debugPrintStream;

public abstract class PresetSupport<T extends ISupport<T>>
        implements ISupport<T>, IGeometryBase<T>, IDebug<T> {

    @Override
    public abstract Object clone();

    @Override
    public abstract boolean equals(Object object);

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return exportToJSON().toString();
    }

    @Override
    public int hashCode() {
        int hashCode = 7;

        for (IFPoint fPoint : getOrigin().disassemble()) {
            hashCode = 31 * hashCode + (int) (fPoint.getX() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getY() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getZ() * 100);
        }

        return hashCode;
    }

    @Override
    public boolean isExact(T element) {

        return getOrigin().isExact(element.getOrigin());
    }

    @Override
    public boolean isSimilar(T element) {

        return getOrigin().isSimilar(element.getOrigin());
    }

    @Override
    public IFPoint getBase() {

        return getOrigin().getBase();
    }

    @Override
    public IFPoint getHead() {

        return getOrigin().getHead();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public T devDescribe() {

        debugPrintStream.println(LocalTime.now().toString()
                + " - " + self().getClass().getSimpleName()
                + " - " + toString());

        return self();
    }

    @Override
    public T devDescribe(String message) {

        debugPrintStream.println(message
                + " / " + LocalTime.now().toString()
                + " - " + self().getClass().getSimpleName()
                + " - " + toString());

        return self();
    }

}


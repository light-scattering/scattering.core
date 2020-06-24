package eu.scattering.core.geometry.support;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.json.JSONObject;

import static eu.scattering.core.Configuration.debugPrintStream;

public abstract class PresetGeometry<T extends IGeometrySupport<T>>
        implements IGeometrySupport<T>, IGeometryBase<T>, IDebug<T> {

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
    public JSONObject exportToJSON() {

        return getOrigin().exportToJSON();
    }

    @Override
    public T importFromJSON(JSONObject json) {
        T origin = copy();

        origin.getOrigin().set(FactoryGeometry.getIFVector().importFromJSON(json));

        return origin;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public T devDescribe() {

        debugPrintStream.println(toString());

        return self();
    }

    @Override
    public T devDescribe(String message) {

        debugPrintStream.println(message + " - " + toString());

        return self();
    }

}


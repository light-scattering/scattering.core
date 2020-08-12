package eu.scattering.core.geometry.support;

import eu.scattering.core.geometry.PresetGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;

public abstract class PresetSupport<T extends ISupport<T>>
        extends PresetGeometry<T> implements ISupport<T> {

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

}


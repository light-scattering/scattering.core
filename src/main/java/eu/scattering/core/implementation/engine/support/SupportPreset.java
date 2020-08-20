package eu.scattering.core.implementation.engine.support;

import eu.scattering.core.implementation.engine.EnginePreset;
import eu.scattering.core.design.engine.base.point.FPoint;
import eu.scattering.core.design.engine.support.Support;

public abstract class SupportPreset<T extends Support<T>> extends EnginePreset<T> implements Support<T> {

    @Override
    public int hashCode() {
        int hashCode = 7;

        for (FPoint fPoint : getOrigin().disassemble()) {
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
    public FPoint getBase() {

        return getOrigin().getBase();
    }

    @Override
    public FPoint getHead() {

        return getOrigin().getHead();
    }

}


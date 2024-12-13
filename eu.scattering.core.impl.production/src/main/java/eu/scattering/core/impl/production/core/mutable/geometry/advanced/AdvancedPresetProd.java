package eu.scattering.core.impl.production.core.mutable.geometry.advanced;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.construct.Construct;

import java.util.List;

public abstract class AdvancedPresetProd<T extends Construct<T>> implements Construct<T> {

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

        return getOrigin().getRefBase();
    }

    @Override
    public FPoint getHead() {

        return getOrigin().getRefHead();
    }

    @Override
    public List<FPoint> disassemble() {

        return getOrigin().disassemble();
    }

}
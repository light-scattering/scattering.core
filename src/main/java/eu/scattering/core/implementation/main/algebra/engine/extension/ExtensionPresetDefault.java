package eu.scattering.core.implementation.main.algebra.engine.extension;

import eu.scattering.core.implementation.main.algebra.AlgebraPresetDefault;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.extension.Extension;

import java.util.List;

public abstract class ExtensionPresetDefault<T extends Extension<T>> extends AlgebraPresetDefault<T> implements Extension<T> {

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

    @Override
    public List<FPoint> disassemble() {

        return getOrigin().disassemble();
    }


}


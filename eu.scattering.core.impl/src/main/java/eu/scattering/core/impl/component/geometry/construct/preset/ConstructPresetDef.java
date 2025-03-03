package eu.scattering.core.impl.component.geometry.construct.preset;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.construct.Construct;

import java.util.List;

public abstract class ConstructPresetDef<T extends Construct<T>> implements Construct<T> {

    @Override
    public int hashCode() {
        int hashCode = 7;

        for (FPoint fPoint : getRefOrigin().disassemble()) {
            hashCode = 31 * hashCode + (int) (fPoint.getX() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getY() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getZ() * 100);
        }

        return hashCode;
    }

    @Override
    public boolean isExact(T arg) {

        return getRefOrigin().isExact(arg.getRefOrigin());
    }

    @Override
    public boolean isSimilar(T arg) {

        return getRefOrigin().isSimilar(arg.getRefOrigin());
    }

    @Override
    public List<FPoint> disassemble() {

        return getRefOrigin().disassemble();
    }

}
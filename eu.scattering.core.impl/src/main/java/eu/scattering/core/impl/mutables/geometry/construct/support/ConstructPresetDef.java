package eu.scattering.core.impl.mutables.geometry.construct.support;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.construct.Construct;

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
    public boolean isExact(T ref) {

        return getRefOrigin().isExact(ref.getRefOrigin());
    }

    @Override
    public boolean isSimilar(T ref) {

        return getRefOrigin().isSimilar(ref.getRefOrigin());
    }

    @Override
    public List<FPoint> disassemble() {

        return getRefOrigin().disassemble();
    }

}
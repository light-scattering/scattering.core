package eu.scattering.core.impl.production.mutables.algebra.geometry.construct;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.construct.Construct;

import java.util.List;

public abstract class AdvancedPresetDef<T extends Construct<T>> implements Construct<T> {

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
    public boolean isExact(T ref) {

        return getOrigin().isExact(ref.getOrigin());
    }

    @Override
    public boolean isSimilar(T ref) {

        return getOrigin().isSimilar(ref.getOrigin());
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
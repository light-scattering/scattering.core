package eu.scattering.core.impl.component.geometry.base.preset;

import eu.scattering.core.design.component.geometry.base.Base;
import eu.scattering.core.design.component.geometry.base.point.FPoint;

public abstract class BasePresetDef<T extends Base<T>> implements Base<T> {

    @Override
    public T applyStateTo(T arg) {

        arg.set(self());

        return self();
    }

    @Override
    public T addXYZ(FPoint arg) {

        toFPoints().forEach(e -> e.addXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T addXYZ(double x, double y, double z) {

        toFPoints().forEach(e -> e.addX(x).addY(y).addZ(z));

        return self();
    }

    @Override
    public T addFactor(double factor) {

        toFPoints().forEach(e -> e.addXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T addX(double x) {

        toFPoints().forEach(e -> e.setX(e.getX() + x));

        return self();
    }

    @Override
    public T addY(double y) {

        toFPoints().forEach(e -> e.setY(e.getY() + y));

        return self();
    }

    @Override
    public T addZ(double z) {

        toFPoints().forEach(e -> e.setZ(e.getZ() + z));

        return self();
    }

    @Override
    public T subXYZ(FPoint arg) {

        toFPoints().forEach(e -> e.subXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T subXYZ(double x, double y, double z) {

        toFPoints().forEach(e -> e.subX(x).subY(y).subZ(z));

        return self();
    }

    @Override
    public T subFactor(double factor) {

        toFPoints().forEach(e -> e.subXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T subX(double x) {

        toFPoints().forEach(e -> e.setX(e.getX() - x));

        return self();
    }

    @Override
    public T subY(double y) {

        toFPoints().forEach(e -> e.setY(e.getY() - y));

        return self();
    }

    @Override
    public T subZ(double z) {

        toFPoints().forEach(e -> e.setZ(e.getZ() - z));

        return self();
    }

    @Override
    public T mulXYZ(FPoint arg) {

        toFPoints().forEach(e -> e.mulXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T mulXYZ(double x, double y, double z) {

        toFPoints().forEach(e -> e.mulX(x).mulY(y).mulZ(z));

        return self();
    }

    @Override
    public T mulFactor(double factor) {

        toFPoints().forEach(e -> e.mulXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T mulX(double x) {

        toFPoints().forEach(e -> e.setX(e.getX() * x));

        return self();
    }

    @Override
    public T mulY(double y) {

        toFPoints().forEach(e -> e.setY(e.getY() * y));

        return self();
    }

    @Override
    public T mulZ(double z) {

        toFPoints().forEach(e -> e.setZ(e.getZ() * z));

        return self();
    }

    @Override
    public T divXYZ(FPoint arg) {

        toFPoints().forEach(e -> e.divXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T divXYZ(double x, double y, double z) {

        toFPoints().forEach(e -> e.divX(x).divY(y).divZ(z));

        return self();
    }

    @Override
    public T divFactor(double factor) {

        toFPoints().forEach(e -> e.divXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T divX(double x) {

        toFPoints().forEach(e -> {

            if (x == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setX(e.getX() / x);
        });

        return self();
    }

    @Override
    public T divY(double y) {

        toFPoints().forEach(e -> {

            if (y == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setY(e.getY() / y);
        });

        return self();
    }

    @Override
    public T divZ(double z) {

        toFPoints().forEach(e -> {

            if (z == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setZ(e.getZ() / z);
        });

        return self();
    }
}
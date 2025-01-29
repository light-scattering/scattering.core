package eu.scattering.core.impl.mutables.geometry.primitive.support;

import eu.scattering.core.design.mutables.geometry.primitive.Primitive;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;

public abstract class PrimitivePresetDef<T extends Primitive<T>> implements Primitive<T> {

    @Override
    public T applyStateTo(T arg) {

        arg.applyStateFrom(self());

        return self();
    }

    @Override
    public T addXYZ(FPoint arg) {

        disassemble().forEach(e -> e.addXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T addXYZ(double x, double y, double z) {

        disassemble().forEach(e -> e.addX(x).addY(y).addZ(z));

        return self();
    }

    @Override
    public T addFactor(double factor) {

        disassemble().forEach(e -> e.addXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T addX(double x) {

        disassemble().forEach(e -> e.setX(e.getX() + x));

        return self();
    }

    @Override
    public T addY(double y) {

        disassemble().forEach(e -> e.setY(e.getY() + y));

        return self();
    }

    @Override
    public T addZ(double z) {

        disassemble().forEach(e -> e.setZ(e.getZ() + z));

        return self();
    }

    @Override
    public T subXYZ(FPoint arg) {

        disassemble().forEach(e -> e.subXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T subXYZ(double x, double y, double z) {

        disassemble().forEach(e -> e.subX(x).subY(y).subZ(z));

        return self();
    }

    @Override
    public T subFactor(double factor) {

        disassemble().forEach(e -> e.subXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T subX(double x) {

        disassemble().forEach(e -> e.setX(e.getX() - x));

        return self();
    }

    @Override
    public T subY(double y) {

        disassemble().forEach(e -> e.setY(e.getY() - y));

        return self();
    }

    @Override
    public T subZ(double z) {

        disassemble().forEach(e -> e.setZ(e.getZ() - z));

        return self();
    }

    @Override
    public T mulXYZ(FPoint arg) {

        disassemble().forEach(e -> e.mulXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T mulXYZ(double x, double y, double z) {

        disassemble().forEach(e -> e.mulX(x).mulY(y).mulZ(z));

        return self();
    }

    @Override
    public T mulFactor(double factor) {

        disassemble().forEach(e -> e.mulXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T mulX(double x) {

        disassemble().forEach(e -> e.setX(e.getX() * x));

        return self();
    }

    @Override
    public T mulY(double y) {

        disassemble().forEach(e -> e.setY(e.getY() * y));

        return self();
    }

    @Override
    public T mulZ(double z) {

        disassemble().forEach(e -> e.setZ(e.getZ() * z));

        return self();
    }

    @Override
    public T divXYZ(FPoint arg) {

        disassemble().forEach(e -> e.divXYZ(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T divXYZ(double x, double y, double z) {

        disassemble().forEach(e -> e.divX(x).divY(y).divZ(z));

        return self();
    }

    @Override
    public T divFactor(double factor) {

        disassemble().forEach(e -> e.divXYZ(factor, factor, factor));

        return self();
    }

    @Override
    public T divX(double x) {

        disassemble().forEach(e -> {

            if (x == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setX(e.getX() / x);
        });

        return self();
    }

    @Override
    public T divY(double y) {

        disassemble().forEach(e -> {

            if (y == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setY(e.getY() / y);
        });

        return self();
    }

    @Override
    public T divZ(double z) {

        disassemble().forEach(e -> {

            if (z == 0) {
                throw new ArithmeticException("Division by zero");
            }

            e.setZ(e.getZ() / z);
        });

        return self();
    }
}
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
    public T add(FPoint arg) {

        disassemble().forEach(e -> e.add(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T add(double x, double y, double z) {

        disassemble().forEach(e -> e.addX(x).addY(y).addZ(z));

        return self();
    }

    @Override
    public T add(double factor) {

        disassemble().forEach(e -> e.add(factor, factor, factor));

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
    public T sub(FPoint arg) {

        disassemble().forEach(e -> e.sub(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T sub(double x, double y, double z) {

        disassemble().forEach(e -> e.subX(x).subY(y).subZ(z));

        return self();
    }

    @Override
    public T sub(double factor) {

        disassemble().forEach(e -> e.sub(factor, factor, factor));

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
    public T mul(FPoint arg) {

        disassemble().forEach(e -> e.mul(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T mul(double x, double y, double z) {

        disassemble().forEach(e -> e.mulX(x).mulY(y).mulZ(z));

        return self();
    }

    @Override
    public T mul(double factor) {

        disassemble().forEach(e -> e.mul(factor, factor, factor));

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
    public T div(FPoint arg) {

        disassemble().forEach(e -> e.div(arg.getX(), arg.getY(), arg.getZ()));

        return self();
    }

    @Override
    public T div(double x, double y, double z) {

        disassemble().forEach(e -> e.divX(x).divY(y).divZ(z));

        return self();
    }

    @Override
    public T div(double factor) {

        disassemble().forEach(e -> e.div(factor, factor, factor));

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
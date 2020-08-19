package eu.scattering.core.implementation.main.engine.base;

import eu.scattering.core.implementation.main.engine.EnginePreset;
import eu.scattering.core.design.main.engine.base.Base;
import eu.scattering.core.design.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.main.engine.base.point.FPoint;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class BasePreset<T extends Base<T>> extends EnginePreset<T> implements Base<T> {

    @Override
    public int hashCode() {
        int hashCode = 7;

        for (FPoint fPoint : disassemble()) {
            hashCode = 31 * hashCode + (int) (fPoint.getX() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getY() * 100);
            hashCode = 31 * hashCode + (int) (fPoint.getZ() * 100);
        }

        return hashCode;
    }

    @Override
    public T add(FPoint fPoint) {

        disassemble().forEach(e -> e.add(fPoint.getX(), fPoint.getY(), fPoint.getZ()));

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
    public T sub(FPoint fPoint) {

        disassemble().forEach(e -> e.sub(fPoint.getX(), fPoint.getY(), fPoint.getZ()));

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
    public T mul(FPoint fPoint) {

        disassemble().forEach(e -> e.mul(fPoint.getX(), fPoint.getY(), fPoint.getZ()));

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
    public T div(FPoint fPoint) {

        disassemble().forEach(e -> e.div(fPoint.getX(), fPoint.getY(), fPoint.getZ()));

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

    @Override
    public T imprint(T element) {

        element.set(self());

        return self();
    }

    @Override
    public T cus(Consumer<T> exp) {

        exp.accept(self());

        return self();
    }

    @Override
    public double cusDouble(Function<T, Double> exp) {

        return exp.apply(self());
    }

    @Override
    public boolean cusBoolean(Predicate<T> exp) {

        return exp.test(self());
    }

    @Override
    public T ext(Consumer<BaseExtensionAssembly> exp) {

        exp.accept(self());

        return self();
    }

    @Override
    public List<Double> extDouble(Function<BaseExtensionAssembly, List<Double>> exp) {

        return exp.apply(self());
    }

    @Override
    public List<Boolean> extBoolean(Function<BaseExtensionAssembly, List<Boolean>> exp) {

        return exp.apply(self());
    }

}

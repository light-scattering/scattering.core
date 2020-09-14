package eu.scattering.core.design.main.algebra.engine.base.point;

public interface FPointFactory {

    FPoint getFPoint();

    default FPoint getFPoint(double x) {

        return getFPoint().setX(x);
    }

    default FPoint getFPoint(double x, double y) {

        return getFPoint().setX(x).setY(y);
    }

    default FPoint getFPoint(double x, double y, double z) {

        return getFPoint().set(x, y, z);
    }

    default FPoint getFPoint(FPoint fPoint) {

        return getFPoint().set(fPoint);
    }
}

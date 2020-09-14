package eu.scattering.core.support.helper;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

import java.util.concurrent.ThreadLocalRandom;

import static eu.scattering.core.Config.factory;

public final class RandomHelper {

    private RandomHelper() { }

    private static final double valueMax = +10000;
    private static final double valueMin = -10000;

    public static double getTestValue(double... exclude) {
        double value = 0;

        mainLoop:
        while (value > -Config.getJitter() && value < Config.getJitter()) {
            value = ThreadLocalRandom.current().nextDouble(valueMin, valueMax);

            for (double singularity : exclude) {
                if (value > (singularity - Config.getJitter()) && value < (singularity + Config.getJitter())) {
                    continue mainLoop;
                }
            }
        }

        return value;
    }

    public static FPoint getTestPoint(FPoint... exclude) {
        FPoint fPointZero = factory.getFPoint();
        FPoint fPoint = factory.getFPoint();

        mainLoop:
        while (fPoint.isSimilar(fPointZero)) {
            fPoint = factory.getFPoint(getTestValue(), getTestValue(), getTestValue());

            for (FPoint singularity : exclude) {
                if (fPoint.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fPoint;
    }

    public static FVector getTestVector(FVector... exclude) {
        FVector fVectorZero = factory.getFVector(factory.getFPoint());
        FVector fVector = factory.getFVector();

        mainLoop:
        while (fVector.isSimilar(fVectorZero)) {
            fVector = factory.getFVector(getTestPoint(), getTestPoint());

            for (FVector singularity : exclude) {
                if (fVector.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fVector;
    }

    public static FComplex getTestComplex(FComplex... exclude) {
        FComplex fComplexZero = factory.getFComplex();
        FComplex fComplex = factory.getFComplex();

        mainLoop:
        while (fComplex.isSimilar(fComplexZero)) {
            fComplex = factory.getFComplex(getTestValue(), getTestValue());

            for (FComplex singularity : exclude) {
                if (fComplex.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fComplex;
    }

    public static FQuaternion getTestQuaternion(FQuaternion... exclude) {
        FQuaternion fQuaternionZero = factory.getFQuaternion();
        FQuaternion fQuaternion = factory.getFQuaternion();

        mainLoop:
        while (fQuaternion.isSimilar(fQuaternionZero)) {
            fQuaternion = factory.getFQuaternion(getTestValue(), getTestValue(), getTestValue(), getTestValue());

            for (FQuaternion singularity : exclude) {
                if (fQuaternion.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fQuaternion;
    }
}

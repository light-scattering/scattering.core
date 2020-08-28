package eu.scattering.core.support.helper;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.injection.MainFactory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;

import java.util.concurrent.ThreadLocalRandom;

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
        FPoint fPointZero = MainFactory.getFPoint();
        FPoint fPoint = MainFactory.getFPoint();

        mainLoop:
        while (fPoint.isSimilar(fPointZero)) {
            fPoint = MainFactory.getFPoint(getTestValue(), getTestValue(), getTestValue());

            for (FPoint singularity : exclude) {
                if (fPoint.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fPoint;
    }

    public static FVector getTestVector(FVector... exclude) {
        FVector fVectorZero = MainFactory.getFVector(MainFactory.getFPoint());
        FVector fVector = MainFactory.getFVector();

        mainLoop:
        while (fVector.isSimilar(fVectorZero)) {
            fVector = MainFactory.getFVector(getTestPoint(), getTestPoint());

            for (FVector singularity : exclude) {
                if (fVector.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fVector;
    }

    public static FComplex getTestComplex(FComplex... exclude) {
        FComplex fComplexZero = MainFactory.getFComplex();
        FComplex fComplex = MainFactory.getFComplex();

        mainLoop:
        while (fComplex.isSimilar(fComplexZero)) {
            fComplex = MainFactory.getFComplex(getTestValue(), getTestValue());

            for (FComplex singularity : exclude) {
                if (fComplex.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fComplex;
    }

    public static FQuaternion getTestQuaternion(FQuaternion... exclude) {
        FQuaternion fQuaternionZero = MainFactory.getFQuaternion();
        FQuaternion fQuaternion = MainFactory.getFQuaternion();

        mainLoop:
        while (fQuaternion.isSimilar(fQuaternionZero)) {
            fQuaternion = MainFactory.getFQuaternion(getTestValue(), getTestValue(), getTestValue(), getTestValue());

            for (FQuaternion singularity : exclude) {
                if (fQuaternion.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fQuaternion;
    }
}

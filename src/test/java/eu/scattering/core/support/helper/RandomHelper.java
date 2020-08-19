package eu.scattering.core.support.helper;

import eu.scattering.core.Config;
import eu.scattering.core.factory.MainFactory;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.base.vector.FVector;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomHelper {

    private RandomHelper() { }

    private static final double valueMax = +10000;
    private static final double valueMin = -10000;

    private static final FPoint fPointZero = MainFactory.getIFPoint();
    private static final FVector fVectorZero = MainFactory.getIFVector(MainFactory.getIFPoint());

    public static double getTestValue(double... exclude) {
        double value = 0;

        mainLoop:
        while (value > -Config.jitter && value < Config.jitter) {
            value = ThreadLocalRandom.current().nextDouble(valueMin, valueMax);

            for (double singularity : exclude) {
                if (value > (singularity - Config.jitter) && value < (singularity + Config.jitter)) {
                    continue mainLoop;
                }
            }
        }

        return value;
    }

    public static FPoint getTestPoint(FPoint... exclude) {
        FPoint fPoint = MainFactory.getIFPoint();

        mainLoop:
        while (fPoint.isSimilar(fPointZero)) {
            fPoint = MainFactory.getIFPoint(getTestValue(), getTestValue(), getTestValue());

            for (FPoint singularity : exclude) {
                if (fPoint.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fPoint;
    }

    public static FVector getTestVector(FVector... exclude) {
        FVector fVector = MainFactory.getIFVector();

        mainLoop:
        while (fVector.isSimilar(fVectorZero)) {
            fVector = MainFactory.getIFVector(getTestPoint(), getTestPoint());

            for (FVector singularity : exclude) {
                if (fVector.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fVector;
    }

}

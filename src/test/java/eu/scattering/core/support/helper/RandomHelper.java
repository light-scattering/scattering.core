package eu.scattering.core.support.helper;

import eu.scattering.core.Config;
import eu.scattering.core.injection.EngineFactory;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomHelper {

    private RandomHelper() { }

    private static final double valueMax = +10000;
    private static final double valueMin = -10000;

    private static final FPoint fPointZero = EngineFactory.getFPoint();
    private static final FVector fVectorZero = EngineFactory.getFVector(EngineFactory.getFPoint());

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
        FPoint fPoint = EngineFactory.getFPoint();

        mainLoop:
        while (fPoint.isSimilar(fPointZero)) {
            fPoint = EngineFactory.getFPoint(getTestValue(), getTestValue(), getTestValue());

            for (FPoint singularity : exclude) {
                if (fPoint.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fPoint;
    }

    public static FVector getTestVector(FVector... exclude) {
        FVector fVector = EngineFactory.getFVector();

        mainLoop:
        while (fVector.isSimilar(fVectorZero)) {
            fVector = EngineFactory.getFVector(getTestPoint(), getTestPoint());

            for (FVector singularity : exclude) {
                if (fVector.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fVector;
    }

}

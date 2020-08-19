package eu.scattering.core.support.helper;

import eu.scattering.core.Config;
import eu.scattering.core.main.MainFactory;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.vector.IFVector;

import java.util.concurrent.ThreadLocalRandom;

public final class HelperRandom {

    private HelperRandom() { }

    private static final double valueMax = +10000;
    private static final double valueMin = -10000;

    private static final IFPoint fPointZero = MainFactory.getIFPoint();
    private static final IFVector fVectorZero = MainFactory.getIFVector(MainFactory.getIFPoint());

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

    public static IFPoint getTestPoint(IFPoint... exclude) {
        IFPoint fPoint = MainFactory.getIFPoint();

        mainLoop:
        while (fPoint.isSimilar(fPointZero)) {
            fPoint = MainFactory.getIFPoint(getTestValue(), getTestValue(), getTestValue());

            for (IFPoint singularity : exclude) {
                if (fPoint.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fPoint;
    }

    public static IFVector getTestVector(IFVector... exclude) {
        IFVector fVector = MainFactory.getIFVector();

        mainLoop:
        while (fVector.isSimilar(fVectorZero)) {
            fVector = MainFactory.getIFVector(getTestPoint(), getTestPoint());

            for (IFVector singularity : exclude) {
                if (fVector.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fVector;
    }

}

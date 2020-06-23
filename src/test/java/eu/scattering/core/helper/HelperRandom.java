package eu.scattering.core.helper;

import eu.scattering.core.Configuration;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;

import java.util.concurrent.ThreadLocalRandom;

public final class HelperRandom {

    private HelperRandom() { }

    private static final double valueMax = 10000;
    private static final double valueMin = -valueMax;

    private static final IFPoint fPointZero = FactoryGeometry.getIFPoint();
    private static final IFVector fVectorZero = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint());

    public static double getTestValue(double... exclude) {
        double value = 0;

        mainLoop:
        while (value > -Configuration.jitter && value < Configuration.jitter) {
            value = ThreadLocalRandom.current().nextDouble(valueMin, valueMax);

            for (double singularity : exclude) {
                if (value > (singularity - Configuration.jitter) && value < (singularity + Configuration.jitter)) {
                    continue mainLoop;
                }
            }
        }

        return value;
    }

    public static IFPoint getTestPoint(IFPoint... exclude) {
        IFPoint fPoint = FactoryGeometry.getIFPoint();

        mainLoop:
        while (fPoint.isSimilar(fPointZero)) {
            fPoint = FactoryGeometry.getIFPoint(getTestValue(), getTestValue(), getTestValue());

            for (IFPoint singularity : exclude) {
                if (fPoint.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fPoint;
    }

    public static IFVector getTestVector(IFVector... exclude) {
        IFVector fVector = FactoryGeometry.getIFVector();

        mainLoop:
        while (fVector.isSimilar(fVectorZero)) {
            fVector = FactoryGeometry.getIFVector(getTestPoint(), getTestPoint());

            for (IFVector singularity : exclude) {
                if (fVector.isSimilar(singularity)) {
                    continue mainLoop;
                }
            }
        }

        return fVector;
    }

}

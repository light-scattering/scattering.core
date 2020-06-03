package eu.scattering.core.helper;

import eu.scattering.core.Configuration;

import java.util.concurrent.ThreadLocalRandom;

public final class HelperRandom {

    private HelperRandom() { }

    private static final double valueMax = 10000;
    private static final double valueMin = -valueMax;

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

}

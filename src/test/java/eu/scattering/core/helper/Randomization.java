package eu.scattering.core.helper;

import java.util.concurrent.ThreadLocalRandom;

public class Randomization {

    public static double getTestValue() {
        double value = 0;

        while (value == 0) {
            value = ThreadLocalRandom.current().nextDouble(-10000, 10000);
        }

        return value;
    }

}

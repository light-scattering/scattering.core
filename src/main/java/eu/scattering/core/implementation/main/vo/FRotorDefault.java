package eu.scattering.core.implementation.main.vo;

import eu.scattering.core.design.main.vo.FRotor;

public class FRotorDefault implements FRotor {

    private double re, i, j, k;
    private double[][] core;;

    private FRotorDefault(double x, double y, double z, double angle) {

        initializeCore(x, y, z, angle);
        initializeRotor();
    }

    private void initializeCore(double x, double y, double z, double angle) {
        double magnitude = Math.sqrt((x * x) + (y * y) + (z * z));

        x /= magnitude;
        y /= magnitude;
        z /= magnitude;

        double factor = Math.sin(angle * 0.5);

        re = Math.cos(angle * 0.5);
        i = x * factor;
        j = y * factor;
        k = z * factor;
    }

    private void initializeRotor() {
        core = new double[3][3];

        core[0][0] = 1 - (2 * j * j) - (2 * k * k);
        core[0][1] = 2 * ((i * j) + (re * k));
        core[0][2] = 2 * ((i * k) - (re * j));
        core[1][0] = 2 * ((i * j) - (re * k));
        core[1][1] = 1 - (2 * i * i) - (2 * k * k);
        core[1][2] = 2 * ((j * k) + (re * i));
        core[2][0] = 2 * ((i * k) + (re * j));
        core[2][1] = 2 * ((j * k) - (re * i));
        core[2][2] = 1 - (2 * i * i) - (2 * j * j);
    }

    public static FRotor create(double x, double y, double z, double angle) {

        return new FRotorDefault(x, y, z, angle);
    }

    @Override
    public double[] getVector() {
        double factor;

        factor = 1 - (re * re);

        if (factor <= 0) {
            return new double[] {0, 0, 0, 0};
        }

        factor = 1 / Math.sqrt(factor);

        return new double[] {i * factor, j * factor, k * factor};
    }

    @Override
    public double getAngle() {

        if (re <= -1) {
            return Math.PI * 2;
        }

        if (re >= 1) {
            return 0;
        }

        return Math.acos(re) * 2;
    }

    @Override
    public double[] rotate(double x, double y, double z) {
        double[] result = new double[3];

        result[0] = (core[0][0] * x) + (core[0][1] * y) + (core[0][2] * z);
        result[1] = (core[1][0] * x) + (core[1][1] * y) + (core[1][2] * z);
        result[2] = (core[2][0] * x) + (core[2][1] * y) + (core[2][2] * z);

        return result;
    }
}

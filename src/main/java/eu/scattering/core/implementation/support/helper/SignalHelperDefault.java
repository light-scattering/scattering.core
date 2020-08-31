package eu.scattering.core.implementation.support.helper;

import eu.scattering.core.design.support.helper.SignalHelper;

public class SignalHelperDefault implements SignalHelper {

    @Override
    public double[] genSignalSine(int length, double deltaTime, double ampl, double freq, double phi) {
        double[] signal = new double[length];

        for (int i = 0; i < length; i++) {
            signal[i] = ampl * Math.sin(2 * Math.PI * freq * (i / deltaTime) + phi);
        }

        return signal;
    }

    @Override
    public double[] genWindowHanning(double[] signal) {
        double[] window = new double[signal.length];

        for (int i = 0; i < signal.length; i++) {
            window[i] = (signal[i] * (0.5 * (1 - Math.cos(2 * Math.PI * (double) i / (signal.length - 1)))));
        }

        return window;
    }
}

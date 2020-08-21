package eu.scattering.core.design.support;

import eu.scattering.core.design.main.engine.type.complex.FComplex;

public interface SignalHelper {

    double[] genSignalSine(int length, double deltaTime, double ampl, double freq, double phi);

    double[] genWindowHanning(double[] signal);

//    FComplex[] mapToComplex(double[] signal);
//    FComplex[][] mapToComplex(double[][] signal);
//
//    double[] getRe(FComplex[] signal);
//    double[][] getRe(FComplex[][] signal);
//    double[] getIm(FComplex[] signal);
//    double[][] getIm(FComplex[][] signal);
//
//    double[] getAmplitude(FComplex[] signal);
//    double[][] getAmplitude(FComplex[][] signal);
//    double[] getPhase(FComplex[] signal);
//    double[][] getPhase(FComplex[][] signal);
//
//    double[] normalize(double[] signal, double min, double max);
//    double[][] normalize(double[][] signal, double min, double max);
//    double[] removeMean(double[] signal);
//    double[][] removeMean(double[][] signal);
//    double[] adaptLengthForFFT(double[] signal);
//    double[][] adaptLengthForFFT(double[][] signal);
//
//    FComplex[] getDFT(FComplex[] signal);
//    FComplex[] getIDFT(FComplex[] signal);
//
//    FComplex[] getFFT(FComplex[] signal);
//    FComplex[] getIFFT(FComplex[] signal);
//    FComplex[][] getFFT(FComplex[][] signal);
//    FComplex[][] getIFFT(FComplex[][] signal);
}

package eu.scattering.core.design.main.vo;

public interface FRotor {

    double[] getVector();
    double getAngle();

    double[] rotate(double x, double y, double z);
}

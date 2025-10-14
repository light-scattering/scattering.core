package eu.scattering.core.design.statistics;

public interface StatisticsHelper {

    double getAbsErr(double arg1, double arg2);
    boolean valAbsErr(double arg1, double arg2, double epsilon);

    double getRelErr(double base, double ref);
    boolean valRelErr(double base, double ref, double epsilon);
}

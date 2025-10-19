package eu.scattering.core.design.statistics.base;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.construct.FPlot2D;

import java.util.List;
import java.util.function.Function;

public interface FStat1D extends Statistics<FStat1D>, Iterable<Double> {

    void add(double value);
    void add(double... value);
    void add(Function<Double, Double> collision, double value);
    void add(Function<Double, Double> collision, double... value);

    double get(int index);
    void set(int index, double value);

    // -------------------------------------------------------------------------------------------------

    double min();
    double max();
    double range();
    double midrange();

    double sum();
    double mean();

    double percentile(double n);
    double q1();
    double q2();
    double q3();
    double median();
    double midspread();

    double[] mode();

    double rms();

    double ss();
    double ss(double mean);

    double mad();
    double mad(double mean);

    double var(boolean sample);
    double var(boolean sample, double mean);

    double std(boolean sample);
    double std(boolean sample, double mean);

    double skewness(boolean sample);
    double skewness(boolean sample, double mean, double std);

    double kurtosis(boolean sample);
    double kurtosis(boolean sample, double mean, double std);

    double kurtosisExcess(boolean sample);
    double kurtosisExcess(boolean sample, double mean, double std);

    boolean isUnique();

    // -------------------------------------------------------------------------------------------------

    int filter(Function<Double, Boolean> function);

    int removeOutliers(boolean sample, double factor);
    int removeOutliers(double mean, double std, double factor);

    // -------------------------------------------------------------------------------------------------

    void mutate(Function<Double, Double> function);

    void sort(boolean ascending);

    void invert();
    void mirror();

    void normalize(boolean sample);
    void normalize(double mean, double std);

    void removeBias();
    void removeBias(double mean);

    // -------------------------------------------------------------------------------------------------

    double[] toArray();

    FPlot2D toFPlot2DLinear();
    FPlot2D toFPlot2DPieChart();
    FPlot2D toFPlot2DHistogram(double min, double max, int divisions);

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    void replaceWithNaN(Function<Double, Boolean> function);
    @Fragment
    void replaceOutliersWithNaN(boolean sample, double factor);
    @Fragment
    void replaceOutliersWithNaN(double mean, double std, double factor);

    @Modificator
    List<Double> getData();
    @Modificator
    void setData(List<Double> data);
}

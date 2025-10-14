package eu.scattering.core.transfer.statistics.FStat1D;

import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2D;
import eu.scattering.core.transfer.statistics.Statistics;

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

    void invertOrder();
    void invertValues();

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

    void replaceWithNaN(Function<Double, Boolean> function);

    void replaceOutliersWithNaN(boolean sample, double factor);
    void replaceOutliersWithNaN(double mean, double std, double factor);

    List<Double> getData();
    void setData(List<Double> data);

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);
}

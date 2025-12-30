package eu.scattering.core.design.statistics.base;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.construct.FPlot;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface FStat extends Statistics<FStat>, Iterable<Double> {

    void add(double value);
    void add(double... value);

    // -------------------------------------------------------------------------------------------------

    double get(int index);
    void set(int index, double value);

    boolean contains(double value);

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

    double[] mode();

    boolean allDistinct();

    // -------------------------------------------------------------------------------------------------

    int filter(Function<Double, Boolean> function);
    int filter(boolean dynamic, BiFunction<Double, Double, Boolean> function);

    int removeOutliers(boolean sample, double factor);
    int removeOutliers(double mean, double std, double factor);

    int deduplicate();

    // -------------------------------------------------------------------------------------------------

    void log(double base);

    void mutate(Function<Double, Double> function);
    void mutate(boolean dynamic, BiFunction<Double, Double, Double> function);

    void sort(boolean ascending);

    void invert();
    void mirror();

    void rescale();
    void rescale(double min, double max);

    void absolute();
    void distribute();

    void normalize(boolean sample);
    void normalize(double mean, double std);

    void removeBias();
    void removeBias(double mean);

    boolean isSimilarAbs(double threshold, FStat... comparison);
    boolean isSimilarRel(double threshold, FStat... comparison);

    // -------------------------------------------------------------------------------------------------

    double[] toArray();

    FPlot toFPlotLinear();
    FPlot toFPlotPieChart();

    FPlot toFPlotHistogram(double step);
    FPlot toFPlotHistogram(double min, double max, int divisions);

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    FStat removeNaN();
    @Fragment
    FStat replaceWithNaN(Function<Double, Boolean> function);
    @Fragment
    FStat replaceWithNaN(boolean dynamic, BiFunction<Double, Double, Boolean> function);
    @Fragment
    FStat replaceOutliersWithNaN(boolean sample, double factor);
    @Fragment
    FStat replaceOutliersWithNaN(double mean, double std, double factor);
    @Fragment
    FStat replaceSameWithNaN();
    @Fragment
    FStat replaceDecreasingWithNaN();
    @Fragment
    FStat replaceIncreasingWithNaN();

    @Modificator
    List<Double> getRefCore();

    // -------------------------------------------------------------------------------------------------

    default void ln() {

        log(Math.E);
    }
}

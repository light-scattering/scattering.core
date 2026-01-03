package eu.scattering.core.design.statistics.base;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface FStat extends Statistics<FStat>, Iterable<Double> {

    FStat add(double value);
    FStat add(double... value);

    // -------------------------------------------------------------------------------------------------

    double get(int index);
    FStat set(int index, double value);

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

    FStat log(double base);

    FStat mutate(Function<Double, Double> function);
    FStat mutate(boolean dynamic, BiFunction<Double, Double, Double> function);

    FStat sort(boolean ascending);

    FStat invert();
    FStat mirror();

    FStat rescale();
    FStat rescale(double min, double max);

    FStat absolute();
    FStat distribute();

    FStat normalize(boolean sample);
    FStat normalize(double mean, double std);

    FStat removeBias();
    FStat removeBias(double mean);

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
    FStat setName(String name);

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

    default FStat ln() {

        return log(Math.E);
    }
}

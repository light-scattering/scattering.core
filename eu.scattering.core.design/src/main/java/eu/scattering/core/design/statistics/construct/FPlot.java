package eu.scattering.core.design.statistics.construct;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.utils.FPlotInterpolator;
import eu.scattering.core.design.statistics.construct.utils.FPlotRegressor;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.transfer.primitive.FPos2D;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FPlot extends Statistics<FPlot> {

    void add(double x);
    void add(double x, double y);
    void add(BiFunction<Double, Double, Double> collision, double x);
    void add(BiFunction<Double, Double, Double> collision, double x, double y);

    // -------------------------------------------------------------------------------------------------

    double getX(int index);
    void setX(int index, double x);

    double getY(int index);
    void setY(int index, double y);

    // -------------------------------------------------------------------------------------------------

    <T> T getWithFStat(BiFunction<FStat, FStat, T> function);

    <T> T getWithFStatX(Function<FStat, T> function);
    <T> T getWithFStatY(Function<FStat, T> function);

    int getIndexX(Index type, double x);
    int getIndexY(Index type, double y);

    double integrate();

    double approximate(double x); //

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, Double, Boolean> filter);

    void setY(FPoly est);

    void mutateFStat(Consumer<FStat> consumer);
    void mutateFStat(BiConsumer<FStat, FStat> consumer);

    void mutateX(BiFunction<Double, Double, Double> function);
    void mutateFStatX(Consumer<FStat> consumer);

    void mutateY(BiFunction<Double, Double, Double> function);
    void mutateFStatY(Consumer<FStat> consumer);

    void interpolate(double step, boolean overflow); //
    void interpolate(double divisions); //

    void sortX(boolean ascending);
    void sortY(boolean ascending);

    void swapXY();

    // -------------------------------------------------------------------------------------------------

    void forEach(TriConsumer<Double, Double, Integer> consumer);

    double[][] toArray();

    FPlotRegressor reg();
    FPlotInterpolator apx();

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name); // comment, boundary.

    // -------------------------------------------------------------------------------------------------

    @Fragment
    FPlot removeNaN();

    @Fragment
    FPos2D getFPos2D(int index);

    @Modificator
    FStat getRefCoreX();
    @Modificator
    FStat getRefCoreY();

    // -------------------------------------------------------------------------------------------------

    enum Index { ROUND, FLOOR, CEIL }
}

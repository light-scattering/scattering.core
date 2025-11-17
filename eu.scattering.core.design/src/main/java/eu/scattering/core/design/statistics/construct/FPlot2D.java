package eu.scattering.core.design.statistics.construct;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.transfer.primitive.FPos2D;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FPlot2D extends Statistics<FPlot2D> {

    void add(double x);
    void add(double x, double y);
    void add(BiFunction<Double, Double, Double> collision, double x);
    void add(BiFunction<Double, Double, Double> collision, double x, double y);

    double getX(int index);
    void setX(int index, double x);

    double getY(int index);
    void setY(int index, double y);

    int getIndexX(Index type, double x);
    int getIndexY(Index type, double y);

    <T> T getWithFStat(BiFunction<FStat1D, FStat1D, T> function);

    <T> T getWithFStatX(Function<FStat1D, T> function);
    <T> T getWithFStatY(Function<FStat1D, T> function);

    // -------------------------------------------------------------------------------------------------

    double integrate();

    double approximate(double x);

    FPos2D simpleLinearRegression(); // Different types of regression, returns FPoly. Does not mutate results. Regression in range, eg. 2 - 3
    // Get slope

    double mse(FPoly est);

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, Double, Boolean> filter);

    void setY(FPoly est);

    void mutateFStat(Consumer<FStat1D> consumer);
    void mutateFStat(BiConsumer<FStat1D, FStat1D> consumer);

    void mutateX(BiFunction<Double, Double, Double> function);
    void mutateFStatX(Consumer<FStat1D> consumer);

    void mutateY(BiFunction<Double, Double, Double> function);
    void mutateFStatY(Consumer<FStat1D> consumer);

    void interpolate(double step, boolean overflow);
    void interpolate(double divisions);

    void sortX(boolean ascending);
    void sortY(boolean ascending);

    void swapXY();

    // -------------------------------------------------------------------------------------------------

    void forEach(TriConsumer<Double, Double, Integer> consumer);

    double[][] toArray();

    FPlot2DInterpolator getInterpolator();

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Modificator
    FStat1D getRefFStatX();
    @Modificator
    void setRefFStatX(FStat1D fStat1DX);

    @Modificator
    FStat1D getRefFStatY();
    @Modificator
    void setRefFStatY(FStat1D fStat1DY);

//    @Fragment
//    FPlot2D removeNull();

    @Fragment
    FPos2D getRecord(int index);

    // -------------------------------------------------------------------------------------------------

    enum Index { ROUND, FLOOR, CEIL }
    // Different toString
}

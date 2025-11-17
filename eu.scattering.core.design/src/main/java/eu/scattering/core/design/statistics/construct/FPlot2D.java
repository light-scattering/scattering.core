package eu.scattering.core.design.statistics.construct;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DRecord;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.transfer.primitive.FPos2D;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface FPlot2D extends Statistics<FPlot2D> {

    void add(double x);
    void add(double x, double y);
    void add(BiFunction<Double, Double, Double> collision, double x);
    void add(BiFunction<Double, Double, Double> collision, double x, double y);

    double getX(int index);
    void setX(int index, double x);

    double getY(int index);
    void setY(int index, double y);

    FStat1D getStatX();
    void setStatX(FStat1D statX); // Option to remove null (or leave).
    void mutateStatX(Consumer<FStat1D> action);

    FStat1D getStatY();
    void setStatY(FStat1D statY); // Option to remove null (or leave).
    void mutateStatY(Consumer<FStat1D> action);

    int getIndex(double x); // Closest or what? Is this needed?

    // -------------------------------------------------------------------------------------------------

    double minX(); // Needed?
    double maxX(); // Needed?

    double minY(); // Needed?
    double maxY(); // Needed?

    double integrate();

    double approximate(double x);

    FPos2D simpleLinearRegression(); // Different types of regression, returns FPoly. Does not mutate results. Regression in range, eg. 2 - 3
    // Get slope

    double mse(FPoly est);

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, Double, Boolean> filter);

    void mutateX(BiFunction<Double, Double, Double> function);
    void mutateY(BiFunction<Double, Double, Double> function);

    void interpolate(double step, boolean overflow);
    void interpolate(double divisions);

    void distribute(); // Maybe 1D?

    void sortX(boolean ascending);
    void sortY(boolean ascending);

    void setY(FPoly est);

    void swapXY();

    void log(double base, boolean x, boolean y); // Maybe 1D?

    // -------------------------------------------------------------------------------------------------

    void forEach(BiConsumer<Double, Double> consumer);

    double[][] toArray();

    FPlot2DRecord getRecord(int index); // No
    FPlot2DInterpolator getInterpolator();

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);

    // -------------------------------------------------------------------------------------------------

//    @Fragment
//    FPlot2D removeNull();

    @Fragment
    int getIndexCeil(double x);
    @Fragment
    int getIndexFloor(double x);

    // -------------------------------------------------------------------------------------------------

    default void ln(boolean x, boolean y) {

        log(Math.E, x, y);
    }

    // Get regression mean square error.
    // Different toString
}

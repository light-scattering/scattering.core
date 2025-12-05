package eu.scattering.core.design.statistics.construct;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.utils.FPlotInterpolator;
import eu.scattering.core.design.statistics.construct.utils.FPlotRegressor;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.type.Round;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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

    int getIndexX(Round type, double x);
    int getIndexY(Round type, double y);

    double integrate();

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, Double, Boolean> filter);

    void setY(FPoly est);

    void mutate(Consumer<FStat> consumer);
    void mutate(BiConsumer<FStat, FStat> consumer);

    void mutateX(Consumer<FStat> consumer);
    void mutateX(BiFunction<Double, Double, Double> function);

    void mutateY(Consumer<FStat> consumer);
    void mutateY(BiFunction<Double, Double, Double> function);

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
    void setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    FPlot removeNaN();

    @Modificator
    FStat getRefCoreX();
    @Modificator
    FStat getRefCoreY();
}

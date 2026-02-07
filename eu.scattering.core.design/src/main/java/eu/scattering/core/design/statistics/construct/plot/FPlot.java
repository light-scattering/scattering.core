package eu.scattering.core.design.statistics.construct.plot;

import eu.scattering.core.design.storage.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.utils.FPlotInterpolator;
import eu.scattering.core.design.statistics.construct.plot.utils.FPlotRegressor;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.utility.type.RoundMethod;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface FPlot extends Statistics<FPlot> {

    FPlot add(double x);
    FPlot add(double x, double y);
    FPlot add(BiFunction<Double, Double, Double> collision, double x);
    FPlot add(BiFunction<Double, Double, Double> collision, double x, double y);

    // -------------------------------------------------------------------------------------------------

    double getX(int index);
    FPlot setX(int index, double x);

    double getY(int index);
    FPlot setY(int index, double y);

    int getIndexX(RoundMethod type, double x);
    int getIndexY(RoundMethod type, double y);

    double integrate();

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, Double, Boolean> filter);

    FPlot setY(FPoly est);

    FPlot mutate(Consumer<FStat> consumer);
    FPlot mutate(BiConsumer<FStat, FStat> consumer);

    FPlot mutateX(Consumer<FStat> consumer);
    FPlot mutateX(BiFunction<Double, Double, Double> function);

    FPlot mutateY(Consumer<FStat> consumer);
    FPlot mutateY(BiFunction<Double, Double, Double> function);

    FPlot sortX(boolean ascending);
    FPlot sortY(boolean ascending);

    FPlot swapXY();

    // -------------------------------------------------------------------------------------------------

    FPlot forEach(TriConsumer<Double, Double, Integer> consumer);

    double[][] toArray();

    FPlotBar toFPlotBar();

    FPlotRegressor reg();
    FPlotInterpolator apx();

    // -------------------------------------------------------------------------------------------------

    String getName();
    FPlot setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    FPlot removeNaN();

    @Modificator
    FStat getRefCoreX();
    @Modificator
    FStat getRefCoreY();
}

package eu.scattering.core.design.statistics.construct.plotbar;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.type.Round;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface FPlotBar extends Statistics<FPlotBar> {

    FPlotBar add(double x);
    FPlotBar add(double x, double y);

    FPlotBar add(double x, FStat y);

    // -------------------------------------------------------------------------------------------------

    double getX(int index);
    FPlotBar setX(int index, double x);

    FStat getY(int index);
    FPlotBar setY(int index, FStat y);

    int getIndexX(Round type, double x);

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, FStat, Boolean> filter);

    FPlotBar mutateX(Consumer<FStat> consumer);
    FPlotBar mutateX(BiFunction<Double, FStat, Double> function);

    FPlotBar mutateY(Consumer<List<FStat>> consumer);
    FPlotBar mutateY(BiFunction<Double, FStat, FStat> function);

    FPlotBar sortX(boolean ascending);

    // -------------------------------------------------------------------------------------------------

    FPlotBar forEach(TriConsumer<Double, FStat, Integer> consumer);

    FPlot toFPlot(Function<FStat, Double> function);

    // -------------------------------------------------------------------------------------------------

    String getName();
    FPlotBar setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    FPlotBar removeNaN();

    @Modificator
    FPlotBar addRef(double x, FStat refY);
    @Modificator
    FStat getRefY(int index);
    @Modificator
    FPlotBar setRefY(int index, FStat refY);

    @Modificator
    FStat getRefCoreX();
    @Modificator
    List<FStat> getRefCoreY();
}

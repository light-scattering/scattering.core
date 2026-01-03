package eu.scattering.core.design.statistics.construct.plotbar;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.Statistics;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.type.Round;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface FPlotBar extends Statistics<FPlotBar> {

    void add(double x);
    void add(double x, double y);

    void add(double x, FStat y);

    // -------------------------------------------------------------------------------------------------

    double getX(int index);
    void setX(int index, double x);

    FStat getY(int index);
    void setY(int index, FStat y);

    int getIndexX(Round type, double x);

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, FStat, Boolean> filter);

    void mutateX(Consumer<FStat> consumer);
    void mutateX(BiFunction<Double, FStat, Double> function);

    void mutateY(Consumer<List<FStat>> consumer);
    void mutateY(BiFunction<Double, FStat, FStat> function);

    void sortX(boolean ascending);

    // -------------------------------------------------------------------------------------------------

    void forEach(TriConsumer<Double, FStat, Integer> consumer);

//    FPlot toFPlot(Function<Double, FStat> function);

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    FPlotBar removeNaN();

    @Modificator
    void addRef(double x, FStat refY);
    @Modificator
    FStat getRefY(int index);
    @Modificator
    void setRefY(int index, FStat refY);

    @Modificator
    FStat getRefCoreX();
    @Modificator
    List<FStat> getRefCoreY();
}

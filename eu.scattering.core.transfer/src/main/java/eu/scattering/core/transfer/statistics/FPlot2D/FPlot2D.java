package eu.scattering.core.transfer.statistics.FPlot2D;

import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.statistics.FStat1D.FStat1D;
import eu.scattering.core.transfer.statistics.Statistics;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

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
    void setStatX(FStat1D statX);

    FStat1D getStatY();
    void setStatY(FStat1D statY);

    int getIndexCeil(double x);
    int getIndexFloor(double x);
    int getIndexRound(double x);

    // -------------------------------------------------------------------------------------------------

    double minX();
    double maxX();

    double minY();
    double maxY();

    double approximate(double x);

    // -------------------------------------------------------------------------------------------------

    int filter(BiFunction<Double, Double, Boolean> filter);

    void mutateX(BiFunction<Double, Double, Double> function);
    void mutateY(BiFunction<Double, Double, Double> function);

    void interpolate(double step, boolean overflow);
    void interpolate(double divisions);

    void sortX(boolean ascending);
    void sortY(boolean ascending);

    FPos2D simpleLinearRegression();

    // -------------------------------------------------------------------------------------------------

    void forEach(BiConsumer<Double, Double> consumer);

    double[][] toArray();

    FPlot2DRecord getRecord(int index);
    FPlot2DInterpolator getInterpolator();

    // -------------------------------------------------------------------------------------------------

    String getName();
    void setName(String name);
}

package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.utils.FPlotInterpolator;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import eu.scattering.core.design.type.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FPlotInterpolatorDef implements FPlotInterpolator {
    private final ScatFactory factory;
    private final FPlot data;

    private FPlotInterpolatorDef(ScatFactory factory, FPlot data) {

        this.factory = factory;
        this.data = data;
    }

    protected static FPlotInterpolator create(ScatFactory factory, FPlot data) {

        return new FPlotInterpolatorDef(factory, data);
    }

    @Override
    public double linear(double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR2 = getIndexR1(data, x);

        if (indexL1 == indexR2) {
            return data.getY(indexL1);
        }

        FPos2D recordL1 = getRecord(indexL1);
        FPos2D recordR1 = getRecord(indexR2);

        double tmp = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());

        return recordL1.getD1() * (1 - tmp) + (recordR1.getD1() * tmp);
    }

    @Override
    public double cosine(double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR2 = getIndexR1(data, x);

        if (indexL1 == indexR2) {
            return data.getY(indexL1);
        }

        FPos2D recordL1 = getRecord(indexL1);
        FPos2D recordR1 = getRecord(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = (1 - Math.cos(tmp1 * Math.PI)) / 2;

        return recordL1.getD1() * (1 - tmp2) + (recordR1.getD1() * tmp2);
    }

    @Override
    public double cubic(double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getY(indexL1);
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPos2D recordL2 = getRecord(indexL2);
        FPos2D recordL1 = getRecord(indexL1);
        FPos2D recordR1 = getRecord(indexR1);
        FPos2D recordR2 = getRecord(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = tmp1 * tmp1;

        double a0 = recordR2.getD1() - recordR1.getD1() - recordL2.getD1() + recordR1.getD1();
        double a1 = recordL2.getD1() - recordL1.getD1() - a0;
        double a2 = recordR1.getD1() - recordL2.getD1();
        double a3 = recordL1.getD1();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    @Override
    public double catmullRom(double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getY(indexL1);
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPos2D recordL2 = getRecord(indexL2);
        FPos2D recordL1 = getRecord(indexL1);
        FPos2D recordR1 = getRecord(indexR1);
        FPos2D recordR2 = getRecord(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = tmp1 * tmp1;

        double a0 = (-0.5 * recordL2.getD1()) + (1.5 * recordL1.getD1()) - (1.5 * recordR1.getD1()) + (0.5 * recordR2.getD1());
        double a1 = recordL2.getD1() - (2.5 * recordL1.getD1()) + (2 * recordR1.getD1()) - (0.5 * recordR2.getD1());
        double a2 = (-0.5 * recordL2.getD1()) + (0.5 * recordR1.getD1());
        double a3 = recordL1.getD1();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    @Override
    public FPlot sampleStep(BiFunction<FPlotInterpolator, Double, Double> function, double step) {
        double min = data.getRefCoreX().min();
        double max = data.getRefCoreX().max();

        return sampleStep(function, min, max, step);
    }

    @Override
    public FPlot sampleStep(BiFunction<FPlotInterpolator, Double, Double> function, double min, double max, double step) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step value must be greater than zero");
        }

        double minX = data.getRefCoreX().min();
        double maxX = data.getRefCoreX().max();

        List<Double> fStatX = new ArrayList<>();
        List<Double> fStatY = new ArrayList<>();

        double value = minX;
        while (value <= maxX) {
            fStatX.add(value);
            fStatY.add(function.apply(this, value));

            value += step;
        }

        if (Math.abs(value - max) < EPSILON) {
            fStatX.add(max);
            fStatY.add(function.apply(this, max));
        }

        return factory.getRefFPlot(factory.getRefFStat(fStatX), factory.getRefFStat(fStatY));
    }

    @Override
    public FPlot sampleDivisions(BiFunction<FPlotInterpolator, Double, Double> function, int divisions) {
        double min = data.getRefCoreX().min();
        double max = data.getRefCoreX().max();

        return sampleDivisions(function, min, max, divisions);
    }

    @Override
    public FPlot sampleDivisions(BiFunction<FPlotInterpolator, Double, Double> function, double min, double max, int divisions) {

        if (divisions < 1) {
            throw new IllegalArgumentException("The number of divisions cannot be smaller then one");
        }

        double step = (max - min) / divisions;

        List<Double> fStatX = new ArrayList<>();
        List<Double> fStatY = new ArrayList<>();

        double value = min;
        while (value < max) {
            fStatX.add(value);
            fStatY.add(function.apply(this, value));

            value += step;
        }

        fStatX.add(max);
        fStatY.add(function.apply(this, max));

        return factory.getRefFPlot(factory.getRefFStat(fStatX), factory.getRefFStat(fStatY));
    }

    @Override
    public double hermite(double x) {

        return hermite(x, 0, 0);
    }

    @Override
    public double hermite(double x, double bias, double tension) {


        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getY(indexL1);
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPos2D recordL2 = getRecord(indexL2);
        FPos2D recordL1 = getRecord(indexL1);
        FPos2D recordR1 = getRecord(indexR1);
        FPos2D recordR2 = getRecord(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = tmp1 * tmp1;
        double tmp3 = tmp2 * tmp1;

        double m0 = (recordL1.getD1() - recordL2.getD1()) * (1 + bias) * ((1 - tension) / 2);
        m0 += (recordR1.getD1() - recordL1.getD1()) * (1 - bias) * ((1 - tension) / 2);
        double m1 = (recordR1.getD1() - recordL1.getD1()) * (1 + bias) * ((1 - tension) / 2);
        m1 += (recordR2.getD1() - recordR1.getD1()) * ( 1 - bias) * ((1 - tension) / 2);


        double a0 = (2 * tmp3) - (3 * tmp2) + 1;
        double a1 = tmp3 - (2 * tmp2) + tmp1;
        double a2 = tmp3 - tmp2;
        double a3 = (-2 * tmp3) + (3 * tmp2);

        return (a0 * recordL1.getD1()) + (a1 * m0) + (a2 * m1) + (a3 * recordR1.getD1());
    }

    //--------------------------------------------------

    private FPos2D getRecord(int index) {

        return factory.getFPos2D(data.getX(index), data.getY(index));
    }

    private int getIndexL1(FPlot data, double x) {
        int indexL1 = data.getIndexX(Round.FLOOR, x);

        if (indexL1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        return indexL1;
    }

    private int getIndexR1(FPlot data, double x) {
        int indexR1 = data.getIndexX(Round.CEIL, x);

        if (indexR1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        return indexR1;
    }

    private int getIndexL2(int indexL1) {
        int indexL2 = indexL1 - 1;

        if (indexL2 < 0) {
            indexL2 = 0;
        }

        return indexL2;
    }

    private int getIndexR2(FPlot data, int indexR1) {
        int indexR2 = indexR1 + 1;

        if (indexR2 > data.size() - 1) {
            indexR2 = data.size() - 1;
        }

        return indexR2;
    }
}

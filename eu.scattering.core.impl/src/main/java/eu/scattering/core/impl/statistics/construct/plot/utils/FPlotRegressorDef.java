package eu.scattering.core.impl.statistics.construct.plot.utils;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.utils.FPlotRegressor;
import eu.scattering.core.design.transfer.primitive.FPoly;

public class FPlotRegressorDef implements FPlotRegressor {
    private final ScatFactory factory;
    private final FPlot data;

    private FPlotRegressorDef(ScatFactory factory, FPlot data) {

        this.factory = factory;
        this.data = data;
    }

    public static FPlotRegressor create(ScatFactory factory, FPlot data) {

        return new FPlotRegressorDef(factory, data);
    }

    @Override
    public FPoly poly(int degree) {
        int min = 0;
        int max = data.size() - 1;

        return poly(degree, min, max);
    }

    @Override
    public FPoly poly(int degree, int min, int max) {

        return switch (degree) {
            case 0 -> fitConstant(min, max);
            case 1 -> fitLinear(min, max);

            default -> throw new IllegalStateException("The method has not been implemented: Poly '" + degree + "'");
        };
    }

    @Override
    public FPoly fitConstant() {

        return poly(0);
    }

    @Override
    public FPoly fitConstant(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("The min value must be greater than the max value");
        }

        if (min < 0) {
            throw new IllegalArgumentException("The min value cannot be lower than zero");
        }

        if (max > data.size() - 1) {
            throw new IllegalArgumentException("The max value cannot be greater than the FPlot size");
        }

        double sum = 0;
        for (int i = min ; i <= max ; i++) {
            sum += data.getY(i);
        }

        return factory.getFPoly(sum / (max - min + 1));
    }

    @Override
    public FPoly fitLinear() {

        return poly(1);
    }

    @Override
    public FPoly fitLinear(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("The min value must be greater than the max value");
        }

        if (min < 0) {
            throw new IllegalArgumentException("The min value cannot be lower than zero");
        }

        if (max > data.size() - 1) {
            throw new IllegalArgumentException("The max value cannot be greater than the FPlot size");
        }

        double mx = 0;
        double my = 0;

        for (int i = min ; i <= max ; i++) {
            mx += data.getX(i);
            my += data.getY(i);
        }

        mx /= (max - min + 1);
        my /= (max - min + 1);

        double numerator = 0;
        double denominator = 0;

        for (int i = min ; i <= max ; i++) {
            numerator += (data.getX(i) - mx) * (data.getY(i) - my);
            denominator += Math.pow(data.getX(i) - mx, 2);
        }

        double a = numerator / denominator;
        double b = my - (a * mx);

        return factory.getFPoly(b, a);
    }

    @Override
    public FPoly fitSlope(int window) {

        if (window < 3) {
            throw new IllegalArgumentException("The window must be at least three elements wide");
        }

        if (window >= data.size()) {
            throw new IllegalArgumentException("The window must be smaller than the size of the FPlot");
        }

        FPoly slope = null;
        double mseMin = Double.MAX_VALUE;

        for (int i = 0 ; i < data.size() - window ; i++) {
            FPoly candidate = fitLinear(i, i + window);
            double error = mse(candidate, i, i + window);

            if (error < mseMin) {
                slope = candidate;
                mseMin = error;
            }
        }

        return slope;
    }

    @Override
    public double mse(FPoly candidate) {

        return mse(candidate, 0, data.size() - 1);
    }

    @Override
    public double mse(FPoly candidate, int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("The min value must be greater than the max value");
        }

        if (min < 0) {
            throw new IllegalArgumentException("The min value cannot be lower than zero");
        }

        if (max > data.size() - 1) {
            throw new IllegalArgumentException("The max value cannot be greater than the FPlot size");
        }

        double mse = 0;

        for (int i = min ; i <= max ; i++) {
            mse += Math.pow(data.getY(i) - candidate.value(data.getX(i)), 2);
        }

        return mse / (max - min + 1);
    }

    @Override
    public double rmse(FPoly candidate) {

        return Math.sqrt(mse(candidate));
    }

    @Override
    public double rmse(FPoly candidate, int min, int max) {

        return Math.sqrt(mse(candidate, min, max));
    }
}

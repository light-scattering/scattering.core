package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.statistics.construct.utils.FPlotRegressor;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.transfer.primitive.FPolyFactory;

public class FPlotRegressorDef implements FPlotRegressor {
    private final FPolyFactory factory;
    private final FPlot data;

    private FPlotRegressorDef(FPolyFactory factory, FPlot data) {

        this.factory = factory;
        this.data = data;
    }

    protected static FPlotRegressor create(FPolyFactory factory, FPlot data) {

        return new FPlotRegressorDef(factory, data);
    }

    @Override
    public FPoly poly(int n) {
        int min = 0;
        int max = data.size() - 1;

        return poly(n, min, max);
    }

    @Override
    public FPoly poly(int n, int min, int max) {

        return switch (n) {
            case 0 -> poly0(min, max);
            case 1 -> poly1(min, max);

            default -> throw new IllegalStateException("The method has not been implemented: Poly '" + n + "'");
        };
    }

    @Override
    public FPoly poly0(int min, int max) {

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
    public FPoly poly1(int min, int max) {

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
    public FPoly slope(int window) {

        if (window < 3) {
            throw new IllegalArgumentException("The window must be at least three elements wide");
        }

        if (window >= data.size()) {
            throw new IllegalArgumentException("The window must be smaller than the size of the FPlot");
        }

        FPoly slope = null;
        double mseMin = Double.MAX_VALUE;

        for (int i = 0 ; i < data.size() - window ; i++) {
            FPoly candidate = poly1(i, i + window);
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
            mse += Math.pow(data.getY(i) - candidate.getValue(data.getX(i)), 2);
        }

        return mse / data.size();
    }
}

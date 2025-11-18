package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.statistics.construct.utils.FPlotInterpolator;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import org.json.JSONObject;

public class FPlotInterpolatorDef implements FPlotInterpolator {
    private static final String JSON_METHOD = "method";
    private static final String JSON_H_BIAS = "bias";
    private static final String JSON_H_TENSION = "tension";

    private Method method = Method.HERMITE;
    private double hTension = 0;
    private double hBias = 0;

    private FPlotInterpolatorDef() {}

    protected static FPlotInterpolator create() {

        return new FPlotInterpolatorDef();
    }

    protected static FPlotInterpolator create(JSONObject json) {
        FPlotInterpolator interpolator = new FPlotInterpolatorDef();

        interpolator.setMethod(json.getEnum(Method.class, JSON_METHOD));
        interpolator.setHermiteTension(json.getDouble(JSON_H_TENSION));
        interpolator.setHermiteBias(json.getDouble(JSON_H_BIAS));

        return interpolator;
    }

    @Override
    public Method getMethod() {

        return this.method;
    }

    @Override
    public void setMethod(Method method) {

        this.method = method;
    }

    @Override
    public double getHermiteBias() {

        return this.hBias;
    }

    @Override
    public void setHermiteBias(double bias) {

        this.hBias = bias;
    }

    @Override
    public double getHermiteTension() {

        return this.hTension;
    }

    @Override
    public void setHermiteTension(double tension) {

        this.hTension = tension;
    }

    @Override
    public double apx(FPlot data, double x) {

        return switch (this.method) {
            case LINEAR -> apxLinear(data, x);
            case COSINE -> apxCosine(data, x);
            case CUBIC -> apxCubic(data, x);
            case CATMULL_ROM -> apxCatmullRom(data, x);
            case HERMITE -> apxHermite(data, x);
        };
    }

    @Override
    public double apxLinear(FPlot data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR2 = getIndexR1(data, x);

        if (indexL1 == indexR2) {
            return data.getY(indexL1);
        }

        FPos2D recordL1 = data.getFPos2D(indexL1);
        FPos2D recordR1 = data.getFPos2D(indexR2);

        double tmp = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());

        return recordL1.getD1() * (1 - tmp) + (recordR1.getD1() * tmp);
    }

    @Override
    public double apxCosine(FPlot data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR2 = getIndexR1(data, x);

        if (indexL1 == indexR2) {
            return data.getY(indexL1);
        }

        FPos2D recordL1 = data.getFPos2D(indexL1);
        FPos2D recordR1 = data.getFPos2D(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = (1 - Math.cos(tmp1 * Math.PI)) / 2;

        return recordL1.getD1() * (1 - tmp2) + (recordR1.getD1() * tmp2);
    }

    @Override
    public double apxCubic(FPlot data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getY(indexL1);
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPos2D recordL2 = data.getFPos2D(indexL2);
        FPos2D recordL1 = data.getFPos2D(indexL1);
        FPos2D recordR1 = data.getFPos2D(indexR1);
        FPos2D recordR2 = data.getFPos2D(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = tmp1 * tmp1;

        double a0 = recordR2.getD1() - recordR1.getD1() - recordL2.getD1() + recordR1.getD1();
        double a1 = recordL2.getD1() - recordL1.getD1() - a0;
        double a2 = recordR1.getD1() - recordL2.getD1();
        double a3 = recordL1.getD1();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    @Override
    public double apxCatmullRom(FPlot data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getY(indexL1);
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPos2D recordL2 = data.getFPos2D(indexL2);
        FPos2D recordL1 = data.getFPos2D(indexL1);
        FPos2D recordR1 = data.getFPos2D(indexR1);
        FPos2D recordR2 = data.getFPos2D(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = tmp1 * tmp1;

        double a0 = (-0.5 * recordL2.getD1()) + (1.5 * recordL1.getD1()) - (1.5 * recordR1.getD1()) + (0.5 * recordR2.getD1());
        double a1 = recordL2.getD1() - (2.5 * recordL1.getD1()) + (2 * recordR1.getD1()) - (0.5 * recordR2.getD1());
        double a2 = (-0.5 * recordL2.getD1()) + (0.5 * recordR1.getD1());
        double a3 = recordL1.getD1();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    @Override
    public double apxHermite(FPlot data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getY(indexL1);
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPos2D recordL2 = data.getFPos2D(indexL2);
        FPos2D recordL1 = data.getFPos2D(indexL1);
        FPos2D recordR1 = data.getFPos2D(indexR1);
        FPos2D recordR2 = data.getFPos2D(indexR2);

        double tmp1 = (x - recordL1.getD0()) / (recordR1.getD0() - recordL1.getD0());
        double tmp2 = tmp1 * tmp1;
        double tmp3 = tmp2 * tmp1;

        double m0 = (recordL1.getD1() - recordL2.getD1()) * (1 + this.hBias) * ((1 - this.hTension) / 2);
        m0 += (recordR1.getD1() - recordL1.getD1()) * (1 - this.hBias) * ((1 - this.hTension) / 2);
        double m1 = (recordR1.getD1() - recordL1.getD1()) * (1 + this.hBias) * ((1 - this.hTension) / 2);
        m1 += (recordR2.getD1() - recordR1.getD1()) * ( 1 - this.hBias) * ((1 - this.hTension) / 2);


        double a0 = (2 * tmp3) - (3 * tmp2) + 1;
        double a1 = tmp3 - (2 * tmp2) + tmp1;
        double a2 = tmp3 - tmp2;
        double a3 = (-2 * tmp3) + (3 * tmp2);

        return (a0 * recordL1.getD1()) + (a1 * m0) + (a2 * m1) + (a3 * recordR1.getD1());
    }

    //--------------------------------------------------

    private int getIndexL1(FPlot data, double x) {
        int indexL1 = data.getIndexX(FPlot.Index.FLOOR, x);

        if (indexL1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        return indexL1;
    }

    private int getIndexR1(FPlot data, double x) {
        int indexR1 = data.getIndexX(FPlot.Index.CEIL, x);

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

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_METHOD, getMethod());
        json.put(JSON_H_BIAS, getHermiteBias());
        json.put(JSON_H_TENSION, getHermiteTension());

        return json;
    }

    //--------------------------------------------------

    @Override
    public boolean isEqual(FPlotInterpolator interpolator) {

        if (getHermiteBias() != interpolator.getHermiteBias()) {
            return false;
        }

        if (getHermiteTension() != interpolator.getHermiteTension()) {
            return false;
        }

        return getMethod().equals(interpolator.getMethod());
    }

    @Override
    public FPlotInterpolator copy() {
        FPlotInterpolator copy = FPlotInterpolatorDef.create();

        copy.setMethod(getMethod());
        copy.setHermiteBias(getHermiteBias());
        copy.setHermiteTension(getHermiteTension());

        return copy;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }
}

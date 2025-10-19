package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DRecord;
import org.json.JSONObject;

public class FPlot2DInterpolatorDef implements FPlot2DInterpolator {
    private static final String JSON_METHOD = "method";
    private static final String JSON_H_BIAS = "bias";
    private static final String JSON_H_TENSION = "tension";

    private Method method = Method.HERMITE;
    private double hTension = 0;
    private double hBias = 0;

    private FPlot2DInterpolatorDef() {}

    protected static FPlot2DInterpolator create() {

        return new FPlot2DInterpolatorDef();
    }

    protected static FPlot2DInterpolator create(JSONObject json) {
        FPlot2DInterpolator interpolator = new FPlot2DInterpolatorDef();

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
    public double apx(FPlot2D data, double x) {

        return switch (this.method) {
            case LINEAR -> apxLinear(data, x);
            case COSINE -> apxCosine(data, x);
            case CUBIC -> apxCubic(data, x);
            case CATMULL_ROM -> apxCatmullRom(data, x);
            case HERMITE -> apxHermite(data, x);
        };
    }

    @Override
    public double apxLinear(FPlot2D data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR2 = getIndexR1(data, x);

        if (indexL1 == indexR2) {
            return data.getRecord(indexL1).getY();
        }

        FPlot2DRecord recordL1 = data.getRecord(indexL1);
        FPlot2DRecord recordR1 = data.getRecord(indexR2);

        double tmp = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());

        return recordL1.getY() * (1 - tmp) + (recordR1.getY() * tmp);
    }

    @Override
    public double apxCosine(FPlot2D data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR2 = getIndexR1(data, x);

        if (indexL1 == indexR2) {
            return data.getRecord(indexL1).getY();
        }

        FPlot2DRecord recordL1 = data.getRecord(indexL1);
        FPlot2DRecord recordR1 = data.getRecord(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = (1 - Math.cos(tmp1 * Math.PI)) / 2;

        return recordL1.getY() * (1 - tmp2) + (recordR1.getY() * tmp2);
    }

    @Override
    public double apxCubic(FPlot2D data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getRecord(indexL1).getY();
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPlot2DRecord recordL2 = data.getRecord(indexL2);
        FPlot2DRecord recordL1 = data.getRecord(indexL1);
        FPlot2DRecord recordR1 = data.getRecord(indexR1);
        FPlot2DRecord recordR2 = data.getRecord(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = tmp1 * tmp1;

        double a0 = recordR2.getY() - recordR1.getY() - recordL2.getY() + recordR1.getY();
        double a1 = recordL2.getY() - recordL1.getY() - a0;
        double a2 = recordR1.getY() - recordL2.getY();
        double a3 = recordL1.getY();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    @Override
    public double apxCatmullRom(FPlot2D data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getRecord(indexL1).getY();
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPlot2DRecord recordL2 = data.getRecord(indexL2);
        FPlot2DRecord recordL1 = data.getRecord(indexL1);
        FPlot2DRecord recordR1 = data.getRecord(indexR1);
        FPlot2DRecord recordR2 = data.getRecord(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = tmp1 * tmp1;

        double a0 = (-0.5 * recordL2.getY()) + (1.5 * recordL1.getY()) - (1.5 * recordR1.getY()) + (0.5 * recordR2.getY());
        double a1 = recordL2.getY() - (2.5 * recordL1.getY()) + (2 * recordR1.getY()) - (0.5 * recordR2.getY());
        double a2 = (-0.5 * recordL2.getY()) + (0.5 * recordR1.getY());
        double a3 = recordL1.getY();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    @Override
    public double apxHermite(FPlot2D data, double x) {
        int indexL1 = getIndexL1(data, x);
        int indexR1 = getIndexR1(data, x);

        if (indexL1 == indexR1) {
            return data.getRecord(indexL1).getY();
        }

        int indexL2 = getIndexL2(indexL1);
        int indexR2 = getIndexR2(data, indexR1);

        FPlot2DRecord recordL2 = data.getRecord(indexL2);
        FPlot2DRecord recordL1 = data.getRecord(indexL1);
        FPlot2DRecord recordR1 = data.getRecord(indexR1);
        FPlot2DRecord recordR2 = data.getRecord(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = tmp1 * tmp1;
        double tmp3 = tmp2 * tmp1;

        double m0 = (recordL1.getY() - recordL2.getY()) * (1 + this.hBias) * ((1 - this.hTension) / 2);
        m0 += (recordR1.getY() - recordL1.getY()) * (1 - this.hBias) * ((1 - this.hTension) / 2);
        double m1 = (recordR1.getY() - recordL1.getY()) * (1 + this.hBias) * ((1 - this.hTension) / 2);
        m1 += (recordR2.getY() - recordR1.getY()) * ( 1 - this.hBias) * ((1 - this.hTension) / 2);


        double a0 = (2 * tmp3) - (3 * tmp2) + 1;
        double a1 = tmp3 - (2 * tmp2) + tmp1;
        double a2 = tmp3 - tmp2;
        double a3 = (-2 * tmp3) + (3 * tmp2);

        return (a0 * recordL1.getY()) + (a1 * m0) + (a2 * m1) + (a3 * recordR1.getY());
    }

    //--------------------------------------------------

    private int getIndexL1(FPlot2D data, double x) {
        int indexL1 = data.getIndexFloor(x);

        if (indexL1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        return indexL1;
    }

    private int getIndexR1(FPlot2D data, double x) {
        int indexR1 = data.getIndexCeil(x);

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

    private int getIndexR2(FPlot2D data, int indexR1) {
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
    public boolean isEqual(FPlot2DInterpolator interpolator) {

        if (getHermiteBias() != interpolator.getHermiteBias()) {
            return false;
        }

        if (getHermiteTension() != interpolator.getHermiteTension()) {
            return false;
        }

        return getMethod().equals(interpolator.getMethod());
    }

    @Override
    public FPlot2DInterpolator copy() {
        FPlot2DInterpolator copy = FPlot2DInterpolatorDef.create();

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

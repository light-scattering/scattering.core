package eu.scattering.core.implementation.main.algebra.type.complex;

import eu.scattering.core.Config;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.implementation.main.algebra.AlgebraPreset;
import org.json.JSONArray;
import org.json.JSONObject;

import static eu.scattering.core.Config.mainFactory;

public class FComplexDefault extends AlgebraPreset<FComplex> implements FComplex {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0 };

    private FComplexDefault() { }

    public static FComplex create() {

        return new FComplexDefault();
    }

    @Override
    public double getRe() {

        return origin[0];
    }

    @Override
    public FComplex setRe(double re) {

        origin[0] = re;

        return this;
    }

    @Override
    public double getIm() {

        return origin[1];
    }

    @Override
    public FComplex setIm(double im) {

        origin[1] = im;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex set(FComplex fComplex) {

        return set(fComplex.getRe(), fComplex.getIm());
    }

    @Override
    public FComplex set(double re, double im) {

        return setRe(re).setIm(im);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FComplex fComplex) {

        if (fComplex == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == fComplex) {
            return true;
        }

        return getRe() == fComplex.getRe() && getIm() == fComplex.getIm();
    }

    @Override
    public boolean isSimilar(FComplex fComplex) {

        if (fComplex == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == fComplex) {
            return true;
        }

        double distanceRe = Math.abs(getRe() - fComplex.getRe());
        double distanceIm = Math.abs(getIm() - fComplex.getIm());

        return distanceRe < Config.getJitter() && distanceIm < Config.getJitter();
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append("complex", getRe());
        json.append("complex", getIm());

        return json;
    }

    @Override
    public FComplex importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("complex");

        setRe(structure.getDouble(0));
        setIm(structure.getDouble(1));

        return this;
    }

    @Override
    public FComplex copy() {

        return mainFactory.getFComplex().set(this);
    }

    @Override
    public FComplex self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + (int) (getRe() * 100);
        hashCode = 31 * hashCode + (int) (getIm() * 100);

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FComplex) {
            return isExact((FComplex) object);
        }

        return false;
    }

    @Override
    public Object clone() {

        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex add(FComplex element) {

        return add(element.getRe(), element.getIm());
    }

    @Override
    public FComplex add(double re, double im) {

        return addRe(re).addIm(im);
    }

    @Override
    public FComplex add(double factor) {

        return add(factor, factor);
    }

    @Override
    public FComplex addRe(double re) {

        return setRe(getRe() + re);
    }

    @Override
    public FComplex addIm(double im) {

        return setIm(getIm() + im);
    }

    @Override
    public FComplex sub(FComplex element) {

        return sub(element.getRe(), element.getIm());
    }

    @Override
    public FComplex sub(double re, double im) {

        return subRe(re).subIm(im);
    }

    @Override
    public FComplex sub(double factor) {

        return sub(factor, factor);
    }

    @Override
    public FComplex subRe(double re) {

        return setRe(getRe() - re);
    }

    @Override
    public FComplex subIm(double re) {

        return setIm(getIm() - re);
    }

    @Override
    public FComplex mul(FComplex element) {

        double valueRe = getRe() * element.getRe() - getIm() * element.getIm();
        double valueIm = getRe() * element.getIm() + getIm() * element.getRe();

        return set(valueRe, valueIm);
    }

    @Override
    public FComplex mul(double re, double im) {

        return mulRe(re).mulIm(im);
    }

    @Override
    public FComplex mul(double factor) {

        return mul(factor, factor);
    }

    @Override
    public FComplex mulRe(double re) {

        return setRe(getRe() * re);
    }

    @Override
    public FComplex mulIm(double im) {

        return setIm(getIm() * im);
    }

    @Override
    public FComplex div(FComplex element) {

        if (element.isZero()) {
            throw new IllegalArgumentException("The divisor cannot be zero");
        }

        double nominatorRe = (getRe() * element.getRe()) + (getIm() * element.getIm());
        double nominatorIm = (getIm() * element.getRe()) - (getRe() * element.getIm());
        double denominator = (element.getRe() * element.getRe()) + (element.getIm() * element.getIm());

        return set(nominatorRe / denominator, nominatorIm / denominator);
    }

    @Override
    public FComplex div(double re, double im) {

        return divRe(re).divIm(im);
    }

    @Override
    public FComplex div(double factor) {

        return div(factor, factor);
    }

    @Override
    public FComplex divRe(double re) {

        if (re == 0) {
            throw new IllegalArgumentException("The real part of the FComplex value cannot be zero");
        }

        return setRe(getRe() / re);
    }

    @Override
    public FComplex divIm(double im) {

        if (im == 0) {
            throw new IllegalArgumentException("The imaginary part of the FComplex value cannot be zero");
        }

        return setIm(getIm() / im);
    }


    // -------------------------------------------------------------------------------------------------


    @Override
    public boolean isExact(double re, double im) {

        return getRe() == re && getIm() == im;
    }

    @Override
    public boolean isSimilar(double re, double im) {
        double distanceRe = Math.abs(getRe() - re);
        double distanceIm = Math.abs(getIm() - im);

        return distanceRe < Config.getJitter() && distanceIm < Config.getJitter();
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt((getRe() * getRe()) + (getIm() * getIm()));
    }

    @Override
    public FComplex setMagnitude(double magnitude) {
        double phase = getPhase();

        return setRe(magnitude * Math.cos(phase)).setIm(magnitude * Math.sin(phase));
    }

    @Override
    public double getPhase() {
        double magnitude = getMagnitude();

        if (getIm() >= 0 && magnitude != 0) {
            return Math.acos(getRe() / magnitude);
        }

        if (getIm() < 0) {
            return Math.acos(getRe() / magnitude) * (-1);
        }

        return 0;
    }

    @Override
    public FComplex setPhase(double phase) {
        double magnitude = getMagnitude();

        return setRe(magnitude * Math.cos(phase)).setIm(magnitude * Math.sin(phase));
    }


    @Override
    public FComplex pow(int n) {
        double pow = Math.pow(getMagnitude(), n);

        return setRe(pow * Math.cos(n * getPhase())).setIm(pow * Math.sin(n * getPhase()));
    }

    @Override
    public FComplex[] root(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("The root value must be greater than zero");
        }

        FComplex[] res = new FComplex[n];
        double tmp = Math.pow(getMagnitude(), 1 / (double) n);
        double phase = getPhase();

        for (int i = 0; i < n; i++) {
            double valueRe = tmp * Math.cos((phase + (2 * i * Math.PI)) / n);
            double valueIm = tmp * Math.sin((phase + (2 * i * Math.PI)) / n);

            res[i] = mainFactory.getFComplex(valueRe, valueIm);
        }

        return res;
    }

    @Override
    public FComplex inverse() {

        return mul(-1);
    }

    @Override
    public FComplex conjugate() {

        return mulIm(-1);
    }

    @Override
    public FComplex normalize() {

        return setMagnitude(1);
    }

    @Override
    public FComplex imprint(FComplex element) {

        element.set(this);

        return this;
    }

    @Override
    public boolean isZero() {

        return getRe() == 0 && getIm() == 0;
    }

}

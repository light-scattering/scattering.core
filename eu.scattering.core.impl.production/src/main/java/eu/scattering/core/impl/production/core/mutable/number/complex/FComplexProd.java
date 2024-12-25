package eu.scattering.core.impl.production.core.mutable.number.complex;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.engine.random.FRandom;
import org.json.JSONArray;
import org.json.JSONObject;

public class FComplexProd implements FComplex {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0 };
    private final Factory factory;
    private final FRandom random;
    private final double epsilon;

    private FComplexProd(Factory factory, FRandom random, double epsilon) {

        this.factory = factory;
        this.random = random;
        this.epsilon = epsilon;
    }

    public static FComplex create(Factory factory, FRandom random, double epsilon) {

        return new FComplexProd(factory, random, epsilon);
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
    public FComplex applyStateFrom(FComplex ref) {

        return set(ref.getRe(), ref.getIm());
    }

    @Override
    public FComplex set(double re, double im) {

        return setRe(re).setIm(im);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FComplex ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == ref) {
            return true;
        }

        return getRe() == ref.getRe() && getIm() == ref.getIm();
    }

    @Override
    public boolean isSimilar(FComplex ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == ref) {
            return true;
        }

        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceIm = Math.abs(getIm() - ref.getIm());

        return distanceRe < epsilon && distanceIm < epsilon;
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

        return FComplexProd.create(factory, random, epsilon).applyStateFrom(this);
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

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex add(FComplex ref) {

        return add(ref.getRe(), ref.getIm());
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
    public FComplex sub(FComplex ref) {

        return sub(ref.getRe(), ref.getIm());
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
    public FComplex mul(FComplex ref) {

        double valueRe = getRe() * ref.getRe() - getIm() * ref.getIm();
        double valueIm = getRe() * ref.getIm() + getIm() * ref.getRe();

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
    public FComplex div(FComplex ref) {

        if (ref.isZero()) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        double nominatorRe = (getRe() * ref.getRe()) + (getIm() * ref.getIm());
        double nominatorIm = (getIm() * ref.getRe()) - (getRe() * ref.getIm());
        double denominator = (ref.getRe() * ref.getRe()) + (ref.getIm() * ref.getIm());

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
            throw new ArithmeticException("The real part of the FComplex value cannot be zero");
        }

        return setRe(getRe() / re);
    }

    @Override
    public FComplex divIm(double im) {

        if (im == 0) {
            throw new ArithmeticException("The imaginary part of the FComplex value cannot be zero");
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

        return distanceRe < epsilon && distanceIm < epsilon;
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt((getRe() * getRe()) + (getIm() * getIm()));
    }

    @Override
    public double getMagnitudeP2() {

        return (getRe() * getRe()) + (getIm() * getIm());
    }

    @Override
    public FComplex setMagnitude(double magnitude) {
        double phase = getPhase();

        return setRe(magnitude * Math.cos(phase)).setIm(magnitude * Math.sin(phase));
    }

    @Override
    public double getDistance(FComplex ref) {
        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceIm = Math.abs(getIm() - ref.getIm());

        return Math.sqrt((distanceRe * distanceRe) + (distanceIm * distanceIm));
    }

    // TODO Not implemented
    @Override
    public FComplex setDistance(FComplex ref, double distance) {

        return null;
    }

    @Override
    public double getDistanceP2(FComplex ref) {
        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceIm = Math.abs(getIm() - ref.getIm());

        return (distanceRe * distanceRe) + (distanceIm * distanceIm);
    }

    @Override
    public double getPhase() {

        if (isZero()) {
            throw new IllegalStateException("The direction is not defined");
        }

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
    public FComplex power(int n) {
        double power = Math.pow(getMagnitude(), n);
        double phase = getPhase();

        return setRe(power * Math.cos(n * phase)).setIm(power * Math.sin(n * phase));
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

            res[i] = factory.getFComplex(valueRe, valueIm);
        }

        return res;
    }

    @Override
    public FComplex negate() {

        return mul(-1);
    }

    @Override
    public FComplex inverse() {

        factory.getFComplex(1, 0).div(this).applyStateTo(this);

        return this;
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
    public FComplex applyStateTo(FComplex ref) {

        ref.applyStateFrom(this);

        return this;
    }

    @Override
    public boolean isZero() {

        return getRe() == 0 && getIm() == 0;
    }
}
